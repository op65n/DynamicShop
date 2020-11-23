package com.sebbaindustries.dynamicshop.tasks;

public abstract class RunnableTask implements Task {

    @Override
    public void async() {
        run();
    }

    @Override
    public void terminate() {

    }

    public abstract void run();

}
