package com.rodgim.mynotes.feature_note.domain.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class NoteOrder(val orderType: OrderType): Parcelable {
    @Parcelize
    data class Title(val order: OrderType): NoteOrder(order)
    @Parcelize
    data class Date(val order: OrderType): NoteOrder(order)
    @Parcelize
    data class Color(val order: OrderType): NoteOrder(order)

    fun copyOf(orderType: OrderType): NoteOrder {
        return when(this) {
            is Title -> Title(orderType)
            is Date -> Date(orderType)
            is Color -> Color(orderType)
        }
    }
}