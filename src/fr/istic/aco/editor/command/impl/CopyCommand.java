package fr.istic.aco.editor.command.impl;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.memento.contract.Memento;
import fr.istic.aco.editor.receiver.contract.Engine;
import fr.istic.aco.editor.receiver.contract.Recorder;

import java.util.Objects;

public class CopyCommand implements Command {
    private final Engine receiver;
    private final Recorder recorder;

    public CopyCommand(Engine receiver, Recorder recorder) {
        Objects.requireNonNull(receiver);
        this.receiver = receiver;
        this.recorder = recorder;
    }

    @Override
    public void execute() {
        receiver.copySelectedText();
        recorder.save(this);
        //System.out.println(receiver.getClipboardContents());
    }

    @Override
    public Memento getMemento() {
        return null;
    }

    @Override
    public void setMemento(Memento memento) {

    }
}
