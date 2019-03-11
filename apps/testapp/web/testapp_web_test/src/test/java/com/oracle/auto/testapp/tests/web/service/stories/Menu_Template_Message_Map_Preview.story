Meta:


Narrative:
Map new template message and preview before saved

Scenario: Map new template message and preview before saved
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click template message link
When create a Action Result template: @Json{actionResultNotification}
Then click preview template button
Then verify if QR code message appears for unsaved template message
Then click save template button
!-- Then verify template message <templateMsgName> is existed
Then click template message @Json{actionResultNotification.templateMsgName} edit button
Then verify template message field is as excepted: @Json{actionResultNotification.templateField}

Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click template message link
Then verify if QR code appears for template message: @Json{actionResultNotification.templateMsgName}

Examples:
|accountName       |
|OMCWI研发服务号 |

Json: actionResultNotification
{
	templateField: "活动审核结果通知",
	templateMsgName: "%RAND_NUM%__AutoTestTemplate__%RAND_NUM%",
	title: "用户邮箱： ${F.C_EmailAddress}",
	bottomTitle: "你好！${F.C_EmailAddress} 你已通过审核，请按时参加会议",
	link: "http://www.baidu.com",
	identifier: "Eloqua ID"
}
