Meta:


Narrative:
Add one personalized menu with five text messages sub menu then reveiw and delete

Scenario: Add one personalized menu with five text messages sub menu then reveiw and delete
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click menu management link
Then publish Sub Personalized Menu with: <number> Text Message: @Json{Filters}, <menuName>, <text>, <field>, <subMenuName>
Then verify filters in Menu Management page: @Json{Filters}, <menuName>
Then review Sub Personalized Menu with: <number> Text Message: @Json{Filters}, <menuName>, <subMenuName>
Then delete personalized menu: <menuName> and verify if delete successfully

Examples:
|accountName        |menuName      |number  |subMenuName       |text    |field|
|OMCWI研发服务号    |M_%RAND_NUM%   |5      |SuM%RAND_NUM%  |Hello   |Email Address|

Json: Filters
{
    tag: "DonotDelete!",
    gender: "Male"
}
