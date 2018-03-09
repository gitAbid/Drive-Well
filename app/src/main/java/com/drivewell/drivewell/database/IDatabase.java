package com.drivewell.drivewell.database;

import com.drivewell.drivewell.model.User;

/**
 * Created by abid on 2/18/18.
 */

public interface IDatabase {
     void createUser(User user);
     void insertdata(Object o);
     void updatedatabase(Object o);
     Object retrieveData(Object o);
     void getUserInformation(String userId);
     void deleteData(Object o);
}
