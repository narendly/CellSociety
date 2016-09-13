package simulation_cells;



/**
 * Member of Foraging Ants Simulation
 *
 * @author Hunter Lee
 */
public class PheromoneInfo {
    public static final double DEFAULT_EVAP_RATIO = 0.01;

    private double myFoodPheromone;
    private double myNestPheromone;
    private double myEvapRatio;

    public PheromoneInfo () {
        myFoodPheromone = 0;
        myNestPheromone = 0;
        myEvapRatio = DEFAULT_EVAP_RATIO;
    }

    private double calcMultiplier () {
        return 1 - getEvapRatio();
    }

    public void evaporate () {
        // Round down
        setFoodPheromone(getFoodPheromone() * calcMultiplier());
        setNestPheromone(getNestPheromone() * calcMultiplier());
    }

    public void setFoodPheromone (double foodPheromone) {
        myFoodPheromone = foodPheromone;
    }

    public void setNestPheromone (double nestPheromone) {
        myNestPheromone = nestPheromone;
    }

    public double getFoodPheromone () {
        return myFoodPheromone;
    }

    public double getNestPheromone () {
        return myNestPheromone;
    }

    public double getEvapRatio () {
        return myEvapRatio;
    }
}
