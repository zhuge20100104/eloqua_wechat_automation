Meta:
@mobile

Narrative: Verify message receiving after disable

Scenario: Create/Edit new text welcome message for service account
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click service account welcome message link
Then click edit button in welcome message page
Then enable the Welcome Message page
Then select text type
Then clear text configuration
Then input text configuration in welcome message content page: <text>
Then disable the Welcome Message page
Then click save and close btn to save welcome msg

Examples:
|accountName       |text          |
|OMCWI研发服务号 |Auto Testing: disable welcome message|


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
!-- Check welcome message, landing page, update contact information
Then verify if not receive welcome message: <text>


Examples:
|accountName        |follow  |unfollow  |text|
|OMCWI研发服务号  |关注     |取消关注  |Auto Testing: disable welcome message|
