package fr.istic.aco.editor.receiver.impl;

import fr.istic.aco.editor.Observer.Observer;
import fr.istic.aco.editor.receiver.contract.Engine;
import fr.istic.aco.editor.receiver.contract.Selection;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EngineImpl implements Engine {
    private StringBuffer buffer;
    private final Selection selection;
    private String clipboardContent;
    private final List<Observer<StringBuffer>> registeredObservers;

    public EngineImpl() {
        this.buffer = new StringBuffer();
        this.selection = new SelectionImpl(buffer);
        this.clipboardContent = "";
        registeredObservers = new ArrayList<>();
    }

    /**
     * Provides access to the selection control object
     *
     * @return the selection object
     */
    @Override
    public Selection getSelection() {
        return this.selection;
    }

    /**
     * Provides the whole contents of the buffer, as a string
     *
     * @return a copy of the buffer's contents
     */
    @Override
    public String getBufferContents() {
        return buffer.toString();
    }

    /**
     * Provides the clipboard contents
     *
     * @return a copy of the clipboard's contents
     */
    @Override
    public String getClipboardContents() {
        return clipboardContent;
    }

    /**
     * Removes the text within the interval
     * specified by the selection control object,
     * from the buffer.
     */
    @Override
    public void cutSelectedText() {
        //recupere le texte entre le debut et la fin de la selection à partir du buffer et le supprime du buffer
        clipboardContent = buffer.toString().substring(selection.getBeginIndex(), selection.getEndIndex());
        deleteSelection();

        setValue(buffer);
        notifyRegisteredObservers();
    }

    /**
     * Copies the text within the interval
     * specified by the selection control object
     * into the clipboard.
     */
    @Override
    public void copySelectedText() {
        clipboardContent = buffer.toString().substring(selection.getBeginIndex(), selection.getEndIndex());
    }

    /**
     * Replaces the text within the interval specified by the selection object with
     * the contents of the clipboard.
     */
    @Override
    public void pasteClipboard() {
        if (!clipboardContent.equals("")) {
            insert(clipboardContent);
        }
    }

    /**
     * Inserts a string in the buffer, which replaces the contents of the selection
     *
     * @param s the text to insert
     */
    @Override
    public void insert(String s) {
        //Supprimer la sélection

        deleteSelection();
        //on insère le texte à partir du début de la sélection
        buffer.insert(selection.getBeginIndex(), s);
        //on deplace le curseur
        selection.setBeginIndex(selection.getBeginIndex() + s.length());
        selection.setEndIndex(selection.getEndIndex() + s.length());
        setValue(buffer);
        notifyRegisteredObservers();
    }

    /**
     * Removes the contents of the selection in the buffer
     */
    @Override
    public void delete() {
        deleteSelection();

        setValue(buffer);
        notifyRegisteredObservers();
    }

    @Override
    public void register(Observer<StringBuffer> o) throws IllegalArgumentException {
        Objects.requireNonNull(o, "o cannot be null");
        if (registeredObservers.contains(o)) {
            throw new IllegalArgumentException("o is registered already");
        }
        registeredObservers.add(o);
    }

    @Override
    public void unregister(Observer<StringBuffer> o) throws IllegalArgumentException {
        Objects.requireNonNull(o, "o cannot be null");
        if (!registeredObservers.contains(o)) {
            throw new IllegalArgumentException("o is not registered");
        }
        registeredObservers.remove(o);

    }

    @Override
    public boolean isRegistered(Observer<StringBuffer> o) {
        Objects.requireNonNull(o, "o cannot be null");

        return registeredObservers.contains(o);

    }

    @Override
    public StringBuffer getValue() {
        return buffer;
    }

    @Override
    public void setValue(StringBuffer v) {
        buffer = v;
    }


    private void deleteSelection() {
        buffer.delete(selection.getBeginIndex(), selection.getEndIndex());
        selection.setBeginIndex(selection.getBeginIndex());
        selection.setEndIndex(selection.getBeginIndex());
    }

    private void notifyRegisteredObservers() {
        for (Observer<StringBuffer> o : registeredObservers) {
            o.doUpdate(this);
        }
    }
}
