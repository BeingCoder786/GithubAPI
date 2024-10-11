package com.example.githubrepofinder.screens

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.repofinder.R
import com.example.repofinder.model.Repository

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    repository: Repository,
    onBackClick: () -> Unit,
) {
    val handle = LocalUriHandler.current
    BackHandler {
        onBackClick()
    }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
            )
        },
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            UserDetails(user = repository, onWebsiteClick = {
                handle.openUri(it)
            }, onProfileUrlClick = {
                handle.openUri(it)
            })
        }
    }
}

@Composable
fun UserDetails(
    modifier: Modifier = Modifier,
    user: Repository,
    onProfileUrlClick: (String) -> Unit,
    onWebsiteClick: (String) -> Unit,
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(8.dp)) {
            Column(
                modifier =
                    Modifier
                        .padding(8.dp)
                        .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Box(
                    modifier =
                        Modifier
                            .clip(CircleShape)
                            .size(150.dp),
                ) {
                    AsyncImage(
                        model = user.owner?.url,
                        contentDescription = user.owner?.username,
                        contentScale = ContentScale.Crop,
                    )
                }
                VerticalSpacer(size = 8)
                user.name?.let { Text(text = it, style = MaterialTheme.typography.headlineSmall) }
            }
            VerticalSpacer(size = 8)

            user.description?.let { bio ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    Text(text = bio, style = MaterialTheme.typography.bodyLarge)
                }
            }
            VerticalSpacer(size = 16)
            VerticalSpacer(size = 32)
            UserLinks(
                modifier = Modifier.fillMaxWidth(),
                user = user,
                onProfileUrlClick = onProfileUrlClick,
                onWebsiteClick = onWebsiteClick,
            )
        }
    }
}

@Composable
fun UserLinks(
    modifier: Modifier = Modifier,
    user: Repository,
    onProfileUrlClick: (String) -> Unit,
    onWebsiteClick: (String) -> Unit,
) {
    Column(modifier = modifier) {
        Text(
            text = stringResource(R.string.profile_url),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold,
        )
        user?.owner?.profileUrl?.let { ClickableText(text = it, onClick = onProfileUrlClick) }
        VerticalSpacer(size = 16)

        user.owner?.url?.let {
            Column {
                Text(
                    text = stringResource(R.string.website),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                )

                ClickableText(text = user.owner.url, onClick = onWebsiteClick)
            }
        }
    }
}

@Composable
fun ClickableText(
    modifier: Modifier = Modifier,
    text: String,
    onClick: (String) -> Unit,
) {
    Text(
        modifier = modifier.clickable { onClick(text) },
        text = text,
        color = Color.Blue,
        textDecoration = TextDecoration.Underline,
    )
}
