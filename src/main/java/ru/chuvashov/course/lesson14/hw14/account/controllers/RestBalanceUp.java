package ru.chuvashov.course.lesson14.hw14.account.controllers;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.chuvashov.course.lesson14.hw14.account.model.exceptions.NotEnoughMoneyException;
import ru.chuvashov.course.lesson14.hw14.account.model.exceptions.UnknownAccountException;
import ru.chuvashov.course.lesson14.hw14.account.repository.exception.RepositoryException;
import org.springframework.beans.factory.annotation.Autowired;
import ru.chuvashov.course.lesson14.hw14.account.service.AccountService;

/**
 * @author Chuvashov Sergey
 */
@RestController
public class RestBalanceUp {

    private final AccountService service;

    @Autowired
    public RestBalanceUp(AccountService service) {
        this.service = service;
    }

    @GetMapping(value = "/plus")
    public ModelAndView getPageInputData() {
        return new ModelAndView("balanceUppingInput.html");
    }

    /**
     * Увеличивает счёт аккаунта на сумму указанную в соответствующем параметре.
     *
     * @param id     - Идентификатор аккаунта.
     * @param amount - Сумма денег.
     * @return - ответ для HTTP cо списком всех аккаунтов в JSON.
     * @throws RepositoryException     - В случае каких-либо проблем с БД.
     * @throws NotEnoughMoneyException - В случае, если сумма денег в параметре будет иметь отрицательное значение.
     * @throws UnknownAccountException - В случае, если аккаунт с указанным ID не существует.
     */
    @PatchMapping("account/plus")
    public ModelAndView updateAccountBalanceUpping(@RequestParam("id") int id, @RequestParam("amount") int amount)
            throws RepositoryException, NotEnoughMoneyException, UnknownAccountException {

        service.deposit(id, amount);
        return new ModelAndView("redirect:/");
    }
}
