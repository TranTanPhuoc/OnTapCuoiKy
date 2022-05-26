package com.example.onthicuoiky.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.onthicuoiky.model.User;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    List<User> getAll();

    @Query("SELECT * FROM users WHERE email LIKE :email")
    User findByEmail(String email);

    @Insert
    void insertAll(User... users);

    @Insert
    void insert(User user);

    @Query("DELETE FROM users")
    void delete();
}
