Meta:


Narrative:
Verify clear first level menu message appears

Scenario: Verify clear first level menu message appears
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click menu management link
Then add Personalized Menu Text Message: @Json{Filters}, <menuName>, <text>, <field>
Then click icon to add first sub menu
Then verify the clear first level menu message appears
Then input text message content: <text>, <field>
Then click personalized menu Publish button
Then delete personalized menu: <menuName> and verify if delete successfully


Examples:
|accountName        |menuName       |text    |field|
|OMCWI研发服务号    |M_%RAND_NUM%   |Hello   |Email Address|

Json: Filters
{
    tag: "DonotDelete!",
    gender: "Male"
}
