package fr.istic.aco.editor.receiver.impl;

import fr.istic.aco.editor.receiver.contract.Engine;
import fr.istic.aco.editor.receiver.contract.Selection;

public class EngineImpl implements Engine {
    private final StringBuffer buffer;
    private final Selection selection;
    private String clipboardContent;

    public EngineImpl() {
        this.buffer = new StringBuffer();
        this.selection = new SelectionImpl(buffer);
        this.clipboardContent = "";
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
       buffer.delete(selection.getBeginIndex(), selection.getEndIndex());
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
        delete();
        //on insère le texte à partir du début de la sélection
        buffer.insert(selection.getBeginIndex(), s);
        //on deplace le curseur
        selection.setBeginIndex(selection.getBeginIndex() + s.length());
        selection.setEndIndex(selection.getEndIndex() + s.length());
    }

    /**
     * Removes the contents of the selection in the buffer
     */
    @Override
    public void delete() {
        buffer.delete(selection.getBeginIndex(), selection.getEndIndex());
        selection.setBeginIndex(selection.getBeginIndex());
        selection.setEndIndex(selection.getBeginIndex());
    }
}
