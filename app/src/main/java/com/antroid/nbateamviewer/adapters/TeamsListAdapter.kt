package com.antroid.nbateamviewer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.antroid.nbateamviewer.R
import com.antroid.nbateamviewer.database.TeamsListTable
import com.antroid.nbateamviewer.ui.team.TeamClickListener

class TeamsListAdapter(private var teamList: List<TeamsListTable>, private val teamClickListener: TeamClickListener) : RecyclerView.Adapter<TeamsListAdapter.ListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.team_list_item, parent, false)
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return teamList.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val team = teamList[position]
        team.let { holder.bind(teamClickListener, it) }
    }

    inner class ListViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val teamNameTextView: TextView = itemView.findViewById(R.id.team_name_text_view)
        private val teamWinsTextView: TextView = itemView.findViewById(R.id.team_name_wins_text_view)
        private val teamLossesTextView: TextView = itemView.findViewById(R.id.team_name_losses_text_view)

        fun bind(clickListener: TeamClickListener, team: TeamsListTable) {
            itemView.setOnClickListener { clickListener.showDetails(team.id) }
            teamNameTextView.text = team.fullName
            teamWinsTextView.text = itemView.context.getString(R.string.no_of_wins, team.wins.toString())
            teamLossesTextView.text = itemView.context.getString(R.string.no_of_loose, team.losses.toString())
        }
    }

    fun setTeamList(list: List<TeamsListTable>) {
        teamList = list
    }
}