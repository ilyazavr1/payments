package ua.epam.payments.payments.web;

import java.util.Arrays;
import java.util.List;

public class Path {

    public static final List<String> ADMIN_PATHS = Arrays.asList(Path.ADMIN_ALL_USERS_PATH, Path.ADMIN_BLOCK_USER_PATH, Path.ADMIN_UNBLOCK_USER_PATH);

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
    //admin
    public static final String ADMIN_ALL_USERS_PATH = "/admin/allUsers";
    public static final String ADMIN_BLOCK_USER_PATH = "/admin/blockUser";
    public static final String ADMIN_UNBLOCK_USER_PATH = "/admin/unblockUser";


    //JSP
    public static final String PROFILE_JSP = "/jsp/profile.jsp";
    public static final String REGISTRATION_JSP = "/jsp/registration.jsp";
    public static final String LOGIN_JSP = "/jsp/login.jsp";
    public static final String CARDS_JSP = "/jsp/cards.jsp";
    public static final String CARD_TOP_UP_JSP = "/jsp/cardTopUp.jsp";
    public static final String CARD_CREATE_JSP = "/jsp/createCard.jsp";
    public static final String PAYMENT_JSP = "/jsp/payment.jsp";
    public static final String PAYMENTS_JSP = "/jsp/payments.jsp";
    //admin
    public static final String ADMIN_ALL_USERS_JSP = "/jsp/admin/adminAllUsers.jsp";
    public static final String ADMIN_BLOCK_USER_JSP = "/jsp/admin/adminBlockUser.jsp";


    public static final String TEST = "/jsp/testLog.jsp";
}
