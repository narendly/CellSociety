package states;

import javafx.scene.paint.Color;


public enum SugarscapeState implements IState {
                                               PATCH(Color.WHITE),
                                               AGENT(Color.RED);
    private final Color myPaint;

    SugarscapeState (Color paint) {
        myPaint = paint;
    }

    @Override
    public Color getColor () {
        return myPaint;
    }
}
