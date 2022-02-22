package com.sbrf.reboot.concurrency;

import com.sbrf.reboot.service.ReportService;
import com.sbrf.reboot.service.SomeService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Duration;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

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
}
