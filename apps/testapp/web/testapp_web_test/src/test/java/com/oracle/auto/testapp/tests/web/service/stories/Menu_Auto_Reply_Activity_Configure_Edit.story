Meta:


Narrative:
Configure new auto reply activity content object message for service account

Scenario: Configure new auto reply activity content object message for service account
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click auto reply management link
Then click configure new auto reply button
Then click activity message button in auto reply message
Then choose object item <objectItem>
Then click add rule button

Examples:
|accountName      |objectItem       |
|OMCWI研发服务号  |Contact Object   |

Scenario: Add a rule with conditions
Then select condition value: <index>, <conditionItem>, <conditionSymbol>, <conditionValue>

Examples:
|index |conditionItem   |conditionSymbol    |conditionValue  |
|0     |Email Address   |not equals to      |5               |


Scenario: Add update field
Then add one update field: <fieldItem>, <fieldValue>

Examples:
|fieldItem  | fieldValue  |
|Address 1   | not null   |

Scenario: Add rule and default rule text messages
Then add text message sending: <msgType>, <msgMsg>, <textInsertMsg>
Then add default rule text message sending: <msgType>, <msgMsg>, <textInsertMsg>

Examples:
|msgMsg   |msgType     |textInsertMsg|
|text msg |Text Message|Address 1    |

Scenario: Add rule and default rule rich media messages
Then add rich media message sending: <msgType>
Then add default rule rich media message sending: <msgType>

Examples:
|msgType  |
|Rich Media Message|

Scenario: Add rule and default rule template messages
Then add template message sending: <msgType>
Then add default rule template message sending: <msgType>
Examples:
|msgType  |
|Template Message|


Scenario: Save and veirify if created successfully
Then input auto reply msg name <autoReplyMsgName>
Then click save and close btn to save auto reply msg
!-- Verify no error msg appears
Then verify auto reply message <autoReplyMsgName> is existed

Then click auto reply rich media message <autoReplyMsgName> edit button
Then input auto reply msg name <newAutoReplyMsgName>
Then click save and close btn to save auto reply msg
Then verify auto reply message <newAutoReplyMsgName> is existed

Examples:
|autoReplyMsgName                   |newAutoReplyMsgName                  |
|Activity扫码发消息测试_%RAND_NUM%  |%RAND_NUM%_编辑Activity扫码发消息测试_%RAND_NUM%  |



