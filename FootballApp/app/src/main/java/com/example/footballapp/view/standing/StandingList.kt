package com.example.footballapp.view.standing

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.example.footballapp.R
import com.example.footballapp.model.standing.Standing
import com.example.footballapp.netservices.ApiRepository
import com.example.footballapp.presenter.StandingPresenter
import com.example.footballapp.utils.EspressoIdling
import com.example.footballapp.utils.invisible
import com.example.footballapp.utils.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.frag_prevmatch.*
import kotlinx.android.synthetic.main.standing_list.*
import kotlinx.android.synthetic.main.standing_list.swipeRefresh
import kotlinx.android.synthetic.main.standing_list.viewprogressBar
import org.jetbrains.anko.support.v4.onRefresh

class StandingList : AppCompatActivity(), StandingView {
    private var standing: MutableList<Standing> = mutableListOf()
    private lateinit var leagueId: String
    private lateinit var presenter: StandingPresenter
    private lateinit var adapter: StandingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.standing_list)

        initToolbar()
        val id = intent.getStringExtra("leagueId")
        leagueId = id.toString()

        val request = ApiRepository()
        val gson = Gson()
        presenter = StandingPresenter(this, request, gson)
        presenter.getStandingList(leagueId)
        adapter = StandingAdapter(standing)
        rvStanding.adapter = adapter
        rvStanding.layoutManager = LinearLayoutManager(this)

        swipeRefresh?.onRefresh {
            EspressoIdling.increment() //espresso idling (wait for async task)
            presenter.getStandingList(leagueId)
        }
    }

    override fun showLoading() {
        viewprogressBar.visible()
    }

    override fun hideLoading() {
        viewprogressBar.invisible()
    }

    override fun showStanding(data: List<Standing>) {
        swipeRefresh?.isRefreshing = false
        standing.clear()
        standing.addAll(data)
        adapter.notifyDataSetChanged()
    }

    private fun initToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_title.text = "LEAGUE STANDING"
        supportActionBar?.title = ""
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}