import Game.Companion.gridSize
import java.util.*

/**
 * Created by Aditya on November 15, 2017.
 */
class BananaPosition(var x: Int = 0, var y: Int = 0) {

    fun generateRandomPosition() {
        x = Random().nextInt(Game.gridSize) * 10
        y = Random().nextInt(Game.gridSize) * 10
        if (x < 0) x = 0
        if (y < 0) y = 0
        if (x + gridSize > Game.width) x = Game.width - gridSize
        if (y + gridSize > Game.height) y = Game.height - gridSize
        if ((x >= Game.monkeyPosition.x && x <= Game.monkeyPosition.x + Game.monkeyDimens.monkeyWidth) &&
                (y >= Game.monkeyPosition.y && y <= Game.monkeyPosition.y + Game.monkeyDimens.monkeyHeight)) {
            generateRandomPosition()
        }
    }
}