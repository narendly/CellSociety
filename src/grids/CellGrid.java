package grids;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import grid_displays.Position;
import simulation_cells.Cell;


/**
 * Stores and provides to access to a 2d array of one type of cell
 *
 * @author David Maydew
 *
 * @param <E> The type of cell to be stored in this grid
 */
public abstract class CellGrid<E extends Cell> {
    private List<List<E>> myCells;

    public CellGrid (List<List<E>> cellGrid) {
        myCells = cellGrid;
    }

    public List<E> getAllCellsInOrder () {
        List<E> allCells = new LinkedList<E>();
        for (List<E> row : getMyCells()) {
            allCells.addAll(row);
        }
        return allCells;
    }

    /**
     * @param basePosition the base position of the cell whose neighbors are to be returned
     * @param numNeighborsConsidered 4 for cardinal, 6 for hexagonal, 8 for all including diagonals
     * @param rangeMultiplier number of cell levels away to consider a neighbor
     * @return ordered list of neighbors sorted by distance from base cell
     */
    public List<E> getNeighbors (Position basePosition,
                                 int numNeighborsConsidered,
                                 int rangeMultiplier) {
        List<E> neighbors = new LinkedList<E>();
        int[] xCoordinateShifts = { -1, 0, 1, 0, 1, 1, -1, -1 };
        int[] yCoordinateShifts = { 0, 1, 0, -1, 1, -1, 1, -1 };
        for (int level = 1; level < rangeMultiplier + 1; level++) {
            int checkedMaxNeighbors = Math.min(numNeighborsConsidered, xCoordinateShifts.length);
            for (int i = 0; i < checkedMaxNeighbors; i++) {
                int neighborX = basePosition.getXCoord() + xCoordinateShifts[i] * level;
                int neighborY = basePosition.getYCoord() + yCoordinateShifts[i] * level;
                addCellToList(neighbors, new Position(neighborX, neighborY));
            }
        }
        return neighbors;
    }

    /**
     * Handles adding the cell (or lack thereof) at a given position to a running list of cells.
     *
     * @param cells running list of cells to add to
     * @param position of the cell to add to the running list
     */
    protected abstract void addCellToList (List<E> cells, Position position);

    public Collection<E> getAllCells () {
        Collection<E> allCells = new HashSet<E>();
        for (List<E> row : getMyCells()) {
            allCells.addAll(row);
        }
        return allCells;
    }

    public List<List<E>> getCellsInGrid () {
        return getMyCells();
    }

    /**
     * @return The specific cell at the given position
     */
    protected E getCell (Position position) {
        return getMyCells().get(position.getXCoord()).get(position.getYCoord());
    }

    public int getMaxX () {
        return getMyCells().size();
    }

    public int getMaxY () {
        return getMyCells().get(0).size();
    }

    private List<List<E>> getMyCells () {
        return myCells;
    }

}
