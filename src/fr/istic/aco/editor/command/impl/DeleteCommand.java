package fr.istic.aco.editor.command.impl;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.receiver.contract.Engine;

public class DeleteCommand implements Command {
    Engine receiver;

    public DeleteCommand(Engine receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.delete();
        System.out.println(receiver.getBufferContents());
    }
}
