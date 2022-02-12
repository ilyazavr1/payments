package ua.epam.payments.payments.model.dao;

import ua.epam.payments.payments.model.entity.Account;
import ua.epam.payments.payments.model.entity.User;

import java.util.List;

public interface AccountDao {

    public Account getAccountById(long id);

    public Account getAccountByNumber(String number);

    public boolean createAccount(Account account);

    public boolean isExistAccount(String number);

    boolean addAccountToUser(Account account, User user);

    List<Account> getAccountsByUser(User user);

}
