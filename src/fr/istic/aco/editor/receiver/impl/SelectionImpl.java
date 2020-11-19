package fr.istic.aco.editor.receiver.impl;

import fr.istic.aco.editor.receiver.contract.Selection;

public class SelectionImpl implements Selection {
    private int beginIndex;
    private int endIndex;
    private final StringBuffer buffer;

    public SelectionImpl(StringBuffer buffer) {
        this.beginIndex = 0;
        this.endIndex = 0;
        this.buffer = buffer;
    }

    @Override
    public int getBeginIndex() {
        return beginIndex;
    }

    @Override
    public int getEndIndex() {
        return endIndex;
    }

    @Override
    public int getBufferBeginIndex() {
        return 0;
    }

    @Override
    public int getBufferEndIndex() {
        return buffer.length();
    }

    @Override
    public void setBeginIndex(int beginIndex) {
        if (beginIndex < getBufferBeginIndex() || beginIndex > getBufferEndIndex())
            throw new IndexOutOfBoundsException("beginIndex is out of bounds");

        this.beginIndex = beginIndex;
    }

    @Override
    public void setEndIndex(int endIndex) {
        if (endIndex < getBeginIndex() || endIndex > getBufferEndIndex())
            throw new IndexOutOfBoundsException("endIndex is out of bounds");

        this.endIndex = endIndex;
    }
}
