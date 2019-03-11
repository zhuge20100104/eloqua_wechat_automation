Meta:
@mobile

Narrative:
Check customer text message to serivce account

GivenStories: com\oracle\auto\testapp\tests\web\campaigns\stories\Ensure_Service_Account_Followed_WM.story

Scenario: Send and check customer text message to serivce account
Given login Eloqua
When choose a multiple steps blank campaign
When create a segment member with default segment
When choose wechat message sender in campaign item list
When connect segment members and message sender
Then double click wechat message sender in drawer container
Then click the configure cloud action button
When select weChat official account <accountName>
And select the text message type
When select customer message type
Then click next button
When type the wechat message <content1>
Then select insert message <insertMessage>
When type the wechat message <content2>
And click the customer submit button
Then save and input campaign name: <campaign>
Then activate the campaign
Then verify if test message is sent successfully


Given login wechat in mobile phone
When click the service account  <accountName>
When verify the broadcast message contains <content1>

!-- When verify the message content with <content1>: <insertValue>, <content2>

Examples:
|accountName        |content1                                       |insertMessage     |content2                  |campaign                              |
|OMCWI研发服务号    |Customer文本消息发自OMCWI研发服务号%RAND_NUM%   |C_EmailAddress     |服务号文本测试消息         |%RAND_NUM%_服务号文本Customer消息_%RAND_NUM%|

