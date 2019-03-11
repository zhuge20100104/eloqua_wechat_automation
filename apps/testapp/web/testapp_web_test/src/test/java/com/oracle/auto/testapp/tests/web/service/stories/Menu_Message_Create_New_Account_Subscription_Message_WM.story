Meta:
@mobile

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
|OMCWI研发订阅号    |

Scenario: Create articles
Then click article item to edit Article <index>
Then message <title> input firstly
And secondly input message <author>
And input content in frame <content>
And input message content URL <contentURL>
Then select rich media content on the popup cover page

Examples:
|index  | title                        |author      |content         |contentURL            |
|0      |Menu_Message_订阅号文章一      |作者一      |Menu_Message_订阅号消息一   |http://www.bing.com/ |
|1      |Menu_Message_订阅号文章二      |作者二      |Menu_Message_订阅号消息二   |%subscription.account.JS.landing.page%|

Scenario: Save and veriry if accout is existed
Then click save button
Then verify message <title> is existed
Then click subscription account welcome message link
Then click edit button in welcome message page
Then enable the Welcome Message page
Then select rich media type
Then select the first rich media item for the welcome message
Then verify if rich media item is selected
Then apply the welcome message settings

Examples:
| title      |
|Menu_Message_订阅号文章一 |

Scenario: Unfollow and follow subscription account.
!-- Unfollow an account
Given login wechat in mobile phone
Given ensure account <accountName> is unfollowed

!-- Follow an account
When follow subscription account by scan QR code

Examples:
|accountName   |
|OMCWI研发订阅号 |

Scenario: verify message
Given login wechat in mobile phone
When click the subscription account <accountName>
When find the message <title> in oracle account
Then verify opened URL with <contentURL> by copy link
!-- Then closed the opened web site

Examples:
|accountName          | title                         |author      |content                    |contentURL                     |
|OMCWI研发订阅号       |Menu_Message_订阅号文章一     |作者一      |Menu_Message_订阅号消息一   |http://cn.bing.com/           |
|OMCWI研发订阅号       |Menu_Message_订阅号文章二     |作者二      |Menu_Message_订阅号消息二   |%subscription.account.JS.landing.page%|
