package fr.istic.aco.editor.command.impl;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.receiver.contract.Engine;

public class CopyCommand implements Command {
    private final Engine receiver;

    public CopyCommand(Engine receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.copySelectedText(); // action
    }
}
