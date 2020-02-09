package veeronten.alphariusgadget.computer

import veeronten.alphariusgadget.model.Card

class FatigueChanceComputer : AbstractChanceComputer() {
    override fun makeDecision(drawedCards: MutableList<Card>) = drawedCards.contains(Card.FATIGUE)
}