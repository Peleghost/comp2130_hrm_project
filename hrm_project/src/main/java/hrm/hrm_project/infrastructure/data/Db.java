package hrm.hrm_project.infrastructure.data;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;

public class Db {
    private static Connection _conn;
    private static final Logger _logger = Logger.getLogger(Db.class.getName());
    private static final String _dbPath = "jdbc:sqlite:src" + File.separator + "database"
            + File.separator + "hrm.db";

    public static Connection getConnection() {
        try {
            if (_conn == null || _conn.isClosed()) {
                _conn = DriverManager.getConnection(_dbPath);
                _logger.info("Connected to database");
            }
        } catch (SQLException ex) {
            _logger.info(ex.toString());
        }
        return _conn;
    }

    public static void closeConnection() {
        try {
            if (!_conn.isClosed()) {
                _conn.close();
                _logger.info("Connection closed");
            } else if (_conn != null) {
                _conn.close();
                _logger.info("Connection closed");
            }
        } catch (SQLException ex) {
            _logger.info(ex.toString());
        }
    }

    public static Logger getLogger() {
        return _logger;
    }
}
