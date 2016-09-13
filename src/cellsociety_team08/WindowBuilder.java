package cellsociety_team08;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


/**
 * Subclass of SimulationBuilder
 * Creates a GUI screen that interacts with the user
 * 
 * @author Hunter Lee
 *
 */
public class WindowBuilder extends SimulationBuilder {
    private static final int BUTTON_WIDTH = 100;
    private static final int BUTTON_HEIGHT = 20;
    private static final int BOX_PADDING = 20;
    private static final int NODE_SPACING = 10;
    private static final int FONT_SIZE = 14;
    private static final String BOX_STYLE = "-fx-background-color: #336699;";
    private static final String H_BUTTON_STYLE = "-fx-font: 16 georgia; -fx-base: #ccd9ff;";
    private static final String V_BUTTON_STYLE = "-fx-font: 14 georgia; -fx-base: #ccd9ff;";

    public WindowBuilder (Simulation simulation, String language) {
        super(simulation, language);
    }

    /**
     * Handles creation of a BorderPane
     * 
     * @return BorderPane
     */
    public BorderPane createBorderPane () {
        BorderPane myBorderPane = new BorderPane();
        HBox hbox = addHBox();
        VBox vbox = addVBox();
        myBorderPane.setBottom(hbox);
        myBorderPane.setRight(vbox);
        return myBorderPane;
    }

    /**
     * Private method that handles button creation
     * 
     * @param name
     * @param width
     * @param height
     * @param style
     * @param e
     * @return
     */
    private Button createButton (String name,
                                 int width,
                                 int height,
                                 String style,
                                 EventHandler<ActionEvent> e) {
        Button myButton = new Button(name);
        myButton.setPrefSize(width, height);
        myButton.setStyle(style);
        myButton.setOnAction(e);
        return myButton;
    }
    
    /**
     * Private method that generates a portion of the main Simulation window
     * Also assigns actions to each button using lambda functions
     * 
     * @return HBox
     */
    private HBox addHBox () {
        HBox hbox = new HBox();
        hbox.setPadding(new Insets(BOX_PADDING));
        hbox.setSpacing(NODE_SPACING);
        hbox.setStyle(BOX_STYLE);

        // Creates buttons for control
        Button start = createButton(getResources().getString("StartButton"),
                                    BUTTON_WIDTH, BUTTON_HEIGHT, H_BUTTON_STYLE,
                                    e -> getSimulation().startSimulation());

        Button stop = createButton(getResources().getString("StopButton"),
                                   BUTTON_WIDTH, BUTTON_HEIGHT, H_BUTTON_STYLE,
                                   e -> getSimulation().stopSimulation());
        Button step = createButton(getResources().getString("StepButton"),
                                   BUTTON_WIDTH, BUTTON_HEIGHT, H_BUTTON_STYLE,
                                   e -> getSimulation().stepSimulation());
        Button reset = createButton(getResources().getString("ResetButton"),
                                    BUTTON_WIDTH, BUTTON_HEIGHT, H_BUTTON_STYLE,
                                    e -> getSimulation().resetSimulation());
        hbox.getChildren().addAll(start, stop, step, reset);
        return hbox;
    }
    
    
    /**
     * Private method that generates a portion of the main Simulation window
     * Also assigns actions to each button using lambda functions
     * 
     * @return VBox
     */
    private VBox addVBox () {
        VBox vbox = new VBox();
        vbox.setPadding(new Insets(BOX_PADDING));
        vbox.setSpacing(NODE_SPACING);
        vbox.setStyle(BOX_STYLE);
        vbox.setAlignment(Pos.CENTER);

        Text title = new Text("Simulation Speed");
        title.setFont(Font.font("Calibri", FontWeight.BOLD, FONT_SIZE));
        title.setFill(Color.WHEAT);
        title.setStyle("-fx-font: 18 georgia; -fx-base: #ccd9ff;");

        // Creates buttons for speed control
        Button speedUp = createButton(getResources().getString("SpeedUpButton"),
                                      BUTTON_WIDTH, BUTTON_HEIGHT, V_BUTTON_STYLE,
                                      e -> getSimulation().speedUp());
        Button speedDown = createButton(getResources().getString("SpeedDownButton"),
                                        BUTTON_WIDTH, BUTTON_HEIGHT, V_BUTTON_STYLE,
                                        e -> getSimulation().speedDown());
        vbox.getChildren().addAll(title, speedUp, speedDown);
        return vbox;
    }
}
