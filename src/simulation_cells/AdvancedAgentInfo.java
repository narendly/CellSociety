package simulation_cells;


/**
 * Member of Sugarscape Advanced Simulation
 *
 */
public class AdvancedAgentInfo extends BasicAgentInfo {

    private static final int MAX_AGE_LOWER_BOUND = 60;
    private static final int MAX_AGE_UPPER_BOUND = 100;
    private static final int INITIAL_SUGAR_LOWER_BOUND = 40;
    private static final int INITIAL_SUGAR_UPPER_BOUND = 80;
    private static final double GENDER_RATIO = 0.5;
    public enum Gender {
                        F,
                        M
    };

    private Gender myGender;
    private int myAge;
    private int myMaxAge;
    private int myFertileLimitsLower;
    private int myFertileLimitsUpper;
    private int myInitialSugar;
    

    public AdvancedAgentInfo (int fertileLimitsLower, int fertileLimitsUpper) {
        super();
        resetAdvancedAgentInfo();
        initializeMaxAge();
        setFertileLimits(fertileLimitsLower, fertileLimitsUpper);
    }

    public void copyInfo (AdvancedAgentInfo info) {
        setAge(info.getAge());
        setSugarMetabolism(info.getSugarMetabolism());
        setVision(info.getVision());
        setGender(info.getGender());
        setMaxAge(info.getMaxAge());
        setInitialSugar(info.getInitialSugar());
    }

    public void initializeMaxAge () {
        myMaxAge = generateRandomInteger(MAX_AGE_LOWER_BOUND, MAX_AGE_UPPER_BOUND);
    }

    public void initializeGender () {
        myGender = (Math.random() <= GENDER_RATIO) ? Gender.F : Gender.M;
    }

    public void setGender (Gender g) {
        myGender = g;
    }

    public Gender getGender () {
        return myGender;
    }

    public int getInitialSugar () {
        return myInitialSugar;
    }

    public void setInitialSugar (int sugar) {
        myInitialSugar = sugar;
    }

    public int getMaxAge () {
        return myMaxAge;
    }

    public void setMaxAge (int age) {
        myMaxAge = age;
    }

    public boolean canReproduceWith (AdvancedAgentInfo info) {
        return isFertile() && info.isFertile() && getGender() != info.getGender();
    }

    public void setAge (int age) {
        this.myAge = age;
    }

    public int getAge () {
        return myAge;
    }

    public void setFertileLimits (int lower, int upper) {
        myFertileLimitsLower = lower;
        myFertileLimitsUpper = upper;
    }

    public void incrementAge () {
        myAge++;
    }

    public boolean isFertile () {
        return getAge() <= myFertileLimitsUpper && getAge() >= myFertileLimitsLower &&
               getSugar() >= myInitialSugar;
    }

    @Override
    public boolean isDead () {
        return getAge() > myMaxAge || getSugar() < 0;
    }

    public void reproduce () {
        setSugar(getInitialSugar() / 2);
    }

    public int reproductionRule (int mother, int father) {
        return (mother + father) / 2;
    }

    public void resetAdvancedAgentInfo () {
        setAge(0);
        initializeMaxAge();
        initializeGender();
        initializeSugar(INITIAL_SUGAR_LOWER_BOUND, INITIAL_SUGAR_UPPER_BOUND);
        myInitialSugar = getSugar();
    }

    public void reproducedFrom (AdvancedAgentInfo m, AdvancedAgentInfo f) {
        resetAdvancedAgentInfo();
        setVision(reproductionRule(m.getVision(), f.getVision()));
        setSugarMetabolism(reproductionRule(m.getSugarMetabolism(), f.getSugarMetabolism()));
        setFertileLimits(myFertileLimitsLower, myFertileLimitsUpper);
    }
}
