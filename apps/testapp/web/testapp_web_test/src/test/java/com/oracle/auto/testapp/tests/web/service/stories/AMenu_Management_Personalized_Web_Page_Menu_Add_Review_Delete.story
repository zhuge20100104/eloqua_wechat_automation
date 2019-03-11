Meta:


Narrative:
Add one personalized menu with text message then reveiw and delete

Scenario: Add one personalized menu with text message then reveiw and delete
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click menu management link
Then publish Web Page Personalized Menu: @Json{Filters}, <menuName>, <URL>, <identifier>
Then verify filters in Menu Management page: @Json{Filters}, <menuName>
!-- Then review one menu with text message content in Personalized Menu page: @Json{Filters}, <menuName>
Then delete personalized menu: <menuName> and verify if delete successfully

Examples:
|accountName        |menuName         |URL                    |identifier|
|OMCWI研发服务号    |M_%RAND_NUM%      |http://www.baidu.com   |None|

Json: Filters
{
    tag: "DonotDelete!",
    gender: "Male"
}
