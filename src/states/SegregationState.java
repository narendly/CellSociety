package states;

import javafx.scene.paint.Color;


public enum SegregationState implements IState {
                                                EMPTY(Color.WHITE),
                                                X(Color.BLUE),
                                                O(Color.RED);

    private final Color myPaint;

    SegregationState (Color paint) {
        myPaint = paint;
    }

    @Override
    public Color getColor () {
        return myPaint;
    }
}
