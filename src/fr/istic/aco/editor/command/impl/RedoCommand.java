package fr.istic.aco.editor.command.impl;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.memento.contract.Memento;
import fr.istic.aco.editor.receiver.impl.UndoManager;

import java.util.Optional;

/**
 * Redo undone action
 */
public class RedoCommand implements Command {
    private final UndoManager undoManager;

    /**
     * Main constructor
     * @param undoManager redo action
     */
    public RedoCommand(UndoManager undoManager) {
        this.undoManager = undoManager;
    }

    @Override
    public void execute() {
        undoManager.redo();
    }

    @Override
    public Optional<Memento> getMemento() {
        return Optional.empty();
    }

    @Override
    public void setMemento(Memento memento) {

    }
}
