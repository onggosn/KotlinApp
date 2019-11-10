package com.example.footballapp.view.leaguedescription

import android.graphics.Color
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.text.Layout.JUSTIFICATION_MODE_INTER_WORD
import android.view.Gravity
import android.widget.*
import com.example.footballapp.R
import com.example.footballapp.model.League.LeagueDesc
import com.example.footballapp.netservices.ApiRepository
import com.example.footballapp.presenter.LeagueDescPresenter
import com.example.footballapp.view.matchlist.Match
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import org.jetbrains.anko.*
import com.example.footballapp.utils.*
import com.example.footballapp.view.standing.StandingList
import com.example.footballapp.view.teams.Teams
import com.example.footballapp.R.color.*

class LeagueDescription : AppCompatActivity(), LeagueDescView {

    private lateinit var progressBar: ProgressBar
    private lateinit var presenter: LeagueDescPresenter
    private lateinit var leagueid: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DescriptionUI().setContentView(this)

        progressBar = findViewById(R.id.progressBar)

        val idLeague = intent.getStringExtra("id")
        leagueid = idLeague.toString()

        val request = ApiRepository()
        val gson = Gson()
        presenter = LeagueDescPresenter(this, request, gson)

        EspressoIdling.increment() //espresso idling (wait for async task)
        presenter.getLeagueDesc(leagueid)

        val btnMatch = find<Button>(R.id.btn_match)
        btnMatch.setOnClickListener { startActivity<Match>("leagueMatch" to leagueid) }

        val btnTeam = find<Button>(R.id.btn_team)
        btnTeam.setOnClickListener { startActivity<Teams>("leagueId" to leagueid) }

        val btnStanding = find<Button>(R.id.btn_standing)
        btnStanding.setOnClickListener { startActivity<StandingList>("leagueId" to leagueid) }

        appBar()
    }

    class DescriptionUI : AnkoComponent<LeagueDescription> {
        override fun createView(ui: AnkoContext<LeagueDescription>) = with(ui)  {
            scrollView {
                lparams(width = matchParent, height = matchParent)
                id = R.id.leagueDesc
                relativeLayout {
                    lparams(width = matchParent, height = wrapContent)

                    linearLayout {
                        lparams(width = matchParent, height = matchParent)
                        orientation = LinearLayout.VERTICAL

                        imageView {
                            id = R.id.imgDesc
                            adjustViewBounds = true
                            scaleType = ImageView.ScaleType.FIT_XY
                        }.lparams(width = matchParent, height = wrapContent) {
                            horizontalMargin = dip(16)
                            verticalMargin = dip(16)
                        }

                        textView {
                            id = R.id.tvLeagueNameDesc
                            textSize = 24f
                            textColor = Color.BLACK
                        }.lparams(width = wrapContent, height = wrapContent) {
                            horizontalMargin = dip(16)
                            gravity = Gravity.CENTER
                        }

                        textView {
                            id = R.id.tvWebDesc
                            textSize = 18f
                            textColor = Color.BLACK

                        }.lparams(width = wrapContent, height = wrapContent) {
                            bottomMargin = dip(16)
                            gravity = Gravity.CENTER
                        }

                        linearLayout {
                            lparams(width = matchParent, height = wrapContent)
                            orientation = LinearLayout.HORIZONTAL
                            horizontalPadding = dip(16)

                            button(R.string.btnMatch) {
                                id = R.id.btn_match
                                textColor = Color.WHITE
                                backgroundColor = ContextCompat.getColor(context, colorBlack)
                                textSize = 18f
                            }.lparams(width = matchParent, height = wrapContent) {
                                bottomMargin = dip(16)
                                weight = 1f
                            }

                            button(R.string.btnTeams) {
                                id = R.id.btn_team
                                textColor = Color.WHITE
                                backgroundColor = ContextCompat.getColor(context, colorBlack)
                                textSize = 18f
                            }.lparams(width = matchParent, height = wrapContent) {
                                bottomMargin = dip(16)
                                weight = 1f
                            }

                            button(R.string.btnStanding) {
                                id = R.id.btn_standing
                                textColor = Color.WHITE
                                textSize = 18f
                                backgroundColor = ContextCompat.getColor(context, colorBlack)
                            }.lparams(width = matchParent, height = wrapContent) {
                                bottomMargin = dip(16)
                                weight = 1f
                            }
                        }

                        textView {
                            id = R.id.tvDesc
                            textSize = 24f
                            textColor = Color.BLACK
                        }.lparams(width = wrapContent, height = wrapContent) {
                            horizontalMargin = dip(16)

                        }
                    }
                    progressBar {
                    id = R.id.progressBar
                    }.lparams {
                    centerInParent()
                    }
                }
            }
        }
    }

    override fun showLoading() {
        progressBar.visible()
    }

    override fun hideLoading() {
        progressBar.invisible()
    }

    override fun showLeagueDesc(data: List<LeagueDesc>) {
        val leagueName = find<TextView>(R.id.tvLeagueNameDesc)
        val leagueDesc = find<TextView>(R.id.tvDesc)
        val leagueBadge = find<ImageView>(R.id.imgDesc)
        val leagueWeb = find<TextView>(R.id.tvWebDesc)
        val defBadges = "android.resource://com.example.submissionone/drawable/defimg.png"

        leagueName.text = data[0].leagueName ?: "404 resource not found"
        leagueDesc.text = data[0].leagueDesc ?: "404 resource not found"
        leagueDesc.setJustificationMode(JUSTIFICATION_MODE_INTER_WORD)
        leagueWeb.text = data[0].leagueWeb ?: "404 resource not found"
        Picasso.get().load(data[0].leagueBadge ?: defBadges).into(leagueBadge)
        if (!EspressoIdling.idlingResource.isIdleNow) {
            //Memberitahukan bahwa tugas sudah selesai dijalankan
            EspressoIdling.decrement()
        }
    }

    override fun onSupportNavigateUp() : Boolean {
        finish()
        return true
    }

    private fun appBar() {
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "League Detail"
    }
}