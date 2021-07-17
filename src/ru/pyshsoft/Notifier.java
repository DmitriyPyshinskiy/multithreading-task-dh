package ru.pyshsoft;

public interface Notifier<R> {

    void notifyObservers(R result);
}
