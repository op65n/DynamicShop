package tech.op65n.dynamicshop.engine.task;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Task {

    public static ExecutorService executorService = Executors.newFixedThreadPool(30);

    public static Future<Boolean> asyncFuture(Runnable runnable) {
        return executorService.submit(() -> {
            try {
                runnable.run();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        });
    }

    public static void async(Runnable runnable) {
        CompletableFuture.runAsync(runnable).exceptionally(throwable -> {
            throwable.printStackTrace();
            return null;
        });
    }


}
