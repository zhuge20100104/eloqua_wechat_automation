Meta:

Narrative:
Create/Edit rich media welcome message for subscription account

Scenario: Create/Edit rich media new welcome message for subscription account
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click subscription account welcome message link
Then click edit button in welcome message page
Then enable the Welcome Message page
Then select rich media type
Then select one rich media item for the welcome message
Then verify if rich media item is selected

!-- Then click save and close btn to save welcome msg


Examples:
|accountName   |
|OMCWI研发订阅号|










