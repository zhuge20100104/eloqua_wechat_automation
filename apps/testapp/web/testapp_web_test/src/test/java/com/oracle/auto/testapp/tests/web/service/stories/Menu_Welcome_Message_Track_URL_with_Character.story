Meta:


Narrative: Track URL with Charactors for Service and Subscription service

Scenario: Track URL with Charactors for Service and Subscription service
Given login Eloqua
Then choose one account in menu service list: <accountName>
Then click service account welcome message link
Then click edit button in welcome message page
Then enable the Welcome Message page
Then select text type
Then clear text configuration
Then input Link with URL: <URL> and text: <text>
Then click save and close btn to save welcome msg



Examples:
|accountName       |text                                 |URL                                            |
|OMCWI研发订阅号   |<p>~!@#%^&*()_+/>:"}?</p>  |http://www.bing.com/addon/intel-b2b?a=index&router=details&id=69~!@#%^&*()_+/>:"}? |
|OMCWI研发服务号   |<p>~!@#%^&*()_+/>:"}?</p>  |http://www.bing.com/addon/intel-b2b?a=index&router=details&id=69~!@#%^&*()_+/>:"}? |