package com.example.tictactoe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.gridlayout.widget.GridLayout
import android.widget.TextView
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.emitter.Emitter
import nl.dionsegijn.konfetti.xml.KonfettiView
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

//    declare variables
    private var player = 0
    private var isempty = arrayOf(2, 2, 2, 2, 2, 2, 2, 2, 2)
    private var finish = 0
    private val winingposition = arrayOf(arrayOf(0, 1, 2), arrayOf(3, 4, 5), arrayOf(6, 7, 8), arrayOf(0, 3, 6), arrayOf(1, 4, 7), arrayOf(2, 5, 8), arrayOf(0, 4, 8), arrayOf(2, 4, 6) )

//    function to add fading animation
    fun fadein(view:View){
        if(finish == 0 && 2 in isempty){
            val counter = view as ImageView
            counter.visibility = View.VISIBLE
            val tappedcounter = counter.tag.toString().toInt()
            if(player == 0 && isempty[tappedcounter] == 2){
                isempty[tappedcounter] = player
                player = 1
                counter.setImageResource(R.drawable.x)
            }
            else if(player == 1 && isempty[tappedcounter] == 2){
                isempty[tappedcounter] = player
                player = 0
                counter.setImageResource(R.drawable.o)
            }
            view.animate().alpha(1F).duration = 1000

//            konfettiview is used to show a confetti for the winner
            val viewKonfetti = findViewById<KonfettiView>(R.id.confetti)
            val party = Party(
                speed = 0f,
                maxSpeed = 30f,
                damping = 0.9f,
                spread = 360,
                colors = listOf(0x33FF76, 0xFFCD33, 0x3433FF, 0xFF33C4, 0x33FFD8, 0xFF3333),
                emitter = Emitter(duration = 100, TimeUnit.MILLISECONDS).max(100),
                position = Position.Relative(0.5, 0.3))

            if(2 !in isempty){
                finish = 1
                val winner = "It's a Draw"
                val result = findViewById<TextView>(R.id.result)
                val button = findViewById<Button>(R.id.button)
                result.text = winner
                button.animate().alpha(1f).duration = 1000
                result.animate().alpha(1f).duration = 1000
            }

            for(winingpos in winingposition){
                if(isempty[winingpos[0]] == isempty[winingpos[1]] && isempty[winingpos[1]] == isempty[winingpos[2]] && isempty[winingpos[0]] != 2){
                    finish = 1
                    val winner: String = if(player == 0){
                        "O has WON"
                    } else{
                        "X has WON"
                    }
                    val result = findViewById<TextView>(R.id.result)
                    val button = findViewById<Button>(R.id.button)
                    result.text = winner
                    button.animate().alpha(1f).duration = 1000
                    result.animate().alpha(1f).duration = 1000
                    viewKonfetti.start(party)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            finish = 0
            val result = findViewById<TextView>(R.id.result)
            button.alpha = 0f
            result.alpha = 0f
            val gridLayout = findViewById<GridLayout>(R.id.board_grid)
            player = 0
            for(i in 0..8)
            {
                val child : ImageView = gridLayout.getChildAt(i) as ImageView
                child.setImageResource(android.R.color.transparent)
                isempty[i] = 2
            }
        }


    }
}