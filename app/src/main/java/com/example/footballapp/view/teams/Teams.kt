package com.example.footballapp.view.teams

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.SearchView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.widget.Toast
import com.example.footballapp.R
import com.example.footballapp.model.teamdetail.Team
import com.example.footballapp.netservices.ApiRepository
import com.example.footballapp.presenter.TeamInfoPresenter
import com.example.footballapp.utils.EspressoIdling
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_team.*
import kotlinx.android.synthetic.main.frag_listteam.*

class Teams : AppCompatActivity(), TeamView {

    private var teams: MutableList<Team> = mutableListOf()
    private lateinit var presenter: TeamInfoPresenter
    private lateinit var adapter: TeamListAdapter
    lateinit var leagueId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_team)
        initToolbar()

        val idLeague = intent.getStringExtra("leagueId")
        leagueId = idLeague.toString()

        val fragmentAdapter = TeamAdapter(supportFragmentManager, leagueId)
        view_pager.adapter = fragmentAdapter
        tab_layout.setupWithViewPager(view_pager)

        //initialization for search mechanism
        val request = ApiRepository()
        val gson = Gson()

        presenter = TeamInfoPresenter(this, request, gson)

        adapter = TeamListAdapter(teams){
                partItem: Team -> sendTeamId(partItem)
        }
    }

    private fun initToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_title.text = "TEAM LIST"
        supportActionBar?.title = ""
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_team, menu)
        val manager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchItem = menu?.findItem(R.id.searchTeam)
        val searchView = searchItem?.actionView as SearchView

        searchView.setSearchableInfo(manager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                rvListTeam.adapter = adapter
                rvListTeam.layoutManager = LinearLayoutManager(applicationContext)
                EspressoIdling.increment()
                presenter.searchTeam(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText == ""){
                    rvListTeam.adapter = adapter
                    rvListTeam.layoutManager = LinearLayoutManager(applicationContext)
                    EspressoIdling.increment()
                    presenter.getListTeam(leagueId)
                }
                return false
            }

        })
        return true
    }

    override fun showLoading() {}

    override fun hideLoading() {}

    override fun showTeam(data: List<Team>) {
        if (!EspressoIdling.idlingResource.isIdleNow) {
            //Memberitahukan bahwa tugas sudah selesai dijalankan
            EspressoIdling.decrement()
        }

        teams.clear()
        teams.addAll(data)
        adapter.notifyDataSetChanged()
    }

    private fun sendTeamId(teamId: Team){
        val intent = Intent(this, TeamDetail::class.java)
        intent.putExtra("teamId", teamId.idTeam.toString())
        startActivity(intent)
    }

    override fun notFound() {
        Toast.makeText(this, "Data Not Found!", Toast.LENGTH_SHORT).show()
    }
}
