package com.pro100kryto.server.envtest;

import com.pro100kryto.server.Server;
import com.pro100kryto.server.logger.ILogger;
import com.pro100kryto.server.service.IService;

import java.nio.file.Paths;

;

public class Main {
    public static void main(String[] args) throws Throwable {

        final Server server = new Server(
                Paths.get(System.getProperty("user.dir"))
        );
        final ILogger logger = server.getLoggerManager().getMainLogger();

        server.getLiveCycle().init();

        final IService<?> testService = server.getServiceLoader()
                .createService("Test", "Test");

        server.getLiveCycle().start();

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
