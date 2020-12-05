package fr.istic.aco.editor.command.impl;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.memento.contract.Memento;
import fr.istic.aco.editor.receiver.contract.Recorder;

import java.util.Objects;

public class StopCommand implements Command {
    private final Recorder recorder;

    public StopCommand(Recorder recorder) {
        Objects.requireNonNull(recorder);
        this.recorder = recorder;
    }

    @Override
    public void execute() {
        recorder.stop();
    }

    @Override
    public Memento getMemento() {
        return null;
    }

    @Override
    public void setMemento(Memento memento) {

    }
}
