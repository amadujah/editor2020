package fr.istic.aco.editor.command.impl;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.memento.contract.Memento;
import fr.istic.aco.editor.receiver.contract.Engine;
import fr.istic.aco.editor.receiver.contract.Recorder;

import java.util.Objects;

public class PasteCommand implements Command {
    private final Engine receiver;
    private final Recorder recorder;

    public PasteCommand(Engine receiver, Recorder recorder) {
        Objects.requireNonNull(receiver);
        Objects.requireNonNull(recorder);
        this.receiver = receiver;
        this.recorder = recorder;
    }

    @Override
    public void execute() {
        if (!recorder.isReplaying()) {
            receiver.pasteClipboard();
            if (recorder.isRecording()) {
                recorder.save(this);
            }
        } else {
            receiver.pasteClipboard();
        }

        System.out.println("Contenu du buffer : " + receiver.getBufferContents());
    }

    @Override
    public Memento getMemento() {
        return null;
    }

    @Override
    public void setMemento(Memento memento) {

    }
}
