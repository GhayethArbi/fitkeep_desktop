package services;

import java.sql.SQLException;
import java.util.List;

public interface CRUD<T> {
<<<<<<< HEAD
=======

>>>>>>> ca935f6e13b21fdf28b7c8dbf7f9751984a06275
    void insertOne(T t) throws SQLException;
    void updateOne(T t) throws SQLException;
    void deleteOne(T t) throws SQLException;
    List<T> selectAll() throws SQLException;
<<<<<<< HEAD
=======

>>>>>>> ca935f6e13b21fdf28b7c8dbf7f9751984a06275
}
