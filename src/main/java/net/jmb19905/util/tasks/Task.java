package net.jmb19905.util.tasks;

public interface Task<O> {
    O perform(O o);
}
