/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
package by.pvt.khudnitsky.payments.services.constants;

/**
 * @author khudnitsky
 * @version 1.0
 *
 */
public class ConfigsConstants {

    public static final String DATABASE_SOURCE = "java:/comp/env/jdbc/payments";
    public static final String CONFIGS_SOURCE = "configs";
    //public static final String LOGGER_CONFIG = "log4j";
    public static final String MESSAGES_SOURCE = "messages";

    public static final String INDEX_PAGE_PATH = "path.page.index";
    public static final String REGISTRATION_PAGE_PATH = "path.page.registration";
    public static final String ERROR_PAGE_PATH = "path.page.error";

    public static final String ADMIN_PAGE_PATH = "path.page.admin";
    public static final String ADMIN_SHOW_CLIENTS_PAGE = "path.page.admin.clients";
    public static final String ADMIN_SHOW_OPERATIONS_PAGE = "path.page.admin.operations";
    public static final String ADMIN_UNBLOCK_PAGE = "path.page.admin.unblock";

    public static final String CLIENT_PAGE_PATH = "path.page.client";
    public static final String CLIENT_BALANCE_PAGE_PATH = "path.page.client.balance";
    public static final String CLIENT_PAYMENT_PAGE_PATH = "path.page.client.payment";
    public static final String CLIENT_ADDFUNDS_PAGE_PATH = "path.page.client.addfunds";
    public static final String CLIENT_BLOCK_PAGE_PATH = "path.page.client.block";

    private ConfigsConstants(){}
}
