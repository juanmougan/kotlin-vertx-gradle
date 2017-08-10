/**
 * Models for this example
 *
 * Created by juanmougan@gmail.com on 8/10/17.
 */


data class Team(val id: Long, val name: String)

data class Player(val id: Long, val name: String, val team: Team)
