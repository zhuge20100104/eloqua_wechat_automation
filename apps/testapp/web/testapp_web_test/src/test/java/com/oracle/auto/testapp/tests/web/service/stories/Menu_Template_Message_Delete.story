Meta:


Narrative:
Delete existed template Message

Scenario: Edit existed template message
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click template message link

When create a Action Result template: @Json{actionResultNotification}
Then click save template button

Then delete existed template message: @Json{actionResultNotification.templateMsgName}
Then verify template message @Json{actionResultNotification.templateMsgName} is deleted


Examples:
|accountName     |
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


