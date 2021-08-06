package ru.chuvashov.course.lesson14.hw14.account.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.chuvashov.course.lesson14.hw14.account.model.exceptions.NotEnoughMoneyException;
import ru.chuvashov.course.lesson14.hw14.account.model.exceptions.UnknownAccountException;
import ru.chuvashov.course.lesson14.hw14.account.repository.exception.RepositoryException;
import ru.chuvashov.course.lesson14.hw14.account.service.AccountService;

/**
 * @author Chuvashov Sergey
 */
@RestController
public class RestBalanceTransfer {

    private final AccountService service;

    @Autowired
    public RestBalanceTransfer(AccountService service) {
        this.service = service;
    }

    @GetMapping(value = "/transfer")
    public ModelAndView getPageInputData() {
        return new ModelAndView("balanceTransferInput.html");
    }

    /**
     * Перечисляет указанную сумму с одного счёта на другой.
     *
     * @param idTo   - Идентификатор аккаунта.
     * @param amount - Сумма денег.
     * @return - ответ для HTTP cо списком всех аккаунтов в JSON.
     * @throws RepositoryException     - В случае каких-либо проблем с БД.
     * @throws NotEnoughMoneyException - В случае, если сумма денег в параметре будет иметь отрицательное значение.
     * @throws UnknownAccountException - В случае, если аккаунт с указанным ID не существует.
     */
    @PatchMapping("account/transfer")
    public ModelAndView updateAccountBalanceUpping(@RequestParam("id_to") int idTo,
                                                   @RequestParam("id_from") int idFrom,
                                                   @RequestParam("amount") int amount)
            throws RepositoryException, NotEnoughMoneyException, UnknownAccountException {

        service.transfer(idFrom, idTo, amount);
        return new ModelAndView("redirect:/");
    }
}
