package com.example.geonotes.presentation.main.components

import android.content.Context
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import com.example.geonotes.domain.model.Note
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker

@Composable
fun NotesMap(
    notes: List<Note>,
    onNoteClick: (Note) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current

    DisposableEffect(Unit) {
        Configuration.getInstance().load(
            context,
            context.getSharedPreferences("osmdroid", Context.MODE_PRIVATE)
        )
        onDispose { }
    }

    val mapView = remember {
        MapView(context).apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
            controller.setZoom(12.0)
            controller.setCenter(GeoPoint(37.7749, -122.4194)) // Default center
        }
    }

    AndroidView(
        factory = { mapView },
        modifier = modifier.fillMaxSize(),
        update = { map ->
            // Clear existing markers
            map.overlays.removeAll { it is Marker }

            // Add markers for each note
            notes.forEach { note ->
                val marker = Marker(map).apply {
                    position = GeoPoint(note.latitude, note.longitude)
                    title = note.title
                    snippet = note.content
                    setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)

                    setOnMarkerClickListener { _, _ ->
                        onNoteClick(note)
                        true
                    }
                }
                map.overlays.add(marker)
            }

            // Center map on notes if available
            if (notes.isNotEmpty()) {
                val bounds = org.osmdroid.util.BoundingBox.fromGeoPoints(
                    notes.map { GeoPoint(it.latitude, it.longitude) }
                )
                map.zoomToBoundingBox(bounds, true, 100)
            }

            map.invalidate()
        }
    )

    DisposableEffect(mapView) {
        onDispose {
            mapView.onDetach()
        }
    }
}