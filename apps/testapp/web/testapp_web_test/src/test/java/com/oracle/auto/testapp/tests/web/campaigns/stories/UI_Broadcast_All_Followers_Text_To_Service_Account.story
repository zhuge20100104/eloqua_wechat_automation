Meta:

Narrative:
Setup messaeg then cheak in browser: campaign to send broadcase text message to serivce account with all followers

Scenario: Send broadcase text message to serivce account to all followers
Given login Eloqua
When choose a multiple steps blank campaign
When create a segment member with default segment
When choose wechat message sender in campaign item list
When connect segment members and message sender
Then double click wechat message sender in drawer container
Then click the configure cloud action button

When select weChat official account <accountName>
And select the text message type
When select broadcast message type
And check the all followers box
Then click next button
When type the wechat message <content>
And click the broadcast submit button
Then save and input campaign name: <campaign>

Then double click wechat message sender in drawer container
Then click the configure cloud action button

Then verify if it is weChat official account <accountName>
Then verify if it is text message type
Then verify if it is broadcast message type
Then verify if it is to all followers
Then click next button
Then verify the text message content: <content>
Then close the cloud action configuration page

Examples:
|accountName        |content                                     |campaign      |
|OMCWI研发服务号    |This is a test message.服务号消息_%RAND_NUM%|%RAND_NUM%_AutoTestCamp_%RAND_NUM%|