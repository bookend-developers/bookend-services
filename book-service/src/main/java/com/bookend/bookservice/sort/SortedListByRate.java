package com.bookend.bookservice.sort;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

public class SortedListByRate<E> extends LinkedList<E> {
    private Comparator<E> comparator;
    public SortedListByRate(final Comparator comparator){
        this.comparator=comparator;
    }
    @Override
    public void add(int index, Object element)
    {
        this.add((E) element);
    }

    @Override
    public boolean add(final E e)
    {
        final boolean result = super.add(e);
        Collections.sort(this, this.comparator);
        return result;
    }
}
