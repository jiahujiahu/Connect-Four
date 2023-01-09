package ui.assignments.connectfour.model

/**
 * A player in Connect-Four.
 */
enum class Player {
    /**
     * No player
     */
    NONE,

    /**
     * The first player
     */
    ONE,

    /**
     * The second player
     */
    TWO;

    /**
     * Returns a string-representation of the player. [Player.NONE] is represented by an underscore ('_'), other players are represented by their number.
     */
    override fun toString(): String {
        return when(this){
            NONE -> "_"
            else -> this.ordinal.toString()
        }
    }
}