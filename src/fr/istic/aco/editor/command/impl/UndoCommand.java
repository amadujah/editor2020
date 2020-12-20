package fr.istic.aco.editor.command.impl;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.memento.contract.Memento;
import fr.istic.aco.editor.receiver.impl.UndoManager;

import java.util.Optional;

public class UndoCommand implements Command {
    private final UndoManager undoManager;

    /**
     * Main constructor
     * @param undoManager contains the undo action
     */
    public UndoCommand(UndoManager undoManager) {
        this.undoManager = undoManager;
    }

    @Override
    public void execute() {
        undoManager.undo();
    }

    @Override
    public Optional<Memento> getMemento() {
        return Optional.empty();
    }

    @Override
    public void setMemento(Memento memento) {

    }
}
