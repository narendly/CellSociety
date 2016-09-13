package simulation_cells;

import java.util.ArrayList;
import java.util.List;
import states.SugarscapeState;


public class SugarscapeCellBasic extends SugarscapeCell {

    public SugarscapeCellBasic (SugarscapeState initialState, int maxSugar, int sugarGrowBackRate) {
        super(initialState);
        setAgentInfo(new BasicAgentInfo());
        setPatchInfo(new PatchInfo(maxSugar, sugarGrowBackRate));
    }

    public void copyAgentInfo (AgentInfo info) {
        getAgentInfo().setSugar(info.getSugar());
    }

    public void calculateNextState (List<SugarscapeCellBasic> neighbors) {
        if (nextAlreadyCalculated()) {
            return;
        }

        getPatchInfo().addSugar();
        if (getState() == SugarscapeState.AGENT) {
            handleAgent(neighbors);
        }
        else {
            setNextState(SugarscapeState.PATCH);
        }
    }

    public void handleAgent (List<SugarscapeCellBasic> neighbors) {
        if (getAgentInfo().isDead()) {
            setNextState(SugarscapeState.PATCH);
            return;
        }
        List<SugarscapeCell> vacantNeighbors = getEligibleOpenNeighbors(neighbors);
        if (vacantNeighbors.size() > 0) {
            SugarscapeCellBasic neighborWithMaxSugar =
                    (SugarscapeCellBasic)findNeighborWithMaxSugar(vacantNeighbors);
            neighborWithMaxSugar.setNextState(SugarscapeState.AGENT);
            neighborWithMaxSugar.copyAgentInfo(getAgentInfo());
            neighborWithMaxSugar.agentMeetsPatch();
            setNextState(SugarscapeState.PATCH);
        }
        else {
            setNextState(SugarscapeState.AGENT);
            getAgentInfo().addSugar(getPatchInfo().getSugar());
            getPatchInfo().reset();
        }
    }

    public boolean isStable (List<SugarscapeCellBasic> neighbors) {
        calculateNextState(neighbors);
        return getState() == getNextState();
    }

    public void agentMeetsPatch () {
        getAgentInfo().addSugar(getPatchInfo().getSugar());
        getAgentInfo().subtractSugarMetabolism();
        getPatchInfo().reset();
    }

    @Override
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
}
