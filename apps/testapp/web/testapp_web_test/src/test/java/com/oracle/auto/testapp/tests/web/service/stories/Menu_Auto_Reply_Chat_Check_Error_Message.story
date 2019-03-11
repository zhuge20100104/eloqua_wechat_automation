Meta:


Narrative:
Configure new auto reply chat message for service account


Scenario: Create new chat auto reply message
Given login Eloqua
Then choose one account in menu service list: @Json{Message.account}

!-- Configure new auto reply rich media message for service account
Then click auto reply management link
Then click configure new auto reply button
Then input auto reply msg name @Json{Message.name}
Then input auto reply msg keyword @Json{Message.keyword}
Then click chat msg button in auto reply message
Then load form: <formName>
Then click save and close btn to save auto reply msg
Then Verify error messages appear for Chat content



Examples:
|emailAddress                                   |formName             |
|wechat.autotest%RAND_NUM%@blackhole.oracle.com |%eloqua.wechat.form% |

Json: Message
{
    account: "OMCWI研发服务号",
    name: "AutoTestChat扫码发消息测试_%RAND_NUM%_%RAND_NUM%",
    title: "AutoTest_服务号文章_%RAND_NUM%",
    author: "作者_%RAND_NUM%",
    keyword: "chat%RAND_NUM%",
    content: "AutoTest_甲骨文消息测试_%RAND_NUM%",
    url: "http://www.bing.com/",
    openedURL: "http://cn.bing.c/"
}
