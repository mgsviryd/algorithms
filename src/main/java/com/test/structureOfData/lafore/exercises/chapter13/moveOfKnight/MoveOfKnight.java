package com.test.structureOfData.lafore.exercises.chapter13.moveOfKnight;

import com.test.structureOfData.lafore.exercises.chapter13.moveOfKnight.location.Shifting;

public enum MoveOfKnight {
    SHIFTING_UP_LEFT(new Shifting(-1, 2)),
    SHIFTING_UP_RIGHT(new Shifting(1, 2)),
    SHIFTING_DOWN_LEFT(new Shifting(-1, -2)),
    SHIFTING_DOWN_RIGHT(new Shifting(1, -2)),
    SHIFTING_LEFT_UP(new Shifting(-2, 1)),
    SHIFTING_LEFT_DOWN(new Shifting(-2, -1)),
    SHIFTING_RIGHT_UP(new Shifting(2, 1)),
    SHIFTING_RIGHT_DOWN(new Shifting(2, -1));

    public static final int COUNT = 8;
    public static int getCount(){
        return COUNT;
    }
    private Shifting shifting;
    public Shifting getShifting(){
        return shifting;
    }

    MoveOfKnight(Shifting shifting) {
        this.shifting = shifting;
    }
}
