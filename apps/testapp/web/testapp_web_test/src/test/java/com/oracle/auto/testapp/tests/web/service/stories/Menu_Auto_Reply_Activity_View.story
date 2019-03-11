Meta:

Narrative:
View auto reply activity content object message for service account

Scenario: View auto reply activity content object message for service account
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click auto reply management link
Then click configure new auto reply button
Then input auto reply msg name <autoReplyMsgName>
Then click activity message button in auto reply message
Then add default rule text message sending: <msgType>, <msgMsg>, <textInsertMsg>
Then input auto reply msg name <autoReplyMsgName>
Then click save and close btn to save auto reply msg

Then input message full name to search <autoReplyMsgName>
Then click view button to view QR code with auto reply rich media message <autoReplyMsgName>
Then verify if QR code with auto reply rich media message appears



Examples:
|accountName       |autoReplyMsgName                               |objectItem       |msgMsg    |msgType      |textInsertMsg|
|OMCWI研发服务号 |%RAND_NUM%_Activity扫码发消息测试_%RAND_NUM%  |Contact Object   |text msg  |Text Message |Address 1|



