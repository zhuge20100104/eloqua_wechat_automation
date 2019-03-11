Meta:
@skip

Narrative:
Delete existed Message - Both Subscripton and Service Account

Scenario: Edit existed message - both for subscription and service account
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click Message Page
Then delete existed message


Examples:
|accountName        |
|OMCWI研发订阅号    |
|OMCWI研发服务号  |


