import Game.Companion.gridSize
import Game.Companion.speed

/**
 * Created by Aditya on November 15, 2017.
 */
class MonkeyPosition(var x: Int = 0, var y: Int = 0) {

    fun goLeft() {
        x -= speed
        if (x < 0) x = 0

    }

    fun goRight() {
        x += speed
        if (x + Game.monkeyDimens.monkeyWidth > Game.width) x = Game.width - Game.monkeyDimens.monkeyWidth
    }

    fun goUp() {
        y -= speed
        if (y < 0) y = 0
    }

    fun goDown() {
        y += speed
        if (y + Game.monkeyDimens.monkeyHeight > Game.height) y = Game.height - Game.monkeyDimens.monkeyHeight
    }

    fun isOver(bananaPosition: BananaPosition): Boolean {
        if (this.x + Game.monkeyDimens.monkeyWidth > bananaPosition.x &&
                this.x < bananaPosition.x + gridSize &&
                this.y + Game.monkeyDimens.monkeyHeight > bananaPosition.y &&
                this.y < bananaPosition.y + gridSize) {
            bananaPosition.generateRandomPosition()
            return true
        }
        return false
    }
}
