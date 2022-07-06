package net.jmb19905.util.threading;

import java.util.ArrayList;
import java.util.List;

public class InvokableLoopThread extends Thread{

    private boolean running = true;
    private final Runnable setupTask;
    private final List<Runnable> tasks;

    public InvokableLoopThread(Runnable setupTask) {
        this.setupTask = setupTask;
        this.tasks = new ArrayList<>();
    }

    @Override
    public void run() {
        setupTask.run();
        while (running) {
            System.out.println("Running");
            tasks.forEach(runnable -> {
                System.out.println("Running task: " + runnable);
                runnable.run();
            });
            tasks.clear();
        }
    }

    public void stopThread() {
        running = false;
    }

    public void invoke(Runnable task) {
        System.out.println("Adding task: " + task);
        tasks.add(task);
    }

}
