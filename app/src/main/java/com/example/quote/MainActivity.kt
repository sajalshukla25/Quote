package com.example.quote


import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quote.ui.ElegantWavesBackground
import kotlinx.coroutines.delay
import java.time.LocalDate
import kotlin.math.sin
//import androidx.compose.material.pullrefresh.PullRefreshIndicator
//import androidx.compose.material.pullrefresh.pullRefresh
//import androidx.compose.material.pullrefresh.rememberPullRefreshState
import com.example.quote.ui.auth.AuthNavigator
import com.example.quote.ui.theme.QuoteTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            QuoteTheme {

                var isLoggedIn by remember { mutableStateOf(false) }

                if (isLoggedIn) {
                    QuoteApp() // âœ… composable context
                } else {
                    AuthNavigator (
                        onAuthSuccess = {
                            isLoggedIn = true // âœ… sirf state change
                        }
                    )
                }
            }
        }
    }
}


/* -------------------- PROFESSIONAL COLOR PALETTE -------------------- */

object AppColors {
    val Primary = Color(0xFF2563EB)
    val Secondary = Color(0xFF7C3AED)
    val Background = Color(0xFFF8FAFC)
    val Surface = Color.White
    val TextPrimary = Color(0xFF1E293B)
    val TextSecondary = Color(0xFF64748B)
    val Gradient1 = Color(0xFF2563EB)
    val Gradient2 = Color(0xFF7C3AED)
    val Gradient3 = Color(0xFF06B6D4)
}

/* -------------------- MODEL -------------------- */

data class Quote(
    val id: Int,
    val text: String,
    val author: String,
    val category: String,
    val isFavorite: Boolean = false
)

/* -------------------- APP ROOT -------------------- */

@Composable
fun QuoteApp() {
    val context = LocalContext.current

    var isRefreshing by remember { mutableStateOf(false) }



    val categories = listOf("All", "Motivation", "Love", "Success", "Wisdom", "Humor")

    val quotes = remember {
        mutableStateListOf(
            Quote(1, "Stay hungry, stay foolish.", "Steve Jobs", "Motivation"),
            Quote(2, "Innovation distinguishes between a leader and a follower.", "Steve Jobs", "Success"),
            Quote(3, "Life is what happens when you're busy making other plans.", "John Lennon", "Wisdom"),
            Quote(4, "The future belongs to those who believe in the beauty of their dreams.", "Eleanor Roosevelt", "Motivation"),
            Quote(5, "Love all, trust a few.", "William Shakespeare", "Love"),
            Quote(6, "Smile, it confuses people.", "Unknown", "Humor")
        )
    }

    /* -------- STATES -------- */

    var selectedTab by remember { mutableIntStateOf(0) }
    var selectedCategory by remember { mutableStateOf("All") }
    var searchQuery by remember { mutableStateOf("") }
    var index by remember { mutableIntStateOf(0) }

    /* -------- FILTER LOGIC -------- */

    val filteredQuotes = remember(searchQuery, selectedCategory, quotes) {
        quotes.filter {
            (selectedCategory == "All" || it.category == selectedCategory) &&
                    (it.text.contains(searchQuery, true) ||
                            it.author.contains(searchQuery, true))
        }
    }

    LaunchedEffect(selectedCategory, searchQuery) { index = 0 }

    /* -------- QUOTE OF THE DAY -------- */

    val quoteOfTheDay = remember {
        quotes[LocalDate.now().dayOfYear % quotes.size]
    }



    Box(Modifier.fillMaxSize()) {
        ElegantWavesBackground()

        Scaffold(
            topBar = { QuoteAppTopBar() },
            bottomBar = {
                QuoteBottomNavigation(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )
            },
            containerColor = Color.Transparent
        ) { padding ->

            when (selectedTab) {

                0 -> LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    verticalArrangement = Arrangement.spacedBy(0.dp)
                ) {
                    // Search Bar
                    item {
                        OutlinedTextField(
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            placeholder = { Text("Search quotes or authors") },
                            leadingIcon = { Icon(Icons.Default.Search, null) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 12.dp),
                            singleLine = true,
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = AppColors.Primary,
                                unfocusedBorderColor = AppColors.TextSecondary.copy(alpha = 0.3f)
                            )
                        )
                    }

                    // Quote of the Day
                    item {
                        QuoteOfTheDayCard(quoteOfTheDay)
                    }

                    // Category Tabs
                    item {
                        CategoryTabs(
                            categories = categories,
                            selectedCategory = selectedCategory,
                            onCategorySelected = { selectedCategory = it }
                        )
                    }

                    // Main Content
                    item {
                        if (filteredQuotes.isEmpty()) {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(400.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center
                                ) {
                                    Icon(
                                        Icons.Default.Search,
                                        contentDescription = null,
                                        tint = AppColors.TextSecondary.copy(alpha = 0.5f),
                                        modifier = Modifier.size(64.dp)
                                    )
                                    Spacer(Modifier.height(16.dp))
                                    Text(
                                        "No quotes found",
                                        fontSize = 18.sp,
                                        color = AppColors.TextSecondary
                                    )
                                }
                            }
                        } else {
                            HomeScreen(
                                modifier = Modifier.fillMaxWidth(),
                                quote = filteredQuotes[index],
                                onNewQuote = {
                                    index = (index + 1) % filteredQuotes.size
                                },
                                onToggleFavorite = {
                                    val q = filteredQuotes[index]
                                    val realIndex = quotes.indexOfFirst { it.id == q.id }
                                    if (realIndex != -1) {
                                        quotes[realIndex] =
                                            quotes[realIndex].copy(isFavorite = !quotes[realIndex].isFavorite)
                                    }
                                }
                            )
                        }
                    }
                }

                1 -> FavoritesScreen(
                    modifier = Modifier.padding(padding),
                    favorites = quotes.filter { it.isFavorite },
                    onRemove = { quote ->
                        val i = quotes.indexOfFirst { it.id == quote.id }
                        if (i != -1) quotes[i] = quotes[i].copy(isFavorite = false)
                    }
                )
            }
        }
    }
}

@Composable
fun QuoteOfTheDayCard(quote: Quote) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 6.dp),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(AppColors.Surface),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text("ðŸŒž", fontSize = 16.sp)
                Text(
                    "Quote of the Day",
                    color = AppColors.Secondary,
                    fontSize = 13.sp
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                quote.text,
                fontSize = 15.sp,
                lineHeight = 22.sp,
                color = AppColors.TextPrimary
            )
            Spacer(Modifier.height(6.dp))
            Text(
                "â€” ${quote.author}",
                fontSize = 13.sp,
                color = AppColors.TextSecondary
            )
        }
    }
}

@Composable
fun CategoryTabs(
    categories: List<String>,
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 16.dp)
    ) {
        items(categories) { category ->
            val selected = category == selectedCategory

            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(20.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                AppColors.Gradient1.copy(alpha = 0.1f),
                                AppColors.Gradient2.copy(alpha = 0.1f)
                            )
                        ),
                        shape = CircleShape
                    )
                    .clickable { onCategorySelected(category) }
                    .padding(horizontal = 20.dp, vertical = 12.dp)
            ) {
                Text(
                    category,
                    fontSize = 14.sp,
                    color = if (selected) Color.White else AppColors.TextSecondary
                )
            }
        }
    }
}

/* -------------------- REDESIGNED TOP BAR -------------------- */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuoteAppTopBar() {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = AppColors.Surface,
        shadowElevation = 2.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column {
                    Text(
                        "QuoteVault",
                        fontSize = 26.sp,
                        color = AppColors.TextPrimary
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "Inspiration for your day",
                        fontSize = 14.sp,
                        color = AppColors.TextSecondary
                    )
                }

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                        .background(
                            Brush.linearGradient(
                                colors = listOf(
                                    AppColors.Gradient1.copy(alpha = 0.1f),
                                    AppColors.Gradient2.copy(alpha = 0.1f)
                                )
                            )
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = AppColors.Primary,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

/* -------------------- ELEGANT WAVES BACKGROUND -------------------- */

/* -------------------- HOME SCREEN WITH FIXED CARD -------------------- */

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    quote: Quote,
    onNewQuote: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    val context = LocalContext.current

    var scale by remember { mutableStateOf(1f) }
    val scaleAnim by animateFloatAsState(
        targetValue = scale,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "scale"
    )

    LaunchedEffect(quote) {
        scale = 0.95f
        delay(50)
        scale = 1f
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            shape = RoundedCornerShape(20.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(AppColors.Surface),
            modifier = Modifier
                .fillMaxWidth()
                .height(280.dp)
                .scale(scaleAnim)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = null,
                        tint = AppColors.Gradient2.copy(alpha = 0.3f),
                        modifier = Modifier.size(36.dp)
                    )

                    Spacer(Modifier.height(16.dp))

                    Text(
                        quote.text,
                        fontSize = 17.sp,
                        lineHeight = 26.sp,
                        textAlign = TextAlign.Center,
                        color = AppColors.TextPrimary,
                        modifier = Modifier.weight(1f, fill = false)
                    )

                    Spacer(Modifier.height(16.dp))

                    HorizontalDivider(
                        modifier = Modifier.width(50.dp),
                        thickness = 2.dp,
                        color = AppColors.Gradient2.copy(alpha = 0.3f)
                    )

                    Spacer(Modifier.height(12.dp))

                    Text(
                        quote.author,
                        fontSize = 15.sp,
                        color = AppColors.Secondary
                    )
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            IconAction(
                icon = Icons.Default.Refresh,
                onClick = onNewQuote,
                colors = listOf(AppColors.Gradient1, AppColors.Gradient2)
            )
            IconAction(
                icon = if (quote.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                onClick = onToggleFavorite,
                colors = listOf(AppColors.Gradient2, AppColors.Gradient3)
            )
            IconAction(
                icon = Icons.Default.Share,
                onClick = {
                    val sendIntent = Intent().apply {
                        action = Intent.ACTION_SEND
                        putExtra(
                            Intent.EXTRA_TEXT,
                            "\"${quote.text}\"\nâ€” ${quote.author}"
                        )
                        type = "text/plain"
                    }
                    context.startActivity(
                        Intent.createChooser(sendIntent, "Share quote")
                    )
                },
                colors = listOf(AppColors.Gradient3, AppColors.Gradient1)
            )

        }
    }
}

/* -------------------- FAVORITES SCREEN -------------------- */

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    favorites: List<Quote>,
    onRemove: (Quote) -> Unit
) {
    AnimatedContent(
        targetState = favorites.isEmpty(),
        transitionSpec = {
            fadeIn(tween(300)) togetherWith fadeOut(tween(300))
        },
        label = "favorites",
        content = { isEmpty ->
            if (isEmpty) {
                Box(
                    modifier = modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(32.dp)
                    ) {
                        Icon(
                            Icons.Default.FavoriteBorder,
                            contentDescription = null,
                            tint = AppColors.TextSecondary.copy(alpha = 0.5f),
                            modifier = Modifier.size(64.dp)
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            "No favorites yet",
                            fontSize = 20.sp,
                            color = AppColors.TextSecondary
                        )
                        Spacer(Modifier.height(8.dp))
                        Text(
                            "Start adding quotes you love",
                            fontSize = 14.sp,
                            color = AppColors.TextSecondary.copy(alpha = 0.7f)
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    itemsIndexed(
                        items = favorites,
                        key = { _, quote -> quote.id }
                    ) { index, quote ->
                        var visible by remember { mutableStateOf(false) }

                        LaunchedEffect(Unit) {
                            delay(index * 50L)
                            visible = true
                        }

                        AnimatedVisibility(
                            visible = visible,
                            enter = fadeIn(tween(300)) + slideInVertically(
                                initialOffsetY = { it / 2 },
                                animationSpec = tween(300)
                            )
                        ) {
                            Card(
                                shape = RoundedCornerShape(20.dp),
                                colors = CardDefaults.cardColors(AppColors.Surface),
                                elevation = CardDefaults.cardElevation(4.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .padding(20.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            quote.text,
                                            fontSize = 16.sp,
                                            lineHeight = 24.sp,
                                            color = AppColors.TextPrimary
                                        )
                                        Spacer(Modifier.height(8.dp))
                                        Text(
                                            "â€” ${quote.author}",
                                            fontSize = 14.sp,
                                            color = AppColors.Secondary
                                        )
                                    }
                                    Spacer(Modifier.width(12.dp))
                                    IconButton(onClick = { onRemove(quote) }) {
                                        Icon(
                                            Icons.Default.Favorite,
                                            contentDescription = "Remove from favorites",
                                            tint = AppColors.Gradient2
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        })
}

@Composable
fun QuoteBottomNavigation(
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    NavigationBar(
        containerColor = AppColors.Surface,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = selectedTab == 0,
            onClick = { onTabSelected(0) },
            icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
            label = { Text("Home") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = AppColors.Primary,
                selectedTextColor = AppColors.Primary,
                indicatorColor = AppColors.Primary.copy(alpha = 0.1f)
            )
        )
        NavigationBarItem(
            selected = selectedTab == 1,
            onClick = { onTabSelected(1) },
            icon = { Icon(Icons.Default.Favorite, contentDescription = "Favorites") },
            label = { Text("Favorites") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = AppColors.Secondary,
                selectedTextColor = AppColors.Secondary,
                indicatorColor = AppColors.Secondary.copy(alpha = 0.1f)
            )
        )
    }
}

/* -------------------- ICON ACTION BUTTON -------------------- */

@Composable
fun IconAction(
    icon: ImageVector,
    onClick: () -> Unit,
    colors: List<Color>
) {
    var pressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(
        targetValue = if (pressed) 0.85f else 1f,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessMedium
        ),
        label = "buttonScale"
    )

    Box(
        modifier = Modifier
            .size(64.dp)
            .scale(scale)
            .clip(CircleShape)
            .background(Brush.linearGradient(colors))
            .clickable(
                onClick = {
                    pressed = true
                    onClick()
                },
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier.size(28.dp)
        )
    }

    LaunchedEffect(pressed) {
        if (pressed) {
            delay(100)
            pressed = false
        }
    }
}

/* -------------------- PREVIEW -------------------- */

@Preview(showBackground = true)
@Composable
fun PreviewApp() {
    QuoteTheme {
        QuoteApp()
    }
}