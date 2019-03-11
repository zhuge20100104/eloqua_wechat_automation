Meta:

Narrative:
Create a new QR code

Scenario: Create a new QR code: notable, text, disable welcome message

Given login Eloqua
When move cursor to assets
And click the emails button
Then create a design blank email
Then click cloud content button in emails page
Then select one cloud content for email: @Json{QRCodeEmail.cloudContentName}
Then input the email QRCode: @Json{QRCodeEmail.QRCodeName} and select: @Json{QRCodeEmail.accountName}
Then disable email Welcome Message
Then select email text message type
Then click email notable button
Then click email next button
Then input text for QRCode message: @Json{QRCodeEmail.textContent}
Then submit and close the QRCode email box
Then save email with name: @Json{QRCodeEmail.emailName} and subject: @Json{QRCodeEmail.emailSubject}

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


Json: QRCodeEmail
{
    cloudContentName: "%eloqua.campaign.email.cloud.content.name%",
    emailName: "%RAND_NUM%_TextEmail_%RAND_NUM%",
    emailSubject: "notable, text, disable welcome message %RAND_NUM%",
    QRCodeName: "QRCodeName%RAND_NUM%",
    accountName: "OMCWI研发服务号",
    textContent: "auto test text with notable QRCode and disable welcome message"
}