package com.rodev.etools.commands;

import com.rodev.butil.api.commands.PluginCommand;
import com.rodev.butil.api.commands.annotations.CommandForwarder;
import com.rodev.butil.api.commands.annotations.Permission;
import com.rodev.butil.api.commands.annotations.SubCommand
import com.rodev.butil.api.commands.util.CommandInput;
import com.rodev.etools.Main
import com.rodev.etools.locale.Locale

@CommandForwarder(name = "enchantedtools")
@Permission("etools.cmd.use")
class MainCommand(private val plugin: Main) : PluginCommand {

    override
    fun onCommand(commandInput: CommandInput) {
        Locale.COMMAND_USAGE.sendTo(commandInput.sender)
    }

    @SubCommand(name = "test")
    @Permission("etools.admin.reload")
    fun onDebug(ci: CommandInput){

    }

    @SubCommand(name = "reload")
    @Permission("etools.admin.reload")
    fun onReload(ci: CommandInput) {
        Locale.RELOADING.sendTo(ci.sender)
        plugin.reload()
        Locale.RELOADED.sendTo(ci.sender)
    }

}
