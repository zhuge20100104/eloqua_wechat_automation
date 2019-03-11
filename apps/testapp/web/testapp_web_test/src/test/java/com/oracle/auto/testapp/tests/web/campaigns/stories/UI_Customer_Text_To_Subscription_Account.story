Meta:

Narrative:
Setup messaeg then cheak in browser: campaign to send customer text message to subscription account

Scenario: Send customer text message to subscription account
Given login Eloqua
When choose a multiple steps blank campaign
When create a segment member with default segment
When choose wechat message sender in campaign item list
When connect segment members and message sender
Then double click wechat message sender in drawer container
Then click the configure cloud action button
When select weChat official account <accountName>
And select the text message type
When select subscription customer message type
Then click next button
When type the wechat message <content1>
Then select insert message <insertMessage>
When type the wechat message <content2>
And click the customer submit button
Then save and input campaign name: <campaign>

Then double click wechat message sender in drawer container
Then click the configure cloud action button

Then verify if it is weChat official account <accountName>
Then verify if it is subscription customer message type
Then verify if it is text message type
Then click next button
Then verify text message contents: <content1>, <insertMessage>, <content2>
Then close the cloud action configuration page

Examples:
|accountName       |content1                 |insertMessage         |content2                     |campaign      |
|OMCWI研发订阅号    |测试号文本消息_%RAND_NUM%|C_FirstAndLastName    |For subscription text message|%RAND_NUM%_AutoTestCamp_%RAND_NUM%|

