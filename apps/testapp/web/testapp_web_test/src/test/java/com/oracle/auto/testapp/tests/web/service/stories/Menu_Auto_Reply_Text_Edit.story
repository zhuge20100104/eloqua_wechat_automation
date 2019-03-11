Meta:

Narrative:
Edit new auto reply text message for service account

Scenario: Edit new auto reply text message for service account
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click auto reply management link
Then click configure new auto reply button
Then input auto reply msg name <autoReplyMsgName>
Then click text message button in auto reply message
Then insert <text> configuration for auto reply text message
Then insert <insertItem> in insert field for auto reply text message
Then close insert field for auto reply text message
Then click save and close btn to save auto reply msg
Then input message full name to search <autoReplyMsgName>
Then click auto reply rich media message <autoReplyMsgName> edit button
Then input auto reply msg name <newAutoReplyMsgName>
Then input <newText> configuration for auto reply text message
Then close insert field for auto reply text message
Then click save and close btn to save auto reply msg
Then verify auto reply message <newAutoReplyMsgName> is existed

Examples:
|accountName      |autoReplyMsgName                         | text                     |insertItem   |newAutoReplyMsgName              |newText|
|OMCWI研发服务号 |%RAND_NUM%_文本扫码发消息测试_%RAND_NUM%  |你好！文本-扫码发消息测试！|Date Modified|编辑文本扫码发消息测试_%RAND_NUM% |你好！编辑文本-扫码发消息测试！|




