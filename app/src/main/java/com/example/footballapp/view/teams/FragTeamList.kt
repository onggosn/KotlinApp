package com.example.footballapp.view.teams

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.ActivityOptionsCompat
import android.support.v4.app.Fragment

import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.footballapp.R
import com.example.footballapp.model.teamdetail.Team
import com.example.footballapp.netservices.ApiRepository
import com.example.footballapp.presenter.TeamInfoPresenter
import com.example.footballapp.utils.EspressoIdling
import com.example.footballapp.utils.invisible
import com.example.footballapp.utils.visible
import com.google.gson.Gson
import kotlinx.android.synthetic.main.content_listteam.*
import kotlinx.android.synthetic.main.frag_listteam.*
import org.jetbrains.anko.support.v4.onRefresh

class FragTeamList : Fragment(), TeamView {
    private var teamList: MutableList<Team> = mutableListOf()
    private lateinit var adapter: TeamListAdapter
    private lateinit var presenter: TeamInfoPresenter
    private lateinit var idLeague: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val idLeague = arguments?.getString("id")
        this.idLeague = idLeague.toString()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_listteam, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val request = ApiRepository()
        val gson = Gson()
        presenter = TeamInfoPresenter(this, request, gson)

        EspressoIdling.increment()
        presenter.getListTeam(idLeague)

        adapter = TeamListAdapter(teamList){
            partItem: Team -> sendTeamId(partItem)
        }

        rvListTeam.adapter = adapter
        rvListTeam.layoutManager = LinearLayoutManager(this.context)

        swipeRefresh?.onRefresh {
            EspressoIdling.increment()
            presenter.getListTeam(idLeague)
        }
    }

    companion object {
        fun newInstance(idLeague: String): FragTeamList {
            val args = Bundle()
            args.putString("id", idLeague)
            val fragment = FragTeamList()
            fragment.arguments = args
            return fragment
        }
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
        swipeRefresh?.isRefreshing = false
        teamList.clear()
        teamList.addAll(data)
        adapter.notifyDataSetChanged()
    }

    private fun sendTeamId(teamId: Team){
        val intent = Intent(this.context, TeamDetail::class.java)
        intent.putExtra("teamId", teamId.idTeam.toString())
        startActivity(intent)
    }

    override fun notFound() {}
}