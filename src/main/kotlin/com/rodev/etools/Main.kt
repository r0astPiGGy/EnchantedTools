package com.rodev.etools

import com.rodev.butil.api.commands.Commands
import com.rodev.etools.commands.MainCommand
import com.rodev.etools.config.Settings
import com.rodev.etools.locale.CommandErrorHandlerImpl
import com.rodev.grayaml.api.Configurable
import com.rodev.grayaml.config.FileConfiguration
import com.rodev.grayaml.util.PluginConfig
import com.rodev.interapi.InterAPI
import com.rodev.interapi.api.InterBundle
import com.rodev.interapi.api.InteractiveMessageProvider
import com.rodev.interapi.api.MessageApplier
import com.rodev.interapi.builders.MessageApplierBuilder
import de.tr7zw.nbtapi.NBTItem
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin(), Configurable {

    companion object {
        private var interApiBundle: InterBundle? = null
        private var messageProvider: InteractiveMessageProvider? = null
        private var messageApplier: MessageApplier? = null

        fun getMessageProvider(): InteractiveMessageProvider {
            return messageProvider!!
        }
    }

    override fun onEnable() {
        initInterAPI()
        loadReloadable()

        Commands.registerCommand(MainCommand(this), CommandErrorHandlerImpl())
    }

    fun reload() {
        loadReloadable()
    }

    private fun loadMessages(): FileConfiguration {
        return loadConfig("messages")
    }

    private fun loadPluginConfig(): FileConfiguration {
        return loadConfig("config")
    }

    private fun loadConfig(name: String): FileConfiguration{
        return PluginConfig(this, name).reloadConfig()
    }

    private fun loadReloadable(){
        val messages = loadMessages()
        val config = loadPluginConfig()

        interApiBundle?.reload(messages)
    }

    private fun initInterAPI() {
        interApiBundle = InterAPI.create(this) {
            return@create MessageApplierBuilder()
                .withColors()
                .withRGB()
                .withPlaceholders(it)
                .build()
        }

        messageProvider = interApiBundle?.interactiveMessageProvider
        messageApplier = interApiBundle?.messageApplier
    }
}