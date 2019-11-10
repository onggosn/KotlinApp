package com.example.footballapp.view

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import com.example.footballapp.R
import com.example.footballapp.view.leaguedescription.RecyclerViewAdapter
import com.example.footballapp.model.League.League
import com.example.footballapp.model.League.LeagueData
import com.example.footballapp.view.leaguedescription.LeagueDescription
import org.jetbrains.anko.*
import org.jetbrains.anko.recyclerview.v7.recyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rv: RecyclerView
    private var list: ArrayList<League> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivityUI().setContentView(this)

        list.addAll(LeagueData.listData)
        rv = findViewById(R.id.rvLeague)
        rv.layoutManager = GridLayoutManager(this, 2)

        rv.adapter = RecyclerViewAdapter(this, list) {
            startActivity<LeagueDescription>("id" to it.leagueID)
        }

    }

    //Anko Layout
    class MainActivityUI : AnkoComponent<MainActivity> {
        override fun createView(ui: AnkoContext<MainActivity>) = with(ui) {
            recyclerView {
                id = R.id.rvLeague
            }
        }
    }
}
