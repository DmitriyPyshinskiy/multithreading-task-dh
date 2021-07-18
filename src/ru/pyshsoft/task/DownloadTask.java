package ru.pyshsoft.task;

import ru.pyshsoft.Notifier;
import ru.pyshsoft.Observer;
import ru.pyshsoft.model.DownloadResult;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;

public class DownloadTask implements Callable<DownloadResult>, Notifier<DownloadResult> {
    private final int id;
    private final Set<Observer<DownloadResult>> observers;

    public DownloadTask(int id, Set<Observer<DownloadResult>> observers) {
        this.id = id;
        this.observers = observers;
    }

    public DownloadResult downloadNext() throws InterruptedException {
        Random r = new Random();
        Thread.sleep(r.nextInt(50)); //download process
        DownloadResult result = new DownloadResult(id, r.nextInt(2000000));

        notifyObservers(result);
        return result;
    }

    @Override
    public DownloadResult call() throws Exception {
        return downloadNext();
    }


    @Override
    public void notifyObservers(DownloadResult result) {
        observers.forEach(observer -> observer.update(result));
    }
}
