package com.example.geonotes.data.mapper

import com.example.geonotes.data.local.entity.NoteEntity
import com.example.geonotes.domain.model.Note

fun Note.toNoteEntity(
): NoteEntity {
    return NoteEntity(
        title = title,
        content = content,
        latitude = latitude,
        longitude = longitude,
        createdAt = createdAt,
        updatedAt = updatedAt,
        userId = id
    )
}


fun NoteEntity.toNote(): Note {
    return Note(
        title = title,
        content = content,
        latitude = latitude,
        longitude = longitude,
        createdAt = createdAt,
        updatedAt = updatedAt,
        id = userId ?: ""
    )
}