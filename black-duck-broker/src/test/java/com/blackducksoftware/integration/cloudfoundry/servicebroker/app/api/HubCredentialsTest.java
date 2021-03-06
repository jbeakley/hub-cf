/**
 * Copyright (C) 2017 Black Duck Software, Inc.
 * http://www.blackducksoftware.com/
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.blackducksoftware.integration.cloudfoundry.servicebroker.app.api;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.blackducksoftware.integration.cloudfoundry.servicebroker.exception.BlackDuckServiceBrokerException;
import com.blackducksoftware.integration.cloudfoundry.servicebroker.json.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 *
 * @author jfisher
 *
 */
public class HubCredentialsTest {
    private static String TEST_SCHEME = "https";

    private static String TEST_HOST = "test.hubcredentials.org";

    private static int TEST_PORT = 9090;

    private static String TEST_USERNAME = "test_user";

    private static String TEST_PASSWORD = "test_pass";

    private static HubLogin TEST_HUBLOGIN = new HubLogin(TEST_USERNAME, TEST_PASSWORD);

    private static Boolean TEST_ISINSECURE = Boolean.TRUE;

    private String testHubLoginJson;

    @DataProvider(name = "testInvalidHubCredentials")
    public Object[][] createInvalidHubCredentials() {
        return new Object[][] {
                { new Object[] { null, new String(TEST_HOST), new Integer(TEST_PORT), new String(testHubLoginJson), TEST_ISINSECURE } },
                { new Object[] { new String(TEST_SCHEME), null, new Integer(TEST_PORT), new String(testHubLoginJson), TEST_ISINSECURE } },
                { new Object[] { new String(TEST_SCHEME), new String(TEST_HOST), new Integer(TEST_PORT), null, TEST_ISINSECURE } },
                { new Object[] { new String(TEST_SCHEME), new String(TEST_HOST), new Integer(TEST_PORT), new String(testHubLoginJson), null } },
        };
    }

    @BeforeClass
    public void setupData() throws JsonProcessingException {
        testHubLoginJson = JsonUtil.getObjectMapper().writeValueAsString(TEST_HUBLOGIN);
    }

    @Test
    public void testValidHubCredentials() {
        HubCredentials hubCreds = new HubCredentials(TEST_SCHEME, TEST_HOST, TEST_PORT, testHubLoginJson, TEST_ISINSECURE);

        Assert.assertNotNull(hubCreds, "HubCredentials object should not be null");

        Assert.assertEquals(hubCreds.getScheme(), TEST_SCHEME, "HubCredentials scheme incorrect");
        Assert.assertEquals(hubCreds.getHost(), TEST_HOST, "HubCredentials host incorrect");
        Assert.assertEquals(hubCreds.getPort(), TEST_PORT, "HubCredentials port incorrect");
        Assert.assertNotNull(hubCreds.getLoginInfo(), "HubCredentials loginInfo should not be null");
        Assert.assertEquals(hubCreds.getLoginInfo().getUsername(), TEST_USERNAME, "HubCredentials loginInfo.username incorrect");
        Assert.assertEquals(hubCreds.getLoginInfo().getPassword(), TEST_PASSWORD, "HubCredentials loginInfo.password incorrect");
        Assert.assertEquals(hubCreds.isInsecure(), TEST_ISINSECURE.booleanValue(), "HubCredentials insecure incorrect");
    }

    @Test(dataProvider = "testInvalidHubCredentials", expectedExceptions = { NullPointerException.class, BlackDuckServiceBrokerException.class })
    public void testInvalidHubCredentials(Object[] args) {
        HubCredentials hubCreds = new HubCredentials((String) args[0], (String) args[1], (Integer) args[2], (String) args[3], (Boolean) args[4]);

        Assert.assertNull(hubCreds, "Should have thrown Exception during construction");
    }
}
