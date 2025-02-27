package io.atomic.sdk.screens.multicards

import androidx.lifecycle.ViewModel
import com.atomic.actioncards.sdk.AACSDK
import com.atomic.actioncards.sdk.AACSingleCardView
import com.atomic.actioncards.sdk.AACStreamContainer
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MultiCardsViewModel @Inject constructor() : ViewModel() {
    var streamContainerTop: AACStreamContainer? = null
    var streamContainerBottom: AACStreamContainer? = null

    init {
        initContainers()
    }

    private fun initContainers() {
        if (streamContainerTop != null && streamContainerBottom != null) {
            return
        }
        streamContainerBottom = AACSingleCardView.Companion.create(containerIdBottom)
        streamContainerTop = AACSingleCardView.Companion.create(containerIdTop)
        registerContainersForNotifications()
    }

    private fun registerContainersForNotifications() {
        val containers = arrayListOf(containerIdBottom, containerIdTop)
        AACSDK.registerStreamContainersForNotifications(containers)
    }

    companion object {
        const val containerIdTop = ""
        const val containerIdBottom = ""
    }
}