package ua.epam.payments.payments.dao;

import ua.epam.payments.payments.model.entity.Account;
import ua.epam.payments.payments.model.entity.User;

import java.util.List;

public interface AccountDao {

    Account getAccountById(long id);

    Account getAccountByNumber(String number);

    List<Account> getAccountsByUser(User user);

    boolean createAccountWithUser(Account account, User user);

    boolean addAccountToUser(Account account, User user);

    boolean updateAccountWithMoney(Account account, int money);

    boolean transferMoneyFromAccToAcc(long accountSenderId, long accountDestinationId, int money);

    boolean isExistAccount(String number);

}
