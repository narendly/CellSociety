package simulation_cells;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import states.SegregationState;


/**
 * Handles the model updates for a given Cell in the Segregation Cellular Automata Simulation
 *
 * @author Sophie Guo, David Maydew
 */
public class SegregationCell extends Cell {

    private double myThreshold;

    /**
     * Constructs a SegregationCell with the given state and the threshold percentage of neighbors
     * required to be satisfied
     *
     * @param initialCellState to set as the current State
     * @param threshold the percentage of neighbors required to be satisfied
     */
    public SegregationCell (SegregationState initialState, double threshold) {
        super(initialState);
        setThreshold(threshold);
    }

    /**
     * Determines whether or not this SegregationCell is satisfied by calculating its nextState and
     * seeing
     * if it is different than the current state
     *
     * @param neighbors up to 8 adjacent cells
     * @param emptyCells a Collection of emptyCells that will be updated if they are filled for the
     *        next generation
     * @return whether or not this cell is satisfied
     */
    public boolean isStable (Set<SegregationCell> neighbors, Set<SegregationCell> emptyCells) {
        calculateNextState(neighbors, emptyCells);
        return getState() == getNextState();
    }

    /**
     * Calculates and sets the next state of this SegregationCell based on its neighbors
     *
     * @param neighbors up to 8 adjacent cells
     * @param emptyCells a Collection of emptyCells that will be updated if they are filled for the
     *        next generation
     */
    public void calculateNextState (Set<SegregationCell> neighbors,
                                    Set<SegregationCell> emptyCells) {
        if (nextAlreadyCalculated()) {
            return;
        }
        setNextState(getState());
        if (getState() == SegregationState.EMPTY) {
            return;
        }
        else {
            boolean dissatisfied = calcSameNeighborRatio(neighbors) < getThreshold();
            if (dissatisfied) {
                swapWithEmptyCell(emptyCells);
            }
        }
    }

    private double calcSameNeighborRatio (Set<SegregationCell> neighbors) {
        double nonEmptyNeighbors = 0;
        double sameStateNeighbors = 0;
        for (Cell cell : neighbors) {
            if (cell.getState() != SegregationState.EMPTY) {
                nonEmptyNeighbors++;
                if (cell.getState() == getState()) {
                    sameStateNeighbors++;
                }
            }
        }
        return (nonEmptyNeighbors > 0) ? sameStateNeighbors / nonEmptyNeighbors : 0.0;
    }

    /**
     * Swaps the next generation of this cell with a random cell from the given list
     *
     * @param emptyCells mutable collection of empty cells that can be swapped with
     */
    private void swapWithEmptyCell (Set<SegregationCell> emptyCells) {
        if (emptyCells.size() > 0) {
            int randomIndex = new Random().nextInt(emptyCells.size());
            List<Cell> cellList = new ArrayList<Cell>(emptyCells);
            Cell randomCell = cellList.get(randomIndex);
            randomCell.setNextState(getState());
            setNextState(SegregationState.EMPTY);
            emptyCells.remove(randomCell);
        }
    }

    private double getThreshold () {
        return myThreshold;
    }

    private void setThreshold (double threshold) {
        myThreshold = threshold;
    }
}
