2013-02-28 
1、本工程师freemarker的Demo项目，展示freemarker的用法。
2、官方网站：http://freemarker.org/
3、语法说明：
A、<#include "/copyright_footer.html"> 可以引入文件
B、${user!"Anonymous"} 为user变量赋予默认值
C、<#if user??><h1>Welcome ${user}!</h1></#if> 当user变量不存在的话，后面的将忽略
D、${3/2} 结果是1.5，而不是1
E、<#if price==0>
G、<#-- Greet the user with his/her name --> 注释写法，可以放在任何位置，包括行内
 
