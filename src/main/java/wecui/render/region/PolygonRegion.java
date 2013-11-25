package wecui.render.region;

import java.util.ArrayList;
import java.util.List;

import wecui.WorldEditCUI;
import wecui.render.LineColor;
import wecui.render.points.PointRectangle;
import wecui.render.shapes.Render2DBox;
import wecui.render.shapes.Render2DGrid;

/**
 * Main controller for a polygon-type region
 * 
 * @author yetanotherx
 * @author lahwran
 */
public class PolygonRegion extends BaseRegion {

    protected List<PointRectangle> points = new ArrayList<PointRectangle>();
    protected int min;
    protected int max;

    public PolygonRegion(WorldEditCUI controller) {
        super(controller);
    }

    @Override
    public void render() {
        if (points == null) {
            return;
        }

        for (PointRectangle point : points) {
            point.render(min, max);
        }

        new Render2DBox(LineColor.POLYBOX, points, min, max).render();
        new Render2DGrid(LineColor.POLYGRID, points, min, max).render();

    }

    @Override
    public void setMinMax(int min, int max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public void setPolygonPoint(int id, int x, int z) {
        PointRectangle point = new PointRectangle(x, z);
        point.setColor(LineColor.POLYPOINT);
        
        if (id < points.size()) {
            points.set(id, point);
        } else {
            for (int i = 0; i < id - points.size(); i++) {
                points.add(null);
            }
            points.add(point);
        }
    }

    @Override
    public RegionType getType() {
        return RegionType.POLYGON;
    }
}
