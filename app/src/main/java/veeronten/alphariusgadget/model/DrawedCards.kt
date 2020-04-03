package veeronten.alphariusgadget.model

class DrawedCards : ArrayList<Card>() {

    fun dmgDrawed() = drawedCards(Card.DMG)
    fun fatigueDrawed() = drawedCards(Card.FATIGUE)

    private fun drawedCards(card: Card) = count { it == card }
}