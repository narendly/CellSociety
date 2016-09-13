package cellsociety_team08;

import javafx.scene.control.Label;
import javafx.scene.control.Slider;


/**
 * Subclass of SimulationBuilder
 * Unimplemented class to handle the display of sliders on the GUI
 * @author Hunter Lee
 *
 *
 */
public class SliderBuilder extends SimulationBuilder {

    public SliderBuilder (Simulation simulation, String language) {
        super(simulation, language);
    }


    public void createSliders () {

        Slider myProbLevel = new Slider(0, 1, 1);

        new Label("Probability: ");

        new Label(
                  Double.toString(myProbLevel.getValue()));

    }
}
