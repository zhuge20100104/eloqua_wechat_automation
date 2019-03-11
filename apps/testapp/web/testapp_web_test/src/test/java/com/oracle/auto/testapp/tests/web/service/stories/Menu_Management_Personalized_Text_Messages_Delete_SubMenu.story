Meta:


Narrative:
Add one personalized menu with one text messages sub menu then delete all

Scenario: Add one personalized menu with one text messages sub menu then delete all

Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click menu management link
Then add Level One Personalized Menu: @Json{Filters}, <menuName>
Then click icon to add first sub menu
Then input <subMenuName> with Text Message: <text>, <field>
Then delete personalized sub menu in first level menu list: <subMenuName>
Then delete personalized first level menu: <menuName>
Then click personalized menu Publish button
Then verify at least one menu message



Examples:
|accountName       |menuName       |subMenuName     |text    |field|
|OMCWI研发服务号    |M_%RAND_NUM%   |Sub_%RAND_NUM%  |Hello   |Email Address|

Json: Filters
{
    tag: "DonotDelete!",
    gender: "Male"
}
