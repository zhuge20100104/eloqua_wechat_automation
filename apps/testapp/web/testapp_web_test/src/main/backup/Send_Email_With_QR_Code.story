Meta:

Narrative:
Send email with QR code

Scenario: Send email with QR code
Given login Eloqua
When choose a multiple steps blank campaign
When create a segment member with default segment
When double click email btn in campaign item list
When connect segment members and message sender
Then double click email in drawer container
Then select a email with resend option
When click save button to save the configuration
Then input the campaign name <campaign> and save
Then activate the campaign

Examples:
|accountName       |campaign    |
|OMCWI研发服务号  |%RAND_NUM%_AutoTestCamp_%RAND_NUM%|
