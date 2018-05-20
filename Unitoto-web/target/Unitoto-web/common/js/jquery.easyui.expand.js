$.extend($.fn.datagrid.methods, {
	tooltip : function (jq, fields) {
		return jq.each(function () {
			var panel = $(this).datagrid('getPanel');
			if (fields && typeof fields == 'object' && fields.sort) {
				$.each(fields, function () {
					var field = this;
					bindEvent($('.datagrid-body td[field=' + field + '] .datagrid-cell', panel));
				});
			} else {
				bindEvent($(".datagrid-body .datagrid-cell", panel));
			}
		});

		function bindEvent(jqs) {
			jqs.mouseover(function () {
				var content = $(this).text();
				if(!content){
					return;
				}
				$(this).tooltip({
					content : '<span style="color:#fff">'+content+'</span>',
					trackMouse : true,
					onHide : function () {
						$(this).tooltip('destroy');
					},
					onShow: function(){
						$(this).tooltip('tip').css({
							backgroundColor: '#666',
							borderColor: '#666'
						});
				    }
				}).tooltip('show');
			});
		}
	}
});
$.extend($.fn.combobox.methods, {
	selectedIndex : function(jq, index) {
		var options=$(jq).combobox('options');
		if(!options.data){
			return;
		}
		$(jq).combobox('setValue', options.data[index][options.valueField]);
	}
});