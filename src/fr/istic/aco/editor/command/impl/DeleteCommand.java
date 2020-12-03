package fr.istic.aco.editor.command.impl;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.memento.contract.Memento;
import fr.istic.aco.editor.receiver.contract.Engine;
import fr.istic.aco.editor.receiver.contract.Recorder;

/**
 * Delete action concrete command
 *
 * @see Command
 */
import java.util.Objects;

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
        receiver.delete();
        recorder.save(this);

        //System.out.println(receiver.getBufferContents());
    }

    @Override
    public Memento getMemento() {
        return null;
    }

    @Override
    public void setMemento(Memento memento) {

    }
}
