Meta:
@mobile

Narrative:
Configure new auto reply activity content object message for service account

Scenario: Configure new auto reply activity content object message for service account
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click auto reply management link
Then click configure new auto reply button
Then click activity message button in auto reply message
!-- Then choose object item <objectItem>
Examples:
|accountName         |objectItem       |
|OMCWI研发服务号     |Contact Object   |

Scenario: Add a rule with conditions
Then click add rule button
Then select condition value: <index>, <conditionItem>, <conditionSymbol>, <conditionValue>
Then add one update field: <fieldItem>, <fieldValue>
Then add text message sending: <msgType>, <msgMsg>, <textInsertMsg>

Examples:
|index  |conditionItem        |conditionSymbol |conditionValue      |fieldItem    |fieldValue          |msgType       |msgMsg                     |textInsertMsg|
|0      |First Name           |is not blank    |0                   |First Name   |FirstName%RAND_NUM%  |Text Message |Updating first name now:   |First Name    |



Scenario: Add a rule with conditions
Then click add rule button
Then select condition value: <index>, <conditionItem>, <conditionSymbol>, <conditionValue>
Then add text message sending: <msgType>, <msgMsg>, <textInsertMsg>
Examples:
|index|conditionItem           |conditionSymbol      |conditionValue             |msgType      |msgMsg                   |textInsertMsg|
|0    |First Name              |not equals to        |NewFirstName%RAND_NUM%     |Text Message |Updated first name is:   |First Name  |


Scenario: Add rule and default rule text messages
Then add default rule text message sending: <msgType>, <msgMsg>, <textInsertMsg>
Examples:
|msgMsg   |msgType      |textInsertMsg    |
|Default  |Text Message |First Name       |

Scenario: Save and veirify if created successfully
Then input auto reply msg name <autoReplyMsgName>
Then input auto reply keyword <keyword>
Then click save and close btn to save auto reply msg
Then verify auto reply message <autoReplyMsgName> is existed
Then click view button to view QR code with auto reply rich media message <autoReplyMsgName>
Then verify if QR code with auto reply rich media message appears
Then download autoreply message qrcode to @Json{File}

!-- Unfollow the account
Given login wechat in mobile phone
When ensure account <accountName> is followed

!-- Scan the auto-reply message qr code
When push file @Json{File} to the phone
When click the top right options button
Then click the scan button
When click more options button
Then click select qrcode from image library button
Then select the qrcode and wait longer

When find the message <msgMsg1> in oracle account
When input keyword <keyword> and wait longer
Then verify message after type keyword: <msgMsg2>

Examples:
|accountName        |msgMsg1                    |msgMsg2                 |keyword                |autoReplyMsgName            |
|OMCWI研发服务号 |Updating first name now:   |Updated first name is:  |activity%RAND_NUM%     |Autoreply_Activity_%RAND_NUM%|

Json: File
{
    name: "autoreply_activity_qrcode_%RAND_NUM%",
    path: "%wechat.test.data%"
}