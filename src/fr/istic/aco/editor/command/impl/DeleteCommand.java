package fr.istic.aco.editor.command.impl;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.memento.contract.Memento;
import fr.istic.aco.editor.receiver.contract.Engine;
import fr.istic.aco.editor.receiver.contract.Recorder;

import java.util.Objects;

/**
 * Concrete command of delete action
 *
 * @see Command
 */
public class DeleteCommand implements Command {
    private final Engine receiver;
    private final Recorder recorder;

    public DeleteCommand(Engine receiver, Recorder recorder) {
        Objects.requireNonNull(receiver);
        Objects.requireNonNull(recorder);
        this.receiver = receiver;
        this.recorder = recorder;
    }

    /**
     * Execute {@link Engine#delete()} method from engine
     * Then save action in recorder {@link Recorder#save(Command)}
     */
    @Override
    public void execute() {
        if (!recorder.isReplaying()) {
            receiver.delete();
            if (recorder.isRecording()) {
                recorder.save(this);
            }
        } else {
            receiver.delete();
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
