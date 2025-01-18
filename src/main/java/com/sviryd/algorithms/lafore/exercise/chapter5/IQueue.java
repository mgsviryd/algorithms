package com.sviryd.algorithms.lafore.exercise.chapter5;

public interface IQueue<E> {
    boolean add(E e);

    boolean offer(E e);

    E remove();

    E poll();

    E element();

    E peek();
}
