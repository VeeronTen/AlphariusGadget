package veeronten.alphariusgadget.computer.abstractcomputer

import veeronten.alphariusgadget.model.Deck
import veeronten.alphariusgadget.model.FullDrawInfo

abstract class AbstractDrawCounterComputer(sourceDeck: Deck)  : AbstractDrawComputer(sourceDeck) {

    private var value = 0
    private var tries = 0

    private var prevEmitted: Int? = null

    abstract fun count(drawInfo: FullDrawInfo): Int

    val countComputation = draws
        .doOnNext {
            value += count(it)
            tries++
        }
        .map { (value / tries) }
        .filter { prevEmitted == null || diffIsSignificant(prevEmitted!!, it) }
        .doOnNext {
            prevEmitted = it
        }

    private fun diffIsSignificant(prevEmitted: Int, gonnaEmit: Int) = Math.abs(prevEmitted - gonnaEmit) != 0

    override fun fullClean(){
        value = 0
        tries = 0
        prevEmitted = null
    }
}