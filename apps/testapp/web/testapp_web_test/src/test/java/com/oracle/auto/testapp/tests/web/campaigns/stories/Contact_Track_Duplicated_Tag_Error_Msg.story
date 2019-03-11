Meta:

Narrative:
Add duplicated Tag in contact information service then track error messages

Scenario: add duplicated Tag in contact information service then track error messages
Given login Eloqua
When choose a multiple steps blank campaign
When choose contact information synchronizer in campaign item list
Then double click contact information in drawer container
Then click the configure cloud action button
When select weChat official account <accountName>
Then add duplicated tag in contact information service
Then track duplicated tag error message in contact information service



Examples:
|accountName       |
|OMCWI研发订阅号    |
|OMCWI研发服务号    |

