package fr.istic.aco.editor.command.impl;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.memento.contract.Memento;
import fr.istic.aco.editor.receiver.contract.Engine;
import fr.istic.aco.editor.receiver.contract.Recorder;

public class PasteCommand implements Command {
    private final Engine receiver;
    private final Recorder recorder;

    public PasteCommand(Engine receiver, Recorder recorder) {
        this.receiver = receiver;
        this.recorder = recorder;
    }

    @Override
    public void execute() {
        receiver.pasteClipboard();
        recorder.save(this);
        System.out.println(receiver.getBufferContents());
    }

    @Override
    public Memento getMemento() {
        return null;
    }

    @Override
    public void setMemento(Memento memento) {

    }
}
