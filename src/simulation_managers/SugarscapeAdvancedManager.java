package simulation_managers;

import java.util.List;
import java.util.Map;
import java.util.Random;
import grid_displays.Position;
import simulation_cells.SugarscapeCellAdvanced;
import states.SugarscapeState;


public class SugarscapeAdvancedManager extends AbstractCellManager<SugarscapeCellAdvanced> {
    private static final String MAX_SUGAR_KEY = "maxSugar";
    private static final int DEFAULT_MAX_SUGAR = 4;
    private static final String SUGAR_GROW_BACK_KEY = "sugarGrowBack";
    private static final int DEFAULT_SUGAR_GROW_BACK = 1;
    private static final String FERTILE_LOWER_KEY = "fertileLower";
    private static final int DEFAULT_FERTILE_LOWER = 20;
    private static final String FERTILE_UPPER_KEY = "fertileUpper";
    private static final int DEFAULT_FERTILE_UPPER = 60;

    @Override
    protected SugarscapeCellAdvanced createNewCell (Integer initialStateNum,
                                                    Map<String, Number> parameters) {
        SugarscapeState initialState = SugarscapeState.values()[initialStateNum];
        int maxSugar = getMaxSugar(parameters);
        int sugarGrowBack = getSugarGrowBack(parameters);
        int fertileLower = getFertileLower(parameters);
        int fertileUpper = getFertileUpper(parameters);
        return new SugarscapeCellAdvanced(initialState, maxSugar, sugarGrowBack, fertileLower,
                                          fertileUpper);
    }

    private int getFertileLower (Map<String, Number> parameters) {
        if (parameters.containsKey(FERTILE_LOWER_KEY)) {
            return parameters.get(FERTILE_LOWER_KEY).intValue();
        }
        else {
            return DEFAULT_FERTILE_LOWER;
        }
    }

    private int getFertileUpper (Map<String, Number> parameters) {
        if (parameters.containsKey(FERTILE_UPPER_KEY)) {
            return parameters.get(FERTILE_UPPER_KEY).intValue();
        }
        else {
            return DEFAULT_FERTILE_UPPER;
        }
    }

    private int getSugarGrowBack (Map<String, Number> parameters) {
        if (parameters.containsKey(SUGAR_GROW_BACK_KEY)) {
            return parameters.get(SUGAR_GROW_BACK_KEY).intValue();
        }
        else {
            return DEFAULT_SUGAR_GROW_BACK;
        }
    }

    private int getMaxSugar (Map<String, Number> parameters) {
        if (parameters.containsKey(MAX_SUGAR_KEY)) {
            return parameters.get(MAX_SUGAR_KEY).intValue();
        }
        else {
            return DEFAULT_MAX_SUGAR;
        }
    }

    @Override
    protected void calculateNextState (SugarscapeCellAdvanced cell, Position position) {
        int vision = cell.getAdvancedAgentInfo().getVision();
        List<SugarscapeCellAdvanced> neighbors = getOrderedNeighbors(position, vision);
        cell.calculateNextState(neighbors);
    }

    @Override
    protected boolean isCellStable (SugarscapeCellAdvanced cell, Position position) {
        int vision = cell.getAdvancedAgentInfo().getVision();
        List<SugarscapeCellAdvanced> neighbors = getOrderedNeighbors(position, vision);
        return cell.isStable(neighbors);
    }

    @Override
    protected SugarscapeCellAdvanced createNewCell (Map<String, Number> parametersMap) {
        int randomStateNum = new Random().nextInt(SugarscapeState.values().length);
        return createNewCell(randomStateNum, parametersMap);
    }

}
