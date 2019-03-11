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
|accountName         |title                         |author      |content                      |contentURL             |identifier|
|1                   |Customer_服务号图文消息一      |作者一      |with only weChat followers   |http://www.bing.com/   |None           |

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
|index  | subTitle                     |author      |content                   |contentURL             |identifier     |
|1      |Customer_服务号图文消息二      |作者二      |with all weChat users     |http://www.bing.com/   |Eloqua ID      |

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
Then uncheck byPass
When select rich media content: <title>
!-- Then verify if media is selected
Then click submit rich media message
Then verify if successful message appears
Then save and input campaign name: <campaign>
Then activate the campaign
Then verify if test message is sent successfully

Examples:
|accountName           | campaign                                              |title                   |
|OMCWI研发服务号        |%RAND_NUM%_服务号图文Customer消息Unbypass_%RAND_NUM%   |Customer_服务号图文消息一|

Scenario: verify message
Given login wechat in mobile phone
When click the service account  <accountName>
When find the message <title> in oracle account


Examples:
|accountName     | title                        |author      |content                     |contentURL              |identifier     |
|OMCWI研发服务号  |Customer_服务号图文消息一      |作者一      |with only weChat followers  |http://www.bing.com/   |None           |
|OMCWI研发服务号  |Customer_服务号图文消息二      |作者二      |with all weChat users       |http://www.bing.com/   |Eloqua ID      |

