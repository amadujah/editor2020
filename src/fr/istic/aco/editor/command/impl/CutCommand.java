package fr.istic.aco.editor.command.impl;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.memento.contract.Memento;
import fr.istic.aco.editor.receiver.contract.Engine;
import fr.istic.aco.editor.receiver.contract.Recorder;

public class CutCommand implements Command {
    private final Engine receiver;
    private final Recorder recorder;

    public CutCommand(Engine receiver, Recorder recorder) {
        this.receiver = receiver;
        this.recorder = recorder;
    }

    @Override
    public void execute() {
        receiver.cutSelectedText();
        recorder.save(this);

/*        System.out.println("Buffer " + receiver.getBufferContents());
        System.out.println("Clipboard " + receiver.getClipboardContents());*/
    }

    @Override
    public Memento getMemento() {
        return null;
    }

    @Override
    public void setMemento(Memento memento) {

    }
}
