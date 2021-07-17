package ru.pyshsoft.service;

import ru.pyshsoft.App;
import ru.pyshsoft.Observable;
import ru.pyshsoft.Observer;
import ru.pyshsoft.model.CalculateResult;
import ru.pyshsoft.model.DownloadResult;
import ru.pyshsoft.task.CalculateTask;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CalculateService implements Observer<DownloadResult>, Observable<CalculateResult> {

    private final ExecutorService calculateExecutorService = Executors.newFixedThreadPool(App.AVAILABLE_PROC);
    private final Set<Observer<CalculateResult>> observers = new HashSet<>();

    @Override
    public void update(DownloadResult result) {
        CalculateTask calculateTask = new CalculateTask(result, observers);
        calculateExecutorService.submit(calculateTask);
    }

    public void terminate() throws InterruptedException {
        calculateExecutorService.shutdown();
        calculateExecutorService.awaitTermination(30, TimeUnit.MINUTES);
    }

    @Override
    public void subscribe(Observer<CalculateResult> observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(Observer<CalculateResult> observer) {
        observers.remove(observer);
    }
}

