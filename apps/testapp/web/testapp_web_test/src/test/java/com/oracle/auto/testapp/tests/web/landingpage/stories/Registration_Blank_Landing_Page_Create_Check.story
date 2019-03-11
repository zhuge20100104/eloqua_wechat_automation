Meta:


Narrative:
Create a registration blank landing page and submit a contact

Scenario: Create a registration blank landing page and submit a contact
Given login Eloqua
When move cursor to assets
And click the landing page button
Then choose blank landing page
Then select domain and vanity URL: <vanityURL>
Then click cloud content button

Then select one cloud content: <registrationLPService>
Then open Registration cloud content issue

Then choose one form
Then choose openID for service account
Then save and close the Registration page
Then save landing page: <landingPageName>
Then get landing page url
Then input Email Address: <emailAddress> and Nick Name

Given login Eloqua
When move cursor to audience
Then click the contacts button
Then input contact mail: <emailAddress>
Then the status should be: <message>



Examples:
|registrationLPService                                  |vanityURL                     |landingPageName                      |emailAddress                                |message|
|%eloqua.wechat.registration.landing.page.generator%    |autotest_%RAND_NUM%_service   |AutoTestServiceAccountLP_%RAND_NUM%  |autotest_%RAND_NUM%@blackhole.oracle.com    |Displaying 1 of 1 matching contact(s)|
