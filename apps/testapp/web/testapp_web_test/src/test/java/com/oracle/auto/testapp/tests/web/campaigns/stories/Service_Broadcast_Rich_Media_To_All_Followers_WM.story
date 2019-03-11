Meta:
@test skip

Narrative:
Send and check broadcase rich meida message to serivce account with all followers

GivenStories: com\oracle\auto\testapp\tests\web\campaigns\stories\Ensure_Service_Account_Followed_WM.story

Scenario: Create new message for service account
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click Message Page
Then click new message button
Then message <mediaTitle> input firstly
And secondly input message <author>
And input content in frame <content>
And input message content URL <contentURL>
And select <identifier> item
Then select rich media content on the popup cover page

Examples:
|accountName          |mediaTitle                            |author      |content         |contentURL            |identifier|
|OMCWI研发服务号      |AllFollowers_服务号图文广播消息一      |作者一       |No Comments 一   |http://www.bing.com/ |None|

Scenario: Create articles
Then click article button to add new article
Then click article item to edit Article <index>
Then message <subTitle> input firstly
And secondly input message <author>
And input content in frame <content>
And input message content URL <contentURL>
And select <identifier> item
Then select rich media content on the popup cover page
Then choose only weChat followers

Examples:
|index  | subTitle                            |author      |content                    |contentURL            |identifier|
|1      |AllFollowers_服务号图文广播消息二     |作者二      |only weChat followers 二   |http://www.bing.com/ |Eloqua ID      |
|2      |AllFollowers_服务号图文广播消息三     |作者三      |only weChat followers 三   |http://www.bing.com/ |WeChat ID      |
|3      |AllFollowers_服务号图文广播消息四     |作者四      |only weChat followers 四   |http://www.bing.com/ |WeChat Profile |
|4      |AllFollowers_服务号图文广播消息五     |作者五      |only weChat followers 五   |%service.account.JS.landing.page% |None|
|5      |AllFollowers_服务号图文广播消息六     |作者六      |only weChat followers 六   |%service.account.JS.landing.page%  |Eloqua ID      |
|6      |AllFollowers_服务号图文广播消息七     |作者七      |only weChat followers 七   |%service.account.registration.landing.page%    |WeChat ID      |
|7      |AllFollowers_服务号图文广播消息八     |作者八      |all weChat users 八        |%service.account.registration.landing.page%    |WeChat Profile |

Scenario: Send broadcase rich meida message to serivce account with all followers
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
And select the rich media message type
When select broadcast message type
And check the all followers box
Then click next button
When select rich media content
Then verify if media is selected
Then click submit rich media message
Then verify if successful message appears
Then save and input campaign name: <campaign>
Then activate the campaign
Then verify if test message is sent successfully

Examples:
|accountName        |campaign                            |mediaTitle|
|OMCWI研发服务号  |%RAND_NUM%_服务号图文广播消息ToAllFollowers_%RAND_NUM%  |AllFollowers_服务号图文广播消息一|

Scenario: verify message
Given login wechat in mobile phone
When click the service account  <accountName>
When find the message <title> in oracle account
Then verify copied Link URL with <contentURL> and <identifier> sent by campaign
!-- Then closed the opened web site

Examples:
|accountName          |title                                |author      |content                   |contentURL            |identifier     |
|OMCWI研发服务号      |AllFollowers_服务号图文广播消息一     |作者一      |No Comments 一             |http://www.bing.com/   |None           |
|OMCWI研发服务号      |AllFollowers_服务号图文广播消息二     |作者二      |only weChat followers 二   |http://www.bing.com/   |Eloqua ID      |
|OMCWI研发服务号      |AllFollowers_服务号图文广播消息三     |作者三      |only weChat followers 三   |http://www.bing.com/   |WeChat ID      |
|OMCWI研发服务号      |AllFollowers_服务号图文广播消息四     |作者四      |only weChat followers 四   |http://www.bing.com/  |WeChat Profile |
|OMCWI研发服务号      |AllFollowers_服务号图文广播消息四     |作者四      |only weChat followers 四   |http://www.bing.com/  |WeChat Profile |
|OMCWI研发服务号      |AllFollowers_服务号图文广播消息五     |作者五      |only weChat followers 五   |%service.account.JS.landing.page%    |None|
|OMCWI研发服务号      |AllFollowers_服务号图文广播消息六     |作者六      |only weChat followers 六   |%service.account.JS.landing.page%   |Eloqua ID      |
|OMCWI研发服务号      |AllFollowers_服务号图文广播消息七     |作者七      |only weChat followers 七   |%service.account.registration.landing.page%    |WeChat ID      |
|OMCWI研发服务号      |AllFollowers_服务号图文广播消息八     |作者八      |all weChat users 八        |%service.account.registration.landing.page%    |WeChat Profile |

