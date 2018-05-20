<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
	<head>
		<title>Unitoto</title>
		<%@include file="/common/jsp/include.jsp"%>
		<link rel="icon" href="${path}/common/css/img/U.png" type="image/x-icon" />
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
	<div class="panel-heading">
		<div class="col-md-4">
			<br>
			&nbsp;&nbsp;
			<img src="${path}/common/css/img/U.png" class="img-circle" style="height: 50px;">
			<h3 class="navbar-text" style="color: #000000;">Unitoto</h3>
			<h3 class="navbar-text" style="color: #000000;">|</h3>
		</div>
		<div class="col-md-4">
			<br>
			<br>
			<input type="text" class="form-control" placeholder="搜索" aria-describedby="basic-addon1">
			</div>
		<div class="col-md-4">
			<br>
			<br>
			<span class="glyphicon glyphicon glyphicon-user"></span>
			<a href="#" class="navbar-link" target="_parent"></a>
		</div>
	</div>
	<br>
	<br>
	<br>
	<br>
	<div class="container-fluid">
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