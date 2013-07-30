1、配置文件位置：searcher\src\main\webapp\WEB-INF\conf\config.xml
2、配置项abstractLength表示搜索结果中摘要的长度，默认为200
3、indexBasepath表示索引的位置
4、searcher\src\main\webapp\WEB-INF\conf\application.properties为数据库配置文件
5、部署后通过：http://[IP]:[端口]/searcher/system/buildIndex?fullFlag=1 创建全量索引
6、部署后的访问路径：http://[IP]:[端口]/searcher/search/detailSearch?query=吉林