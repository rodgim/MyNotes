package com.rodgim.mynotes.feature_note.domain.utils

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed class OrderType: Parcelable {
    @Parcelize
    object Ascending: OrderType()
    @Parcelize
    object Descending: OrderType()
}
