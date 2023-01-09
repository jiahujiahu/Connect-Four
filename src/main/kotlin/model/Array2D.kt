package ui.assignments.connectfour.model

import kotlin.math.max
import kotlin.math.min

/**
 * Stores a 2D array.
 *
 * @param width width of the array (x-coordinate)
 * @param height height of the array (y-coordinate)
 */
data class Array2D(private val width: Int, private val height: Int) {

    private var data = Array(width * height) { Piece(it % width, it / width) }

    /**
     * Sets (overwrites) the value of a single cell.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     * @param value new value
     */
    fun setCell(x: Int, y: Int, value: Player) {
        data[x + y * width].player = value
    }

    /**
     * Returns the value of a single cell.
     *
     * @param x x-coordinate
     * @param y y-coordinate
     */
    fun getCell(x: Int, y: Int) : Piece {
        return data[x + y * width]
    }

    /**
     * Returns a list of all [height] rows of the array. Each row itself is represented as a list of [width] values.
     * ┌─────────────┐
     * │ ─ ─ ─ ─ ─ ─ │
     * │ ─ ─ ─ ─ ─ ─ │
     * │ ─ ─ ─ ─ ─ ─ │
     * └─────────────┘
     */
    fun getRows() : List<List<Player>> {
        return List<List<Player>>(height) { y -> List<Player>(width) { x -> data[x + width * y].player } }
    }

    /**
     * Returns a single column of the array. The column is represented as a list of [height] values.
     *
     * @param x the x-coordinate of the column
     */
    fun getColumn(x: Int) : List<Piece> {
        return List<Piece>(height) { y -> data[x + width * y] }
    }

    /**
     * Returns a list of all [width] columns of the array. Each column itself is represented as a list of [height] values.
     * ┌─────────────┐
     * │ │ │ │ │ │ │ │
     * │ │ │ │ │ │ │ │
     * │ │ │ │ │ │ │ │
     * └─────────────┘
     */
    fun getColumns() : List<List<Player>> {
        return List<List<Player>>(width) { x -> List<Player>(height) { y -> data[x + width * y].player } }
    }

    /**
     * Returns a list of all diagonals (top-right -> bottom-left) of the array. Each diagonal itself is represented as a list of values.
     * ┌─────────────┐
     * │ ╱ ╱ ╱ ╱ ╱ ╱ │
     * │ ╱ ╱ ╱ ╱ ╱ ╱ │
     * │ ╱ ╱ ╱ ╱ ╱ ╱ │
     * └─────────────┘
     * version 1.01: added that height can be larger than width; fixed documentation
     */
    fun getDiagonals() : List<List<Player>> {
        val mini = min(width, height)
        val maxi = max(width, height)
        return List<List<Player>>(width + height - 1) { d ->
            if (d < mini - 1) { // d=0,1
                List<Player>(d + 1) { n -> data[d - n + width * (n)].player }
            } else if (d < maxi) { // d=2,3,4
                if (width >= height) List<Player>(mini) { n -> data[d - n + width * (n)].player }
                else List<Player>(mini) { n -> data[width - 1 - n + width * (d - width + 1 + n)].player  }
            } else { // d=5,6
                List<Player>(maxi + mini - d - 1) { n -> data[width - 1 - n + width * (-width + d + n + 1)].player }
            }
        }
    }

    /**
     * Returns a list of all diagonals (bottom-right -> top-left) of the array. Each diagonal itself is represented as a list of values.
     * ┌─────────────┐
     * │ ╲ ╲ ╲ ╲ ╲ ╲ │
     * │ ╲ ╲ ╲ ╲ ╲ ╲ │
     * │ ╲ ╲ ╲ ╲ ╲ ╲ │
     * └─────────────┘
     * version 1.01: added that height can be larger than width; fixed documentation
     */
    fun getDiagonals2() : List<List<Player>> {
        val mini = min(width, height)
        val maxi = max(width, height)
        return List<List<Player>>(width + height - 1) { d ->
            if (d < mini - 1) {
                List<Player>(d + 1) { n -> data[d - n + width * (height - 1 - n)].player }
            } else if (d < maxi) {// d=2,3,4
                if (width >= height) List<Player>(mini) { n -> data[d - n + width * (height - 1 - n)].player }
                else List<Player>(mini) { n -> data[width - 1 - n + width * (height - d + height - width - n)].player  }
                //width - 1 - n + width * (d - width + 1 + height - 1 - n)
            } else {
                List<Player>(maxi + mini - d - 1) { n -> data[width - n - 1 + width * (height - 1 + width - d - n - 1)].player }
            }
        }
    }

    /**
     * Returns a string-representation of the array.
     */
    override fun toString(): String {
        return data.foldIndexed("") { idx, acc, cur ->
            acc + cur + if(idx % width == width - 1) "\n" else ""
        }
    }
}
