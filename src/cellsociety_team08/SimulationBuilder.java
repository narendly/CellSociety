package cellsociety_team08;

import java.util.ResourceBundle;


/**
 * Abstract class to create a Builder hierarchy
 *
 */
public abstract class SimulationBuilder {
    protected static final String DEFAULT_RESOURCE_PACKAGE = "resources/";

    private Simulation mySimulation;
    private ResourceBundle myResources;

    public SimulationBuilder (Simulation simulation, String language) {
        mySimulation = simulation;
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
    }

    protected Simulation getSimulation () {
        return mySimulation;
    }

    protected ResourceBundle getResources () {
        return myResources;
    }
}
