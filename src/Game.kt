import Game.Companion.gridSize
import Game.Companion.speed
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import java.awt.event.KeyEvent
import java.awt.event.KeyListener
import java.util.*
import javax.sound.sampled.AudioSystem
import javax.swing.JFrame
import javax.swing.JOptionPane
import javax.swing.JPanel
import kotlin.collections.HashSet
import javax.sound.sampled.AudioInputStream
import java.io.BufferedInputStream

/**
 * Created by Aditya on November 13, 2017.
 */
class Game : JFrame(), KeyListener {

    private val pressedList = HashSet<Int>()
    private val panel by lazy {
        object : JPanel() {
            override fun setSize(width: Int, height: Int) {
                super.setSize(Game.width, Game.height)
            }

            override fun paint(g: Graphics?) {
                super.paint(g)
                val graphics = g!!
                graphics.color = Color.BLACK
                graphics.fillRect(0, 0, Game.width, Game.height)
                graphics.color = Color.CYAN
                graphics.fillRect(monkeyPosition.x,
                        monkeyPosition.y,
                        monkeyDimens.monkeyWidth,
                        monkeyDimens.monkeyHeight)
/*                for (i in (0..Game.height).filter { it % speed == 0 })
                    graphics.fillRect(i, 0, 1, Game.height)
                for (i in (0..Game.width).filter { it % speed == 0 })
                    graphics.fillRect(0, i, Game.width, 1)*/
                graphics.color = Color.DARK_GRAY
                graphics.fillRect(bananaPosition.x, bananaPosition.y, gridSize, gridSize)
                graphics.color = Color.BLACK
                graphics.font = Font("TimesRoman", Font.PLAIN, 24)
                graphics.drawString("Score is $score    Time remaining is $timer", speed, Game.height + gridSize)

            }

        }
    }
    companion object {
        val gridSize = 50
        val width = gridSize * 10
        val height = gridSize * 10
        val monkeyDimens = MonkeyDimens(gridSize)
        private val bananaPosition = BananaPosition()
        val monkeyPosition = MonkeyPosition()
        val speed = 5
        var score = 0
        var timer = 31
    }


    override fun keyTyped(e: KeyEvent?) {}

    @Synchronized
    override fun keyPressed(e: KeyEvent?) {
        pressedList.add(e!!.keyCode)
        if (pressedList.size > 0) {
            val iterator = pressedList.iterator()
            while (iterator.hasNext())
                when (iterator.next()) {
                    37 -> goLeft()
                    38 -> goUp()
                    39 -> goRight()
                    40 -> goDown()
                }
            checkCollision()
        }
    }

    private fun checkCollision() {
        if (monkeyPosition.isOver(bananaPosition)) {
            Game.score += 1
            playSound()
        }
    }

    @Synchronized
    private fun playSound() {
        Thread(Runnable {
            try {
                val clip = AudioSystem.getClip()
                val audioSrc = javaClass.getResourceAsStream("/music.wav")
                val bufferedIn = BufferedInputStream(audioSrc)
                val audioStream = AudioSystem.getAudioInputStream(bufferedIn)
                clip.open(audioStream)
                clip.start()
            } catch (e: Exception) {
                System.err.println(e.message)
            }
        }).start()
    }

    private fun goLeft() {
        monkeyPosition.goLeft()
        repaint()
    }

    private fun goRight() {
        monkeyPosition.goRight()
        repaint()
    }

    private fun goDown() {
        monkeyPosition.goDown()
        repaint()
    }

    private fun goUp() {
        monkeyPosition.goUp()
        repaint()
    }

    @Synchronized
    override fun keyReleased(e: KeyEvent?) {
        pressedList.clear()
    }



    private fun startGame() {
        Timer().scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                timer -= 1
                repaint()
                if (timer == 0) {
                    cancel()
                    val opt = JOptionPane.showConfirmDialog(null, "Your score is $score\nDo you want to restart?", "Game Over", JOptionPane.YES_NO_OPTION)
                    if (opt == JOptionPane.YES_OPTION) {
                        timer = 31
                        score = 0
                        startGame()
                    }
                    if(opt == JOptionPane.NO_OPTION){
                        System.exit(0)
                    }
                }
            }
        }, 0, 1000)
    }

    init {
        defaultCloseOperation = JFrame.EXIT_ON_CLOSE
        isVisible = true
        title = "Monkey Banana game"
        setSize(Game.width + speed, Game.height + 100)
        isResizable = false
        add(panel)
        addKeyListener(this)
        bananaPosition.generateRandomPosition()
        setLocationRelativeTo(null)

        startGame()
    }

}



fun main(args: Array<String>) {
    Game()
}