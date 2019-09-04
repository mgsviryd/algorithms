package com.test.structureOfData.lafore.exercises.chapter13.moveOfKnight;

import com.test.structureOfData.lafore.exercises.chapter13.moveOfKnight.location.Location;

public class Knight {
    private Location location;

    public Knight(final Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
