Meta:

Narrative:
Test access to All users function in settings page

GivenStories: com/oracle/auto/testapp/tests/web/settings/stories/Test_Access_To_Defined_Users_Function.story

Scenario: test access to All users function in settings page
Given login Eloqua
When go to my apps page in settings configuration
Then click <accountName> edit button
Then click advanced user link
Then choose access to all users in advanced setting
Then verify if the advanced user is All
Then save all settings
Then close WeChat App Configuration page
Then click eloqua home logo

When go to my apps page in settings configuration
Then click <accountName> edit button
Then click advanced user link
Then verify if the advanced user is All
Then close settings page
Then close WeChat App Configuration page
Then click eloqua home logo


Then choose one account in menu service list: <accountName>
Then close menu service page

Examples:
|accountName|
|OMCWI研发服务号 |
|OMCWI研发订阅号|



