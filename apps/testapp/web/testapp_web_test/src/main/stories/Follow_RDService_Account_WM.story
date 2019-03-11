Meta:


Narrative:
test follow and unfollow

Scenario: Unfollow and follow.
Given login wechat in mobile phone
When ensure account <accountName> is followed
When tap service account <accountName> for a while
Then click unfollow button to unfollow the account
Then click the confirm button to unfollow again

!-- Follow an account
When click the top right options button
Then click the scan button
When click more options button
Then click select qrcode from image library button
Then user clicks the all images button
Then click the accounts image library
Then select the qrcode of Service Account
Then click the follow button

!-- Verify if the account is followed
Given login wechat in mobile phone
When click the service account  <accountName>

Examples:
|accountName    |follow|unfollow|
|OMCWI研发服务号   |关注   |取消关注 |

Json: follower
{
	firstName: "%follower.firstName%",
	lastName: "%follower.lastName%",
	email: "stephen_%RAND_NUM%@blackhole.oracle.com"
}