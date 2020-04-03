package veeronten.alphariusgadget.computer.abstractcomputer

import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import veeronten.alphariusgadget.model.Deck
import veeronten.alphariusgadget.model.DrawedCards
import veeronten.alphariusgadget.model.FullDrawInfo
import java.util.concurrent.TimeUnit

abstract class AbstractDrawComputer(val sourceDeck: Deck) {

    private var computingDeck = Deck()

    private var toDrawCount = 1
    private val drawedCards = DrawedCards()

    init {
        sourceDeck.updated.subscribeBy(
            onNext = { fullClean() },
            onComplete = {},
            onError = {}
        )
    }
    val draws = Flowable.interval(10, TimeUnit.MICROSECONDS).onBackpressureDrop()
        .observeOn(Schedulers.computation())
        .doOnNext {
            iterationClean()
            drawSequence()
        }
        .map { FullDrawInfo(computingDeck, drawedCards) }

    private fun iterationClean() {
        toDrawCount = 1
        drawedCards.clear()
        computingDeck.clear()
        computingDeck.addAll(sourceDeck)
    }
    private fun showDraws() {

    }

    abstract fun fullClean()


    private fun drawSequence() {
        while (toDrawCount > 0) {
            val drawedCard = computingDeck.draw()
            drawedCards.add(drawedCard)
            toDrawCount--
            toDrawCount += drawedCard.drawCount
        }
//        Log.d("jojo", " 1:    $computingDeck   ->   $drawedCards")
    }

}