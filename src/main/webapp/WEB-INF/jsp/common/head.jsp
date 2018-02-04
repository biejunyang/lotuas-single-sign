<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags/form"  prefix="spring"%>

<!--  jquery  -->
<script type="text/javascript" src="${ctx }/resources/easyui-1.5.4/jquery.min.js"></script>

<!-- jquery easyui  -->
<link rel="stylesheet" type="text/css" href="${ctx }/resources/easyui-1.5.4/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx }/resources/easyui-1.5.4/themes/icon.css">
<script type="text/javascript" src="${ctx }/resources/easyui-1.5.4/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx }/resources/easyui-1.5.4/locale/easyui-lang-zh_CN.js"></script>


<!-- jquery easyui  extend-->
<script type="text/javascript" src="${ctx}/resources/easyui-1.5.4/jquery.easyui.extend.js"></script>
<script type="text/javascript" src="${ctx}/resources/easyui-1.5.4/jquery.easyui.validation.js"></script>

<!-- js util -->
<script type="text/javascript" src="${ctx}/resources/js/js_util.js"></script>
<script type="text/javascript" src="${ctx}/resources/js/json_util.js"></script>

<!-- common css -->
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/common.css">




<%-- <link rel="stylesheet" type="text/css" href="${ctx}resources/easyui/themes/easyui.css?${version}"/>

<link rel="stylesheet" type="text/css" href="${ctx}resources/css/jquery.jOrgChart.css?${version}"/>
<link rel="stylesheet" type="text/css" href="${ctx}resources/css/custom.css?${version}"/>

<link rel="stylesheet" type="text/css" href="${ctx}resources/easyui/themes/icon.css?${version}"/>
<link rel="stylesheet" type="text/css" href="${ctx}resources/css/searchPanel.css?${version}"/>
<link rel="stylesheet" type="text/css" href="${ctx}resources/datepicker/jquery.datetimepicker.css?${version}"/>

<link rel="shortcut icon" href="${ctx}resources/images/sample_favicon.png">
<link rel="shortcut icon" href="${ctx}resources/images/sample_favicon.ico">
<script type="text/javascript" src="${ctx}resources/jquery/jquery-1.8.0.min.js?${version}"></script>

<script type="text/javascript" src="${ctx}resources/jquery/prettify.js?${version}"></script>
<script type="text/javascript" src="${ctx}resources/jquery/jquery-ui-1.10.3.custom.min.js?${version}"></script>
<script type="text/javascript" src="${ctx}resources/jquery/jquery.jOrgChart.js?${version}"></script>

<script type="text/javascript" src="${ctx}resources/jquery/jquery.form.js?${version}"></script>
<script type="text/javascript" src="${ctx}resources/easyui/jquery.easyui.min.js?${version}"></script>
<script type="text/javascript" src="${ctx}resources/easyui/jquery.layout.extend.js?${version}"></script>
<script type="text/javascript" src="${ctx}resources/easyui/locale/easyui-lang-zh_CN.js?${version}"></script>
<script type="text/javascript" src="${ctx}resources/jquery/ajaxfileupload.js?${version}"></script>
<script type="text/javascript" src="${ctx}resources/easyui/syUtils.js?${version}" charset="UTF-8"></script>
<script type="text/javascript" src="${ctx}resources/js/DatePicker/WdatePicker.js?${version}"></script>
<script type="text/javascript" src="${ctx}resources/js/JsonUtil.js?${version}"></script>
<script type="text/javascript" src="${ctx}resources/js/search/search_panel.js?${version}"></script>
<script type="text/javascript" src="${ctx}resources/js/search/data.structure.js?${version}"></script>
<script type="text/javascript" src="${ctx}resources/easyui/jquery.cookie.js?${version}"></script>
<script type="text/javascript" src="${ctx}resources/datepicker/jquery.datetimepicker.js?${version}"></script>
<script type="text/javascript" src="${ctx}resources/highcharts/highcharts.js?${version}"></script>
<!--自定义验证插件-->
<script type="text/javascript" src="${ctx}resources/easyui/validatebox.rules.js?${version}"></script>
<script type="text/javascript" src="${ctx}resources/easyui/jquery.extends.js?${version}"></script>
 --%>
<script type="text/javascript">
   var JS_CONTEXT = '${ctx}';
</script>