Meta:
@mobile

Narrative:
Configure new auto reply rich media message for service account

Scenario: Create new message for service account
Given login Eloqua
When click the cloud app button in right panel
Then click weChat msg button
Then select account @Json{Message.account}
Then click Message Page
Then click new message button
Then message @Json{Message.title} input firstly
And secondly input message @Json{Message.author}
And input content in frame @Json{Message.content}
And input message content URL @Json{Message.url}
!-- And select <identifier> item
Then select rich media content on the popup cover page
Then click save button
Then verify message @Json{Message.title} is existed

!-- Configure new auto reply rich media message for service account
Then click auto reply management link
Then click configure new auto reply button
Then input auto reply msg name @Json{Message.name}
Then input auto reply msg keyword @Json{Message.keyword}
Then click rich media msg button in auto reply message
Then choose the first rich media for auto reply message
Then click save and close btn to save auto reply msg
Then verify auto reply message @Json{Message.name} is existed
Then click view button to view QR code with auto reply rich media message @Json{Message.name}
Then verify if QR code with auto reply rich media message appears
Then download autoreply message qrcode to @Json{File}
!-- login wechat to verify the auto reply message
Given login wechat in mobile phone
When ensure account <accountName> is followed
When push file @Json{File} to the phone
Then scan the first QRCode picture
Then check rich media message @Json{Message.title}
Then click the message @Json{Message.title}
Then verify opened URL with @Json{Message.openedURL} by copy link
!-- Then verify if the opened address @Json{Message.openedURL} is correct
Then closed the opened web site
When input keyword @Json{Message.keyword} in the account
Then check rich media message @Json{Message.title}

Examples:
|accountName         |autoReplyMsgName                                 |autoReplyMsgKewWord             |title                            |contentURL          |
|OMCWI研发服务号  |AutoTest图文扫码发消息测试_%RAND_NUM%_%RAND_NUM% |Keyword_%RAND_NUM%_%RAND_NUM%   |AutoTest_服务号文章_%RAND_NUM%   |http://cn.bing.com/ |

Json: Message
{
    account: "OMCWI研发服务号",
    name: "RichMediaMessage_%RAND_NUM%_%RAND_NUM%",
    title: "AutoTest_服务号文章_%RAND_NUM%",
    author: "作者_%RAND_NUM%",
    keyword: "keyword%RAND_NUM%",
    content: "AutoTest_甲骨文消息测试_%RAND_NUM%",
    url: "http://www.bing.com/",
    openedURL: "http://cn.bing.com/"
}

Json: File
{
    name: "autoreply_richmedia_qrcode_%RAND_NUM%",
    path: "%wechat.test.data%"
}