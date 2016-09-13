package simulation_managers;

import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import grid_displays.Position;
import simulation_cells.SegregationCell;
import states.SegregationState;


/**
 * Manager for the back-end model of a Segregation simulation
 *
 * @author David Maydew
 *
 */
public class SegregationManager extends AbstractCellManager<SegregationCell> {
    private static final String THRESHOLD_KEY = "threshold";
    private static final double DEFAULT_THRESHOLD = 0.5;

    private Set<SegregationCell> myEmptyCells;

    @Override
    protected SegregationCell createNewCell (Integer initialStateNum,
                                             Map<String, Number> parameters) {
        SegregationState initialState = SegregationState.values()[initialStateNum];
        double threshold = getThreshold(parameters);
        return new SegregationCell(initialState, threshold);
    }

    private double getThreshold (Map<String, Number> parameters) {
        if (parameters.containsKey(THRESHOLD_KEY)) {
            return parameters.get(THRESHOLD_KEY).doubleValue();
        }
        else {
            return DEFAULT_THRESHOLD;
        }
    }

    /**
     * Includes necessary preparation for a Segregation next state calculation, such as finding the
     * active empty cells
     */
    @Override
    protected void prepareForNextStateCalc () {
        calcAndSetEmptyCells();
    }

    @Override
    protected void calculateNextState (SegregationCell cell, Position position) {
        Set<SegregationCell> neighbors = getNeighbors(position);
        cell.calculateNextState(neighbors, getEmptyCells());
    }

    /**
     * Includes necessary preparation for a Segregation stability check, such as finding the active
     * empty cells
     */
    @Override
    protected void prepareForStableCheck () {
        calcAndSetEmptyCells();
    }

    @Override
    protected boolean isCellStable (SegregationCell cell, Position position) {
        Set<SegregationCell> neighbors = getNeighbors(position);
        return cell.isStable(neighbors, getEmptyCells());
    }

    /**
     * Finds all of the currently empty cells in preparation for their (simultaneous) use during the
     * next state calculations
     */
    private void calcAndSetEmptyCells () {
        Set<SegregationCell> emptyCells = new HashSet<SegregationCell>();
        for (SegregationCell segCell : getAllCells()) {
            if (segCell.getState() == SegregationState.EMPTY) {
                emptyCells.add(segCell);
            }
        }
        myEmptyCells = emptyCells;
    }

    private Set<SegregationCell> getEmptyCells () {
        return myEmptyCells;
    }

    @Override
    protected SegregationCell createNewCell (Map<String, Number> parametersMap) {
        int randomStateNum = new Random().nextInt(SegregationState.values().length);
        return createNewCell(randomStateNum, parametersMap);
    }
}
