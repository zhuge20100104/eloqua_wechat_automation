Meta:


Narrative:
test follow and unfollow

Scenario: Unfollow and follow.
Given login wechat in mobile phone
Given ensure account <accountName> is unfollowed

!-- Follow an account
When click the top right options button
Then click the scan button
When click more options button
Then click select qrcode from image library button
Then user clicks the all images button
Then click the accounts image library
Then select the qrcode of Subscription Account
Then click the follow button

!-- Verify if the account is followed
Given login wechat in mobile phone
When click the service account  <account>
And click the oradc account <subscription>

Examples:
|account |subscription  |follow|unfollow|
|订阅号   |OMCWI研发订阅号 |关注  |取消关注 |

Json: follower
{
	firstName: "%follower.firstName%",
	lastName: "%follower.lastName%",
	email: "stephen_%RAND_NUM%@blackhole.oracle.com"
}