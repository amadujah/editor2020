package fr.istic.aco.editor.Observer;

public interface Observer<T> {

    /**
     * Signals to this that s's value has been updated
     *
     * @param s the updated subject
     */
    void doUpdate(Subject<T> s);

}
