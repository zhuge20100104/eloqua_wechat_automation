Meta:
@mobile

Narrative:
Create a campaign to send template message to serivce account

GivenStories: com\oracle\auto\testapp\tests\web\campaigns\stories\Ensure_Service_Account_Followed_WM.story

Scenario: Ensure account is followed and Map new template message and preview before saved
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click template message link

When create a Action Result template: @Json{actionResultNotification}
Then input APP ID and page path
Then click preview template button
Then verify if QR code message appears for unsaved template message
Then click save template button
Then verify template message @Json{actionResultNotification.templateMsgName} is existed
Then close menu service page

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
Then verify if template message is selected successfully
Then save and input campaign name: <campaign>
Then activate the campaign
Then verify if test message is sent successfully

Given login wechat in mobile phone
When click the service account <accountName>
When find the message @Json{actionResultNotification.title} in oracle account
Then verify APP page is present

Examples:
|accountName        |campaign                               |
|OMCWI研发服务号    |%RAND_NUM%_服务号Template消息_%RAND_NUM%|

Json: actionResultNotification
{
	templateField: "活动审核结果通知",
	templateMsgName: "AutoTestTemp_%RAND_NUM%_%RAND_NUM%",
	title: "测试服务号小程序%RAND_NUM%",
	bottomTitle: "你好！${F.C_FirstAndLastName} 你已通过审核，请按时参加会议",
	link: "%service.account.JS.landing.page%",
	identifier: "Eloqua ID",
	appID:"wxc3933c45d47942c9",
	pagePath:"pages/index/index"
}