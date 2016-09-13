package cellsociety_team08;

import javafx.application.Application;
import javafx.stage.Stage;


/**
 * Boilerplate code that creates an instance of Simulation
 * and starts a simulation
 *
 * @author Team 8: Sophie Guo, David Maydew, Hunter Lee
 */
public class Main extends Application {
    public static final int XSIZE = 500;
    public static final int YSIZE = 500;

    private Simulation mySimulation;

    public static void main (String[] args) {
        Application.launch(args);
    }

    @Override
    public void start (Stage simulationStage) throws Exception {
        mySimulation = new Simulation(simulationStage, "default");
        mySimulation.initialize();
    }
}
