Meta:

Narrative:
Edit auto reply rich media message for service account

Scenario: Eidt auto reply rich media message for service account
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
Then click auto reply rich media message <autoReplyMsgName> edit button
Then input auto reply msg name <newautoReplyMsgName>
Then choose one rich media for auto reply message
Then click save and close btn to save auto reply msg
Then verify auto reply message <newautoReplyMsgName> is existed



Examples:
| accountName        |autoReplyMsgName                                        |autoReplyMsgKewWord |newautoReplyMsgName|
|OMCWI研发服务号  |%RAND_NUM%_AutoTest图文扫码发消息测试_%RAND_NUM%        |%RAND_NUM%_Keyword_%RAND_NUM%  |%RAND_NUM%_编辑AutoTest图文扫码发消息测试_%RAND_NUM% |



