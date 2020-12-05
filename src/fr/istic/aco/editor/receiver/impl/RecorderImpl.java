package fr.istic.aco.editor.receiver.impl;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.memento.contract.Memento;
import fr.istic.aco.editor.receiver.contract.Recorder;
import fr.istic.aco.editor.utils.Pair;

import java.util.ArrayList;
import java.util.List;

public class RecorderImpl implements Recorder {
    private final List<Pair<Command, Memento>> pairs;
    private boolean isReplaying = false;
    private boolean isRecording = false;

    public RecorderImpl() {
        pairs = new ArrayList<>();
    }

    /**
     * Save the command for replaying it after
     * @param command
     */
    @Override
    public void save(Command command) {
        Memento memento = command.getMemento();
        pairs.add(Pair.of(command, memento));
    }

    @Override
    public void start() {
        isRecording = true;
    }


    @Override
    public void stop() {
        isRecording = false;
    }

    /**
     * Browse all the recorded commands and replay them
     */
    @Override
    public void replay() {
        isReplaying = true;
        for (Pair<Command, Memento> pair : pairs) {
            Command command = pair.getKey();
            Memento memento = pair.getValue();
            command.setMemento(memento);
            command.execute();
        }
        isReplaying = false;
    }

    @Override
    public boolean isReplaying() {
        return isReplaying;
    }

    @Override
    public boolean isRecording() {
        return isRecording;
    }
}
