Meta:

Narrative:
Preview existed Message - Service Account

Scenario: Preview existed message for service account
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click Message Page
Then preview existed service account message
Then verify if QR code appears


Examples:
|accountName        |
|OMCWI研发服务号  |


