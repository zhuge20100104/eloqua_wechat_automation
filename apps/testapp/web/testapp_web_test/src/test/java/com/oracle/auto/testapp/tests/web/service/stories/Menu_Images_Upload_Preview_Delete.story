Meta:


Narrative:
Upload images,then preview and delete

Scenario: Upload images,then preview and delete
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click images message link
Then click upload image button to upload files

Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click images message link
Then Verify if the files are uploaded successfully
Then verify if image can be previewed: @Json{File.name}

Then delete all automation images: AutoImage


Examples:
|accountName    |
|OMCWI研发服务号 |
|OMCWI研发订阅号 |


Json: File
{
    name: "AutoImage1.png",
    path: "%wechat.test.images%"
}