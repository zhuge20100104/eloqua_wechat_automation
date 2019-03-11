Meta:

Narrative:
Test access to defined users function in settings page

Scenario: test access to defined users function in settings page
Given login Eloqua
When go to my apps page in settings configuration
Then click <accountName> edit button
Then click advanced user link
Then select the login user
Then verify if the user is the login user
Then save all settings
Then close WeChat App Configuration page
Then click eloqua home logo

When go to my apps page in settings configuration
Then click <accountName> edit button
Then click advanced user link
Then verify if the user is the login user
Then close settings page
Then close WeChat App Configuration page
Then click eloqua home logo

Then choose one account in menu service list: <accountName>
Then close menu service page
When go to my apps page in settings configuration
Then click <accountName> edit button
Then click advanced user link
Then select one no-login advanced user
Then verify if the user is the no-login advanced user
Then save all settings
Then close WeChat App Configuration page
Then click eloqua home logo

When go to my apps page in settings configuration
Then click <accountName> edit button
Then click advanced user link
Then verify if the user is the no-login advanced user
Then close settings page
Then close WeChat App Configuration page
Then click eloqua home logo


When click the cloud app button in right panel
Then click weChat msg button
Then verify account <accountName> is not existed
Then close menu service page


Examples:
|accountName|
|OMCWI研发服务号 |
|OMCWI研发订阅号|




