package fr.istic.aco.editor;

import fr.istic.aco.editor.command.impl.*;
import fr.istic.aco.editor.invoker.contract.Invoker;
import fr.istic.aco.editor.invoker.impl.InvokerImpl;
import fr.istic.aco.editor.receiver.contract.Engine;
import fr.istic.aco.editor.receiver.contract.Recorder;
import fr.istic.aco.editor.receiver.impl.EngineImpl;
import fr.istic.aco.editor.receiver.impl.RecorderImpl;

public class EditorConfigurator {
    private Invoker invoker;
    private Engine receiver;
    private Recorder recorder;

    public static void main(String[] lineArgs) {

        EditorConfigurator client = new EditorConfigurator();
        client.run();

    }

    private void run() {
        invoker = new InvokerImpl();
        receiver = new EngineImpl();
        recorder = new RecorderImpl();
        invoker.setReadStream(System.in);
        //invoker.setText("");
        configureCommands();
        invoker.runInvokerLoop();
    }

    private void configureCommands() {
        invoker.addCommand("Copy", new CopyCommand(receiver, recorder));
        invoker.addCommand("Paste", new PasteCommand(receiver, recorder));
        invoker.addCommand("Cut", new CutCommand(receiver, recorder));
        invoker.addCommand("Delete", new DeleteCommand(receiver, recorder));
        invoker.addCommand("Insert", new InsertCommand(receiver, invoker, recorder));
        invoker.addCommand("Select", new SelectCommand(receiver, invoker, recorder));
    }
}
