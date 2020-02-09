package veeronten.alphariusgadget

import io.reactivex.subjects.BehaviorSubject
import veeronten.alphariusgadget.model.Card
import veeronten.alphariusgadget.model.Deck

class SourceDeck {
    companion object {
        var deck = BehaviorSubject.createDefault(Deck.createDefaultDeck())

        fun addCard(cardToAdd: Card) = deck.onNext(deck.value!!.apply { add(cardToAdd) })

        fun addStun() = addCard(Card.STUN)

        fun addDraw() = addCard(Card.DRAW_3)

        fun addDmg() = addCard(Card.DMG)

        fun addKill() = addCard(Card.KILL)

        val trapsCardsInDeck = deck.map { deck -> Pair(deck.count { it != Card.NONE }, deck.size) }
    }
}