2013-05-08
1、Tag中定义的返回值：
    SKIP_BODY--不处理标签体，直接调用doEndTag()方法
    EVAL_BODY_INCLUDE--解析标签体,但绕过 doInitBody () 和 setBodyContent () 方法
    SKIP_PAGE--不解析标签后面的JSP内容
    EVAL_PAGE--解析标签后,继续解析标签后面的JSP内容