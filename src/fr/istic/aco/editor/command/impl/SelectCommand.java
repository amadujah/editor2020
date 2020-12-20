package fr.istic.aco.editor.command.impl;

import fr.istic.aco.editor.command.contract.Command;
import fr.istic.aco.editor.invoker.contract.Invoker;
import fr.istic.aco.editor.memento.contract.Memento;
import fr.istic.aco.editor.receiver.contract.Engine;
import fr.istic.aco.editor.receiver.contract.Recorder;
import fr.istic.aco.editor.receiver.contract.Selection;

import java.util.Objects;
import java.util.Optional;

/**
 * Concrete command of select action
 */
public class SelectCommand implements Command {
    private final Engine receiver;
    private final Invoker invoker;
    private final Recorder recorder;
    private int beginIndex;
    private int endIndex;

    /**
     * Main constructor
     * @param receiver of the command
     * @param invoker of the command
     * @param recorder saves the command
     */
    public SelectCommand(Engine receiver, Invoker invoker, Recorder recorder) {
        Objects.requireNonNull(receiver);
        Objects.requireNonNull(invoker);
        Objects.requireNonNull(recorder);
        this.receiver = receiver;
        this.invoker = invoker;
        this.recorder = recorder;
    }

    @Override
    public void execute() {
        Selection selection = this.receiver.getSelection();
        if (!this.recorder.isReplaying()) {
            beginIndex = invoker.getBeginIndex();
            endIndex = invoker.getEndIndex();

            //Si la sélection ne peut être effectuée, une exception est capturée.
            try {
                selection.setBeginIndex(this.beginIndex);
                selection.setEndIndex(this.endIndex);
            } catch (IndexOutOfBoundsException ex) {
                ex.printStackTrace();
            }
            //Sauvegarder la commande pour pouvoir la rejouer après
            if (recorder.isRecording()) {
                recorder.save(this);
            }
        } else {
            selection.setBeginIndex(this.beginIndex);
            selection.setEndIndex(this.endIndex);
        }
    }

    @Override
    public Optional<Memento> getMemento() {
        SelectMemento memento = new SelectMemento();
        memento.setBeginIndex(this.beginIndex);
        memento.setEndIndex(this.endIndex);

        return Optional.of(memento);
    }

    @Override
    public void setMemento(Memento memento) {
        this.beginIndex = ((SelectMemento) memento).getBeginIndex();
        this.endIndex = ((SelectMemento) memento).getEndIndex();
    }

    private static class SelectMemento implements Memento {
        private int beginIndex;
        private int endIndex;

        public int getBeginIndex() {
            return beginIndex;
        }

        public void setBeginIndex(int beginIndex) {
            this.beginIndex = beginIndex;
        }

        public int getEndIndex() {
            return endIndex;
        }

        public void setEndIndex(int endIndex) {
            this.endIndex = endIndex;
        }
    }
}
