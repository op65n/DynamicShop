package com.sebbaindustries.dynamicshop.tasks;

import java.util.Collections;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

public class Tasks {

    private static final Set<Task> tasks = Collections.newSetFromMap(new WeakHashMap<>());


    public static void async(IRunnable<Task> runnable) {
        AtomicReference<TaskRunner> reference = new AtomicReference<>();
        IRunnableTask iRunnableTask = () -> runnable.create(reference.get());
        TaskRunner runner = new TaskRunner(iRunnableTask);
        CompletableFuture.supplyAsync(() -> runner).exceptionally(e -> {e.printStackTrace(); return null;});
    }
}
