package veeronten.alphariusgadget.model

enum class Card(val drawCount: Int, val text: String) {
    DRAW_3(3, "DRAW"),
    STUN(1, "STUN"),
    KILL(1, "KILL"),
    DMG(1, "DMG"),
    NONE(0, "N")
}