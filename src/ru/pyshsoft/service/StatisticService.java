package ru.pyshsoft.service;

import ru.pyshsoft.Observer;
import ru.pyshsoft.model.CalculateResult;

import java.util.Calendar;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class StatisticService implements Observer<CalculateResult> {
    private Calendar startTime;
    private Calendar stopTime;

    private final Set<CalculateResult> calculateResults = ConcurrentHashMap.newKeySet();

    @Override
    public void update(CalculateResult result) {
        calculateResults.add(result);
    }

    public void fixStartTime() {
        this.startTime = Calendar.getInstance();
    }

    public void fixStopTime() {
        this.stopTime = Calendar.getInstance();
    }

    public void printStatistic() {
        System.out.println("Total success checks: " + evaluateCountSuccessClcResults());
        System.out.println("Total time: " + getTimeStatisticInMs() + " ms");
    }

    private long getTimeStatisticInMs() {
        return stopTime.getTimeInMillis() - startTime.getTimeInMillis();
    }

    public long evaluateCountSuccessClcResults() {
        return calculateResults.stream().filter(res -> res.found).count();
    }

}

