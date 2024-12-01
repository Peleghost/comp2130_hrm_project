package hrm.hrm_project.domain.interfaces;

import java.sql.SQLException;

import hrm.hrm_project.domain.entities.Employee;
import hrm.hrm_project.infrastructure.data.Db;

public interface IEmployee {
    
    Db.DbResult insertEmployee(Employee emp) throws SQLException;

    Employee getEmployeeById(int id);
   
    Db.DbResult updateEmployee(Employee emp) throws SQLException;

    Db.DbResult deleteEmployee(int id) throws SQLException;

    boolean login(String username, String password);
}
