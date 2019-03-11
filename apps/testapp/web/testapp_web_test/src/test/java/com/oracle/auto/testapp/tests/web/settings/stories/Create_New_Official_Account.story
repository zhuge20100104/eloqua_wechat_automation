Meta:

Narrative:
As a user
I want to create a new official account

Scenario: create a new offical account
Given login Eloqua
When click settings button on home page
When click apps button on platform extensions table on settings page
Then select wechat app in appcloud catalog page
Then click config link in my apps page
Then click add account link
Then select openID field <openIDValue>
Then input default campaign ID <campaignIDValue>
Then click authorize button
Then verify qrcode in new page <pageTitle>


Examples:
|campaignIDValue |openIDValue                   |pageTitle|
|12345           |Email Address (C_EmailAddress)|WeChat Official Account Platform|

