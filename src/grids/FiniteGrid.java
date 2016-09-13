package grids;

import java.util.List;
import grid_displays.Position;
import simulation_cells.Cell;


/**
 * Manages a Finite grid with appropriate neighbor calculations
 *
 * @author David Maydew
 *
 * @param <E> type of cell
 */
public class FiniteGrid<E extends Cell> extends CellGrid<E> {

    public FiniteGrid (List<List<E>> cellGrid) {
        super(cellGrid);
    }

    /**
     * Adds the cell based on the given position to a running list, based on a finite grid where if
     * the position is outside of the grid bounds, no cell is added
     */
    @Override
    protected void addCellToList (List<E> cells, Position position) {
        int xCoord = position.getXCoord();
        int yCoord = position.getYCoord();
        if (xCoord >= 0 && xCoord < getMaxX() &&
            yCoord >= 0 && yCoord < getMaxY()) {
            cells.add(getCell(position));
        }

    }

}
