package com.pahanaedu.util;

public final class Constants {

    // Private constructor to prevent instantiation
    private Constants() {
        throw new AssertionError("Utility class - cannot be instantiated");
    }

    // User roles
    public static final String ROLE_ADMIN = "admin";
    public static final String ROLE_STAFF = "staff";

    // JSP paths
    public static final String LOGIN_JSP = "/WEB-INF/views/login.jsp";
    public static final String DASHBOARD_JSP = "/WEB-INF/views/dashboard.jsp";
    public static final String MANAGE_ITEMS_JSP = "/WEB-INF/views/manageItems.jsp";
    public static final String ADD_CUSTOMER_JSP = "/WEB-INF/views/addCustomer.jsp";
    public static final String EDIT_CUSTOMER_JSP = "/WEB-INF/views/editCustomer.jsp";
    public static final String VIEW_CUSTOMER_JSP = "/WEB-INF/views/viewCustomer.jsp";
    public static final String BILLING_JSP = "/WEB-INF/views/billing.jsp";
    public static final String HELP_JSP = "/WEB-INF/views/help.jsp";

    // MongoDB collection names
    public static final String USERS_COLLECTION = "users";
    public static final String CUSTOMERS_COLLECTION = "customers";
    public static final String ITEMS_COLLECTION = "items";
}