package com.sebbaindustries.dynamicshop.engine.task;

import java.util.concurrent.CompletableFuture;

public class Task {

    public static void async(Runnable runnable) {
        CompletableFuture.runAsync(runnable).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });
    }


}
