package fr.istic.aco.editor.command.impl;

import fr.istic.aco.editor.Observer.Observer;
import fr.istic.aco.editor.Observer.Subject;
import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.memento.contract.Memento;
import fr.istic.aco.editor.receiver.contract.Engine;
import fr.istic.aco.editor.invoker.contract.Invoker;
import fr.istic.aco.editor.receiver.contract.Recorder;
import fr.istic.aco.editor.receiver.contract.Selection;
import fr.istic.aco.editor.receiver.impl.UndoManager;

import java.util.Objects;
import java.util.Optional;

/**
 * Concrete command of insert action
 */
public class InsertCommand implements Command, Observer<Memento> {
    private final Engine receiver;
    private final Invoker invoker;
    private final Recorder recorder;
    private String insertText;
    private final UndoManager undoManager;

    /**
     * Main constructor
     * @param receiver of the command
     * @param invoker provide infos from UI
     * @param recorder saves the command
     * @param undoManager undo or redo the command
     */
    public InsertCommand(Engine receiver, Invoker invoker, Recorder recorder, UndoManager undoManager) {
        this.undoManager = undoManager;
        Objects.requireNonNull(receiver);
        Objects.requireNonNull(invoker);
        Objects.requireNonNull(recorder);
        Objects.requireNonNull(undoManager);
        this.receiver = receiver;
        this.invoker = invoker;
        this.recorder = recorder;
    }

    @Override
    public void execute() {
        //Insert from UI
        if (!recorder.isReplaying() && !undoManager.isRedo()) {
            insertText = invoker.getText();
            receiver.insert(insertText);
            if (recorder.isRecording()) {
                recorder.save(this);
            }
            undoManager.save(this);
        } else {
            //Insert from memento
            receiver.insert(insertText);
        }

    }

    public Optional<Memento> getMemento() {
        InsertMemento memento = new InsertMemento();
        memento.setText(insertText);
        return Optional.of(memento);
    }

    public void setMemento(Memento memento) {
        insertText = ((InsertMemento) memento).getText();
    }

    /**
     * Undo action if get notified by the UndoManager
     * @param s the updated subject
     */
    @Override
    public void doUpdate(Subject<Memento> s) {
        if (s.getValue() instanceof InsertMemento) {
            undo(s);
        }
    }

    /**
     * Undo the last insert by removing text from buffer
     * @param subject the updated subject
     */
    private void undo(Subject<Memento> subject) {
        Selection s = this.receiver.getSelection();
        s.setBeginIndex(this.receiver.getBufferContents().indexOf(((InsertMemento) subject.getValue()).getText()));
        s.setEndIndex(s.getBeginIndex() + ((InsertMemento) subject.getValue()).getText().length());
        this.receiver.delete();
    }

    /**
     * Inner class for saving the state of the command
     */
    private static class InsertMemento implements Memento {
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
