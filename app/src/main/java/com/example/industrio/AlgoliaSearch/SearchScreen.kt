package com.example.industrio.AlgoliaSearch

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items

class SearchScreen {
}

@Composable
fun ProductsList(
    modifier: Modifier = Modifier,
    pagingHits: LazyPagingItems<QuestionSearch>,
    listState: LazyListState
) {
    LazyColumn(modifier, listState) {
        items(pagingHits) { item ->
            if (item == null) return@items
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(14.dp),
                text = item.name,
                style = MaterialTheme.typography.body1
            )
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .width(1.dp)
            )
        }
    }
}
