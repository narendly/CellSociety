package states;

import javafx.scene.paint.Color;


public enum ForagingAntState implements IState {
                                                ANT(Color.RED),
                                                PHEROMONE(Color.WHITE),
                                                FOODSOURCE(Color.GREEN),
                                                NEST(Color.YELLOW);

    private final Color myPaint;

    ForagingAntState (Color paint) {
        myPaint = paint;
    }

    @Override
    public Color getColor () {
        return myPaint;
    }

}
