package fr.istic.aco.editor.command.impl;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.receiver.contract.Engine;
import fr.istic.aco.editor.invoker.contract.Invoker;

public class InsertCommand implements Command {
    private final Engine receiver;
    private final Invoker invoker;

    public InsertCommand(Engine receiver, Invoker invoker) {
        this.receiver = receiver;
        this.invoker = invoker;
    }

    @Override
    public void execute() {
        receiver.insert(invoker.getText());
    }
}
