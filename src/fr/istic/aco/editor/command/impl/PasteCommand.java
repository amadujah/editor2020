package fr.istic.aco.editor.command.impl;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.receiver.contract.Engine;

public class PasteCommand implements Command {
    private final Engine receiver;

    public PasteCommand(Engine receiver) {
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.pasteClipboard();
    }
}
