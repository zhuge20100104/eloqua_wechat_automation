Meta:

Narrative:
Add new tag to followers then remove tag for the followers

Scenario: add new tag to followers then remove tag for the followers
Given login Eloqua
When choose a multiple steps blank campaign
When create a segment member with default segment
When choose contact information synchronizer in campaign item list
Then connect segment members and contact information sync
Then double click contact information in drawer container
Then click the configure cloud action button
When select weChat official account <accountName>
Then add a Tag: <tagName> in contact information service
Then click submit rich media message
Then verify if successful message appears
Then save and input campaign name: <campaign>
Then activate the campaign
Then verify if test message is sent successfully

Given login Eloqua
When choose a multiple steps blank campaign
When create a segment member with default segment
When choose contact information synchronizer in campaign item list
Then connect segment members and contact information sync
Then double click contact information in drawer container
Then click the configure cloud action button
When select weChat official account <accountName>
Then select the removed Tag: <tagName> in contact information service
Then click submit rich media message
Then verify if successful message appears
Then save and input campaign name: <campaign>
Then activate the campaign
Then verify if test message is sent successfully

Examples:
|accountName       |campaign                      |tagName           |
|OMCWI研发订阅号    |%RAND_NUM%_AddTag_%RAND_NUM%  |SubAuoTestReomoved_%RAND_NUM%|
|OMCWI研发服务号    |%RAND_NUM%_AddTag_%RAND_NUM%  |SerAuoTestReomoved_%RAND_NUM%|

