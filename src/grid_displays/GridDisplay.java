package grid_displays;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import cellsociety_team08.Configuration;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;
import simulation_managers.ICellManager;


/**
 * Abstract class that manages the display of a grid of cells with unspecified shapes. Should be
 * extended to define which shape to use to display each cell
 *
 * @author David Maydew
 *
 */
public abstract class GridDisplay {

    public static final String DEFAULT_RESOURCE_PACKAGE = "resources/";
    private ResourceBundle myResources =
            ResourceBundle.getBundle(DEFAULT_RESOURCE_PACKAGE + "style");

    private Region myDisplay;
    private Pane myInnerDisplay;
    private ICellManager myCellManager;
    private List<List<Shape>> myCells;

    /**
     * Creates an empty grid display with the given width and height
     *
     * @param dispWidth width of the display pane
     * @param dispHeight height of the display pane
     */
    public GridDisplay (double dispWidth, double dispHeight) {
        createDisplay();
        getDisplay().setPrefSize(dispWidth, dispHeight);
    }

    /**
     * Creates and sets the appropriate displays required to show a grid of shapes
     */
    private void createDisplay () {
        ScrollPane scrollDisplay = new ScrollPane();
        Pane innerDisplay = new Pane();
        setInnerDisplay(innerDisplay);
        scrollDisplay.setContent(innerDisplay);
        setDisplay(scrollDisplay);
    }

    /**
     * Initializes the display based on a given configuration and model interface
     *
     * @param configuration of the active simulation
     * @param cellManager interface for communicating with the model
     */
    public void initialize (Configuration configuration, ICellManager cellManager) {
        setCellManager(cellManager);
        setCells(createCells());
        updateGridDisplay();
    }

    /**
     * Updates the grid to show the current color representations of all cells in the model
     */
    public void updateGridDisplay () {
        List<List<Color>> updatedColors = getCellManager().getAllCellColors();
        for (int x = 0; x < updatedColors.size(); x++) {
            for (int y = 0; y < updatedColors.get(0).size(); y++) {
                Paint updatedPaint = updatedColors.get(x).get(y);
                getCell(x, y).setFill(updatedPaint);
            }
        }
    }

    /**
     * @return 2d list representing all of the shapes, appropriately positioned, that are to be
     *         displayed on the screen
     */
    private List<List<Shape>> createCells () {
        double cellWidth =
                calculateDimension("CellWidth", getDisplayWidth() / getCellManager().getMaxY());
        double cellHeight =
                calculateDimension("CellHeight", getDisplayHeight() / getCellManager().getMaxX());
        List<List<Shape>> cells = new ArrayList<List<Shape>>();
        for (int x = 0; x < getCellManager().getMaxX(); x++) {
            List<Shape> cellRow = new ArrayList<Shape>();
            for (int y = 0; y < getCellManager().getMaxY(); y++) {
                Shape newCell = createNewCell(new Position(x, y), cellWidth, cellHeight);
                styleCell(newCell);
                getInnerDisplay().getChildren().add(newCell);
                cellRow.add(newCell);
            }
            cells.add(cellRow);
        }
        return cells;
    }

    private double calculateDimension (String dimensionKey, double defaultval) {
        if (myResources.containsKey(dimensionKey)) {
            return Integer.parseInt(myResources.getString(dimensionKey));
        }
        else {
            return defaultval;
        }
    }

    /**
     * Styles a given cell based on style properties
     */
    private void styleCell (Shape cell) {
        if (Boolean.parseBoolean(myResources.getString("Outline"))) {
            cell.setStroke(Color.BLACK);
        }
    }

    /**
     * Abstract method to allow subclasses to create a specific shape type and location to use in
     * the display
     *
     * @param position of the current cell
     * @return a shape with its position appropriate calculated
     */
    protected abstract Shape createNewCell (Position position, double cellWidth, double cellHeight);

    /**
     * a and b must be of the same length to work appropriately
     *
     * @param a first array
     * @param b second array
     * @return array with the interleaved elements of a and b, starting with a
     */
    protected double[] interleave (double[] a, double[] b) {
        double[] result = new double[a.length * 2];
        for (int i = 0; i < a.length; i++) {
            result[2 * i] = a[i];
            result[2 * i + 1] = b[i];
        }
        return result;
    }

    /**
     * Shifts all of the values of an array by a given amount
     *
     * @param elements array of values
     * @param shiftAmount amount to shift by
     */
    protected void shiftElements (double[] elements, double shiftAmount) {
        for (int i = 0; i < elements.length; i++) {
            double x = elements[i];
            elements[i] = x + shiftAmount;
        }
    }

    public Region getDisplay () {
        return myDisplay;
    }

    private void setDisplay (Region display) {
        myDisplay = display;
    }

    private ICellManager getCellManager () {
        return myCellManager;
    }

    private void setCellManager (ICellManager cellManager) {
        myCellManager = cellManager;
    }

    private Shape getCell (int x, int y) {
        return myCells.get(x).get(y);
    }

    private void setCells (List<List<Shape>> cells) {
        myCells = cells;
    }

    private double getDisplayWidth () {
        return getDisplay().getPrefWidth();
    }

    private double getDisplayHeight () {
        return getDisplay().getPrefHeight();
    }

    private Pane getInnerDisplay () {
        return myInnerDisplay;
    }

    private void setInnerDisplay (Pane innerDisplay) {
        myInnerDisplay = innerDisplay;
    }
}
