package ru.chuvashov.course.lesson14.hw14.account.model;

import org.junit.Test;
import ru.chuvashov.course.lesson14.hw14.account.model.exceptions.NotEnoughMoneyException;


public class AccountTest {

    @Test(expected = NotEnoughMoneyException.class)
    public void setAmountTest() throws NotEnoughMoneyException {
        new Account().setAmount(-10);
    }
}
