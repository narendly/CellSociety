package grid_displays;

import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;


/**
 * Handles the display and positioning of a 2d grid of interlocking squares
 *
 * @author David Maydew
 *
 */
public class SquareGridDisplay extends GridDisplay {

    public SquareGridDisplay (double dispWidth, double dispHeight) {
        super(dispWidth, dispHeight);
    }

    @Override
    protected Shape createNewCell (Position position, double cellWidth, double cellHeight) {
        double xOffset = position.getYCoord() * cellWidth;
        double yOffset = position.getXCoord() * cellHeight;
        return new Rectangle(xOffset, yOffset, cellWidth, cellHeight);
    }

}
