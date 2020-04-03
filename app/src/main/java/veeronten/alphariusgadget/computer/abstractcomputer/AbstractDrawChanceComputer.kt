package veeronten.alphariusgadget.computer.abstractcomputer

import veeronten.alphariusgadget.model.Deck
import veeronten.alphariusgadget.model.FullDrawInfo

abstract class AbstractDrawChanceComputer(sourceDeck: Deck) : AbstractDrawComputer(sourceDeck) {

    private var success = 0
    private var all = 0

    private var prevEmitted: Int? = null

    abstract fun makeDecision(drawInfo: FullDrawInfo): Boolean


    val chanceComputation = draws
        .doOnNext {
            if(makeDecision(it)) success++
            all++
        }
        .map { ((success.toDouble() / all.toDouble()) * 100).toInt() }
        .filter { prevEmitted == null || diffIsSignificant(prevEmitted!!, it) }
        .doOnNext {
            prevEmitted = it
        }

    private fun diffIsSignificant(prevEmitted: Int, gonnaEmit: Int) = Math.abs(prevEmitted - gonnaEmit) != 0

    override fun fullClean() {
//        Log.d("jojo", "CLEAN BB")
//        Log.d("jojo", "$success / $all")
        success = 0
        all = 0
        prevEmitted = null
//        Log.d("jojo", "$success / $all")
    }
}