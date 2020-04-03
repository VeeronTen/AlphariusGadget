package veeronten.alphariusgadget.computer

import veeronten.alphariusgadget.computer.abstractcomputer.AbstractDrawChanceComputer
import veeronten.alphariusgadget.model.Card
import veeronten.alphariusgadget.model.Deck
import veeronten.alphariusgadget.model.FullDrawInfo

class StunChanceComputer(sourceDeck: Deck) : AbstractDrawChanceComputer(sourceDeck) {
    override fun makeDecision(drawInfo: FullDrawInfo) = drawInfo.drawedCards.contains(Card.STUN)
}