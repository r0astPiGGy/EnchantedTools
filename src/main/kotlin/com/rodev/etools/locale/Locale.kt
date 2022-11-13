package com.rodev.etools.locale

import com.rodev.butil.api.commands.CmdSender
import com.rodev.etools.Main
import com.rodev.interapi.api.InteractiveMessageProvider
import com.rodev.interapi.api.MessageObject
import org.bukkit.command.CommandSender
import java.util.function.Consumer

enum class Locale(var configCode: String) {

    NOT_ENOUGH_PERMISSIONS("not-enough-perms"){
        override fun replaceArgs(msg: String, vararg args: String): String {
            return msg.replace("%permission%", args[0]);
        }
    },
    NOT_ENOUGH_ARGS("not-enough-args"),
    ONLY_PLAYER("only-player"),
    COMMAND_USAGE("command-usage"),
    RELOADING("reloading"),
    RELOADED("reloaded")

    ;

    open fun replaceArgs(msg: String, vararg args: String): String {
        return msg
    }

    open fun sendTo(sender: CmdSender, vararg args: String) {
        sendTo(sender.handle as CommandSender, *args)
    }

    open fun sendTo(sender: CommandSender?, vararg args: String) {
        getMessage(*args)?.sendTo(sender)
    }

    open fun getMessage(vararg args: String): MessageWrapper? {
        return MessageWrapper(args, this)
    }

    class MessageWrapper(val args: Array<out String>, var type: Locale) {
        fun sendTo(sender: CommandSender?) {
            Handler.sendMsg(sender, this)
        }

        fun sendTo(sender: CmdSender) {
            sendTo(sender.handle as CommandSender)
        }

        fun <T : CommandSender?> sendTo(senders: Iterable<T>) {
            senders.forEach(Consumer { sender: T -> this.sendTo(sender) })
        }

        val rawMessage: String?
            get() = Handler.getRawMessage(this)
    }

    private object Handler {
        fun getMessageObject(key: String): MessageObject? {
            val provider: InteractiveMessageProvider = Main.getMessageProvider()
            return provider.getMessage(key) ?: return null
        }

        fun getRawMessage(rev: MessageWrapper): String? {
            val `object` = getMessageObject(rev.type.configCode) ?: return null
            val type: Locale = rev.type
            val args = rev.args
            val rawMessage = `object`.rawMessage ?: return null
            return type.replaceArgs(rawMessage, *args)
        }

        fun sendMsg(sender: CommandSender?, messageWrapper: MessageWrapper) {
            val `object` = getMessageObject(messageWrapper.type.configCode) ?: return
            val type: Locale = messageWrapper.type
            val args = messageWrapper.args
            `object`.sendTo(
                sender
            ) { msg: String ->
                type.replaceArgs(
                    msg,
                    *args
                )
            }
        }
    }
}