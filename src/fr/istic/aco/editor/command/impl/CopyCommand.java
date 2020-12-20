package fr.istic.aco.editor.command.impl;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.memento.contract.Memento;
import fr.istic.aco.editor.receiver.contract.Engine;
import fr.istic.aco.editor.receiver.contract.Recorder;

import java.io.PrintStream;
import java.util.Objects;
import java.util.Optional;

/**
 * Concrete command of Copy action
 */
public class CopyCommand implements Command {
    private final Engine receiver;
    private final Recorder recorder;
    private final PrintStream output;

    /**
     * Main constructor
     * @param receiver of the command
     * @param recorder save command
     * @param output
     */
    public CopyCommand(Engine receiver, Recorder recorder, PrintStream output) {
        Objects.requireNonNull(receiver);
        Objects.requireNonNull(recorder);
        Objects.requireNonNull(output);
        this.receiver = receiver;
        this.recorder = recorder;
        this.output = output;
    }

    @Override
    public void execute() {
        if (!recorder.isReplaying()) {
            receiver.copySelectedText();
            if (recorder.isRecording()) {
                recorder.save(this);
            }
        } else {
            receiver.copySelectedText();
        }

        output.println("Contenu du presse papier : " + receiver.getClipboardContents());
    }

    @Override
    public Optional<Memento> getMemento() {
        return Optional.empty();
    }

    @Override
    public void setMemento(Memento memento) {
    }
}
