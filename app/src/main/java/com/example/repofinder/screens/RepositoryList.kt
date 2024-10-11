package com.example.githubrepofinder.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.repofinder.model.Repository

@Composable
fun RepositoriesList(
    listState: LazyListState,
    modifier: Modifier = Modifier,
    repositories: LazyPagingItems<Repository>,
    onRepositoryClick: (Repository) -> Unit,
) {
    LazyColumn(
        state = listState,
        modifier = modifier,
        contentPadding = PaddingValues(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(repositories) { repository ->
            RepositoryItem(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                repository = repository,
                onRepositoryClick = onRepositoryClick,
            )
        }

        repositories.apply {
            when (loadState.append) {
                is LoadState.Loading -> {
                    item { CircularProgressIndicator() } // Show loading indicator
                }

                is LoadState.Error -> {
                    val error = (loadState.append as LoadState.Error).error
                    item {
                        Text("Error: ${error.localizedMessage}") // Show error message
                    }
                }

                is LoadState.NotLoading -> {
                    retry()
                }
            }
        }
    }
}

@Composable
fun RepositoryItem(
    modifier: Modifier = Modifier,
    repository: Repository,
    onRepositoryClick: (Repository) -> Unit,
) {
    var isOverflow by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    val uriHandler = LocalUriHandler.current

    Card(modifier = modifier.clickable { onRepositoryClick(repository) }) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = repository.name ?: "",
                    style = MaterialTheme.typography.titleMedium,
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = repository.numberOfStars.toString() ?: "",
                        style = MaterialTheme.typography.bodyMedium,
                    )
                }
            }
            VerticalSpacer(size = 16)

            Column(
                modifier =
                    Modifier
                        .fillMaxWidth()
                        .animateContentSize(),
            ) {
                Text(
                    text = repository.description ?: "Unknown Repository",
                    maxLines = if (expanded) Int.MAX_VALUE else 2,
                    overflow = TextOverflow.Ellipsis,
                    onTextLayout = { isOverflow = it.hasVisualOverflow },
                )

                AnimatedVisibility(visible = isOverflow) {
                    Text(
                        modifier = Modifier.clickable { expanded = true },
                        text = "See more",
                        color = Color.Gray,
                    )
                }
            }

            VerticalSpacer(size = 8)

            Text(
                modifier = Modifier.clickable { uriHandler.openUri(repository.url) },
                text = repository.url,
                color = Color.Blue,
                textDecoration = TextDecoration.Underline,
            )
        }
    }
}

inline fun <T : Any> LazyListScope.items(
    items: LazyPagingItems<T>,
    crossinline itemContent: @Composable LazyItemScope.(item: T) -> Unit,
) = items(
    count = items.itemCount,
) {
    items[it]?.let { it1 -> itemContent(it1) }
}

@Composable
fun VerticalSpacer(size: Int) = Spacer(modifier = Modifier.height(size.dp))
