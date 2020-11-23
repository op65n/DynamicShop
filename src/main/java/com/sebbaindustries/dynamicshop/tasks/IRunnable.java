package com.sebbaindustries.dynamicshop.tasks;

@FunctionalInterface
public interface IRunnable<T> {

    void create(T t);

}
