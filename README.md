# RxBus 学习 demo

本文地址：[我学 rxjava 2（5）- RxBus](https://www.jianshu.com/p/4248c6e2bc7f)

文章内容如下

### 吐槽
***

必须吐槽下啊，一直以为 RxBus 是像 EventBus 一样是1个开源库呢，谁知道压根不是这么回事啊，RxBus 只是一个思路：用 rxjava 来实现一个 EventBus ，没有规范，标准的开源库，需要我们自己去写，不过好在这么久过去了，有一些大家都认可的写法，这里不是我的原创，我也是看别人的，总结下，代码不手写出来就永远不是你的。

### 开工
***

其实思路很简单的，EventBus 怎么设计咱就怎么设计。在 rxjava 中用到了 subject 用来进行热发射和 ofType 操作符
* 热发射不熟悉的朋友可以看我的文章：[我学 rxjava 2（3）- 热发射](https://www.jianshu.com/p/9b8605b202b7)
* ofType 操作符是filter + cast的组合，只发送特定的类型。fliter用来判断是否为指定的类型，cast将一个Observable转换为指定的特殊Observable

就这些了，其他的都算是代码封装中最简单的，单例模式。

### 代码
***

这是简单的不能再简单了，我就不特别去说了，大家自己领会
```java
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
```

使用页很简单，分步说一下

1. 前置

管理 rxjava 管道对象
```java
    private CompositeDisposable disposables;
```

2. 注册
```java
            catDisposable = RxBus.getInstance()
                    .setTag(new Cat().getClass())
                    .subscribe(new Consumer<Cat>() {
                        @Override
                        public void accept(Cat cat) throws Exception {
                            Toast.makeText(context, "数据2收到", Toast.LENGTH_SHORT).show();
                        }
                    });
            disposables.add(catDisposable);
```

3. 解绑
```java
        if (bookDisposable != null && !bookDisposable.isDisposed()) {
            bookDisposable.dispose();
        }
```

效果演示：
![效果演示](https://upload-images.jianshu.io/upload_images/1785445-1679a2e4b328a6cf.gif?imageMogr2/auto-orient/strip)

好了，就这么多，看着很简单，大家手写一遍才真的行啊，练手很不错的，写完很有成就感

最后是本篇文章 demo：[BW_RxBus](https://github.com/zb25810045/BW_RxBus)


### 参考资料：
***

* [RxBus学习](https://www.jianshu.com/p/8f43fc948ddc)
* [从 RxBus 这辆兰博基尼深入进去](https://www.jianshu.com/p/fd547ba6595e)
* [RxBus 的简单实现](http://brucezz.itscoder.com/a-simple-rxbus-implementation)
