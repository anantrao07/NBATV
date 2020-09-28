package com.antroid.nbateamviewer.databaseconstants

const val TEAMS_TABLE_NAME = "TeamsTable"
const val COLUMN_NAME_TEAM_ID = "id"
const val COLUMN_NAME_TEAM_WINS = "wins"
const val COLUMN_NAME_TEAM_LOSSES = "losses"
const val COLUMN_NAME_TEAM_FULL_NAME = "fullName"
const val COLUMN_NAME_TEAM_PLAYERS = "players"
const val QUERY_GET_TEAMS = "Select * from $TEAMS_TABLE_NAME"
const val QUERY_GET_TEAM_BY_ID = "Select * from $TEAMS_TABLE_NAME where $COLUMN_NAME_TEAM_ID =:teamID"
