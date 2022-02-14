package ua.epam.payments.payments.dao.impl;

import ua.epam.payments.payments.dao.AccountDao;
import ua.epam.payments.payments.db.DBManager;
import ua.epam.payments.payments.model.entity.Account;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.services.mapper.AccountMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccountDaoImpl implements AccountDao {
    public static final String SQL_GET_ACCOUNT_BY_ID = "SELECT * FROM account WHERE id=?";
    public static final String SQL_GET_ACCOUNT_BY_NUMBER = "SELECT * FROM account WHERE number=?";
    public static final String SQL_IS_EXIST_ACCOUNT = "SELECT EXISTS(SELECT 1 FROM account WHERE number=?)";
    public static final String SQL_UPDATE_ACCOUNT_WITH_MONEY = "UPDATE account SET money=money+? WHERE id=?";

    public static final String SQL_CREATE_ACCOUNT = "INSERT INTO account values (default, ?, default, ?)";
    public static final String SQL_ADD_ACCOUNT_TO_USER = "UPDATE account SET user_id=? WHERE id=?";
    public static final String SQL_GET_ACCOUNTS_BY_USER = "SELECT * FROM account WHERE user_id=?";

    @Override
    public Account getAccountById(long id) {
        Account account = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_GET_ACCOUNT_BY_ID)) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AccountMapper accountMapper = new AccountMapper();
                    account = accountMapper.mapRSToAccount(rs);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return account;
    }

    @Override
    public List<Account> getAccountsByUser(User user) {
        List<Account> accountsList = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_GET_ACCOUNTS_BY_USER)) {
            stmt.setLong(1, user.getId());

            try (ResultSet rs = stmt.executeQuery()) {
                accountsList = new ArrayList<>();
                while (rs.next()) {
                    AccountMapper accountMapper = new AccountMapper();
                    accountsList.add(accountMapper.mapRSToAccount(rs));

                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return accountsList;
    }

    @Override
    public Account getAccountByNumber(String number) {
        Account account = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_GET_ACCOUNT_BY_NUMBER)) {
            stmt.setString(1, number);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    AccountMapper accountMapper = new AccountMapper();
                    account = accountMapper.mapRSToAccount(rs);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return account;
    }

    @Override
    public boolean createAccountWithUser(Account account, User user) {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_CREATE_ACCOUNT)) {
            stmt.setString(1, account.getNumber());
            stmt.setLong(2, user.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isExistAccount(String number) {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_IS_EXIST_ACCOUNT)) {
            stmt.setString(1, number);
            try (ResultSet rs = stmt.executeQuery()) {

                rs.next();
                return rs.getBoolean(1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean addAccountToUser(Account account, User user) {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_ADD_ACCOUNT_TO_USER)) {
            stmt.setLong(1, user.getId());
            stmt.setLong(2, account.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateAccountWithMoney(Account account, int money) {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_UPDATE_ACCOUNT_WITH_MONEY)) {
           stmt.setInt(1, money);
           stmt.setLong(2, account.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }
}
