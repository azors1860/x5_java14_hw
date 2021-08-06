package ru.chuvashov.course.lesson14.hw14.account.repository;

import lombok.NonNull;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Repository;
import ru.chuvashov.course.lesson14.hw14.account.annotation.CachePutBalance;
import ru.chuvashov.course.lesson14.hw14.account.model.Account;
import ru.chuvashov.course.lesson14.hw14.account.model.exceptions.UnknownAccountException;
import ru.chuvashov.course.lesson14.hw14.account.repository.exception.RepositoryException;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author Chuvashov Sergey
 */
@Repository
public class AccountRepository {

    private SessionFactory sessionFactory;

    /**
     * Создаёт новый объект
     *
     * @param item - Объект который должен быть создан.
     * @throws RepositoryException - В случае, если возникнут проблемы с чтением информации из
     *                              БД, либо при записи информации в БД.
     */
    public void create(@NonNull Account item) throws RepositoryException {
        try {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.save(item);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            throw new RepositoryException("Error creating the accounts", e);
        }
    }

    /**
     * Обновляет (изменяет) существующий объект в базе.
     *
     * @param item - Объект который должен быть изменен.
     * @throws UnknownAccountException Если аккаунт с идентификатором из параметра не найден в списке.
     */
    @CachePutBalance
    public void update(@NonNull Account item) throws RepositoryException, UnknownAccountException {
        if (read(item.getId()) != null) {
            try {
                Session session = sessionFactory.openSession();
                Transaction transaction = session.beginTransaction();
                session.update(item);
                transaction.commit();
                session.close();
            } catch (Exception e) {
                throw new RepositoryException("Error updating the accounts", e);
            }
        }
    }

    /**
     * Возвращает объект с соответствующим идентификатором из параметра (из БД).
     *
     * @param id - Идентификартор объекта.
     * @return - Объект аккаунта с идентификатором указанный в параметре метода.
     * @throws UnknownAccountException Если аккаунт с идентификатором из параметра не найден в списке,
     *                                 либо если параметр имеет отризательное значение.
     * @throws RepositoryException    - В случае, если возникнут проблемы с чтением информации из
     *                                 БД, либо при записи информации в БД.
     */
    public Account read(int id) throws RepositoryException, UnknownAccountException {

        if (id < 1) {
            throw new UnknownAccountException("Некорректный идентификатор акаауета на входе: " + id);
        }
        Account result;
        try {
            Session session = sessionFactory.openSession();
            result = session.get(Account.class, id);
            session.close();
        } catch (Exception e) {
            throw new RepositoryException("Error reading the accounts", e);
        }
        if (result == null) {
            throw new UnknownAccountException("Произошла ошибка при получении объекта с указанным id: " + id);
        }
        return result;
    }

    /**
     * Удаляет переданный объект из БД.
     *
     * @param item - Объект, который должен быть удалён из базы.
     * @throws UnknownAccountException Если аккаунт с идентификатором из параметра не найден в списке.
     * @throws RepositoryException    - В случае, если возникнут проблемы с чтением информации из
     *                                 БД, либо при записи информации в БД.
     */
    public void delete(@NonNull Account item) throws RepositoryException, UnknownAccountException {

        if (read(item.getId()) != null) {
            try {
                Session session = sessionFactory.openSession();
                Transaction transaction = session.beginTransaction();
                session.delete(item);
                transaction.commit();
                session.close();
            } catch (Exception e) {
                throw new RepositoryException("Error reading the accounts", e);
            }
        }
    }

    /**
     * Метод для получения всех объектов, имеющихся в БД.
     *
     * @return - Лист со всеми аккаунтами.
     * @throws RepositoryException - В случае, если возникнут проблемы с чтением информации из
     *                              БД, либо при записи информации в БД.
     */
    public List<Account> getListAccounts() throws RepositoryException {
        List<Account> accounts;
        try {
            Session session = sessionFactory.openSession();
            accounts = (List<Account>) session.createQuery("From Account").list();
            session.close();
        } catch (Exception e) {
            throw new RepositoryException("Error reading the accounts", e);
        }
        if (accounts == null) {
            throw new RepositoryException("Error reading the accounts");
        } else {
            return accounts;
        }
    }

    /**
     * Инициализирует поле 'sessionFactory'.
     */
    @PostConstruct
    private void initialization() {
        if (sessionFactory == null) {
            Configuration configuration = new Configuration().configure();
            configuration.addAnnotatedClass(Account.class);
            StandardServiceRegistryBuilder builder =
                    new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
            sessionFactory = configuration.buildSessionFactory(builder.build());
        }
    }
}


