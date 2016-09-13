package states;

import javafx.scene.paint.Color;


/**
 * Represents one of the various states that any cell could take on, and specifies that a color be
 * associated with each state
 *
 * @author David Maydew
 *
 */
public interface IState {

    Color getColor ();
}
