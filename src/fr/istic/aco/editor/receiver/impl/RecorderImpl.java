package fr.istic.aco.editor.receiver.impl;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.memento.contract.Memento;
import fr.istic.aco.editor.receiver.contract.Recorder;
import fr.istic.aco.editor.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class RecorderImpl implements Recorder {
    private final List<Pair<Command, Memento>> pairs;

    public RecorderImpl() {
        pairs = new ArrayList<>();
    }

    @Override
    public void save(Command command) {
        pairs.add(Pair.of(command, command.getMemento()));
    }

    @Override
    public void start() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void replay() {

    }
}
