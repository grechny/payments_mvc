package by.pvt.khudnitsky.payments.services.commands.admin;

import by.pvt.khudnitsky.payments.services.constants.ConfigsConstants;
import by.pvt.khudnitsky.payments.services.managers.ConfigurationManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Copyright (c) 2016, Khudnitsky. All rights reserved.
 */
public class GoBackAdminCommandTest {

    @Ignore
    @Before
    public void setUp() throws Exception {

    }

    @Ignore
    @After
    public void tearDown() throws Exception {

    }

    @Ignore
    @Test
    public void testExecuteForAdminPage() throws Exception {
        String expected = ConfigurationManager.INSTANCE.getProperty(ConfigsConstants.ADMIN_PAGE_PATH);

    }

    @Ignore
    @Test
    public void testExecuteForIndexPage() throws Exception {
        String expected = ConfigurationManager.INSTANCE.getProperty(ConfigsConstants.INDEX_PAGE_PATH);

    }
}