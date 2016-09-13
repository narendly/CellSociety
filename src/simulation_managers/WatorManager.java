package simulation_managers;

import java.util.Map;
import java.util.Random;
import java.util.Set;
import grid_displays.Position;
import simulation_cells.WatorCell;
import states.WatorState;


/**
 * Manager of the back-end model for a WaTor / PredatorPrey simulation
 *
 * @author David Maydew
 *
 */
public class WatorManager extends AbstractCellManager<WatorCell> {
    private static final String PREYBREED_KEY = "preyBreedTime";
    private static final String PREDATORBREED_KEY = "predatorBreedTime";
    private static final String STARVE_KEY = "starveTime";
    private static final int DEFAULT_PREYBREED = 4;
    private static final int DEFAULT_PREDATORBREED = 5;
    private static final int DEFAULT_STARVE = 4;

    @Override
    protected WatorCell createNewCell (Integer initialStateNum, Map<String, Number> parameters) {
        WatorState initialState = WatorState.values()[initialStateNum];
        int preyBreedTime = getParameter(PREYBREED_KEY, parameters, DEFAULT_PREYBREED);
        int predatorBreedTime = getParameter(PREDATORBREED_KEY, parameters, DEFAULT_PREDATORBREED);
        int starveTime = getParameter(STARVE_KEY, parameters, DEFAULT_STARVE);
        return new WatorCell(initialState, preyBreedTime, predatorBreedTime, starveTime);
    }

    private int getParameter (String key, Map<String, Number> parameters, int defaultValue) {
        if (parameters.containsKey(key)) {
            return parameters.get(key).intValue();
        }
        else {
            return defaultValue;
        }
    }

    @Override
    protected void calculateNextState (WatorCell cell, Position position) {
        Set<WatorCell> neighbors = getNeighbors(position);
        cell.calculateNextState(neighbors);
    }

    @Override
    protected boolean isCellStable (WatorCell cell, Position position) {
        Set<WatorCell> neighbors = getNeighbors(position);
        return cell.isStable(neighbors);
    }

    @Override
    protected WatorCell createNewCell (Map<String, Number> parametersMap) {
        int randomStateNum = new Random().nextInt(WatorState.values().length);
        return createNewCell(randomStateNum, parametersMap);
    }
}
