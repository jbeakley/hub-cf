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

import java.util.Optional;

import javax.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BindResource {

    private Optional<String> appGuid = Optional.empty();

    private Optional<String> route = Optional.empty();

    @JsonCreator
    public BindResource(@JsonProperty(value = "app_guid", required = false) @Nullable String appGuid,
            @JsonProperty(value = "route", required = false) @Nullable String route) {
        this.appGuid = Optional.ofNullable(appGuid);
        this.route = Optional.ofNullable(route);
    }

    /**
     * @return the appGuid
     */
    @JsonProperty(value = "app_guid")
    public Optional<String> getAppGuid() {
        return appGuid;
    }

    /**
     * @param appGuid
     *            the appGuid to set
     */
    public void setAppGuid(Optional<String> appGuid) {
        this.appGuid = appGuid;
    }

    /**
     * @return the route
     */
    @JsonProperty(value = "route")
    public Optional<String> getRoute() {
        return route;
    }

    /**
     * @param route
     *            the route to set
     */
    public void setRoute(Optional<String> route) {
        this.route = route;
    }
}
