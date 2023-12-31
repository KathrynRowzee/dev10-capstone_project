package learn.capstone.data.mappers;

import learn.capstone.models.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;


public class UserMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        User user = new User();
        user.setUserId(resultSet.getInt("user_id"));
        user.setFirstName(resultSet.getString("first_name"));
        user.setLastName(resultSet.getString("last_name"));
        user.setUsername(resultSet.getString("user_name"));
        user.setPassword(resultSet.getString("password"));
        user.setUserType(Boolean.parseBoolean(resultSet.getString("user_type")));
        return user;
    }
}
