Meta:


Narrative:
Delete existed Message - Both Subscripton and Service Account

Scenario: Edit existed message - both for subscription and service account
Given login Eloqua
When choose: <accountName>
Then delete all unused existed messages: <msgTitle>


Examples:
|accountName        |msgTitle|
|OMCWI研发订阅号    |订阅号文章|
|OMCWI研发服务号    |服务号文章|



