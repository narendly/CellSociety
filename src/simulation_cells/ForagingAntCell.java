package simulation_cells;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import javafx.scene.paint.Color;
import states.ForagingAntState;


public class ForagingAntCell extends Cell {

    private static final int COLOR_MIN = 50;
    private static final int RGB_MAX = 255;
    private static final double N_POWER = 10.0;
    private static final double K_FACTOR = 0.001;
    private static final double GRAD_FACTOR = 2.0;
    private static final double SOURCE_PHEROMONE_VALUE = 10000.0;
    private static final int ANT_POPULATION = 50;

    private int myAntLifetime;
    private double myEvapRatio;
    private int myMaxAntsinCell;
    private Collection<AntInfo> myAnts;
    private boolean isFood;
    private boolean isNest;
    private PheromoneInfo myPheromone;

    public ForagingAntCell (ForagingAntState state,
                            int antlifetime,
                            double evapRatio,
                            int maxAntsinCell) {
        super(state);
        myAntLifetime = antlifetime;
        myEvapRatio = evapRatio;
        myAnts = new HashSet<AntInfo>();
        isFood = state == ForagingAntState.FOODSOURCE;
        isNest = state == ForagingAntState.NEST;
        this.myMaxAntsinCell = maxAntsinCell;
        myPheromone = new PheromoneInfo();

        if (state == ForagingAntState.NEST) {
            for (int i = 0; i < ANT_POPULATION; i++) {
                myAnts.add(new AntInfo());
            }
        }
    }

    public int getNumberOfAnts () {
        return myAnts.size();
    }

    
    /**
     * Implements calculateNextState in the superclass
     * 
     * @param neighbors
     */
    public void calculateNextState (Set<ForagingAntCell> neighbors) {
        resetPheromones();
        getMyPheromone().evaporate();
        moveAnts(neighbors);
        if (isFood) {
            setNextState(ForagingAntState.FOODSOURCE);
            return;
        }
        if (isNest) {
            setNextState(ForagingAntState.NEST);
            getMyAnts().add(new AntInfo());
            return;
        }
        if (getNumberOfAnts() == 0) {
            setNextState(ForagingAntState.PHEROMONE);
        }
        else {
            setNextState(ForagingAntState.ANT);
        }
    }

    /**
     * Packages all the operations needed to move ants
     * 
     * @param neighbors
     */
    public void moveAnts (Set<ForagingAntCell> neighbors) {
        for (Iterator<AntInfo> iterator = getMyAnts().iterator(); iterator.hasNext();) {
            AntInfo ant = iterator.next();
            ant.incrementAge();
            if (ant.getAge() > getAntLifetime()) {
                iterator.remove();
                continue;
            }
            ForagingAntCell nextCell = ant.selectLocation(ant, ant.sortNeighbors(neighbors));
            if (nextCell != null) {
                nextCell.getMyAnts().add(ant);
                dropPheromones(ant, nextCell);
                iterator.remove();
                if (nextCell.isFood) {
                    ant.setHasFood(true);
                }
                if (nextCell.isNest) {
                    ant.setHasFood(false);
                }
            }
        }
    }

    /**
     * Adjust Pheromone levels when ants move into a new place
     * 
     * @param ant
     * @param nextCell
     */
    public void dropPheromones (AntInfo ant, ForagingAntCell nextCell) {
        double dropValue;
        if (ant.getHasFood()) {
            dropValue = ant.getMaxPheromone() - GRAD_FACTOR - nextCell.getFoodPheromone();
            if (dropValue > 0) {
                nextCell.setFoodPheromone(dropValue);
            }
        }
        else {
            dropValue = ant.getMaxPheromone() - GRAD_FACTOR - nextCell.getNestPheromone();
            if (dropValue > 0) {
                nextCell.setNestPheromone(dropValue);
            }
        }
    }

    /**
     * When ants reach one of the sources, resets pheromone value
     * 
     */
    public void resetPheromones () {
        if (getState() == ForagingAntState.FOODSOURCE) {
            setFoodPheromone(SOURCE_PHEROMONE_VALUE);
        }
        if (getState() == ForagingAntState.NEST) {
            setNestPheromone(SOURCE_PHEROMONE_VALUE);
        }
    }

    public Collection<AntInfo> getMyAnts () {
        return myAnts;
    }

    private int getAntLifetime () {
        return myAntLifetime;
    }

    public int getMaxAntsinCell () {
        return myMaxAntsinCell;
    }

    public double getFoodPheromone () {
        return myPheromone.getFoodPheromone();
    }

    public double getNestPheromone () {
        return myPheromone.getNestPheromone();
    }

    public void setFoodPheromone (double foodPheromone) {
        myPheromone.setFoodPheromone(foodPheromone);
    }

    public void setNestPheromone (double nestPheromone) {
        myPheromone.setNestPheromone(nestPheromone);
    }

    public PheromoneInfo getMyPheromone () {
        return myPheromone;
    }

    /**
     * Public method called by GridDisplay to give information about density
     * of colors
     * 
     */
    @Override
    public Color getColor () {
        if (getState() == ForagingAntState.FOODSOURCE || getState() == ForagingAntState.NEST ||
            getState() == ForagingAntState.PHEROMONE) {
            return getState().getColor();
        }
        else {
            double ratio = getNumberOfAnts() * 1.0 / getMaxAntsinCell();
            ratio = ratio > 1.00 ? 1 : ratio;
            Math.max(COLOR_MIN * 1.0 / RGB_MAX, 1 - ratio);
            return new Color(1.0, 0, 0, ratio);
        }
    }
}
