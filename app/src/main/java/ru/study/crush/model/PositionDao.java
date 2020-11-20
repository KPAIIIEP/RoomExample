package ru.study.crush.model;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface PositionDao {
    @Query("SELECT * FROM position WHERE userId = :userId")
    Position getUserPositions(int userId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Position position);

    @Query("SELECT * FROM position")
    List<Position> getAll();

//    @Delete
//    void delete(Position position);
}
