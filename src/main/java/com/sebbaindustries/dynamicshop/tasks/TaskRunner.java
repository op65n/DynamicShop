package com.sebbaindustries.dynamicshop.tasks;

public class TaskRunner extends RunnableTask {

    private final IRunnableTask runnable;

    public TaskRunner(IRunnableTask runnable) {
        this.runnable = runnable;
    }


    @Override
    public void run() {
        runnable.run();
    }

}
