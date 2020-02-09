package veeronten.alphariusgadget.computer

import veeronten.alphariusgadget.model.Card

class StunChanceComputer : AbstractChanceComputer() {
    override fun makeDecision(drawedCards: MutableList<Card>) = drawedCards.contains(Card.STUN)
}