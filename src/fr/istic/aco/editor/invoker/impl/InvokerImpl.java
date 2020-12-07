package fr.istic.aco.editor.invoker.impl;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.invoker.contract.Invoker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Objects;

public class InvokerImpl implements Invoker {
    private final HashMap<String, Command> commands = new HashMap<>();
    private boolean stopLoop = false;
    private BufferedReader bufferedReader;

    @Override
    public void runInvokerLoop() {
        while (!stopLoop) {
            String userInput = null;
            try {
                userInput = readUserInput();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (userInput == null) {
                stopLoop = true;
                break;
            }
            Command cmdToExecute = commands.get(userInput.toLowerCase());
            if (cmdToExecute != null) {
                cmdToExecute.execute();
            }
        }
    }

    @Override
    public void stopLoop() {
        stopLoop = true;
    }

    private String readUserInput() throws IOException {
        return bufferedReader.readLine();
    }

    /**
     * Registers a new keyword/command pair
     *
     * @param keyword a non-null String
     * @param cmd     a non-null Command reference
     * @throws IllegalArgumentException for null parameters
     */
    @Override
    public void addCommand(String keyword, Command cmd) {
        if ((keyword == null) || (cmd == null)) {
            throw new IllegalArgumentException("null parameter");
        }
        commands.put(keyword, cmd);
    }

    @Override
    public void setReadStream(InputStream inputStream) {
        if (inputStream == null) {
            throw new IllegalArgumentException("null inputStream");
        }
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    @Override
    public String getText() {
        String userInput = null;
        try {
            userInput = readUserInput();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userInput;
    }

    @Override
    public int getBeginIndex() {
        return getUserInput();
    }

    @Override
    public int getEndIndex() {
        return getUserInput();
    }

    private int getUserInput() {
        String userInput = null;
        try {
            userInput = readUserInput();
        } catch (IOException e) {
            e.printStackTrace();
        }

        int userInputInteger = -1;
        try {
            userInputInteger = Integer.parseInt(Objects.requireNonNull(userInput));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        return userInputInteger;
    }
}
