Meta:
@test skip

Narrative: Create new welcome text message for service account

Scenario: Create/Edit new text welcome message for service account
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click service account welcome message link
Then click edit button in welcome message page
Then enable the Welcome Message page
Then select text type
Then clear text configuration

Examples:
|accountName   |
|OMCWI研发服务号 |

Scenario: Input wecome message content
Then input text configuration in welcome message content page: <text>

Examples:
|text          |
|click <a href=\'${%service.account.JS.landing.page%}\'>here</a> to sign up.|

Scenario: Save and close
Then click save and close btn to save welcome msg

Scenario: Unfollow and follow.
Given login wechat in mobile phone
Given ensure account <accountName> is unfollowed

!-- Follow an account
When follow service account by scan QR code

!-- Check welcome message, landing page, update contact information
Then click the landing page link
Then verify the opened eloqua URL with track option
When input first name @Json{follower.firstName}
And input email address @Json{follower.email}
Then click submit button

Examples:
|accountName        |
|OMCWI研发服务号  |

Scenario: Check if contact information exists.
Given login Eloqua
When move cursor to audience
Then click the contacts button
Then input contact mail: @Json{follower.email}
Then the fist name should be @Json{follower.firstName}

Examples:
|accountName|
|OMCWI研发服务号|

Json: follower
{
	firstName: "%follower.firstName%_%RAND_NUM%",
	lastName: "%follower.lastName%",
	email: "stephen.zhou@oracle.com"
}