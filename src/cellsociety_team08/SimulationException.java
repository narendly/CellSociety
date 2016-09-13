package cellsociety_team08;

public class SimulationException extends Exception {

    /**
     * Default serialVersion as suggested by Eclipse
     */
    private static final long serialVersionUID = 1L;

    public SimulationException () {
    }

    public SimulationException (String arg0) {
        super(arg0);
    }

    public SimulationException (Throwable arg0) {
        super(arg0);
    }

    public SimulationException (String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    public SimulationException (String arg0, Throwable arg1, boolean arg2, boolean arg3) {
        super(arg0, arg1, arg2, arg3);
    }

}
