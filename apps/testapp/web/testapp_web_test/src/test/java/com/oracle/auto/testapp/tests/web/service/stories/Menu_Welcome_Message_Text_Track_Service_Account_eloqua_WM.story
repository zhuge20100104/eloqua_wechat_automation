Meta:
@mobile

Narrative: Create new welcome text message for service account

Scenario: Create/Edit new text welcome message for service account
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click service account welcome message link
Then click edit button in welcome message page
Then enable the Welcome Message page
Then select text type
Then clear text configuration
Then input Link with URL: <URL> and text: <text>
Then click save and close btn to save welcome msg

!-- Refollow an account
Given login wechat in mobile phone
Given ensure account <accountName> is unfollowed
When follow service account by scan QR code

!-- Check welcome message, landing page, update contact information
Then click the landing page link according to content: <text>
Then verify the opened eloqua URL with track option for service account

Examples:
|accountName         |text                                     |url                                           |
|OMCWI研发服务号     |<p>click %RAND_NUM% to sign up.</p>       |%service.account.registration.landing.page%  |

!-- When find the message <text> in oracle account

