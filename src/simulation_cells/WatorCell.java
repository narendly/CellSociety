package simulation_cells;

import java.util.HashSet;
import java.util.Set;
import states.WatorState;


public class WatorCell extends Cell {

    private PreyInfo myPreyInfo;
    private PredatorInfo myPredatorInfo;

    /**
     * Constructor
     */
    public WatorCell (WatorState initialCellState,
                      int preyBreedTime,
                      int predatorBreedTime,
                      int starveTime) {
        super(initialCellState);
        myPreyInfo = new PreyInfo(preyBreedTime);
        myPredatorInfo = new PredatorInfo(predatorBreedTime, starveTime);
    }

    /**
     * Get the PREY information
     */
    public PreyInfo getPreyInfo () {
        return myPreyInfo;
    }

    /**
     * Get the PREDATOR information
     */
    public PredatorInfo getPredatorInfo () {
        return myPredatorInfo;
    }

    /**
     * Given a collection of cells, return the ones that are eligible to be swapped with because
     * their next state hasn't been calculated yet or will remain Empty
     */
    private Set<WatorCell> findEligibleEmptyNeighbors (Set<WatorCell> neighbors) {
        Set<WatorCell> emptyCells = new HashSet<WatorCell>();
        for (WatorCell cell : neighbors) {
            if (cell.getState() == WatorState.EMPTY &&
                (!cell.nextAlreadyCalculated() || cell.getNextState() == WatorState.EMPTY)) {
                emptyCells.add(cell);
            }
        }
        return emptyCells;
    }

    /**
     * Given a collection of cells, return the ones that are eligible prey because they are
     * currently a Prey and haven't calculated their next State yet
     */
    private Set<WatorCell> findEligiblePreyNeighbors (Set<WatorCell> neighbors) {
        Set<WatorCell> preyCells = new HashSet<WatorCell>();
        for (WatorCell cell : neighbors) {
            if (cell.getState() == WatorState.PREY && !cell.nextAlreadyCalculated()) {
                preyCells.add(cell);
            }
        }
        return preyCells;
    }

    public boolean isStable (Set<WatorCell> neighbors) {
        calculateNextState(neighbors);
        return getState() == getNextState();
    }

    public void calculateNextState (Set<WatorCell> neighbors) {
        if (nextAlreadyCalculated()) {
            return;
        }
        if (getState() == WatorState.EMPTY) {
            setNextState(WatorState.EMPTY);
        }
        Set<WatorCell> emptyNeighbors = findEligibleEmptyNeighbors(neighbors);
        Set<WatorCell> preyNeighbors = findEligiblePreyNeighbors(neighbors);
        if (getState() == WatorState.PREY) {
            handlePrey(emptyNeighbors);
        }
        else if (getState() == WatorState.PREDATOR) {
            handlePredator(emptyNeighbors, preyNeighbors);
        }
    }

    private void handlePredator (Set<WatorCell> emptyNeighbors, Set<WatorCell> preyNeighbors) {
        if (getPredatorInfo().isStarving()) {
            setNextState(WatorState.EMPTY);
            return;
        }
        else if (preyNeighbors.size() != 0) {
            handleEat(emptyNeighbors, preyNeighbors);
        }
        else if (emptyNeighbors.size() != 0) {
            handleMove(emptyNeighbors);

        }
        else {
            myPredatorInfo.decreaseBreedAndIncreaseStarve();
            setNextState(WatorState.PREDATOR);
        }
    }

    private void handleMove (Set<WatorCell> emptyNeighbors) {
        WatorCell chosenEmptyCell =
                chooseANeighborAndSetNextState(emptyNeighbors, WatorState.PREDATOR);
        if (myPredatorInfo.getPredatorTurnsToBreed() <= 0) {
            myPredatorInfo.resetBreedAndIncreaseStarve();
            chosenEmptyCell.replacedBy(this);
            myPredatorInfo.reset();
            setNextState(WatorState.PREDATOR);
        }
        else {
            myPredatorInfo.decreaseBreedAndIncreaseStarve();
            chosenEmptyCell.replacedBy(this);
            setNextState(WatorState.EMPTY);
        }
    }

    private WatorCell chooseANeighborAndSetNextState (Set<WatorCell> neighbors,
                                                      WatorState newNextState) {
        WatorCell chosenCell = getRandomElement(neighbors);
        chosenCell.setNextState(newNextState);
        return chosenCell;
    }

    private void handleEat (Set<WatorCell> emptyNeighbors, Set<WatorCell> preyNeighbors) {
        setNextState(WatorState.PREDATOR);
        chooseANeighborAndSetNextState(preyNeighbors, WatorState.EMPTY);
        getPredatorInfo().resetTurnsSinceLastAte();
        if (getPredatorInfo().getPredatorTurnsToBreed() <= 0 && emptyNeighbors.size() != 0) {
            WatorCell chosenEmptyCell =
                    chooseANeighborAndSetNextState(emptyNeighbors, WatorState.PREDATOR);
            chosenEmptyCell.getPredatorInfo().reset();
            getPredatorInfo().resetBreedAndIncreaseStarve();
        }
        else {
            getPredatorInfo().decreaseBreedAndIncreaseStarve();
        }
    }

    private void handlePrey (Set<WatorCell> emptyNeighbors) {
        if (emptyNeighbors.size() == 0) {
            myPreyInfo.decrementBreedTurns();
            setNextState(getState());
            return;
        }
        if (myPreyInfo.getPreyTurnsToBreed() <= 0) {
            myPreyInfo.reset();
            setNextState(WatorState.PREY);
        }
        else {
            myPreyInfo.decrementBreedTurns();
            setNextState(WatorState.EMPTY);
        }
        WatorCell chosenEmptyCell = chooseANeighborAndSetNextState(emptyNeighbors, WatorState.PREY);
        chosenEmptyCell.replacedBy(this);
    }

    /**
     * Replace the information of a cell with the information of myCell
     */
    public void replacedBy (WatorCell myCell) {
        myPreyInfo.copyInfo(myCell.getPreyInfo());
        myPredatorInfo.copyInfo(myCell.getPredatorInfo());
    }
}
