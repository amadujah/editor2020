package fr.istic.aco.editor.command.impl;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.receiver.contract.Engine;

import java.util.Objects;

public class DeleteCommand implements Command {
    Engine receiver;

    public DeleteCommand(Engine receiver) {
        Objects.requireNonNull(receiver);
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.delete();
        System.out.println(receiver.getBufferContents());
    }
}
