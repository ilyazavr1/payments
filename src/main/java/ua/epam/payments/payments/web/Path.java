package ua.epam.payments.payments.web;

import java.util.Arrays;
import java.util.List;

public class Path {

    public static final List<String> ADMIN_PATHS = Arrays.asList(
            Path.ADMIN_ALL_USERS_PATH,
            Path.ADMIN_BLOCK_USER_PATH,
            Path.ADMIN_UNBLOCK_USER_PATH,
            Path.ADMIN_UNBLOCK_USERS_CARDS_REQUESTS_PATH,
            Path.ADMIN_UNBLOCK_CARD_PATH,
            Path.ADMIN_CARD_BLOCK_PATH,
            Path.ADMIN_USER_CARDS_PATH
    );
    public static final List<String> CLIENT_PATHS = Arrays.asList(
            Path.CARDS_PATH,
            Path.CARD_TOP_UP_PATH,
            Path.CARD_CREATE_PATH,
            Path.PAYMENT_PATH,
            Path.PAYMENTS_PATH,
            Path.PAYMENTS_CONFIRM_PATH,
            Path.CARD_BLOCK_PATH,
            Path.CARD_UNBLOCK_REQUEST_PATH,
            Path.CARD_CONFIRM_TOP_UP_PATH
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
    public static final String CARD_UNBLOCK_REQUEST_PATH = "/card/unblockRequest";
    public static final String CARD_CONFIRM_TOP_UP_PATH = "/card/confirmTopUp";
    //admin
    public static final String ADMIN_ALL_USERS_PATH = "/admin/allUsers";
    public static final String ADMIN_BLOCK_USER_PATH = "/admin/blockUser";
    public static final String ADMIN_UNBLOCK_USER_PATH = "/admin/unblockUser";

    public static final String ADMIN_UNBLOCK_USERS_CARDS_REQUESTS_PATH = "/admin/cards/requests";
    public static final String ADMIN_UNBLOCK_CARD_PATH = "/admin/card/unblock";
    public static final String ADMIN_CARD_BLOCK_PATH = "/admin/card/block";
    public static final String ADMIN_USER_CARDS_PATH = "/admin/user/cards";


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
    public static final String ADMIN_UNBLOCK_CARDS_REQUEST_JSP = "/WEB-INF/jsp/admin/adminCardsUnblock.jsp";
    public static final String ADMIN_BLOCK_USER_CARD_JSP = "/WEB-INF/jsp/admin/adminCardBlock.jsp";

    public static final String ADMIN_USER_CARDS_JSP = "/WEB-INF/jsp/admin/adminUserCards.jsp";




}
