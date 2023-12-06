package services;

import java.sql.SQLException;

public class ServiceException extends RuntimeException {
    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String errorExecutingSqlQuery, SQLException e) {

    }
}
