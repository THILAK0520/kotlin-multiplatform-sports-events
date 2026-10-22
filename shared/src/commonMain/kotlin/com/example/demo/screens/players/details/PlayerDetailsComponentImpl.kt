package com.example.demo.screens.players.details

import com.example.demo.data.remote.dto.PlayerDto

class PlayerDetailsComponentImpl(
    override val player: PlayerDto,
    private val onBackClicked: () -> Unit
) : PlayerDetailsComponent {
    override fun onBack() = onBackClicked()
}
