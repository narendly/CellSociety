package grid_displays;

import javafx.scene.shape.Polygon;
import javafx.scene.shape.Shape;


/**
 * Handles the display and positioning of a 2d grid of interlocking hexagons
 *
 * @author David Maydew
 *
 */
public class HexagonGridDisplay extends GridDisplay {

    public HexagonGridDisplay (double dispWidth, double dispHeight) {
        super(dispWidth, dispHeight);
    }

    /**
     * Creates and positions a hexagon based on geometric calculations of hexagons
     */
    @Override
    protected Shape createNewCell (Position position, double cellWidth, double cellHeight) {
        int x = position.getXCoord();
        int y = position.getYCoord();
        double[] xCoords =
                new double[] { (y + 1.0 / 3) * cellWidth, (y + 1) * cellWidth,
                               (y + 4.0 / 3) * cellWidth, (y + 1) * cellWidth,
                               (y + 1.0 / 3) * cellWidth, y * cellWidth };
        double[] yCoords =
                new double[] { x * cellHeight, x * cellHeight, (x + 0.5) * cellHeight,
                               (x + 1) * cellHeight, (x + 1) * cellHeight, (x + 0.5) * cellHeight };
        if (y % 2 == 1) {
            shiftElements(yCoords, 0.5 * cellHeight);
        }

        return new Polygon(interleave(xCoords, yCoords));

    }
}
