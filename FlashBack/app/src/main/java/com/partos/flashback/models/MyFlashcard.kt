package com.partos.flashback.models

class MyFlashcard (
    val id: Long,
    val userId: Long,
    val packageId: Long,
    var polish: String,
    var english: String,
    var knowledgeLevel: Int,
    var isNew: Int,
    var isKnown: Int
){

}