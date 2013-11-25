package wecui.render.region;

import wecui.WorldEditCUI;
import wecui.render.LineColor;
import wecui.render.points.PointCube;
import wecui.render.shapes.RenderEllipsoid;
import wecui.util.Vector3;

/**
 * Main controller for a ellipsoid-type region
 * 
 * @author yetanotherx
 * @author lahwran
 */
public class EllipsoidRegion extends BaseRegion {

    protected PointCube center;
    protected Vector3 radii;
    
    public EllipsoidRegion(WorldEditCUI controller) {
        super(controller);
    }

    @Override
    public void render() {
        if( center != null && radii != null ) {
            center.render();
            
            new RenderEllipsoid(LineColor.ELLIPSOIDGRID, center, radii).render();
            
        }
        else if( center != null ) {
            center.render();
        }
    }

    @Override
    public void setEllipsoidCenter(int x, int y, int z) {
        center = new PointCube(x, y, z);
        center.setColor(LineColor.ELLIPSOIDCENTER);
    }

    @Override
    public void setEllipsoidRadii(double x, double y, double z) {
        radii = new Vector3(x, y, z);
    }

    @Override
    public RegionType getType() {
        return RegionType.ELLIPSOID;
    }
    
}
