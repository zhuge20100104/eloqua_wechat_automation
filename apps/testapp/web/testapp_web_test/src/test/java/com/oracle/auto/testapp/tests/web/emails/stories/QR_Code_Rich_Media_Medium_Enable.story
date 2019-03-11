Meta:

Narrative:
Create a new QR code

Scenario: Create a new QR code: medium, rich media message, enable welcome message

!-- Create Message
Given login Eloqua
Then choose one account in menu service list: @Json{QRCodeEmail.accountName}
Then click Message Page
Then click new message button
Then message @Json{MessageArtical.title} input firstly
And secondly input message @Json{MessageArtical.author}
And input content in frame @Json{MessageArtical.content}
And input message content URL @Json{MessageArtical.contentURL}
And select @Json{MessageArtical.identifier} item
Then select rich media content on the popup cover page
Then click save button
Then verify message @Json{MessageArtical.title} is existed

!-- Create email
Given login Eloqua
When move cursor to assets
And click the emails button
Then create a design blank email
Then click cloud content button in emails page
Then select one cloud content for email: @Json{QRCodeEmail.cloudContentName}
Then input the email QRCode: @Json{QRCodeEmail.QRCodeName} and select: @Json{QRCodeEmail.accountName}
Then enable email Welcome Message
Then select email rich media message type
Then click email medium button
Then click email next button
Then select rich media message with title: @Json{MessageArtical.title}
Then submit and close the QRCode email box
Then save email with name: @Json{QRCodeEmail.emailName} and subject: @Json{QRCodeEmail.emailSubject}

!-- Send email
When choose a multiple steps blank campaign
When create a segment member with default segment
When double click email btn in campaign item list
When connect segment members and message sender
Then double click email in drawer container
Then choose email in drawer container: @Json{QRCodeEmail.emailName}
When click save button to save the configuration
Then input the campaign name <campaign> and save
Then activate the campaign
Then verify if email is sent successfully

Examples:
|campaign                        |
|%RAND_NUM%_SendEmail_%RAND_NUM% |

Json: MessageArtical
{
    title: "AutoTestEmailMessage_%RAND_NUM%",
    author: "作者一",
    content: "AutoTest_甲骨文消息测试一",
    contentURL: "%service.account.JS.landing.page%",
    identifier: "None"
}

Json: QRCodeEmail
{
    cloudContentName: "%eloqua.campaign.email.cloud.content.name%",
    emailName: "%RAND_NUM%_RichMediaEmail_%RAND_NUM%",
    emailSubject: "medium, rich media, enable welcome message %RAND_NUM%",
    QRCodeName: "QRCodeName%RAND_NUM%",
    accountName: "OMCWI研发服务号"
}