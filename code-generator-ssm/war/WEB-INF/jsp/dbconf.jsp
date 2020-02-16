<!DOCTYPE html>
<%@page import="db.DBType" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<% String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html;charset=UTF-8"/>
    <base href="<%=basePath%>"/>
    <title>数据库连接--配置</title>
    <link rel="stylesheet" style="text/css" href="js/easyui/themes/default/easyui.css"/>
    <link rel="stylesheet" style="text/css" href="js/easyui/themes/icon.css"/>
    <script type="text/javascript" src="js/easyui/jquery.min.js"></script>
    <script type="text/javascript" src="js/easyui/jquery.easyui.min.js"></script>
    <script type="text/javascript" src="js/easyui/locale/easyui-lang-zh_CN.js"></script>
    <style type="text/css">
        .comm {
            width: 150px;
        }

        table {
            border: 1px solid #95BDD7;
            border-collapse: collapse;
            font: normal 13px/1 Verdana, sans-serif;
            margin: 30px auto;
            width: 600px;
        }

        table th {
            border: 1px solid #95BDD7;
            background: #EFF0F2;
            color: #1F83B7;
            text-align: right;
            height: 25px;
        }

        table td {
            border: 1px solid #95BDD7;
            color: #1F83B7;
            text-align: left;
            padding-left: 5px;
        }
    </style>
    <script type="text/javascript">
        $(function () {
            $('#dbType').combobox({
                onChange: function (newVal, oldVal) {
                    var the = $(this).find('option[value=' + newVal + ']');
                    var index = the.parent().find('option').index(the);
                    var port = $('#port').find('option').eq(index).val();
                    $('#port').combobox('setValue', port)
                }
            });
            $('#submitBtn').click(function () {
                if ($('#editForm').form('validate')) {
                    $('#editForm').submit();
                }
            });
            $('#resetBtn').click(function () {
                $('#editForm').form('reset');
            });
        });
    </script>
</head>
<body>
<form name="editForm" id="editForm" action="dbconf.do" method="get">
    <table>
        <tr>
            <th colspan="2" style="text-align: center;">请配置数据库连接信息</th>
        </tr>
        <tr>
            <th>数据库类型：</th>
            <td>
                <select name="dbType" id="dbType" class="easyui-combobox comm"
                        data-options="required:true,editable:false,panelHeight:100">
                    <%
                        for (DBType dbType : DBType.values()) {
                            out.println("<option value='" + dbType + "'>" + dbType + "</option>");
                        }
                    %>
                </select>
            </td>
        </tr>
        <tr>
            <th>主机IP地址：</th>
            <td><input type="text" name="ip" value="192.168." class="easyui-validatebox comm"
                       data-options="required:true"/></td>
        </tr>
        <tr>
            <th>端口：</th>
            <td>
                <select name="port" id="port" class="easyui-combobox comm" data-options="required:true,panelHeight:100">
                    <%
                        for (DBType dbType : DBType.values()) {
                            out.println("<option value='" + dbType.getPort() + "'>" + dbType.getPort() + "</option>");
                        }
                    %>
                </select>
            </td>
        </tr>
        <tr>
            <th>数据库SID：</th>
            <td><input type="text" name="sid" class="easyui-validatebox comm" data-options="required:true"/></td>
        </tr>
        <tr>
            <th>用户名：</th>
            <td><input type="text" name="user" class="easyui-validatebox comm" data-options="required:true"/></td>
        </tr>
        <tr>
            <th>密码：</th>
            <td><input type="text" name="password" class="easyui-validatebox comm" data-options="required:true"/></td>
        </tr>
        <tr>
            <td colspan="2" style="text-align: center;">
                <a href="javascript:void(0)" id="submitBtn" class="easyui-linkbutton"
                   data-options="plain:true,iconCls:'icon-ok'">连接</a>&nbsp;&nbsp;
                <a href="javascript:void(0)" id="resetBtn" class="easyui-linkbutton"
                   data-options="plain:true,iconCls:'icon-reload'">重置</a>
            </td>
        </tr>
    </table>
</form>
</body>
</html>