package veeronten.alphariusgadget.computer.abstractcomputer

import android.util.Log
import veeronten.alphariusgadget.model.Card

abstract class AbstractDrawChanceComputer : AbstractDrawComputer() {

    private var success = 0
    private var all = 0

    private var prevEmitted: Int? = null

    abstract fun makeDecision(drawedCards: MutableList<Card>): Boolean


    val chanceComputation = draws
        .doOnNext {
            if(makeDecision(it)) success++
            all++
        }
        .map { ((success.toFloat() / all.toFloat()) * 100).toInt() }
        .filter { prevEmitted == null || diffIsSignificant(prevEmitted!!, it) }
        .doOnNext {
            prevEmitted = it
        }

    private fun diffIsSignificant(prevEmitted: Int, gonnaEmit: Int) = Math.abs(prevEmitted - gonnaEmit) != 0

    override fun sourceUpdated() {
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