<%@ page language="java" %>
<%@ include file="/taglibs.jsp"%>
<html>
	<head>
		<title>Tabla de Cinco Claves</title>
		
		<!-- UX libraries -->
        <script type="text/javascript" src="${ctx}/resources/extjs4/plugins/spreadsheet/ux-all-debug.js"></script>
        <!-- Spread library -->
        <script type="text/javascript" src="${ctx}/resources/extjs4/plugins/spreadsheet/spread-all-debug.js"></script>
        <link type="text/css" rel="stylesheet" href="${ctx}/resources/extjs4/plugins/spreadsheet/css/spread.css" />
        
		<script type="text/javascript">
            var _CONTEXT = '${ctx}';
            var _URL_CONSULTA_CABECERAS_CLAVES = '<s:url namespace="/catalogos" action="obtieneClavesTablaApoyo" />';
            var _URL_CONSULTA_VALORES_TABLA_CINCO_CLAVES = '<s:url namespace="/tablasapoyo" action="obtieneValoresTablaCincoClaves" />';
            var _NMTABLA = '<s:property value="params.nmtabla" />';
        </script>
        
        <script type="text/javascript" src="${ctx}/js/catalogos/tablaCincoClaves.js"></script>
	</head>
    <body>
        <div id="dvCincoClaves"/>
    </body>
</html>