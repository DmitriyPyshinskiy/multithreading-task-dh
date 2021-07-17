package ru.pyshsoft.service;

import ru.pyshsoft.App;
import ru.pyshsoft.Observable;
import ru.pyshsoft.Observer;
import ru.pyshsoft.model.DownloadResult;
import ru.pyshsoft.task.DownloadTask;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DownloadService implements Observable<DownloadResult> {
    private final ExecutorService downloadExecutorService = Executors.newFixedThreadPool(App.AVAILABLE_PROC);
    private final Set<Observer<DownloadResult>> observers = new HashSet<>();


    public void download(int downloadId) {
        downloadExecutorService.submit(new DownloadTask(downloadId, observers));
    }

    @Override
    public void subscribe(Observer<DownloadResult> observer) {
        observers.add(observer);
    }

    @Override
    public void unsubscribe(Observer<DownloadResult> observer) {
        observers.remove(observer);
    }

    public void terminate() throws InterruptedException {
        downloadExecutorService.shutdown();
        downloadExecutorService.awaitTermination(30, TimeUnit.MINUTES);
    }
}

