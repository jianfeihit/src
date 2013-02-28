String test:${stringKey}
dateKey test:${dateKey?string("yyyy-MM-dd HH:mm:ss")}
upperFC test:<@upperFC>${word}</@upperFC>
<#list testList as test>
${test}
</#list>
