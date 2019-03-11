Meta:

Narrative:
Add duplicated tag and track error message

Scenario: add duplicated tag and track error message
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click service account toolkit link
Then add duplicated tag and track error message


Examples:
|accountName       |
|OMCWI研发订阅号    |
|OMCWI研发服务号  |

