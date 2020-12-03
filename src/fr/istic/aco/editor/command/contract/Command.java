package fr.istic.aco.editor.command.contract;
/**
 * Created by @amadou &
 */

import fr.istic.aco.editor.memento.contract.Memento;
import fr.istic.aco.editor.memento.contract.Originator;

/**
 * Defines a common interface for concrete commands.
 */
public interface Command extends Originator {
    /**
     * Calls an appropriate operation on an appropriate receiver.
     * 'Appropriates' are defined in concrete implementation of Command.
     */
    void execute();
}