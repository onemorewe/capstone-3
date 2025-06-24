package org.yearup.data.mysql;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.yearup.data.UserDao;
import org.yearup.models.AppUser;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class MySqlUserDao extends MySqlDaoBase implements UserDao
{
    @Autowired
    public MySqlUserDao(DataSource dataSource)
    {
        super(dataSource);
    }


    @Override
    public AppUser create(AppUser newAppUser)
    {
        String sql = "INSERT INTO users (username, hashed_password, role) VALUES (?, ?, ?)";
        String hashedPassword = new BCryptPasswordEncoder().encode(newAppUser.getPassword());

        try (Connection connection = getConnection())
        {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, newAppUser.getUsername());
            ps.setString(2, hashedPassword);
            ps.setString(3, newAppUser.getRole());

            ps.executeUpdate();

            AppUser appUser = getByUserName(newAppUser.getUsername());
            appUser.setPassword("");

            return appUser;

        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<AppUser> getAll()
    {
        List<AppUser> appUsers = new ArrayList<>();

        String sql = "SELECT * FROM users";
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql);

            ResultSet row = statement.executeQuery();

            while (row.next())
            {
                AppUser appUser = mapRow(row);
                appUsers.add(appUser);
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }

        return appUsers;
    }

    @Override
    public AppUser getUserById(int id)
    {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet row = statement.executeQuery();

            if(row.next())
            {
                AppUser appUser = mapRow(row);
                return appUser;
            }
        }
        catch (SQLException e)
        {
            throw new RuntimeException(e);
        }
        return null;
    }

    @Override
    public AppUser getByUserName(String username)
    {
        String sql = "SELECT * " +
                " FROM users " +
                " WHERE username = ?";

        try (Connection connection = getConnection())
        {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);

            ResultSet row = statement.executeQuery();
            if(row.next())
            {

                AppUser appUser = mapRow(row);
                return appUser;
            }
        }
        catch (SQLException e)
        {
            System.out.println(e);
        }

        return null;
    }

    @Override
    public int getIdByUsername(String username)
    {
        AppUser appUser = getByUserName(username);

        if (appUser != null)
        {
            return appUser.getId();
        }

        return -1;
    }

    @Override
    public boolean exists(String username)
    {
        AppUser appUser = getByUserName(username);
        return appUser != null;
    }

    private AppUser mapRow(ResultSet row) throws SQLException
    {
        int userId = row.getInt("user_id");
        String username = row.getString("username");
        String hashedPassword = row.getString("hashed_password");
        String role = row.getString("role");

        return new AppUser(userId, username, hashedPassword, role);
    }
}
