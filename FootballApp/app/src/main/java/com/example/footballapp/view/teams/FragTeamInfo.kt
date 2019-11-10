package com.example.footballapp.view.teams

import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.Layout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.footballapp.R
import com.example.footballapp.model.teamdetail.Team
import com.example.footballapp.netservices.ApiRepository
import com.example.footballapp.presenter.TeamInfoPresenter
import com.example.footballapp.utils.EspressoIdling
import com.google.gson.Gson
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.frag_teaminfo.*

class FragTeamInfo : Fragment(), TeamView {
    private lateinit var teamId: String
    private lateinit var presenter: TeamInfoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val idTeam = arguments?.getString("id")
        this.teamId = idTeam.toString()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_teaminfo, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val request = ApiRepository()
        val gson = Gson()

        presenter = TeamInfoPresenter(this, request, gson)

        EspressoIdling.increment() //espresso idling (wait for async task)
        presenter.getInfoTeam(teamId)
    }

    companion object {
        fun newInstance(teamId: String): FragTeamInfo {
            val args = Bundle()
            args.putString("id", teamId)
            val fragment = FragTeamInfo()
            fragment.arguments = args
            return fragment
        }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showTeam(data: List<Team>) {
        if (!EspressoIdling.idlingResource.isIdleNow) {
            //Memberitahukan bahwa tugas sudah selesai dijalankan
            EspressoIdling.decrement()
        }

        val capacity = "Capacity : ${data[0].intStadiumCapacity}"
        teamDesc.text = data[0].strDescriptionEN
        teamDesc.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD)
        Picasso.get().load(data[0].strStadiumThumb).into(imgStadium)
        strStadium.text = data[0].strStadium
        stadiumLoc.text = data[0].strStadiumLocation
        stadiumCap.text = capacity
        stadiumDesc.text = data[0].strStadiumDescription
        stadiumDesc.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD)
        Picasso.get().load(data[0].strTeamJersey).into(imgJersey)
    }

    override fun notFound() {}
}