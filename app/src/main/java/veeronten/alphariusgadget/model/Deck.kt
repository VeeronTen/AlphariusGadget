package veeronten.alphariusgadget.model

import kotlin.random.Random

const val CARDS_COUNT_IN_DEF_DECK = 30

class Deck : ArrayList<Card>() {
    companion object {
        fun createDefaultDeck() = Deck().apply { repeat(CARDS_COUNT_IN_DEF_DECK) { add(Card.NONE ) } }
    }

    fun draw(): Card {
        val randomIndex = Random.nextInt(this.size)
        val drawedCard = this[randomIndex]
        this.removeAt(randomIndex)
        return  drawedCard
    }
}