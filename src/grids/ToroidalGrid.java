package grids;

import java.util.List;
import grid_displays.Position;
import simulation_cells.Cell;


/**
 * Manages a toroidal grid with appropriate neighbor calculations
 *
 * @author David Maydew
 *
 * @param <E> type of cell
 */
public class ToroidalGrid<E extends Cell> extends CellGrid<E> {

    public ToroidalGrid (List<List<E>> cellGrid) {
        super(cellGrid);
    }

    /**
     * Adds the cell based on the given position to a running list, based on a toroidal grid where
     * if the position is outside of the grid bounds, the coordinates wrap around from the opposite
     * side of the grid and access that cell
     */
    @Override
    protected void addCellToList (List<E> cells, Position position) {
        int wrappedXCoord = (position.getXCoord() % getMaxX() + getMaxX()) % getMaxX();
        int wrappedYCoord = (position.getYCoord() % getMaxY() + getMaxY()) % getMaxY();
        cells.add(getCell(new Position(wrappedXCoord, wrappedYCoord)));
    }
}
