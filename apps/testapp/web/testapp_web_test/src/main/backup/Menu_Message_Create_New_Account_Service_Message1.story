Meta:
@test skip

Narrative:
Create new message for service account

Scenario: Create new message for service account
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click Message Page
Then click new message button
Then message <title> input firstly
And secondly input message <author>
And input content in frame <content>
And input message content URL <contentURL>
And select <identifier> item
Then select rich media content on the popup cover page

Examples:
|accountName         |title                               |author      |content         |contentURL            |identifier|
|OMCWI研发服务号   |AutoTest_服务号文章一     |作者一      |AutoTest_甲骨文消息测试一   |http://www.baidu.com/ |None|

Scenario: Create articles
Then click article button to add new article
Then click article item to edit Article <index>
Then message <title> input firstly
And secondly input message <author>
And input content in frame <content>
And input message content URL <contentURL>
And select <identifier> item
Then select rich media content on the popup cover page

Examples:
|index  | title                              |author      |content         |contentURL            |identifier|
|1      |AutoTest_服务号文章二     |作者二      |AutoTest_甲骨文消息测试二   |http://www.baidu.com/ |Eloqua ID      |
|2      |AutoTest_服务号文章三     |作者三      |AutoTest_甲骨文消息测试三   |http://www.baidu.com/ |WeChat ID      |
|3      |AutoTest_服务号文章四     |作者四      |AutoTest_甲骨文消息测试四   |http://www.baidu.com/ |WeChat Profile |

Scenario: Save and veriry if accout is existed
Then click save button
Then verify message <title> is existed

Examples:
| title      |
|AutoTest_服务号文章一 |





