<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>

<script type="text/javascript">

var _CONTEXT = '${ctx}';

var _PAGO_DIRECTO = "1";
var _REEMBOLSO    = "2";

var _nmTramite = '<s:property value="params.ntramite" />';
var _nmSiniestro = '<s:property value="params.nmSiniestro" />';

debug("Ntramite: "+ _nmTramite);
var _URL_LoadFacturas =  '<s:url namespace="/siniestros" action="loadListaFacturasTramite" />';
var _URL_GuardaFactura =  '<s:url namespace="/siniestros" action="guardaFacturaTramite" />';

var _URL_CATALOGOS = '<s:url namespace="/catalogos" action="obtieneCatalogo" />';
var _CATALOGO_TipoAtencion = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@TIPO_ATENCION_SINIESTROS"/>';
var _CATALOGO_PROVEEDORES  = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@PROVEEDORES"/>';

Ext.onReady(function() {
	
	var storeFacturas;
	var storeConceptos;
	
	var gridFacturas;
	var gridConceptos;
	
	Ext.define('modelFacturas',{
        extend: 'Ext.data.Model',
        fields: [{type:'string',    name:'NFACTURA'},
                 {type:'string',    name:'FFACTURA'},
                 {type:'string',    name:'CDTIPSER'},
                 {type:'string',    name:'DESCSERVICIO'},
                 {type:'string',    name:'CDPRESTA'},
                 {type:'string',    name:'NOMBREPROVEEDOR'},
                 {type:'string',    name:'PTIMPORT'},
                 {type:'string',    name:'CDGARANT'},
                 {type:'string',    name:'DSGARANT'},
                 {type:'string',    name:'DESCPORC'},
                 {type:'string',    name:'DESCNUME'}
				]
    });
	
	Ext.define('modelConceptos',{
        extend: 'Ext.data.Model',
        fields: [{type:'string',    name:'concepto'},
                 {type:'string',    name:'ub'},
                 {type:'string',    name:'subcobertura'},
                 {type:'string',    name:'importe'},
                 {type:'string',    name:'descuento'},
                 {type:'string',    name:'subtotalfactura'},
                 {type:'string',    name:'importeAutorizado'}
				]
    });

	Ext.define('modelListadoProvMedico',{
	    extend: 'Ext.data.Model',
	    fields: [
            {type:'string', name:'cdpresta'},
            {type:'string', name:'nombre'},
            {type:'string', name:'cdespeci'},
            {type:'string',name:'descesp'}
	    ]
	});
	
	storeFacturas=new Ext.data.Store(
	{
	    autoDestroy: true,
	    model: 'modelFacturas',
	    proxy: {
            type: 'ajax',
            url: _URL_LoadFacturas,
            reader: {
                type: 'json',
                root: 'loadList'
            }
        }
	});
	storeFacturas.load({
    	params: {
    		'params.nmtramite' : _nmTramite
    	}
    });

	storeConceptos=new Ext.data.Store(
			{
			    autoDestroy: true,
			    model: 'modelConceptos'
			});
	
	var storeTipoAtencion = Ext.create('Ext.data.JsonStore', {
		model:'Generic',
		proxy: {
			type: 'ajax',
			url: _URL_CATALOGOS,
			extraParams : {catalogo:_CATALOGO_TipoAtencion},
			reader: {
				type: 'json',
				root: 'lista'
			}
		}
	});
	storeTipoAtencion.load();
	
	var storeProveedor = Ext.create('Ext.data.Store', {
        model:'modelListadoProvMedico',
        autoLoad:false,
        proxy: {
            type: 'ajax',
            url : _URL_CATALOGOS,
            extraParams:{
                catalogo         : _CATALOGO_PROVEEDORES,
                catalogoGenerico : true
            },
            reader: {
                type: 'json',
                root: 'listaGenerica'
            }
        }
    });

	
	var panelEdicionFacturas= Ext.create('Ext.form.Panel',{
        border  : 0,
        url: _URL_GuardaFactura
        ,bodyStyle:'padding:5px;'
        ,items :
        [   
             {
		        xtype      : 'textfield'
		    	,fieldLabel : 'No. Factura'
	    		,allowBlank:false
		    	,name       : 'noFactInterno'
    		},            
    		{
    	        name: 'fechaFactInterno',
    	        fieldLabel: 'Fecha Factura',
    	        xtype: 'datefield',
    	        format: 'd/m/Y',
    	        editable: true,
    	        allowBlank:false,
    	        value:new Date()
    	    },{
            	xtype: 'combo',
                name:'tipoServicioInterno',
                valueField: 'key',
                displayField: 'value',
                fieldLabel: 'Tipo de servicio',
                store: storeTipoAtencion,
                queryMode:'local',
                allowBlank:false,
                editable:false,
                emptyText:'Seleccione...'
            },{
            	xtype       : 'combo',
            	name        : 'proveedorInterno',
            	fieldLabel  : 'Proveedor',
            	displayField: 'nombre',
            	valueField  : 'cdpresta',
            	allowBlank  : false,
                forceSelection : true,
                matchFieldWidth: false,
                queryMode   :'remote',
                queryParam  : 'params.cdpresta',
                store       : storeProveedor,
                triggerAction  : 'all',
                emptyText   : 'Seleccione...',
                editable    : false
            },{
		        xtype      : 'numberfield'
		    	,fieldLabel : 'Importe'
                ,allowBlank:false
                ,allowDecimals: true
                ,decimalSeparator: '.'
                ,minValue: 0
		    	,name       : 'importeInterno'
    		},{
		        	xtype      : 'numberfield'
			    	,fieldLabel : 'Descuento %'
	                ,allowBlank:false
	                ,allowDecimals: true
	                ,decimalSeparator: '.'
	                ,minValue: 0
			    	,name       : 'descPorc'
	    	},{
		        xtype      : 'numberfield'
			    	,fieldLabel : 'Descuento Importe'
	                ,allowBlank:false
	                ,allowDecimals: true
	                ,decimalSeparator: '.'
	                ,minValue: 0
			    	,name       : 'descImport'
	    	}
        ]
    });
    
    
    var panelEdicionConceptos = Ext.create('Ext.form.Panel',{
        border  : 0
        ,bodyStyle:'padding:5px;'
        ,items :
        [   
             {
		        xtype      : 'textfield'
		    	,fieldLabel : 'Concepto'
	    		,labelWidth: 150
	    		,allowBlank:false
		    	,name       : 'nombreConcepto'
    		}
            ,
            {
		        xtype      : 'textfield'
		    	,fieldLabel : 'UB'
	    		,labelWidth: 150
	    		,allowBlank:false
		    	,name       : 'ub'
    		}
            ,
            {
		        xtype      : 'textfield'
		    	,fieldLabel : 'Subcobertura'
	    		,labelWidth: 150
	    		,allowBlank:false
		    	,name       : 'subcobertura'
    		}
            ,
            {
		        xtype      : 'textfield'
		    	,fieldLabel : 'importe'
	    		,labelWidth: 150
	    		,allowBlank:false
		    	,name       : 'importe'
    		}
            ,
            {
		        xtype      : 'textfield'
		    	,fieldLabel : 'Descuento (%)'
	    		,labelWidth: 150
	    		,allowBlank:false
		    	,name       : 'porcentajeDescuento'
    		}
            ,
            {
		        xtype      : 'textfield'
		    	,fieldLabel : 'Subtotal factura'
	    		,labelWidth: 150
	    		,allowBlank:false
		    	,name       : 'subtotalFactura'
    		}
            ,
            {
		        xtype      : 'textfield'
		    	,fieldLabel : 'Importe autorizado arancel'
	    		,labelWidth: 150
	    		,allowBlank:false
		    	,name       : 'importeAutorizado'
    		}
        ]
    });

	/*PANTALLA EMERGENTE PARA LA INSERCION Y MODIFICACION DE LOS DATOS DEL GRID*/
    var windowConceptos= Ext.create('Ext.window.Window', {
          title: 'Agregar Concepto',
          closeAction: 'hide',
          items:[panelEdicionConceptos],
          
          buttons:[{
                 text: 'Aceptar',
                 icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
                 handler: function() {
                       if (panelEdicionConceptos.form.isValid()) {
                       	
                       	var datos=panelEdicionConceptos.form.getValues();
                       	console.log(datos);
                       	var rec = new modelConceptos({
                       		concepto: datos.nombreConcepto,
                       		ub: datos.ub,
                       		subcobertura: datos.subcobertura,
                       		importe: datos.importe,
                       		descuento: datos.porcentajeDescuento,        	
                       		subtotalfactura: datos.subtotalFactura,
                       		importeAutorizado: datos.importeAutorizado,
		        	 		});
                       	storeConceptos.add(rec);
                       	panelEdicionConceptos.getForm().reset();
                       	windowConceptos.close();
                       } else {
                           Ext.Msg.show({
                                  title: 'Aviso',
                                  msg: 'Complete la informaci&oacute;n requerida',
                                  buttons: Ext.Msg.OK,
                                  icon: Ext.Msg.WARNING
                              });
                       }
                   }
             },
           {
                 text: 'Cancelar',
                 icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
                 handler: function() {
                	 panelEdicionConceptos.getForm().reset();
                     windowConceptos.close();
                 }
           }
          ]
          });
    
    var windowFacturas= Ext.create('Ext.window.Window', {
           title: 'Agregar Factura',
           bodyStyle:'padding:5px;',
           closeAction: 'hide',
           items:[panelEdicionFacturas],
           
           buttons:[{
                  text: 'Aceptar',
                  icon:_CONTEXT+'/resources/fam3icons/icons/accept.png',
                  handler: function() {
                        if (panelEdicionFacturas.form.isValid()) {
                        	
                        	var datos=panelEdicionFacturas.form.getValues();
                        	panelEdicionFacturas.form.submit({
            		        	waitMsg:'Procesando...',			
            		        	params: {
            		        		'params.pv_ntramite_i'   : _nmTramite, 
            		        		'params.pv_nfactura_i'   : datos.noFactInterno,
            		        		'params.pv_ffactura_i'   : datos.fechaFactInterno,
            		        		'params.pv_cdtipser_i'   : datos.tipoServicioInterno,
            		        		'params.pv_cdpresta_i'   : datos.proveedorInterno,
            		        		'params.pv_ptimport_i'   : datos.importeInterno,
            		        		'params.pv_cdgarant_i'   : '',
            		        		'params.pv_descporc_i'   : datos.descPorc,
            		        		'params.pv_descnume_i'   : datos.descImport,
            		        	},
            		        	failure: function(form, action) {
            		        		mensajeError("Error al guardar la Factura");
            					},
            					success: function(form, action) {
            						
            						storeFacturas.reload();
            						panelEdicionFacturas.getForm().reset();
                                	windowFacturas.close();
                                	mensajeCorrecto("Aviso","Se ha guardado la Factura");
            						
            						
            					}
            				});
                        	
                        } else {
                            Ext.Msg.show({
                                   title: 'Aviso',
                                   msg: 'Complete la informaci&oacute;n requerida',
                                   buttons: Ext.Msg.OK,
                                   icon: Ext.Msg.WARNING
                               });
                        }
                    }
              },
            {
                  text: 'Cancelar',
                  icon:_CONTEXT+'/resources/fam3icons/icons/cancel.png',
                  handler: function() {
                      panelEdicionFacturas.getForm().reset();
                      windowFacturas.close();
                  }
            }
           ]
           });    

	
	
/*////////////////////////////////////////////////////////////////
////////////////   DECLARACION DE GRID FACTURAS ////////////
///////////////////////////////////////////////////////////////*/
Ext.define('EditorFacturas', {
		extend: 'Ext.grid.Panel',
 		requires: [
	 	'Ext.selection.CellModel',
	 	'Ext.grid.*',
	 	'Ext.data.*',
	 	'Ext.util.*',
	 	'Ext.form.*'
 	],
		selType: 'checkboxmodel',
		title: 'Facturas en Tr&aacute;mite',
		frame: false,
 		initComponent: function(){
 		this.cellEditing = new Ext.grid.plugin.CellEditing({
 		clicksToEdit: 1
 		});

 			Ext.apply(this, {
 			height: 250,
 			plugins: [this.cellEditing],
 			store: storeFacturas,
 			columns: 
 				[{
 					header : 'No. de Factura',
 					dataIndex : 'NFACTURA',
 					width : 150
 				},{
 					header : 'Fecha de Factura',
 					dataIndex : 'FFACTURA',
 					width : 150
 				// , renderer: Ext.util.Format.dateRenderer('d M Y')

 				},{
 					header : 'Cobertura',
 					dataIndex : 'DSGARANT',
 					width : 150
 				},{
 					header : 'Importe',
 					dataIndex : 'PTIMPORT',
 					width : 150,
 					renderer : Ext.util.Format.usMoney
 				},{
 					header : 'Deducible',
 					dataIndex : '',
 					width : 120
 				},{
 					header : 'Copago',
 					dataIndex : '',
 					width : 120
 				}, <s:property value="imap.gridColumns" />
 				,{
 					xtype : 'actioncolumn',
 					width : 50,
 					sortable : false,
 					menuDisabled : true,
 					items : [ {
 						icon : _CONTEXT+'/resources/fam3icons/icons/delete.png',
 						tooltip : 'Quitar inciso',
 						scope : this,
 						handler : this.onRemoveClick
 					},
 					{
 						icon : _CONTEXT+'/resources/fam3icons/icons/pencil.png',
 						tooltip : 'Editar',
 						scope : this,
 						handler : this.onEditClick
 					}]
 				} ],
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
 		windowFacturas.show();
 		centrarVentana(windowFacturas);
 		
 	},
 	onRemoveClick: function(grid, rowIndex){
 		var record=this.getStore().getAt(rowIndex);
 		debug(record);        	
 		this.getStore().removeAt(rowIndex);
 	},
 	onEditClick: function(grid, rowIndex){
 		var record=this.getStore().getAt(rowIndex);
 		debug("Editando...");        	
 		
 	}
	});

gridFacturas=new EditorFacturas();


/*////////////////////////////////////////////////////////////////
////////////////   DECLARACION DE GRID CONCEPTOS  ////////////
///////////////////////////////////////////////////////////////*/
Ext.define('EditorConceptos', {
		extend: 'Ext.grid.Panel',
 	requires: [
	 	'Ext.selection.CellModel',
	 	'Ext.grid.*',
	 	'Ext.data.*',
	 	'Ext.util.*',
	 	'Ext.form.*'
 	],
		title: 'Conceptos en Factura',
		frame: false,

 	initComponent: function(){
 		this.cellEditing = new Ext.grid.plugin.CellEditing({
 		clicksToEdit: 1
 		});

 			Ext.apply(this, {
 			height: 250,
 			plugins: [this.cellEditing],
 			store: storeConceptos,
 			columns: 
 				[{
 					header : 'Concepto',
 					dataIndex : 'concepto',
 					width : 150
 				},{
 					header : 'UB',
 					dataIndex : 'ub',
 					width : 150
 				// , renderer: Ext.util.Format.dateRenderer('d M Y')

 				},{
 					header : 'Subcobertura',
 					dataIndex : 'subcobertura',
 					width : 150
 				},{
 					header : 'Importe',
 					dataIndex : 'importe',
 					width : 150,
 					renderer : Ext.util.Format.usMoney
 				},{
 					header : 'Descuento (%)',
 					dataIndex : 'descuento',
 					width : 150
 				},{
 					header : 'Subtotal Factura',
 					dataIndex : 'subtotalfactura',
 					width : 150
 				},{
 					header : 'Importe autorizado <br/> arancel',
 					dataIndex : 'importeAutorizado',
 					width : 150
 				},{
 					xtype : 'actioncolumn',
 					width : 30,
 					sortable : false,
 					menuDisabled : true,
 					items : [ {
 						icon : _CONTEXT
 								+ '/resources/extjs4/resources/ext-theme-classic/images/icons/fam/delete.png',
 						tooltip : 'Quitar inciso',
 						scope : this,
 						handler : this.onRemoveClick
 					} ]
 				} ],
	 		tbar: [{
			 	icon:_CONTEXT+'/resources/extjs4/resources/ext-theme-classic/images/icons/fam/add.png',
			 	text: 'Agregar concepto',
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
 		if(gridFacturas.getSelectionModel().hasSelection()){
 			windowConceptos.show();
 		}else {
 			mensajeWarning("Debe seleccionar una factura para poder agregar un concepto a la misma.");
 		} 
 	},
 	onRemoveClick: function(grid, rowIndex){
 		var record=this.getStore().getAt(rowIndex);
 		console.log(record);        	
 		this.getStore().removeAt(rowIndex);
 	}
	});

gridConceptos=new EditorConceptos();


Ext.create('Ext.form.Panel',{
	renderTo: 'maindivAdminData',
	border     : false
	,bodyStyle:'padding:5px;'
	,items      : [
        		gridFacturas,
		        {
			        xtype      : 'textfield'
			    	,fieldLabel : 'Total Facturado'
		    		,labelWidth: 170
		            ,width:500
		            ,allowBlank:false
			    	,name       : 'totalFacturado'
		    		,aling:	'center'
					,padding : 10
				},
				gridConceptos
	]
	});
});
</script>

<div id="maindivAdminData" style="height:600px;"></div>