package grid_displays;

import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;


/**
 * Handles the display and positioning of a 2d grid of interlocking triangles
 *
 * @author David Maydew
 *
 */
public class TriangleGridDisplay extends GridDisplay {

    private static final double OFFSET = 0.5;

    public TriangleGridDisplay (double dispWidth, double dispHeight) {
        super(dispWidth, dispHeight);
    }

    /**
     * Creates and positions a triangle based on geometric calculations of triangle
     */
    @Override
    protected Shape createNewCell (Position position, double width, double height) {
        int x = position.getXCoord();
        int y = position.getYCoord();
        double[] xPoints;
        double[] yPoints;
        if ((x + y) % 2 == 0) {
            xPoints =
                    new double[] { width * (y / 2 + OFFSET), width * (y / 2 + 1), width * (y / 2) };
            yPoints = new double[] { x * height, (x + 1) * height, (x + 1) * height };
            if (x % 2 == 1) {
                shiftElements(xPoints, width * OFFSET);
            }
        }
        else {
            xPoints =
                    new double[] { width * (y / 2), width * (y / 2 + OFFSET), width * (y / 2 + 1) };
            yPoints = new double[] { x * height, (x + 1) * height, x * height };
            if (x % 2 == 0) {
                shiftElements(xPoints, width * OFFSET);
            }
        }
        return new Polygon(interleave(xPoints, yPoints));
    }
}
