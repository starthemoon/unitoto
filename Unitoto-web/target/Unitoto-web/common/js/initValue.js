var InitValue={
	get:function(id,names){
		var elementData={};
		$('#'+id).find("input").each(function(){
			if(InitValue.match(names, this)){
				return;
			}
			InitValue.add(this,elementData);
		});
		$('#'+id).find("select").each(function(){
			if(InitValue.match(names, this)){
				return;
			}
			InitValue.add(this,elementData);
		});
		return elementData;
	},
	match:function(names,o){
		if(!names){
			return false;
		}
		for(var i=0;i<names.length;i++){
			if(names[i]==o.name){
				return true;
			}
		}
		return false;
	},
	add:function(o,elementData){
		var $this=$(o),type,css=$this.attr('class')
		if(!css){
			return;
		}
		var s=css.indexOf('easyui-');
		if(s!=-1){
			e=css.indexOf(' ',s+7);
			if(e==-1){
				e=css.length;
			}
			type=css.substring(s+7,e);
			if(!elementData[type]){
				elementData[type]=[];
			}
			elementData[type].push([$this,$this.attr('name')]);
		}
	},
	init:function(elementData,data){
		for(var e in elementData){
			for(var i=0;i<elementData[e].length;i++){
				var row=elementData[e][i],value=data[row[1]];
				var options=row[0][e]('options');
				if(!options.valueInit){
					options.valueInit=1;
					options.originalValue=value;
				}
				if(value){
					row[0][e]($.isArray(value)?'setValues':'setValue',value);
				}else{
					row[0][e]('clear');
				}
			}
		}
	},
	changeState:function(elementData,disable){
		for(var e in elementData){
			for(var i=0;i<elementData[e].length;i++){
				var row=elementData[e][i];
				row[0][e](disable?'disable':'enable')[e]('options').disable=disable;
				if(disable&&e=='combobox'){
					row[0][e]('hidePanel');
				}
			}
		}
	},
	changeAndClear:function(elementData,names,readonly){
		for(var e in elementData){
			for(var i=0;i<elementData[e].length;i++){
				var row=elementData[e][i],name=row[0].attr('textboxname');
				if(this.index(names, name)==-1){
					continue;
				}
				row[0][e]('readonly',readonly).next().children('input:eq(0)').css('background-color',readonly?'#ebebe4':'');
				if(readonly&&e=='combobox'){
					row[0][e]('hidePanel');
				}
				if(readonly){
					row[0][e]('clear');
				}
			}
		}
	},
	index:function(names,value){
		for(var i=0;i<names.length;i++){
			if(names[i]==value){
				return i;
			}
		}
		return -1;
	}
}