Meta:

Narrative:
Create/Edit new welcome message for subscription account

!-- Scenario: Save and close
!-- Then click save and close btn to save welcome msg

Scenario: Create/Edit new welcome message for subscription account
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click subscription account welcome message link
Then click edit button in welcome message page
Then enable the Welcome Message page
Then select text type
Then clear text configuration

Examples:
|accountName      |
|OMCWI研发订阅号|


Scenario: Input text content
Then input text configuration in welcome message content page: <text>



Examples:
|text          |
|Configuration information:|
|你好！${subscriber.nickname}!|
|Welcome to subscriber our account. Please click <a href=\'${url}\'>here</a> to sign up.|
|微信城市： ${subscriber.city}! |
|微信省份：${subscriber.province}! |
|微信国家：${subscriber.country}! |















