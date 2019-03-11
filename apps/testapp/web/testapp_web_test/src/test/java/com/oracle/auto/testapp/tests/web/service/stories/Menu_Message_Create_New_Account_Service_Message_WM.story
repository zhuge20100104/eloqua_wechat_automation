Meta:
@mobile

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
|accountName          |title                           |author      |content                     |contentURL            |identifier|
|OMCWI研发服务号      |WelcomeMessage_服务号消息一      |作者一       |链接为bing网站一   |http://www.bing.com/   |None|

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
|index  | title                          |author      |content                  |contentURL                                       |identifier|
|1      |WelcomeMessage_服务号消息二     |作者二      |链接为bing网站二   |http://www.bing.com/                            |Eloqua ID      |
|2      |WelcomeMessage_服务号消息三     |作者三      |链接为bing网站三   |http://www.bing.com/                            |WeChat ID      |
|3      |WelcomeMessage_服务号消息四     |作者四      |链接为bing网站四   |http://www.bing.com/                            |WeChat Profile |
|4      |WelcomeMessage_服务号消息五     |作者五      |JS Landing Page五   |%service.account.JS.landing.page% |None|
|5      |WelcomeMessage_服务号消息六     |作者六      |JS Landing Page六   |%service.account.JS.landing.page%  |Eloqua ID      |
|6      |WelcomeMessage_服务号消息七     |作者七      |Registration Landing Page七   |%service.account.registration.landing.page%    |WeChat ID      |
|7      |WelcomeMessage_服务号消息八     |作者八      |Registration Landing Page八   |%service.account.registration.landing.page%    |WeChat Profile |

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
| title                |
|WelcomeMessage_服务号消息一  |

Scenario: Login mobile and enter the account
!-- Unfollow an account
Given login wechat in mobile phone
Given ensure account <accountName> is unfollowed

!-- Follow an account
When follow service account by scan QR code

Examples:
|accountName       |
|OMCWI研发服务号  |

Scenario: verify message
Given login wechat in mobile phone
When click the service account  <accountName>
When find the message <title> in oracle account
Then verify service account opened URL with <contentURL> and <identifier> by copy link
!-- Then closed the opened web site

Examples:
|accountName          | title                        |author      |content                       |contentURL                                     |identifier     |
|OMCWI研发服务号      |WelcomeMessage_服务号消息一     |作者一      |链接为bing网站一              |http://www.bing.com/                           |None           |
|OMCWI研发服务号      |WelcomeMessage_服务号消息二     |作者二      |链接为bing网站二              |http://www.bing.com/                           |Eloqua ID      |
|OMCWI研发服务号      |WelcomeMessage_服务号消息三     |作者三      |链接为bing网站三              |http://www.bing.com/                           |WeChat ID      |
|OMCWI研发服务号      |WelcomeMessage_服务号消息四     |作者四      |链接为bing网站四              |http://www.bing.com/                           |WeChat Profile |
|OMCWI研发服务号      |WelcomeMessage_服务号消息五     |作者五      |JS Landing Page五             |%service.account.JS.landing.page%              |None|
|OMCWI研发服务号      |WelcomeMessage_服务号消息六     |作者六      |JS Landing Page六             |%service.account.JS.landing.page%              |Eloqua ID      |
|OMCWI研发服务号      |WelcomeMessage_服务号消息七     |作者七      |Registration Landing Page七   |%service.account.registration.landing.page%    |WeChat ID      |
|OMCWI研发服务号      |WelcomeMessage_服务号消息八     |作者八      |Registration Landing Page八   |%service.account.registration.landing.page%    |WeChat Profile |


