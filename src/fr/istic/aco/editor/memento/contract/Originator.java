package fr.istic.aco.editor.memento.contract;

import java.util.Optional;

/**
 * Originator API
 */
public interface Originator {

    /**
     * Save the state
     * @return the saved state memento
     */
    Optional<Memento> getMemento();

    /**
     * Restore the state
     * @param memento the memento which state is restored
     */
    void setMemento(Memento memento);
}
