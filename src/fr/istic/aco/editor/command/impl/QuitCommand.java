package fr.istic.aco.editor.command.impl;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.invoker.contract.Invoker;
import fr.istic.aco.editor.memento.contract.Memento;

import java.util.Optional;

/**
 * Concrete command of quit action
 */
public class QuitCommand implements Command {
    private final Invoker receiver;

    /**
     * Main contructor
     * @param receiver of the command
     */
    public QuitCommand(Invoker receiver) {
        this.receiver = receiver;
    }
    @Override
    public void execute() {
        receiver.stopLoop();
    }

    @Override
    public Optional<Memento> getMemento() {
        return Optional.empty();
    }

    @Override
    public void setMemento(Memento memento) {

    }
}
