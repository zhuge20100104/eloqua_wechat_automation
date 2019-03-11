Meta:
@test skip

Narrative:
Used to test add a follower.

Scenario: register a new follower
Given test login wechat in mobile phone
When enter the oracle public account
Then swipe to the top of the screen
Then click the landing page link
When input first name <firstname>
And input last name <lastname>
And input email address <email>
Then click submit button

Examples:
|firstname|lastname|email                   |
|Stephen1 |Zhou1   |stephen.zhou1@oracle.com|