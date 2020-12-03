package fr.istic.aco.editor.command.impl;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.receiver.contract.Engine;
import fr.istic.aco.editor.invoker.contract.Invoker;

import java.util.Objects;

public class InsertCommand implements Command {
    private final Engine receiver;
    private final Invoker invoker;

    public InsertCommand(Engine receiver, Invoker invoker) {
        Objects.requireNonNull(receiver);
        Objects.requireNonNull(invoker);
        this.receiver = receiver;
        this.invoker = invoker;
    }

    @Override
    public void execute() {
        receiver.insert(invoker.getText());
        System.out.println(receiver.getBufferContents());
    }
}
