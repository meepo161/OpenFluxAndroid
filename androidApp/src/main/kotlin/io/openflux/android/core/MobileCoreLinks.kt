package io.openflux.android.core

import io.openflux.bridge.mobile.Mobile
import io.openflux.desktop.model.CoreLinks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

/** The core's link reading and making on Android: package mobile, share.Result JSON. */
internal object MobileCoreLinks : CoreLinks {
    override suspend fun read(link: String): String = withContext(Dispatchers.IO) { Mobile.readShareLink(link) }

    override suspend fun make(configJson: String): String = withContext(Dispatchers.IO) { Mobile.makeShareLink(configJson) }
}
