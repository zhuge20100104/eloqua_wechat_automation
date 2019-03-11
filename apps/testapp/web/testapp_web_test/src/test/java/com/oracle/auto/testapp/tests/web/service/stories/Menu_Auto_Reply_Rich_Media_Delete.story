Meta:

Narrative:
Delete auto reply rich media message for service account

Scenario: Delete auto reply rich media message for service account
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click auto reply management link

Then click configure new auto reply button
Then input auto reply msg name <autoReplyMsgName>
Then input auto reply msg keyword <autoReplyMsgKewWord>
Then click rich media msg button in auto reply message
Then choose one rich media for auto reply message
Then click save and close btn to save auto reply msg

Then input message full name to search <autoReplyMsgName>
Then click button to delete auto reply rich media message <autoReplyMsgName>
Then confirm delete auto reply rich media message
Then verify auto reply message <autoReplyMsgName> is not existed



Examples:
| accountName       |autoReplyMsgName                                         |autoReplyMsgKewWord |
|OMCWI研发服务号 |%RAND_NUM%_AutoTest图文扫码发消息测试_%RAND_NUM%        |%RAND_NUM%_Keyword_%RAND_NUM%  |


