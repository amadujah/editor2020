package fr.istic.aco.editor.command.impl;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.invoker.contract.Invoker;
import fr.istic.aco.editor.memento.contract.Memento;
import fr.istic.aco.editor.receiver.contract.Engine;
import fr.istic.aco.editor.receiver.contract.Recorder;
import fr.istic.aco.editor.receiver.contract.Selection;

import java.util.Objects;

public class SelectCommand implements Command {
    private final Engine receiver;
    private final Invoker invoker;
    private final Recorder recorder;

    public SelectCommand(Engine receiver, Invoker invoker, Recorder recorder) {
        Objects.requireNonNull(receiver);
        Objects.requireNonNull(invoker);
        Objects.requireNonNull(recorder);
        this.receiver = receiver;
        this.invoker = invoker;
        this.recorder = recorder;
    }

    @Override
    public void execute() {
        Selection selection = receiver.getSelection();
        selection.setBeginIndex(invoker.getBeginIndex());
        selection.setEndIndex(invoker.getEndIndex());

        recorder.save(this);
    }

    @Override
    public Memento getMemento() {
        return null;
    }

    @Override
    public void setMemento(Memento memento) {

    }
}
