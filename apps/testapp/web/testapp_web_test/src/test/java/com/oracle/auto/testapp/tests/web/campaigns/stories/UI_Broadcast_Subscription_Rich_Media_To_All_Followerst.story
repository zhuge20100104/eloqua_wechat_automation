Meta:

Narrative:
Setup messaeg then cheak in browser: campaign to send broadcase rich media message to subscription account

Scenario: Send broadcase rich media message to subscription account
Given login Eloqua
When choose a multiple steps blank campaign
When create a segment member with default segment
When choose wechat message sender in campaign item list
When connect segment members and message sender
Then double click wechat message sender in drawer container
Then click the configure cloud action button
When select weChat official account <accountName>
When select the rich media message type
When select subscription broadcast message type
When check the all followers box
Then click next button
When select rich media content
Then verify if media is selected
Then click submit rich media message
Then verify if successful message appears
Then save and input campaign name: <campaign>

Then double click wechat message sender in drawer container
Then click the configure cloud action button

Then verify if it is weChat official account <accountName>
Then verify if it is subscription broadcast message type
Then verify if it is rich media message type
Then verify if it is to all followers
Then click next button
Then confirm news media is selected
Then close the cloud action configuration page

Examples:
|accountName                 |campaign      |
|OMCWI研发订阅号              |%RAND_NUM%_AutoTestCamp_%RAND_NUM%|


