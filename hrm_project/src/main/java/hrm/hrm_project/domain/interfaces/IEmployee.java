package hrm.hrm_project.domain.interfaces;

import hrm.hrm_project.domain.entities.Employee;
import hrm.hrm_project.infrastructure.data.Db;

import java.sql.SQLException;

public interface IEmployee {
    public Db.DbResult insertEmployee(Employee emp) throws SQLException;
}
