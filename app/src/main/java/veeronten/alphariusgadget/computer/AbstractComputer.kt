package veeronten.alphariusgadget.computer

import android.util.Log
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import veeronten.alphariusgadget.SourceDeck
import veeronten.alphariusgadget.model.Card
import veeronten.alphariusgadget.model.Deck

abstract class AbstractComputer {

    private var localSourceDeck = BehaviorSubject.createDefault(Deck.createDefaultDeck())
    private var computingDeck = Deck()

    private var success = 0
    private var all = 0

    private var prevEmitted: Int? = null

    var toDrawCount = 1
    val drawedCards = mutableListOf<Card>()

    init {
        SourceDeck.deck.subscribeBy(
            onNext = {
                localSourceDeck.onNext(it)
                clearResults()
            },
            onComplete = {},
            onError = {}
        )
    }

    abstract fun makeDecision(drawedCards: MutableList<Card>): Boolean

    val computation = Flowable.create<Int>({ emitter ->
        while(true) {
            prepareCleanComputation()
            drawSequense()
            if(makeDecision(drawedCards)) success++
            all++
            emitIfNeed(emitter)
        }
    }, BackpressureStrategy.LATEST)
        .subscribeOn(Schedulers.computation())
        .observeOn(AndroidSchedulers.mainThread())


    private fun emitIfNeed(emitter: FlowableEmitter<Int>) {
        val gonnaEmit = ((success.toFloat() / all.toFloat()) * 100).toInt()
        if(prevEmitted == null || diffIsSignificant(prevEmitted!!, gonnaEmit)) {
            emitter.onNext(gonnaEmit)
            prevEmitted = gonnaEmit
        }
    }

    private fun diffIsSignificant(prevEmitted: Int, gonnaEmit: Int) = Math.abs(prevEmitted - gonnaEmit) != 0

    private fun prepareCleanComputation() {
        toDrawCount = 1
        drawedCards.clear()
        computingDeck.clear()
        computingDeck.addAll(localSourceDeck.value!!)
    }

    private fun drawSequense() {
        while (toDrawCount > 0) {
            val drawedCard = computingDeck.draw()
            drawedCards.add(drawedCard)
            toDrawCount--
            toDrawCount += drawedCard.drawCount
        }
    }

    private fun clearResults() {
        success = 0
        all = 0
        prevEmitted = null
    }

    fun printCase(sequense: MutableList<Card>) {
        var result = ""
        sequense.forEach {
            result += "${it.text} -> "
        }
        Log.d("jojo", result)
    }
}