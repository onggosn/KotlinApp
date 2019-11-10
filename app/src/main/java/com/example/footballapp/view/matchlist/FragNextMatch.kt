package com.example.footballapp.view.matchlist

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.footballapp.R
import com.example.footballapp.model.matchlist.NextMatch
import com.example.footballapp.model.matchlist.PrevMatch
import com.example.footballapp.netservices.ApiRepository
import com.example.footballapp.presenter.NextMatchPresenter
import com.example.footballapp.utils.*
import com.google.gson.Gson
import kotlinx.android.synthetic.main.frag_nextmatch.*
import org.jetbrains.anko.support.v4.onRefresh

class FragNextMatch : Fragment(), MatchView {
    private var match: MutableList<NextMatch> = mutableListOf()
    private lateinit var adapter: NextMatchAdapter
    private lateinit var presenter: NextMatchPresenter
    private lateinit var idLeague: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val idLeague = arguments?.getString("id")
        this.idLeague = idLeague.toString()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.frag_nextmatch, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val request = ApiRepository()
        val gson = Gson()

        presenter = NextMatchPresenter(this, request, gson)
        presenter.getNextMatch(idLeague)

        adapter = NextMatchAdapter(match){
            val toast = Toast.makeText(this.context, it.eventName, Toast.LENGTH_SHORT)
            toast.show()
        }

        fragNextMatch.adapter = adapter
        fragNextMatch.layoutManager = LinearLayoutManager(this.context)

        swipeRefreshPrev.onRefresh {
            presenter.getNextMatch(idLeague)
        }
    }

    companion object {
        fun newInstance(idLeague: String): FragNextMatch {
            val args = Bundle()
            args.putString("id", idLeague)
            val fragment = FragNextMatch()
            fragment.arguments = args
            return fragment
        }
    }

    override fun showLoading() {
        prevProgressBar.visible()
    }

    override fun hideLoading() {
        prevProgressBar.invisible()
    }

    override fun showNextMatch(data: List<NextMatch>) {
        swipeRefreshPrev.isRefreshing = false
        match.clear()
        match.addAll(data)
        Log.d("Data API", data.toString())
        adapter.notifyDataSetChanged()
    }

    override fun showPrevMatch(data: List<PrevMatch>) {}
    override fun notFound() {}
}
