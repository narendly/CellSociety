package simulation_cells;

import java.util.Collection;
import java.util.Set;
import states.GameOfLifeState;


public class GameOfLifeCell extends Cell {
    private static final int REPRODUCTION_NUMBER = 3;

    public GameOfLifeCell (GameOfLifeState initialState) {
        super(initialState);
    }

    /**
     * Count the number of live neighbors
     *
     * @param cells:collection
     *        of neighboring cells
     */
    public int countLiveNeighbors (Collection<GameOfLifeCell> cells) {
        int liveNeighbors = 0;
        for (GameOfLifeCell cell : cells) {
            if (cell.getState() == GameOfLifeState.ALIVE) {
                liveNeighbors++;
            }
        }
        return liveNeighbors;
    }

    public void calculateNextState (Set<GameOfLifeCell> neighbors) {
        if (nextAlreadyCalculated()) {
            return;
        }
        setNextState(getState());
        int liveNeighbors = countLiveNeighbors(neighbors);
        if (getState() == GameOfLifeState.DEAD && isRevived(liveNeighbors)) {
            setNextState(GameOfLifeState.ALIVE);
        }
        else if (getState() == GameOfLifeState.ALIVE && isOverOrUnderPopulated(liveNeighbors)) {
            setNextState(GameOfLifeState.DEAD);
        }
    }

    private boolean isRevived (int liveNeighbors) {
        return liveNeighbors == REPRODUCTION_NUMBER;
    }

    private boolean isOverOrUnderPopulated (int liveNeighbors) {
        return liveNeighbors < REPRODUCTION_NUMBER - 1 || liveNeighbors > REPRODUCTION_NUMBER;
    }

    public boolean isStable (Set<GameOfLifeCell> neighbors) {
        calculateNextState(neighbors);
        return getState() == getNextState();
    }
}
