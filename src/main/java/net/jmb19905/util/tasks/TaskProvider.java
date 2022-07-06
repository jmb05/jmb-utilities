package net.jmb19905.util.tasks;

import java.util.List;

public interface TaskProvider<T extends Task<O>, O> {
    default O performTasks(O o, List<T> tasks) {
        for(T task : tasks) {
            o = task.perform(o);
        }
        return o;
    }
    void addTask(T task);
}
