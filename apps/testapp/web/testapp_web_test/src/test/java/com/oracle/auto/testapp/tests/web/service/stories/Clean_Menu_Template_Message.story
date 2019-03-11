Meta:


Narrative:
Delete automation template Messages

Scenario: Delete automation template Messages
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click template message link

Then clean automation template messages: <templateMsgName>


Examples:
|accountName     |templateMsgName|
|OMCWI研发服务号  |AutoTest|

