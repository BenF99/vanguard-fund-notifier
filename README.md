# Fund Notifier

**Fund Notifier** is a Java application developed with the Quarkus framework. It is designed to send daily email summaries for Vanguard equity funds. Additionally, it provides an HTTP endpoint to manually trigger email notifications.

## Features

- **Daily Email Notifications**: Automatically fetches data from Vanguard equity funds and sends daily updates via email.
- **Manual Email Trigger**: Exposes an endpoint to trigger email notifications on demand.

## Endpoint

- **Trigger Email Manually**:
    - **URL**: `http://localhost:8080/funds/mail`
    - **Method**: POST
    - **Description**: Manually triggers the email notification.

## Configuration

### Email Provider Configuration

**Zoho Mail** is the default email provider used for sending notifications. You can find more information and sign up at [Zoho Mail](https://www.zoho.com/mail/).

If you prefer to use a different email provider, you can configure it by updating the `application.properties` file with the appropriate SMTP server settings.

### Environment Variables

The application requires the following environment variables to be set for email configuration:

- `QUARKUS_MAILER_APPROVED_RECIPIENTS`: List of recipients for email notifications.
- `QUARKUS_MAILER_FROM`: The email address used as the sender.
- `QUARKUS_MAILER_PASSWORD`: Password for the zoho email account.
- `QUARKUS_MAILER_USERNAME`: Username for the zoho email account.

### Fund Selection

The funds included in the email summary are determined by the `ENUMS` defined in the application. The current `ENUMS` are:

```java
@Getter
public enum VanguardFund {

    FTSE_GLOBAL("vanguard-ftse-global-all-cap-index-fund-gbp-acc"),
    SP_500("vanguard-s-and-p-500-ucits-etf-usd-accumulating");

    private final String qualifiedName;

    VanguardFund(String qualifiedName) {
        this.qualifiedName = qualifiedName;
    }
}
```

## Setup

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-username/fund-notifier.git

2. **Navigate to the Project Directory**:
   ```bash
   cd fund-notifier

3. **Run the Application**:
   ```bash
   quarkus:dev