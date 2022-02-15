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

    public static final String SQL_TRANSFER_MONEY = "";
    public static final String SQL_WITHDRAW_MONEY = "UPDATE account SET money=money-? WHERE id=?";
    public static final String SQL_TOP_UP_MONEY = "UPDATE account SET money=money+? WHERE id=?";

    public static final String SQL_CREATE_ACCOUNT = "INSERT INTO account values (default, ?, default, default, ?)";
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

  /*  public boolean transferMoneyFromAccToAcc(Account accountSender, Account accountDestination, int money) {

        boolean result1 = false;
        boolean result2 = false;

        Connection con = null;
        PreparedStatement stmtWithdraw = null;
        PreparedStatement stmtTopUp = null;
        try {
            con = DBManager.getInstance().getConnection();
            con.setAutoCommit(false);
            //withdraw
            stmtWithdraw = con.prepareStatement(SQL_WITHDRAW_MONEY);
            stmtWithdraw.setLong(1, accountSender.getId());
            stmtWithdraw.setInt(2, money);
            result1 = stmtWithdraw.executeUpdate() > 0;
            //top up
            stmtTopUp = con.prepareStatement(SQL_TOP_UP_MONEY);
            stmtTopUp.setLong(1, accountDestination.getId());
            stmtTopUp.setInt(2, money);
            result2 = stmtTopUp.executeUpdate() > 0;



            con.commit();
            con.setAutoCommit(true);

            return true;
        } catch (SQLException throwables) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } finally {
            if (con != null && stmtWithdraw != null&& stmtTopUp != null) {
                try {
                    stmtWithdraw.close();
                    stmtTopUp.close();
                    con.close();
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        }
        return false;
    }*/

    @Override
    public boolean transferMoneyFromAccToAcc(long accountSenderId, long accountDestinationId, int money) {
        Connection con = null;
        boolean result1 = false;
        boolean result2 = false;
        try {
            con = DBManager.getInstance().getConnection();
            con.setAutoCommit(false);
            try (PreparedStatement stmtWithdraw = con.prepareStatement(SQL_WITHDRAW_MONEY);
                 PreparedStatement stmtTopUp = con.prepareStatement(SQL_TOP_UP_MONEY);) {
                //withdraw
                stmtWithdraw.setInt(1, money);
                stmtWithdraw.setLong(2, accountSenderId);
                result1 = stmtWithdraw.executeUpdate() > 0;
                //top up
                stmtTopUp.setInt(1, money);
                stmtTopUp.setLong(2, accountDestinationId);
                result2 = stmtTopUp.executeUpdate() > 0;

            }
            con.commit();
            con.setAutoCommit(true);

            if (result1 && result2) {
                return true;
            }
        } catch (SQLException throwables) {
            try {
                if (con != null) {
                    con.rollback();
                    con.setAutoCommit(true);
                }
            } catch (SQLException exception) {
                exception.printStackTrace();
            }
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return false;
    }

    private boolean transferMoney(Connection con, long accountSenderId, long accountDestinationId, int money) throws SQLException {
        boolean result1 = false;
        boolean result2 = false;
        try (PreparedStatement stmtWithdraw = con.prepareStatement(SQL_WITHDRAW_MONEY);
             PreparedStatement stmtTopUp = con.prepareStatement(SQL_TOP_UP_MONEY);) {
            //withdraw
            stmtWithdraw.setLong(1, accountSenderId);
            stmtWithdraw.setInt(2, money);
            result1 = stmtWithdraw.executeUpdate() > 0;
            //top up
            stmtTopUp.setLong(1, accountDestinationId);
            stmtTopUp.setInt(2, money);
            result2 = stmtTopUp.executeUpdate() > 0;
            System.out.println("traaasdasdasdasdas   1");
            if (result1 == true && result2 == true) {
                System.out.println("traaasdasdasdasdas   2");
                return true;
            }
        } catch (Exception e) {
            System.out.println("oshibka");
        }
        return false;
    }

}
