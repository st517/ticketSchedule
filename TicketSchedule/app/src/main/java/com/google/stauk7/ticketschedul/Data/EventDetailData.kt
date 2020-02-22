package com.google.stauk7.ticketschedul.Data

data class EventDetailData(
    var eventId: Int,
    var detailId: Int,
    var title: String,
    var dateStart: String?,
    var dateEnd: String?,
    var memo: String?,
    var checkFlg: Int = 0,
    var deleteFlg: Int = 0
)