package com.kachidoki.rxjavatest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        Observable<String> observable = Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                subscriber.onNext("Hello!");
//                subscriber.onNext("I");
//                subscriber.onNext("Am");
//                subscriber.onNext("KachidokiMa");
//                subscriber.onCompleted();
//            }
//        });
//
//        Observer<String> observer = new Observer<String>() {
//            @Override
//            public void onCompleted() {
//                Log.e("Rxjava","Completed");
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                Log.e("Rxjava","Error");
//            }
//
//            @Override
//            public void onNext(String s) {
//                Log.e("Rxjava"," "+s);
//            }
//        };
//
//        observable.subscribe(observer);


        Observable.just("Hello!","I","Am","KachidokiMa")
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e("Rxjava","doOnNext "+s+",run in "+Thread.currentThread().getName());

                    }
                })
                .subscribeOn(Schedulers.newThread())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String s) {
                        Log.e("Rxjava","Map run in "+Thread.currentThread().getName());
                        return "~"+s+"~";
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.e("Rxjava","get Result "+s+",run in "+Thread.currentThread().getName());
                    }
                });


    }
}
