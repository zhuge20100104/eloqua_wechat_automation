Meta:
@test skip

Narrative:
Send and check broadcase text message to serivce account with all followers
GivenStories: com\oracle\auto\testapp\tests\web\campaigns\stories\Ensure_Service_Account_Followed_WM.story

Scenario: Send and check broadcase text message to serivce account with all followers
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
Then activate the campaign
Then verify if test message is sent successfully

When click the service account  <accountName>
When verify the broadcast message content with <content>

Examples:
|accountName          |content                                                 |campaign                              |
|OMCWI研发服务号    |广播给所有用户的文本消息发自OMCWI研发服务号_%RAND_NUM%  |%RAND_NUM%_服务号广播文本ToAllFollowers_%RAND_NUM%|



