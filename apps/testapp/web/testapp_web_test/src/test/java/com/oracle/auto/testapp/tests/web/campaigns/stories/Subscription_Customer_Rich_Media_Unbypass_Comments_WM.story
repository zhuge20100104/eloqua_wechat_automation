Meta:
@mobile

Narrative:
Check customer rich media  message to subscription account

GivenStories: com\oracle\auto\testapp\tests\web\campaigns\stories\Ensure_Subscription_Account_Followed_WM.story

Scenario: Ensure account is followed and Prepare to create a new rich media message
Given login wechat in mobile phone
When ensure account <accountName> is followed

Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click Message Page
Then click new message button
Then click article button to add new article

Examples:
|accountName       |
|OMCWI研发订阅号   |

Scenario: Create rich meida message
Then click article item to edit Article <index>
Then message <title> input firstly
And secondly input message <author>
And input content in frame <content>
And input message content URL <contentURL>
Then select rich media content on the popup cover page
Then choose only weChat followers

Examples:
|index  |title                         |author     |content                     |contentURL            |
|0      |Customer_订阅号图文消息一     |作者一      |with only weChat followers  |http://cn.bing.com/    |
|1      |Customer_订阅号图文消息二     |作者二      |with all weChat users       |%subscription.account.JS.landing.page% |

Scenario: Send customer rich media message to subscription account
Then choose all weChat users
Then click save button
Then verify message <title> is existed
Given login Eloqua
When choose a multiple steps blank campaign
When create a segment member with default segment
When choose wechat message sender in campaign item list
When connect segment members and message sender
Then double click wechat message sender in drawer container
Then click the configure cloud action button
When select weChat official account <accountName>
And select the rich media message type
When select customer message type
Then click next button
Then uncheck byPass
When select rich media content: <title>
!-- Then verify if media is selected
Then click submit rich media message
Then verify if successful message appears
Then save and input campaign name: <campaign>
Then activate the campaign
Then verify if test message is sent successfully

Examples:
|accountName             |campaign                                      |title|
|OMCWI研发订阅号         |%RAND_NUM%_订阅号图文Customer消息_%RAND_NUM%    |Customer_订阅号图文消息一|


Scenario: verify message
Given login wechat in mobile phone
When click the subscription account <accountName>
When find the message <title> in oracle account


Examples:
|accountName          |title                          |author     |content                    |contentURL                            |
|OMCWI研发订阅号       |Customer_订阅号图文消息一      |作者一      |with only weChat followers |http://cn.bing.com/                   |
|OMCWI研发订阅号       |Customer_订阅号图文消息二      |作者二      |with all weChat users      |%subscription.account.JS.landing.page% |
