Meta:


Narrative:
Create a registration blank landing page and submit a contact

Scenario: Create a registration blank landing page and submit a contact
Given login Eloqua
When move cursor to assets
And click the landing page button
Then choose blank landing page
Then select domain and vanity URL: <vanityURL>

!-- choose and move JS
Then click cloud content button
Then select one cloud content: <registrationLPService>
Then move cloud content element in canvas

!-- choose and move form
Then click form button
Then select one form: <formName>
Then move form element in canvas

Then save landing page: <landingPageName>

!-- edit the JS content
Then open JS cloud content issue
Then choose openID and nick name for JS Generator
Then save and close the JS message page

!-- save, open and edit JS LP
Then click save landing page button
Then get landing page url
Then input Email Address: <emailAddress> and Nick Name

!-- check if the contact is saved
Given login Eloqua
When move cursor to audience
Then click the contacts button
Then input contact mail: <emailAddress>
Then the status should be: <message>


Examples:
|registrationLPService                       |formName              |vanityURL                     |landingPageName                      |emailAddress                                |message|
|%eloqua.wechat.JS.landing.page.generator%   |%eloqua.wechat.form% |autotest_%RAND_NUM%_service   |AutoTestServiceAccountLP_%RAND_NUM%  |autotest_%RAND_NUM%@blackhole.oracle.com    |Displaying 1 of 1 matching contact(s)|
