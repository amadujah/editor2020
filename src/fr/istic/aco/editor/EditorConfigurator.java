package fr.istic.aco.editor;

import fr.istic.aco.editor.command.impl.*;
import fr.istic.aco.editor.invoker.contract.Invoker;
import fr.istic.aco.editor.invoker.impl.InvokerImpl;
import fr.istic.aco.editor.receiver.contract.Engine;
import fr.istic.aco.editor.receiver.contract.Recorder;
import fr.istic.aco.editor.receiver.impl.EngineImpl;
import fr.istic.aco.editor.receiver.impl.RecorderImpl;

import java.io.PrintStream;

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
        PrintStream output = new PrintStream(System.out);
        invoker.addCommand("copy", new CopyCommand(receiver, recorder, output));
        invoker.addCommand("paste", new PasteCommand(receiver, recorder, output));
        invoker.addCommand("cut", new CutCommand(receiver, recorder, output));
        invoker.addCommand("delete", new DeleteCommand(receiver, recorder, output));
        invoker.addCommand("insert", new InsertCommand(receiver, invoker, recorder, output));
        invoker.addCommand("select", new SelectCommand(receiver, invoker, recorder, output));
        invoker.addCommand("start", new StartCommand(recorder, output));
        invoker.addCommand("stop", new StopCommand(recorder, output));
        invoker.addCommand("replay", new ReplayCommand(recorder, output));
        invoker.addCommand("quit", new QuitCommand(invoker, output));
    }
}
