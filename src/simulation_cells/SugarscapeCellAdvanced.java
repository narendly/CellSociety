package simulation_cells;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import states.SugarscapeState;


public class SugarscapeCellAdvanced extends SugarscapeCell {
    private AdvancedAgentInfo myAdvancedAgentInfo;

    public SugarscapeCellAdvanced (SugarscapeState initialState,
                                   int maxSugar,
                                   int sugarGrowBackRate,
                                   int fertileLimitsLower,
                                   int fertileLimitsUpper) {
        super(initialState);
        setPatchInfo(new PatchInfo(maxSugar, sugarGrowBackRate));
        setAdvancedAgentInfo(new AdvancedAgentInfo(fertileLimitsLower, fertileLimitsUpper));
    }

    public void setAdvancedAgentInfo (AdvancedAgentInfo info) {
        myAdvancedAgentInfo = info;
    }

    public AdvancedAgentInfo getAdvancedAgentInfo () {
        return myAdvancedAgentInfo;
    }

    public boolean isStable (List<SugarscapeCellAdvanced> neighbors) {
        calculateNextState(neighbors);
        return getState() == getNextState();
    }

    public void calculateNextState (List<SugarscapeCellAdvanced> neighbors) {
        if (nextAlreadyCalculated()) {
            return;
        }
        getPatchInfo().addSugar();
        if (getState() == SugarscapeState.PATCH) {
            setNextState(SugarscapeState.PATCH);
            return;
        }

        calculateAdvancedAgentNextState(neighbors);
    }

    public void calculateAdvancedAgentNextState (List<SugarscapeCellAdvanced> neighbors) {
        if (getAdvancedAgentInfo().isDead()) {
            setNextState(SugarscapeState.PATCH);
            return;
        }

        getAdvancedAgentInfo().incrementAge();
        List<SugarscapeCell> vacantNeighbors = getEligibleOpenNeighbors(neighbors);
        List<SugarscapeCellAdvanced> fertileNeighbors = findEligibleFertileNeighbors(neighbors);
        if (!hasConditionToReproduce(fertileNeighbors, vacantNeighbors)) {
            handleAgent(vacantNeighbors);
        }
        else {
            handleAdvancedAgent(fertileNeighbors, vacantNeighbors);
        }
    }

    public void handleAgent (List<SugarscapeCell> vacantNeighbors) {
        if (vacantNeighbors.size() > 0) {
            SugarscapeCellAdvanced neighborWithMaxSugar =
                    (SugarscapeCellAdvanced)findNeighborWithMaxSugar(
                                                                      vacantNeighbors);
            neighborWithMaxSugar.setNextState(SugarscapeState.AGENT);
            neighborWithMaxSugar.copyAdvancedAgentInfo(getAdvancedAgentInfo());
            neighborWithMaxSugar.agentMeetsPatch();
            setNextState(SugarscapeState.PATCH);
        }
        else {
            setNextState(SugarscapeState.AGENT);
            getAdvancedAgentInfo().addSugar(getPatchInfo().getSugar());
            getPatchInfo().reset();
        }
    }

    public void copyAdvancedAgentInfo (AdvancedAgentInfo info) {
        getAdvancedAgentInfo().copyInfo(info);
    }

    public void agentMeetsPatch () {
        getAdvancedAgentInfo().addSugar(getPatchInfo().getSugar());
        getAdvancedAgentInfo().subtractSugarMetabolism();
        getPatchInfo().reset();
    }

    public void handleAdvancedAgent (List<SugarscapeCellAdvanced> fertileNeighbors,
                                     List<SugarscapeCell> vacantNeighbors) {
        SugarscapeCellAdvanced chosenCell = getRandomElement(fertileNeighbors);
        Set<SugarscapeCellAdvanced> parents =
                new HashSet<SugarscapeCellAdvanced>(Arrays.asList(this, chosenCell));
        for (SugarscapeCellAdvanced cell : parents) {
            cell.setNextState(SugarscapeState.AGENT);
            cell.reproduce();
            cell.agentMeetsPatch();
        }

        SugarscapeCellAdvanced chosenEmptyCell =
                (SugarscapeCellAdvanced)getRandomElement(vacantNeighbors);
        chosenEmptyCell.setNextState(SugarscapeState.AGENT);
        chosenEmptyCell.reproducedFrom(this, chosenCell);
    }

    public boolean hasConditionToReproduce (List<SugarscapeCellAdvanced> fertileNeighbors,
                                            List<SugarscapeCell> vacantNeighbors) {
        return getAdvancedAgentInfo().isFertile() && fertileNeighbors.size() != 0 &&
               vacantNeighbors.size() != 0;
    }

    public void reproduce () {
        getAdvancedAgentInfo().reproduce();
    }

    public void reproducedFrom (SugarscapeCellAdvanced m, SugarscapeCellAdvanced f) {
        getAdvancedAgentInfo().reproducedFrom(m.getAdvancedAgentInfo(), f.getAdvancedAgentInfo());
    }

    public List<SugarscapeCellAdvanced> findEligibleFertileNeighbors (List<SugarscapeCellAdvanced> neighbors) {
        List<SugarscapeCellAdvanced> fertileNeighbors = new ArrayList<SugarscapeCellAdvanced>();
        for (SugarscapeCellAdvanced cell : neighbors) {
            if (cell.getState() == SugarscapeState.AGENT && !cell.nextAlreadyCalculated() &&
                getAdvancedAgentInfo().canReproduceWith(cell.getAdvancedAgentInfo())) {
                fertileNeighbors.add(cell);
            }
        }
        return fertileNeighbors;
    }
}
