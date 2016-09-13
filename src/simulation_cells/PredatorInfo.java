package simulation_cells;



/**
 * Member of Predator-Prey (Wa-Tor) Simulation
 *
 */
public class PredatorInfo {

    private int myPredatorBreedTime;
    private int myPredatorTurnsToBreed;
    private int myTurnsSinceLastAte;
    private int myStarveTime;

    public PredatorInfo (int predatorBreedTime, int starveTime) {
        myPredatorBreedTime = predatorBreedTime;
        myPredatorTurnsToBreed = predatorBreedTime;
        myStarveTime = starveTime;
        myTurnsSinceLastAte = 0;
    }

    public void copyInfo (PredatorInfo myInfo) {
        myPredatorTurnsToBreed = myInfo.getPredatorTurnsToBreed();
        myTurnsSinceLastAte = myInfo.getTurnsSinceLastAte();

    }

    public void decrementBreedTurns () {
        myPredatorTurnsToBreed--;
    }

    public void incrementStarveTurns () {
        myTurnsSinceLastAte++;
    }

    public void decreaseBreedAndIncreaseStarve () {
        myPredatorTurnsToBreed--;
        myTurnsSinceLastAte++;
    }

    public int getTurnsSinceLastAte () {
        return myTurnsSinceLastAte;
    }

    public int getPredatorTurnsToBreed () {
        return myPredatorTurnsToBreed;
    }

    public int getPredatorBreedTime () {
        return myPredatorBreedTime;
    }

    public boolean isStarving () {
        return getTurnsSinceLastAte() == getStarveTime();
    }

    public void reset () {
        myPredatorTurnsToBreed = myPredatorBreedTime;
        myTurnsSinceLastAte = 0;
    }

    public void resetTurnsSinceLastAte () {
        myTurnsSinceLastAte = 0;
    }

    public void resetTurnsToBreed () {
        myPredatorTurnsToBreed = myPredatorBreedTime;
    }

    public int getStarveTime () {
        return myStarveTime;
    }

    public void resetBreedAndIncreaseStarve () {
        resetTurnsToBreed();
        incrementStarveTurns();
    }
}
