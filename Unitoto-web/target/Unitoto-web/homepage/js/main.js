$(function(){
/*------------------------日期----------------------------*/
	var nowDate = new Date();
	var eDate = nowDate;
	var sYear = nowDate.getFullYear();
	var sMonth = nowDate.getMonth() + 1;
	if (sMonth < 10) {
		var sDate = sYear + "-0" + sMonth + "-01";
	} else {
		var sDate = sYear + "-" + sMonth + "-01";
	}
	
    $('#startDate').datetimepicker({
    	format:"yyyy-mm-dd",
    	autoclose: true,
    	todayBtn: true,
    	todayHighlight: true,
    	keyboardNavigation: true,
    	showMeridian: true,
    	language: 'zh-CN',
    	startView: 2,
    	minView: 2
    });

    $('#endDate').datetimepicker({
    	format:"yyyy-mm-dd",
    	autoclose: true,
    	todayBtn: true,
    	todayHighlight: true,
    	keyboardNavigation: true,
    	showMeridian: true,
    	language: 'zh-CN',
    	startView: 2,
    	minView: 2
    });

    $('#startDate input').val(sDate);
    $('#endDate input').val(eDate.format("yyyy-MM-dd"));
    
/*--------------------使用模态框-------------------------*/
    $('#waitModal').on('show.bs.modal', function (e) {
    		setInterval("change()", "800");
	});
    
});

/*--------------------使用模态框-------------------------*/
var c = 1;
function change() {
	var temp = c % 3;
	switch (temp) {
	case 0:
		$('#querying').text("查询中...");
		break;
	case 1:
		$('#querying').text("查询中......");
		break;
	case 2:
		$('#querying').text("查询中.........");
		break;
	default:
		break;
	}
	c++;
}

/*------------------------地图----------------------------*/
var map, layer;
var isTransform = true; //是否需要进行坐标转换
var sourceProjection = 'EPSG:3857';
var destProjection = 'EPSG:4326';
var url = "http://172.16.16.30:8090/iserver/services/map-mbtiles-GuangDong17/rest/maps/广东17";

/**
 * 初始化地图
 */
function init() {
	map = new SuperMap.Map("map",{controls:[
		new SuperMap.Control.Navigation(),
		new SuperMap.Control.LayerSwitcher(),
		new SuperMap.Control.PanZoomBar()
		]});
	layer= new SuperMap.Layer.TiledDynamicRESTLayer("水域地图", url, null,{maxResolution:"auto"});
	vector = new SuperMap.Layer.Vector("vector");
	layer.events.on({"layerInitialized":addLayer});
}

/**
 * 描绘格子
 */
function addData(data) {
    transformPoint(data,"leftLong","leftLat");
    transformPoint(data,"rightLong","rightLat");
    var x = data["leftLong"];
    var y = data["rightLat"];
    var w = data["rightLong"] - data["leftLong"];
    var h = data["leftLat"] - data["rightLat"];
    var count = data["gpsCount"];
    if (count >= 100) {
        var style = {
    		fillColor:"#FF0000",
    		strokeColor:"#FF0000"
        }
    } else if (count >= 50) {
        var style = {
    		fillColor:"#FF4500",
    		strokeColor:"#FF4500"
        }
    } else if (count >= 25) {
        var style = {
    		fillColor:"#FFB6C1",
    		strokeColor:"#FFB6C1"
        }
    } else if (count >= 10) {
        var style = {
    		fillColor:"#FFA07A",
    		strokeColor:"#FFA07A"
        }
    } else {
        var style = {
    		fillColor:"#FFDEAD",
    		strokeColor:"#FFDEAD"
        }
    }
    var rectangle = new SuperMap.Geometry.Rectangle(x, y, w, h);
    var rectangleVector = new SuperMap.Feature.Vector(rectangle, null, style);
    vector.addFeatures(rectangleVector);
}

/**
 * 添加图层
 */
function addLayer() {
	map.addLayers([layer, vector]);
    //出图，map.setCenter函数显示地图
	var centerLonLat = transform(new SuperMap.LonLat(113.52029, 22.99245));
	map.setCenter(centerLonLat, 6);
}

/**
 * 清空图层和vector
 */
function clearFeatures() {
	map.removeLayer(layer);
	map.removeLayer(vector);
	layer= new SuperMap.Layer.TiledDynamicRESTLayer("水域地图", url, null,{maxResolution:"auto"});
	layer.events.on({"layerInitialized":addLayer});
	vector = new SuperMap.Layer.Vector("vector");
}

/**
 * 坐标转换 
 */
function transform(lonLat) {
    if (!isTransform) {
        return lonLat;
    }

    if (lonLat && lonLat.CLASS_NAME && (lonLat.CLASS_NAME == 'SuperMap.LonLat' || lonLat.CLASS_NAME == 'SuperMap.Geometry.Point')) {
        //坐标转换
        lonLat.transform(destProjection, sourceProjection);
        return lonLat;
    }
    //throw new Error("not a valid SuperMap.LonLat or SuperMap.Geometry.Point object");
}

/**
 * 点对象坐标转换
 * @param data
 * @param xf
 * @param yf
 * @returns
 */
function transformPoint(data,xf,yf){
	var point = new SuperMap.LonLat(data[xf],data[yf]);
	//坐标转换
    point.transform(destProjection, sourceProjection);
	//console.log(point);
	data[xf]=point.lon;
	data[yf]=point.lat;
	return data;
}

/*--------------------使用JSON文件-------------------------*/
function useJSON() {
	if ($('#startDateText').val() > $('#endDateText').val()) {
		$('#wrongModal').modal('show');
	} else {
		$('#waitModal').modal('show');
		$.ajax({
	        type: "post",
	        url: "homepage/generateJSON.do",
	        dataType: "text",
	        data:
	        {
	             "startDateText": $('#startDateText').val(),
	             "endDateText": $('#endDateText').val()
	        },
	        success: function(path) {
	        	clearFeatures();
	        	$.getJSON(path, function(data) {
	    			$.each(data, function(i, item) {
	    				addData(item);
	    			});
	    		});
	        	$('#waitModal').modal('hide');
			}
	    });
	}
}

/*------------------------分页----------------------------*/
function paging() {
	if ($('#startDateText').val() > $('#endDateText').val()) {
		$('#wrongModal').modal('show');
	} else {
		var $table = $('#infoTable');
	    $table.bootstrapTable('destroy');
	    $table.bootstrapTable({
	       url: "homepage/getShip.do",
	       method: "post",
	       contentType: "application/x-www-form-urlencoded",
	       queryParams: queryParams,
	       
	       locale: 'zh-CN',
	       pagination: true,
	       pageNumber: 1,
	       pageSize: 10,
	       pageList: [10, 20, 30],
	       sidePagination: "client",
	       paginationPreText: "上一页",
	       paginationNextText: "下一页",
	       rowStyle: function () {
	           var style = "text-align:left";
	           return { classes: style }
	       },
	       
	       columns: [
	        {field:'ship_ID', title:'船编', width:'110px'}, 
	        {field:'ship_NAME_CN', title:'中文船名', width:'60px'},
	        {field:'ship_NAME_EN', title:'英文船名', width:'60px'}
	       ]
	   });
	}
}

function queryParams(params) {
	var temp = {
			pageSize: params.limit,
		    pageNumber: params.pageNumber,
			startDate: $('#startDateText').val(),
			endDate: $('#endDateText').val()
	};
	return temp;
}

/*------------------------日期----------------------------*/
Date.prototype.format = function(fmt) { 
    var o = { 
       "M+" : this.getMonth()+1,                 //月份 
       "d+" : this.getDate(),                    //日 
       "h+" : this.getHours(),                   //小时 
       "m+" : this.getMinutes(),                 //分 
       "s+" : this.getSeconds(),                 //秒 
       "q+" : Math.floor((this.getMonth()+3)/3), //季度 
       "S"  : this.getMilliseconds()             //毫秒 
   }; 
   if(/(y+)/.test(fmt)) {
           fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length)); 
   }
    for(var k in o) {
       if(new RegExp("("+ k +")").test(fmt)){
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
        }
    }
   return fmt; 
}
