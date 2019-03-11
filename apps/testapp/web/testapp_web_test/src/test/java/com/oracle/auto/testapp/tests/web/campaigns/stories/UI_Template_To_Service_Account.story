Meta:

Narrative:
Setup messaeg then cheak in browser: campaign to send template message to serivce account

Scenario: Send template message to serivce account
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click template message link

When create a Action Result template: @Json{actionResultNotification}
Then click preview template button
Then verify if QR code message appears for unsaved template message
Then click save template button
Then verify template message @Json{actionResultNotification.templateMsgName} is existed


Given login Eloqua
When choose a multiple steps blank campaign
When create a segment member with default segment
When choose wechat message sender in campaign item list
When connect segment members and message sender
Then double click wechat message sender in drawer container
Then click the configure cloud action button
When select weChat official account <accountName>
When select template message type
Then click next button
When search the template message @Json{actionResultNotification.templateMsgName}
Then select excepted @Json{actionResultNotification.templateMsgName} message
Then click submit template message
Then verify if successful message appears
Then save and input campaign name: <campaign>

Then double click wechat message sender in drawer container
Then click the configure cloud action button

Then verify if it is weChat official account <accountName>
Then verify if it is template message type
Then click next button
When search the template message @Json{actionResultNotification.templateMsgName}
Then confirm template message is selected
Then close the cloud action configuration page

Examples:
|accountName                             |campaign      |
|OMCWI研发服务号 |%RAND_NUM%_AutoTestCamp_%RAND_NUM%|

Json: actionResultNotification
{
	templateField: "活动审核结果通知",
	templateMsgName: "%RAND_NUM%_AutoTestTemp_%RAND_NUM%",
	title: "Date",
	bottomTitle: "你好！${F.C_FirstAndLastName} 你已通过审核，请按时参加会议",
	link: "%service.account.JS.landing.page%",
	identifier: "Eloqua ID"
}
