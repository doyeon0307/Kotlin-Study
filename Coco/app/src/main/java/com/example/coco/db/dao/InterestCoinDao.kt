package com.example.coco.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.coco.db.entity.InterestCoinEntity
import kotlinx.coroutines.flow.Flow

// Dao - 쿼리

@Dao
interface InterestCoinDAO {

    // getAllData
    // Flow-> 데이터 변경 사항 감지하기 좋음
    @Query("SELECT * FROM interest_coin_table")
    fun getAllData(): Flow<List<InterestCoinEntity>>

    // insert
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(interestCoinEntity: InterestCoinEntity)

    // update
    @Update
    fun update(interestCoinEntity: InterestCoinEntity)

    // getSelectedCoinList
    @Query("SELECT * FROM interest_coin_table WHERE selected = :selected")
    fun getSelectedData(
        selected: Boolean = true
    ): List<InterestCoinEntity>

}