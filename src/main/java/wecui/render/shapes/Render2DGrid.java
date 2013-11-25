package wecui.render.shapes;

import java.util.List;

import org.lwjgl.opengl.GL11;

import wecui.obfuscation.RenderObfuscation;
import wecui.render.LineColor;
import wecui.render.LineInfo;
import wecui.render.points.PointRectangle;

/**
 * Draws the grid for a polygon region
 * 
 * @author yetanotherx
 * @author lahwran
 */
public class Render2DGrid {

    protected RenderObfuscation obf = RenderObfuscation.getInstance();
    protected LineColor color;
    protected List<PointRectangle> points;
    protected int min;
    protected int max;

    public Render2DGrid(LineColor color, List<PointRectangle> points, int min, int max) {
        this.color = color;
        this.points = points;
        this.min = min;
        this.max = max;
    }

    public void render() {
        double off = 0.03;
        for (double height = min; height <= max + 1; height++) {
            drawPoly(height + off);
        }
    }

    protected void drawPoly(double height) {
        for (LineInfo tempColor : color.getColors()) {
            tempColor.prepareRender();

            obf.startDrawing(GL11.GL_LINE_LOOP);
            tempColor.prepareColor();
            for (PointRectangle point : points) {
                if (point != null) {
                    obf.addVertex(point.getPoint().getX() + 0.5, height, point.getPoint().getY() + 0.5);
                }
            }
            obf.finishDrawing();
        }
    }
}
