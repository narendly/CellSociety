package cellsociety_team08;

import java.io.File;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * Subclass of SimulationBuilder that creates
 * a user prompt asking for the XML file to base
 * the simulation on.
 * 
 * @author Hunter Lee
 **/
public class PromptBuilder extends SimulationBuilder {
    private static final int XPROMPTSIZE = 500;
    private static final int YPROMPTSIZE = 300;
    private static final int PADDING = 30;
    private static final int LABEL_FONTSIZE = 36;
    private static final int TEXT_FONTSIZE = 20;
    private static final String FONT = "Georgia";

    
    public PromptBuilder (Simulation simulation, String language) {
        super(simulation, language);
    }

    /**
     * A method called by Simulation to create a prompt screen
     * 
     */
    public void promptUser () {
        Stage prompt = new Stage();

        Label label = new Label(getResources().getString("Select"));
        label.setTextFill(Color.DARKBLUE);
        label.setFont(Font.font(FONT, FontWeight.BOLD, LABEL_FONTSIZE));

        HBox labelHb = new HBox();
        labelHb.setAlignment(Pos.CENTER);
        labelHb.getChildren().add(label);

        Text myActionStatus = new Text();
        myActionStatus.setFont(Font.font(FONT, FontWeight.NORMAL, TEXT_FONTSIZE));
        myActionStatus.setFill(Color.FIREBRICK);

        Button btn1 = new Button(getResources().getString("FileChoose"));
        btn1.setOnAction(e -> showSingleFileChooser(prompt, myActionStatus));
        HBox buttonHb1 = new HBox(PADDING);
        buttonHb1.setAlignment(Pos.CENTER);
        buttonHb1.getChildren().addAll(btn1);

        VBox vbox = new VBox(PADDING);
        vbox.setPadding(new Insets(PADDING));
        vbox.getChildren().addAll(labelHb, buttonHb1, myActionStatus);

        Scene promptScene = new Scene(vbox, XPROMPTSIZE, YPROMPTSIZE);
        prompt.setScene(promptScene);
        prompt.showAndWait();
    }

    /**
     * Private filechooser method used for prompt screen
     * 
     * @param prompt
     * @param myActionStatus
     */
    private void showSingleFileChooser (Stage prompt, Text myActionStatus) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            myActionStatus.setText(getResources().getString("Selected") + selectedFile.getName());
            getSimulation().setXMLFile(selectedFile);
            prompt.hide();
        }
        else {
            myActionStatus.setText(getResources().getString("Cancelled"));
        }
    }
}
