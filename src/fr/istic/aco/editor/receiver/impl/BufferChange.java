package fr.istic.aco.editor.receiver.impl;

import fr.istic.aco.editor.Observer.Observer;
import fr.istic.aco.editor.Observer.Subject;

import java.io.PrintStream;

/**
 * Observes buffer content changes then prints new buffer content
 */
public class BufferChange implements Observer<StringBuffer> {
    private final PrintStream out = new PrintStream(System.out);

    @Override
    public void doUpdate(Subject<StringBuffer> s) {
        out.println("Buffer content : " + s.getValue().toString());
    }
}
