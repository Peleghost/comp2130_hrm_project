package hrm.hrm_project.domain.interfaces;

import hrm.hrm_project.domain.entities.User;
import hrm.hrm_project.infrastructure.data.Db;

import java.sql.SQLException;

public interface IUser {
    Db.DbResult insertUser(User user) throws SQLException;
    User getUserById(int id);
    Db.DbResult updateUser(User user) throws SQLException;
    Db.DbResult deleteUser(int id) throws SQLException;
}
