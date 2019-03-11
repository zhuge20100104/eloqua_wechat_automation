Meta:

Narrative: Create complicated text and verify after saving.

Scenario: Create/Edit new text welcome message for service account
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click service account welcome message link
Then click edit button in welcome message page
Then enable the Welcome Message page
Then select text type
Then clear text configuration
Then input text configuration in welcome message content page: <text>
Then click save and close btn to save welcome msg
Then the welcome message is <text>

Examples:
|accountName    |text               |
|OMCWI研发服务号 |SampleWelcomeMessage~!@#%^&*()_+/>:"}?|