package fr.istic.aco.editor.command.contract;

import fr.istic.aco.editor.memento.contract.Originator;

/**
 * @author Amadou & Romaric
 * Defines a common interface for concrete commands.
 */
public interface Command extends Originator {
    /**
     * Calls an appropriate operation on an appropriate receiver.
     * 'Appropriates' are defined in concrete implementation of Command.
     */
    void execute();
}