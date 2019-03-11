Meta:

Narrative:
Select new Tag in toolkit then check synced status

Scenario: select new Tag in toolkit then check synced status
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click service account toolkit link
Then select a new tag then verify if selected: <tagName>

Given login Eloqua
When choose a multiple steps blank campaign
When choose contact information synchronizer in campaign item list
Then double click contact information in drawer container
Then click the configure cloud action button
When select weChat official account <accountName>
Then in contact information service verify if tag is existed: <tagName>

Given login Eloqua
When choose a multiple steps blank campaign
When choose wechat message sender in campaign item list
Then double click wechat message sender in drawer container
Then click the configure cloud action button
When select weChat official account <accountName>
Then check the tag box
Then in action service verify if the tag existed: <tagName>


Examples:
|accountName       |tagName           |
|OMCWI研发订阅号    |SubAuoTest_%RAND_NUM%|
|OMCWI研发服务号    |SerAuoTest_%RAND_NUM%|

