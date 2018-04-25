package com.bloodcrown.bw_rxbus;

import android.content.Context;
import android.widget.Toast;

import com.bloodcrown.rxbus.RxBus;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

/**
 * 作者 ： BloodCrown
 * 时间 ： 2018/4/25 下午1:37
 * 描述 ：
 */

public class MainPersenter {

    private CompositeDisposable disposables;
    private Disposable bookDisposable, catDisposable;
    private Context context;

    public MainPersenter(Context context) {
        this.context = context;
        disposables = new CompositeDisposable();
    }

    public void sendMessage(Object message) {
        RxBus.getInstance().post(message);
    }

    public void register(Object type) {
        if (type instanceof Book) {
            bookDisposable = RxBus.getInstance().setTag(new Book().getClass()).subscribe(new Consumer<Book>() {
                @Override
                public void accept(Book book) throws Exception {
                    Toast.makeText(context, "数据1收到", Toast.LENGTH_SHORT).show();
                }
            });
            disposables.add(bookDisposable);
        }

        if (type instanceof Cat) {
            catDisposable = RxBus.getInstance().setTag(new Cat().getClass()).subscribe(new Consumer<Cat>() {
                @Override
                public void accept(Cat cat) throws Exception {
                    Toast.makeText(context, "数据2收到", Toast.LENGTH_SHORT).show();
                }
            });
            disposables.add(catDisposable);
        }
    }

    public void unBundle(Object type) {
        if (type instanceof Book && bookDisposable != null && !bookDisposable.isDisposed()) {
            bookDisposable.dispose();
        }

        if (type instanceof Cat && catDisposable != null && !catDisposable.isDisposed()) {
            catDisposable.dispose();
        }
    }

    public void hasObserver() {
        String message = "";
        if (RxBus.getInstance().hasObserver()) {
            message = "当前还有观察者";
        } else {
            message = "当前没有观察者";
        }
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void onDestroy() {
        if (disposables != null && !disposables.isDisposed()) {
            disposables.clear();
        }
    }
}
