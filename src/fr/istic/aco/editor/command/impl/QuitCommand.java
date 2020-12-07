package fr.istic.aco.editor.command.impl;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.invoker.contract.Invoker;
import fr.istic.aco.editor.memento.contract.Memento;

import java.io.PrintStream;

/**
 * @author amad & romaric
 */
public class QuitCommand implements Command {
    private final Invoker receiver;
    private final PrintStream output;

    public QuitCommand(Invoker receiver, PrintStream output) {
        this.receiver = receiver;
        this.output = output;
    }
    @Override
    public void execute() {
        receiver.stopLoop();
    }

    @Override
    public Memento getMemento() {
        return null;
    }

    @Override
    public void setMemento(Memento memento) {

    }
}
