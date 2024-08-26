package com.financial.notifier.scheduler;

import com.financial.notifier.model.VanguardFund;
import com.financial.notifier.service.FundMailerService;
import io.quarkus.scheduler.Scheduled;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.util.List;

@ApplicationScoped
public class FundsMailScheduler {

    private final List<String> recipients;

    private final FundMailerService fundMailerService;

    @Inject
    public FundsMailScheduler(FundMailerService fundMailerService,
                              @ConfigProperty(name = "quarkus.mailer.approved-recipients") List<String> recipients) {
        this.fundMailerService = fundMailerService;
        this.recipients = recipients;
    }

    @Scheduled(cron = "0 0 13 * * ?")
    public Uni<Void> sendFundData() {
        Multi<VanguardFund> funds = Multi.createFrom().items(VanguardFund.values());
        return Multi.createFrom().iterable(recipients)
                .onItem()
                .transformToUniAndMerge(m -> fundMailerService.send(funds, m))
                .toUni();

    }

}
