<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>

<script type="text/javascript">

var _CONTEXT = '${ctx}';

var _PAGO_DIRECTO = "1";
var _REEMBOLSO    = "2";

var _UrlAltaDeTramite           = '<s:url namespace="/siniestros" action="altaTramite"               />';
var _UrlRevisionDocsSiniestro   = '<s:url namespace="/siniestros" action="revisionDocumentos"        />';
var _UrlRechazarTramiteWindwow  = '<s:url namespace="/siniestros" action="rechazoReclamaciones"      />';


Ext.onReady(function() {
	
/*////////////////////////////////////////////////////////////////
////////////////   DECLARACION DE EDITOR DE INCISOS  ////////////
///////////////////////////////////////////////////////////////*/
Ext.define('EditorIncisos', {
		extend: 'Ext.grid.Panel',
 	requires: [
	 	'Ext.selection.CellModel',
	 	'Ext.grid.*',
	 	'Ext.data.*',
	 	'Ext.util.*',
	 	'Ext.form.*'
 	],
		xtype: 'cell-editing',

		title: 'Facturas en Tr&aacute;mite',
		frame: false,

 	initComponent: function(){
 		this.cellEditing = new Ext.grid.plugin.CellEditing({
 		clicksToEdit: 1
 		});

 			Ext.apply(this, {
 			height: 200,
 			plugins: [this.cellEditing],
 			store: storeIncisos,
 			columns: 
 			[
			 	{	
			 		header: 'No. de Factura',			dataIndex: 'noFactura',			width           : 150
			 	},
			 	{
			 		header: 'Fecha de Factura',			dataIndex: 'fechaFactura',		width           : 150 //,			 	renderer: Ext.util.Format.dateRenderer('d M Y')
			 		
			 	},
			 	{
				 	header: 'Cobertura',			dataIndex: 'tipoServicio',			width           : 150
			 	},
			 	{
				 	header: 'Importe', 					dataIndex: 'importe',			width           : 150,				renderer: Ext.util.Format.usMoney
			 	},
			 	{
				 	header: 'Deducible',	dataIndex: 'proveedor',			width           : 150
			 	},
			 	{
				 	header: 'Copago',	dataIndex: 'proveedor',			width           : 150
			 	},
			 	{
				 	header: 'Autorizado AR',	dataIndex: 'proveedor',			width           : 150
			 	},
			 	{
				 	header: 'Observaciones AR',	dataIndex: 'proveedor',			width           : 150
			 	},
			 	{
				 	xtype: 'actioncolumn',
				 	width: 30,
				 	sortable: false,
				 	menuDisabled: true,
				 	items: [{
				 		icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/delete.png',
				 		tooltip: 'Quitar inciso',
				 		scope: this,
				 		handler: this.onRemoveClick
			 		}]
			 	}
	 		],
	 		selModel: {
		 		selType: 'cellmodel'
		 	},
	 		tbar: [{
			 	icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/add.png',
			 	text: 'Agregar Factura',
			 	scope: this,
			 	handler: this.onAddClick
	 		}]
	 	});
			this.callParent();
 	},
 	getColumnIndexes: function () {
	 	var me, columnIndexes;
	 	me = this;
	 	columnIndexes = [];
	 	Ext.Array.each(me.columns, function (column)
	 	{
	 		if (column.getEditor&&Ext.isDefined(column.getEditor())&&column.getEditor().allowBlank==false) {
	 			columnIndexes.push(column.dataIndex);
		 	} else {
		 		columnIndexes.push(undefined);
		 	}
	 	});
	 	return columnIndexes;
 	},
 	validateRow: function (columnIndexes,record, y)
 	//hace que una celda de columna con allowblank=false tenga el estilo rojito
 	{
	 	var view = this.getView();
	 	Ext.each(columnIndexes, function (columnIndex, x)
	 	{
	 		if(columnIndex)
		 	{
		 		var cell=view.getCellByPosition({row: y, column: x});
		 		cellValue=record.get(columnIndex);
			 	if(cell.addCls&&((!cellValue)||(cellValue.lenght==0))){
			 		cell.addCls("custom-x-form-invalid-field");
			 	}
		 	}
	 	});
	 	return false;
 	},
 	onAddClick: function(){	 		
 		ventanaGrid.show();
 		
 		/*window.parent.scrollTo(0,600);
 		// Create a model instance
 		var rec = new modelClau({
 				  	noFactura: '1233',
				 	fechaFactura: '12/12/2012',
				 	tipoServicio: 'CONSULTA',
				 	proveedor: 'MATRIZ',
				 	importe: '1245678.00'            	
 		});
 		this.getStore().add(rec);
	 	//para acomodarse en la primer celda para editar
	 	this.cellEditing.startEditByPosition({
	 		row: storeIncisos.getRange().length-1, 
	 		column: 0
	 	});*/
 	},
 	onRemoveClick: function(grid, rowIndex){
 		var record=this.getStore().getAt(rowIndex);
 		console.log(record);        	
 		this.getStore().removeAt(rowIndex);
 	}
	});

gridIncisos=new EditorIncisos();


/*////////////////////////////////////////////////////////////////
////////////////   DECLARACION DE EDITOR DE INCISOS  ////////////
///////////////////////////////////////////////////////////////*/
Ext.define('EditorIncisos2', {
		extend: 'Ext.grid.Panel',
 	requires: [
	 	'Ext.selection.CellModel',
	 	'Ext.grid.*',
	 	'Ext.data.*',
	 	'Ext.util.*',
	 	'Ext.form.*'
 	],
		xtype: 'cell-editing',

		title: 'Facturas en Tr&aacute;mite',
		frame: false,

 	initComponent: function(){
 		this.cellEditing = new Ext.grid.plugin.CellEditing({
 		clicksToEdit: 1
 		});

 			Ext.apply(this, {
 			height: 200,
 			plugins: [this.cellEditing],
 			store: storeDetalle,
 			columns: 
 			[
			 	{	
			 		header: 'Concepto',			dataIndex: 'concepto',			width           : 150
			 	},
			 	{
			 		header: 'UB',			dataIndex: 'ub',		width           : 150 //,			 	renderer: Ext.util.Format.dateRenderer('d M Y')
			 		
			 	},
			 	{
				 	header: 'Subcobertura',			dataIndex: 'subcobertura',			width           : 150
			 	},
			 	{
				 	header: 'Importe', 					dataIndex: 'importe',			width           : 150,				renderer: Ext.util.Format.usMoney
			 	},
			 	{
				 	header: 'Descuento (%)',	dataIndex: 'descuento',			width           : 150
			 	},
			 	{
				 	header: 'Subtotal Factura',	dataIndex: 'subtotalfactura',			width           : 150
			 	},
			 	{
				 	header: 'Importe autorizado <br/> arancel',	dataIndex: 'importeAutorizado',			width           : 150
			 	},
			 	{
				 	xtype: 'actioncolumn',
				 	width: 30,
				 	sortable: false,
				 	menuDisabled: true,
				 	items: [{
				 		icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/delete.png',
				 		tooltip: 'Quitar inciso',
				 		scope: this,
				 		handler: this.onRemoveClick
			 		}]
			 	}
	 		],
	 		selModel: {
		 		selType: 'cellmodel'
		 	},
	 		tbar: [{
			 	icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/add.png',
			 	text: 'Agregar detalle',
			 	scope: this,
			 	handler: this.onAddClick
	 		}]
	 	});
			this.callParent();
 	},
 	getColumnIndexes: function () {
	 	var me, columnIndexes;
	 	me = this;
	 	columnIndexes = [];
	 	Ext.Array.each(me.columns, function (column)
	 	{
	 		if (column.getEditor&&Ext.isDefined(column.getEditor())&&column.getEditor().allowBlank==false) {
	 			columnIndexes.push(column.dataIndex);
		 	} else {
		 		columnIndexes.push(undefined);
		 	}
	 	});
	 	return columnIndexes;
 	},
 	validateRow: function (columnIndexes,record, y)
 	//hace que una celda de columna con allowblank=false tenga el estilo rojito
 	{
	 	var view = this.getView();
	 	Ext.each(columnIndexes, function (columnIndex, x)
	 	{
	 		if(columnIndex)
		 	{
		 		var cell=view.getCellByPosition({row: y, column: x});
		 		cellValue=record.get(columnIndex);
			 	if(cell.addCls&&((!cellValue)||(cellValue.lenght==0))){
			 		cell.addCls("custom-x-form-invalid-field");
			 	}
		 	}
	 	});
	 	return false;
 	},
 	onAddClick: function(){	 		
 		ventanaGrid2.show();
 		
 		/*window.parent.scrollTo(0,600);
 		// Create a model instance
 		var rec = new modelClau({
 				  	noFactura: '1233',
				 	fechaFactura: '12/12/2012',
				 	tipoServicio: 'CONSULTA',
				 	proveedor: 'MATRIZ',
				 	importe: '1245678.00'            	
 		});
 		this.getStore().add(rec);
	 	//para acomodarse en la primer celda para editar
	 	this.cellEditing.startEditByPosition({
	 		row: storeIncisos.getRange().length-1, 
	 		column: 0
	 	});*/
 	},
 	onRemoveClick: function(grid, rowIndex){
 		var record=this.getStore().getAt(rowIndex);
 		console.log(record);        	
 		this.getStore().removeAt(rowIndex);
 	}
	});

gridIncisos2=new EditorIncisos2();


Ext.create('Ext.form.Panel',{
	renderTo: 'maindivAdminData',
	border     : false
	,bodyStyle:'padding:5px;'
	//,layout: {type:'hbox', pack: 'center'}
	,items      : [
        		gridIncisos,
		        {
			    	id:'totalFacturado'
			        ,xtype      : 'textfield'
			    	,fieldLabel : 'Total Facturado'
		    		,labelWidth: 170
		            ,width:500
		            ,allowBlank:false
			    	,name       : 'totalFacturado'
		    		,aling:	'center'
					,padding : 10
				},
				gridIncisos2
	],
    buttonAlign:'center',
    buttons: [
             {
            icon:_CONTEXT+'/resources/fam3icons/icons/calculator.png',
            text: 'Generar Tramite',
    		handler: function(btn,e) {
    			
    			var form = this.up('form').getForm();
    			if (form.isValid())
                {
    				Ext.Msg.show({
                    	title:'Exito',
                    	msg: 'Se contemplaron todo',
                    	buttons: Ext.Msg.OK,
                    	icon: Ext.Msg.WARNING
                	});
                }
    			else {
    				Ext.Msg.show({
    					title: 'Aviso',
    		            msg: 'Complete la informaci&oacute;n requerida',
    		            buttons: Ext.Msg.OK,
    		            animateTarget: btn,
    		            icon: Ext.Msg.WARNING
    				});
    			}
    			
    			/*else
				{
    				var incisosRecords = storeIncisos.getRange();
    				console.log(incisosRecords.length);
    				
    				var incisosJson = [];
    				storeIncisos.each(function(record,index){
                    	if(record.get('nombre')
                    			&&record.get('nombre').length>0)
                		{
                    		nombres++;
                		}
                        incisosJson.push({
                        	noFactura: record.get('noFactura'),
                        	fechaFactura: record.get('fechaFactura'),
                        	tipoServicio: record.get('tipoServicio'),
                        	proveedor: record.get('proveedor'),
                        	importe: record.get('importe')
                        });
                    });
    				
    				console.log('---- VALOR DE IncisosJson ---- ');
    				console.log(incisosJson);
    				
    				var submitValues=form.getValues();
                	submitValues['incisos']=incisosJson;
                	console.log('---- VALOR DE submitValues ---- ');
    				console.log(submitValues);
    				
    				Ext.Msg.show({
                    	title:'Datos incompletos',
                    	msg: 'Favor de introducir todos los campos requeridos',
                    	buttons: Ext.Msg.OK,
                    	icon: Ext.Msg.WARNING
                	});
				}*/
            }
        },
        {
        	text: 'Cancelar',
            icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
            handler: function() {
            	menu2.close();
            }
        }
	]
	});
});
</script>

<div id="maindivAdminData" style="height:700px;"></div>