package ru.chuvashov.course.lesson14.hw14.account.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.chuvashov.course.lesson14.hw14.account.model.exceptions.UnknownAccountException;
import ru.chuvashov.course.lesson14.hw14.account.repository.exception.RepositoryException;
import ru.chuvashov.course.lesson14.hw14.account.service.AccountService;

@RestController
public class RestGetBalance {

    private final AccountService service;

    @Autowired
    public RestGetBalance(AccountService service) {
        this.service = service;
    }

    @GetMapping(value = "/getbalance")
    public ModelAndView getPageBalance() {
        return new ModelAndView("getbalance.html");
    }

    @GetMapping(value = "/balance")
    public ModelAndView getPageInputId() {
        return new ModelAndView("balanceInput.html");
    }

    /**
     * Возвращает баланс указанного аккаунта.
     *
     * @param id - Идентификатор аккаунта.
     * @return -  Ответ для HTTP с балансом аккаунта в JSON.
     * @throws RepositoryException     - В случае каких-либо проблем с БД.
     * @throws UnknownAccountException - В случае, если аккаунт с указанным ID не существует.
     */
    @GetMapping(value = "/balance/answer/{id}")
    public ResponseEntity<Integer> getBalanceAnswer(@PathVariable("id") int id)
            throws RepositoryException, UnknownAccountException {

        return ResponseEntity.accepted().body(service.getBalance(id));
    }
}
