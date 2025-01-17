package com.sviryd.algorithms.lafore.exercises.chapter5;

public interface IQueue<E> {
    boolean add(E e);

    boolean offer(E e);

    E remove();

    E poll();

    E element();

    E peek();
}
