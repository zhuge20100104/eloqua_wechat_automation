Meta:
@mobile

Narrative:
Configure new auto reply text message for service account

Scenario: Configure new auto reply text message for service account
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click auto reply management link
Then click configure new auto reply button
Then click text message button in auto reply message
Then insert <text> configuration for auto reply text message
Then insert <insertItem> in insert field for auto reply text message
Then close insert field for auto reply text message
Then input auto reply msg name <autoReplyMsgName>
Then input auto reply keyword <keyword>
Then click save and close btn to save auto reply msg
Then verify auto reply message <autoReplyMsgName> is existed
Then click view button to view QR code with auto reply rich media message <autoReplyMsgName>
Then verify if QR code with auto reply rich media message appears
Then download autoreply message qrcode to @Json{File}

!-- login wechat to verify the auto reply message
Given login wechat in mobile phone
When push file @Json{File} to the phone
Then scan the first QRCode picture
Then verify the autoreply message: <text>
When type texts <keyword> in the account
Then verify message after type keyword: <text>

Examples:
|accountName       |insertItem         |text                                  |autoReplyMsgName                |keyword                    |
|OMCWI研发服务号   |Email Address      | 你好!文本-扫码发消息测试!%RAND_NUM%  |文本扫码发消息测试_%RAND_NUM%   |autoreply_text_%RAND_NUM%  |

Json: File
{
    name: "autoreply_text_qrcode_%RAND_NUM%",
    path: "%wechat.test.data%"
}