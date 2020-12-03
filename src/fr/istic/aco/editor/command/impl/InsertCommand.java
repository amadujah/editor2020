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
    private Memento memento;

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
        receiver.insert(invoker.getText());
        recorder.save(this);
        new InsertMemento().setText(invoker.getText());

        //System.out.println(receiver.getBufferContents());
    }

    public Memento getMemento() {
        return memento;
    }

    public void setMemento(Memento memento) {
        this.memento = memento;
    }

    private static class InsertMemento {
        private String text;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }
}
