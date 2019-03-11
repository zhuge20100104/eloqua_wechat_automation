Meta:
@test skip

!-- Edit an existing official account
Given login Eloqua
When click settings button on home page
When click apps button on platform extensions table on settings page
Then select wechat app in appcloud catalog page
Then click config link in my apps page
Then click <accountName> edit button
!-- Then select openID field <openIDValue>
!-- Then input default campaign ID <campaignIDValue>
Then click authorize button
Then verify qrcode in new page <pageTitle>
Then download qrcode to @Json{File}

!-- login wechat to authorize the new official account
Given login wechat in mobile phone
When push file @Json{File} to the phone
And click the top right options button
Then click the scan button
When click more options button
Then click select qrcode from image library button
Then select the qrcode
Then the message <authorizemessage> will appear
Then select continue use button
Then authorize success message <authorizesuccess> will show up

!-- back to Eloqua website to save the settings
Then save the account settings

Examples:
|accountName|campaignIDValue|openIDValue|pageTitle|authorizemessage|authorizesuccess|
|OMCWI研发订阅号|12345|WeChat_UnionID (C_WeChat_UnionID1)|微信公众平台|你之前已授权|授权成功|


Json: File
{
    name: "qrcode_%RAND_NUM%",
    path: "%wechat.test.data%"
}