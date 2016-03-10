<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html ng-app="customerModule">
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="description" content="">
<meta name="author" content="William Hsiao">

<style>
.smallProfile {
	text-align: center;
	width: 42px;
	height: 42px;
	border: 3px solid #eeeeee;
	border-radius: 100%;
	margin-right: 7px; /*space between*/
	margin-bottom: 40px;
	background-color: #eeeeee;
	margin-left: -8px;
} 
.Profile {
	text-align: center;
	width: 112px;
	height: 112px;
	border: 10px solid #d8d8d8;
	border-radius: 100%;
	margin-right: 7px; /*space between*/
	margin-bottom: 10px;
	background-color: #d8d8d8;
} 
.imtip {
	position: absolute;
	bottom: -36px;
	right: 48px;
}
.imtip0 {
	position: absolute;
	bottom: -48px;
	right: 80px;
}

.panel-heading {
	background-color: #e6e6e6;
}
</style>
<link href="http://netdna.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css" rel="stylesheet">
<link rel="stylesheet" href="https://rawgit.com/esvit/ng-table/master/dist/ng-table.min.css">

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/twitter-bootstrap/2.3.2/js/bootstrap.min.js"></script>

<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.7/angular.min.js"></script>
<script src="http://angular-ui.github.io/bootstrap/ui-bootstrap-tpls-0.14.3.js"></script>
<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.4.7/angular-animate.js"></script>

<!--script src="/js/vendor.js"></script-->

<script>
var _contextPath = "<%=request.getContextPath()%>";

angular.module("customerModule", ["ui.bootstrap", "ngAnimate"]); 

angular.module('customerModule').controller('ModalInstanceCtrl', ['$scope', '$uibModalInstance', 'errCode', 'errMsg', 
    function ($scope, $uibModalInstance, errCode, errMsg) {
		$scope.errCode = errCode;
		$scope.errMsg = errMsg;
		$scope.reload = function () {
			$uibModalInstance.dismiss('ok');
			window.location.reload();
		};
		$scope.cancel = function () {
			$uibModalInstance.dismiss('cancel');
		};
	}
]);

angular.module("customerModule").controller("customerController", ['$scope', '$http', '$uibModal',
		
	function($scope, $http, $uibModal) {
	
		$scope.noneStyle = false;
		$scope.bodyCon = false;

		$scope.toggleStyle = function () {
			$scope.bodyCon = !$scope.bodyCon;
			$scope.noneStyle = !$scope.noneStyle;
		};
	
		var reloadPrompt = function(code, xx) {
			var modalInstanceReload = $uibModal.open({
				templateUrl: 'modalContentReload.html',
				controller: 'ModalInstanceCtrl',
				resolve: {
					errCode: function () {return code},
					errMsg: function() { return ""}
				}
			});
		}
		
		var errPrompt = function(errCode, errMsg) {
			
			var modalInstance = $uibModal.open({
				templateUrl: 'modalContent.html',
				controller: 'ModalInstanceCtrl',
				resolve: {
					errCode: function () {
						return errCode;
					},
					errMsg: function () {
							return errMsg;
					}
				}
			});
		}
		
		var loadCustomers = function() {
			$http.get(_contextPath + '/customers' )
				.success(function(response, status, headers, config){
					$scope.customers = response.customers;	
				})
				.error(function(data, status, headers, config){
					errPrompt(data.errCode, data.errMsg);
				}
			);
		};

		$scope.sort = function(keyname){
			$scope.sortKey = keyname;	//set the sortKey to the param passed
			$scope.reverse = !$scope.reverse; //if true make it false and vice versa
		};
			

		$scope.restLink = function(row) {
			return _contextPath + '/Sales/Restaurant/'+row.restaurant.id+'/Profile';
		}
		
		$scope.readyCreateLink = function(row) {
			if (!!row.restaurant) return false;
			return true;
		}
		
		$scope.createLink = function(row) {
			return _contextPath + '/Sales/Restaurant/'+row.operatorId;
		}
			
		$scope.delCustomer = function(row) {
			$http['delete'](_contextPath + '/customer/profile' + row.id )
			.success(function(response, status, headers, config){
				reloadPrompt("Remove Success", "");				
			})
			.error(function(data, status, headers, config){
				errPrompt(data.errCode, data.errMsg);
			});
		};
		
		$scope.updCustomer = function(row) {
			
			var formData = {
				"name" : row.name,
				"address" : row.address,
				"phone": row.phone,
				"mail": row.email
			};
			var response;
			
			response = $http.put(_contextPath + '/customer/profile/' + row.id, formData);
			response.success(function(data, status, headers, config) {
				reloadPrompt("Update success", "");
			});
			response.error(function(data, status, headers, config) {
				errPrompt(data.errCode, data.errMsg);
			});	
		};

		$scope.insCustomer = function() {
			
			var formData = {
				"name" : $scope.profile.fullName,
				"address" : $scope.profile.passwd,
				"phone": $scope.profile.phone,
				"mail": $scope.profile.email
			};
			var response;
			
			response = $http.post(_contextPath + '/customer', formData);
			response.success(function(data, status, headers, config) {
				reloadPrompt("Create success", "");
			});
			response.error(function(data, status, headers, config) {
				errPrompt(data.errCode, data.errMsg);
			});	
		};
		
		loadCustomers();
	}
]);

</script>
</head>

<body>

	<div id="wrapper" ng-controller="customerController" ng-init="mode='bootstrap'">
		<script type="text/ng-template" id="modalContentReload.html">
			<div class="modal-header">
				<h3 class="modal-title">Success</h3>
			</div>
			<div class="modal-footer">
				<button class="btn btn-primary" type="button" ng-click="reload()">OK</button>
			</div>
		</script> 
		<script type="text/ng-template" id="modalContent.html">
			<div class="modal-header">
				<h3 class="modal-title">{{errCode}}</h3>
			</div>
				<div class="modal-body">
				<div ng-repeat="msg in errMsg">
					{{msg}}
				</div>
				</div>
			<div class="modal-footer">
				<button class="btn btn-primary" type="button" ng-click="cancel()">OK</button>
			</div>
		</script>

		<div id="page-wrapper" style="height:400px" >
			<div class="col-lg-12" >
				<div class="raw">
					<div class="panel panel-default">
						<div class="panel-heading">
							<img src="/image/app_name.png" width="48" align="right" style="margin-top:-4px;"><h4 style="font-weight:bold">基本資料</h4>
						</div>
					</div>
				</div>

				<div class="raw">
					<div class="panel panel-default" style="background-color: #e6e6e6;">
						<div class="panel-heading" style="background-color: #e6e6e6; padding-bottom:0px; border-bottom: 0px;">

							<table class="table table-striped table-hover">
								<tr>
									<form class="form-inline">
										<div class="form-group">
											<label >Search</label>
											<input type="text" ng-model="search" class="form-control" placeholder="Search">
										</div>
									</form>
 								</tr>
								<tr>
									<th ng-click="sort('name')">Name
										<span class="glyphicon sort-icon" ng-show="sortKey=='name'" ng-class="{'glyphicon-chevron-up':reverse,'glyphicon-chevron-down':!reverse}"></span>
									</th>
									<th ng-click="sort('address')">Address
										<span class="glyphicon sort-icon" ng-show="sortKey=='address'" ng-class="{'glyphicon-chevron-up':reverse,'glyphicon-chevron-down':!reverse}"></span>
									</th>
									<th ng-click="sort('phone')">Phone
										<span class="glyphicon sort-icon" ng-show="sortKey=='phone'" ng-class="{'glyphicon-chevron-up':reverse,'glyphicon-chevron-down':!reverse}"></span>
									</th>
									<th ng-click="sort('email')">email
										<span class="glyphicon sort-icon" ng-show="sortKey=='email'" ng-class="{'glyphicon-chevron-up':reverse,'glyphicon-chevron-down':!reverse}"></span>
									</th>
									<th></th>
								</tr>
								<tr ng-repeat="row in customers|orderBy:sortKey:reverse|filter:search">
									<td>{{row.name}}</td>
									<td>{{row.fullName}}</td>
									<td>{{row.phone}}</td>
									<td>{{row.phone}}</td>
									<td><img src="/image/block_user.png" width="20px" ng-click="blockSales(row)"/><img src="/image/block_user.png" width="20px" ng-click="blockSales(row)"/></td>
								</tr>
							</table> 
						</div>
					</div>
				</div>
				
				<div class="raw">
					<div class="panel panel-default" style="background-color: #e6e6e6;">
						<div class="panel-heading" style="background-color: #e6e6e6; padding-bottom:0px; border-bottom: 0px;">
							<div style="position: relative;display: inline;"><img src="/image/smallProfile.png" width="80px" class="Profile" /><img src="/image/contactPersonPhoto.png" class="imtip0" width="40px"/></div>
						</div>
						<div class="panel-body" style="margin-left:128px; margin-bottom:16px">
							<h4 style="font-weight:bold">建立 Customer</h4>
							<form name="newStoreOpForm" >
								<div style="margin-left:-24px"><img src="/image/email.png" width="20px" /><input required type="text" ng-model="profile.email" name="name" placeholder="email" size="28" style="margin-left:5px" /></div>
								<div style="margin-left:-24px"><img src="/image/contactPersonName.png" width="20px" /><input required type="text" ng-model="profile.fullName"  placeholder="聯絡人" size="28" style="margin-left:5px" /></div>
								<div style="margin-left:-24px"><img src="/image/contactPersonPhone.png" width="20px" /><input required type="text" ng-model="profile.phone" placeholder="聯絡電話"  size="28" style="margin-left:5px" /></div>
								<div style="margin-left:-24px"><img src="/image/contactPersonName.png" width="20px" /><input required type="password" ng-model="profile.passwd" placeholder="新密碼" ng-minlength="6" size="28" style="margin-left:5px" /></div>
								<div style="margin-left:-24px"><img src="/image/contactPersonName.png" width="20px" /><input required type="password" ng-model="profile.passwd2" placeholder="確認新密碼" ng-minlength="6" size="28" style="margin-left:5px" /></div>
								<div style="display:inline-block;float:right">
								<button class="btn btn-primary pull-right btn-sm" type="button" ng-disabled="newStoreOpForm.$invalid" ng-click="postAccount()">建立</button></div>
								<!--input type="submit" id="submit" value="建立"  style="font-size:20px"/></div-->
							</form>
						</div>								
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
