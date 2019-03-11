Meta:

Narrative:
Edit existed template message

Scenario: Edit existed template message
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click template message link
When create a Action Result template: @Json{actionResultNotification}
Then click save template button
Then click template message @Json{actionResultNotification.templateMsgName} edit button
When create a Action Result template: @Json{newActionResultNotification}
Then click save template button

Then click template message @Json{newActionResultNotification.templateMsgName} edit button
Then verify template message field is as excepted: @Json{newActionResultNotification.templateField}


Examples:
|accountName         |
|OMCWI研发服务号     |

Json: actionResultNotification
{
	templateField: "活动审核结果通知",
	templateMsgName: "%RAND_NUM%__AutoTestTemplate__%RAND_NUM%",
	title: "活动审核结果通知： ${F.C_EmailAddress}",
	bottomTitle: "你好！${F.C_EmailAddress} 你已通过审核，请按时参加会议",
	link: "http://www.baidu.com",
	identifier: "Eloqua ID"
}

Json: newActionResultNotification
{
	templateField: "报名成功通知",
	templateMsgName: "%RAND_NUM%__AutoTestTemplate__%RAND_NUM%",
	title: "报名成功通知： ${F.C_EmailAddress}",
	bottomTitle: "你好！${F.C_EmailAddress} 你已通过审核，请按时参加会议",
	link: "http://www.bing.com",
	identifier: "None"
}




