Meta:
@test skip

Narrative:
Used to test add a follower.

Scenario: Check if contact information exists.
Given login Eloqua
When move cursor to audience
And click the contacts button
Then input contact mail @Json{follower.email}
Then the status should be <message>

Examples:
|message|
|No contacts found|

Scenario: Register a new follower.
Given login wechat in mobile phone
When click the service account  <accountName>
Then click the landing page link
When input email address @Json{follower.email}
Then click submit button

Examples:
|accountName|
|OMCWI研发服务号|

Scenario: Verify the contact information is present.
Then input contact mail @Json{follower.email}
Then the status should be <message>

Examples:
|message|
|Displaying 1 of 1 matching contact(s)|

Json: follower
{
	firstName: "%follower.firstName%",
	lastName: "%follower.lastName%",
	email: "auto_%RAND_NUM%@blackhole.oracle.com"
}