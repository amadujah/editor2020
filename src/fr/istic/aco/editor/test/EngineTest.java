package fr.istic.aco.editor.test;

import fr.istic.aco.editor.receiver.contract.Engine;
import fr.istic.aco.editor.receiver.contract.Selection;
import fr.istic.aco.editor.receiver.impl.EngineImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EngineTest {

    private Engine engine;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        engine = new EngineImpl();
    }

    private void todo() {
        fail("Unimplemented test");
    }

    @Test
    @DisplayName("Buffer must be empty after initialisation")
    void getSelection() {
        Selection selection = engine.getSelection();
        assertEquals(selection.getBufferBeginIndex(), selection.getBeginIndex());
        assertEquals("", engine.getBufferContents());
    }

    @Test
    void getBufferContents() {
        assertEquals("", engine.getBufferContents(), "The buffer content is empty");
        var sentence = "Insert this to the buffer";
        engine.insert(sentence);
        assertEquals(sentence, engine.getBufferContents());
    }

    @Test
    void getClipboardContents() {
        var word = "Copy this to clip";
        engine.insert(word);
        Selection selection = engine.getSelection();
        selection.setBeginIndex(0);
        selection.setEndIndex(4);
        engine.copySelectedText();
        assertEquals("Copy", engine.getClipboardContents());
        assertNotEquals("this", engine.getClipboardContents());
    }

    @Test
    void cutSelectedText() {
        var word = "Copy this to clip";
        engine.insert(word);
        var bufferInitialLength = word.length();
        Selection selection = engine.getSelection();
        selection.setBeginIndex(0);
        selection.setEndIndex(4);
        engine.cutSelectedText();
        assertEquals("Copy", engine.getClipboardContents());
        assertEquals(" this to clip", engine.getBufferContents());
        assertEquals(engine.getBufferContents().length(), bufferInitialLength - 4);
        selection.setEndIndex(engine.getBufferContents().length());
        engine.cutSelectedText();
        assertEquals("", engine.getBufferContents());
    }

    @Test
    void copySelectedText() {
        var word = "Copy this to clip";
        engine.insert(word);
        Selection selection = engine.getSelection();
        selection.setBeginIndex(0);
        selection.setEndIndex(4);
        engine.copySelectedText();
        assertEquals("Copy", engine.getClipboardContents());
        assertEquals(word, engine.getBufferContents());
        selection.setEndIndex(engine.getBufferContents().length());
        engine.cutSelectedText();
        assertEquals("", engine.getBufferContents());
    }

    @Test
    void pasteClipboard() {
        var word = "Copy this to clip";
        engine.insert(word);
        Selection selection = engine.getSelection();
        selection.setBeginIndex(0);
        selection.setEndIndex(4);
        engine.copySelectedText();
        selection.setBeginIndex(selection.getBufferEndIndex());
        selection.setEndIndex(selection.getBufferEndIndex());
        engine.pasteClipboard();

        assertEquals(word +"Copy", engine.getBufferContents());
    }

    @Test
    void insert() {

    }
}
