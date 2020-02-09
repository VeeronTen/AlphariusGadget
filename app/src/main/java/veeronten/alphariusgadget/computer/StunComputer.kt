package veeronten.alphariusgadget.computer

import veeronten.alphariusgadget.model.Card

class StunComputer : AbstractComputer() {
    override fun makeDecision(drawedCards: MutableList<Card>) = drawedCards.contains(Card.STUN)
}