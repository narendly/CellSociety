package simulation_cells;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import javafx.scene.paint.Color;
import states.IState;


public abstract class Cell {

    private IState myState;
    private IState myNextState;

    public Cell (IState state) {
        myState = state;
    }

    public void update () {
        setState(getNextState());
        resetNextState();
    }

    public IState getState () {
        return myState;
    }

    protected void setState (IState state) {
        myState = state;
    }

    protected IState getNextState () {
        return myNextState;
    }

    protected void setNextState (IState nextState) {
        myNextState = nextState;
    }

    protected void resetNextState () {
        myNextState = null;
    }

    protected boolean nextAlreadyCalculated () {
        return getNextState() != null;
    }

    public Color getColor () {
        return myState.getColor();
    }

    /**
     * Randomly pick an element from a collection of objects
     *
     * @param <E>
     */
    public <E> E getRandomElement (Collection<E> elements) {
        int randomIndex = new Random().nextInt(elements.size());
        List<E> cellList = new ArrayList<E>(elements);
        return cellList.get(randomIndex);
    }
}
