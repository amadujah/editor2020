package fr.istic.aco.editor;

import fr.istic.aco.editor.command.impl.*;
import fr.istic.aco.editor.invoker.contract.Invoker;
import fr.istic.aco.editor.invoker.impl.InvokerImpl;
import fr.istic.aco.editor.receiver.contract.Engine;
import fr.istic.aco.editor.receiver.impl.EngineImpl;

public class EditorConfigurator {
    private Invoker invoker;
    private Engine receiver;

    public static void main(String[] lineArgs) {


        System.out.println("*** Tapez les commandes suivantes : ***");
        System.out.println("*Insert* pour insérer du texte");
        System.out.println("*Select* pour sélectionner un contenu");
        System.out.println("*Copy* pour copier le texte selectionné");
        System.out.println("*Delete* pour supprimé le texte selectionné");
        System.out.println("*Cut* pour couper le texte selectionné");
        System.out.println("*Paste* pour coller le contenu du texte");
        EditorConfigurator client = new EditorConfigurator();
        client.run();

    }

    private void run() {
        invoker = new InvokerImpl();
        receiver = new EngineImpl();
        invoker.setReadStream(System.in);
        //invoker.setText("");
        configureCommands();
        invoker.runInvokerLoop();
    }

    private void configureCommands() {
        invoker.addCommand("Copy", new CopyCommand(receiver));
        invoker.addCommand("Paste", new PasteCommand(receiver));
        invoker.addCommand("Cut", new CutCommand(receiver));
        invoker.addCommand("Delete", new DeleteCommand(receiver));
        invoker.addCommand("Insert", new InsertCommand(receiver, invoker));
        invoker.addCommand("Select", new SelectCommand(receiver, invoker));
    }
}
