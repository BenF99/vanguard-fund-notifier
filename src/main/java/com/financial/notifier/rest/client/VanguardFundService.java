package com.financial.notifier.rest.client;

import com.financial.notifier.model.Fund;
import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/funds")
@RegisterRestClient(configKey="funds-api")
public interface VanguardFundService {

    @GET
    @Path("/{name}")
    Uni<Fund> getFund(@PathParam("name") String fundName);
}
