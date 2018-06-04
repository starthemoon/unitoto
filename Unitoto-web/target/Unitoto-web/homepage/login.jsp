<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Unitoto</title>
<%@include file="/common/jsp/include.jsp"%>
<link rel="icon" href="${path}/common/css/img/U.png" type="image/x-icon" />
<link rel="stylesheet" type="text/css"
	href="${path}/common/css/bootstrap/bootstrap-datetimepicker.min.css">
<link rel="stylesheet" type="text/css"
	href="${path}/common/css/bootstrap/bootstrap-table.min.css">
<script type="text/javascript"
	src="${path}/common/js/bootstrap-datetimepicker.min.js"></script>
<script type="text/javascript"
	src="${path}/common/js/bootstrap-datetimepicker.zh-CN.js"></script>
<script type="text/javascript"
	src="${path}/common/js/bootstrap-paginator.js"></script>
<script type="text/javascript"
	src="${path}/common/SuperMap/libs/SuperMap.Include.js"></script>
<script type="text/javascript" src="${path}/common/js/json2.js"></script>
<script type="text/javascript"
	src="${path}/common/js/bootstrap-table.min.js"></script>
<script type="text/javascript"
	src="${path}/common/js/bootstrap-table-locale-all.min.js"></script>
</head>

<body>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<div class="row">
		<div class="col-md-1"></div>
		<div class="col-md-5">
			<img src="${path}/common/css/img/TroyeSivan.jpeg"
				class="img-responsive" alt="Responsive image">
		</div>
		<div class="col-md-4">
			<div class="panel panel-default">
				<img src="${path}/common/css/img/Unitoto.png"
					class="img-responsive center-block" alt="Responsive image">
				<div class="panel-body">
					<form>
						<div class="form-group">
							<input id="userID" type="text" class="form-control"
								placeholder="账号" aria-describedby="basic-addon1">
						</div>
						<div class="form-group">
							<input id="userPassword" type="text" class="form-control"
								placeholder="密码" aria-describedby="basic-addon1">
						</div>
						<div class="btn-group btn-group-justified" role="group">
							<div class="btn-group" role="group">
								<button type="button" class="btn btn-default" onclick="login()">登录</button>
							</div>
							<div class="btn-group" role="group">
								<button type="button" class="btn btn-primary"
									onclick="register()">注册</button>
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<div class="col-md-2"></div>
	</div>
	<script type="text/javascript" src="${path}/homepage/js/login.js"
		onload="init()"></script>
</body>
</html>
