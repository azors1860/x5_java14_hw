package ru.chuvashov.course.lesson14.hw14.account.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import ru.chuvashov.course.lesson14.hw14.account.model.Account;
import ru.chuvashov.course.lesson14.hw14.account.model.exceptions.NotEnoughMoneyException;
import ru.chuvashov.course.lesson14.hw14.account.model.exceptions.UnknownAccountException;
import ru.chuvashov.course.lesson14.hw14.account.repository.AccountRepository;
import ru.chuvashov.course.lesson14.hw14.account.repository.exception.RepositoryException;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class AccountServiceImplTest {

    @Mock
    AccountRepository repository;

    @InjectMocks
    AccountServiceImpl service;

    @Test
    public void testGetBalanceWhenAccountExists() throws UnknownAccountException, RepositoryException {
        Account account = new Account(1, "Василий Петрович", 10);
        when(repository.read(1)).thenReturn(account);
        assertEquals(service.getBalance(1), 10);
    }

    @Test(expected = UnknownAccountException.class)
    public void testGetBalanceWhenAccountNotExistsThenException() throws UnknownAccountException, RepositoryException {
        when(repository.read(100)).thenThrow(UnknownAccountException.class);
        service.getBalance(100);
    }

    @Test
    public void testDepositWhenPresentInDataSource() throws RepositoryException, UnknownAccountException, NotEnoughMoneyException {

        Account account = new Account(1, "Василий Петрович", 10);
        Account updateAccount = new Account(1, "Василий Петрович", 30);
        when(repository.read(1)).thenReturn(account);

        service.deposit(1, 20);

        verify(repository).update(updateAccount);

    }

    @Test(expected = NotEnoughMoneyException.class)
    public void testDepositWhenAmountValuesNegativeWhenException()
            throws UnknownAccountException, NotEnoughMoneyException, RepositoryException {

        service.deposit(1, -1);
    }

    @Test(expected = UnknownAccountException.class)
    public void testDepositWhenAccountNotExistsThenException()
            throws UnknownAccountException, NotEnoughMoneyException, RepositoryException {

        when(repository.read(100)).thenThrow(UnknownAccountException.class);

        service.deposit(100, 1);
    }

    @Test
    public void testWithDrawWhenPresentInDataSource()
            throws UnknownAccountException, NotEnoughMoneyException, RepositoryException {

        Account account = new Account(1, "Василий Петрович", 30);
        Account updateAccount = new Account(1, "Василий Петрович", 10);
        when(repository.read(1)).thenReturn(account);
        service.withDraw(1, 20);

        verify(repository).update(updateAccount);

    }

    @Test(expected = UnknownAccountException.class)
    public void testWithDrawWhenAccountNotExistsThenException()
            throws UnknownAccountException, NotEnoughMoneyException, RepositoryException {

        when(repository.read(100)).thenThrow(UnknownAccountException.class);

        service.withDraw(100, 1);
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void testWithDrawWhenAmountValuesNegativeWhenException()
            throws UnknownAccountException, RepositoryException, NotEnoughMoneyException {

        service.withDraw(1, -1);
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void testWithDrawWhenNotEnoughMoneyAccountThenException()
            throws UnknownAccountException, RepositoryException, NotEnoughMoneyException {

        Account account = new Account(1, "Василий Петрович", 0);
        when(repository.read(1)).thenReturn(account);
        service.withDraw(1, 20);
    }

    @Test
    public void testTransferWhenPresentInDataSource()
            throws RepositoryException, UnknownAccountException, NotEnoughMoneyException {

        Account account1 = new Account(1, "Василий Петрович", 30);
        Account account2 = new Account(2, "Василиса Петровна", 30);
        Account updateAccount1 = new Account(1, "Василий Петрович", 20);
        Account updateAccount2 = new Account(2, "Василиса Петровна", 40);

        when(repository.read(1)).thenReturn(account1);
        when(repository.read(2)).thenReturn(account2);

        service.transfer(1, 2, 10);
        verify(repository).update(updateAccount1);
        verify(repository).update(updateAccount2);
    }

    @Test(expected = UnknownAccountException.class)
    public void testTransferWhenAccount1NotExistsThenException()
            throws UnknownAccountException, RepositoryException, NotEnoughMoneyException {

        when(repository.read(20)).thenThrow(UnknownAccountException.class);

        service.transfer(20, 1, 5);
    }

    @Test(expected = UnknownAccountException.class)
    public void testTransferWhenAccount2NotExistsThenException()
            throws UnknownAccountException, NotEnoughMoneyException, RepositoryException {

        Account account1 = new Account(1, "Василий Петрович", 30);
        when(repository.read(1)).thenReturn(account1);
        when(repository.read(20)).thenThrow(UnknownAccountException.class);

        service.transfer(1, 20, 5);
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void testTransferWhenAmountValuesNegativeWhenException()
            throws UnknownAccountException, NotEnoughMoneyException, RepositoryException {

        service.transfer(1, 20, -5);
    }

    @Test(expected = NotEnoughMoneyException.class)
    public void testTransferWhenNotEnoughMoneyAccountThenException()
            throws UnknownAccountException, NotEnoughMoneyException, RepositoryException {

        Account account1 = new Account(1, "Василий Петрович", 30);
        Account account2 = new Account(2, "Василиса Петровна", 30);

        when(repository.read(1)).thenReturn(account1);
        when(repository.read(2)).thenReturn(account2);

        service.transfer(1, 2, 40);
    }
}
