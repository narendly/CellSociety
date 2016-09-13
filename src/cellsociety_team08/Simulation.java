package cellsociety_team08;

import java.io.File;
import java.util.ResourceBundle;
import grid_displays.GridDisplay;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import javafx.util.Duration;
import simulation_managers.ICellManager;


/**
 * Center class that houses both front-end and back-end
 * that runs Animation
 *
 */
public class Simulation {
    public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
    private static final int FRAMES_PER_SECOND = 1;
    private static final int MILLISECOND_DELAY = 1000 / FRAMES_PER_SECOND;
    private static final double GRID_SIZE = 500;
    private static final int SPEEDCONTROL = 1;

    private ResourceBundle myStyleResources;
    private ResourceBundle myResources;
    private ResourceBundle myErrorResources;
    private File myXMLFile;
    private Stage mySimulationStage;
    private Scene myScene;
    private String myLanguage;
    private Timeline myAnimation;
    private ICellManager myCellManager;
    private GridDisplay myGridDisplay;
    private WindowBuilder myWindowBuilder;
    private ChartBuilder myChartBuilder;

    public Simulation (Stage simulationStage, String language) {
        mySimulationStage = simulationStage;
        myLanguage = language;
        myResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + language);
        myStyleResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "style");
        myErrorResources = ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "error");
    }

    /**
     * Initializes the simulation
     * 
     */
    public void initialize () {
        promptUser();
        try {
            Configuration config = parseXML();
            myGridDisplay = createGridDisplay();
            myCellManager = createCellManager(config);
            myCellManager.initialize(config);
            myGridDisplay.initialize(config, myCellManager);
            Region simulationGrid = myGridDisplay.getDisplay();
            myWindowBuilder = new WindowBuilder(this, getLanguage());
            myChartBuilder = new ChartBuilder(this, getLanguage(), getCellManager());
            buildSimulation(config.getSimulationType(), simulationGrid);
            myAnimation = new Timeline();
            KeyFrame frame = new KeyFrame(Duration.millis(MILLISECOND_DELAY),
                                          e -> update());
            myAnimation.setCycleCount(Animation.INDEFINITE);
            myAnimation.getKeyFrames().add(frame);
        }
        catch (SimulationException e) {
            showError(e.getMessage());
            initialize();
        }
    }

    /**
     * Dynamic instantiation of CellManager using Reflection
     * 
     * @param config
     * @return
     * @throws SimulationException
     */
    private ICellManager createCellManager (Configuration config) throws SimulationException {
        Class<?> cellManagerClass;
        ICellManager cellManager = null;
        try {
            cellManagerClass = Class.forName("simulation_managers." +
                                             config.getSimulationType() + "Manager");
            cellManager = (ICellManager)cellManagerClass.newInstance();
        }
        catch (ReflectiveOperationException e) {
            throw new SimulationException(myErrorResources.getString("InvalidSimulation"), e);
        }
        return cellManager;
    }
    
    /**
     * Dynamic instantiation of GridDisplay using Reflection
     * 
     * @return
     * @throws SimulationException
     */
    private GridDisplay createGridDisplay () throws SimulationException {
        String cellShape = myStyleResources.getString("CellShape");
        Class<?> gridClass = null;
        GridDisplay gridDisplay = null;
        try {
            gridClass = Class.forName("grid_displays." + cellShape + "GridDisplay");
            gridDisplay = (GridDisplay)gridClass.getConstructor(double.class, double.class)
                    .newInstance(GRID_SIZE, GRID_SIZE);
        }
        catch (ReflectiveOperationException e) {
            throw new SimulationException(myErrorResources.getString("InvalidCellShape"), e);
        }
        return gridDisplay;
    }

    /**
     * Parses the XML file
     * 
     * @return
     * @throws SimulationException
     */
    private Configuration parseXML () throws SimulationException {
        XMLProcessor xmlProcessor = new XMLProcessor();
        Configuration config = null;
        config = xmlProcessor.parse(myXMLFile);
        return config;
    }

    /**
     * This method instantiates the border pane used for simulation
     * 
     * @param simulationType
     * @param simulationGrid
     */
    private void buildSimulation (String simulationType, Region simulationGrid) {
        BorderPane border = getWindowBuilder().createBorderPane();
        border.setCenter(simulationGrid);
        border.setLeft(getChartBuilder().extractChart());
        myScene = new Scene(border);
        getSimulationStage().setScene(myScene);
        getSimulationStage().setTitle(simulationType + " Simulator");
        getSimulationStage().show();
    }

    /**
     * Calls update mandated by Timeline
     * 
     */
    private void update () {
        checkEndOfSimulation();
        getCellManager().updateAllCells();
        getGridDisplay().updateGridDisplay();
        getChartBuilder().updateChart();
    }
    
    /**
     * Instantiates PromptBuilder
     * 
     */
    public void promptUser () {
        PromptBuilder prompt = new PromptBuilder(this, getLanguage());
        prompt.promptUser();
    }

    /**
     * A function that generates an error message
     * 
     * @param message
     */
    protected void showError (String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(myErrorResources.getString("ErrorTitle"));
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void startSimulation () {
        getTimeline().play();
    }

    public void stopSimulation () {
        getTimeline().stop();
    }

    public void stepSimulation () {
        getTimeline().stop();
        update();
    }

    public void resetSimulation () {
        getTimeline().stop();
        getSimulationStage().hide();
        initialize();
    }

    public void speedUp () {
        getTimeline().stop();
        getTimeline().setRate(getTimeline().getRate() + SPEEDCONTROL);
        getTimeline().play();
    }

    public void speedDown () {
        getTimeline().stop();
        double newRate = getTimeline().getRate() - SPEEDCONTROL;
        if (newRate > 0) {
            getTimeline().setRate(newRate);
        }
        getTimeline().play();
    }

    public void checkEndOfSimulation () {
        if (myCellManager.areAllCellsStable()) {
            getTimeline().stop();
            displayEndMessage();
        }
    }
    
    /**
     * Notifies user that the simulation has ended.
     *
     */
    public void displayEndMessage () {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(myResources.getString("EndDisplayTitle"));
        alert.setHeaderText(myResources.getString("EndDisplayHeader"));
        alert.setContentText(myResources.getString("EndDisplayContent"));
        alert.show();
    }

    public Stage getSimulationStage () {
        return mySimulationStage;
    }

    public Scene getScene () {
        return myScene;
    }

    public GridDisplay getGridDisplay () {
        return myGridDisplay;
    }

    public ICellManager getCellManager () {
        return myCellManager;
    }

    public ChartBuilder getChartBuilder () {
        return myChartBuilder;
    }

    public WindowBuilder getWindowBuilder () {
        return myWindowBuilder;
    }

    public Timeline getTimeline () {
        return myAnimation;
    }

    public String getLanguage () {
        return myLanguage;
    }

    public void setXMLFile (File file) {
        myXMLFile = file;
    }
}
