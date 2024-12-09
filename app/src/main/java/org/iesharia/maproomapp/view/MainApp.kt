package org.iesharia.maproomapp.view

import org.iesharia.maproomapp.R
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.utsman.osmandcompose.*
import org.osmdroid.tileprovider.tilesource.OnlineTileSourceBase
import org.osmdroid.tileprovider.tilesource.XYTileSource
import org.osmdroid.util.GeoPoint
import org.osmdroid.util.MapTileIndex
import org.iesharia.maproomapp.viewmodel.MapViewModel

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
        zoom = 14.5
    }

    var mapProperties by remember { mutableStateOf(DefaultMapProperties) }

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
                5 -> R.drawable.health_marker
                6 -> R.drawable.cinema_marker
                7 -> R.drawable.default_marker
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
                        .widthIn(0.dp, 250.dp)
                        .background(
                            color = Color(0xD0000000),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // Título del marcador
                    Text(
                        text = it.title,
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 22.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // Tipo de marcador
                    Text(
                        text = it.snippet,
                        color = Color(0xFFFFC107),
                        fontSize = 20.sp,
                        modifier = Modifier.padding(top = 4.dp, bottom = 8.dp)
                    )

                    // Descripción (si existe)
                    if (!marker.description.isNullOrEmpty()) {
                        Text(
                            text = marker.description,
                            color = Color.LightGray,
                            fontSize = 18.sp,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(top = 8.dp)
                        )
                    }
                    var expanded by remember { mutableStateOf(false) }
                    // Icono para ampliar el lugar del marcador
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                    ) {
                        IconButton(
                            onClick = {
                                cameraState.geoPoint = GeoPoint(
                                    marker.latitude.toDouble(),
                                    marker.longitude.toDouble()
                                )
                                cameraState.zoom = if (expanded) 13.5 else 16.0
                                expanded = !expanded
                            },
                            modifier = Modifier.padding(top = 6.dp)
                        ) {
                            Icon(
                                imageVector = if (!expanded) Icons.Default.Search else Icons.Default.Close,
                                contentDescription = "Zoom",
                                tint = if (!expanded) Color(0xFF0064FF) else Color.Red,
                                modifier = Modifier.size(40.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}