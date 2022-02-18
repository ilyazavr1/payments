package ua.epam.payments.payments.dao.impl;

import ua.epam.payments.payments.dao.CardDao;
import ua.epam.payments.payments.db.DBManager;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.services.mapper.CardMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CardDaoImpl implements CardDao {
    public static final String SQL_GET_CARD_BY_ID = "SELECT * FROM card WHERE id=?";
    public static final String SQL_GET_CARD_BY_NUMBER = "SELECT * FROM card WHERE number=?";
    public static final String SQL_IS_EXIST_CARD = "SELECT EXISTS(SELECT 1 FROM card WHERE number=?)";
    public static final String SQL_UPDATE_CARD_WITH_MONEY = "UPDATE card SET money=money+? WHERE id=?";

    public static final String SQL_TRANSFER_MONEY = "";
    public static final String SQL_WITHDRAW_MONEY = "UPDATE card SET money=money-? WHERE id=?";
    public static final String SQL_TOP_UP_MONEY = "UPDATE card SET money=money+? WHERE id=?";

    public static final String SQL_CREATE_CARD = "INSERT INTO card values (default, ?, ?, default, default, ?)";
    public static final String SQL_ADD_CARD_TO_USER = "UPDATE card SET user_id=? WHERE id=?";
    public static final String SQL_GET_CARD_BY_USER = "SELECT * FROM card WHERE user_id=?";
    public static final String SQL_GET_CARD_BY_USER_LIMIT = "SELECT * FROM card WHERE user_id=? LIMIT ? OFFSET ?";

    @Override
    public Card getCardById(long id) {
        Card account = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_GET_CARD_BY_ID)) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CardMapper accountMapper = new CardMapper();
                    account = accountMapper.mapRSToCard(rs);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return account;
    }

    @Override
    public List<Card> getCardByUserLimit(User user, int limit, int offset) {
        List<Card> accountsList = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_GET_CARD_BY_USER_LIMIT)) {
            stmt.setLong(1, user.getId());
            stmt.setInt(2, limit);
            stmt.setInt(3, offset);
            try (ResultSet rs = stmt.executeQuery()) {
                accountsList = new ArrayList<>();
                while (rs.next()) {
                    CardMapper accountMapper = new CardMapper();
                    accountsList.add(accountMapper.mapRSToCard(rs));

                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return accountsList;
    }

    @Override
    public List<Card> getCardByUser(User user) {
        List<Card> accountsList = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_GET_CARD_BY_USER)) {
            stmt.setLong(1, user.getId());

            try (ResultSet rs = stmt.executeQuery()) {
                accountsList = new ArrayList<>();
                while (rs.next()) {
                    CardMapper accountMapper = new CardMapper();
                    accountsList.add(accountMapper.mapRSToCard(rs));

                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return accountsList;
    }

    @Override
    public Card getCardByNumber(String number) {
        Card account = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_GET_CARD_BY_NUMBER)) {
            stmt.setString(1, number);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CardMapper accountMapper = new CardMapper();
                    account = accountMapper.mapRSToCard(rs);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return account;
    }

    @Override
    public boolean createCardWithUser(Card card, User user) {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_CREATE_CARD)) {
            stmt.setString(1, card.getName());
            stmt.setString(2, card.getNumber());
            stmt.setLong(3, user.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isExistCard(String number) {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_IS_EXIST_CARD)) {
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
    public boolean addCardToUser(Card card, User user) {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_ADD_CARD_TO_USER)) {
            stmt.setLong(1, user.getId());
            stmt.setLong(2, card.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean updateCardWithMoney(Card card, int money) {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_UPDATE_CARD_WITH_MONEY)) {
            stmt.setInt(1, money);
            stmt.setLong(2, card.getId());

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
    public boolean transferMoneyFromCardToCard(long cardSenderId, long cardDestinationId, int money) {
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
                stmtWithdraw.setLong(2, cardSenderId);
                result1 = stmtWithdraw.executeUpdate() > 0;
                //top up
                stmtTopUp.setInt(1, money);
                stmtTopUp.setLong(2, cardDestinationId);
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

   /* private boolean transferMoney(Connection con, long accountSenderId, long accountDestinationId, int money) throws SQLException {
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
    }*/

}
