Meta:

Narrative:
Track edit icon function for message

Scenario: Track edit icon function for message
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click Message Page

Examples:
|accountName        |
|OMCWI研发服务号 |

Scenario: Add message with two articles
Then create new message with articles: <index>, <title>, <author>, <content>, <contentURL>, <identifier>
Examples:
|index   |title                           |author      |content            |contentURL            |identifier|
|0       |CheckEidtIcon_服务号消息一      |作者一       |链接为bing网站一   |http://www.bing.com/   |None|
|1       |CheckEidtIcon_服务号消息        |作者         |JS Landing Page    |%service.account.JS.landing.page%  |Eloqua ID      |


Scenario: Edit articles
Then click save button
Then verify message <title> is existed
Then edit message Link and identifier: <title>, <contentURL>, <identifier>
Then verify <title>, <contentURL> and <identifier> are as excepted

Examples:
|title                           |contentURL                         |identifier|
|CheckEidtIcon_服务号消息一      |%service.account.JS.landing.page%   |Eloqua ID|
