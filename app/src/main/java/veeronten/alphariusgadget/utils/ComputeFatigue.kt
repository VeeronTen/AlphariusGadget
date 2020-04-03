package veeronten.alphariusgadget.utils

import veeronten.alphariusgadget.model.Deck
import veeronten.alphariusgadget.model.DrawedCards

fun computeFatigue(deck: Deck, drawedCards: DrawedCards): Int {
    var result = 0
    var fatigue = deck.lastFatigueDmg
    repeat(drawedCards.fatigueDrawed()) { result += fatigue-- }
    return result
}

