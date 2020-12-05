package fr.istic.aco.editor.invoker.contract;

import fr.istic.aco.editor.command.contract.Command;

import java.io.InputStream;

public interface Invoker {
    /**
     * Starts the reading of the read stream set by setReadStream operation
     */
    void runInvokerLoop();

    /**
     * Stops the read stream loop now.
     */
    void stopLoop();

    /**
     * Sets the read stream that be be used by runInvokerLoop
     *
     * @param inputStream the read stream
     * @throws IllegalArgumentException if inputStream is null
     */
    void setReadStream(InputStream inputStream);


    /**
     * Registers a new keyword/command pair
     *
     * @param keyword a non-null String
     * @param cmd     a non-null Command reference
     * @throws IllegalArgumentException for null parameters
     */
    void addCommand(String keyword, Command cmd);

    /**
     *
     * @return the text from UI
     */
    String getText();

    /**
     *
     * @return the begin index of the selection
     */
    int getBeginIndex();

    /**
     *
     * @return the end index of the selection
     */
    int getEndIndex();
}
