package com.sbrf.reboot.concurrency;

import com.sbrf.reboot.service.ReportService;
import com.sbrf.reboot.service.SomeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static org.mockito.Mockito.*;

public class CompletableFeatureTest {

    @Test
    public void successCompletableFeature() throws ExecutionException, InterruptedException, TimeoutException {
        ReportService reportService = Mockito.mock(ReportService.class);

        when(reportService.sendReport("Отправляю отчет")).then(e->{

            Thread.sleep(Duration.ofSeconds(3).toMillis());
            return "SUCCESS";
        });

        SomeService someService = new SomeService(reportService);

        someService.doSomething();

        verify(reportService, times(1)).sendReport("Отправляю отчет");
    }
    // 5+ exercise
    @Test
    void customCompletableFuture() throws ExecutionException, InterruptedException {
        // цель: асинхронно сгенерировать 1_000_000 номеров банковских карт платежной системы MIR
        final int countResults = 1_000_000;
        int countOfThreads = Runtime.getRuntime().availableProcessors();
        int countOfNumbersForOneTask = countResults / countOfThreads;
        Set<String> result = new HashSet<>();

        ExecutorService service = Executors.newFixedThreadPool(countOfThreads);
        List<CompletableFuture<Set<String>>> listOfTasks = new ArrayList<>();
        // run async
        for (int i = 0; i < countOfThreads; i++){
            CompletableFuture<Set<String>> completableFuture = getSetCompletableFuture(countOfNumbersForOneTask, service);
            listOfTasks.add(completableFuture);
        }
        // collect result
        for (int i= 0; i < listOfTasks.size(); i++){
            result.addAll(listOfTasks.get(i).get());
        }
        // reRun if was generated same numbers
        while (result.size() < countResults){
            int diff = countResults - result.size();
            CompletableFuture<Set<String>> completableFuture = getSetCompletableFuture(diff, service);
            result.addAll(completableFuture.get());
        }
        service.shutdown();

        Assertions.assertEquals(countResults, result.size());

    }
    private CompletableFuture<Set<String>> getSetCompletableFuture(int countOfNumbersForOneTask, ExecutorService service) {
        CompletableFuture<Set<String>> completableFuture =
                CompletableFuture.supplyAsync(()->getCard(countOfNumbersForOneTask), service);
        return completableFuture;
    }
    private Set<String> getCard(int count){
        Set<String> numbers = new HashSet<>();
        String prefix = "2202 2011";
        for (int i = 0; i < count; i++){
            StringBuilder number = new StringBuilder(prefix);
            int thirdPart = getPart();
            int fourthPart = getPart();
            number.append(" ").append(addPrefixZero(thirdPart)).append(" ").append(addPrefixZero(fourthPart));
            numbers.add(number.toString());
        }
        return numbers;
    }
    private int getPart(){
        return (int)(Math.random() * 10_000);
    }
    // If Generated Number Less Then 1000
    private String addPrefixZero(int generatedNumber){
        StringBuilder outString = new StringBuilder(String.valueOf(generatedNumber));
        while (outString.length() < 4){
            outString.insert(0,'0');
        }
        return outString.toString();
    }
}
