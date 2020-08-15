package com.allana.assigmentudacodingweek3.model

data class ResponseServer (

    var status: String? = null,
    var total_page: Int? = null,
    var articles: ArrayList<ArticlesNews>? = null

)