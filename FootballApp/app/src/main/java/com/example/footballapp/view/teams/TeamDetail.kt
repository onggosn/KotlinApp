package com.example.footballapp.view.teams

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.footballapp.R
import com.example.footballapp.db.FavoriteTeam
import com.example.footballapp.db.database
import com.example.footballapp.model.teamdetail.Team
import com.example.footballapp.netservices.ApiRepository
import com.example.footballapp.presenter.TeamInfoPresenter
import com.example.footballapp.utils.EspressoIdling
import com.example.footballapp.utils.invisible
import com.example.footballapp.utils.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.team_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh

class TeamDetail : AppCompatActivity(), TeamView {


    private lateinit var teamId: String
    private lateinit var presenter: TeamInfoPresenter
    private var menuItem: Menu? = null
    private var isFavorite: Boolean = false
    private lateinit var team: Team

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.team_detail)

        initToolbar()

        val id = intent.getStringExtra("teamId")
        teamId = id.toString()

        favoriteState()

        val fragmentAdapter = TeamDetailAdapter(supportFragmentManager, teamId)
        view_pager.adapter = fragmentAdapter
        tab_layout.setupWithViewPager(view_pager)

        //initialization for search mechanism
        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamInfoPresenter(this, request, gson)

        EspressoIdling.increment()
        presenter.getInfoTeam(teamId)

        swipeRefreshTeamDetail?.onRefresh {
            EspressoIdling.increment()
            presenter.getInfoTeam(teamId)
        }

    }

    private fun initToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_title.text = "TEAM DETAIL"
        supportActionBar?.title = ""
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun showLoading() {
        viewprogressBar.visible()
    }

    override fun hideLoading() {
        viewprogressBar.invisible()
    }

    override fun showTeam(data: List<Team>) {
        if (!EspressoIdling.idlingResource.isIdleNow) {
            //Memberitahukan bahwa tugas sudah selesai dijalankan
            EspressoIdling.decrement()
        }
        team = Team(
            idTeam = data[0].idTeam,
            strTeam = data[0].strTeam,
            strTeamBadge = data[0].strTeamBadge
        )

        teamName.text = data[0].strTeam
        teamWeb.text = data[0].strWebsite
        Picasso.get().load(data[0].strTeamBadge).into(teamBadge)
        swipeRefreshTeamDetail?.isRefreshing = false
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.favbutton, menu)
        menuItem = menu
        setFav()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            android.R.id.home -> {
                finish()
                true
            }
            R.id.add_to_favorite -> {
                if (isFavorite) removeFromFav() else addToFavorite()
                isFavorite = !isFavorite
                setFav()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun addToFavorite(){
        try {
            database.use {
                insert(
                    FavoriteTeam.TABLE_FAVTEAM,
                    FavoriteTeam.TEAM_ID to team.idTeam,
                    FavoriteTeam.TEAM_NAME to team.strTeam,
                    FavoriteTeam.TEAM_BADGE to team.strTeamBadge)
            }
            swipeRefreshTeamDetail.snackbar("Added to Favorite")
        } catch (e: SQLiteConstraintException) {
            swipeRefreshTeamDetail.snackbar(e.localizedMessage).show()
        }
    }

    private fun removeFromFav(){
        try {
            database.use{
                delete(FavoriteTeam.TABLE_FAVTEAM, "(TEAM_ID = {id})", "id" to teamId)
            }
            swipeRefreshTeamDetail.snackbar("Remove from Favorite")
        }catch (e: SQLiteConstraintException){
            swipeRefreshTeamDetail.snackbar(e.localizedMessage).show()
        }
    }

    private fun favoriteState(){
        database.use {
            val result = select(FavoriteTeam.TABLE_FAVTEAM).whereArgs("(TEAM_ID = {id})", "id" to teamId)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun setFav(){
        if(isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }

    override fun notFound() {}
}