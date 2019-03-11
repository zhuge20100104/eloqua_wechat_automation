Meta:
@test skip

Narrative:
Send and check broadcase text message to subscription account

GivenStories: com\oracle\auto\testapp\tests\web\campaigns\stories\Ensure_Subscription_Account_Followed_WM.story

Scenario: Send and check broadcase text message to subscription account
Given login Eloqua
When choose a multiple steps blank campaign
When create a segment member with default segment
When choose wechat message sender in campaign item list
When connect segment members and message sender
Then double click wechat message sender in drawer container
Then click the configure cloud action button

When select weChat official account <accountName>
When select the text message type
When select subscription broadcast message type
When check the all followers box
Then click next button
When type the wechat message <content>
And click the broadcast submit button
Then save and input campaign name: <campaign>
Then activate the campaign
Then verify if test message is sent successfully

Given login wechat in mobile phone
When click the subscription account <accountName>
When verify the broadcast message content with <content>

Examples:
|accountName      |content                                 |campaign      |
|OMCWI研发订阅号  |ToAllFollowers广播文本消息来自OMCWI研发订阅号%RAND_NUM%  |%RAND_NUM%_AutoTestCamp_%RAND_NUM%|
