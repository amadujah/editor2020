package fr.istic.aco.editor.memento.contract;

/**
 * Originator API
 */
public interface Originator {

    /**
     * Save the state
     * @return the saved state memento
     */
    Memento getMemento();

    /**
     * Restore the state
     * @param memento the memento with state is restored
     */
    void setMemento(Memento memento);
}
