Meta:
@test skip

Narrative:
Create new message for subscripton account

Scenario: Create new message for subscription account
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click Message Page
Then click new message button
Then click article button to add new article

Examples:
|accountName       |
|OMCWI研发订阅号   |

Scenario: Create articles
Then click article item to edit Article <index>
Then message <title> input firstly
And secondly input message <author>
And input content in frame <content>
And input message content URL <contentURL>
Then select rich media content on the popup cover page

Examples:
|index  | title          |author      |content         |contentURL            |
|0      |AutoTest_订阅号文章一     |作者一      |AutoTest_甲骨文消息测试一   |http://www.baidu.com/ |
|1      |AutoTest_订阅号文章二     |作者二      |AutoTest_甲骨文消息测试二   |http://p01.msqa01.com/ElQWECHATREADMORE |

Scenario: Save and veriry if accout is existed
Then click save button
Then verify message <title> is existed

Examples:
| title      |
|AutoTest_订阅号文章一 |





