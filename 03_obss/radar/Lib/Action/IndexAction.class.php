<?php
class IndexAction extends Action {
    public function index(){
	   $this->name = 'thinkphp'; // 进行模板变量赋值
       $this->display();
    }
}