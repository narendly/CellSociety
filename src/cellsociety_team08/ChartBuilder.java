package cellsociety_team08;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import simulation_managers.ICellManager;
import states.IState;

/**
 * Subclass of SimulationBuilder that constructs
 * a dynamically updating chart that reflects
 * key statistics of the simulation.
 * 
 * @author Hunter Lee
 */
public class ChartBuilder extends SimulationBuilder {
    private static final int RGB_CONST = 255;
    
    private ICellManager myCellManager;
    private LineChart myLineChart;
    private Map<String, Integer> myMap;
    private Map<String, XYChart.Series> mySeriesMap;
    private Map<String, Color> myColorMap;
    private Set<String> myStates;
    private int myTimeCount = 0;
    
    /**
     * Constructor
     * 
     * @param simulation
     * @param language
     * @param cellManager
     */
    public ChartBuilder (Simulation simulation,
                         String language,
                         ICellManager cellManager) {
        super(simulation, language);
        myCellManager = cellManager;
    }

    /**
     * A function called by Simulation
     * to retrieve a chart
     * 
     * @return VBox
     */
    public VBox extractChart () {
        createChart();
        VBox root = new VBox();
        root.getChildren().addAll(getChart());
        return root;
    }

    /**
     * An internal method that creates a chart
     * 
     */
    private void createChart () {
        final NumberAxis xAxis = new NumberAxis();
        final NumberAxis yAxis = new NumberAxis();
        myLineChart = new LineChart(xAxis, yAxis);
        yAxis.setLabel(getResources().getString("Yaxis"));
        xAxis.setLabel(getResources().getString("Xaxis"));
        myLineChart.getXAxis().setAutoRanging(true);
        myLineChart.getYAxis().setAutoRanging(true);
        myLineChart.setTitle(getResources().getString("Title"));
        mySeriesMap = new HashMap();
        myStates = new HashSet();
        myColorMap = new HashMap();
        getCounts();
        for (String state : myStates) {
            createSeries(state);
        }
    }

    /**
     * Calculates the count of states
     * 
     */
    private void getCounts () {
        List<List<IState>> cellStates = myCellManager.getAllStates();
        Map<IState, Integer> countMap = new HashMap();

        for (List<IState> row : cellStates) {
            for (IState col : row) {
                if (countMap.containsKey(col)) {
                    countMap.merge(col, 2, (v, vv) -> ++v);
                }
                else {
                    countMap.put(col, 1);
                }
            }
        }
        myMap = new HashMap();
        for (IState state : countMap.keySet()) {
            myMap.put(state.toString(), countMap.get(state));
            myStates.add(state.toString());
            myColorMap.put(state.toString(), state.getColor());
        }
    }
    
    /**
     * A function that can be called outside this class
     * that updates the chart
     */
    public void updateChart () {
        getCounts();
        for (String state : myStates) {
            addData(state, mySeriesMap.get(state));
        }
        incrementTime();
    }

    /**
     * Private method that creates Series to be used with the chart
     * 
     * @param state
     * @return XYChart.Series
     */
    private XYChart.Series createSeries (String state) {
        XYChart.Series mySeries = new XYChart.Series();
        mySeries.setName(state);
        getChart().getData().add(mySeries);
        getSeriesMap().put(state, mySeries);
        return mySeries;
    }

    /**
     * Private method that adds data
     * 
     * @param state
     * @param mySeries
     */
    private void addData (String state, XYChart.Series mySeries) {
        if (mySeries == null) {
            mySeries = createSeries(state);
        }
        if (!myMap.containsKey(state)) {
            myMap.put(state, 0);
        }
        mySeries.getData().add(new XYChart.Data(myTimeCount, myMap.get(state)));
    }

    private LineChart getChart () {
        return myLineChart;
    }

    private Map<String, Integer> getMap () {
        return myMap;
    }

    private Map<String, XYChart.Series> getSeriesMap () {
        return mySeriesMap;
    }

    private void incrementTime () {
        myTimeCount++;
    }

    /**
     * Private method that converts Color object to
     * its corresponding hexademical value
     * 
     * @param color
     * @return
     */
    private String toHex (Color color) {
        String hex = String.format("#%02X%02X%02X",
                                   (int) (color.getRed() * RGB_CONST),
                                   (int) (color.getGreen() * RGB_CONST),
                                   (int) (color.getBlue() * RGB_CONST));
        return hex;
    }
}
