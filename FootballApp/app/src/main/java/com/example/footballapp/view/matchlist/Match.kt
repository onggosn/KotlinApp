package com.example.footballapp.view.matchlist

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.view.Menu
import android.widget.Toast
import com.example.footballapp.R
import com.example.footballapp.model.matchlist.NextMatch
import com.example.footballapp.model.matchlist.PrevMatch
import com.example.footballapp.netservices.ApiRepository
import com.example.footballapp.presenter.PrevMatchPresenter
import com.example.footballapp.utils.EspressoIdling
import com.example.footballapp.view.matchdetail.MatchDetail
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_match.*
import kotlinx.android.synthetic.main.frag_prevmatch.*

class Match : AppCompatActivity(), MatchView {
    private var match: MutableList<PrevMatch> = mutableListOf()
    private lateinit var adapter: PrevMatchAdapter
    private lateinit var presenter: PrevMatchPresenter
    lateinit var leagueId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_match)

        initToolbar()

        val idLeague = intent.getStringExtra("leagueMatch")
        leagueId = idLeague.toString()

        val fragmentAdapter = MatchAdapter(supportFragmentManager, leagueId)
        view_pager.adapter = fragmentAdapter
        tab_layout.setupWithViewPager(view_pager)

        //initialization for search mechanism
        val request = ApiRepository()
        val gson = Gson()
        presenter = PrevMatchPresenter(this, request, gson)

        adapter = PrevMatchAdapter(match){
                partItem: PrevMatch -> sendMatchId(partItem)
        }
    }

    private fun initToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_title.text = "MATCH LIST"
        supportActionBar?.title = ""
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)
        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                fragPrevMatch.adapter = adapter
                fragPrevMatch.layoutManager = LinearLayoutManager(applicationContext)
                EspressoIdling.increment() //espresso idling (wait for async task)
                presenter.getEvent(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == ""){
                    fragPrevMatch.adapter = adapter
                    fragPrevMatch.layoutManager = LinearLayoutManager(applicationContext)
                    EspressoIdling.increment() //espresso idling (wait for async task)
                    presenter.getPrevMatch(leagueId)
                }
                return false
            }
        })
        return true
    }

    private fun sendMatchId(matchId: PrevMatch){
        val intent = Intent(this, MatchDetail::class.java)
        intent.putExtra("MATCHID", matchId.eventId.toString())
        startActivity(intent)
    }

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun showPrevMatch(data: List<PrevMatch>) {
        if (!EspressoIdling.idlingResource.isIdleNow) {
            //Memberitahukan bahwa tugas sudah selesai dijalankan
            EspressoIdling.decrement()
        }
        swipeRefresh?.isRefreshing = false
        match.clear()
        match.addAll(data)
        adapter.notifyDataSetChanged()
    }

    override fun showNextMatch(data: List<NextMatch>) {}

    override fun notFound() {
        Toast.makeText(this, "Data Not Found!", Toast.LENGTH_SHORT).show()
    }
}