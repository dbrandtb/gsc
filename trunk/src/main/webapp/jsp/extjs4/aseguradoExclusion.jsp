<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
	///////////////////////
	////// variables //////
	/*///////////////////*/
	var venExcluUrlCargar       = '<s:url namespace="/" action="cargarPantallaExclusion" />';
	var venExcluUrlCargarDisp   = '<s:url namespace="/" action="obtenerExclusionesPorTipo" />';
	var venExcluUrlGuardar      = '<s:url namespace="/" action="guardarExclusiones" />';
	var venExcluUrlLoadHtml     = '<s:url namespace="/" action="cargarHtmlExclusion" />';
	var venExcluUrlAddExclu     = '<s:url namespace="/" action="agregarExclusion" />';
	var venExcluUrlSaveHtml     = '<s:url namespace="/" action="guardarHtmlExclusion" />';
	var venExcluUrlCargarTipos  = '<s:url namespace="/" action="cargarTiposClausulasExclusion" />';
	var venExcluContexto        = '${ctx}';
	var inputCduniecopx         = '<s:property value="smap1.pv_cdunieco" />';
	var inputCdramopx           = '<s:property value="smap1.pv_cdramo" />';
	var inputEstadopx           = '<s:property value="smap1.pv_estado" />';
	var inputNmpolizapx         = '<s:property value="smap1.pv_nmpoliza" />';
	var inputNmsituacpx         = '<s:property value="smap1.pv_nmsituac" />';
	var inputCdpersonpx         = '<s:property value="smap1.pv_cdperson" />';
	var inputCdrolpx            = '<s:property value="smap1.pv_cdrol" />';
	var inputNombreaseguradopx  = '<s:property value="smap1.nombreAsegurado" escapeHtml="false" />';
	var inputCdrfcpx            = '<s:property value="smap1.cdrfc" escapeHtml="false" />';
	var venExcluStoreDisp;
	var venExcluStoreUsa;
	var venExcluStoreTipos;
	/*///////////////////*/
	////// variables //////
	///////////////////////
	
	///////////////////////
	////// funciones //////
	/*///////////////////*/
	
	/*///////////////////*/
	////// funciones //////
	///////////////////////
    
Ext.onReady(function(){
    
    /////////////////////
    ////// modelos //////
    /*/////////////////*/
    Ext.define('ModeloExclusion',{
        extend:'Ext.data.Model',
        fields:['cdclausu','dsclausu','linea_usuario','cdtipcla','linea_general']
    });
    
    Ext.define('ModeloTipoClausula',
    {
    	extend:'Ext.data.Model',
    	fields:['cdtipcla','dstipcla']
    });
    /*/////////////////*/
    ////// modelos //////
    /////////////////////
    
    ////////////////////
    ////// stores //////
    /*////////////////*/
    venExcluStoreTipos = new Ext.data.Store(
    {
        model      : 'ModeloTipoClausula'
        ,autoLoad  : true
        ,proxy     :
        {
            url     : venExcluUrlCargarTipos
            ,type   : 'ajax'
            ,reader :
            {
                type  : 'json'
                ,root : 'slist1'
            }
        }
    });
    
    venExcluStoreDisp = new Ext.data.Store(
    {
    	model      : 'ModeloExclusion'
    	,autoLoad  : false
    	,proxy     :
    	{
    		url     : venExcluUrlCargarDisp
    		,type   : 'ajax'
    		,reader :
    		{
    			type  : 'json'
    			,root : 'slist1'
    		}
    	}
    });
    
    venExcluStoreUsa = new Ext.data.Store(
    {
        model      : 'ModeloExclusion'
        ,autoLoad  : true
        ,proxy     :
        {
            url     : venExcluUrlCargar
            ,extraParams :
            {
                'smap1.pv_cdunieco'  : inputCduniecopx
                ,'smap1.pv_cdramo'   : inputCdramopx
                ,'smap1.pv_estado'   : inputEstadopx
                ,'smap1.pv_nmpoliza' : inputNmpolizapx
                ,'smap1.pv_nmsituac' : inputNmsituacpx
            }
            ,type   : 'ajax'
            ,reader :
            {
                type  : 'json'
                ,root : 'slist1'
            }
        }
    });
    /*////////////////*/
    ////// stores //////
    ////////////////////
    
    /////////////////////////
    ////// componentes //////
    /*/////////////////////*/
    /*/////////////////////*/
    ////// componentes //////
    /////////////////////////
    
    ///////////////////////
    ////// contenido //////
    /*///////////////////*/
    Ext.create('Ext.panel.Panel',
    {
    	border    : 0
    	,renderTo : 'maindiv_scr_exclu'
    	,items    :
    	[
    	    Ext.create('Ext.form.field.ComboBox',
    	    {
    	    	id              : 'idComboTipCla'
    	    	,store          : venExcluStoreTipos
    	    	,displayField   : 'dstipcla'
    	    	,valueField     : 'cdtipcla'
    	    	,editable       : false
    	    	,forceSelection : true
    	    	,style          : 'margin:5px'
    	    	,fieldLabel     : 'Tipo de cl&aacute;usula'
    	    	,width          : 400
    	    	,listeners      :
    	    	{
    	    		change : function(me,value)
    	    		{
    	    			debug(value);
    	    			venExcluStoreDisp.load(
    	    			{
    	    				params : { 'smap1.pv_cdtipcla_i' : ''+value}
    	    			});
    	    		}
    	    	}
    	    })
    	    ,Ext.create('Ext.grid.Panel',
    	    {
    	    	id             : 'venExcluGridDisp'
    	    	,title         : 'Cl&aacute;usulas disponibles'
    	    	,store         : venExcluStoreDisp
    	    	,collapsible   : true
    	    	,titleCollapse : true
    	    	,style         : 'margin:5px'
   	    		,height        : 200
    	    	,columns       :
    	    	[
    	    	    {
    	    	    	header     : 'Nombre'
    	    	    	,dataIndex : 'dsclausu'
    	    	    	,flex      : 1
    	    	    }
    	    	    ,{
    	    	    	menuDisabled : true
    	    	    	,xtype       : 'actioncolumn'
    	    	    	,width       : 30
    	    	    	,items       :
    	    	    	[
    	    	    	    {
    	    	    	    	icon     : venExcluContexto+'/resources/fam3icons/icons/add.png'
    	    	    	    	,tooltip : 'Agregar cl&aacute;usula'
    	    	    	    	,handler : function(me,rowIndex)
    	    	    	    	{
    	    	    	    		debug(rowIndex);
    	    	    	    		var record=venExcluStoreDisp.getAt(rowIndex);
    	    	    	    		Ext.Ajax.request(
    	    	    	    		{
    	    	    	    			url     : venExcluUrlLoadHtml
    	    	    	    			,params :
    	    	    	    			{
    	    	    	    				'smap1.pv_cdclausu_i' : record.get('cdclausu')
    	    	    	    			}
    	    	    	    		    ,success : function(response)
    	    	    	    		    {
    	    	    	    		    	var json=Ext.decode(response.responseText);
    	    	    	    		    	if(json.success==true)
   	    	    	    		    		{
   	    	    	    		    		    var exclu=json.smap1;
   	    	    	    		    		    debug(exclu);
   	    	    	    		    		    Ext.create('Ext.window.Window',
   	    	    	    		    		    {
   	    	    	    		    		    	title        : 'Detalle de '+exclu.dsclausu
   	    	    	    		    		    	,modal       : true
   	    	    	    		    		    	,buttonAlign : 'center'
   	    	    	    		    		    	,width       : 600
   	    	    	    		    		    	,height      : 400
   	    	    	    		    		    	,items       :
   	    	    	    		    		    	[
														Ext.create('Ext.form.HtmlEditor', {
														    id        : 'venExcluHtmlInputCopy'
														    ,width    : 580
														    ,height   : 380
														    ,value    : exclu.dslinea//viene del lector individual con dslinea
														    ,readOnly : true
														})
														,{
															id      : 'venExcluHidenInputCopy'
															,xtype  : 'textfield'
															,hidden : true
															,value  : '0'
														}
   	    	    	    		    		    	]
   	    	    	    		    		        ,buttons     :
   	    	    	    		    		        [
   	    	    	    		    		            {
   	    	    	    		    		            	id       : 'venExcluHtmlCopyWindowBoton1'
   	    	    	    		    		            	,text    : 'Editar'
   	    	    	    		    		            	,icon    : venExcluContexto+'/resources/fam3icons/icons/pencil.png'
   	    	    	    		    		            	,handler : function(me)
   	    	    	    		    		            	{
   	    	    	    		    		            		debug(me);
   	    	    	    		    		            		me.setDisabled(true);
   	    	    	    		    		            		Ext.getCmp('venExcluHidenInputCopy').setValue('1');
   	    	    	    		    		            		Ext.getCmp('venExcluHtmlInputCopy').setReadOnly(false);
   	    	    	    		    		            	}
   	    	    	    		    		            }
   	    	    	    		    		            ,{
   	    	    	    		    		            	id       : 'venExcluHtmlCopyWindowBoton2'
                                                            ,text    : 'Agregar'
                                                            ,icon    : venExcluContexto+'/resources/fam3icons/icons/add.png'
                                                            ,handler : function(me)
                                                            {
                                                                debug(me);
                                                                me.up().up().setLoading(true);
                                                                Ext.Ajax.request(
                                                                {
                                                                    url     : venExcluUrlAddExclu
                                                                    ,params : 
                                                                    {
                                                                        'smap1.pv_cdunieco_i'  : inputCduniecopx
                                                                        ,'smap1.pv_cdramo_i'   : inputCdramopx
                                                                        ,'smap1.pv_estado_i'   : inputEstadopx
                                                                        ,'smap1.pv_nmpoliza_i' : inputNmpolizapx
                                                                        ,'smap1.pv_nmsituac_i' : inputNmsituacpx
                                                                        ,'smap1.pv_cdclausu_i' : record.get('cdclausu')
                                                                        ,'smap1.pv_nmsuplem_i' : '0'
                                                                        ,'smap1.pv_status_i'   : 'V'
                                                                        ,'smap1.pv_cdtipcla_i' : Ext.getCmp('idComboTipCla').getValue()
                                                                        ,'smap1.pv_swmodi_i'   : ''
                                                                        ,'smap1.pv_accion_i'   : 'I'
                                                                        ,'smap1.pv_dslinea_i'  :
                                                                        	Ext.getCmp('venExcluHidenInputCopy').getValue()=='1'?
                                                                        			Ext.getCmp('venExcluHtmlInputCopy').getValue():''
                                                                    }
                                                                    ,success : function (response)
                                                                    {
                                                                        var json=Ext.decode(response.responseText);
                                                                        if(json.success==true)
                                                                        {
                                                                        	me.up().up().destroy();
                                                                            venExcluStoreDisp.remove(record)
                                                                            venExcluStoreUsa.load();
                                                                            //venExcluStoreUsa.add(record);
                                                                            //Ext.getCmp('venExcluGridUsaId').getView().refresh();
                                                                        }
                                                                        else
                                                                        {
                                                                        	me.up().up().setLoading(false);
                                                                            Ext.Msg.show({
                                                                                title:'Error',
                                                                                msg: 'Error al agregar cl&aacute;usula',
                                                                                buttons: Ext.Msg.OK,
                                                                                icon: Ext.Msg.ERROR
                                                                            });
                                                                        }
                                                                    }
                                                                    ,failure : function ()
                                                                    {
                                                                    	me.up().up().setLoading(false);
                                                                        Ext.Msg.show({
                                                                            title:'Error',
                                                                            msg: 'Error de comunicaci&oacute;n',
                                                                            buttons: Ext.Msg.OK,
                                                                            icon: Ext.Msg.ERROR
                                                                        });
                                                                    }
                                                                });
                                                            }
   	    	    	    		    		            }
   	    	    	    		    		        ]
   	    	    	    		    		    }).show();
   	    	    	    		    		}
    	    	    	    		    	else
    	    	    	    		    	{
    	    	    	    		    		Ext.Msg.show({
                                                    title:'Error',
                                                    msg: 'Error al cargar',
                                                    buttons: Ext.Msg.OK,
                                                    icon: Ext.Msg.ERROR
                                                });
    	    	    	    		    	}
    	    	    	    		    }
    	    	    	    		    ,failure : function()
    	    	    	    		    {
    	    	    	    		    	Ext.Msg.show({
                                                title:'Error',
                                                msg: 'Error de comunicaci&oacute;n',
                                                buttons: Ext.Msg.OK,
                                                icon: Ext.Msg.ERROR
                                            });
    	    	    	    		    }
    	    	    	    		});
    	    	    	    	}
    	    	    	    }
    	    	    	]
    	    	    }
    	    	]
    	    })
    	    ,Ext.create('Ext.grid.Panel',
            {
                title          : 'Cl&aacute;usulas indicadas'
                ,id            : 'venExcluGridUsaId' 
                ,store         : venExcluStoreUsa
                ,collapsible   : true
                ,titleCollapse : true
                ,style         : 'margin:5px'
                ,height        : 200
                ,buttonAlign   : 'center'
                ,columns       :
                [
                    {
                        header     : 'Nombre'
                        ,dataIndex : 'dsclausu'
                        ,flex      : 1
                    }
                    ,{
                        menuDisabled : true
                        ,xtype       : 'actioncolumn'
                        ,width       : 30
                        ,items       :
                        [
                            {
                                icon     : venExcluContexto+'/resources/fam3icons/icons/pencil.png'
                                ,tooltip : 'Editar detalle'
                                ,handler : function(me,rowIndex)
                                {
                                    debug(rowIndex);
                                    var record=venExcluStoreUsa.getAt(rowIndex);
                                    debug(record);
                                    Ext.create('Ext.window.Window',
                                    {
                                        title        : 'Detalle de '+record.get('dsclausu')
                                        ,modal       : true
                                        ,buttonAlign : 'center'
                                        ,width       : 600
                                        ,height      : 400
                                        ,items       :
                                        [
                                            Ext.create('Ext.form.HtmlEditor', {
                                                id        : 'venExcluHtmlInputEdit'
                                                ,width    : 580
                                                ,height   : 380
                                                ,value    : record.get('linea_usuario')&&record.get('linea_usuario').length>0?
                                                		record.get('linea_usuario'):record.get('linea_general')
                                                ,readOnly : true
                                            })
                                            ,{
                                                id      : 'venExcluHidenInputEdit'
                                                ,xtype  : 'textfield'
                                                ,hidden : true
                                                ,value  : '0'
                                            }
                                        ]
                                        ,buttons     :
                                        [
                                            {
                                                text     : 'Editar'
                                                ,icon    : venExcluContexto+'/resources/fam3icons/icons/pencil.png'
                                                ,handler : function(me)
                                                {
                                                    debug(me);
                                                    me.setDisabled(true);
                                                    Ext.getCmp('venExcluHidenInputEdit').setValue('1');
                                                    Ext.getCmp('venExcluHtmlInputEdit').setReadOnly(false);
                                                }
                                            }
                                            ,{
                                                text     : 'Guardar'
                                                ,icon    : venExcluContexto+'/resources/fam3icons/icons/disk.png'
                                                ,handler : function(me)
                                                {
                                                    debug(me);
                                                    if(Ext.getCmp('venExcluHidenInputEdit').getValue()=='1')
                                                    {
	                                                    me.up().up().setLoading(true);
	                                                    Ext.Ajax.request(
	                                                    {
	                                                        url     : venExcluUrlAddExclu
	                                                        ,params : 
	                                                        {
	                                                            'smap1.pv_cdunieco_i'  : inputCduniecopx
	                                                            ,'smap1.pv_cdramo_i'   : inputCdramopx
	                                                            ,'smap1.pv_estado_i'   : inputEstadopx
	                                                            ,'smap1.pv_nmpoliza_i' : inputNmpolizapx
	                                                            ,'smap1.pv_nmsituac_i' : inputNmsituacpx
	                                                            ,'smap1.pv_cdclausu_i' : record.get('cdclausu')
	                                                            ,'smap1.pv_nmsuplem_i' : '0'
	                                                            ,'smap1.pv_status_i'   : 'V'
	                                                            ,'smap1.pv_cdtipcla_i' : record.get('cdtipcla')
	                                                            ,'smap1.pv_swmodi_i'   : ''
	                                                            ,'smap1.pv_accion_i'   : 'U'
	                                                            ,'smap1.pv_dslinea_i'  :
	                                                                Ext.getCmp('venExcluHidenInputEdit').getValue()=='1'?
	                                                                        Ext.getCmp('venExcluHtmlInputEdit').getValue():''
	                                                        }
	                                                        ,success : function (response)
	                                                        {
	                                                            var json=Ext.decode(response.responseText);
	                                                            if(json.success==true)
	                                                            {
	                                                                me.up().up().destroy();
	                                                                venExcluStoreDisp.remove(record)
	                                                                venExcluStoreUsa.load();
	                                                                //venExcluStoreUsa.add(record);
	                                                                //Ext.getCmp('venExcluGridUsaId').getView().refresh();
	                                                            }
	                                                            else
	                                                            {
	                                                                me.up().up().setLoading(false);
	                                                                Ext.Msg.show({
	                                                                    title:'Error',
	                                                                    msg: 'Error al agregar cl&aacute;usula',
	                                                                    buttons: Ext.Msg.OK,
	                                                                    icon: Ext.Msg.ERROR
	                                                                });
	                                                            }
	                                                        }
	                                                        ,failure : function ()
	                                                        {
	                                                            me.up().up().setLoading(false);
	                                                            Ext.Msg.show({
	                                                                title:'Error',
	                                                                msg: 'Error de comunicaci&oacute;n',
	                                                                buttons: Ext.Msg.OK,
	                                                                icon: Ext.Msg.ERROR
	                                                            });
	                                                        }
	                                                    });
                                                    }
                                                    else
                                                    {
                                                    	me.up().up().destroy();
                                                    }
                                                }
                                            }
                                        ]
                                    }).show();
                                }
                            }
                        ]
                    }
                ]
    	        ,buttons:
    	        [
    	            /*{
    	            	text     : 'Guardar'
    	            	,icon    : venExcluContexto+'/resources/fam3icons/icons/disk.png'
    	            	,handler : function(but)
    	            	{
    	            		var me=but;
    	            		me.up().up().setLoading(true);
    	            		var slist1=[];
    	            		venExcluStoreUsa.each(function(record,index)
    	            		{
    	            			slist1.push(
    	            			{
    	            				'cd'    : record.get('cd')
    	            				,'ds'   : record.get('ds')
    	            			});
    	            		});
    	            		var submitValues={};
    	            		submitValues['slist1']=slist1;
    	            		var smap1={
   	            				cdunieco  : inputCduniecopx
   	                            ,cdramo   : inputCdramopx
   	                            ,estado   : inputEstadopx
   	                            ,nmpoliza : inputNmpolizapx
   	                            ,nmsituac : inputNmsituacpx
   	                            ,cdperson : inputCdpersonpx
   	                            ,cdrol    : inputCdrolpx
    	            		};
    	            		submitValues['smap1']=smap1;
    	            		debug('submit',Ext.encode(submitValues));
    	            		Ext.Ajax.request(
    	            		{
    	            			url      : venExcluUrlGuardar
    	            			,jsonData : Ext.encode(submitValues)
    	            			,success : function()
    	            			{
		    	            		me.up().up().setLoading(false);
		    	            		debug('success');
    	            			}
    	            		    ,failure : function()
    	            		    {
    	            		    	me.up().up().setLoading(false);
    	            		    	debug('failure');
    	            		    }
    	            		});
    	            	}
    	            }*/
    	            {
                        text     : 'Aceptar'
                        ,icon    : venExcluContexto+'/resources/fam3icons/icons/accept.png'
                        ,handler : function()
                        {
                        	expande(2);
                        }
                    }
    	        ]
            })
    	]
    });
    /*///////////////////*/
    ////// contenido //////
    ///////////////////////
    
    //////////////////////
    ////// cargador //////
    /*//////////////////*/
    /*//////////////////*/
    ////// cargador //////
    //////////////////////
    
});
</script>
<div id="maindiv_scr_exclu"></div>