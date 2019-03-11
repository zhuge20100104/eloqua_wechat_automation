Meta:

Narrative:
Disable the welcome text/rich media message page for subscription account

Scenario: Disable the welcome text/rich media message page for subscription account
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click subscription account welcome message link
Then click edit button in welcome message page
Then disable the Welcome Message page
Then verify the text and rich media button is disappear

!-- Then click save and close btn to save welcome msg


Examples:
|accountName    |
|OMCWI研发订阅号|











