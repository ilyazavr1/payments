package ua.epam.payments.payments.model.dao.impl;

import ua.epam.payments.payments.model.dao.AccountDao;
import ua.epam.payments.payments.model.db.DBManager;
import ua.epam.payments.payments.model.entity.Account;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.services.mapper.AccountMapper;
import ua.epam.payments.payments.model.services.mapper.UserMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountDaoImpl implements AccountDao {
    public static final String SQL_GET_ACCOUNT_BY_ID = "SELECT * FROM account WHERE id=?";
    public static final String SQL_GET_ACCOUNT_BY_NUMBER = "SELECT * FROM account WHERE number=?";
    public static final String SQL_CREATE_ACCOUNT = "INSERT INTO account values (default, ?, default)";
    public static final String SQL_IS_EXIST_ACCOUNT = "SELECT EXISTS(SELECT 1 FROM account WHERE number=?)";
    public static final String SQL_ADD_ACCOUNT_TO_USER = "INSERT INTO user_account values (?, ?)";

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
    public boolean createAccount(Account account) {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_CREATE_ACCOUNT)) {
            stmt.setString(1, account.getNumber());

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
}
