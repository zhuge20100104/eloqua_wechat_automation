Meta:

Narrative:
Add new Tag in contact information service, then check if synced to the action service and menu service

Scenario: dd new Tag in contact information service then check synced status
Given login Eloqua
When choose a multiple steps blank campaign
When choose contact information synchronizer in campaign item list
Then double click contact information in drawer container
Then click the configure cloud action button
When select weChat official account <accountName>
Then add a Tag: <tagName>

Then click submit rich media message
Then verify if successful message appears
Then save and input campaign name: <campaign>
Then activate the campaign
Then verify if test message is sent successfully

Given login Eloqua
When choose a multiple steps blank campaign
When choose wechat message sender in campaign item list
Then double click wechat message sender in drawer container
Then click the configure cloud action button

When select weChat official account <accountName>
Then check the tag box
Then verify if the tag existed: <tagName>

Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click service account toolkit link
Then verify if tag is existed: <tagName>


Examples:
|accountName       |campaign                      |tagName           |
|OMCWI研发订阅号    |%RAND_NUM%_AddTag_%RAND_NUM%  |SubAuoTest_%RAND_NUM%|
|OMCWI研发服务号    |%RAND_NUM%_AddTag_%RAND_NUM%  |SerAuoTest_%RAND_NUM%|

