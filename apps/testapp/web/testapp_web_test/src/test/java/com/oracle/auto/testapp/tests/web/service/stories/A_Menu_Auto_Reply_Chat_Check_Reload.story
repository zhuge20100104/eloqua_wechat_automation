Meta:
@mobile

Narrative:
Configure new auto reply chat message for service account


Scenario: Create new chat auto reply message
Given login Eloqua
Then choose one account in menu service list: @Json{Message.account}

!-- Configure new auto reply rich media message for service account
Then click auto reply management link
Then click configure new auto reply button
Then input auto reply msg name @Json{Message.name}
Then input auto reply msg keyword @Json{Message.keyword}
Then click chat msg button in auto reply message
Then choose form: <formName> to load and select openID
Then choose <minutes> and input error message: <errorMsg>

Then add Thanks message with text type: <thanksMsg>, Email Address
Then add Submission message with text type: <submissionMsg>, Email Address

!-- Then choose the first rich media for auto reply message
Then click save and close btn to save auto reply msg

Then verify auto reply message @Json{Message.name} is existed
Then click view button to view QR code with auto reply rich media message @Json{Message.name}
Then verify if QR code with auto reply rich media message appears


Then click auto reply rich media message @Json{Message.name} edit button
Then choose form: <newFormName> to load and select openID
Then choose <newMinutes> and input error message: <errorMsg>

Then click save and close btn to save auto reply msg
Then verify auto reply message @Json{Message.name} is existed
Then click view button to view QR code with auto reply rich media message @Json{Message.name}
Then verify if QR code with auto reply rich media message appears
Then download autoreply message qrcode to @Json{File}

!-- timeOut then error message appears
Given login wechat in mobile phone
When click the service account @Json{Message.account}
When type texts @Json{Message.keyword} in the account
Then verify the autoreply message: WeChat_City
Then wait for <newMinutes> minute(s)
Then verify the autoreply message: <errorMsg>

!-- input the email, then thanks message appears
Given login wechat in mobile phone
When click the service account @Json{Message.account}
When type texts @Json{Message.keyword} in the account
Then verify the autoreply message: City
When type texts 北京 in the account
Then verify message after type keyword: Email Address
When type texts <emailAddress> in the account
Then verify message after type keyword: OpenID
When type texts openID1 in the account
Then verify message after type keyword: OpenID
When type texts openID2 in the account
Then wait for one minute(s)
Then verify the autoreply message: <thanksMsg>

Given login Eloqua
When move cursor to audience
Then click the contacts button
Then input contact mail: <emailAddress>
Then check the email: <emailAddress> and openID: <openID>

Examples:
|emailAddress                                   |formName              |newFormName                |newMinutes    |minutes      |openID           |errorMsg          |thanksMsg              |submissionMsg|
|wechat.autotest%RAND_NUM%@blackhole.oracle.com |%eloqua.wechat.form%  |%eloqua.wechat.new.form%  |Three minutes |Two minutes  |%service_open_id% |time out message%RAND_NUM%|thanks complete message%RAND_NUM%| submission message%RAND_NUM%|

Json: Message
{
    account: "OMCWI研发服务号",
    name: "AutoTestChat扫码发消息测试_%RAND_NUM%_%RAND_NUM%",
    title: "AutoTest_服务号文章_%RAND_NUM%",
    author: "作者_%RAND_NUM%",
    keyword: "chat%RAND_NUM%",
    content: "AutoTest_甲骨文消息测试_%RAND_NUM%",
    url: "http://www.bing.com/",
    openedURL: "http://cn.bing.c/"
}

Json: File
{
    name: "autoreply_richmedia_qrcode_%RAND_NUM%",
    path: "%wechat.test.data%"
}