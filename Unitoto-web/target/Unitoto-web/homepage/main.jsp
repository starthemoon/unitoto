<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>小船大数据分析与服务平台</title>
		<%@include file="/common/jsp/include.jsp"%>
		<link rel="icon" href="${path}/common/css/img/ship.ico" type="image/x-icon" />
		<link rel="stylesheet" type="text/css" href="${path}/common/css/bootstrap/bootstrap-datetimepicker.min.css">
		<link rel="stylesheet" type="text/css" href="${path}/common/css/bootstrap/bootstrap-table.min.css">
		<script type="text/javascript" src="${path}/common/js/bootstrap-datetimepicker.min.js"></script>
		<script type="text/javascript" src="${path}/common/js/bootstrap-datetimepicker.zh-CN.js"></script>
		<script type="text/javascript" src="${path}/common/js/bootstrap-paginator.js"></script>
		<script type="text/javascript" src="${path}/common/SuperMap/libs/SuperMap.Include.js"></script>
		<script type="text/javascript" src="${path}/common/js/json2.js"></script>
		<script type="text/javascript" src="${path}/common/js/bootstrap-table.min.js"></script>
		<script type="text/javascript" src="${path}/common/js/bootstrap-table-locale-all.min.js"></script>
	</head>

	<body>
	<nav class="navbar navbar-inverse navbar-fixed-top">
		<div class="container-fluid">
			<div class="navbar-header">
				<h4 class="navbar-text" style="color: #FFFFFF;">小船大数据分析与服务平台</h4>
				<p class="navbar-text" style="color: #FFFFFF;">停航警示分布图</p>
			</div>
			<p class="navbar-text pull-right">
				<span class="glyphicon glyphicon glyphicon-user"></span>
				<a href="#" class="navbar-link" target="_parent">admin</a>
			</p>
		</div>
	</nav>
	<br>
	<br>
	<br>
	<br>
	<div class="container-fluid">
		<div class="row-fluid">
			<div class="col-md-3 col-xs-4">
				<h5 style="float: left;">起始日期：</h5>
				<div id="startDate" class="input-group date" data-picker-position="bottom-left">
					<input id="startDateText" type="text" class="form-control" readonly />
					<span class="input-group-addon">
                    		<span class="glyphicon glyphicon-calendar"></span>
                		</span>
				</div>
			</div>
			<div class="col-md-3 col-xs-4">
				<h5 style="float: left;">结束日期：</h5>
				<div id="endDate" class="input-group date" data-picker-position="bottom-left">
					<input id="endDateText" type="text" class="form-control" readonly />
					<span class="input-group-addon">
                    		<span class="glyphicon glyphicon-calendar"></span>
                		</span>
				</div>
			</div>
			<div class="col-md-6 col-xs-4">
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				<button id="analyse" type="button" class="btn btn-info" onclick="useJSON()">分析</button>
				<button id="query" type="button" class="btn btn-primary" onclick="paging()">区域查询</button>
				<div class="modal fade" id="waitModal" tabindex="-1" role="dialog" data-keyboard="false" data-backdrop="static">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h4>信息提示</h4>
							</div>
							<div class="modal-body">
								<p id="querying">查询中...</p>
							</div>
						</div>
					</div>
				</div>
				<div class="modal fade" id="wrongModal" tabindex="-1" role="dialog" data-keyboard="false" data-backdrop="static">
					<div class="modal-dialog" role="document">
						<div class="modal-content">
							<div class="modal-header">
								<h4>信息提示</h4>
							</div>
							<div class="modal-body">
								<p>查询的时间段不正确，请重新输入...</p>
							</div>
							<div class="modal-footer">
								<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<br>
		<br>
		<br>
		<div class="row-fluid">
			<div class="col-md-9 col-xs-12">
				<div id="map" class="row" style="width:900px; height:590px; position: absolute; z-index: 500;"></div>
			</div>
			<div class="col-md-3 col-xs-12">
				<table id="infoTable" class="table table-bordered"></table>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="${path}/homepage/js/main.js" onload="init()"></script>
    </body>
</html>