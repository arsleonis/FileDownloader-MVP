package com.zrz.android.filedownloader.model.db.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import androidx.room.Transaction

@Dao
interface PDFResourceDao {
    @Insert(onConflict = REPLACE)
    fun firstFilling(resources: Iterable<RoomPDFResource>)

    @Query("SELECT * FROM RoomPDFResource")
    fun getAllEntries(): List<RoomPDFResource>

    @Query("UPDATE RoomPDFResource SET image_name = :imageName WHERE urlAddress IN (:urlAddress)")
    fun update(urlAddress: String, imageName: String)

    @Query("SELECT * FROM RoomPDFResource WHERE urlAddress IN (:urlAddress)")
    fun read(urlAddress: String): RoomPDFResource

    @Transaction
    fun updateAndRead(urlAddress: String, imageName: String): RoomPDFResource {
        update(urlAddress, imageName)
        return read(urlAddress)
    }
}