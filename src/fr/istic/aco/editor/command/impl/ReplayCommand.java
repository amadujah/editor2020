package fr.istic.aco.editor.command.impl;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.memento.contract.Memento;
import fr.istic.aco.editor.receiver.contract.Recorder;

/**
 * Concrete command of replay action
 */
public class ReplayCommand implements Command {
    private final Recorder receiver;

    public ReplayCommand(Recorder receiver) {
        this.receiver = receiver;
    }

    /**
     * Replay all the recorded commands
     */
    @Override
    public void execute() {
        this.receiver.replay();
    }

    @Override
    public Memento getMemento() {
        return null;
    }

    @Override
    public void setMemento(Memento memento) {

    }
}
