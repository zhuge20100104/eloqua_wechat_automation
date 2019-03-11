Meta:

Narrative:
Setup messaeg then cheak in browser: campaign to send customer text message to serivce account

Scenario: Send customer text message to serivce account
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
When type the wechat message <content>
Then select insert message <insertMessage>
And click the customer submit button
Then save and input campaign name: <campaign>

Then double click wechat message sender in drawer container
Then click the configure cloud action button

Then verify if it is weChat official account <accountName>
Then verify if it is customer message type
Then verify if it is text message type
Then click next button
Then verify text message content: <content>, <insertMessage>
Then close the cloud action configuration page

Examples:
|accountName              |content                                   |insertMessage          | campaign      |
|OMCWI研发服务号         |文本服务号消息_%RAND_NUM%|C_Address1|%RAND_NUM%_AutoTestCamp_%RAND_NUM%|

