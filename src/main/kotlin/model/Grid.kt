package ui.assignments.connectfour.model

/**
 * The grid of Connect-Four and functionality to interact with it, i.e., play the game.
 * version 1.01: BUGFIX: did not detect insertions that form chains > length, e.g., ***_** -> ******
 */
class Grid(width: Int, height: Int, private val length: Int) {

    /**
     * The grid. Coordinates run from (0,0) (top-left) to (width-1,height-1) (bottom-right).
     */
    private val grid = Array2D(width, height)

    /**
     * Returns the larger of two Pair<Player, Int>.
     */
    private fun max(item: Pair<Player, Int>, other: Pair<Player, Int>): Pair<Player, Int> {
        return if (item.first == Player.NONE && other.first != Player.NONE) other
               else if (item.first != Player.NONE && other.first == Player.NONE) item
               else if (other.second > item.second) other
               else item
    }

    /**
     * Returns the largest consecutive chain within a list
     * @param list the list
     * @return a [Pair<Player, Int>] that holds the length of the longest chain, and which player's pieces form this chain.
     */
    private fun longestChain(list: List<Player>) : Pair<Player, Int> {
        var curCount = Pair(list[0], 1)
        var maxCount = curCount.copy()
        (1 until list.count()).forEach { idx ->
            curCount = if (list[idx] == curCount.first) {
                Pair(curCount.first, curCount.second + 1)
            } else {
                maxCount = max(maxCount, curCount)
                Pair(list[idx], 1)
            }
        }
        return max(maxCount, curCount)
    }

    /**
     * Returns the player who has won the game on the current grid.
     * version 1.01: BUGFIX: did not detect insertions that form chains > length, e.g., ***_** -> ******
     *                       changed (playerWon.second == length) to: (playerWon.second >= length)
     * @return player who has won the game, or [Player.NONE] if no-one has won the game yet.
     */
    fun hasWon() : Player {
        var playerWon = Pair(Player.NONE, 0)

        grid.getColumns().forEach { // check columns
            playerWon = max(playerWon, longestChain(it))
        }

        grid.getRows().forEach { // check rows
            playerWon = max(playerWon, longestChain(it))
        }
        grid.getDiagonals().forEach { // check diagonals top-right -> bottom-left
            playerWon = max(playerWon, longestChain(it))
        }

        grid.getDiagonals2().forEach { // check diagonals bottom-right -> top-left
            playerWon = max(playerWon, longestChain(it))
        }

        return if (playerWon.second >= length) playerWon.first else Player.NONE
    }

    /**
     * Checks if the draw-condition has occurred, i.e., the grid is full.
     * @return draw-condition has occurred
     */
    fun hasDraw() : Boolean {
        return grid.getColumns().any { list -> list.any { it == Player.NONE } }.not()
    }

    /**
     * Attempts to drop a piece in a certain column. If a piece can be successfully dropped, the function returns that [Piece]. If not, the function returns null.
     * @param column the column in which to drop a piece
     * @return the dropped [Piece], if successful; null otherwise
     */
    fun dropPiece(column: Int, player: Player): Piece? {
        val row = grid.getColumn(column).count { it.player == Player.NONE } // number of unoccupied spots in the column
        if (row == 0) return null                                                // if none ... return null
        grid.setCell(column, row - 1, player)                                 // set ownership for piece furthest down in the column
        return grid.getCell(column, row - 1)                                  // return piece
    }
}