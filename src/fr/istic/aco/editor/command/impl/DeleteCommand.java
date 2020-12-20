package fr.istic.aco.editor.command.impl;

import fr.istic.aco.editor.Observer.Observer;
import fr.istic.aco.editor.Observer.Subject;
import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.memento.contract.Memento;
import fr.istic.aco.editor.receiver.contract.Engine;
import fr.istic.aco.editor.receiver.contract.Recorder;
import fr.istic.aco.editor.receiver.contract.Selection;
import fr.istic.aco.editor.receiver.impl.UndoManager;

import java.util.Objects;
import java.util.Optional;

/**
 * Concrete command of delete action
 *
 * @see Command
 */
public class DeleteCommand implements Command, Observer<Memento> {
    private final Engine receiver;
    private final Recorder recorder;
    private final UndoManager undoManager;
    private String deletedText;

    /**
     * Main constructor
     * @param receiver of the command
     * @param recorder saves the command
     * @param undoManager undo or redo the command
     */
    public DeleteCommand(Engine receiver, Recorder recorder, UndoManager undoManager) {
        this.undoManager = undoManager;
        Objects.requireNonNull(receiver);
        Objects.requireNonNull(recorder);
        Objects.requireNonNull(undoManager);
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
            Selection selection = receiver.getSelection();
            deletedText = receiver.getBufferContents().substring(selection.getBeginIndex(), selection.getEndIndex());
            receiver.delete();
            if (recorder.isRecording()) {
                recorder.save(this);
            }

            undoManager.save(this);
        } else {
            receiver.delete();
        }

    }

    @Override
    public Optional<Memento> getMemento() {
        DeleteMemento memento = new DeleteMemento();
        memento.setText(deletedText);
        return Optional.of(memento);
    }

    @Override
    public void setMemento(Memento memento) {
        deletedText = ((DeleteMemento) memento).getText();
    }

    @Override
    public void doUpdate(Subject<Memento> s) {
        if (s.getValue() instanceof DeleteMemento) {
            undo(s);
        }
    }

    private void undo(Subject<Memento> s) {
        receiver.insert(((DeleteMemento)s.getValue()).getText());
    }
    private static class DeleteMemento implements Memento {
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
