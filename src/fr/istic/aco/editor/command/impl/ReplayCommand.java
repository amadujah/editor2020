package fr.istic.aco.editor.command.impl;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.memento.contract.Memento;
import fr.istic.aco.editor.receiver.contract.Recorder;

import java.io.PrintStream;
import java.util.Objects;

/**
 * Concrete command of replay action
 */
public class ReplayCommand implements Command {
    private final Recorder recorder;
    private final PrintStream output;

    public ReplayCommand(Recorder recorder, PrintStream output) {
        this.output = output;
        Objects.requireNonNull(recorder, "Recorder cannot be null");
        this.recorder = recorder;
    }

    /**
     * Replay all the recorded commands
     */
    @Override
    public void execute() {
        if (!recorder.isRecording()) {
            this.recorder.replay();
        } else {
            output.println("Il faut arrêter l'enregistrement pour pouvoir rejouer les actions");
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
