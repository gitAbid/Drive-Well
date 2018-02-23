package com.drivewell.drivewell.database;

/**
 * Created by abid on 2/18/18.
 */

public interface IDatabase {
     void insertdata(Object o);
     void updatedatabase(Object o);
     Object retrieveData(Object o);
     void deleteData(Object o);
}
