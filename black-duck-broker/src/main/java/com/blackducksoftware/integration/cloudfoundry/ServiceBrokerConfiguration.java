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
package com.blackducksoftware.integration.cloudfoundry;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.blackducksoftware.integration.cloudfoundry.servicebroker.app.api.HubCredentials;
import com.blackducksoftware.integration.cloudfoundry.servicebroker.app.api.PhoneHomeParameters;
import com.blackducksoftware.integration.cloudfoundry.servicebroker.app.impl.BindingInstanceService;
import com.blackducksoftware.integration.cloudfoundry.servicebroker.app.impl.ServiceInstanceService;
import com.blackducksoftware.integration.cloudfoundry.servicebroker.security.AuthenticationEntryPoint;

/**
 * @author jfisher
 *
 */
@Configuration
public class ServiceBrokerConfiguration {

    @Bean
    public HubCredentials hubCredentials(
            @Value(value = "#{ @environment['HUB_SCHEME'] ?: '0' }") final String scheme,
            @Value(value = "#{ @environment['HUB_HOST'] ?: '0' }") final String host,
            @Value(value = "#{ @environment['HUB_PORT'] ?: -1 }") final int port,
            @Value(value = "#{ @environment['HUB_LOGIN'] ?: '{}' }") final String loginInfo,
            @Value(value = "#{ @environment['HUB_INSECURE'] ?: false }") final boolean insecure) {
        return new HubCredentials(scheme, host, port, loginInfo, insecure);
    }

    @Bean
    public ServiceInstanceService serviceInstanceService() {
        return new ServiceInstanceService();
    }

    @Bean
    public BindingInstanceService bindingInstanceService(ServiceInstanceService serviceInstanceService, HubCredentials hubCredentials, String pluginVersion,
            PhoneHomeParameters phoneHomeParms) {
        return new BindingInstanceService(serviceInstanceService, hubCredentials, pluginVersion, phoneHomeParms);
    }

    @Bean
    public AuthenticationEntryPoint authEntryPoint(@Value(value = "${application.realm}") final String realm) {
        return new AuthenticationEntryPoint(realm);
    }

    @Bean
    public String getPluginVersion(@Value(value = "${plugin.version}") final String pluginVersion) {
        return pluginVersion;
    }

    @Bean
    public PhoneHomeParameters phoneHomeParameters(
            @Value(value = "#{ @environment['INTEGRATION_SOURCE'] ?: '0' }") final String source,
            @Value(value = "#{ @environment['INTEGRATION_VENDOR'] ?: '0' }") final String vendor) {
        return new PhoneHomeParameters(source, vendor);
    }
}
