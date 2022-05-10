package ua.epam.payments.payments.web.servlets;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ua.epam.payments.payments.model.dao.CardDao;
import ua.epam.payments.payments.model.dao.impl.CardDaoImpl;
import ua.epam.payments.payments.model.dao.impl.PaymentsDaoImpl;
import ua.epam.payments.payments.model.entity.Card;
import ua.epam.payments.payments.model.entity.User;
import ua.epam.payments.payments.model.exception.InvalidCardNumberException;
import ua.epam.payments.payments.model.exception.InvalidMoneyException;
import ua.epam.payments.payments.model.exception.OutOfMoneyException;
import ua.epam.payments.payments.model.services.CardService;
import ua.epam.payments.payments.model.services.PaymentService;
import ua.epam.payments.payments.model.util.validation.CardValidation;
import ua.epam.payments.payments.web.Constants;
import ua.epam.payments.payments.web.Path;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "PaymentServlet", value = Path.PAYMENT_PATH)
public class PaymentServlet extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(CardBlock.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CardService cardService = new CardService(new CardDaoImpl());


        User user = (User) req.getSession().getAttribute("user");

        List<Card> cards = cardService.getCardByUserId(user.getId());

        req.setAttribute("cards", cards);


        req.getRequestDispatcher(Path.PAYMENT_JSP).forward(req, resp);
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.debug("PaymentServlet tarted");

        CardService cardService = new CardService(new CardDaoImpl());
        PaymentService paymentService = new PaymentService(new PaymentsDaoImpl(), new CardDaoImpl());
        CardValidation cardValidation = new CardValidation();

        long cardSenderId = Long.parseLong(req.getParameter("cardSenderId"));
        String cardDestinationNumber = req.getParameter("cardDestinationNumber").trim();
        String money = req.getParameter("money").trim();


        if (!cardValidation.isCardNumberValid(cardDestinationNumber)) {
            req.setAttribute(Constants.INVALID_CARD_NUMBER, Constants.INVALID_CARD_NUMBER);
            doGet(req, resp);
            return;
        }

        Card cardSender = cardService.getCardById(cardSenderId);
        Card cardDestination = cardService.getCardByNumber(cardDestinationNumber.replaceAll("[^0-9]+", "").trim());


        try {
            if (!cardValidation.isCardsValid(cardSender, cardDestination)) {
                cardValidation.getErrors().forEach(err -> req.setAttribute(err, err));
                doGet(req, resp);
                return;
            }


            if (req.getParameter("prepare") != null) {
                paymentService.createPreparedPayment(cardSender, cardDestination, money);
                logger.info("Payment prepared");
            } else if (req.getParameter("send") != null) {
                paymentService.makePayment(cardSender, cardDestination, money);
                logger.info("Payment has been made");
            }

        } catch (InvalidCardNumberException e) {
            req.setAttribute(Constants.INVALID_CARD_NUMBER, Constants.INVALID_CARD_NUMBER);
            doGet(req, resp);
            return;
        } catch (InvalidMoneyException e) {
            req.setAttribute(Constants.INVALID_MONEY_AMOUNT, Constants.INVALID_MONEY_AMOUNT);
            doGet(req, resp);
            return;
        } catch (OutOfMoneyException e) {
            req.setAttribute(Constants.OUT_OF_MONEY, Constants.OUT_OF_MONEY);
            doGet(req, resp);
            return;
        }


        resp.sendRedirect(Path.PAYMENTS_PATH);
    }
}
