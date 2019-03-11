Meta:

Narrative:
Verify welcome message

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
|accountName         |title           |author      |content         |contentURL            |identifier|
|OMCWI研发服务号      |AutoTest_服务号文章一      |作者一       |AutoTest_甲骨文消息测试一   |http://www.bing.com/ |None|

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
|index  | title          |author      |content         |contentURL            |identifier|
|1      |AutoTest_服务号文章二     |作者二      |AutoTest_甲骨文消息测试二   |http://www.bing.com/ |Eloqua ID      |
|2      |AutoTest_服务号文章三     |作者三      |AutoTest_甲骨文消息测试三   |http://www.bing.com/ |WeChat ID      |
|3      |AutoTest_服务号文章四     |作者四      |AutoTest_甲骨文消息测试四   |http://www.bing.com/ |WeChat Profile |

Scenario: Save and veriry if accout is existed
Then click save button
Then verify message <title> is existed
Then click service account welcome message link
Then click edit button in welcome message page
Then enable the Welcome Message page
Then select rich media type
Then select the first rich media item for the welcome message
Then verify if rich media item is selected
Then apply the welcome message settings

Examples:
| title      |
|AutoTest_服务号文章一 |

Scenario: Login mobile and enter the account
!-- Unfollow an account
Given login wechat in mobile phone
When tap service account <accountName> for a while
Then click unfollow button to unfollow the account
Then click the confirm button to unfollow again
!-- Follow an account
When click the top right options button
Then click the scan button
When click more options button
Then click select qrcode from image library button
Then user clicks the all images button
Then click the accounts image library
Then select the qrcode of Service Account
Then click the follow button

Examples:
|accountName |follow|unfollow|
|OMCWI研发服务号|关注   |取消关注 |

Scenario: verify message
Given login wechat in mobile phone
When click the service account  <accountName>
When find the message <title> in oracle account
Then verify the opened URL with <contentURL> and <identifier>
!-- Then closed the opened web site

Examples:
|accountName| title          |author      |content         |contentURL            |identifier     |
|OMCWI研发服务号|AutoTest_服务号文章一     |作者一      |AutoTest_甲骨文消息测试一   |http://www.bing.com/   |None           |
|OMCWI研发服务号|AutoTest_服务号文章二     |作者二      |AutoTest_甲骨文消息测试二   |http://www.bing.com/   |Eloqua ID      |
|OMCWI研发服务号|AutoTest_服务号文章三     |作者三      |AutoTest_甲骨文消息测试三   |http://www.bing.com/   |WeChat ID      |
|OMCWI研发服务号|AutoTest_服务号文章四     |作者四      |AutoTest_甲骨文消息测试四   |http://www.bing.com/  |WeChat Profile |
