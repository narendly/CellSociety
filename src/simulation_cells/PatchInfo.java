package simulation_cells;


/**
 * Member of Sugarscape Simulation
 *
 */
public class PatchInfo {
    private int mySugar;
    private int myMaxSugar;
    private int mySugarGrowBackRate;

    public PatchInfo (int maxSugar, int sugarGrowBackRate) {
        myMaxSugar = maxSugar;
        mySugarGrowBackRate = sugarGrowBackRate;
        initializeSugar();
    }

    public void initializeSugar () {
        setSugar(getMaxSugar());
    }

    public void reset () {
        mySugar = 0;
    }

    public int getSugar () {
        return mySugar;
    }

    public int getMaxSugar () {
        return myMaxSugar;
    }

    public void setSugar (int sugar) {
        mySugar = Math.min(sugar, myMaxSugar);
    }

    public void addSugar () {
        addSugar(mySugarGrowBackRate);
    }

    public void addSugar (int sugar) {
        setSugar(getSugar() + sugar);
    }
}
