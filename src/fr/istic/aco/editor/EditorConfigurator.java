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
        System.out.println("*** Tapez les commandes suivantes : ***");
        System.out.println("Insert pour insérer du texte");
        System.out.println("Select pour sélectionner un contenu");
        System.out.println("Copy pour copier le texte selectionné");
        System.out.println("Cut pour couper le texte selectionné");
        System.out.println("Paste pour coller le contenu du texte");
        System.out.println("Start pour démarrer l'enregistrement");
        System.out.println("Stop pour arrêter l'enregistrement");
        System.out.println("Replay pour rejouer les actions enregistrées");
        client.run();
    }

    private void run() {
        invoker = new InvokerImpl();
        receiver = new EngineImpl();
        recorder = new RecorderImpl();
        invoker.setReadStream(System.in);
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
        invoker.addCommand("Start", new StartCommand(recorder));
        invoker.addCommand("Stop", new StopCommand(recorder));
        invoker.addCommand("Replay", new ReplayCommand(recorder));
    }
}
