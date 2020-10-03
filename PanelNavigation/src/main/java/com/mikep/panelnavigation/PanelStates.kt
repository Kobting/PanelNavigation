package com.mikep.panelnavigation

import android.os.Parcelable
import com.discord.panels.PanelState
import kotlinx.android.parcel.Parcelize

@Parcelize
data class PanelStates(val startPanelState: ParcelablePanelState = ParcelablePanelState.CLOSED, val endPanelState: ParcelablePanelState = ParcelablePanelState.CLOSED): Parcelable

@Parcelize
enum class ParcelablePanelState: Parcelable {
    OPENING,
    OPENED,
    CLOSING,
    CLOSED;

    fun toPanelState(): PanelState = when(this) {
        OPENING -> PanelState.Opening
        OPENED -> PanelState.Opened
        CLOSING -> PanelState.Closing
        CLOSED -> PanelState.Closed
    }
}

internal fun PanelState.toParcelablePanelState(): ParcelablePanelState = when(this) {
    PanelState.Opening -> com.mikep.panelnavigation.ParcelablePanelState.OPENING
    PanelState.Opened -> com.mikep.panelnavigation.ParcelablePanelState.OPENED
    PanelState.Closing -> com.mikep.panelnavigation.ParcelablePanelState.CLOSING
    PanelState.Closed -> com.mikep.panelnavigation.ParcelablePanelState.CLOSED
}
