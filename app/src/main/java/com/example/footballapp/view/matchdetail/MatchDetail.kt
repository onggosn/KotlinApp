package com.example.footballapp.view.matchdetail

import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.footballapp.R
import com.example.footballapp.db.FavoriteMatch
import com.example.footballapp.db.database
import com.example.footballapp.model.matchdetail.MatchDesc
import com.example.footballapp.model.teamdetail.Team
import com.example.footballapp.netservices.ApiRepository
import com.example.footballapp.presenter.MatchDetailPresenter
import com.example.footballapp.presenter.TeamPresenter
import com.example.footballapp.utils.EspressoIdling
import com.example.footballapp.utils.dateFormat
import com.example.footballapp.utils.invisible
import com.example.footballapp.utils.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.match_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import org.jetbrains.anko.design.snackbar
import org.jetbrains.anko.support.v4.onRefresh

class MatchDetail : AppCompatActivity(), MatchDetailView {
    private lateinit var presenter: MatchDetailPresenter
    private lateinit var presenterTeam: TeamPresenter
    private lateinit var teamIdHome: String
    private lateinit var teamIdAway: String
    private var menuItem: Menu? = null
    private lateinit var match: MatchDesc
    private lateinit var id: String
    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.match_detail)

        val request = ApiRepository()
        val gson = Gson()
        id = intent.getStringExtra("MATCHID")

        favoriteState()
        presenter = MatchDetailPresenter(this, request, gson)
        EspressoIdling.increment()
        presenter.getMatchDetail(id)

        swipRefreshMatchDetail.onRefresh {
            EspressoIdling.increment()
            presenter.getMatchDetail(id)
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showMatchDetail(data: List<MatchDesc>) {
        if (!EspressoIdling.idlingResource.isIdleNow) {
            //Memberitahukan bahwa tugas sudah selesai dijalankan
            EspressoIdling.decrement()
        }
        match = MatchDesc(
            eventId = data[0].eventId,
            dateMatch = dateFormat("${data[0].dateMatch} ${data[0].stringTime}"),
            homeName = data[0].homeName,
            awayName = data[0].awayName,
            homeScore = data[0].homeScore,
            awayScore = data[0].awayScore)

        swipRefreshMatchDetail.isRefreshing = false
        teamIdHome = data[0].idHome.toString()
        teamIdAway = data[0].idAway.toString()

        val request = ApiRepository()
        val gson = Gson()

        presenterTeam = TeamPresenter(this, request, gson)

        EspressoIdling.increment()
        presenterTeam.getBadge(teamIdHome, teamIdAway)

        tvEventDate.text = dateFormat("${data[0].dateMatch} ${data[0].stringTime}")
        tvHomeTeam.text = data[0].homeName
        tvHomeScore.text = data[0].homeScore.toString()
        tvAwayTeam.text = data[0].awayName
        tvAwayScore.text = data[0].awayScore.toString()
        tvHomeGoals.text = data[0].homeGoalScorer?.replace(";", "\n")
        tvAwayGoals.text = data[0].awayGoalScorer?.replace(";", "\n")
        tvYellowHome.text = data[0].homeYellowCards?.replace(";", "\n")
        tvYellowAway.text = data[0].awayYellowCards?.replace(";", "\n")
        tvRedHome.text = data[0].homeRedCards?:"-"
        tvRedAway.text = data[0].awayRedCards?:"-"
        tvHomeKeeper.text = data[0].homeGoalKeeper?.replace(";", "\n")
        tvAwayKeeper.text = data[0].awayGoalKeeper?.replace(";", "\n")
        tvHomeDef.text = data[0].homeDefense?.replace(";", "\n")
        tvAwayDef.text = data[0].awayDefense?.replace(";", "\n")
        tvHomeMid.text = data[0].homeMidfield?.replace(";", "\n")
        tvAwayMid.text = data[0].awayMidfield?.replace(";", "\n")
        tvHomeFw.text = data[0].homeForward?.replace(";", "\n")
        tvAwayFw.text = data[0].awayForward?.replace(";", "\n")
        tvHomeSub.text = data[0].homeSubtitute?.replace(";", "\n")
        tvAwaySub.text = data[0].awaySubtitute?.replace(";", "\n")

        supportActionBar?.title = data[0].eventName
    }

    override fun showBadge(dataHome: List<Team>, dataAway: List<Team>){
        if (!EspressoIdling.idlingResource.isIdleNow) {
            //Memberitahukan bahwa tugas sudah selesai dijalankan
            EspressoIdling.decrement()
        }
        Picasso.get().load("${dataHome[0].strTeamBadge}/preview").into(imgHomeTeam)
        Picasso.get().load("${dataAway[0].strTeamBadge}/preview").into(imgAwayTeam)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
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
        try{
            database.use {
                insert(FavoriteMatch.TABLE_FAVMATCH,
                    FavoriteMatch.MATCH_ID to match.eventId,
                    FavoriteMatch.DATE_MATCH to match.dateMatch,
                    FavoriteMatch.HOME_TEAM to match.homeName,
                    FavoriteMatch.AWAY_TEAM to match.awayName,
                    FavoriteMatch.HOME_SCORE to match.homeScore,
                    FavoriteMatch.AWAY_SCORE to match.awayScore)
            }
            swipRefreshMatchDetail.snackbar("Added to Favorite")
        }catch (e: SQLiteConstraintException){
            swipRefreshMatchDetail.snackbar(e.localizedMessage).show()
        }
    }

    private fun removeFromFav(){
        try {
            database.use{
                delete(FavoriteMatch.TABLE_FAVMATCH, "(MATCH_ID = {id})", "id" to id)
            }
            swipRefreshMatchDetail.snackbar("Remove from Favorite")
        }catch (e: SQLiteConstraintException){
            swipRefreshMatchDetail.snackbar(e.localizedMessage).show()
        }
    }

    private fun favoriteState(){
        database.use {
            val result = select(FavoriteMatch.TABLE_FAVMATCH).whereArgs("(MATCH_ID = {id})", "id" to id)
            val favorite = result.parseList(classParser<FavoriteMatch>())
            if (!favorite.isEmpty()) isFavorite = true
        }
    }

    private fun setFav(){
        if(isFavorite)
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_added_to_favorites)
        else
            menuItem?.getItem(0)?.icon = ContextCompat.getDrawable(this, R.drawable.ic_add_to_favorites)
    }
}