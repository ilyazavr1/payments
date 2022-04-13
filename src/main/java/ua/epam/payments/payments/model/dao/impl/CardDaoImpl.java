package ua.epam.payments.payments.model.dao.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.payments.payments.model.dao.CardDao;
import ua.epam.payments.payments.db.DBManager;
import ua.epam.payments.payments.model.entity.dto.CardsUnblockRequestDto;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.dao.mapper.CardMapper;

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
    public static final String SQL_BLOCK_CARD = "UPDATE card SET blocked=true WHERE id=?";

    public static final String SQL_UNBLOCK_CARD = "UPDATE card SET blocked=false, under_consideration=false WHERE id=?";
    public static final String SQL_UPDATE_CARD_CONSIDERATION = "UPDATE card SET under_consideration=true WHERE id=?";
    public static final String SQL_CREATE_CARD_UNBLOCK_REQUEST = "INSERT INTO card_unblock_request VALUES (default, ?, ?, ?, ?, ?, ?, ?, ?);";


    public static final String SQL_WITHDRAW_MONEY = "UPDATE card SET money=money-? WHERE id=?";
    public static final String SQL_TOP_UP_MONEY = "UPDATE card SET money=money+? WHERE id=?";

    public static final String SQL_CREATE_CARD = "INSERT INTO card values (default, ?, ?, default, default,default, ?)";
    public static final String SQL_ADD_CARD_TO_USER = "UPDATE card SET user_id=? WHERE id=?";
    public static final String SQL_GET_CARD_BY_USER = "SELECT * FROM card WHERE user_id=?";
    public static final String SQL_COUNT_CARD_BY_USER = "SELECT count(card.id) FROM card WHERE user_id =?;";
    public static final String SQL_GET_CARD_BY_USER_LIMIT = "SELECT * FROM card WHERE user_id=? LIMIT ? OFFSET ?";
    public static final String SQL_GET_CARDS_REQUESTS = "select * from card_unblock_request";
    public static final String SQL_DELETE_CARD_REQUEST = "DELETE FROM card_unblock_request WHERE card_id = ?;";
    //public static final String SQL_GET_CARD_BY_USER_LIMIT_SORTED = "SELECT * FROM card WHERE user_id=? ORDER BY ? LIMIT ? OFFSET ?";

    private final Logger logger = LogManager.getLogger(CardDaoImpl.class);

    @Override
    public Card getCardById(long id) {
        Card card = null;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_GET_CARD_BY_ID)) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    CardMapper accountMapper = new CardMapper();
                    card = accountMapper.mapRSToCard(rs);
                }
            }

        } catch (SQLException throwables) {
            logger.error("{}, when trying to get Card by Id = {}", throwables.getMessage(), id);
            throw new RuntimeException(throwables);
        }

        return card;
    }

/*    @Override
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
    }*/

    //"SELECT * FROM card WHERE user_id=? ORDER BY ? ? LIMIT ? OFFSET ?";
    @Override
    public List<CardsUnblockRequestDto> getCardRequests() {
        List<CardsUnblockRequestDto> list;

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_GET_CARDS_REQUESTS)) {


            try (ResultSet rs = stmt.executeQuery()) {
                list = new ArrayList<>();
                while (rs.next()) {
                    CardMapper accountMapper = new CardMapper();
                    list.add(accountMapper.mapRSToCardRequest(rs));

                }
            }

        } catch (SQLException throwables) {
            logger.error("{}, when trying to get card unblock requests", throwables.getMessage());
            throw new RuntimeException(throwables);
        }

        return list;
    }

    @Override
    public List<Card> getCardByUserLimitSorted(long id, String query) {
        return getCards(id, query);
    }

    @Override
    public List<Card> getCardByUserId(long id) {
        return getCards(id, SQL_GET_CARD_BY_USER);
    }

    private List<Card> getCards(long id, String query) {
        List<Card> accountsList = new ArrayList<>();

        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setLong(1, id);

            try (ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    CardMapper accountMapper = new CardMapper();
                    accountsList.add(accountMapper.mapRSToCard(rs));

                }
            }

        } catch (SQLException throwables) {
            logger.error("{}, when trying to get cards with different input queries by User id = {}, query = [{}]"
                    , throwables.getMessage()
                    , id
                    , query
            );
            throw new RuntimeException(throwables);
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
            logger.error("{}, when trying to get Card by Number = {}", throwables.getMessage(), number);
            throw new RuntimeException(throwables);
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
            logger.error("{}, when trying to create card with Number = {}, User = {}", throwables.getMessage(), card.getNumber(), user.getEmail());
            throw new RuntimeException(throwables);
        }
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
            logger.error("{}, when trying to check card existence with Number = {}", throwables.getMessage(), number);
            throw new RuntimeException(throwables);
        }
    }


    @Override
    public boolean updateCardWithMoney(Card card, int money) {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_UPDATE_CARD_WITH_MONEY)) {
            stmt.setInt(1, money);
            stmt.setLong(2, card.getId());

            return stmt.executeUpdate() > 0;

        } catch (SQLException throwables) {
            logger.error("{}, when trying to add money on card balance", throwables.getMessage());
            throw new RuntimeException(throwables);
        }
    }

    @Override
    public boolean blockCardById(long id) {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_BLOCK_CARD)) {
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException throwables) {
            logger.error("{}, when trying to block card by id", throwables.getMessage());
            throw new RuntimeException(throwables);
        }
    }

    @Override
    public boolean unblockCardById(long id) {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_UNBLOCK_CARD)) {
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException throwables) {
            logger.error("{}, when trying to unblock card by id", throwables.getMessage());
            throw new RuntimeException(throwables);
        }
    }

    @Override
    public boolean deleteCardRequestByCardId(long id) {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_DELETE_CARD_REQUEST)) {
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException throwables) {
            logger.error("{}, when trying delete user requests to unblock card", throwables.getMessage());
            throw new RuntimeException(throwables);
        }
    }

    @Override
    public boolean updateCardConsiderationById(long id) {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_UPDATE_CARD_CONSIDERATION)) {
            stmt.setLong(1, id);
            return stmt.executeUpdate() > 0;

        } catch (SQLException throwables) {
            logger.error("{}, when trying to change card reconsideration status", throwables.getMessage());
            throw new RuntimeException(throwables);
        }
    }

    @Override
    public boolean createCardUnblockRequest(Card card, User user) {
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_CREATE_CARD_UNBLOCK_REQUEST)) {
            stmt.setLong(1, user.getId());
            stmt.setLong(2, card.getId());
            stmt.setString(3, user.getFirstName());
            stmt.setString(4, user.getLastName());
            stmt.setString(5, user.getSurname());
            stmt.setString(6, card.getNumber());
            stmt.setInt(7, card.getMoney());
            stmt.setBoolean(8, card.isBlocked());


            return stmt.executeUpdate() > 0;
        } catch (SQLException throwables) {
            logger.error("{}, when trying create request to unblock card id = {}, user email = {}", throwables.getMessage(), card.getId(),user.getEmail());
            throw new RuntimeException(throwables);
        }

    }


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

            if (result1 && result2) {
                con.commit();
                con.setAutoCommit(true);
            }else {
                con.rollback();
                con.setAutoCommit(true);
                return false;
            }

        } catch (SQLException throwables) {
            try {
                if (con != null) {
                    con.rollback();
                    con.setAutoCommit(true);
                }
            } catch (SQLException exception) {
                logger.error("{}, when trying to transfer money between cards" +
                        " sender id = {} and destination id = {}, money to transfer = {}",
                        throwables.getMessage(),
                        cardSenderId,
                        cardDestinationId,money
                );
                throw new RuntimeException(throwables);
            }
        } finally {
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException throwables) {
                logger.error("{}, when trying to transfer money between cards" +
                                " sender id = {} and destination id = {}, money to transfer = {}",
                        throwables.getMessage(),
                        cardSenderId,
                        cardDestinationId,money
                );
                throw new RuntimeException(throwables);
            }
        }
        return true;
    }


}




/*    @Override
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

            @Override
    public int countCardsByUser(User user) {
        int countCards = 0;
        try (Connection con = DBManager.getInstance().getConnection();
             PreparedStatement stmt = con.prepareStatement(SQL_COUNT_CARD_BY_USER)) {
            stmt.setLong(1, user.getId());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    countCards = rs.getInt(1);
                }
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return countCards;
    }
    }*/