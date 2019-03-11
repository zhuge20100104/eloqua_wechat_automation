<#ftl strip_whitespace=true>
<#macro renderStat stats name class=""><#assign value = stats.get(name)!0><#if (value != 0)><span class="${class}">${value}</span><#else>${value}</#if></#macro>
<#macro renderTime millis class=""><span class="${class}"><#assign time = timeFormatter.formatMillis(millis)>${time}</span></#macro>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN"
        "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<title>Saas Web Auto Reports</title>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<style type="text/css" media="all">
@import url( "./style/jbehave-core.css" );
</style>
</head>

<body>
<div id="banner"><img src="images/jbehave-logo.png" alt="jbehave" />
<div class="clear"></div>
</div>

<div class="reports">

<h2>Reports Overview</h2> 


<table>
<colgroup span="2" class="stories"></colgroup>
<colgroup span="5" class="scenarios"></colgroup>
<colgroup span="6" class="steps"></colgroup>
<colgroup span="1" class="view"></colgroup>
<tr>
    <th colspan="2">Stories</th>
    <th colspan="5">Scenarios</th>
    <th colspan="6">Steps</th>
    <th colspan="1"><a href="../reports.csv">Download as CSV</a></th>
</tr>
<tr>
    <th>Total</th>
    <th>Excluded</th>
    <th>Total</th>
    <th>Successful</th>
    <th>Pending</th>
    <th>Failed</th>
    <th>Excluded</th>
    <th>Total</th>
    <th>Successful</th>
    <th>Pending</th>
    <th>Failed</th>
    <th>Not Performed</th>
    <th>Ignorable</th>
    <th>Duration (hh:mm:ss.sss)</th>
</tr>
<#assign reportNames = reportsTable.getReportNames()>
<#assign totalReports = reportNames.size() - 3>
<#list reportNames as name>
<#assign report = reportsTable.getReport(name)>
<#if name != "Totals">
</#if>
</#list>
<tr class="totals">
<td>${totalReports}</td>
<#assign stats = reportsTable.getReport("Totals").getStats()>
<#assign scenariosPending = stats.get("scenariosPending")!0>
<#assign scenariosSuccessful = stats.get("scenariosSuccessful")!0>
<#assign scenariosSuccessful = scenariosSuccessful - scenariosPending>
<#if (scenariosSuccessful < 0)>
<#assign scenariosSuccessful = 0>
</#if>

<#assign scenarios = stats.get("scenarios")!0>
<#assign scenariosNotAllowed = stats.get("scenariosNotAllowed")!0>
<#assign scenarios = scenarios - scenariosNotAllowed>
<#assign scenariosNotAllowed = 0>


<td>
<@renderStat stats "notAllowed" "failed"/>
</td>
<td>
${scenarios}
</td>
<td class="successful">
${scenariosSuccessful}
</td>
<td>
<@renderStat stats "scenariosPending" "pending"/> 
</td>
<td>
<@renderStat stats "scenariosFailed" "failed"/>
</td>
<td>
${scenariosNotAllowed}
</td>

<td>
<@renderStat stats "steps" />
</td>
<td>
<@renderStat stats "stepsSuccessful" "successful"/>
</td>
<td>
<@renderStat stats "stepsPending" "pending"/>
</td>
<td>
<@renderStat stats "stepsFailed" "failed"/>
</td>
<td>
<@renderStat stats "stepsNotPerformed" "notPerformed" />
</td>
<td>
<@renderStat stats "stepsIgnorable" "ignorable"/>
</td>
<td>
<@renderTime storyDurations.get('total')!0/>
</td>
</tr>
</table>






<h2>Story Reports</h2>

<table>
<colgroup span="2" class="stories"></colgroup>
<colgroup span="5" class="scenarios"></colgroup>
<colgroup span="6" class="steps"></colgroup>
<colgroup class="view"></colgroup>
<tr>
    <th colspan="2">Stories</th>
    <th colspan="5">Scenarios</th>
    <th colspan="6">Steps</th>
    <th></th>
</tr>
<tr>
    <th>Name</th>
    <th>Excluded</th>
    <th>Total</th>
    <th>Successful</th>
    <th>Pending</th>
    <th>Failed</th>
    <th>Excluded</th>
    <th>Total</th>
    <th>Successful</th>
    <th>Pending</th>
    <th>Failed</th>
    <th>Not Performed</th>
    <th>Ignorable</th>
    <th>Duration (hh:mm:ss.sss)</th>
</tr>
<#assign reportNames = reportsTable.getReportNames()>
<#assign totalReports = reportNames.size() - 3>
<#list reportNames as name>
<#assign report = reportsTable.getReport(name)>
<#if name != "Totals" && name != "AfterStories" && name != "BeforeStories">
<tr>
<#assign stats = report.getStats()>
<#assign stepsFailed = stats.get("stepsFailed")!0>
<#assign scenariosFailed = stats.get("scenariosFailed")!0>
<#assign scenariosPending = stats.get("scenariosPending")!0>
<#assign scenariosSuccessful = stats.get("scenariosSuccessful")!0>
<#assign scenariosSuccessful = scenariosSuccessful - scenariosPending>

<#if (scenariosSuccessful < 0)>
<#assign scenariosSuccessful = 0>
</#if>
<#assign scenarios = stats.get("scenarios")!0>
<#assign scenariosNotAllowed = stats.get("scenariosNotAllowed")!0>
<#assign scenarios = scenarios - scenariosNotAllowed>
<#assign scenariosNotAllowed = 0>

<#assign pending = stats.get("pending")!0>
<#assign storyClass = "story">
<#if stepsFailed != 0 || scenariosFailed != 0>
    <#assign storyClass = storyClass + " failed">
<#elseif scenariosSuccessful == 0 && pending != 0>
    <#assign storyClass = storyClass + " pending">
<#else>
    <#assign storyClass = storyClass + " successful">
</#if>
<td>
<a href="${report.filesByFormat.get('html').name}"><p class="${storyClass}">${report.name}</p></a>
</td>

</td>
<td>
<@renderStat stats "notAllowed" "failed"/>
</td>
<td>
${scenarios}
</td>
<td class="successful">
${scenariosSuccessful}
</td>
<td>
<@renderStat stats "scenariosPending" "pending"/> 
</td>
<td>
<@renderStat stats "scenariosFailed" "failed"/>
</td>
<td>
${scenariosNotAllowed}
</td>

<td>
<@renderStat stats "steps" />
</td>
<td>
<@renderStat stats "stepsSuccessful" "successful"/>
</td>
<td>
<@renderStat stats "stepsPending" "pending"/>
</td>
<td>
<@renderStat stats "stepsFailed" "failed"/>
</td>
<td>
<@renderStat stats "stepsNotPerformed" "notPerformed" />
</td>
<td>
<@renderStat stats "stepsIgnorable" "ignorable"/>
</td>
<td>
<#assign path = report.getPath()>
<@renderTime storyDurations.get(path)!0/>
</td>
</tr>
</#if>
</#list>
<tr class="totals">
<td>${totalReports}</td>
<#assign stats = reportsTable.getReport("Totals").getStats()>
<#assign scenariosPending = stats.get("scenariosPending")!0>
<#assign scenariosSuccessful = stats.get("scenariosSuccessful")!0>
<#assign scenariosSuccessful = scenariosSuccessful - scenariosPending>

<#if (scenariosSuccessful < 0)>
<#assign scenariosSuccessful = 0>
</#if>
<#assign scenarios = stats.get("scenarios")!0>
<#assign scenariosNotAllowed = stats.get("scenariosNotAllowed")!0>
<#assign scenarios = scenarios - scenariosNotAllowed>
<#assign scenariosNotAllowed = 0>


<td>
<@renderStat stats "notAllowed" "failed"/>
</td>
<td>
${scenarios}
</td>
<td class="successful">
${scenariosSuccessful}
</td>
<td>
<@renderStat stats "scenariosPending" "pending"/> 
</td>
<td>
<@renderStat stats "scenariosFailed" "failed"/>
</td>
<td>
${scenariosNotAllowed}
</td>


<td>
<@renderStat stats "steps" />
</td>
<td>
<@renderStat stats "stepsSuccessful" "successful"/>
</td>
<td>
<@renderStat stats "stepsPending" "pending"/>
</td>
<td>
<@renderStat stats "stepsFailed" "failed"/>
</td>
<td>
<@renderStat stats "stepsNotPerformed" "notPerformed" />
</td>
<td>
<@renderStat stats "stepsIgnorable" "ignorable"/>
</td>
<td>
<@renderTime storyDurations.get('total')!0/>
</td>
</tr>
</table>
<br />
</div>

<div class="clear"></div>
<div id="footer">
<div class="left">Generated on ${date?string("dd/MM/yyyy HH:mm:ss")}</div>
<div class="right">Saas Web Automation &#169; 2014-2015</div>
<div class="clear"></div>
</div>

</body>

</html>