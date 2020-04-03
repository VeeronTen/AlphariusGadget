package veeronten.alphariusgadget.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.rxkotlin.subscribeBy
import kotlinx.android.synthetic.main.activity_main.*
import veeronten.alphariusgadget.R
import veeronten.alphariusgadget.computer.AwerageCardsComputer
import veeronten.alphariusgadget.computer.AwerageTrapsComputer
import veeronten.alphariusgadget.computer.FatigueChanceComputer
import veeronten.alphariusgadget.computer.StunChanceComputer
import veeronten.alphariusgadget.model.Deck

class MainActivity : AppCompatActivity() {

    val sourceDeck = Deck.createDefaultDeck()

    val stunChanceComputer = StunChanceComputer(sourceDeck)
    val fatigueChanceComputer = FatigueChanceComputer(sourceDeck)
    val awerageTrapsComputer = AwerageTrapsComputer(sourceDeck)
    val awerageCardsComputer = AwerageCardsComputer(sourceDeck)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onResume() {
        super.onResume()
        addStunBtn.setOnClickListener {
            sourceDeck.addStun()
        }

        addDrawBtn.setOnClickListener {
            sourceDeck.addDraw()
        }

        addDmgBtn.setOnClickListener {
            sourceDeck.addDmg()
        }

        addKillBtn.setOnClickListener {
            sourceDeck.addKill()
        }

        reduseStunBtn.setOnClickListener {
            sourceDeck.reduseStun()
        }

        reduseDrawBtn.setOnClickListener {
            sourceDeck.reduseDraw()
        }

        reduseDmgBtn.setOnClickListener {
            sourceDeck.reduseDmg()
        }

        reduseKillBtn.setOnClickListener {
            sourceDeck.reduseKill()
        }
        stunChanceComputer.chanceComputation.observeOn(AndroidSchedulers.mainThread()).subscribeBy(
            onNext = { stunChanceTv.text = "$it%" },
            onComplete = { Log.d("jojo", "stunChanceComputer complete") },
            onError = {
                Log.d("jojo", "stunChanceComputer error")
                it.printStackTrace()
            }
        )

        fatigueChanceComputer.chanceComputation.observeOn(AndroidSchedulers.mainThread()).subscribeBy(
            onNext = { fatigueChanceTv.text = "$it%" },
            onComplete = { Log.d("jojo", "fatigueChanceComputer complete") },
            onError = {
                Log.d("jojo", "fatigueChanceComputer error")
                it.printStackTrace()
            }
        )

        awerageTrapsComputer.countComputation.observeOn(AndroidSchedulers.mainThread()).subscribeBy(
            onNext = { trapsTv.text = "$it" },
            onComplete = { Log.d("jojo", "awerageTrapsComputer complete") },
            onError = {
                Log.d("jojo", "awerageTrapsComputer error")
                it.printStackTrace()
            }
        )

        awerageCardsComputer.countComputation.observeOn(AndroidSchedulers.mainThread()).subscribeBy(
            onNext = { cardsTv.text = "$it" },
            onComplete = { Log.d("jojo", "awerageCardsComputer complete") },
            onError = {
                Log.d("jojo", "awerageCardsComputer error")
                it.printStackTrace()
            }
        )

        Observable.combineLatest(sourceDeck.trapsInDeck, sourceDeck.cardsInDeck, BiFunction<Int, Int, Pair<Int, Int>> { traps, cards ->  Pair(traps, cards)})
            .observeOn(AndroidSchedulers.mainThread()).subscribeBy(
                onNext = { trapsCardsTv.text = "${it.first}/${it.second}" },
                onComplete = { Log.d("jojo", "trapscards complete") },
                onError = {
                    Log.d("jojo", "trapscards error")
                    it.printStackTrace()
                }
            )




        sourceDeck.stunsInDeck.observeOn(AndroidSchedulers.mainThread()).subscribeBy(
            onNext = { stunCounterTv.text = "$it" },
            onComplete = { Log.d("jojo", "stunsInDeck complete") },
            onError = {
                Log.d("jojo", "stunsInDeck error")
                it.printStackTrace()
            }
        )

        sourceDeck.drawsInDeck.observeOn(AndroidSchedulers.mainThread()).subscribeBy(
            onNext = { drawCounterTv.text = "$it" },
            onComplete = { Log.d("jojo", "drawsInDeck complete") },
            onError = {
                Log.d("jojo", "drawsInDeck error")
                it.printStackTrace()
            }
        )

        sourceDeck.dmgsInDeck.observeOn(AndroidSchedulers.mainThread()).subscribeBy(
            onNext = { dmgCounterTv.text = "$it" },
            onComplete = { Log.d("jojo", "dmgsInDeck complete") },
            onError = {
                Log.d("jojo", "dmgsInDeck error")
                it.printStackTrace()
            }
        )

        sourceDeck.killsInDeck.observeOn(AndroidSchedulers.mainThread()).subscribeBy(
            onNext = { killCounterTv.text = "$it" },
            onComplete = { Log.d("jojo", "killsInDeck complete") },
            onError = {
                Log.d("jojo", "killsInDeck error")
                it.printStackTrace()
            }
        )

        sourceDeck.updated.onNext(Unit) //todo костыль для запуска трапс кардс


//        SourceDeck.trapsCardsInDeck.subscribeBy(
//            onNext = { trapsCardsTv.text = "${it.first}/${it.second}" },
//            onComplete = { Log.d("jojo", "trapsCardsInDeck complete") },
//            onError = { Log.d("jojo", "trapsCardsInDeck error") }
//        )
//
//        dmgCounterComputer.countComputation.subscribeBy(
//            onNext = { dmgTv.text = "$it" },
//            onComplete = { Log.d("jojo", "dmgCounterComputer complete") },
//            onError = { Log.d("jojo", "dmgCounterComputer error") }
//        )

        //trapsTv cardsTv

    }
}
