package com.example.images.beans



data class Response(
	val hits: List<HitsItem?>? = null,
	val total: Int? = null,
	val totalHits: Int? = null
)


