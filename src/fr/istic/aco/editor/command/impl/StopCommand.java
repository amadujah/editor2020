package fr.istic.aco.editor.command.impl;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.memento.contract.Memento;
import fr.istic.aco.editor.receiver.contract.Recorder;

import java.util.Objects;
import java.util.Optional;

public class StopCommand implements Command {
    private final Recorder recorder;

    /**
     * Main contructor
     * @param recorder receiver of the command
     */
    public StopCommand(Recorder recorder) {
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
    public Optional<Memento> getMemento() {
        return Optional.empty();
    }

    @Override
    public void setMemento(Memento memento) {

    }
}
