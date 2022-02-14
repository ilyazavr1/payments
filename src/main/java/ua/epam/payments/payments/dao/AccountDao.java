package ua.epam.payments.payments.dao;

import ua.epam.payments.payments.model.entity.Account;
import ua.epam.payments.payments.model.entity.User;

import java.util.List;

public interface AccountDao {

    public Account getAccountById(long id);

    public Account getAccountByNumber(String number);

    List<Account> getAccountsByUser(User user);

    public boolean createAccountWithUser(Account account, User user);

    public boolean isExistAccount(String number);

    boolean addAccountToUser(Account account, User user);

    boolean updateAccountWithMoney(Account account, int money);

}
