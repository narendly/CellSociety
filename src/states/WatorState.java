package states;

import javafx.scene.paint.Color;


public enum WatorState implements IState {
                                          EMPTY(Color.BLUE),
                                          PREDATOR(Color.ORANGE),
                                          PREY(Color.GREEN);
    private final Color myPaint;

    WatorState (Color paint) {
        myPaint = paint;
    }

    @Override
    public Color getColor () {
        return myPaint;
    }

}
