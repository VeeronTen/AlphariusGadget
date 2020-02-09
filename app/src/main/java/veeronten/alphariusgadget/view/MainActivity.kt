package veeronten.alphariusgadget.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*
import veeronten.alphariusgadget.R
import veeronten.alphariusgadget.SourceDeck
import veeronten.alphariusgadget.computer.StunComputer

class MainActivity : AppCompatActivity() {

    val stanComputer = StunComputer()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        addStunBtn.setOnClickListener {
            SourceDeck.addStun()
        }

        addDrawBtn.setOnClickListener {
            SourceDeck.addDraw()
        }

        addDmgBtn.setOnClickListener {
            SourceDeck.addDmg()
        }

        addKillBtn.setOnClickListener {
            SourceDeck.addKill()
        }

        stanComputer.computation.subscribeBy(
            onNext = {
                stunChanseTv.text = it.toString()// + "%"
            },
            onComplete = {
                Log.d("jojo", "complete")

            },
            onError = {
                Log.d("jojo", "error")
                it.printStackTrace()
            }
        )
    }
}
