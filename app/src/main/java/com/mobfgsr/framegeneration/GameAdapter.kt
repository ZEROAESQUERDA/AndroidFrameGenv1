package com.mobfgsr.framegeneration

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class GameAdapter(
    context: Context,
    private val games: List<Game>
) : ArrayAdapter<Game>(context, 0, games) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createView(position, convertView, parent)
    }

    private fun createView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: LayoutInflater.from(context)
            .inflate(R.layout.item_game, parent, false)

        val game = games[position]
        
        val gameIcon = view.findViewById<ImageView>(R.id.gameIcon)
        val gameName = view.findViewById<TextView>(R.id.gameName)
        
        // Configurar o ícone do jogo (em uma implementação real, usaríamos recursos reais)
        gameIcon.setImageResource(R.drawable.ic_game_default)
        
        // Configurar o nome do jogo
        gameName.text = game.name
        
        return view
    }
}

data class Game(
    val id: String,
    val name: String,
    val packageName: String,
    val isInstalled: Boolean = false
)
