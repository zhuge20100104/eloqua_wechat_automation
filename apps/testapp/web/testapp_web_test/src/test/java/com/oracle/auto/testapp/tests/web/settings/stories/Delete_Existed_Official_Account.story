Meta:

Narrative:
As a user
I want to delete an existed official account

Scenario: delete an existed official account
Given login Eloqua
When click settings button on home page
When click apps button on platform extensions table on settings page
Then select wechat app in appcloud catalog page
Then click config link in my apps page
Then click <accountName> delete button
!-- Then confirm delete action
Then Verify if the alert warning message appears

Examples:
|accountName|
|OMCWI研发订阅号|

