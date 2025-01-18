package com.sviryd.algorithms.lafore.exercise.chapter13;

import com.sviryd.algorithms.lafore.exercise.chapter13.moveOfKnight.MoveOfKnightSimulator;
import org.junit.Test;

public class MoveOfKnightSimulationTest {
    @Test
    public void passAllCellsUsingKnight(){
        MoveOfKnightSimulator moveOfKnightSimulator = new MoveOfKnightSimulator(5);
        moveOfKnightSimulator.displaySequenceOfKnights();
    }
}
