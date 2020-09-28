package com.antroid.nbateamviewer.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.antroid.nbateamviewer.R
import com.antroid.nbateamviewer.model.Player

class PlayersListAdapter(private val playerList: List<Player>) : RecyclerView.Adapter<PlayersListAdapter.PlayerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.player_item, parent, false)
        return PlayerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return playerList.size
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
       val player = playerList[position]
        player.let { holder.bind(it) }
    }

    inner class PlayerViewHolder(@NonNull itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val firstName: TextView = itemView.findViewById(R.id.firstNameTextView)
        private val lastName: TextView = itemView.findViewById(R.id.lastNameTextView)
        private val position: TextView = itemView.findViewById(R.id.positionTextView)
        private val number: TextView = itemView.findViewById(R.id.numberTextView)
        fun bind(player: Player) {
            firstName.text = player.first_name
            lastName.text = player.last_name
            number.text = itemView.context.getString(R.string.player_number, player.number.toString())
            position.text = itemView.context.getString(R.string.player_position, player.position)
        }
    }
}