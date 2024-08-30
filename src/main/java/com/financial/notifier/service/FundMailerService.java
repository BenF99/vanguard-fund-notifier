package com.financial.notifier.service;

import com.financial.notifier.exception.EmailSendingException;
import com.financial.notifier.model.VanguardFund;
import com.financial.notifier.rest.client.VanguardFundService;
import io.quarkus.mailer.Mail;
import io.quarkus.mailer.reactive.ReactiveMailer;
import io.quarkus.qute.Location;
import io.quarkus.qute.Template;
import io.smallrye.mutiny.Multi;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

@ApplicationScoped
@Slf4j
public class FundMailerService {

    private final ReactiveMailer mailer;

    private final Template fundTemplate;
    private final VanguardFundService fundService;

    @Inject
    public FundMailerService(ReactiveMailer mailer,
                             @Location("fundData.html") Template fundTemplate,
                             @RestClient VanguardFundService fundService) {
        this.mailer = mailer;
        this.fundTemplate = fundTemplate;
        this.fundService = fundService;
    }

    public Uni<Void> send(Multi<VanguardFund> funds, String to) {
        return funds.onItem().transform(VanguardFund::getQualifiedName)
                .onItem().transformToUniAndConcatenate(fundService::getFund).collect().asList()
                .onItem().transform(fundsList -> fundTemplate.data("funds", fundsList).render())
                .onItem().transformToUni(html -> {
                    String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
                    String subject = String.format("Vanguard Funds UPDATE | %s", date);
                    return mailer.send(Mail.withHtml(to, subject, html));
                })
                .invoke(() -> log.info("Fund data successfully mailed to {}", to))
                .ifNoItem().after(Duration.ofSeconds(5))
                .failWith(new EmailSendingException("Failed to send email within the timeout period"));

    }
}