package ru.chuvashov.course.lesson14.hw14.account.service;

import ru.chuvashov.course.lesson14.hw14.account.model.exceptions.NotEnoughMoneyException;
import ru.chuvashov.course.lesson14.hw14.account.model.exceptions.UnknownAccountException;
import ru.chuvashov.course.lesson14.hw14.account.repository.exception.RepositoryException;

/**
 * @author Chuvashov Sergey
 */
public interface AccountService {

    /**
     * Снять с указанного счёта указанную сумму денег.
     *
     * @param accountId - Идентификатор аккаунта.
     * @param amount    - Сумма, которая будет списана со счёта.
     * @throws NotEnoughMoneyException - В случае, если указанная в параметре сумма имеет отрицательное значение,
     *                                 либо если после вычета со счёта аккаунта на аккаунте будет отрицательеый баланс.
     * @throws UnknownAccountException - В случае, если указанный параметр имеет отрицательное значение,
     *                                 либо если аккаут с указанным идентификатором не будет найден в списке.
     * @throws RepositoryException    - В случае, если возникнут проблемы с выполнениями операций
     *                                 связанные с базой данных.
     */
    void withDraw(int accountId, int amount)
            throws NotEnoughMoneyException, UnknownAccountException, RepositoryException;

    /**
     * Возвращает баланс указанного аккаунта в консоль.
     *
     * @param accountId - Идентификатор аккаунта.
     * @return - Баланс аккаунта.
     * @throws UnknownAccountException - В случае, если указанный параметр имеет отрицательное значение,
     *                                 либо если аккаут с указанным идентификатором не будет найден в списке.
     * @throws RepositoryException    - В случае, если возникнут проблемы с выполнениями операций
     *                                 связанные с базой данных.
     */
    int getBalance(int accountId)
            throws UnknownAccountException, RepositoryException;

    /**
     * Пополнить указанный счёт на указанную сумму денег.
     *
     * @param accountId - Идентификатор аккаунта.
     * @param amount    - Сумма, на которую будет пополнен счёт.
     * @throws NotEnoughMoneyException - В случае, если указанная в параметре сумма имеет отрицательное значение.
     * @throws UnknownAccountException - В случае, если указанный параметр имеет отрицательное значение,
     *                                 либо если аккаут с указанным идентификатором не будет найден в списке.
     * @throws RepositoryException    - В случае, если возникнут проблемы с выполнениями операций
     *                                 связанные с базой данных.
     */
    void deposit(int accountId, int amount)
            throws NotEnoughMoneyException, UnknownAccountException, RepositoryException;

    /**
     * Перевести деньги с одного счёта на другой (указаны в параметре) на указанную в параметре сумму.
     *
     * @param from   - Идентификатор аккаунта, со счёта которого будут списаны деньги.
     * @param to     - Идентификатор аккаунта, на счёт которого будут зачислены деньги.
     * @param amount - Сумма денег для перевода.
     * @throws NotEnoughMoneyException - В случае, если указанная в параметре сумма имеет отрицательное значение,
     *                                 либо если после вычета со счёта аккаунта на аккаунте будет отрицательеый баланс.
     * @throws UnknownAccountException - В случае, если указанный параметр имеет отрицательное значение,
     *                                 либо если аккаут с указанным идентификатором не будет найден в списке.
     * @throws RepositoryException    - В случае, если возникнут проблемы с выполнениями операций
     *                                 связанные с базой данных.
     */
    void transfer(int from, int to, int amount)
            throws NotEnoughMoneyException, UnknownAccountException, RepositoryException;
}
