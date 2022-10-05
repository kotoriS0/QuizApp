package com.example.quizapp

data class Question (
    var question: String,
    var answers: MutableList<String>,
    var correct: List<String>,
    var id: Int
)