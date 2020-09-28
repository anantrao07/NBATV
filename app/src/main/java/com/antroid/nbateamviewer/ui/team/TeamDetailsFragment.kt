package com.antroid.nbateamviewer.ui.team

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.antroid.nbateamviewer.R
import com.antroid.nbateamviewer.adapters.PlayersListAdapter
import com.antroid.nbateamviewer.database.TeamsListTable
import kotlinx.android.synthetic.main.team_details_fragment.*

class TeamDetailsFragment : Fragment() {

    companion object {
        fun newInstance() = TeamDetailsFragment()
    }

    private var viewModel: TeamDetailsViewModel? = null
    private val args: TeamDetailsFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.let {
            viewModel = TeamDetailsViewModel.Factory(it.application).create(TeamDetailsViewModel::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        observeViewModel()
        viewModel?.getTeam(args.teamID)
        return inflater.inflate(R.layout.team_details_fragment, container, false)
    }

    private fun observeViewModel(){
        viewModel?.getTeamInfo()?.observe(viewLifecycleOwner, Observer {
            it?.let {
                setUpUI(it)
            }
        })
    }

    private fun setUpUI(team: TeamsListTable){
        teamNameTextView.text = team.fullName
        noOfWins.text = getString(R.string.no_of_wins, team.wins.toString())
        noOfLosses.text = getString(R.string.no_of_loose, team.losses.toString())
        val playerListAdapter = PlayersListAdapter(team.players)
        teamPlayersRV.layoutManager = LinearLayoutManager(requireContext())
        teamPlayersRV.adapter = playerListAdapter

    }
}