package com.sviryd.algorithms.lafore.exercise.chapter13.moveOfKnight.location;

public class Location {
    private int abs;
    private int ord;

    public Location(final int abs, final int ord) {
        this.abs = abs;
        this.ord = ord;
    }

    public int getAbs() {
        return abs;
    }

    public int getOrd() {
        return ord;
    }

    public Location getShiftingLocation(final Shifting shifting){
        int newAbs = abs + shifting.getAbs();
        int newOrd = ord + shifting.getOrd();
        return new Location(newAbs,newOrd);
    }
}

