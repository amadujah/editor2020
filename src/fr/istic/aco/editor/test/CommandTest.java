package fr.istic.aco.editor.test;

import fr.istic.aco.editor.Observer.Observer;
import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.command.impl.*;
import fr.istic.aco.editor.invoker.contract.Invoker;
import fr.istic.aco.editor.invoker.impl.InvokerImpl;
import fr.istic.aco.editor.memento.contract.Memento;
import fr.istic.aco.editor.receiver.contract.Engine;
import fr.istic.aco.editor.receiver.contract.Recorder;
import fr.istic.aco.editor.receiver.contract.Selection;
import fr.istic.aco.editor.receiver.impl.EngineImpl;
import fr.istic.aco.editor.receiver.impl.RecorderImpl;
import fr.istic.aco.editor.receiver.impl.UndoManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class CommandTest {
    private Engine engine;
    private Invoker invoker;
    private Recorder recorder;
    private PrintStream output;
    private UndoManager undoManager;

    @BeforeEach
    void setup() {
        engine = new EngineImpl();
        invoker = new InvokerImpl();
        recorder = new RecorderImpl();
        output = new PrintStream(System.out);
        undoManager = new UndoManager();
    }
    @Test
    void copyCommand() {
        engine.insert("Hello World");
        Selection selection = engine.getSelection();
        selection.setBeginIndex(0);
        selection.setEndIndex(5);
        Command copy = new CopyCommand(engine, recorder, output);
        copy.execute();

        assertEquals(engine.getClipboardContents(), "Hello");
    }

    @Test
    void cutCommand() {
        engine.insert("Hello World");
        Selection selection = engine.getSelection();
        selection.setBeginIndex(0);
        selection.setEndIndex(5);
        Command cut = new CutCommand(engine, recorder, output);
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
        Command cut = new CutCommand(engine, recorder, output);
        cut.execute();

        selection.setBeginIndex(selection.getBufferEndIndex());
        selection.setEndIndex(selection.getBufferEndIndex());
        Command paste = new PasteCommand(engine, recorder);

        paste.execute();

        assertEquals(engine.getBufferContents(), " WorldHello");
    }

    @Test
    void insertCommand() {
        String mockInput = "Salut";
        InputStream mockReadStream = new ByteArrayInputStream(mockInput.getBytes());
        invoker.setReadStream(mockReadStream);
        Command insert = new InsertCommand(engine, invoker, recorder, undoManager);
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
        Command selection = new SelectCommand(engine, invoker, recorder);
        selection.execute();

        Command copy = new CopyCommand(engine, recorder, output);
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
        Command selection = new SelectCommand(engine, invoker, recorder);
        selection.execute();

        Command delete = new DeleteCommand(engine, recorder, undoManager);
        delete.execute();

        assertEquals(engine.getBufferContents(), "Salut");
    }

    @DisplayName("Buffer remain the same if delete without selection")
    @Test
    void deleteCommand1() {
        String content = "Salut tout le monde";
        engine.insert(content);

        Command delete = new DeleteCommand(engine, recorder, undoManager);
        delete.execute();
        assertEquals(engine.getBufferContents(), content);
    }

    @DisplayName("")
    @Test
    void replayCommand() {
        String mockInput = "Salut à tous";
        //start recording
        new StartCommand(recorder).execute();
        InputStream mockReadStream = new ByteArrayInputStream(mockInput.getBytes());
        invoker.setReadStream(mockReadStream);
        Command insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
        insertCommand.execute();
        //stop recording
        new StopCommand(recorder).execute();

        //Delete buffer content
        String selectIndex = 0 + System.lineSeparator() + mockInput.length();
        mockReadStream = new ByteArrayInputStream(selectIndex.getBytes());
        invoker.setReadStream(mockReadStream);
        Command selectCommand = new SelectCommand(engine, invoker, recorder);
        selectCommand.execute();

        Command deleteCommand = new DeleteCommand(engine, recorder, undoManager);
        deleteCommand.execute();

        //Replay insert command
        Command replayCommand = new ReplayCommand(recorder, output);
        replayCommand.execute();

        assertEquals(mockInput, engine.getBufferContents());
    }

    @Test
    void replaySelectCommand() {
        String mockInput = "Salut à tous";
        InputStream mockReadStream = new ByteArrayInputStream(mockInput.getBytes());
        invoker.setReadStream(mockReadStream);
        Command insertCommand = new InsertCommand(engine, invoker, recorder, undoManager);
        insertCommand.execute();

        //start recording
        String selectionIndexes = "0"+System.lineSeparator()+"5"+System.lineSeparator();
        new StartCommand(recorder).execute();
        mockReadStream = new ByteArrayInputStream(selectionIndexes.getBytes());

        invoker.setReadStream(mockReadStream);
        Command selectCommand = new SelectCommand(engine, invoker, recorder);
        selectCommand.execute();
        //stop recording
        new StopCommand(recorder).execute();
        //reinitialize selection
        engine.getSelection().setBeginIndex(0);
        engine.getSelection().setEndIndex(0);

        //Replay select command
        Command replayCommand = new ReplayCommand(recorder, output);
        replayCommand.execute();

        assertEquals(0, engine.getSelection().getBeginIndex());
        assertEquals(5, engine.getSelection().getEndIndex());
    }

    @Test
    void undoCommand() {
        String mockInput = "Salut à tous";
        InputStream mockReadStream = new ByteArrayInputStream(mockInput.getBytes());
        invoker.setReadStream(mockReadStream);
        Command insert = new InsertCommand(engine, invoker, recorder, undoManager);
        insert.execute();
        //undoCommand action
        Command undoCommand = new UndoCommand(undoManager);

        Observer<Memento> obs2 = mock(Observer.class);

        undoManager.register(obs2);
        undoCommand.execute();

        // Check that the updates were sent to observers
        verify(obs2).doUpdate(undoManager);
    }

    /**
     * Robustness test cases
     */
    @DisplayName("Null receiver on command throws NullPointerException")
    @Test
    void nullReceiverOnCommand() {
        assertThrows(NullPointerException.class, () -> new CopyCommand(null, recorder, output));
    }

    @DisplayName("Null invoker on command throws NullPointerException")
    @Test
    void nullInvokerOnCommand() {
        assertThrows(NullPointerException.class, () -> new InsertCommand(engine, null, recorder, undoManager));
    }

    @DisplayName("Null invoker and receiver on command throws NullPointerException")
    @Test
    void nullInvokerAndReceiverOnCommand() {
        assertThrows(NullPointerException.class, () -> new InsertCommand(null, null, recorder, undoManager));
    }
}
