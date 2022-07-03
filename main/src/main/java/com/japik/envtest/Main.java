package com.japik.envtest;

import com.japik.Japik;
import com.japik.logger.ILogger;
import com.japik.service.IService;

import java.nio.file.Paths;

;

public class Main {
    public static void main(String[] args) throws Throwable {

        final Japik server = new Japik(
                Paths.get(System.getProperty("user.dir"))
        );
        final ILogger logger = server.getLoggerManager().getMainLogger();

        server.getLiveCycle().init();
        server.getLiveCycle().start();

        final IService<?> testService = server.getServiceLoader()
                .load("Test", "test");
        testService.getLiveCycle().init();
        testService.getLiveCycle().start();

        System.in.read();

        try {
            server.getLiveCycle().stopSlow();
        } catch (Throwable throwable){
            logger.exception(throwable);
            server.getLiveCycle().stopForce();
        }
    }
}
