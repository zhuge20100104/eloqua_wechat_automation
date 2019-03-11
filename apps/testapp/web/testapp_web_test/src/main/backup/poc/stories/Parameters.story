Meta:
@test skip

Narrative: poc stroy

Scenario: this is the first scenario
Given welcome <name>
When its description is <description>
Then thanks everyone
Then their boss is @Json{alex}

Examples:
|name   |description|
|stephen|student    |
|ming   |boss       |


Json: alex
{
	firstName: "%user.alex.firstName%",
	lastName: "%user.alex.lastName%",
	initials: "%user.alex.initials%",
	email: "%user.alex.email%",
	password: "%user.alex.password%"
}