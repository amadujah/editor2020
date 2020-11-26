package fr.istic.aco.editor.receiver.contract;

import fr.istic.aco.editor.command.contract.Command;

public interface Recorder {

    void save(Command command);
    void start();
    void stop();
    void replay();
}
