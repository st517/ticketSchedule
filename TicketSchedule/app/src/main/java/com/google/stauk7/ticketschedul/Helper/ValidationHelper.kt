package com.google.stauk7.ticketschedul.Helper

import android.util.Log

class ValidationHelper() {
    companion object{
        val MANDATORY = "mandatory"
        val MAX_LENGTH = "max_length"
        val MIN_LENGTH = "min_length"
        val DATE_VALIDITY = "date_validity"
    }

    fun checkValidation(item: String, validateList: MutableList<String>, max: Int? = null, min: Int? = null): MutableList<String> {

        var errorMessage = mutableListOf<String>()

        validateList.forEach {
            when (it) {
                MANDATORY -> {
                    if (checkMandatry(item)){
                        errorMessage.add("必須項目です")
                    }
                }
                MAX_LENGTH -> {
                    if (checkMaxCharacters(item, max)){
                        errorMessage.add("最大文字数 $max を超えています")
                    }
                }
                MIN_LENGTH -> {
                    if (checkMinCharacters(item, min)){
                        errorMessage.add("最大文字数 $min を超えています")
                    }
                }
                DATE_VALIDITY -> {
                    if (checkDateValidity(item)){
                        errorMessage.add("日付形式ではありません")
                    }
                }

            }
        }
        return errorMessage
    }

    fun checkMandatry(item: String?): Boolean {
        return item.isNullOrEmpty()
    }

    fun checkMaxCharacters(item: String, max: Int?):Boolean{
        if (max != null){
            return item.length <= max
        }
        Log.e("Validation", "max length is NULL")
        return false
    }

    fun checkMinCharacters(item: String, min: Int?): Boolean{
        if (min != null){
            return item.length > min
        }
        Log.e("Validation", "min length is NULL")
        return false
    }

    fun checkDateValidity(item: String):Boolean{

    }
}