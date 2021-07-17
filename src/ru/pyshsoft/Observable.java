package ru.pyshsoft;

public interface Observable<R> {

    void subscribe(Observer<R> observer);

    void unsubscribe(Observer<R> observer);

}
