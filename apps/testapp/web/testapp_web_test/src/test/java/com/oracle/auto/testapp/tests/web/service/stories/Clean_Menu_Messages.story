Meta:


Narrative:
Delete existed Message - Both Subscripton and Service Account

Scenario: Edit existed message - both for subscription and service account
Given login Eloqua
When choose: <accountName>
Then click Message Page
Then delete all unused existed messages: <msgTitle>


Examples:
|accountName        |msgTitle|
|OMCWI研发服务号    |AutoTest|
|OMCWI研发服务号    |Broad|
|OMCWI研发服务号    |CheckEidtIcon|
|OMCWI研发服务号    |Menu_Message|
|OMCWI研发订阅号    |Menu_Message|
|OMCWI研发订阅号    |AutoTest|



