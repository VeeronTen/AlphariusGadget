package veeronten.alphariusgadget.computer.abstractcomputer

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import veeronten.alphariusgadget.SourceDeck
import veeronten.alphariusgadget.model.Card
import veeronten.alphariusgadget.model.Deck

abstract class AbstractDrawComputer {

    private var localSourceDeck = BehaviorSubject.createDefault(Deck.createDefaultDeck())
    private var computingDeck = Deck()

    private var toDrawCount = 1
    private val drawedCards = mutableListOf<Card>()

    abstract fun sourceUpdated()

    init {
        SourceDeck.deck.subscribeBy(
            onNext = {
                localSourceDeck.onNext(it)
                sourceUpdated()
            },
            onComplete = {},
            onError = {}
        )
    }

    val draws = Flowable.create<MutableList<Card>>({ emitter ->
        while(true) {
            prepareCleanComputation()
            drawSequence()
            emitter.onNext(drawedCards)
        }
    }, BackpressureStrategy.LATEST)
    .subscribeOn(Schedulers.computation())
    .observeOn(AndroidSchedulers.mainThread())

    private fun prepareCleanComputation() {
        toDrawCount = 1
        drawedCards.clear()
        computingDeck.clear()
        computingDeck.addAll(localSourceDeck.value!!)
    }

    private fun drawSequence() {
        while (toDrawCount > 0) {
            val drawedCard = computingDeck.draw()
            drawedCards.add(drawedCard)
            toDrawCount--
            toDrawCount += drawedCard.drawCount
        }
    }

}