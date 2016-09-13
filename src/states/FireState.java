package states;

import javafx.scene.paint.Color;


public enum FireState implements IState {
                                         EMPTY(Color.YELLOW),
                                         TREE(Color.GREEN),
                                         BURNING(Color.RED);
    private final Color myPaint;

    FireState (Color paint) {
        myPaint = paint;
    }

    @Override
    public Color getColor () {
        return myPaint;
    }
}
