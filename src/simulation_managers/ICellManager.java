package simulation_managers;

import java.util.List;
import cellsociety_team08.Configuration;
import cellsociety_team08.SimulationException;
import javafx.scene.paint.Color;
import states.IState;


/**
 * Interface to allow for interaction with the back-end model of a Cellular Automata simulation
 *
 * @author David Maydew
 *
 */
public interface ICellManager {

    /**
     * Initialize a back end model for a CA simulation
     *
     * @param config
     * @throws SimulationException if the configuration file contains invalid data for this
     *         simulation
     */
    void initialize (Configuration config) throws SimulationException;

    /**
     * Updates all cells in the CA simulation to their next state
     */
    void updateAllCells ();

    /**
     * Calculates the next state of every cell in preparation for the next update
     */
    void calculateAllNextStates ();

    /**
     * @return whether all of the cells in the model are currently satisfied (won't change state
     *         next turn)
     */
    boolean areAllCellsStable ();

    /**
     * @return a high-level picture of the state representation for every cell in the model
     */
    List<List<IState>> getAllStates ();

    /**
     * @return a specific color representation of every cell in the model
     */
    List<List<Color>> getAllCellColors ();

    /**
     * @return number of rows in the model
     */
    int getMaxX ();

    /**
     * @return number of columns in the model
     */
    int getMaxY ();
}
