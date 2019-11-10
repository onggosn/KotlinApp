package com.example.footballapp.view.matchlist

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.Toast
import com.example.footballapp.R
import com.example.footballapp.model.matchlist.NextMatch
import com.example.footballapp.model.matchlist.PrevMatch
import com.example.footballapp.netservices.ApiRepository
import com.example.footballapp.presenter.PrevMatchPresenter
import com.example.footballapp.utils.EspressoIdling
import com.example.footballapp.utils.invisible
import com.example.footballapp.utils.visible
import com.example.footballapp.view.matchdetail.MatchDetail
import com.google.gson.Gson
import kotlinx.android.synthetic.main.frag_prevmatch.*
import org.jetbrains.anko.support.v4.onRefresh

class FragPrevMatch : Fragment(), MatchView {
    private var match: MutableList<PrevMatch> = mutableListOf()
    private lateinit var adapter: PrevMatchAdapter
    private lateinit var presenter: PrevMatchPresenter
    private lateinit var idLeague: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val idLeague = arguments?.getString("id")
        this.idLeague = idLeague.toString()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_prevmatch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val request = ApiRepository()
        val gson = Gson()
        presenter = PrevMatchPresenter(this, request, gson)

        EspressoIdling.increment() //espresso idling (wait for async task)
        presenter.getPrevMatch(idLeague)

        adapter = PrevMatchAdapter(match){
            partItem: PrevMatch -> sendMatchId(partItem)
        }

        fragPrevMatch.adapter = adapter
        fragPrevMatch.layoutManager = LinearLayoutManager(this.context)

        swipeRefresh?.onRefresh {
            EspressoIdling.increment() //espresso idling (wait for async task)
            presenter.getPrevMatch(idLeague)
        }
    }

    override fun showLoading() {
        viewprogressBar?.visible()
    }

    override fun hideLoading() {
        viewprogressBar?.invisible()
    }

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

    override fun notFound() {
        Toast.makeText(this.context, "Data Not Found!", Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance(idLeague: String): FragPrevMatch {
            val args = Bundle()
            args.putString("id", idLeague)
            val fragment = FragPrevMatch()
            fragment.arguments = args
            return fragment
        }
    }

    private fun sendMatchId(matchId: PrevMatch){
        val intent = Intent(this.context, MatchDetail::class.java)
        intent.putExtra("MATCHID", matchId.eventId.toString())
        startActivity(intent)
    }

    override fun showNextMatch(data: List<NextMatch>) {}

}