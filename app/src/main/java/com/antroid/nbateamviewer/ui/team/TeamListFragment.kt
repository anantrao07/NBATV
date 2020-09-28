package com.antroid.nbateamviewer.ui.team

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.antroid.nbateamviewer.R
import com.antroid.nbateamviewer.adapters.TeamsListAdapter
import com.antroid.nbateamviewer.constants.SORT_BY_LOSSES
import com.antroid.nbateamviewer.constants.SORT_BY_NAME
import com.antroid.nbateamviewer.constants.SORT_BY_WINS
import com.antroid.nbateamviewer.database.TeamsListTable
import kotlinx.android.synthetic.main.team_list_fragment.*

class TeamListFragment : Fragment() {

    companion object {
        fun newInstance() = TeamListFragment()
    }
    private var mTeamsList: List<TeamsListTable> = emptyList()
    private var adapter: TeamsListAdapter? = null
    private val teamClickListener = object : TeamClickListener {
        override fun showDetails(teamID: Int) {
            view?.let {
                val action = TeamListFragmentDirections.actionTeamListFragmentToTeamDetailsFragment(teamID)
                it.findNavController().navigate(action)
            }
        }
    }
    private var viewModel: TeamListViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
        activity?.let {
            viewModel = TeamListViewModel.Factory(it.application).create(TeamListViewModel::class.java)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        val menuInflater: MenuInflater = inflater
        menuInflater.inflate(R.menu.menu, menu)
    }
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        observeViewModel()
        return inflater.inflate(R.layout.team_list_fragment, container, false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_sort_by_name -> {
                adapter?.setTeamList(sortList(SORT_BY_NAME))
                adapter?.notifyDataSetChanged()
                true
            }
            R.id.menu_sort_by_wins -> {
                adapter?.setTeamList(sortList(SORT_BY_WINS))
                adapter?.notifyDataSetChanged()
                true
            }
            R.id.sort_by_looses -> {
                adapter?.setTeamList(sortList(SORT_BY_LOSSES))
                adapter?.notifyDataSetChanged()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun observeViewModel() {
        viewModel?.getTeamList()?.observe(viewLifecycleOwner, Observer { teamList ->
            teamList?.let {
                mTeamsList = it
                setUpUI(teamClickListener)
            }
        })
    }

    private fun setUpUI( teamClickListener: TeamClickListener) {
        adapter = TeamsListAdapter(mTeamsList, teamClickListener)
        team_list_recycler_view.layoutManager = LinearLayoutManager(requireContext())
        team_list_recycler_view.adapter = adapter
    }

    private fun sortList(sortBy: String): List<TeamsListTable>{
        return when(sortBy){
            SORT_BY_NAME -> {
                mTeamsList = mTeamsList.sortedBy { it.fullName }
                mTeamsList
            }
            SORT_BY_WINS -> {
                mTeamsList = mTeamsList.sortedByDescending { it.wins }
                mTeamsList
            }
            SORT_BY_LOSSES -> {
                mTeamsList = mTeamsList.sortedBy{ it.losses }
                mTeamsList
            }
            else -> emptyList()
        }
    }
}