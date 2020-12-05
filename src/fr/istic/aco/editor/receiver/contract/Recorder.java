package fr.istic.aco.editor.receiver.contract;

import fr.istic.aco.editor.command.contract.Command;

/**
 * Provides access to record operations
 * @author Amadou & Romaric
 */
public interface Recorder {
    /**
     * Save the command
     * @param command the saved command
     */
    void save(Command command);

    /**
     * Start the recording
     */
    void start();

    /**
     * Stop the recording
     */
    void stop();

    /**
     * Replay the recorded commands
     */
    void replay();

    /**
     * Check if we are in a replaying mode
     * @return true if it's replay mode and false if it's not
     */
    boolean isReplaying();

    /**
     * Check if we are in a recording mode
     * @return true if it's recording mode and false if it's not
     */
    boolean isRecording();
}
