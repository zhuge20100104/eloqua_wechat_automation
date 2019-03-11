Meta:

Narrative:
As a user
I want to edit an existed official account

Scenario: edit an existed official account
Given login Eloqua
When click settings button on home page
When click apps button on platform extensions table on settings page
Then select wechat app in appcloud catalog page
Then click config link in my apps page
Then click <accountName> edit button
Then select openID field <openIDValue>
Then input default campaign ID <campaignIDValue>
Then click authorize button
Then verify qrcode in new page <pageTitle>



Examples:
|accountName          |campaignIDValue |openIDValue                      |pageTitle|
|OMCWI研发订阅号      |12345            |Email Address (C_EmailAddress)  |WeChat Official Account Platform|

