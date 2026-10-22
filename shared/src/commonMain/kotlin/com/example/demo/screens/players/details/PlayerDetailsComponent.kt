package com.example.demo.screens.players.details

import com.example.demo.data.remote.dto.PlayerDto

interface PlayerDetailsComponent {
    val player: PlayerDto
    fun onBack()
}
