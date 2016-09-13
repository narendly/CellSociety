package simulation_cells;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;


/**
 * Member of Foraging Ants Simulation
 *	
 * @author Hunter Lee
 */
public class AntInfo {
    public static final int MAX_PHEROMONE = 1000;
    public static final double PROBABILITY = 0.5;
    public static final double DECREAST_FACTOR = 2;

    private boolean hasFood;
    private int myAge;
    private double myPheromone;
    private double myMaxPheromone;
    private String myOrientation;

    public AntInfo () {
        myAge = 0;
        myPheromone = MAX_PHEROMONE;
        myOrientation = "N";
        hasFood = false;
    }

    public void incrementAge () {
        myAge++;
    }

    public int getAge () {
        return myAge;
    }

    /**
     * Public method to be used by AntCell that sorts Neighbors
     * 
     * @param neighbors
     * @return
     */
    public ArrayList<ForagingAntCell> sortNeighbors (Set<ForagingAntCell> neighbors) {
        ArrayList<ForagingAntCell> sortedNeighbors = new ArrayList<ForagingAntCell>(neighbors);
        if (getHasFood()) {
            Collections.sort(sortedNeighbors,
                             (ForagingAntCell o1,
                              ForagingAntCell o2) -> (int)(o2.getNestPheromone() -
                                                            o1.getNestPheromone()));
        }
        else {
            Collections.sort(sortedNeighbors,
                             (ForagingAntCell o1,
                              ForagingAntCell o2) -> (int)(o2.getFoodPheromone() -
                                                            o1.getFoodPheromone()));
        }
        return sortedNeighbors;
    }

    /**
     * Public method to be used by AntCell to select next location for each ant
     * 
     * @param ant
     * @param sortedNeighbors
     * @return
     */
    public ForagingAntCell selectLocation (AntInfo ant,
                                           ArrayList<ForagingAntCell> sortedNeighbors) {
        if (getHasFood()) {
            setMaxPheromone(sortedNeighbors.get(0).getFoodPheromone());
        }
        else {
            setMaxPheromone(sortedNeighbors.get(0).getNestPheromone());
        }
        double probability = PROBABILITY;
        Iterator<ForagingAntCell> iterator = sortedNeighbors.iterator();
        while (iterator.hasNext()) {
            ForagingAntCell nextCell = iterator.next();
            if (nextCell.getNumberOfAnts() > nextCell.getMaxAntsinCell()) {
                continue;
            }
            if (Math.random() <= probability) {
                return nextCell;
            }
            else {
                probability = probability / DECREAST_FACTOR;
            }
        }
        return null;
    }

    public boolean getHasFood () {
        return hasFood;
    }

    public void setHasFood (boolean hasFood) {
        this.hasFood = hasFood;
        topUpPheromone();
    }

    public double getPheromone () {
        return myPheromone;
    }

    public void topUpPheromone () {
        myPheromone = MAX_PHEROMONE;
    }

    public void setMaxPheromone (double maxpheromone) {
        myMaxPheromone = maxpheromone;
    }

    public double getMaxPheromone () {
        return myMaxPheromone;
    }
}
