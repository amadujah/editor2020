package fr.istic.aco.editor.command.impl;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.receiver.contract.Engine;

import java.util.Objects;

public class CutCommand implements Command {
    private final Engine receiver;

    public CutCommand(Engine receiver) {
        Objects.requireNonNull(receiver);
        this.receiver = receiver;
    }

    @Override
    public void execute() {
        receiver.cutSelectedText();
        System.out.println("Buffer " + receiver.getBufferContents());
        System.out.println("Clipboard " + receiver.getClipboardContents());
    }
}
