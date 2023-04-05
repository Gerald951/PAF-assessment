package ibf2022.paf.assessment.server.repositories;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.stereotype.Repository;

import ibf2022.paf.assessment.server.Exception.InsertTaskException;
import ibf2022.paf.assessment.server.models.User;

// TODO: Task 3
@Repository
public class UserRepository {

    private final static String FIND_USER = "select * from user_ where username=?";
    private final static String INSERT_USER = "insert into user_ (user_id, username, friendly_name) values(?,?,?)";

    @Autowired
    private JdbcTemplate template;

    public Optional<User> findUserByUsername(String username)  {
        return template.query(FIND_USER, new ResultSetExtractor<Optional<User>>() {
            @Override
            public Optional<User> extractData(ResultSet rs) throws SQLException, DataAccessException {
                if (rs.next()) {
                    User user = new User();
                    user.setUserId(rs.getString("user_id"));
                    user.setUsername(rs.getString("username"));
                    user.setName(rs.getString("friendly_name"));
                    return Optional.of(user);
                } else {
                    return Optional.empty();
                }
            }
        }, username);
    
    }

    public String generateUserId() {
        return UUID.randomUUID().toString().substring(0,8);
    }

    public String insertUser(User user) {
        String generatedUUID = generateUserId();

        // update method returns 0 if update fails
        Integer updated = template.update(INSERT_USER, generatedUUID, user.getUsername(), user.getName());
        if (updated > 0) {
            return generatedUUID;
        } else {    
            throw new InsertTaskException("Insert User Fails.");
        } 
    }
}
