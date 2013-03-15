String test:${stringKey}
dateKey test:${dateKey?string("yyyy-MM-dd HH:mm:ss")}
upperFC test:<@upperFC>${word}</@upperFC>
<#list testList as test>
${test}
</#list>
test number caculate：${3/2}
<#list ["Joe", "Fred"] + ["Julia", "Kate"] as user>
${user}
</#list>
<#assign ages = {"Joe":23, "Fred":25} + {"Joe":30, "Julia":18}>
- Joe is ${ages.Joe}
- Fred is ${ages.Fred}
- Julia is ${ages.Julia}

${"<test>"?html}
${"test"?cap_first}
${"TEST"?lower_case}
${"test"?upper_case}
${" test "?trim}
