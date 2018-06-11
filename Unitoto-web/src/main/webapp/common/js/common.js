$.ajaxSetup ({
   cache: false //关闭AJAX相应的缓存
});
function func_sys_error(result,options){
	if(!result.status&&result.code){
		switch(result.code){
			case 'noLogin':
				$.messager.alert('提示','您未登录或者登录已经超时！请重新登录！','warning',function(){
					window.top.location=basePath+'/';
				});
				return false;
			case 'noAuthority':
				$.messager.alert('提示','您没有权限进行该操作！','warning');
				$.messager.progress('close');
				return false;
			case 'tokenInvalid':
				$.messager.alert('提示','登录已经失效！请重新登录！','warning',function(){
					window.top.location=basePath+'/';
				});
				return false;
			case 'poll':
				$.ajax(options);
				return false;
			case 'error':
				$.messager.alert('提示',result.message,'error');
				$.messager.progress('close');
				return false;
		}
	}
	return true;
}
var $ajax=$.ajax;
$.ajax=function(options){
	if(!options.error){
		options.error=function(XMLHttpRequest, textStatus, errorThrown){
			$.messager.alert('提示','操作出现错误！','warning');
			$.messager.progress('close');
		};
	}
	if(!options.dataType){
		options.dataType='json';
	}
	var oldCallback=options.success;
	if(oldCallback){
		options.success=function(result){
			if(result==null){
				return;
			}
			if(options.dataType!='json'){
				oldCallback(result);
				return;
			}
			if(!func_sys_error(result,options)){
				return;
			}
			if(result.code){
				oldCallback(result);
			}else if(result.status){
				oldCallback(result.data);
			}else{
				$.messager.progress('close');
				$.messager.alert('提示',result.message||'操作出现错误！','warning');
			}
		};
	}
	var data=options.data;
	if(options.type=='get'&&data){
		for(var p in data){
			if(!data[p]){
				delete data[p];
			}
		}
	}
	$ajax(options);
};
String.prototype.lenB=function(){return this.replace(/[^\x00-\xff]/g,"**").length;};
var message_cn={email:"邮箱格式不正确.",length:"长度必须为{0}-{1}个字符.",url:"不是有效的url地址."};var rules=$.fn.validatebox.defaults.rules;for(var p in message_cn){rules[p].message=message_cn[p]}rules.maxLength={message:"长度不能超过{0}个字符.",validator:function(value,param){return value.lenB()<=param[0]}};rules.minLength={message:"长度不能少于{0}个字符.",validator:function(value,param){return value.lenB()>=param[0]}};
var main_tab={
	get:function(){
		return window.top.$('#centerTab');
	},
	add:function(title,url,icon,closable){
		var $tab=this.get();
		var $created=$tab.tabs('getTab',title);
		if($created){
			$tab.tabs('select',title);
			$tab.tabs('update',{tab: $created,options:{title:title,content:'<iframe src="'+basePath+url+'" frameborder="0" style="border:0px; height:100%; width: 100%;"></iframe>'}});
			return;
		}
		$tab.tabs('add',{
			title:title,
			closable:closable==undefined?true:false,
			cls:"tab-no-overflow",
			iconCls:icon||'icon-reload',
			content:'<iframe src="'+basePath+url+'" frameborder="0" style="border:0px; height:100%; width: 100%;"></iframe>'
		}).tabs('getTab',title).data('url',url);
		var index=$tab.tabs('getTabIndex',$tab.tabs('getSelected'));
		var $span=$tab.children(':first').find('.tabs').children('li').eq(index).find('.tabs-icon');
		$span.css('cursor','pointer');
		var _this=this;
		$span.bind('click',function(){
			_this.add(title, url);
		});
	},
	getUrl:function(){
		return this.get().tabs('getSelected').data('url');
	},
	close:function(){
		var $tab=this.get();
		$tab.tabs('close',$tab.tabs('getSelected').panel('options').title);
	},
	refresh:function(){
		var $tab=this.get();
		var $selected=$tab.tabs('getSelected');
		var content=$selected.panel('options').content;
		$tab.tabs('update',{tab: $selected,options:{content:content}});
	},
	change:function(title,url){
		var $tab=this.get(),old=$tab.tabs('getSelected').panel('options').title;
		this.add(title,url);
		$tab.tabs('close',old);
	},
	closeAll:function(){
		var $tab=this.get();
		var tabs=$tab.tabs('tabs');
		for(var i=tabs.length-1;i>0;i--){
			$tab.tabs('close',tabs[i].panel('options').title);
		}
	}
}

var main_user_menu=null;

var global_paging_num=10;

function initPaging($table){$table.datagrid("getPager").pagination({pageSize:global_paging_num,pageList:[5,10,15,20],beforePageText:"第",afterPageText:"页    共 {pages} 页",displayMsg:"当前显示 {from} - {to} 条记录    共 {total} 条记录"})}function initTable($table,url){$table.datagrid({pageSize:global_paging_num,url:url,onLoadSuccess:function(){$table.datagrid("tooltip")}});initPaging($table)};

function edit_callback(isAdd,page_url){if(!isAdd){window.parent.operate_callback();return}if(main_tab.getUrl()==page_url){window.parent.operate_callback()}else{if(main_user_menu==null){main_user_menu=window.top.main_user_menu}var menu=main_user_menu[page_url];if(menu){main_tab.change(menu,page_url)}else{main_tab.refresh()}}};

var Combo={filter:function(data){if(!data){return[]}if(!data.length||!data[0].length){return data}for(var i=0;i<data.length;i++){var row=data[i];data[i]={value:row[0],text:row[0]+":"+row[1]}}return data},hide:function(){var $this=$(this),value=$this.combobox("getValue"),opts=$this.combobox("options");if(!value){if(opts.onSelect){opts.onSelect.call(this,{})}return}if(opts.extSelect){delete opts.extSelect;return}var data=$this.combobox("getData");if(!data.length){return}$this.combobox("clear");for(var i=0;i<data.length;i++){if(data[i].value==value){$this.combobox("select",value);delete opts.extSelect;return}}}};
$.fn.serializeObject=function(){var o={};var a=this.serializeArray();$.each(a,function(){if(o[this.name]!==undefined){if(!o[this.name].push){o[this.name]=[o[this.name]]}o[this.name].push(this.value||"")}else{o[this.name]=this.value||""}});return o};
$.fn.validatebox.defaults.rules["number"]={validator:function(value,param){return new RegExp("^\\d{1,"+param[0]+"}(\\.\\d{1,"+param[1]+"})?$").test(value)},message:"格式错误，必须是整数位少于{0}位，小数位少于{1}位的数字"};$.fn.validatebox.defaults.rules["int"]={validator:function(value,param){return new RegExp("^-?\\d{1,"+param[0]+"}$").test(value)},message:"格式错误，必须是少于{0}位的整数"};$.fn.validatebox.defaults.rules["intL"]={validator:function(value,param){return/^\d+$/.test(value)&&value.length==param[0]},message:"格式错误，必须是{0}位的数字"};
$.fn.validatebox.defaults.rules["str"] = {
	validator : function(value, param) {
		return (!/[\u4e00-\u9fa5]/.test(value)) && value.lenB() <= param[0]
	},
	message : "格式错误，必须是少于{0}位的非中文字符"
};
$.fn.validatebox.defaults.rules["strL"] = {
	validator : function(value, param) {
		return (!/[\u4e00-\u9fa5]/.test(value)) && value.lenB() == param[0]
	},
	message : "格式错误，必须是{0}位的非中文字符"
};
function open_declare_win(win,options,isParent){var _target=isParent?window.parent:window;if(!_target[win]){_target.$.messager.progress();_target.$.ajax({url:basePath+"/common/js/win/"+win+".js",cache:true,dataType:"script",success:function(value){_target.$.messager.progress("close");_target[win].open(options)}})}else{_target[win].open(options)}};
function ft_time_param(v,s){if(!v){return null}return v+(s?" 00:00:00":" 23:59:59")};
function validate_time_sd(s,e){s.datebox({onHidePanel:function(){var value=s.datebox("getValue");if(!value){return}var date=$.fn.datebox.defaults.parser(value).getTime();var end=e.datebox("getValue");if(end){endDate=$.fn.datebox.defaults.parser(end).getTime();if(endDate<date){$.messager.alert("提示","开始时间不能大于结束时间","info");s.datebox("clear");return}}}});e.datebox({onHidePanel:function(){var value=e.datebox("getValue");if(!value){return}var date=$.fn.datebox.defaults.parser(value).getTime();var start=s.datebox("getValue");if(start){startDate=$.fn.datebox.defaults.parser(start).getTime();if(date<startDate){$.messager.alert("提示","结束时间不能小于开始时间","info");e.datebox("clear");return}}}})};
function bindChangeFocus($textbox){
	$(document).bind('keydown',function(event){
		if(event.keyCode==9){
			var active=document.activeElement,next,len=$textbox.length,match=!active||!$(active).hasClass('textbox-text');
			for(var i=0;i<len;i++){
				if(match){
					next=$textbox.eq(i);
					var el=next.parents('.textbox:eq(0)').prev('input');
					if(el.textbox('options').disabled||el.textbox('options').readonly){
						continue;
					}
					break;
				}
				if(active==$textbox[i]){
					match=true;
				}
			}	
			if(next){
				next.focus();
			}
			return false;
		}
	});
}