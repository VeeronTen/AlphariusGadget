package veeronten.alphariusgadget.model

const val DMG_TRAP_DMG = 5

enum class Card(val drawCount: Int, val text: String) {
    DRAW_3(3, "DRAW"),
    STUN(1, "STUN"),
    KILL(1, "KILL"),
    DMG(1, "DMG"),
    NONE(0, "N"),
    FATIGUE(0, "FATIGUE")
}