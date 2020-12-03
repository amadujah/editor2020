package fr.istic.aco.editor.command.impl;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.invoker.contract.Invoker;
import fr.istic.aco.editor.receiver.contract.Engine;
import fr.istic.aco.editor.receiver.contract.Selection;

import java.util.Objects;

public class SelectCommand implements Command {
    Engine receiver;
    Invoker invoker;

    public SelectCommand(Engine receiver, Invoker invoker) {
        Objects.requireNonNull(receiver);
        Objects.requireNonNull(invoker);
        this.receiver = receiver;
        this.invoker = invoker;
    }

    @Override
    public void execute() {
        Selection selection = receiver.getSelection();
        selection.setBeginIndex(invoker.getBeginIndex());
        selection.setEndIndex(invoker.getEndIndex());
    }
}
