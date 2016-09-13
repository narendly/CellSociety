package simulation_cells;


/**
 * Member of Sugarscape Simulation
 *
 */
public class BasicAgentInfo extends AgentInfo {
    private static final int INITIAL_SUGAR_LOWER_BOUND = 5;
    private static final int INITIAL_SUGAR_UPPER_BOUND = 25;
    private static final int INITIAL_METABOLISM_LOWER_BOUND = 1;
    private static final int INITIAL_METABOLISM_UPPER_BOUND = 6;
    private static final int INITIAL_VISION_LOWER_BOUND = 1;
    private static final int INITIAL_VISION_UPPER_BOUND = 4;

    public BasicAgentInfo () {
        initializeSugar(INITIAL_SUGAR_LOWER_BOUND, INITIAL_SUGAR_UPPER_BOUND);
        initializeVision(INITIAL_METABOLISM_LOWER_BOUND, INITIAL_METABOLISM_UPPER_BOUND);
        initializeSugarMetabolism(INITIAL_VISION_LOWER_BOUND, INITIAL_VISION_UPPER_BOUND);
    }

}
