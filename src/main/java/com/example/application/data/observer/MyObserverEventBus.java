package com.example.application.data.observer;

import com.google.common.eventbus.EventBus;

public class MyObserverEventBus {
    private static final EventBus eventBus = new EventBus();

    public static void post(Object event) {
        eventBus.post(event);
    }

    public static void register(Object listener) {
        eventBus.register(listener);
    }
}
