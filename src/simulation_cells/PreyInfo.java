package simulation_cells;


/**
 * Member of Predator Prey (Wa-Tor) Simulation
 *
 */
public class PreyInfo {
    private int myPreyTurnsToBreed;
    private int myPreyBreedTime;

    public PreyInfo (int preyBreedTime) {
        myPreyBreedTime = myPreyTurnsToBreed;
        myPreyBreedTime = preyBreedTime;
    }

    public void setPreyTurnsToBreed (int value) {
        myPreyTurnsToBreed = value;
    }

    public void decrementBreedTurns () {
        myPreyTurnsToBreed--;
    }

    public void copyInfo (PreyInfo myInfo) {
        myPreyTurnsToBreed = myInfo.getPreyTurnsToBreed();
    }

    public void reset () {
        myPreyTurnsToBreed = myPreyBreedTime;
    }

    public int getPreyTurnsToBreed () {
        return myPreyTurnsToBreed;
    }

    public int getPreyBreedTime () {
        return myPreyBreedTime;
    }
}
