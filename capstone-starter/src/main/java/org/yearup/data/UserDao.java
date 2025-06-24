package org.yearup.data;

import org.yearup.models.AppUser;

import java.util.List;

public interface UserDao {

    List<AppUser> getAll();

    AppUser getUserById(int userId);

    AppUser getByUserName(String username);

    int getIdByUsername(String username);

    AppUser create(AppUser appUser);

    boolean exists(String username);
}
