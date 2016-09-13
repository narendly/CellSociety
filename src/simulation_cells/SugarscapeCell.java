package simulation_cells;

import java.util.ArrayList;
import java.util.List;
import javafx.scene.paint.Color;
import states.SugarscapeState;


public abstract class SugarscapeCell extends Cell {
    private static final int COLOR_MIN = 50;
    private static final int RGB_MAX = 255;
    private AgentInfo myAgentInfo;
    private PatchInfo myPatchInfo;

    public SugarscapeCell (SugarscapeState initialState) {
        super(initialState);
    }

    public void setAgentInfo (AgentInfo newInfo) {
        myAgentInfo = newInfo;
    }

    public AgentInfo getAgentInfo () {
        return myAgentInfo;
    }

    public void setPatchInfo (PatchInfo newInfo) {
        myPatchInfo = newInfo;
    }

    public PatchInfo getPatchInfo () {
        return myPatchInfo;
    }

    public boolean isVacant () {
        return getState() == SugarscapeState.PATCH;
    }

    public List<SugarscapeCell> getEligibleOpenNeighbors (List<? extends SugarscapeCell> neighbrs) {
        List<SugarscapeCell> vacantNeighbors = new ArrayList<SugarscapeCell>();
        for (SugarscapeCell cell : neighbrs) {
            if ((cell.isVacant() && !cell.nextAlreadyCalculated()) ||
                (cell.isVacant() && cell.getNextState() == SugarscapeState.PATCH)) {
                vacantNeighbors.add(cell);
            }
        }
        return vacantNeighbors;
    }

    public Cell findNeighborWithMaxSugar (List<SugarscapeCell> neighbors) {
        double maxSugar = Double.NEGATIVE_INFINITY;
        List<SugarscapeCell> maxSugarNeighbors = new ArrayList<SugarscapeCell>();
        for (SugarscapeCell cell : neighbors) {
            if (cell.getPatchInfo().getSugar() == maxSugar) {
                maxSugarNeighbors.add(cell);
            }
            else if (cell.getPatchInfo().getSugar() > maxSugar) {
                maxSugarNeighbors.clear();
                maxSugarNeighbors.add(cell);
                maxSugar = cell.getPatchInfo().getSugar();
            }
        }
        return getRandomElement(maxSugarNeighbors);
    }

    @Override
    public Color getColor () {
        if (getState() == SugarscapeState.AGENT) {
            return SugarscapeState.AGENT.getColor();
        }
        else {
            double ratio = getPatchInfo().getSugar() * 1.0 / getPatchInfo().getMaxSugar();
//            double redAndBlue = Math.max(COLOR_MIN * 1.0 / RGB_MAX, 1 - ratio);
            return new Color(0, 1.0, 0, ratio);
        }
    }
}
