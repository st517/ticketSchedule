package com.google.stauk7.ticketschedul.Data

data class EventDetailData(
    var title: String,
    var dateStart: String,
    var dateEnd: String,
    var memo: String,
    var checkFlg: Int,
    var deleteFlg: Int
)