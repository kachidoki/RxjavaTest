package com.kachidoki.rxjavatest;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by mayiwei on 16/11/17.
 */
public class Elementary extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
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
                    .flatMap(new Func1<String, Observable<String>>() {
                        @Override
                        public Observable<String> call(String s) {
                            Log.e("Rxjava","FlatMap "+s+" run in "+Thread.currentThread().getName());
                            return Observable.just("~"+s+"~","1","2","3");
                        }
                    })
                    .map(new Func1<String, String>() {
                @Override
                public String call(String s) {
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
