package ui.assignments.connectfour.model

/**
 * A single piece in Connect-Four.
 */
class Piece(val x: Int, val y: Int) {

    /**
     * the player who owns this piece. This property can only be set once.
     */
    var player = Player.NONE
        set(value) { if (player == Player.NONE) field = value }

    /**
     * Returns a string-representation of the piece.
     */
    override fun toString(): String {
        return player.toString()
        //return "($x,$y):${player.toString()}"
    }
}