package com.example.helloworld

import android.util.Log
import io.reactivex.*
import io.reactivex.rxjava3.core.*
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
//import io.reactivex.rxjava3.schedulers.Schedulers.computation
import java.util.concurrent.TimeUnit

fun main() {
    Log.d("tag", "main")
//    observable()
    observableFromCreate()
//    hotObservable()
//    completable()
//    maybe()
}

fun maybe() {
    val maybe = Maybe.create<String> { emitter ->
        emitter.onComplete()
    }

    maybe.subscribe(
        {
            println(it)
        }, {
            println("error")
        }, {
            println("onComplete")
        })
}

fun completable() {
    val complitable = Completable.error(Throwable())
    complitable.subscribe(
        {
            println("Complete")
        }, {
            println("error")
        })
}

fun singleFromCallable() {
    val single = Single.fromCallable {
        println("fromCallable")
        "fromCallable"
    }
    single.subscribe(
        {
            println(it)
        }, {
            it.printStackTrace()
        }

    )
}

fun hotObservable() {

    val observableInterval = Observable.interval(1, TimeUnit.SECONDS)

    val hotObservable = observableInterval.publish()

    hotObservable.subscribe {
        println("First subscriber $it")
    }

    hotObservable.connect()
    Thread.sleep(5000)
    hotObservable.subscribe {
        println("Second subscriber $it")
    }
    hotObservable.connect()
    Thread.sleep(4000)
}

fun observableFromCreate() {
    val observable = Observable.create<String> { emitter ->
        emitter.onNext("first")
        emitter.onNext("second")
        emitter.onError(Throwable())
    }
        .subscribeOn(Schedulers.computation())

    observable.subscribe(object : Observer<String> {
        override fun onNext(t: String) {
            println(t)
        }

        override fun onSubscribe(d: Disposable) {
        }

        override fun onError(e: Throwable) {
            println("onError")
        }

        override fun onComplete() {
            println("onComplete")
        }
    })

    observable.subscribe(object : Observer<String> {
        override fun onNext(t: String) {
            println("asdasd" + t)
        }

        override fun onSubscribe(d: Disposable) {
        }

        override fun onError(e: Throwable) {
            println("onError")
        }

        override fun onComplete() {
            println("onComplete")
        }
    })


    Thread.sleep(3000)
}

fun observable() {
    val observable = Observable.just(1, 2, 3, 4)

    observable.subscribeOn(Schedulers.computation())
    observable.map { item ->
        val string = "Item - $item"
        string
    }


    val observer = object : Observer<Int> {
        override fun onSubscribe(d: Disposable) {
            println("onSubscribe")
            println(Thread.currentThread().name)
        }

        override fun onNext(t: Int) {
            println("onNext")
            println(Thread.currentThread().name)
            println(t)
        }

        override fun onError(e: Throwable) {
            println("onError")
            println(Thread.currentThread().name)
        }

        override fun onComplete() {
            println("onComplete")
            println(Thread.currentThread().name)
        }
    }

    observable.subscribe(observer)

}