package com.bloodcrown.rxbus;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * 作者 ： BloodCrown
 * 时间 ： 2018/4/25 下午1:00
 * 描述 ：
 */

public class RxBus {

    public static volatile RxBus instance;

    private Subject<Object> bus;

    private RxBus() {
        bus = PublishSubject.create().toSerialized();
    }

    public static RxBus getInstance() {
        if (instance == null) {
            synchronized (RxBus.class) {
                if (instance == null) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    public void post(Object message) {
        bus.onNext(message);
    }

    public <T> Observable<T> setTag(Class<T> type) {
        return bus.ofType(type);
    }

    public boolean hasObserver() {
        return bus.hasObservers();
    }

}
