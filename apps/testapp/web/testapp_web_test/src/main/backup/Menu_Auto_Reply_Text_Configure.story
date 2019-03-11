Meta:
@test skip

Narrative:
Configure new auto reply text message for service account

Scenario: Configure new auto reply text message for service account
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click auto reply management link
Then click configure new auto reply button
Then click text message button in auto reply message
Then insert <text> configuration for auto reply text message
Then insert a line break for auto reply text message

Examples:
| accountName      | text                    |
|OMCWI研发服务号 |你好！文本-扫码发消息测试！|

Scenario: Input content
Then insert <text> configuration for auto reply text message
Then insert <insertItem> in insert field for auto reply text message
Then insert a line break for auto reply text message

Examples:
|text          |insertItem   |
|邮箱地址：     |Email Address|
|创建时间：     |Date Created|
|修改时间：     |Date Modified|

Scenario: Save and verifiy
Then insert <text> configuration for auto reply text message
Then close insert field for auto reply text message
Then input auto reply msg name <autoReplyMsgName>
Then click save and close btn to save auto reply msg
!-- Verify no error msg appears
Then verify auto reply message <autoReplyMsgName> is existed


Examples:
|autoReplyMsgName                         | text  |
|文本扫码发消息测试_%RAND_NUM%             |结束！|





