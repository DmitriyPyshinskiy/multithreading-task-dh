package ru.pyshsoft;

import ru.pyshsoft.service.CalculateService;
import ru.pyshsoft.service.DownloadService;
import ru.pyshsoft.service.StatisticService;

import static java.lang.Runtime.getRuntime;

public class App {
    public static final int AVAILABLE_PROC = getRuntime().availableProcessors();

    public static void main(String[] args) throws InterruptedException {
        StatisticService statisticService = new StatisticService();
        CalculateService calculateService = new CalculateService();
        DownloadService downloadService = new DownloadService();

        downloadService.subscribe(calculateService);
        calculateService.subscribe(statisticService);

        statisticService.fixStartTime();

        for (int i = 0; i < 300; i++) {
            downloadService.download(i);
        }

        downloadService.terminate();
        calculateService.terminate();

        statisticService.fixStopTime();
        statisticService.printStatistic();
    }
}
