package states;

import javafx.scene.paint.Color;


public enum GameOfLifeState implements IState {
                                               DEAD(Color.WHITE),
                                               ALIVE(Color.BLACK);
    private final Color myPaint;

    GameOfLifeState (Color paint) {
        myPaint = paint;
    }

    @Override
    public Color getColor () {
        return myPaint;
    }
}
