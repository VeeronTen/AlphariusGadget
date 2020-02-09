package veeronten.alphariusgadget.computer

import veeronten.alphariusgadget.computer.abstractcomputer.AbstractDrawChanceComputer
import veeronten.alphariusgadget.model.Card

class StunChanceComputer : AbstractDrawChanceComputer() {
    override fun makeDecision(drawedCards: MutableList<Card>) = drawedCards.contains(Card.STUN)
}