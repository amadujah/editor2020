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

    void todo() {
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
    void cutSelectedText1() {
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
    }

    @Test
    void cutSelectedText2() {
        var word = "Copy this to clip";
        engine.insert(word);
        Selection selection = engine.getSelection();
        selection.setBeginIndex(0);
        selection.setEndIndex(engine.getBufferContents().length());
        engine.cutSelectedText();
        assertEquals("", engine.getBufferContents());
    }

    @Test
    void copySelectedText1() {
        var word = "Copy this to clip";
        engine.insert(word);
        Selection selection = engine.getSelection();
        selection.setBeginIndex(0);
        selection.setEndIndex(4);
        engine.copySelectedText();
        assertEquals("Copy", engine.getClipboardContents());
        assertEquals(word, engine.getBufferContents());
    }

    @Test
    void copySelectedText2() {
        var word = "Copy this to clip";
        engine.insert(word);
        Selection selection = engine.getSelection();
        selection.setBeginIndex(0);
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

        assertEquals(word + "Copy", engine.getBufferContents());
    }

    @Test
    void insert0() {
        String word = ""; //insert empty content
        engine.insert(word);
        assertEquals("", engine.getBufferContents());
    }

    @Test
    void insert1() {
        String word = "Buffer content";
        engine.insert(word);
        assertEquals(word, engine.getBufferContents());
    }

    @Test
    void insert2() {
        String word = "Buffer content";
        engine.insert(word);
        Selection selection = engine.getSelection();
        selection.setBeginIndex(selection.getBufferBeginIndex());
        selection.setEndIndex(selection.getBufferBeginIndex());
        engine.insert("hello");
        assertEquals("hello" + word, engine.getBufferContents());
    }

    @Test
    void insert3() {
        String word = "Buffer content";
        engine.insert(word);
        Selection selection = engine.getSelection();
        selection.setBeginIndex(selection.getBufferEndIndex());
        selection.setEndIndex(selection.getBufferEndIndex());
        engine.insert("hello");
        assertEquals(word + "hello", engine.getBufferContents());
    }

    @Test
    void insert4() {
        String word = "Buffer content";
        engine.insert(word);
        Selection selection = engine.getSelection();
        selection.setBeginIndex(6);
        selection.setEndIndex(7);
        engine.insert("machin");
        assertEquals("Buffermachincontent", engine.getBufferContents());
    }

    @Test
    void insert5() {
        String word = "Buffer content";
        engine.insert(word);
        Selection selection = engine.getSelection();
        selection.setBeginIndex(0);
        selection.setEndIndex(word.length());
        engine.insert("");
        assertEquals("", engine.getBufferContents());
        assertEquals(0, selection.getBufferEndIndex());
        assertEquals(0, engine.getBufferContents().length());
    }

    @Test
    void delete1() {
        String word = "Buffer content";
        engine.insert(word);
        engine.delete();
        assertEquals(word, engine.getBufferContents());
    }

    @Test
    void delete2() {
        String word = "Buffer content";
        engine.insert(word);
        Selection selection = engine.getSelection();
        selection.setBeginIndex(0);
        selection.setEndIndex(7);
        engine.delete();
        assertEquals("content", engine.getBufferContents());
    }

    @Test
    void delete3() {
        String word = "Buffer content";
        engine.insert(word);
        Selection selection = engine.getSelection();
        selection.setBeginIndex(0);
        selection.setEndIndex(selection.getBufferEndIndex());
        engine.delete();
        assertEquals("", engine.getBufferContents());
    }

    @Test
    void delete4() {
        String word = "Buffer content";
        engine.insert(word);
        Selection selection = engine.getSelection();
        selection.setBeginIndex(0);
        selection.setEndIndex(selection.getBufferEndIndex());
        engine.delete();
        assertEquals(engine.getBufferContents().length(), 0);
    }

    @Test
    @DisplayName("Begin and end must be 0 after initialization")
    void beginAndEndMustBeZeroAfterInitialization() {
        todo();
    }

    @Test
    @DisplayName("Buffer length must equal to ")
    void bufferLengthMustBeEqualBufferEndIndex() {
        todo();
    }

    @Test
    void beginIndexMustNotBeNegative() {
        Selection selection = engine.getSelection();
        Throwable t = assertThrows(IndexOutOfBoundsException.class, () -> selection.setBeginIndex(-2), "The index");
        assertEquals("beginIndex is out of bounds", t.getMessage());
        assertThrows(IndexOutOfBoundsException.class, () -> selection.setBeginIndex(1), "beginIndex must be lower than endIndex");
    }

    @Test
    void endIndexMustNotBeLowerThanBeginIndex() {
        Selection selection = engine.getSelection();
        Throwable t = assertThrows(IndexOutOfBoundsException.class, () -> {
            selection.setBeginIndex(2);
            selection.setEndIndex(1);
        });

        //     assertEquals("endIndex is out of bounds", t.getMessage());
    }

    @Test
    @DisplayName("endIndex cannot be grater than bufferEndIndex")
    void selectionEndIndex() {
        Selection selection = engine.getSelection();
        assertThrows(IndexOutOfBoundsException.class, () -> {
            selection.setBeginIndex(0);
            selection.setEndIndex(1);
        });
    }
}
