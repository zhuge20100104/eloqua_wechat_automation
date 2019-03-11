Meta:


Narrative:
Track template message URL with charactors

Scenario: Track template message URL with charactors
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click template message link
When create a Action Result template: @Json{actionResultNotification}
Then click preview template button
Then verify if QR code message appears for unsaved template message
Then click save template button
Then click template message @Json{actionResultNotification.templateMsgName} edit button
Then verify template message field is as excepted: @Json{actionResultNotification.templateField}
Then verify if URL is as excepted: @Json{actionResultNotification.link}


Examples:
|accountName       |
|OMCWI研发服务号 |

Json: actionResultNotification
{
	templateField: "活动审核结果通知",
	templateMsgName: "%RAND_NUM%__AutoTestTemplate__%RAND_NUM%",
	title: "用户邮箱： ${F.C_EmailAddress}",
	bottomTitle: "你好！${F.C_EmailAddress} 你已通过审核，请按时参加会议",
	link: "http://www.baidu.com/?a=1&b=2a=index~!@#$%^&*()_+=-[]{}:;\%22'%3C%3E.,qwe%E6%B1%89%E5%AD%97~%21%40%23%24%25%5E%26%2A%28%29_%2B%3D-%5B%5D%7B%7D%3A%3B%22%27%3C%3E.%2C%2F%3F123qwe%E6%B1%89%E5%AD%97",
	identifier: "None"
}
