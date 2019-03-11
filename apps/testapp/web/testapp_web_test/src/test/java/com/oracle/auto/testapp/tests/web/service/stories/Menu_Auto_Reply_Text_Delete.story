Meta:

Narrative:
Delete new auto reply text message for service account

Scenario: Delete new auto reply text message for service account
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click auto reply management link
Then click configure new auto reply button
Then input auto reply msg name <autoReplyMsgName>
Then click text message button in auto reply message
Then insert <text> configuration for auto reply text message
Then insert a line break for auto reply text message
Then insert <insertItem> in insert field for auto reply text message
Then close insert field for auto reply text message
Then click save and close btn to save auto reply msg

Then input message full name to search <autoReplyMsgName>
Then click button to delete auto reply rich media message <autoReplyMsgName>
Then confirm delete auto reply rich media message
Then verify auto reply message <autoReplyMsgName> is not existed

Examples:
| accountName      |autoReplyMsgName                        | text                     |insertItem   |
|OMCWI研发服务号 |%RAND_NUM%_文本扫码发消息测试_%RAND_NUM%  |你好！文本-扫码发消息测试！|Date Modified|





