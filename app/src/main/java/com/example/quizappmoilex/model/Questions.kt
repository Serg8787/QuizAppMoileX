package com.example.quizappmoilex.model

import com.example.quizappmoilex.model.Question
import com.google.gson.annotations.SerializedName


// conversion with a converter
data class Questions(
    @SerializedName("questions")
    val data: ArrayList<Question> = ArrayList()
)