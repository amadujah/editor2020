package fr.istic.aco.editor.command.impl;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.memento.contract.Memento;
import fr.istic.aco.editor.receiver.contract.Recorder;

import java.io.PrintStream;
import java.util.Objects;

public class StopCommand implements Command {
    private final Recorder recorder;
    private final PrintStream output;

    public StopCommand(Recorder recorder, PrintStream output) {
        this.output = output;
        Objects.requireNonNull(recorder);
        this.recorder = recorder;
    }

    @Override
    public void execute() {
        if (recorder.isRecording()) {
            recorder.stop();
        }
    }

    @Override
    public Memento getMemento() {
        return null;
    }

    @Override
    public void setMemento(Memento memento) {

    }
}
