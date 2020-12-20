package fr.istic.aco.editor.receiver.impl;

import fr.istic.aco.editor.Observer.Observer;
import fr.istic.aco.editor.Observer.Subject;
import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.memento.contract.Memento;
import fr.istic.aco.editor.utils.Pair;

import java.util.*;

/**
 * Handles undo and redo actions
 */
public class UndoManager implements Subject<Memento> {
    /**
     * list of observers
     */
    private final List<Observer<Memento>> registeredObservers;
    /**
     * Stock the commands to undo
     */
    private final Stack<Pair<Command, Optional<Memento>>> undoCommands;
    /**
     * Stock the undone commands to redo
     */
    private final Stack<Pair<Command, Optional<Memento>>> redoCommands;
    /**
     * The current memento
     */
    private Memento memento;
    private boolean isRedo = false;

    /**
     * Main constructor
     */
    public UndoManager() {
        undoCommands = new Stack<>();
        registeredObservers = new ArrayList<>();
        redoCommands = new Stack<>();
    }

    /**
     * Save command for later undo action
     *
     * @param command to save
     */
    public void save(Command command) {
        Optional<Memento> memento = command.getMemento();
        undoCommands.push(Pair.of(command, memento));
    }

    /**
     * Undo last action
     * Save the undone action for later redo
     * Notify observers
     */
    public void undo() {
        if (!undoCommands.isEmpty()) {
            Pair<Command, Optional<Memento>> pair = undoCommands.pop();
            redoCommands.push(pair);
            Command command = pair.getKey();
            Optional<Memento> memento = pair.getValue();
            if (memento.isPresent()) {
                command.setMemento(memento.get());
                setValue(memento.get());
            }
            notifyRegisteredObservers();
        }
    }

    /**
     * Redo last undone action
     */
    public void redo() {
        isRedo = true;
        if (!redoCommands.isEmpty()) {
            Pair<Command, Optional<Memento>> pair = redoCommands.pop();
            Command command = pair.getKey();
            Optional<Memento> memento = pair.getValue();
            memento.ifPresent(command::setMemento);
            command.execute();
        }
        isRedo = false;
    }

    @Override
    public void register(Observer<Memento> o) throws IllegalArgumentException {
        Objects.requireNonNull(o, "o cannot be null");
        if (registeredObservers.contains(o)) {
            throw new IllegalArgumentException("o is registered already");
        }
        registeredObservers.add(o);
    }

    @Override
    public void unregister(Observer<Memento> o) throws IllegalArgumentException {
        Objects.requireNonNull(o, "o cannot be null");
        if (!registeredObservers.contains(o)) {
            throw new IllegalArgumentException("o is not registered");
        }
        registeredObservers.remove(o);
    }

    @Override
    public boolean isRegistered(Observer<Memento> o) {
        Objects.requireNonNull(o, "o cannot be null");

        return registeredObservers.contains(o);
    }

    @Override
    public Memento getValue() {
        return memento;
    }

    @Override
    public void setValue(Memento v) {
        memento = v;
    }

    private void notifyRegisteredObservers() {
        for (Observer<Memento> o : registeredObservers) {
            o.doUpdate(this);
        }
    }

    /**
     * @return true if it's a redo or undo action
     */
    public boolean isRedo() {
        return isRedo;
    }
}
