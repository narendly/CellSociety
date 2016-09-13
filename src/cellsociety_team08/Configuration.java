package cellsociety_team08;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * A one-size-fits-for-all object used throughout
 * the program. This stores the key variables 
 * of the simulation chosen
 * 
 */
public class Configuration {
    public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";

    private String mySimulationType;
    private String myAuthorName;
    private ResourceBundle myErrorResources =
            ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "error");

    private final Map<String, Number> myParameters = new HashMap<String, Number>();
    private List<List<Integer>> myInitialStates;
    private boolean myRandomStates;
    private int myRandomWidth;
    private int myRandomHeight;

    public Map<String, Number> getParametersMap () {
        return myParameters;
    }

    public Number getParameter (String parameter) {
        return getParameters().get(parameter);
    }

    public void setParameter (String parameter, Double value) {
        getParameters().put(parameter, value);
    }

    public String getSimulationType () {
        return mySimulationType;
    }

    public void setAuthorName (String authorname) {
        myAuthorName = authorname;
    }

    public String getAuthorName () {
        return myAuthorName;
    }

    public void setSimulationType (String simulationType) {
        mySimulationType = simulationType;
    }

    private Map<String, Number> getParameters () {
        return myParameters;
    }

    public List<List<Integer>> getInitialStates () {
        return myInitialStates;
    }

    public void setInitialStates (List<List<Integer>> initialStates) throws SimulationException {
        checkInvalidState(initialStates);
        myInitialStates = initialStates;
    }

    public int getRandomWidth () {
        return myRandomWidth;
    }

    public void setRandomWidth (int width) {
        myRandomWidth = width;
    }

    public int getRandomHeight () {
        return myRandomHeight;
    }

    public void setRandomHeight (int height) {
        myRandomHeight = height;
    }
    
    /**
     * A private method that checks states for error
     * 
     * @param initialStates
     * @throws SimulationException
     */
    private void checkInvalidState (List<List<Integer>> initialStates) throws SimulationException {
        if (initialStates.size() == 0) {
            return;
        }
        int lastRowLength = -1;
        for (List<Integer> row : initialStates) {
            if (lastRowLength == -1) {
                lastRowLength = row.size();
            }
            else if (lastRowLength != row.size()) {
                throw new SimulationException(myErrorResources.getString("UnevenGrid"));
            }
        }
    }

    /**
     * For unit testing purposes
     */
    public void printContent () {
        System.out.println("Simulation Type:  " + getSimulationType());
        System.out.println("Author:  " + getAuthorName());
        for (String key : myParameters.keySet()) {
            System.out.println("Key:  " + key);
            System.out.println("Value:  " + myParameters.get(key));
        }
    }

    public boolean isRandomStates () {
        return myRandomStates;
    }

    public void setRandomStates (boolean randomStates) {
        this.myRandomStates = randomStates;
    }
}
