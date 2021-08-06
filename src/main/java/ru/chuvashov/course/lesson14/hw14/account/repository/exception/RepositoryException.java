package ru.chuvashov.course.lesson14.hw14.account.repository.exception;

/**
 * @author Chuvashov Sergey
 * Выбрасывается, чтобы указать что, возникли проблемы c получением или записью информации Dao.
 */
public class RepositoryException extends Exception {

    public RepositoryException(String message, Throwable e) {
        super(message, e);
    }

    public RepositoryException(String message) {
        super(message);
    }
}
