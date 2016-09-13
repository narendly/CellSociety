package simulation_managers;

import java.util.Map;
import java.util.Random;
import grid_displays.Position;
import simulation_cells.FireCell;
import states.FireState;


/**
 * Manager for the back end model for a Fire simulation
 *
 * @author David Maydew
 */
public class FireManager extends AbstractCellManager<FireCell> {
    private static final String PROBCATCH_KEY = "probCatch";
    private static final double DEFAULT_PROBCATCH = 0.5;

    /**
     * Creates a new FireCell based on the given state and parameters
     */
    @Override
    protected FireCell createNewCell (Integer initialStateNum,
                                      Map<String, Number> parameters) {
        FireState initialState = FireState.values()[initialStateNum];
        double probCatch = getProbCatch(parameters);
        return new FireCell(initialState, probCatch);
    }

    /**
     * @return the probability value to be used to determine whether or not to catch fire
     */
    private double getProbCatch (Map<String, Number> parameters) {
        if (parameters.containsKey(PROBCATCH_KEY)) {
            return parameters.get(PROBCATCH_KEY).doubleValue();
        }
        else {
            return DEFAULT_PROBCATCH;
        }
    }

    @Override
    protected void calculateNextState (FireCell cell, Position position) {
        cell.calculateNextState(getNeighbors(position));
    }

    @Override
    protected boolean isCellStable (FireCell cell, Position position) {
        return cell.isStable(getNeighbors(position));
    }

    /**
     * Creates a FireCell with a random initial state
     */
    @Override
    protected FireCell createNewCell (Map<String, Number> parametersMap) {
        int randomStateNum = new Random().nextInt(FireState.values().length);
        return createNewCell(randomStateNum, parametersMap);
    }

}
