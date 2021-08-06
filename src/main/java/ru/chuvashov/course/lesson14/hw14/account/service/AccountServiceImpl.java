package ru.chuvashov.course.lesson14.hw14.account.service;

import ru.chuvashov.course.lesson14.hw14.account.annotation.CacheGetBalance;
import ru.chuvashov.course.lesson14.hw14.account.model.Account;
import ru.chuvashov.course.lesson14.hw14.account.model.exceptions.NotEnoughMoneyException;
import ru.chuvashov.course.lesson14.hw14.account.model.exceptions.UnknownAccountException;
import ru.chuvashov.course.lesson14.hw14.account.repository.AccountRepository;
import ru.chuvashov.course.lesson14.hw14.account.repository.exception.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Chuvashov Sergey
 */
@Component
public class AccountServiceImpl implements AccountService {

    private final AccountRepository repository;

    @Autowired
    public AccountServiceImpl(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public void withDraw(int accountId, int amount)
            throws NotEnoughMoneyException, UnknownAccountException, RepositoryException {

        if (amount < 0) {
            throw new NotEnoughMoneyException("Значение 'amount' не может быть отрицательеым: " + amount);
        }
        if (accountId < 0) {
            throw new UnknownAccountException("Значение 'accountId' не может быть отрицательеым: " + accountId);
        }

        Account account = repository.read(accountId);
        account.setAmount(account.getAmount() - amount);
        repository.update(account);
    }

    @Override
    @CacheGetBalance
    public int getBalance(int accountId) throws UnknownAccountException, RepositoryException {

        if (accountId < 0) {
            throw new UnknownAccountException("Значение 'accountId' не может быть отрицательеым: " + accountId);
        }

        Account account = repository.read(accountId);
        return account.getAmount();
    }

    @Override
    public void deposit(int accountId, int amount)
            throws NotEnoughMoneyException, UnknownAccountException, RepositoryException {

        if (amount < 0) {
            throw new NotEnoughMoneyException("Значение 'amount' не может быть отрицательеым: " + amount);
        }
        if (accountId < 0) {
            throw new UnknownAccountException("Значение 'accountId' не может быть отрицательеым: " + accountId);
        }
        Account tmp = repository.read(accountId);
        tmp.setAmount(tmp.getAmount() + amount);
        repository.update(tmp);
    }

    @Override
    public void transfer(int from, int to, int amount)
            throws NotEnoughMoneyException, UnknownAccountException, RepositoryException {

        if (amount < 0) {
            throw new NotEnoughMoneyException("Значение 'amount' не может быть отрицательеым: " + amount);
        }
        if (from < 0) {
            throw new UnknownAccountException("Значение 'from' не может быть отрицательеым: " + from);
        }
        if (to < 0) {
            throw new UnknownAccountException("Значение 'accountId' не может быть отрицательеым: " + to);
        }

        Account accountFrom = repository.read(from);
        Account accountTo = repository.read(to);
        accountFrom.setAmount(accountFrom.getAmount() - amount);
        accountTo.setAmount(accountTo.getAmount() + amount);
        repository.update(accountFrom);
        repository.update(accountTo);
    }
}
