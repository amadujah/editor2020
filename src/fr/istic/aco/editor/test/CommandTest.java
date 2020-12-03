package fr.istic.aco.editor.test;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.command.impl.*;
import fr.istic.aco.editor.invoker.contract.Invoker;
import fr.istic.aco.editor.invoker.impl.InvokerImpl;
import fr.istic.aco.editor.receiver.contract.Engine;
import fr.istic.aco.editor.receiver.contract.Selection;
import fr.istic.aco.editor.receiver.impl.EngineImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class CommandTest {
    private Engine engine;
    private Invoker invoker;

    @BeforeEach
    void setup() {
        engine = new EngineImpl();
        invoker = new InvokerImpl();
    }
    @Test
    void copyCommand() {
        engine.insert("Hello World");
        Selection selection = engine.getSelection();
        selection.setBeginIndex(0);
        selection.setEndIndex(5);
        Command copy = new CopyCommand(engine);
        copy.execute();

        assertEquals(engine.getClipboardContents(), "Hello");
    }

    @Test
    void cutCommand() {
        engine.insert("Hello World");
        Selection selection = engine.getSelection();
        selection.setBeginIndex(0);
        selection.setEndIndex(5);
        Command cut = new CutCommand(engine);
        cut.execute();

        assertEquals(engine.getClipboardContents(), "Hello");
        assertEquals(engine.getBufferContents(), " World");
    }

    @Test
    void pastCommand() {
        engine.insert("Hello World");

        Selection selection = engine.getSelection();
        selection.setBeginIndex(0);
        selection.setEndIndex(5);
        Command cut = new CutCommand(engine);
        cut.execute();

        selection.setBeginIndex(selection.getBufferEndIndex());
        selection.setEndIndex(selection.getBufferEndIndex());
        Command paste = new PasteCommand(engine);

        paste.execute();

        assertEquals(engine.getBufferContents(), " WorldHello");
    }

    @Test
    void insertCommand() {
        String mockInput = "Salut";
        InputStream mockReadStream = new ByteArrayInputStream(mockInput.getBytes());
        invoker.setReadStream(mockReadStream);
        Command insert = new InsertCommand(engine, invoker);
        insert.execute();

        assertEquals(engine.getBufferContents(), mockInput);
    }

    @Test
    void selectionCommand() {
        String content = "Salut tout le monde";
        engine.insert(content);
        String mockInput = "0" + System.lineSeparator() + "5";
        InputStream mockReadStream = new ByteArrayInputStream(mockInput.getBytes());
        invoker.setReadStream(mockReadStream);
        Command selection = new SelectCommand(engine, invoker);
        selection.execute();

        Command copy = new CopyCommand(engine);
        copy.execute();

        assertEquals(engine.getClipboardContents(), "Salut");
    }

    /**
     * Suppression de " tout le monde" du buffer
     */
    @Test
    void deleteCommand() {
        String content = "Salut tout le monde";
        engine.insert(content);

        String mockInput = "5" + System.lineSeparator() + content.length();
        InputStream mockReadStream = new ByteArrayInputStream(mockInput.getBytes());
        invoker.setReadStream(mockReadStream);
        Command selection = new SelectCommand(engine, invoker);
        selection.execute();

        Command delete = new DeleteCommand(engine);
        delete.execute();

        assertEquals(engine.getBufferContents(), "Salut");
    }

    @DisplayName("Buffer remain the same if delete without selection")
    @Test
    void deleteCommand1() {
        String content = "Salut tout le monde";
        engine.insert(content);

        Command delete = new DeleteCommand(engine);
        delete.execute();

        assertEquals(engine.getBufferContents(), content);
    }

    @DisplayName("Null receiver on command throws NullPointerException")
    @Test
    void nullReceiverOnCommand() {
        assertThrows(NullPointerException.class, () -> new CopyCommand(null));
    }

    @DisplayName("Null invoker on command throws NullPointerException")
    @Test
    void nullInvokerOnCommand() {
        assertThrows(NullPointerException.class, () -> new InsertCommand(engine, null));
    }

    @DisplayName("Null invoker and receiver on command throws NullPointerException")
    @Test
    void nullInvokerAndReceiverOnCommand() {
        assertThrows(NullPointerException.class, () -> new InsertCommand(null, null));
    }
}
