package fr.istic.aco.editor.command.contract;
/**
 * Created by @amadou &
 */

/**
 * Defines a common interface for concrete commands.
 */
public interface Command {
    /**
     * Calls an appropriate operation on an appropriate receiver.
     * 'Appropriates' are defined in concrete implementation of Command.
     */
    public void execute();
}