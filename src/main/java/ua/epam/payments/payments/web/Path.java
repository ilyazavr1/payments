package ua.epam.payments.payments.web;

import java.util.Arrays;
import java.util.List;

public class Path {

    public static final List<String> ADMIN_PATHS = Arrays.asList(Path.ADMIN_ALL_USERS_PATH, Path.ADMIN_BLOCK_USER_PATH, Path.ADMIN_UNBLOCK_USER_PATH);
    public static final List<String> CLIENT_PATHS = Arrays.asList(
            Path.CARDS_PATH,
            Path.CARD_TOP_UP_PATH,
            Path.CARD_CREATE_PATH,
            Path.PAYMENT_PATH,
            Path.PAYMENTS_PATH
    );

    //SERVLETS PATHS
    public static final String PROFILE_PATH = "/profile";
    public static final String REGISTRATION_PATH = "/registration";
    public static final String LOGIN_PATH = "/login";
    public static final String LOGOUT_PATH = "/logout";
    public static final String CARDS_PATH = "/cards";
    public static final String CARD_TOP_UP_PATH = "/card/topUp";
    public static final String CARD_CREATE_PATH = "/card/create";
    public static final String PAYMENT_PATH = "/payment";
    public static final String PAYMENTS_PATH = "/payments";
    public static final String PAYMENTS_CONFIRM_PATH = "/confirmPayment";
    public static final String CARD_BLOCK_PATH = "/card/block";
    //admin
    public static final String ADMIN_ALL_USERS_PATH = "/admin/allUsers";
    public static final String ADMIN_BLOCK_USER_PATH = "/admin/blockUser";
    public static final String ADMIN_UNBLOCK_USER_PATH = "/admin/unblockUser";

    //TODO add to security
    public static final String ADMIN_ALL_PAYMENTS_REQUESTS_PATH = "/admin/paymentsRequests";
    public static final String ADMIN_CONFIRM_PAYMENT_PATH = "/admin/paymentsRequests/confirm";
    //

    //JSP
    public static final String PROFILE_JSP = "/WEB-INF/jsp/profile.jsp";
    public static final String REGISTRATION_JSP = "/WEB-INF/jsp/registration.jsp";
    public static final String LOGIN_JSP = "/WEB-INF/jsp/login.jsp";
    public static final String CARDS_JSP = "/WEB-INF/jsp/cards.jsp";
    public static final String CARD_TOP_UP_JSP = "/WEB-INF/jsp/cardTopUp.jsp";
    public static final String CARD_CREATE_JSP = "/WEB-INF/jsp/createCard.jsp";
    public static final String PAYMENT_JSP = "/WEB-INF/jsp/payment.jsp";
    public static final String PAYMENTS_JSP = "/WEB-INF/jsp/payments.jsp";
    public static final String CARD_BLOCK_JSP = "/WEB-INF/jsp/cardBlock.jsp";
    //admin
    public static final String ADMIN_ALL_USERS_JSP = "/WEB-INF/jsp/admin/adminAllUsers.jsp";
    public static final String ADMIN_BLOCK_USER_JSP = "/WEB-INF/jsp/admin/adminBlockUser.jsp";
    public static final String ADMIN_ALL_PAYMENTS_REQUESTS_JSP = "/WEB-INF/jsp/admin/paymentsRequests.jsp";


    public static final String TEST = "/jsp/testLog.jsp";
}
