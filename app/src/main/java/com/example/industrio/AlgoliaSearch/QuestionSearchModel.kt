package com.example.industrio.AlgoliaSearch

import androidx.lifecycle.ViewModel
import com.algolia.instantsearch.android.paging3.Paginator
import com.algolia.instantsearch.android.paging3.searchbox.connectPaginator
import com.algolia.instantsearch.compose.searchbox.SearchBoxState
import com.algolia.instantsearch.core.connection.ConnectionHandler
import com.algolia.instantsearch.searchbox.SearchBoxConnector
import com.algolia.instantsearch.searchbox.connectView
import com.algolia.instantsearch.searcher.hits.HitsSearcher
import com.algolia.search.model.APIKey
import com.algolia.search.model.ApplicationID
import com.algolia.search.model.IndexName

class QuestionSearchModel : ViewModel() {

    val searcher = HitsSearcher(
        applicationID = ApplicationID("0VH9R3FHKD"),
        apiKey = APIKey("b206fa2bf88e152db8aaa3145ac852ea"),
        indexName = IndexName("Questions")
    )

    // Search Box
    val searchBoxState = SearchBoxState()
    val searchBoxConnector = SearchBoxConnector(searcher)

    // Hits
    val hitsPaginator = Paginator(searcher) { it.deserialize(QuestionSearch.serializer()) }

    val connections = ConnectionHandler(searchBoxConnector)

    init {
        connections += searchBoxConnector.connectView(searchBoxState)
        connections += searchBoxConnector.connectPaginator(hitsPaginator)
    }

    override fun onCleared() {
        super.onCleared()
        searcher.cancel()
    }
}
