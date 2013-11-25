package wecui.obfuscation;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet3Chat;
import wecui.WorldEditCUI;
import wecui.event.ChatCommandEvent;
import wecui.event.OutgoingChatEvent;

/**
 * Overrides the dataPacket list in NetworkManager
 * Uses reflection to replace the list with this. It overrides the add()
 * method to check if it's a Packet3Chat event.
 * 
 * @author yetanotherx
 * 
 */
public class DataPacketList<T> extends ArrayList<T> {

    private static final long serialVersionUID = 275687258277L;
    protected WorldEditCUI controller;
    protected Class<T> typeClass;
    public static boolean registered = false;

    public DataPacketList(WorldEditCUI controller, Class<T> typeClass) {
        this.controller = controller;
        this.typeClass = typeClass;
    }

    /**
     * Overrides the packet addition class. If a Packet3Chat is added, there's an outgoing
     * message and we need to parse it. If it's a command, send a command event. If it's 
     * cancelled, let's not add it at all.
     * 
     * @param packet
     * @return 
     */
    public boolean add(T packet) {
        if (packet instanceof Packet3Chat) {

            boolean cancelled = false;
            String s = Obfuscation.getChatFromPacket((Packet3Chat) packet);

            OutgoingChatEvent chatevent = new OutgoingChatEvent(controller, s);
            controller.getEventManager().callEvent(chatevent);
            if (!chatevent.isCancelled() && s.startsWith("/") && s.length() > 1) {
                ChatCommandEvent commandevent = new ChatCommandEvent(controller, s);
                controller.getEventManager().callEvent(commandevent);
                if (commandevent.isHandled() || commandevent.isCancelled()) {
                    cancelled = true;
                }
            }

            if (!cancelled) {
                return super.add(packet);
            }
            return true;
        }
        return super.add(packet);
    }

    /**
     * Attaches the new packet handler to the actual NetworkManager class
     * 
     * @param controller 
     */
    public static void register(WorldEditCUI controller) {

        if (registered) {
            return;
        }
        registered = true;

        DataPacketList<Packet> list = new DataPacketList<Packet>(controller, Packet.class);
        Obfuscation obf = controller.getObfuscation();

        //Checks if it's a multiplayer world
        if (!obf.isMultiplayerWorld()) {
            return;
        }

        EntityClientPlayerMP player = (EntityClientPlayerMP) obf.getPlayer();

        try {
            NetClientHandler nch = obf.getNetClientHandler(player);

            Field nmField = NetClientHandler.class.getDeclaredField(FieldObfuscation.NETWORKMANAGER.getVariable());
            nmField.setAccessible(true);
            Object nmMebbe = nmField.get(nch);
            NetworkManager nm = null;
            if (nmMebbe instanceof NetworkManager) {
                nm = (NetworkManager) nmField.get(nch);
            } else {
                return;
            }

            Field listField = NetworkManager.class.getDeclaredField(FieldObfuscation.PACKETLIST.getVariable());
            listField.setAccessible(true);
            List oldPacketList = (List) listField.get(nm);
            for (Object item : oldPacketList) {
                list.add((Packet) item);
            }

            listField.set(nm, list);
            nmField.set(nch, nm);

        } catch (Exception e) {
            throw new RuntimeException("Error inserting outgoing chat handler - Certain parts of WorldEditCUI will not work!", e);
        }

        controller.getDebugger().debug("Outgoing chat handler registered.");
    }
}
