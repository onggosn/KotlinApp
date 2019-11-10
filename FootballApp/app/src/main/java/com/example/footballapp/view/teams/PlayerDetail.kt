package com.example.footballapp.view.teams

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Layout
import com.example.footballapp.R
import com.example.footballapp.model.player.PlayerInfo
import com.example.footballapp.model.player.PlayerList
import com.example.footballapp.netservices.ApiRepository
import com.example.footballapp.presenter.PlayerPresenter
import com.example.footballapp.utils.invisible
import com.example.footballapp.utils.visible
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.player_detail.*
import kotlinx.android.synthetic.main.team_detail.toolbar
import kotlinx.android.synthetic.main.team_detail.toolbar_title
import org.jetbrains.anko.support.v4.onRefresh

class PlayerDetail : AppCompatActivity(), PlayerView {
    lateinit var playerId: String
    private lateinit var presenter: PlayerPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_detail)
        initToolbar()

        val id = intent.getStringExtra("playerId")
        playerId = id.toString()

        val request = ApiRepository()
        val gson = Gson()
        presenter = PlayerPresenter(this, request, gson)
        presenter.getDetailPlayer(playerId)

        swipeRefresh?.onRefresh {
            presenter.getDetailPlayer(playerId)
        }
    }

    private fun initToolbar(){
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        toolbar_title.text = "PLAYER DETAIL"
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

    override fun showPlayerList(data: List<PlayerList>) {
    }

    override fun showPlayerDetail(data: List<PlayerInfo>) {
        val born = "${data[0].dateBorn}, ${data[0].strBirthLocation}"
        Picasso.get().load(data[0].strCutout).into(playerImg)
        playerName.text = data[0].strPlayer
        playerPosition.text = data[0].strPosition
        playerNat.text = data[0].strNationality
        playerHeight.text = data[0].strHeight
        playerWidth.text = data[0].strWeight
        playerBorn.text = born
        playerDesc.text = data[0].strDescriptionEN
        playerDesc.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD)
        swipeRefresh?.isRefreshing = false
    }
}