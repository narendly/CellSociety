package simulation_managers;

import java.util.Map;
import java.util.Random;
import grid_displays.Position;
import simulation_cells.ForagingAntCell;
import states.ForagingAntState;


public class ForagingAntsManager extends AbstractCellManager<ForagingAntCell> {
    private static final String ANT_LIFETIME_KEY = "antLifetime";
    private static final int DEFAULT_ANT_LIFETIME = 500;
    private static final String EVAP_RATIO_KEY = "evapRatio";
    private static final double DEFAULT_EVAP_RATIO = 0.01;
    private static final String MAX_ANTS_KEY = "maxAnts";
    private static final int DEFAULT_MAX_ANTS = 10;

    @Override
    protected ForagingAntCell createNewCell (Integer initialStateNum,
                                             Map<String, Number> parameters) {
        ForagingAntState initialState = ForagingAntState.values()[initialStateNum];
        int antLifetime = getAntLifetime(parameters);
        double evapRatio = getEvapRatio(parameters);
        int maxAntsInCell = getMaxAntsInCell(parameters);
        return new ForagingAntCell(initialState, antLifetime, evapRatio, maxAntsInCell);
    }

    private int getMaxAntsInCell (Map<String, Number> parameters) {
        if (parameters.containsKey(MAX_ANTS_KEY)) {
            return parameters.get(MAX_ANTS_KEY).intValue();
        }
        else {
            return DEFAULT_MAX_ANTS;
        }
    }

    private double getEvapRatio (Map<String, Number> parameters) {
        if (parameters.containsKey(EVAP_RATIO_KEY)) {
            return parameters.get(EVAP_RATIO_KEY).doubleValue();
        }
        else {
            return DEFAULT_EVAP_RATIO;
        }
    }

    private int getAntLifetime (Map<String, Number> parameters) {
        if (parameters.containsKey(ANT_LIFETIME_KEY)) {
            return parameters.get(ANT_LIFETIME_KEY).intValue();
        }
        else {
            return DEFAULT_ANT_LIFETIME;
        }
    }

    @Override
    protected ForagingAntCell createNewCell (Map<String, Number> parametersMap) {
        int randomStateNum = new Random().nextInt(ForagingAntState.values().length);
        return createNewCell(randomStateNum, parametersMap);
    }

    @Override
    protected void calculateNextState (ForagingAntCell cell, Position position) {
        cell.calculateNextState(getNeighbors(position));
    }

    @Override
    protected boolean isCellStable (ForagingAntCell cell, Position position) {
        return cell.getNumberOfAnts() > 0;
    }

}
