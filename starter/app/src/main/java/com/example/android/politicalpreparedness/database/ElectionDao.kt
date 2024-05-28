package com.example.android.politicalpreparedness.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.politicalpreparedness.network.models.Election

@Dao
interface ElectionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertElection(election: Election)

    @Query("SELECT * FROM election_table")
    fun getAllElections(): LiveData<List<Election>>

    @Query("SELECT * FROM election_table where id = :electionId")
    suspend fun getElectionById(electionId: Int): Election?

    @Delete
    suspend fun deleteElection(election: Election): Int

    @Query("DELETE FROM election_table")
    suspend fun deleteAllElections()
}