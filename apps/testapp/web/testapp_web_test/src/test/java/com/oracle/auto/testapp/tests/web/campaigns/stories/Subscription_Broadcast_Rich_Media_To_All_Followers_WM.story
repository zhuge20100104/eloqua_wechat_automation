Meta:
@test skip

Narrative:
Send and check broadcase rich media message to subscription account

GivenStories: com\oracle\auto\testapp\tests\web\campaigns\stories\Ensure_Subscription_Account_Followed_WM.story

Scenario: Prepare to create a new rich media message
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
|index  | title                           |author      |content                    |contentURL            |
|0      |广播订阅号消息AllFollowers一      |作者一      |only weChat followers 一   |http://cn.bing.com/    |
|1      |广播订阅号消息AllFollowers二      |作者二      |all weChat users 二        |%subscription.account.JS.landing.page% |

Scenario: Send broadcase rich media message to subscription account
Then choose all weChat users
Then click save button
Then verify message <mediaTitle> is existed
Given login Eloqua
When choose a multiple steps blank campaign
When create a segment member with default segment
When choose wechat message sender in campaign item list
When connect segment members and message sender
Then double click wechat message sender in drawer container
Then click the configure cloud action button
When select weChat official account <accountName>
When select the rich media message type
When select broadcast message type
When check the all followers box
Then click next button
When select rich media content
Then verify if media is selected
Then click submit rich media message
Then verify if successful message appears
Then save and input campaign name: <campaign>
Then activate the campaign
Then verify if test message is sent successfully

Examples:
|accountName             |campaign                              |mediaTitle|
|OMCWI研发订阅号         |%RAND_NUM%_AutoTestCamp_%RAND_NUM%    |广播订阅号消息AllFollowers一|

Scenario: verify message
Given login wechat in mobile phone
When click the subscription account <accountName>
When find the message <title> in oracle account
!-- Then verify opened URL with <contentURL> by copy link
!-- Then closed the opened web site

Examples:
|accountName           | title                          |author     |content                    |contentURL                            |
|OMCWI研发订阅号       |广播订阅号消息AllFollowers一     |作者一      |only weChat followers 一   |http://cn.bing.com/                   |
|OMCWI研发订阅号       |广播订阅号消息AllFollowers二     |作者二      |all weChat users 二        |%subscription.account.JS.landing.page% |

