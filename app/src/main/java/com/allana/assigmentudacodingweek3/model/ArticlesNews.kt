package com.allana.assigmentudacodingweek3.model

data class ArticlesNews (
    var source: Source? = null,
    var author: String? = null,
    var title: String? = null,
    var description: String? = null,
    var url: String? = null,
    var urlToImage: String? = null,
    var publishedAt: String? = null,
    var content: String? = null
)

data class Source(
    val id: String? = null,
    val name: String? = null
)