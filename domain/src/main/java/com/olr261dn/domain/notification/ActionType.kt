package com.olr261dn.domain.notification

enum class ActionType(val action: String) {
    DONE("DONE"),
    DELAY("DELAY"),
    SKIP("SKIP");

    companion object {
        fun fromAction(action: String?): ActionType? {
            return ActionType.entries.find { it.action == action }
        }
    }
}
