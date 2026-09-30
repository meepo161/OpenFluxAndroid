package io.openflux.android.core

import io.openflux.desktop.model.Profile
import io.openflux.desktop.model.TransportType
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.buildJsonArray
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

/** A profile in the form the gomobile core's Start calls take. */
internal object CoreSpecs {
    /**
     * The Session transport list for StartSession / StartSessionProxy /
     * StartSessionExit, named like the CLI (type, then type-2…) so they match
     * the exit's. An exit with a Direct transport listens on [directPort] instead of
     * dialing, like the desktop's exit .conf.
     */
    fun session(profile: Profile, exit: Boolean, directPort: Int): String {
        val specs = profile.sessionSpecs().filterNot { exit && it.type == TransportType.DIRECT }
        val transports = buildJsonArray {
            for (spec in specs) add(spec(spec.name, spec.type, spec.value, spec.uid, spec.priority, spec.network.cli))
            // The exit listens for direct only when the profile has it.
            val direct = profile.carriers.firstOrNull { it.type == TransportType.DIRECT }
            if (exit && direct != null) {
                add(buildJsonObject {
                    put("name", "direct")
                    put("type", "direct")
                    put("url", "")
                    put("priority", direct.priority)
                    put("params", buildJsonObject { put("listen", "0.0.0.0:$directPort") })
                })
            }
        }
        val bonding = !exit && profile.bonding
        if (profile.context.isBlank() && !bonding) return transports.toString()
        return buildJsonObject {
            if (profile.context.isNotBlank()) put("context", profile.context)
            if (bonding) put("bonding", true)
            put("transports", transports)
        }.toString()
    }

    private fun spec(name: String, type: TransportType, value: String, uid: String, priority: Int, network: String): JsonObject = buildJsonObject {
        put("name", name)
        if (network.isNotEmpty()) put("network", network)
        put("type", type.cliName)
        put("url", if (type == TransportType.DIRECT || type == TransportType.ONEME) "" else value)
        put("priority", priority)
        put("params", buildJsonObject {
            when (type) {
                TransportType.DIRECT -> put("dial", value)
                TransportType.ONEME -> {
                    put("token", value)
                    put("uid", uid)
                }
                else -> Unit
            }
        })
    }

    /** Classic mode's arguments: (type, document URL, MAX token, MAX uid). */
    fun classic(profile: Profile): List<String> {
        val value = profile.value.trim()
        return if (profile.transport == TransportType.ONEME) {
            listOf(profile.transport.cliName, "", value, profile.uid.trim())
        } else {
            listOf(profile.transport.cliName, value, "", "")
        }
    }
}
