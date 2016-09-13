package simulation_cells;

import java.util.Set;
import states.FireState;


/**
 * Handles the model updates for a given Cell in the Fire Cellular Automata Simulation
 *
 * @author Sophie Guo, David Maydew
 */
public class FireCell extends Cell {

    private double myProbCatchFire;

    /**
     * Constructs a FireCell with the given state and probability of catching fire
     *
     * @param initialCellState to set as the current State
     * @param probCatchFire probability of this Cell catching fire from an adjacent burning cell
     */
    public FireCell (FireState initialCellState, double probCatchFire) {
        super(initialCellState);
        setCatchFireProb(probCatchFire);
    }

    /**
     * Calculates and sets the next state of this FireCell based on its neighbors
     *
     * @param neighbors up to 4 adjacent cells
     */
    public void calculateNextState (Set<FireCell> set) {
        if (getState() == FireState.EMPTY || getState() == FireState.BURNING) {
            setNextState(FireState.EMPTY);
        }
        else {
            setNextState(FireState.TREE);
            for (FireCell cell : set) {
                if (cell.getState() == FireState.BURNING &&
                    Math.random() < getCatchFireProb()) {
                    setNextState(FireState.BURNING);
                    break;
                }
            }
        }
    }

    private double getCatchFireProb () {
        return myProbCatchFire;
    }

    private void setCatchFireProb (double probability) {
        myProbCatchFire = probability;
    }

    /**
     * Determines whether or not this FireCell is satisfied by calculating its nextState and seeing
     * if it is different than the current state
     *
     * @param set up to 4 adjacent cells
     * @return whether or not this cell is satisfied
     */
    public boolean isStable (Set<FireCell> set) {
        calculateNextState(set);
        return getState() == getNextState();
    }
}
