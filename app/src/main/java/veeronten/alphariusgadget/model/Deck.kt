package veeronten.alphariusgadget.model


import io.reactivex.subjects.PublishSubject
import kotlin.random.Random

const val CARDS_COUNT_IN_DEF_DECK = 1

class Deck : ArrayList<Card>() {

    var lastFatigueDmg = 0

    companion object {
        fun createDefaultDeck() = Deck().apply { repeat(CARDS_COUNT_IN_DEF_DECK) { add(Card.NONE ) } } //todo remove
    }

    val updated = PublishSubject.create<Unit>()

    fun draw(): Card {

        return if(this.size > 0) {
            val randomIndex = Random.nextInt(this.size)
            val drawedCard = this[randomIndex]
            this.removeAt(randomIndex)
            updated.onNext(Unit)
            drawedCard
        } else {
            lastFatigueDmg++
            updated.onNext(Unit)
            Card.FATIGUE
        }

    }

    val trapsInDeck = updated.map { count { it != Card.NONE } }

    val cardsInDeck = updated.map { size }

    val stunsInDeck = updated.map { count { it == Card.STUN } }

    val drawsInDeck = updated.map { count { it == Card.DRAW_3} }

    val dmgsInDeck = updated.map { count { it == Card.DMG } }

    val killsInDeck = updated.map { count { it == Card.KILL} }



    ///ADD///
    private fun addCard(cardToAdd: Card) {
        add(cardToAdd)
        updated.onNext(Unit)
    }

    fun addStun() = addCard(Card.STUN)

    fun addDraw() = addCard(Card.DRAW_3)

    fun addDmg() = addCard(Card.DMG)

    fun addKill() = addCard(Card.KILL)
    ///---///

    ///REDUSE///
    private fun reduseCard(cardToReduse: Card) {
//        find { it ==  cardToReduse}?.let {

//        }
        remove(cardToReduse)
        updated.onNext(Unit)
    }

    fun reduseStun() = reduseCard(Card.STUN)

    fun reduseDraw() = reduseCard(Card.DRAW_3)

    fun reduseDmg() = reduseCard(Card.DMG)

    fun reduseKill() = reduseCard(Card.KILL)
    ///------///
}