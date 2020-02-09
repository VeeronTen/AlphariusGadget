package veeronten.alphariusgadget.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*
import veeronten.alphariusgadget.R
import veeronten.alphariusgadget.SourceDeck
import veeronten.alphariusgadget.computer.FatigueChanceComputer
import veeronten.alphariusgadget.computer.StunChanceComputer

class MainActivity : AppCompatActivity() {

    val stunChanceComputer = StunChanceComputer()
    val fatigueChanceComputer = FatigueChanceComputer()

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

        stunChanceComputer.chanceComputation.subscribeBy(
            onNext = { stunChanceTv.text = "$it%" },
            onComplete = { Log.d("jojo", "stunChanceComputer complete") },
            onError = { Log.d("jojo", "stunChanceComputer error") }
        )

        fatigueChanceComputer.chanceComputation.subscribeBy(
            onNext = { fatigueChanceTv.text = "$it%" },
            onComplete = { Log.d("jojo", "fatigueChanceComputer complete") },
            onError = { Log.d("jojo", "fatigueChanceComputer error") }
        )

        SourceDeck.trapsCardsInDeck.subscribeBy(
            onNext = { trapsCardsTv.text = "${it.first}/${it.second}" },
            onComplete = { Log.d("jojo", "trapsCardsInDeck complete") },
            onError = { Log.d("jojo", "trapsCardsInDeck error") }
        )
    }
}
