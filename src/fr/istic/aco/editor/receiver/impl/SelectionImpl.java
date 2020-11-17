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
        this.beginIndex = beginIndex;
    }

    @Override
    public void setEndIndex(int endIndex) {
        this.endIndex = endIndex;
    }
}
