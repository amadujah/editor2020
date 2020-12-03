package fr.istic.aco.editor.memento.contract;

public interface Originator {
    Memento getMemento();

    void setMemento(Memento memento);
}
