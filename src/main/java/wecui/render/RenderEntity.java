package wecui.render;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import wecui.WorldEditCUI;

/**
 * Custom entity renderer, attached in the ModLoader class
 * 
 * @author lahwran
 * @author yetanotherx
 */
public class RenderEntity extends Entity {

    protected WorldEditCUI controller;

    public RenderEntity(WorldEditCUI controller, WorldClient world) {
        super(world);

        this.controller = controller;
        this.ignoreFrustumCheck = true; // Entity.ignoreFrustumCheck
        this.noClip = true; // Entity.noClip
        this.setSize(0, 0); // Entity.setSize()
        controller.getDebugger().debug("Entity spawned");
    }

    /**
     * Entity.readEntityFromNBT
     * @param arg0 
     */
    @Override
    protected void readEntityFromNBT(NBTTagCompound arg0) {
    }

    /**
     * Entity.writeEntityToNBT
     * @param arg0 
     */
    @Override
    protected void writeEntityToNBT(NBTTagCompound arg0) {
    }

    /**
     * Entity.entityInit
     */
    @Override
    protected void entityInit() {
    }

    /**
     * Entity.onUpdate
     */
    @Override
    public void onUpdate() {
        //Obfuscation.setEntityPositionToPlayer(controller.getMinecraft(), this);
    }

    /**
     * Entity.setDead
     */
    @Override
    public void setDead() {
    }

    /**
     * Entity.whatEver()
     * Returns the name of the entity.
     * @return 
     
    @Override
    public String whatEver() {
        return "CUI";
    }
    */

    /**
     * Entity.isInRangeToRenderVec3D
     * Always returns true, we want to render the entity no matter where we are.
     * @param vector
     * @return 
     */
    @Override
    public boolean isInRangeToRenderVec3D(Vec3 vector) {
        return true;
    }

    /**
     * Entity.getBrightnessForRender
     * Always return the maximum value, we want to always see it.
     * @param f
     * @return 
     */
    @Override
    public int getBrightnessForRender(float f) {
        return 0xf000f0;
    }

    /**
     * Entity.getBrightness
     * Always return the maximum value, we want to always see it.
     * @param f
     * @return 
     */
    @Override
    public float getBrightness(float f) {
        return 1f;
    }
}
