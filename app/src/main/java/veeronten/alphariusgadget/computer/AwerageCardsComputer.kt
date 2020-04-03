package veeronten.alphariusgadget.computer

import veeronten.alphariusgadget.computer.abstractcomputer.AbstractDrawCounterComputer
import veeronten.alphariusgadget.model.Card
import veeronten.alphariusgadget.model.Deck
import veeronten.alphariusgadget.model.FullDrawInfo

class AwerageCardsComputer(sourceDeck: Deck) : AbstractDrawCounterComputer(sourceDeck) {
    override fun count(drawInfo: FullDrawInfo) = drawInfo.drawedCards.count { it != Card.FATIGUE }
}