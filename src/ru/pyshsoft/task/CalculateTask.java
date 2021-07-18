package ru.pyshsoft.task;

import ru.pyshsoft.Notifier;
import ru.pyshsoft.Observer;
import ru.pyshsoft.model.CalculateResult;
import ru.pyshsoft.model.DownloadResult;

import java.util.Random;
import java.util.Set;
import java.util.concurrent.Callable;

public class CalculateTask implements Callable<CalculateResult>, Notifier<CalculateResult> {

    private final DownloadResult downloadResult;
    private final Set<Observer<CalculateResult>> observers;

    public CalculateTask(DownloadResult downloadResult, Set<Observer<CalculateResult>> observers) {
        this.downloadResult = downloadResult;
        this.observers = observers;
    }

    public CalculateResult calculate() {
        Random r = new Random();
        CalculateResult result = new CalculateResult();
        result.id = downloadResult.id;

        int check;
        do {
            check = r.nextInt(2000000);
        } while (!downloadResult.check(check));
        result.found = true;

        notifyObservers(result);

        return result;
    }

    @Override
    public CalculateResult call() {
        return calculate();
    }


    @Override
    public void notifyObservers(CalculateResult result) {
        observers.forEach(observer -> observer.update(result));
    }
}

