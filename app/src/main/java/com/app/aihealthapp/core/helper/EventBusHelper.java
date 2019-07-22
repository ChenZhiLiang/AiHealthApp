package com.app.aihealthapp.core.helper;


import com.app.aihealthapp.core.eventbus.Event;

import org.greenrobot.eventbus.EventBus;

/**
 * author：chenzl
 * Create time: 2017/11/16 0016 09:04
 * describe:EventBus封装.
 * e_mail：chenzhiliang@guangxirenrenparking.com
 */
public class EventBusHelper {

    public static void register(Object subscriber) {
        EventBus.getDefault().register(subscriber);
    }

    public static void unregister(Object subscriber) {
        EventBus.getDefault().unregister(subscriber);
    }

    public static void sendEvent(Event event) {
        EventBus.getDefault().post(event);
    }

    public static void sendStickyEvent(Event event) {
        EventBus.getDefault().postSticky(event);
    }
}
