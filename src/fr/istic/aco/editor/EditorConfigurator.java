package fr.istic.aco.editor;

import fr.istic.aco.editor.command.impl.*;
import fr.istic.aco.editor.invoker.contract.Invoker;
import fr.istic.aco.editor.invoker.impl.InvokerImpl;
import fr.istic.aco.editor.receiver.contract.Engine;
import fr.istic.aco.editor.receiver.contract.Recorder;
import fr.istic.aco.editor.receiver.impl.BufferChange;
import fr.istic.aco.editor.receiver.impl.EngineImpl;
import fr.istic.aco.editor.receiver.impl.RecorderImpl;
import fr.istic.aco.editor.receiver.impl.UndoManager;

import java.io.PrintStream;

public class EditorConfigurator {
    private Invoker invoker;
    private Engine receiver;
    private Recorder recorder;
    private UndoManager undoManager;
    private static PrintStream output;

    public static void main(String[] lineArgs) {

        output = new PrintStream(System.out);
        EditorConfigurator client = new EditorConfigurator();
        output.println("*** Tapez les commandes suivantes : ***");
        output.println("*Insert* pour insérer du texte");
        output.println("*Select* pour sélectionner un contenu");
        output.println("*Copy* pour copier le texte selectionné");
        output.println("*Cut* pour couper le texte selectionné");
        output.println("*Paste* pour coller le contenu du texte");
        output.println("*Start* pour démarrer l'enregistrement");
        output.println("*Stop* pour arrêter l'enregistrement");
        output.println("*Undo* pour défaire la dernière action");
        output.println("*Redo* pour refaire la dernière action annulée");
        output.println("*Replay* pour rejouer les actions enregistrées");
        client.run();
    }

    private void run() {
        invoker = new InvokerImpl();
        receiver = new EngineImpl();
        recorder = new RecorderImpl();
        undoManager = new UndoManager();
        invoker.setReadStream(System.in);
        configureCommands();
        invoker.runInvokerLoop();

    }

    private void configureCommands() {
        DeleteCommand deleteCommand = new DeleteCommand(receiver, recorder, undoManager);
        InsertCommand insertCommand = new InsertCommand(receiver, invoker, recorder, undoManager);

        invoker.addCommand("copy", new CopyCommand(receiver, recorder, output));
        invoker.addCommand("paste", new PasteCommand(receiver, recorder));
        invoker.addCommand("cut", new CutCommand(receiver, recorder, output));
        invoker.addCommand("delete", deleteCommand);
        invoker.addCommand("insert", insertCommand);
        invoker.addCommand("select", new SelectCommand(receiver, invoker, recorder));
        invoker.addCommand("start", new StartCommand(recorder));
        invoker.addCommand("stop", new StopCommand(recorder));
        invoker.addCommand("replay", new ReplayCommand(recorder, output));
        invoker.addCommand("quit", new QuitCommand(invoker));
        invoker.addCommand("undo", new UndoCommand(undoManager));
        invoker.addCommand("redo", new RedoCommand(undoManager));

        undoManager.register(insertCommand);
        undoManager.register(deleteCommand);

        receiver.register(new BufferChange());
    }
}
