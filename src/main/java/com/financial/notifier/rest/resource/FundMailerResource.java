package com.financial.notifier.rest.resource;

import com.financial.notifier.model.VanguardFund;
import com.financial.notifier.service.FundMailerService;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Response;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

@Path("/funds")
@ApplicationScoped
public class FundMailerResource {


    private final List<String> recipients;

    private final FundMailerService fundMailerService;

    @Inject
    public FundMailerResource(FundMailerService fundMailerService,
                              @ConfigProperty(name = "quarkus.mailer.approved-recipients") List<String> recipients) {
        this.fundMailerService = fundMailerService;
        this.recipients = recipients;
    }

    @POST
    @Path("mail")
    public Uni<Response> sendFundData() {
        Multi<VanguardFund> funds = Multi.createFrom().items(VanguardFund.values());
        return Multi.createFrom().iterable(recipients)
                .onItem()
                .transformToUniAndMerge(m -> fundMailerService.send(funds, m))
                .toUni()
                .map(v -> Response.noContent().build());

    }
}
