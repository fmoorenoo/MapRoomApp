package org.iesharia.maproomapp.view

import org.iesharia.maproomapp.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.utsman.osmandcompose.DefaultMapProperties
import com.utsman.osmandcompose.Marker
import com.utsman.osmandcompose.OpenStreetMap
import com.utsman.osmandcompose.ZoomButtonVisibility
import com.utsman.osmandcompose.rememberCameraState
import com.utsman.osmandcompose.rememberMarkerState
import org.iesharia.maproomapp.viewmodel.MapViewModel
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.util.GeoPoint
import org.osmdroid.util.MapTileIndex

val GoogleSat: OnlineTileSourceBase = object : XYTileSource(
    "Google-Sat",
    0, 19, 256, ".png", arrayOf(
        "https://mt0.google.com",
        "https://mt1.google.com",
        "https://mt2.google.com",
        "https://mt3.google.com",
    )
) {
    override fun getTileURLString(pTileIndex: Long): String {
        return baseUrl + "/vt/lyrs=s&x=" + MapTileIndex.getX(pTileIndex) + "&y=" + MapTileIndex.getY(
            pTileIndex
        ) + "&z=" + MapTileIndex.getZoom(pTileIndex)
    }
}

@Composable
fun MainApp(mapViewModel: MapViewModel) {
    // Observar los marcadores y tipos desde el ViewModel
    val markers by mapViewModel.markers.collectAsState()
    val markerTypes by mapViewModel.markerTypes.collectAsState()

    // Localización inicial del mapa
    val cameraState = rememberCameraState {
        geoPoint = GeoPoint(48.86694609924531, 2.310372951354165)
        zoom = 15.0
    }

    var mapProperties by remember {
        mutableStateOf(DefaultMapProperties)
    }

    OpenStreetMap(
        modifier = Modifier.fillMaxSize(),
        cameraState = cameraState,
        properties = mapProperties
    ) {
        // Configuración del mapa (estilo, botones, etc.)
        mapProperties = mapProperties
            .copy(tileSources = GoogleSat)
            .copy(isEnableRotationGesture = true)
            .copy(zoomButtonVisibility = ZoomButtonVisibility.SHOW_AND_FADEOUT)

        // Agregar marcadores desde los datos del ViewModel
        markers.forEach { marker ->
            val markerState = rememberMarkerState(
                geoPoint = GeoPoint(
                    marker.latitude.toDouble(),
                    marker.longitude.toDouble()
                )
            )

            // Obtener el nombre del tipo de marcador desde el mapa
            val markerTypeName = markerTypes[marker.markerTypeId] ?: "Sin Tipo"


            val context = LocalContext.current

            // Elegir el icono según el tipo de marcador
            val markerImage = when (marker.markerTypeId) {
                1 -> R.drawable.monument_marker
                2 -> R.drawable.food_marker
                3 -> R.drawable.school_marker
                4 -> R.drawable.shop_marker
                else -> R.drawable.default_marker
            }

            val drawable = ContextCompat.getDrawable(context, markerImage)

            Marker(
                state = markerState,
                title = marker.name,
                snippet = markerTypeName,
                icon = drawable
            ) {
                Column(
                    modifier = Modifier
                        .background(
                            color = Color(0xBA000000),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = it.title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                    Text(
                        text = it.snippet,
                        color = Color(0xFFFFC107),
                        fontSize = 20.sp,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}