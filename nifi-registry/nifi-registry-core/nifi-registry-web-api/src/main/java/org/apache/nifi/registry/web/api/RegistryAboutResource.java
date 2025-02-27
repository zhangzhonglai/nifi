/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.nifi.registry.web.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.nifi.registry.about.RegistryAbout;
import org.apache.nifi.registry.event.EventService;
import org.apache.nifi.registry.web.service.ServiceFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/about")
@Api(
    value = "about",
    authorizations = {@Authorization("Authorization")},
    tags = {"Swagger Resource"}
)
@SwaggerDefinition(tags = {
    @Tag(name = "Swagger Resource", description = "Retrieves the version information for this NiFi Registry.")
})
public class RegistryAboutResource extends ApplicationResource {

    @Autowired
    public RegistryAboutResource(
            final ServiceFacade serviceFacade,
            final EventService eventService) {
        super(serviceFacade, eventService);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Get version",
            notes = "Gets the NiFi Registry version.",
            response = RegistryAbout.class
    )
    public Response getVersion() {
        final String implVersion = RegistryAbout.class.getPackage().getImplementationVersion();
        final RegistryAbout version = new RegistryAbout(implVersion);
        return Response.status(Response.Status.OK).entity(version).build();
    }
}
