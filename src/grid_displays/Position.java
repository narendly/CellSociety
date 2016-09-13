package grid_displays;

public class Position {
    private final int myXCoord;
    private final int myYCoord;

    public Position (int xCoord, int yCoord) {
        myXCoord = xCoord;
        myYCoord = yCoord;
    }

    public int getXCoord () {
        return myXCoord;
    }

    public int getYCoord () {
        return myYCoord;
    }
}
