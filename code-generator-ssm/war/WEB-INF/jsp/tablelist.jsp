<!DOCTYPE html>
<%@page import="assist.Template"%>
<%@page import="db.IDType"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<% String basePath=request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath()+"/";%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
<base href="<%=basePath%>"/>
<title>数据库操作--导出</title>
<link rel="stylesheet" style="text/css" href="js/easyui/themes/default/easyui.css"/>
<link rel="stylesheet" style="text/css" href="js/easyui/themes/icon.css"/>
<script type="text/javascript" src="js/easyui/jquery.min.js"></script>
<script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="js/easyui/locale/easyui-lang-zh_CN.js"></script>
<style type="text/css">
.comm { width:150px; }
table { border:1px solid #95BDD7;border-collapse:collapse;font:normal 13px/1 Verdana,sans-serif;margin:10px auto;width:90%; }
table th { border:1px solid #95BDD7;background:#EFF0F2;color:#1F83B7;text-align:center;font-size:15px;font-weight:bold;}
table td { border:1px solid #95BDD7;color:#000;text-align:left;}
table td > label { display:inline-block;width:90%;padding:10px 5px; }
table td:first-child { text-align: center; }
</style>
<script type="text/javascript">
$(function() {
	$('#allCheck').change(function() {
		$('tbody :checkbox').prop('checked', $(this).is(':checked'));
	});
	$('input[name=domain]').change(function() {
		$('tbody span[title=domain]').text($(this).val());
	});
	$('input[name=project]').change(function() {
		$('tbody span[title=project]').text($(this).val());
	});
	$('.exportBtn').click(function() {
		var checkeds = $('tbody :checkbox:checked');
		if (checkeds.size() == 0) {alert('请选择待导出表!');return;}
		var checkedRows = checkeds.closest('tr'), expr = '[name=moduleName],[name=menuName]';
		checkedRows.find( expr ).validatebox({required:true});
		var otherRows = checkedRows.siblings().not(checkedRows);
		var otherMMs = otherRows.find( expr );
		otherMMs.validatebox({required:false});
		otherMMs.prop('disabled', true);
		if ($('#editForm').form('validate')) {
			$('#editForm').submit();
		}
		otherMMs.prop('disabled', false);
	});
});
</script>
</head>
<body>
	<form name="editForm" id="editForm" action="export.do" method="post">
		<table>
			<thead>
				<tr>
					<th colspan="2">导出路径:<input type="text" name="storePath" value="${exportInfo.storePath}" class="easyui-validatebox" data-options="required:true"/></th>
					<th colspan="2">
						域名:<input type="text" name="domain" value="${exportInfo.domain}" class="easyui-validatebox" data-options="required:true" style="width:120px;"/>
						项目名称:<input type="text" name="project" value="${exportInfo.project}" class="easyui-validatebox" data-options="required:true" style="width:80px;"/>
						模板:<select name="template"><c:forEach items="<%=Template.values()%>" var="vo"><option value="${vo}">${vo}</option></c:forEach></select>
					</th>
					<th><label><input type="checkbox" name="daoMerge" value="true" checked="checked"/>合并DAO层(默认)</label></th>
					<th>Entity:
						<select name="idType">
						<c:forEach items="<%=IDType.values()%>" var="vo"><option value="${vo}">${vo.className}</option></c:forEach>
						</select>
					</th>
				</tr>
				<tr>
					<th width="5%"><label style="display:inline-block;width:90%;"><input type="checkbox" id="allCheck"/></label></th>
					<th width="20%">表名(共<span style="color:red;">${fn:length(tableList)}</span>个表)</th>
					<th width="20%">表描述信息</th>
					<th width="20%">包名前缀<a href="javascript:void(0)" class="easyui-linkbutton exportBtn" data-options="plain:true,iconCls:'icon-ok'">导出</a></th>
					<th width="15%">所属模块</th>
					<th width="20%">所属菜单</th>
				</tr>
				<tr><th colspan="6" style="color:#f00;padding:5px;">1.包名前缀(域名+项目名称)+所属模块构成Controller/Manager(及DAO层[若不合并])的包名;&nbsp;&nbsp;2.所属模块/所属菜单则构成JSP路径.</th></tr>
			</thead>
			<tbody>
				<c:forEach items="${tableList}" var="vo" varStatus="voStatus">
					<tr>
						<td><label><input type="checkbox" name="tableName" id="tableName${voStatus.count}" value="${vo.tableName}"/></label></td>
						<td><label for="tableName${voStatus.count}">${vo.tableName}</label></td>
						<td><label for="tableName${voStatus.count}">${vo.remarks}</label></td>
						<td>
							<span title="domain">${exportInfo.domain}</span>
							<span style="margin:none;padding:none;font-weight:bold;">.</span>
							<span title="project" style="color:blue;">${exportInfo.project}</span>
						</td>
						<td><input type="text" name="moduleName" class="easyui-validatebox" data-options="required:false" style="width:90%;"/></td>
						<td><input type="text" name="menuName" class="easyui-validatebox" data-options="required:false" style="width:90%;"/></td>
					</tr>
				</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="6">
						<a href="javascript:void(0)" class="easyui-linkbutton exportBtn" data-options="plain:true,iconCls:'icon-ok'">导出</a>
					</td>
				</tr>
			</tfoot>
		</table>
	</form>
</body>
</html>