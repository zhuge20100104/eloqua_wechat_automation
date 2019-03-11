Meta:
@mobile

Narrative:
Check customer rich media message to serivce account

GivenStories: com\oracle\auto\testapp\tests\web\campaigns\stories\Ensure_Service_Account_Followed_WM.story

Scenario: Ensure account is followed and Create new message for service account
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
Then choose only weChat followers

Examples:
|accountName         |title                          |author      |content                    |contentURL            |identifier|
|OMCWI研发服务号      |Customer_服务号图文消息一      |作者一       |服务号图文消息customer一   |http://www.bing.com/ |None|

Scenario: Create articles
Then click article button to add new article
Then click article item to edit Article <index>
Then message <subTitle> input firstly
And secondly input message <author>
And input content in frame <content>
And input message content URL <contentURL>
And select <identifier> item
Then select rich media content on the popup cover page
Then choose all weChat users

Examples:
|index  | subTitle                    |author      |content                    |contentURL            |identifier|
|1      |Customer_服务号图文消息二     |作者二      |服务号图文消息customer二   |http://www.bing.com/ |Eloqua ID      |
|2      |Customer_服务号图文消息三     |作者三      |服务号图文消息customer三   |http://www.bing.com/ |WeChat ID      |
|3      |Customer_服务号图文消息四     |作者四      |服务号图文消息customer四   |http://www.bing.com/ |WeChat Profile |
|4      |Customer_服务号图文消息五     |作者五      |服务号图文消息customer五   |%service.account.JS.landing.page% |None|
|5      |Customer_服务号图文消息六     |作者六      |服务号图文消息customer六   |%service.account.JS.landing.page%  |Eloqua ID      |
|6      |Customer_服务号图文消息七     |作者七      |服务号图文消息customer七   |%service.account.registration.landing.page%    |WeChat ID      |
|7      |Customer_服务号图文消息八     |作者八      |服务号图文消息customer八   |%service.account.registration.landing.page%    |WeChat Profile |

Scenario: Send customer rich media message to serivce account
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
When select rich media content: <title>
!-- Then verify if media is selected
Then click submit rich media message
Then verify if successful message appears
Then save and input campaign name: <campaign>
Then activate the campaign
Then verify if test message is sent successfully

Examples:
|accountName           | campaign                                    |title|
|OMCWI研发服务号        |%RAND_NUM%_服务号图文Customer消息_%RAND_NUM% |Customer_服务号图文消息一|

Scenario: verify normal URL message
Given login wechat in mobile phone
When click the service account  <accountName>
When find the message <title> in oracle account
Then verify copied Link URL with <contentURL> and <identifier> sent by campaign
!-- Then closed the opened web site

Examples:
|accountName      | title                        |author      |content                    |contentURL              |identifier     |
|OMCWI研发服务号  |Customer_服务号图文消息一      |作者一      |服务号图文消息customer一   |http://www.bing.com/  |None           |
|OMCWI研发服务号  |Customer_服务号图文消息二      |作者二      |服务号图文消息customer二   |http://www.bing.com/  |Eloqua ID      |
|OMCWI研发服务号  |Customer_服务号图文消息三      |作者三      |服务号图文消息customer三   |http://www.bing.com/  |WeChat ID      |
|OMCWI研发服务号  |Customer_服务号图文消息四      |作者四      |服务号图文消息customer四   |http://www.bing.com/  |WeChat Profile |

Scenario: Ensure account is followed and Create new message for service account

Scenario: verify Landing Page message
Given login wechat in mobile phone
When click the service account  <accountName>
When find the message <title> in oracle account
Then verify copied Link URL with <contentURL> and <identifier> sent by campaign
Then verify there is no copy link

Examples:
|accountName          | title                      |author      |content                    |contentURL              |identifier     |
|OMCWI研发服务号      |Customer_服务号图文消息五     |作者五      |服务号图文消息customer五   |%service.account.JS.landing.page%    |None|
|OMCWI研发服务号      |Customer_服务号图文消息六     |作者六      |服务号图文消息customer六   |%service.account.JS.landing.page%   |Eloqua ID      |
|OMCWI研发服务号      |Customer_服务号图文消息七     |作者七      |服务号图文消息customer七   |%service.account.registration.landing.page%    |WeChat ID      |
|OMCWI研发服务号      |Customer_服务号图文消息八     |作者八      |服务号图文消息customer八   |%service.account.registration.landing.page%    |WeChat Profile |

