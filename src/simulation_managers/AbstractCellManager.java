package simulation_managers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import cellsociety_team08.Configuration;
import cellsociety_team08.SimulationException;
import grid_displays.Position;
import grids.CellGrid;
import grids.FiniteGrid;
import grids.ToroidalGrid;
import javafx.scene.paint.Color;
import simulation_cells.Cell;
import states.IState;


/**
 * Manages the back-end model of a Cellular Automata model
 *
 * @author David Maydew
 */
public abstract class AbstractCellManager<E extends Cell> implements ICellManager {

    public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";

    private CellGrid<E> myCellGrid;
    private ResourceBundle myStyleResources =
            ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "style");
    private ResourceBundle myErrorResources =
            ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "error");

    /**
     * Initialize a CA model based on a configuration containing initial states and necessary
     * parameters to run this simulation
     *
     * @param config contains initial States and Parameters for this model
     * @throws SimulationException
     */
    @Override
    public void initialize (Configuration config) throws SimulationException {
        List<List<E>> allCells;
        if (!config.isRandomStates()) {
            allCells = createCellsFromStates(config.getInitialStates(), config.getParametersMap());
        }
        else {
            allCells =
                    createCellsRandomly(config.getRandomWidth(), config.getRandomHeight(),
                                        config.getParametersMap());
        }
        createCellGrid(allCells);
    }

    /**
     * Creates a grid of the appropriate shape to manage storage and access to the cells used in
     * this simulation
     *
     * @param allCells 2d list of cells to be initially put in the grid
     */
    private void createCellGrid (List<List<E>> allCells) {
        String gridEdge = myStyleResources.getString("GridEdge");
        CellGrid<E> cellGrid = null;
        if (gridEdge.equals("Finite")) {
            cellGrid = new FiniteGrid<E>(allCells);
        }
        else if (gridEdge.equals("Toroidal")) {
            cellGrid = new ToroidalGrid<E>(allCells);
        }
        else {
            throw new UnsupportedOperationException();
        }
        setCellGrid(cellGrid);
    }

    /**
     * Initializes the cells for this model to their initial States and parameters
     *
     * @param initStates 2d list of Integers representing the initial states of the Cells
     * @param parameters map with all the necessary parameters to initialize this simulation's cell
     * @throws SimulationException
     */
    private List<List<E>> createCellsFromStates (List<List<Integer>> initStates,
                                                 Map<String, Number> parameters) throws SimulationException {
        List<List<E>> cells = new ArrayList<List<E>>();
        for (List<Integer> stateRow : initStates) {
            List<E> cellRow = new LinkedList<E>();
            for (Integer initialState : stateRow) {
                try {
                    cellRow.add(createNewCell(initialState, parameters));
                }
                catch (ArrayIndexOutOfBoundsException e) {
                    throw new SimulationException(myErrorResources.getString("InvalidInitialState"),
                                                  e);
                }
            }
            cells.add(cellRow);
        }
        return cells;
    }

    /**
     * @param initialStateNum integer representation of the initial state for this cell, based on
     *        State enum
     * @param parameters map with all necessary parameters for this cell's initialization
     * @return a new Cell of a specific type with the specified state and parameters
     */
    protected abstract E createNewCell (Integer initialStateNum, Map<String, Number> parameters);

    /**
     * Randomly generates a 2d list of cells based on the given dimensions and parameters
     *
     * @param randomWidth number of cells per row
     * @param randomHeight number of cells per column
     * @param parametersMap parameters used to initialize cells
     * @return 2d array of the randomly generated cells
     */
    private List<List<E>> createCellsRandomly (int randomWidth,
                                               int randomHeight,
                                               Map<String, Number> parametersMap) {
        List<List<E>> cells = new ArrayList<List<E>>();
        for (int x = 0; x < randomWidth; x++) {
            List<E> cellRow = new LinkedList<E>();
            for (int y = 0; y < randomHeight; y++) {
                cellRow.add(createNewCell(parametersMap));
            }
            cells.add(cellRow);
        }
        return cells;
    }

    /**
     * Returns a "randomly" generated cell, whose state can be decided by the specific
     * implementation to align with a probability distribution, for example
     */
    protected abstract E createNewCell (Map<String, Number> parametersMap);

    /**
     * Updates all cells based on the CA steps of calculating the next state for every node, in
     * order, then updating every cell to that next state
     */
    @Override
    public void updateAllCells () {
        calculateAllNextStates();
        updateAllToNextStates();
    }

    /**
     * Calculates the next step of every cell, in order from top left to bottom right
     */
    @Override
    public void calculateAllNextStates () {
        prepareForNextStateCalc();
        int cellCount = 0;
        for (E cell : getCellGrid().getAllCellsInOrder()) {
            calculateNextState(cell, positionFromCount(cellCount));
            cellCount++;
        }
    }

    /**
     * @param cellCount which number cell in the grid this is
     * @return Position object representing the 2d coordinates of the cell
     */
    private Position positionFromCount (int cellCount) {
        int xCoord = cellCount / getMaxY();
        int yCoord = cellCount % getMaxY();
        return new Position(xCoord, yCoord);
    }

    /**
     * This method is run before any of the Cells have their next state calculated, and should be
     * overridden if any calculations need to take place once per generation
     */
    protected void prepareForNextStateCalc () {
    }

    /**
     * Calculates the next stage for a given cell at a given position
     *
     * @param cell the cell to be updated
     * @param position the object representing the coordinate of the current cell
     */
    protected abstract void calculateNextState (E cell, Position position);

    /**
     * Updates all Cells to their next states in an unordered manner, since in this step order is
     * not relevant
     */
    private void updateAllToNextStates () {
        for (Cell cell : getCellGrid().getAllCells()) {
            cell.update();
        }
    }

    /**
     * @return whether all cells in this model are 'stable,' a.k.a. their next state is the same as
     *         their current state
     */
    @Override
    public boolean areAllCellsStable () {
        prepareForStableCheck();
        boolean allStable = true;
        int cellCount = 0;
        for (E cell : getCellGrid().getAllCellsInOrder()) {
            allStable &= isCellStable(cell, positionFromCount(cellCount));
            cellCount++;
        }
        return allStable;
    }

    /**
     * This method is run before any of the Cells have their stability checked, and should be
     * overridden if any calculations need to take place once per generation
     */
    protected void prepareForStableCheck () {
    }

    /**
     * Checks if the state for a given cell at a given position is stable
     *
     * @param cell to be checked
     * @param position the object representing the coordinate of the current cell
     */
    protected abstract boolean isCellStable (E cell, Position position);

    /**
     * Get all of the states represented in this Manager
     *
     * @return 2d list of State enums of all cells active in this simulation
     */
    @Override
    public List<List<IState>> getAllStates () {
        List<List<IState>> allStates = new ArrayList<List<IState>>();
        for (List<E> cellRow : getCellGrid().getCellsInGrid()) {
            List<IState> stateRow = new ArrayList<IState>();
            for (Cell cell : cellRow) {
                stateRow.add(cell.getState());
            }
            allStates.add(stateRow);
        }
        return allStates;
    }

    /**
     * Gets the neighboring cells based on the number of neighbors considered from the style
     * properties file, one level away.
     *
     * @param position of the base cell to look for neighbors around
     * @return set of neighbors
     */
    protected Set<E> getNeighbors (Position position) {
        return new HashSet<E>(getOrderedNeighbors(position, 1));
    }

    /**
     * Gets an ordered list of neighboring cells that can come at a variable distance away from the
     * base cell
     *
     * @param position of the base cell to look for neighbors around
     * @param rangeMultiplier number of cells away that is considered a neighbor
     * @return an ordered list of neighbors, from closest to farthest away
     */
    protected List<E> getOrderedNeighbors (Position position, int rangeMultiplier) {
        int numNeighborsConsidered =
                Integer.parseInt(myStyleResources.getString("NumNeighborsConsidered"));
        return getCellGrid().getNeighbors(position, numNeighborsConsidered, rangeMultiplier);
    }

    /**
     * @return All cells active in the grid
     */
    protected Collection<E> getAllCells () {
        return getCellGrid().getAllCells();
    }

    /**
     * @return a 2d grid that contains the color representation of each cell at that position
     */
    @Override
    public List<List<Color>> getAllCellColors () {
        List<List<Color>> paints = new ArrayList<List<Color>>();
        for (List<E> cellRow : getCellGrid().getCellsInGrid()) {
            List<Color> paintRow = new ArrayList<Color>();
            for (E cell : cellRow) {
                paintRow.add(cell.getColor());
            }
            paints.add(paintRow);
        }
        return paints;
    }

    /**
     * @return the CellGrid object that manages the grid for this simulation
     */
    private CellGrid<E> getCellGrid () {
        return myCellGrid;
    }

    /**
     * Get the maximum x value (number of rows) in this simulation
     */
    @Override
    public int getMaxX () {
        return getCellGrid().getMaxX();
    }

    /**
     * Get the maximum y value (number of columns) in this simulation
     */
    @Override
    public int getMaxY () {
        return getCellGrid().getMaxY();
    }

    private void setCellGrid (CellGrid<E> cellGrid) {
        myCellGrid = cellGrid;
    }

}
