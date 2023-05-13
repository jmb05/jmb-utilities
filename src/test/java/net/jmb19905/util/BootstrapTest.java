package net.jmb19905.util;

import net.jmb19905.util.bootstrapping.Bootstrap;
import net.jmb19905.util.bootstrapping.DeployState;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;

public class BootstrapTest {

    @Test
    public void testDeployStates() {
        Bootstrap bootstrap = new TestBootstrap(new String[]{"dev"})
                .bootstrap();
        Assertions.assertEquals(DeployState.DEV, bootstrap.getDeployState());
        
        bootstrap = new TestBootstrap(new String[]{"deploy"})
                .bootstrap();
        Assertions.assertEquals(DeployState.DEPLOY, bootstrap.getDeployState());

        bootstrap = new TestBootstrap(new String[]{"dev-deploy"})
                .bootstrap();
        Assertions.assertEquals(DeployState.DEV_DEPLOY, bootstrap.getDeployState());

        bootstrap = new TestBootstrap(new String[]{"client"})
                .bootstrap();
        Assertions.assertEquals(DeployState.CLIENT, bootstrap.getDeployState());
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    @Test
    public void testLogger() {
        new TestBootstrap(new String[]{})
                .loggerSignature("test")
                .bootstrap();
        File logFile = new File("logs/latest_test.log");
        Assertions.assertTrue(logFile.exists());
        Logger.info("Logger Test");
        Logger.close();
        logFile.delete();
    }

    static class TestBootstrap extends Bootstrap {
        public TestBootstrap(String[] args) {
            super(args);
        }
    }

}