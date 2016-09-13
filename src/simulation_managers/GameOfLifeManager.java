package simulation_managers;

import java.util.Map;
import java.util.Random;
import grid_displays.Position;
import simulation_cells.GameOfLifeCell;
import states.GameOfLifeState;


/**
 * Manager for the back-end model of a Game of Life simulation
 *
 * @author David Maydew
 *
 */
public class GameOfLifeManager extends AbstractCellManager<GameOfLifeCell> {

    @Override
    protected GameOfLifeCell createNewCell (Integer initialStateNum,
                                            Map<String, Number> parameters) {
        GameOfLifeState initialState = GameOfLifeState.values()[initialStateNum];
        return new GameOfLifeCell(initialState);
    }

    @Override
    protected void calculateNextState (GameOfLifeCell cell, Position position) {
        cell.calculateNextState(getNeighbors(position));
    }

    @Override
    protected boolean isCellStable (GameOfLifeCell cell, Position position) {
        return cell.isStable(getNeighbors(position));
    }

    @Override
    protected GameOfLifeCell createNewCell (Map<String, Number> parametersMap) {
        int randomStateNum = new Random().nextInt(GameOfLifeState.values().length);
        return createNewCell(randomStateNum, parametersMap);
    }

}
