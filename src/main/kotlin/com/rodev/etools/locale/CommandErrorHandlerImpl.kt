package com.rodev.etools.locale;

import com.rodev.butil.api.commands.CommandErrorHandler
import com.rodev.butil.api.commands.util.CommandInput

class CommandErrorHandlerImpl : CommandErrorHandler {
    override fun onPermissionInsufficiency(ci: CommandInput, perm: String) {
        Locale.NOT_ENOUGH_PERMISSIONS.getMessage(perm)?.sendTo(ci.sender);
    }

    override fun onArgumentInsufficiency(ci: CommandInput) {
        Locale.NOT_ENOUGH_ARGS.sendTo(ci.sender);
    }

    override fun onCommandUnavailability(ci: CommandInput) {
        Locale.ONLY_PLAYER.sendTo(ci.sender);
    }
}
