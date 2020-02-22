package com.google.stauk7.ticketschedul.Data

data class EventData(
    var id: Int,
    var title: String,
    var memo: String,
    var deleteFlg: Int = 0
)