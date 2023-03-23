import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberImagePainter
import shpp.myapplication.colivery.R
import shpp.myapplication.colivery.data.network.SpaceModel
import shpp.myapplication.colivery.presentation.main.SpacesViewModel
import shpp.myapplication.colivery.utils.Semantics

@Composable
fun SpacesComposable(
    viewModel: SpacesViewModel = hiltViewModel()
) {
    val spaces: List<SpaceModel> by viewModel.spaces.collectAsState()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { /*...*/ },
        floatingActionButton = {
            FloatingActionButton(onClick = { /* navigate to add space screen */ }) {
                Icon(Icons.Default.Add, contentDescription = Semantics.ADD_SPACE_FAB)
            }
        },
        content = { padding ->
            val modifier = Modifier.padding(padding)
            if (spaces.isEmpty()) {
                NoSpacesView(
                    modifier = modifier
                )
            } else {
                SpacesList(
                    spaces = spaces,
                    modifier = modifier
                )
            }
        }
    )
}

@Composable
fun NoSpacesView(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = stringResource(R.string.no_spaces_warning),
            textAlign = TextAlign.Center,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun SpacesList(spaces: List<SpaceModel>, modifier: Modifier) {
    spaces.forEach {
        SpaceItem(
            space = it,
            hasActiveOrder = false
        ) // todo define hasActiveOrder via firestore
    }
}

@Composable
fun SpaceItem(space: SpaceModel, hasActiveOrder: Boolean) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val painter = rememberImagePainter(
            data = space.icon,
            builder = {
                crossfade(true)
                error(Icons.Default.Home)
            }
        )

        Image(
            painter = painter,
            contentDescription = Semantics.SPACE_ICON,
            modifier = Modifier
                .size(40.dp)
                .clip(CircleShape)
        )

        Spacer(modifier = Modifier.width(16.dp))

        Column {
            Text(
                text = space.name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "${space.usersList.size}",
                fontSize = 14.sp,
                fontWeight = FontWeight.Normal
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        if (hasActiveOrder) {
            Box(
                modifier = Modifier
                    .size(16.dp)
                    .background(Color.Green, CircleShape)
            )
        }
    }
}

@Preview
@Composable
fun SpacesScreenPreview() {
    SpacesComposable()
}
