package com.example.artisana.features.ui.search

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.artisana.R
import com.example.artisana.core.composables.BottomNavigationBar
import com.example.artisana.core.composables.currentRoute
import com.example.artisana.core.navigation.Screen

data class SearchProduct(
    val id: String,
    val name: String,
    val price: String,
    val imageRes: Int,
    var quantity: Int = 1
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(navController: NavHostController) {
    val scrollState = rememberScrollState()
    var searchQuery by remember { mutableStateOf("") }
    var showRecentSearches by remember { mutableStateOf(false) }
    val focusRequester = remember { FocusRequester() }

    val recentSearches = remember {
        mutableStateListOf(
            "Pofa",
            "Pendant",
            "Brass",
            "Cushion",
            "Table"
        )
    }

    val searchResults = remember {
        mutableStateListOf(
            SearchProduct("2", "POFA", "MAD 260", 0, 1),
            SearchProduct("3", "BRASS PENDANT", "MAD 760", 0, 1),
            SearchProduct("1", "BRASS PENDANT", "MAD 890", 0, 1)
        )
    }

    // Filtered recent searches based on searchQuery
    val filteredRecentSearches = remember(searchQuery, recentSearches) {
        if (searchQuery.isBlank()) {
            recentSearches
        } else {
            recentSearches.filter {
                it.contains(searchQuery, ignoreCase = true)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Rechercher",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onBackground,
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center
                    )
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.Cart.route)
                    }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_shopping_bag),
                            contentDescription = "Cart",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                navController = navController,
                currentRoute = navController.currentRoute()
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(MaterialTheme.colorScheme.background)
        ) {
            // 1. Base Content (Search Bar and Results)
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .padding(horizontal = 15.dp),
            ) {
                Spacer(modifier = Modifier.height(16.dp))

                // Search Bar
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                ) {
                    OutlinedTextField(
                        value = searchQuery,
                        onValueChange = {
                            searchQuery = it
                            showRecentSearches = true
                        },
                        modifier = Modifier
                            .weight(1f)
                            .focusRequester(focusRequester)
                            .onFocusChanged { focusState ->
                                showRecentSearches = focusState.isFocused || searchQuery.isNotEmpty()
                            },
                        placeholder = {
                            Text(
                                "Search",
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                fontSize = 16.sp
                            )
                        },
                        leadingIcon = {
                            Icon(
                                Icons.Outlined.Search,
                                contentDescription = "Search",
                                tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            )
                        },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.outline,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                        ),
                        shape = RoundedCornerShape(8.dp),
                        singleLine = true,
                        keyboardActions = androidx.compose.foundation.text.KeyboardActions(
                            onSearch = { showRecentSearches = false }
                        )
                    )

                    // Filter Icon
                    IconButton(
                        onClick = { /* Filter action */ },
                        modifier = Modifier
                            .size(48.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.ic_filter),
                            contentDescription = "Filter",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Results Header
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Résultat pour \"$searchQuery\"",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = "${searchResults.count().toString().replace(" ", "").trim()} résultats",
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Search Results
                searchResults.forEach { product ->
                    SearchResultCard(
                        product = product,
                        onRemove = {
                            searchResults.remove(product)
                        }
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // 2. Recent Searches Overlay (Higher Z-index)
            if (showRecentSearches && filteredRecentSearches.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp + 48.dp + 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    RecentSearchesContent(
                        recentSearches = filteredRecentSearches,
                        onSearchClick = { search ->
                            searchQuery = search
                            showRecentSearches = false
                        },
                        onClearAll = {
                            recentSearches.clear()
                            showRecentSearches = false
                            searchQuery = ""
                        },
                        onRemoveSearch = { search ->
                            recentSearches.remove(search)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun RecentSearchesContent(
    recentSearches: List<String>,
    onSearchClick: (String) -> Unit,
    onClearAll: () -> Unit,
    onRemoveSearch: (String) -> Unit,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxWidth(.93f)
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(8.dp),
                spotColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.2f)
            )
            .clip(RoundedCornerShape(8.dp))
            .background(MaterialTheme.colorScheme.background)
            .heightIn(max = 225.dp)
            .padding(top = 12.dp, bottom = 12.dp, start = 20.dp, end = 12.dp),
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Recherches récentes",
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface,
            )
            TextButton(
                onClick = onClearAll,
            ) {
                Text(
                    text = "Tout Effacer",
                    fontSize = 14.sp,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.padding(0.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Recent search items
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(scrollState)
        ) {
            recentSearches.forEach { search ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .clickable { onSearchClick(search) },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            Icons.Outlined.Search,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            modifier = Modifier.size(20.dp)
                        )
                        Text(
                            text = search,
                            fontSize = 15.sp,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }

                    IconButton(
                        onClick = { onRemoveSearch(search) },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = "Remove",
                            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun SearchResultCard(
    product: SearchProduct,
    onRemove: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
    ) {
        // Product Image
        Box(
            modifier = Modifier
                .size(100.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.inverseSurface.copy(alpha = 0.1f)),
            contentAlignment = Alignment.Center
        ) {
            // Replace with: Image(painter = painterResource(product.imageRes), ...)
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    Icons.Outlined.Home,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onBackground.copy(0.5f),
                    modifier = Modifier.size(48.dp)
                )
            }
        }

        Spacer(modifier = Modifier.width(16.dp))

        // Product Details
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(
                        text = product.name,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onBackground,
                        letterSpacing = 0.5.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = product.price,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                IconButton(
                    onClick = onRemove,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "Remove",
                        tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}