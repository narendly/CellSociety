package simulation_managers;

import java.util.List;
import java.util.Map;
import java.util.Random;
import grid_displays.Position;
import simulation_cells.SugarscapeCellBasic;
import states.SugarscapeState;


public class SugarscapeBasicManager extends AbstractCellManager<SugarscapeCellBasic> {
    private static final String MAX_SUGAR_KEY = "maxSugar";
    private static final int DEFAULT_MAX_SUGAR = 4;
    private static final String SUGAR_GROW_BACK_KEY = "sugarGrowBack";
    private static final int DEFAULT_SUGAR_GROW_BACK = 1;

    @Override
    protected SugarscapeCellBasic createNewCell (Integer initialStateNum,
                                                 Map<String, Number> parameters) {
        SugarscapeState initialState = SugarscapeState.values()[initialStateNum];
        int maxSugar = getMaxSugar(parameters);
        int sugarGrowBack = getSugarGrowBack(parameters);
        return new SugarscapeCellBasic(initialState, maxSugar, sugarGrowBack);
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
    protected void calculateNextState (SugarscapeCellBasic cell, Position position) {
        int vision = cell.getAgentInfo().getVision();
        List<SugarscapeCellBasic> neighbors = getOrderedNeighbors(position, vision);
        cell.calculateNextState(neighbors);
    }

    @Override
    protected boolean isCellStable (SugarscapeCellBasic cell, Position position) {
        int vision = cell.getAgentInfo().getVision();
        List<SugarscapeCellBasic> neighbors = getOrderedNeighbors(position, vision);
        return cell.isStable(neighbors);
    }

    @Override
    protected SugarscapeCellBasic createNewCell (Map<String, Number> parametersMap) {
        int randomStateNum = new Random().nextInt(SugarscapeState.values().length);
        return createNewCell(randomStateNum, parametersMap);
    }

}
