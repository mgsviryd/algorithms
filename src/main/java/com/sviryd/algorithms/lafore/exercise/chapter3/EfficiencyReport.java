package com.sviryd.algorithms.lafore.exercise.chapter3;

import java.util.concurrent.atomic.AtomicInteger;

public class EfficiencyReport {
    private AtomicInteger compare;
    private AtomicInteger copy;

    public EfficiencyReport() {
        this.compare = new AtomicInteger(0);
        this.copy = new AtomicInteger(0);
    }

    public void increaseCompare() {
        compare.getAndIncrement();
    }

    public void increaseCopy() {
        copy.getAndIncrement();
    }

    public Integer getCompare() {
        return compare.intValue();
    }

    public Integer getCopy() {
        return copy.intValue();
    }
}
