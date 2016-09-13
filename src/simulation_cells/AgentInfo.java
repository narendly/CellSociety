package simulation_cells;


/**
 * Member of Sugarscape Advanced Simulation
 *
 */
public abstract class AgentInfo {
    private int mySugar;
    private int mySugarMetabolism;
    private int myVision;

    public void initializeSugar (int lowerBound, int upperBound) {
        setSugar(generateRandomInteger(lowerBound, upperBound));
    }

    public void initializeSugarMetabolism (int lowerBound, int upperBound) {
        setSugarMetabolism(generateRandomInteger(lowerBound, upperBound));
    }

    public void initializeVision (int lowerBound, int upperBound) {
        setVision(generateRandomInteger(lowerBound, upperBound));
    }

    public int getSugar () {
        return mySugar;
    }

    public void setSugar (int sugar) {
        mySugar = sugar;
    }

    public void addSugar (int sugar) {
        setSugar(getSugar() + sugar);
    }

    public int getVision () {
        return myVision;
    }

    public void setVision (int vision) {
        myVision = vision;
    }

    public int getSugarMetabolism () {
        return mySugarMetabolism;
    }

    public void setSugarMetabolism (int metabolism) {
        mySugarMetabolism = metabolism;
    }

    public boolean isDead () {
        return getSugar() < 0;
    }

    public int generateRandomInteger (int lowerBound, int upperBound) {
        return (int)(Math.random() * (upperBound - lowerBound) + lowerBound);
    }

    public void subtractSugarMetabolism () {
        setSugar(getSugar() - getSugarMetabolism());
    }
}
