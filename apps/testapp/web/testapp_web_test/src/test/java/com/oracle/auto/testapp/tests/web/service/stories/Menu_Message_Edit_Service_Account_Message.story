Meta:

Narrative:
Edit message for service Account


Scenario: Edit existed message for service account
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click Message Page
Then click new message button
Then create article: @Json{articalContent}
Then click save button

Then edit existed message
Then create article: @Json{newArticalContent}
Then click save button
Then verify message @Json{newArticalContent.title} is existed


Examples:
|accountName         |
|OMCWI研发服务号   |

Json: articalContent
{
	title: "AutoTest_服务号文章一",
	author: "作者一",
	content: "AutoTest_甲骨文消息测试一",
	contentURL: "http://www.baidu.com/",
	identifier: "Eloqua ID",
    accountName: "OMCWI研发服务号"
}

Json: newArticalContent
{
	title: "编辑AutoTest_服务号文章一",
	author: "甲骨文编辑",
	content: "甲骨文消息编辑测试",
	contentURL: "%service.account.JS.landing.page%",
	identifier: "WeChat Profile",
	accountName: "OMCWI研发服务号"
}



