Meta:
@test skip

Narrative:
Configure new auto reply rich media message for service account

Scenario: Configure new auto reply rich media message for service account
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click auto reply management link
Then click configure new auto reply button
Then input auto reply msg name <autoReplyMsgName>
Then input auto reply msg keyword <autoReplyMsgKewWord>
Then click rich media msg button in auto reply message
Then choose one rich media for auto reply message
!-- verify rich media is chosen
Then click save and close btn to save auto reply msg
!-- Verify no error msg appears
Then verify auto reply message <autoReplyMsgName> is existed

Examples:
|accountName       |autoReplyMsgName                    |autoReplyMsgKewWord|
|OMCWI研发服务号 |AutoTest图文扫码发消息测试_%RAND_NUM%        |Keyword_%RAND_NUM%  |




