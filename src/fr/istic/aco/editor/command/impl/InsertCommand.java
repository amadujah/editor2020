package fr.istic.aco.editor.command.impl;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.memento.contract.Memento;
import fr.istic.aco.editor.receiver.contract.Engine;
import fr.istic.aco.editor.invoker.contract.Invoker;
import fr.istic.aco.editor.receiver.contract.Recorder;

import java.util.Objects;

public class InsertCommand implements Command {
    private final Engine receiver;
    private final Invoker invoker;
    private final Recorder recorder;
    private String insertText;

    public InsertCommand(Engine receiver, Invoker invoker, Recorder recorder) {
        Objects.requireNonNull(receiver);
        Objects.requireNonNull(invoker);
        Objects.requireNonNull(recorder);
        this.receiver = receiver;
        this.invoker = invoker;
        this.recorder = recorder;
    }

    @Override
    public void execute() {
        //Insert from UI
        if (!recorder.isReplaying()) {
            insertText = invoker.getText();
            receiver.insert(insertText);
            if (recorder.isRecording()) {
                recorder.save(this);
            }
        } else {
            //Insert from memento
            receiver.insert(insertText);
        }

        System.out.println(receiver.getBufferContents());
    }

    public Memento getMemento() {
        InsertMemento memento = new InsertMemento();
        memento.setText(insertText);
        return memento;
    }

    public void setMemento(Memento memento) {
        insertText = ((InsertMemento) memento).getText();
    }

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
