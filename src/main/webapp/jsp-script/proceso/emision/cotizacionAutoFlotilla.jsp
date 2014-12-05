<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
////// overrides //////
////// overrides //////

////// urls //////
var _p30_urlCargarSumaAseguradaRamo5       = '<s:url namespace="/emision"   action="cargarSumaAseguradaRamo5"       />';
var _p30_urlCargarCduniecoAgenteAuto       = '<s:url namespace="/emision"   action="cargarCduniecoAgenteAuto"       />';
var _p30_urlCargarRetroactividadSuplemento = '<s:url namespace="/emision"   action="cargarRetroactividadSuplemento" />';
var _p30_urlCargarParametros               = '<s:url namespace="/emision"   action="obtenerParametrosCotizacion"    />';
var _p30_urlRecuperarCliente               = '<s:url namespace="/"          action="buscarPersonasRepetidas"        />';
var _p30_urlCargarCatalogo                 = '<s:url namespace="/catalogos" action="obtieneCatalogo"                />';
var _p30_urlCotizar                        = '<s:url namespace="/emision"   action="cotizarAutosFlotilla"           />';
////// urls //////

////// variables //////
var _p30_smap1 = <s:property value="%{convertToJSON('smap1')}" escapeHtml="false" />;
debug('_p30_smap1:',_p30_smap1);
var _p28_smap1 =
{
    cdtipsit : _p30_smap1.cdtipsit
};
debug('_p28_smap1:',_p28_smap1);

var _p30_windowAuto              = null;
var _p30_store                   = null;
var _p30_selectedRecord          = null;
var _p30_recordClienteRecuperado = null;

var _p30_storeSubmarcasRamo5 = null;
var _p30_storeVersionesRamo5 = null;
var _p30_storeUsosRamo5      = null;
var _p30_storeMarcasRamo5    = null;
////// variables //////

////// dinamicos //////
var _p30_gridColsConf =
[
    <s:if test='%{getImap().get("gridCols")!=null}'>
        <s:property value="imap.gridCols" />
    </s:if>
];
var _p30_gridCols =
[
    { xtype : 'rownumberer' }
    ,{
        sortable      : false
        ,menuDisabled : true
        ,dataIndex    : 'personalizado'
        ,renderer     : function(v)
        {
            var r='';
            if(v+'x'=='six')
            {
                r='<img src="${ctx}/resources/fam3icons/icons/tag_blue_edit.png" />';
            }
            return r;
        }
    }
];
for(var i=0;i<_p30_gridColsConf.length;i++)
{
    _p30_gridCols.push(_p30_gridColsConf[i]);
}
for(var i=0;i<_p30_gridCols.length;i++)
{
    if(!Ext.isEmpty(_p30_gridCols[i].editor)&&_p30_gridCols[i].editor.readOnly)
    {
        _p30_gridCols[i].editor='';
    }
}
_p30_gridCols.push(
{
    xtype  : 'actioncolumn'
    ,items :
    [
        {
            tooltip  : 'Seleccionar auto'
            ,icon    : '${ctx}/resources/fam3icons/icons/car.png'
            ,handler : _p30_gridBotonAutoClic
        }
        ,{
            tooltip  : 'Configurar plan'
            ,icon    : '${ctx}/resources/fam3icons/icons/cog.png'
            ,handler : _p30_gridBotonConfigClic
        }
        ,{
            tooltip  : 'Eliminar'
            ,icon    : '${ctx}/resources/fam3icons/icons/delete.png'
            ,handler : _p30_gridBotonEliminarClic
        }
    ]
}
);

var _p30_panel1ItemsConf =
[
    <s:if test='%{getImap().get("panel1Items")!=null}'>
        <s:property value="imap.panel1Items" />
    </s:if>
];

var _p30_panel2ItemsConf =
[
    <s:if test='%{getImap().get("panel2Items")!=null}'>
        <s:property value="imap.panel2Items" />
    </s:if>
];

var _p30_panel3ItemsConf =
[
    <s:if test='%{getImap().get("panel3Items")!=null}'>
        <s:property value="imap.panel3Items" />
    </s:if>
];

var _p30_panel5ItemsConf =
[
    <s:if test='%{getImap().get("panel5Items")!=null}'>
        <s:property value="imap.panel5Items" />
    </s:if>
];

var _p30_panel6ItemsConf =
[
    <s:if test='%{getImap().get("panel6Items")!=null}'>
        <s:property value="imap.panel6Items" />
    </s:if>
];

var _p30_paneles  = [];
<s:iterator value="imap">
    <s:if test='%{key.substring(0,"paneldin_".length()).equals("paneldin_")}'>
        _p30_paneles['<s:property value='%{key.substring("paneldin_".length())}' />']=Ext.create('Ext.window.Window',
        {
            title        : ''
            ,modal       : true
            ,closeAction : 'hide'
            ,maxHeight   : 600
            ,width       : 850
            ,autoScroll  : true
            ,callback    : false
            ,valores     : false
            ,items       :
            [
                Ext.create('Ext.form.Panel',
                {
                    itemId    : '_p30_form_panel_<s:property value='%{key.substring("paneldin_".length())}' />'
                    ,defaults : { style : 'margin:5px;' }
                    ,border   : 0
                    ,layout   :
                    {
                        type     : 'table'
                        ,columns : 3
                    }
                    ,items       : [ <s:property value="value" /> ]
                    ,buttonAlign : 'center'
                    ,buttons     :
                    [
                        {
                            text     : 'Aceptar'
                            ,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
                            ,handler : function(me){ me.up('window').callback(); }
                        }
                        ,{
                            text     : 'Cancelar'
                            ,icon    : '${ctx}/resources/fam3icons/icons/cancel.png'
                            ,handler : function(me){ me.up('window').hide();}
                        }
                    ]
                })
            ]
        });
    </s:if>
    <s:set var="contador" value="#contador+1" />
</s:iterator>

var _p30_gridTbarItems =
[
    {
        text     : 'Agregar'
        ,icon    : '${ctx}/resources/fam3icons/icons/add.png'
        ,handler : function(){_p30_agregarAuto();}
    }
    ,'->'
];

var _f1_botones =[];
for(var i in _p30_smap1)
{
    if(i.slice(0,6)=='boton_')
    {
        _f1_botones.push(
        {
            text      : i.split('_')[1]
            ,icon     : '${ctx}/resources/fam3icons/icons/cog.png'
            ,cdtipsit : _p30_smap1[i]
            ,handler  : function(me){_p30_configuracionPanelDinClic(me.cdtipsit,me.text);}
        });
    }
}
if(_f1_botones.length>1)
{
    for(var i=0;i<_f1_botones.length-1;i++)
    {
        for(var j=i+1;j<_f1_botones.length;j++)
        {
            if(_f1_botones[j].text<_f1_botones[i].text)
            {
                var _f1_aux=_f1_botones[j];
                _f1_botones[j]=_f1_botones[i];
                _f1_botones[i]=_f1_aux;
            }
        }
    }
    for(var i=0;i<_f1_botones.length;i++)
    {
        _p30_gridTbarItems.push(_f1_botones[i]);
    }
}
else if(_f1_botones.length==1)
{
    _p30_gridTbarItems.push(_f1_botones[0]);
}
////// dinamicos //////

Ext.onReady(function()
{
	////// modelos //////
	Ext.define('_p30_modelo',
	{
	    extend  : 'Ext.data.Model'
	    ,fields :
	    [
	         'parametros.pv_otvalor01','parametros.pv_otvalor02','parametros.pv_otvalor03','parametros.pv_otvalor04','parametros.pv_otvalor05'
            ,'parametros.pv_otvalor06','parametros.pv_otvalor07','parametros.pv_otvalor08','parametros.pv_otvalor09','parametros.pv_otvalor10'
            ,'parametros.pv_otvalor11','parametros.pv_otvalor12','parametros.pv_otvalor13','parametros.pv_otvalor14','parametros.pv_otvalor15'
            ,'parametros.pv_otvalor16','parametros.pv_otvalor17','parametros.pv_otvalor18','parametros.pv_otvalor19','parametros.pv_otvalor20'
            ,'parametros.pv_otvalor21','parametros.pv_otvalor22','parametros.pv_otvalor23','parametros.pv_otvalor24','parametros.pv_otvalor25'
            ,'parametros.pv_otvalor26','parametros.pv_otvalor27','parametros.pv_otvalor28','parametros.pv_otvalor29','parametros.pv_otvalor30'
            ,'parametros.pv_otvalor31','parametros.pv_otvalor32','parametros.pv_otvalor33','parametros.pv_otvalor34','parametros.pv_otvalor35'
            ,'parametros.pv_otvalor36','parametros.pv_otvalor37','parametros.pv_otvalor38','parametros.pv_otvalor39','parametros.pv_otvalor40'
            ,'parametros.pv_otvalor41','parametros.pv_otvalor42','parametros.pv_otvalor43','parametros.pv_otvalor44','parametros.pv_otvalor45'
            ,'parametros.pv_otvalor46','parametros.pv_otvalor47','parametros.pv_otvalor48','parametros.pv_otvalor49','parametros.pv_otvalor50'
            ,'parametros.pv_otvalor51','parametros.pv_otvalor52','parametros.pv_otvalor53','parametros.pv_otvalor54','parametros.pv_otvalor55'
            ,'parametros.pv_otvalor56','parametros.pv_otvalor57','parametros.pv_otvalor58','parametros.pv_otvalor59','parametros.pv_otvalor60'
            ,'parametros.pv_otvalor61','parametros.pv_otvalor62','parametros.pv_otvalor63','parametros.pv_otvalor64','parametros.pv_otvalor65'
            ,'parametros.pv_otvalor66','parametros.pv_otvalor67','parametros.pv_otvalor68','parametros.pv_otvalor69','parametros.pv_otvalor70'
            ,'parametros.pv_otvalor71','parametros.pv_otvalor72','parametros.pv_otvalor73','parametros.pv_otvalor74','parametros.pv_otvalor75'
            ,'parametros.pv_otvalor76','parametros.pv_otvalor77','parametros.pv_otvalor78','parametros.pv_otvalor79','parametros.pv_otvalor80'
            ,'parametros.pv_otvalor81','parametros.pv_otvalor82','parametros.pv_otvalor83','parametros.pv_otvalor84','parametros.pv_otvalor85'
            ,'parametros.pv_otvalor86','parametros.pv_otvalor87','parametros.pv_otvalor88','parametros.pv_otvalor89','parametros.pv_otvalor90'
            ,'parametros.pv_otvalor91','parametros.pv_otvalor92','parametros.pv_otvalor93','parametros.pv_otvalor94','parametros.pv_otvalor95'
            ,'parametros.pv_otvalor96','parametros.pv_otvalor97','parametros.pv_otvalor98','parametros.pv_otvalor99'
            ,'cdplan','cdtipsit','personalizado'
        ]
	});
	
	Ext.define('_p30_modeloRecuperado',
    {
        extend  : 'Ext.data.Model'
        ,fields :
        [
            'NOMBRECLI'
            ,'DIRECCIONCLI'
        ]
    });
	////// modelos //////
	
	////// stores //////
	_p30_store = Ext.create('Ext.data.Store',
	{
	    model : '_p30_modelo'
	});
	
	_p30_storeSubmarcasRamo5 = Ext.create('Ext.data.Store',
    {
        model     : 'Generic'
        ,cargado  : false
        ,autoLoad : _p30_smap1.cdramo+'x'=='5x'
        ,proxy    :
        {
            type    : 'ajax'
            ,url    : _p30_urlCargarCatalogo
            ,extraParams :
            {
                'catalogo' : 'RAMO_5_SUBMARCAS'
            }
            ,reader :
            {
                type  : 'json'
                ,root : 'lista'
            }
        }
        ,listeners :
        {
            load : function()
            {
                this.cargado=true;
                _fieldById('_p30_grid').getView().refresh();
            }
        }
    });
    
    _p30_storeVersionesRamo5 = Ext.create('Ext.data.Store',
    {
        model     : 'Generic'
        ,cargado  : false
        ,autoLoad : _p30_smap1.cdramo+'x'=='5x'
        ,proxy    :
        {
            type    : 'ajax'
            ,url    : _p30_urlCargarCatalogo
            ,extraParams :
            {
                'catalogo' : 'RAMO_5_VERSIONES'
            }
            ,reader :
            {
                type  : 'json'
                ,root : 'lista'
            }
        }
        ,listeners :
        {
            load : function()
            {
                this.cargado=true;
                _fieldById('_p30_grid').getView().refresh();
            }
        }
    });
    
    _p30_storeUsosRamo5 = Ext.create('Ext.data.Store',
    {
        model     : 'Generic'
        ,cargado  : false
        ,autoLoad : _p30_smap1.cdramo+'x'=='5x'
        ,proxy    :
        {
            type    : 'ajax'
            ,url    : _p30_urlCargarCatalogo
            ,extraParams :
            {
                'catalogo' : 'RAMO_5_TIPOS_USO'
            }
            ,reader :
            {
                type  : 'json'
                ,root : 'lista'
            }
        }
        ,listeners :
        {
            load : function()
            {
                this.cargado=true;
                _fieldById('_p30_grid').getView().refresh();
            }
        }
    });
    
    _p30_storeMarcasRamo5 = Ext.create('Ext.data.Store',
    {
        model     : 'Generic'
        ,cargado  : false
        ,autoLoad : _p30_smap1.cdramo+'x'=='5x'
        ,proxy    :
        {
            type    : 'ajax'
            ,url    : _p30_urlCargarCatalogo
            ,extraParams :
            {
                'catalogo' : 'RAMO_5_MARCAS'
            }
            ,reader :
            {
                type  : 'json'
                ,root : 'lista'
            }
        }
        ,listeners :
        {
            load : function()
            {
                this.cargado=true;
                _fieldById('_p30_grid').getView().refresh();
            }
        }
    });
	////// stores //////
	
	////// componentes //////
	var _p30_panel1Items =
	[
	    {
	        xtype   : 'fieldset'
	        ,border : 0
	        ,items  :
	        [
	            {
                    layout  :
                    {
                        type     : 'table'
                        ,columns : 2
                    }
                    ,border : 0
                    ,items  :
                    [
                        {
                            xtype        : 'numberfield'
                            ,fieldLabel  : 'FOLIO'
                            ,name        : 'nmpoliza'
                            ,style       : 'margin:5px;'
                            ,listeners   :
                            {
                                change : _p30_nmpolizaChange
                            }
                        }
                        ,{
                            xtype    : 'button'
                            ,itemId  : '_p30_botonCargar'
                            ,text    : 'BUSCAR'
                            ,icon    : '${ctx}/resources/fam3icons/icons/zoom.png'
                            /*,handler : _p28_cargar*/
                        }
                    ]
                }
            ]
        }
        ,{
            xtype   : 'fieldset'
            ,itemId : '_p30_panel3Fieldset'
            ,title  : '<span style="font:bold 14px Calibri;">CLIENTE</span>'
            ,items  : _p30_panel3ItemsConf
        }
	];
	for(var i=0;i<_p30_panel1ItemsConf.length;i++)
	{
	    _p30_panel1Items[0].items.push(_p30_panel1ItemsConf[i]);
	}
	_p30_panel1Items[0].items.push(
	{
        xtype       : 'datefield'
        ,name       : 'feini'
        ,fieldLabel : 'INICIO DE VIGENCIA'
        ,value      : new Date()
        ,style      : 'margin:5px;'
    }
    ,{
        xtype       : 'datefield'
        ,name       : 'fefin'
        ,fieldLabel : 'FIN DE VIGENCIA'
        ,value      : Ext.Date.add(new Date(),Ext.Date.YEAR,1)
        ,minValue   : Ext.Date.add(new Date(),Ext.Date.DAY,1)
        ,style      : 'margin:5px;'
    });
	////// componentes //////
	
	////// contenido //////
	Ext.create('Ext.panel.Panel',
	{
	    renderTo  : '_p30_divpri'
	    ,itemId   : '_p30_panelpri'
	    ,border   : 0
	    ,defaults : { style : 'margin:5px;' }
	    ,items    :
	    [
	        Ext.create('Ext.form.Panel',
	        {
	            itemId    : '_p30_form'
	            ,title    : 'DATOS GENERALES'
	            ,defaults : { style : 'margin:5px;' }
	            ,layout   :
	            {
	                type     : 'table'
	                ,columns : 2
	                ,tdAttrs : {valign:'top'}
	            }
	            ,items    : _p30_panel1Items
	        })
	        ,Ext.create('Ext.grid.Panel',
	        {
	            itemId      : '_p30_grid'
	            ,title      : 'INCISOS'
	            ,tbar       : _p30_gridTbarItems
	            ,bbar       :
	            [
	                '->'
	                ,{
	                    xtype       : 'checkbox'
	                    ,boxLabel   : '<span style="color:white;">Tomar configuraci&oacute;n de carga masiva</span>'
	                    ,name       : 'tomarMasiva'
	                    ,inputValue : '1'
	                }
	                ,{
	                    xtype         : 'filefield'
	                    ,buttonOnly   : true
	                    ,buttonConfig :
	                    {
	                        text  : 'Carga masiva...'
	                        ,icon : '${ctx}/resources/fam3icons/icons/book_next.png'
	                    }
	                    ,listeners :
	                    {
	                        change : function(me)
                            {
                                var indexofPeriod = me.getValue().lastIndexOf("."),
                                uploadedExtension = me.getValue().substr(indexofPeriod + 1, me.getValue().length - indexofPeriod).toLowerCase();
                                debug('uploadedExtension:',uploadedExtension);
                                if (!Ext.Array.contains(['xls','xlsx'], uploadedExtension))
                                {
                                    mensajeWarning('Solo se permiten hojas de c&aacute;lculo');
                                    me.reset();
                                }
                                else
                                {
                                    //qwe
                                }
                            }
	                    }
	                }
	            ]
	            ,columns    : _p30_gridCols
	            ,height     : 350
	            ,viewConfig : viewConfigAutoSize
	            ,store      : _p30_store
	            ,plugins    : Ext.create('Ext.grid.plugin.RowEditing',
	            {
	                clicksToEdit  : 1
	                ,errorSummary : false
	                ,listeners    :
	                {
	                    beforeedit : function()
	                    {
	                        if(_p30_smap1.cdramo+'x'=='5x')
	                        {
	                            var cdnegocio = _fieldByLabel('NEGOCIO').getValue();
	                            if(!Ext.isEmpty(cdnegocio))
	                            {
	                                var tipoUsoName = _fieldById('_p30_grid').down('[text=TIPO USO]').dataIndex;
	                                var tipoVehName = _fieldById('_p30_grid').down('[text*=TIPO VEH]').dataIndex;
	                                var tipoUsoComp = Ext.ComponentQuery.query('[id*=editor_][name='+tipoUsoName+']')[0];
	                                var tipoVehComp = Ext.ComponentQuery.query('[id*=editor_][name='+tipoVehName+']')[0];
	                                debug('tipoUsoComp:',tipoUsoComp,'tipoVehComp:',tipoVehComp);
	                                tipoVehComp.on(
	                                {
	                                    select : function(me,rec)
	                                    {
	                                        debug('select:',rec[0].get('key'));
	                                        tipoUsoComp.getStore().load(
	                                        {
	                                            params :
	                                            {
	                                                'params.cdtipsit'   : rec[0].get('key')
	                                               ,'params.cdnegocio' : _fieldByLabel('NEGOCIO').getValue()
	                                            } 
	                                        });
	                                    }
	                                });
	                            }
	                            else
	                            {
	                                mensajeWarning('Seleccione el negocio');
	                                return false;
	                            }
	                        } 
	                    }
	                }
                })
	        })
	        ,Ext.create('Ext.panel.Panel',
	        {
	            itemId       : '_p30_botonera'
	            ,buttonAlign : 'center'
                ,border      : 0
                ,buttons     :
                [
                    {
                        itemId   : '_p30_cotizarButton'
                        ,text    : 'Cotizar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/calculator.png'
                        ,handler : function(){_p30_cotizar();}
                    }
                    ,{
                        itemId   : '_p30_limpiarButton'
                        ,text    : 'Limpiar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/arrow_refresh.png'
                        ,handler : function(){_p30_limpiar();}
                    }
                ]
	        })
	    ]
	});
	
	_p30_windowAuto = Ext.create('Ext.window.Window',
	{
	    title        : 'B&Uacute;SQUEDA DE VEH&Iacute;CULO'
	    ,closeAction : 'hide'
	    ,modal       : true
	    ,items       :
	    [
	        Ext.create('Ext.form.Panel',
	        {
	            itemId    : '_p30_formAuto'
	            ,border   : 0
	            ,defaults : { style : 'margin:5px;' }
	            ,items    : _p30_panel2ItemsConf
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        text     : 'Aceptar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
                        ,handler : _p30_windowAutoAceptarClic
                    }
                    ,{
                        text     : 'Cancelar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/cancel.png'
                        ,handler : function(me){ me.up('window').hide();}
                    }
                ]
	        })
	    ]
	});
	////// contenido //////
	
	////// custom //////
	
	//fechas
    _fieldByName('feini').on(
    {
        change : function(me,val)
        {
            debug('val:',val);
            var fefin = _fieldByName('fefin');
            fefin.setMinValue(Ext.Date.add(val,Ext.Date.DAY,1));
            fefin.isValid();
        }
    });
    //fechas
    
    //ramo 5
    if(_p30_smap1.cdramo+'x'=='5x')
    {
        //fechas
        _fieldByName('feini').on(
        {
            change : function(){_p30_calculaVigencia();}
        });
    
        _fieldByName('fefin').on(
        {
            change : function(){_p30_calculaVigencia();}
        });
        
        _p30_calculaVigencia();
        
        //clave gs
        _fieldByName('parametros.pv_otvalor06',_p30_windowAuto).on(
        {
            select : function(combo,records){ _p30_herenciaDescendiente(records[0]); }
        });
        //clave gs
        
        //version
        _fieldLikeLabel('VERSI',_p30_windowAuto).anidado = true;
        _fieldLikeLabel('VERSI',_p30_windowAuto).heredar = function()
        {
            _fieldLikeLabel('VERSI',_p30_windowAuto).getStore().load(
            {
                params :
                {
                    'params.submarca' : _fieldByLabel('SUBMARCA' , _p30_windowAuto).getValue()
                    ,'params.modelo'  : _fieldByLabel('MODELO'   , _p30_windowAuto).getValue()
                }
            });
        };
        _fieldLikeLabel('VERSI',_p30_windowAuto).on(
        {
            select : function(){_p30_herenciaAscendente();}
        });
        //version
        
        //modelo
        _fieldByLabel('MODELO',_p30_windowAuto).on(
        {
            select : function()
            {
                _fieldLikeLabel('VERSI',_p30_windowAuto).heredar();
            }
        });
        _fieldByLabel('MODELO',_p30_windowAuto).on(
        {
            select : function(){_p30_herenciaAscendente();}
        });
        //modelo
        
        //agente
        if(_p30_smap1.cdsisrol=='EJECUTIVOCUENTA')
        {
            _fieldByLabel('AGENTE').setValue(_p30_smap1.cdagente);
            _fieldByLabel('AGENTE').setReadOnly(true);
            _p30_ramo5AgenteSelect(_fieldByLabel('AGENTE'),_p30_smap1.cdagente);
        }
        else if(_p30_smap1.cdsisrol=='PROMOTORAUTO'
            ||_p30_smap1.cdsisrol=='SUSCRIAUTO')
        {
            _fieldByLabel('AGENTE').on(
            {
                'select' : _p30_ramo5AgenteSelect
            });
        }
        //agente
        
        //tipo valor
        var tipovalorName = _fieldById('_p30_grid').down('[text=TIPO VALOR]').dataIndex;
        for(var i=0;i<_p30_gridCols.length;i++)
        {
            debug('buscando editor tipo valor');
            if(!Ext.isEmpty(_p30_gridCols[i].editor)
                &&_p30_gridCols[i].editor.name==tipovalorName)
            {
                debug('tipo valor es:',_p30_gridCols[i].editor);
                _p30_gridCols[i].editor.on(
                {
                    change : function()
                    {
                        var record = _fieldById('_p30_grid').getSelectionModel().getSelection()[0];
                        debug('record sel:',record);
                        var valorName = _fieldById('_p30_grid').down('[text*=VALOR VEH]').dataIndex;
                        if(record.get(valorName)+'x'!='x')
                        {
                            mensajeWarning('Debe actualizar el valor del veh&iacute;culo');
                            record.set(valorName,'');
                            debug('cambiado:',valorName,record);
                        }
                    }
                });
            }
            else
            {
                debug('tipo valor no es:',_p30_gridCols[i].editor);
            }
        }
        //tipo valor
        
        //tipo vehiculo
        var tipoVehiName = _fieldById('_p30_grid').down('[text*=TIPO VEH]').dataIndex;
        for(var i=0;i<_p30_gridCols.length;i++)
        {
            debug('buscando editor tipo vehiculo');
            if(!Ext.isEmpty(_p30_gridCols[i].editor)
                &&_p30_gridCols[i].editor.name==tipoVehiName)
            {
                debug('tipo vehiculo es:',_p30_gridCols[i].editor);
                _p30_gridCols[i].editor.on(
                {
                    change : function()
                    {
                        var record    = _fieldById('_p30_grid').getSelectionModel().getSelection()[0];
                        var valorName = _fieldById('_p30_grid').down('[text*=VALOR VEH]').dataIndex;
                        var personalizado = false;
                        var conValor      = false;
                        if(record.get('personalizado')+'x'=='six')
                        {
                            personalizado = true;
                        }
                        if(record.get(valorName)+'x'!='x')
                        {
                            conValor = true;
                        }
                        record.set('personalizado' , '');
                        record.set(valorName       , '');
                        if(personalizado&&conValor)
                        {
                            mensajeWarning('Debe actualizar la configuraci&oacute;n del plan y el valor del veh&iacute;culo');
                        }
                        else if(conValor)
                        {
                            mensajeWarning('Debe actualizar el valor del veh&iacute;culo');
                        }
                        else if(personalizado)
                        {
                            mensajeWarning('Debe actualizar la configuraci&oacute;n del plan');
                        }
                    }
                });
            }
            else
            {
                debug('tipo vehiculo no es:',_p30_gridCols[i].editor);
            }
        }
        //tipo vehiculo
        
        //cliente nuevo
        _fieldLikeLabel('CLIENTE NUEVO').on(
        {
            change : _p30_ramo5ClienteChange
        });
        
        _fieldLikeLabel('CLIENTE NUEVO').getStore().on('load',function()
        {
            debug('combo cliente nuevo store load');
            _fieldLikeLabel('CLIENTE NUEVO').setValue('S');
        });
        //cliente nuevo
        
        //renderers
        _fieldById('_p30_grid').down('[text=SUBMARCA]').renderer=function(v)
        {
            if(_p30_storeSubmarcasRamo5.cargado&&v+'x'!='x')
            {
                var index = _p30_storeSubmarcasRamo5.find('key',v);
                if(index==-1)
                {
                    v='...';
                }
                else
                {
                    v=_p30_storeSubmarcasRamo5.getAt(index).get('value');
                }
            }
            else
            {
                v='';
            }
            return v;
        };
        _fieldById('_p30_grid').down('[text*=VERSI]').renderer=function(v)
        {
            if(_p30_storeVersionesRamo5.cargado&&v+'x'!='x')
            {
                var index = _p30_storeVersionesRamo5.find('key',v);
                if(index==-1)
                {
                    v='...';
                }
                else
                {
                    v=_p30_storeVersionesRamo5.getAt(index).get('value');
                }
            }
            else
            {
                v='';
            }
            return v;
        };
        _fieldById('_p30_grid').down('[text=TIPO USO]').renderer=function(v)
        {
            if(_p30_storeUsosRamo5.cargado&&v+'x'!='x')
            {
                var index = _p30_storeUsosRamo5.find('key',v);
                if(index==-1)
                {
                    v='...';
                }
                else
                {
                    v=_p30_storeUsosRamo5.getAt(index).get('value');
                }
            }
            else
            {
                v='';
            }
            return v;
        };
        _fieldById('_p30_grid').down('[text=MARCA]').renderer=function(v)
        {
            if(_p30_storeMarcasRamo5.cargado&&v+'x'!='x')
            {
                var index = _p30_storeMarcasRamo5.find('key',v);
                if(index==-1)
                {
                    v='...';
                }
                else
                {
                    v=_p30_storeMarcasRamo5.getAt(index).get('value');
                }
            }
            else
            {
                v='';
            }
            return v;
        };
        //renderers
        
        //negocio
        _fieldByLabel('NEGOCIO').on(
        {
            change : function(me,val)
            {
                if(me.findRecord('key',val)!=false)
                {
                    var tipoUsoName = _fieldById('_p30_grid').down('[text=TIPO USO]').dataIndex;
                    var marcaName   = _fieldById('_p30_grid').down('[text=MARCA]').dataIndex;
                    _p30_store.each(function(record)
                    {
                        record.set(tipoUsoName , '');
                        record.set(marcaName   , '');
                    });
                }
            }
        });
        //negocio
    }
    //ramo 5
	
	////// custom //////
	
	////// loaders //////
	////// loaders //////
});

////// funciones //////
function _p30_calculaVigencia()
{
    debug('>_p30_calculaVigencia');
    var feini = _fieldByName('feini');
    var fefin = _fieldByName('fefin');
    
    var itemVigencia=_fieldByLabel('VIGENCIA');
    itemVigencia.hide();
    
    if(feini.isValid()&&fefin.isValid())
    {
        var milisDif = Ext.Date.getElapsed(feini.getValue(),fefin.getValue());
        var diasDif  = (milisDif/1000/60/60/24).toFixed(0);
        debug('milisDif:',milisDif,'diasDif:',diasDif);
        itemVigencia.setValue(diasDif);
    }
    debug('<_p30_calculaVigencia');
}

function _p30_configuracionPanelDinClic(cdtipsit,titulo)
{
    debug('>_p30_configuracionPanelDinClic:',cdtipsit,titulo);
    var panel = _p30_paneles[cdtipsit];
    panel.setTitle(titulo);
    if(panel.valores!=false)
    {
        panel.down('form').loadRecord(new _p30_modelo(panel.valores));
    }
    else
    {
        panel.down('form').getForm().reset();
    }
    panel.callback=function()
    {
        var form = panel.down('form');
        var valido = form.isValid();
        if(!valido)
        {
            datosIncompletos();
        }
        
        if(valido)
        {
            panel.valores=form.getValues();
            panel.hide();
            debug('panel:',panel);
        }
    }
    centrarVentanaInterna(panel.show());
    debug('<_p30_configuracionPanelDinClic');
}

function _p30_agregarAuto()
{
    debug('>_p30_agregarAuto');
    if(!Ext.isEmpty(_fieldByLabel('NEGOCIO').getValue()))
    {
        _p30_store.add(new _p30_modelo());
    }
    else
    {
        mensajeWarning('Seleccione el negocio');
    }
    debug('<_p30_agregarAuto');
}

function _p30_gridBotonConfigClic(view,row,col,item,e,record)
{
    debug('>_p30_gridBotonConfigClic:',record);
    var cdtipsit = record.get('cdtipsit');
    
    var valido = !Ext.isEmpty(cdtipsit);
    if(!valido)
    {
        mensajeWarning('Debe seleccionar el tipo de veh&iacute;culo');
    }
    
    if(valido)
    {
        var cdtipsitPanel = _p30_smap1['destino_'+cdtipsit];
        debug('cdtipsit:',cdtipsit,'cdtipsitPanel:',cdtipsitPanel);
        var panel = _p30_paneles[cdtipsitPanel];
        panel.setTitle('CONFIGURACI&Oacute;N DE PLAN');
        var form  = panel.down('form');
        if(record.get('personalizado')=='si')
        {
            form.loadRecord(record);
        }
        else if(panel.valores!=false)
        {
            form.loadRecord(new _p30_modelo(panel.valores));
        }
        else
        {
            form.getForm().reset();
        }
        panel.callback=function()
        {
            var values = form.getValues();
            for(var prop in values)
            {
                record.set(prop,values[prop]);
            }
            record.set('personalizado','si');
            debug('record:',record);
            panel.hide();
            _fieldById('_p30_grid').editingPlugin.cancelEdit();
        }
        centrarVentanaInterna(panel.show());
    }
    debug('<_p30_gridBotonConfigClic');
}

function _p30_gridBotonEliminarClic(view,row,col,item,e,record)
{
    debug('>_p30_gridBotonEliminarClic:',record);
    _p30_store.remove(record);
    debug('<_p30_gridBotonEliminarClic');
}

function _p30_gridBotonAutoClic(grid,row,col,item,e,record)
{
    debug('>_p30_gridBotonAutoClic:',record);
    var cdtipsit = record.get('cdtipsit');
    
    _p30_selectedRecord = record;
    debug('_p30_selectedRecord:',_p30_selectedRecord);
    
    var valido = !Ext.isEmpty(cdtipsit);
    if(!valido)
    {
        mensajeWarning('Debe seleccionar el tipo de veh&iacute;culo');
    }
    
    if(valido)
    {
        valido = !Ext.isEmpty(_fieldByLabel('NEGOCIO').getValue());
        if(!valido)
        {
            mensajeWarning('Seleccione el negocio');
        }
    }
    
    if(valido)
    {
        _p30_selectAuto(record.data,function(datos)
        {
            grid.editingPlugin.cancelEdit();
            debug('datos:',datos);
            for(var i in datos)
            {
                _p30_selectedRecord.set(i,datos[i]);
            }
            debug('_p30_selectedRecord:',_p30_selectedRecord);
        });
    }
    
    debug('<_p30_gridBotonAutoClic');
}

function _p30_selectAuto(datos,callback)
{
    debug('>_p30_selectAuto:',datos);
    _p30_windowAuto.miCallback=callback;
    centrarVentanaInterna(_p30_windowAuto.show());
    var nameModelo = _fieldByLabel('MODELO',_p30_windowAuto).name;
    
    var formItems = Ext.ComponentQuery.query('[fieldLabel]',_p30_windowAuto.down('form'));
    for(var i=0;i<formItems.length;i++)
    {
        var item=formItems[i];
        debug('item:',item);
        if(!Ext.isEmpty(item)
            &&!Ext.isEmpty(item.store)
            &&!Ext.isEmpty(item.store.proxy)
            &&!Ext.isEmpty(item.store.proxy.extraParams)
            &&!Ext.isEmpty(item.store.proxy.extraParams['params.cdtipsit'])
        )
        {
            item.store.proxy.extraParams['params.cdtipsit']=datos.cdtipsit;
            debug('item.store.proxy.extraParams:',item.store.proxy.extraParams);
        }
    }
    _fieldByLabel('MARCA',_p30_windowAuto).getStore().load(
    {
        params :
        {
            'params.cdnegocio' : _fieldByLabel('NEGOCIO').getValue()
        }
    });
    
    debug('datos[nameModelo]:',datos[nameModelo]);
    if(Ext.isEmpty(datos[nameModelo]))
    {
        _fieldById('_p30_formAuto').getForm().reset();
    }
    else
    {
        var numBlurs  = 0;
        for(var i=0;i<formItems.length;i++)
        {
            var item=formItems[i];
            if(item.anidado == true)
            {
                var numBlursSeguidos = 1;
                debug('contando blur:',item);
                for(var j=i+1;j<formItems.length;j++)
                {
                    if(formItems[j].anidado == true)
                    {
                        numBlursSeguidos=numBlursSeguidos+1;
                    }
                }
                if(numBlursSeguidos>numBlurs)
                {
                    numBlurs=numBlursSeguidos;
                }
            }
        }
        debug('numBlurs:',numBlurs);
        var i      = 0;
        var form   = _fieldById('_p30_formAuto');
        var record = new _p30_modelo(datos);
    
        var renderiza=function()
        {
            debug('renderiza',i);
            form.loadRecord(record);
            if(i<numBlurs)
            {
                i=i+1;
                for(var j=0;j<formItems.length;j++)
                {
                    var iItem  = formItems[j];
                    var iItem2 = formItems[j+1];
                    debug('iItem2:',iItem2,'store:',iItem2?iItem2.store:'iItem2 no');
                    if(iItem2&&iItem2.anidado==true)
                    {
                        debug('tiene blur y lo hacemos heredar',formItems[j]);
                        iItem2.heredar(true);
                    }
                }
                setTimeout(renderiza,1000);
            }
            else
            {
                _p30_windowAuto.setLoading(false);
                if(_p30_smap1.cdramo=='5')
                {
                    _p30_herenciaAscendente(function()
                    {
                        var valorName = _fieldById('_p30_grid').down('[text*=VALOR VEH]').dataIndex;
                        if(record.get(valorName)+'x'!='x')
                        {
                            form.loadRecord(record);
                        }
                    });
                }
            }
        }
        _p30_windowAuto.setLoading(true);
        renderiza();
    }
    debug('<_p30_selectAuto');
}

function _p30_herenciaDescendiente(record)
{
    var marca    = _fieldByLabel('MARCA'    , _p30_windowAuto);
    var submarca = _fieldByLabel('SUBMARCA' , _p30_windowAuto);
    var modelo   = _fieldByLabel('MODELO'   , _p30_windowAuto);
    var version  = _fieldLikeLabel('VERSI'  , _p30_windowAuto);
    debug('>_p30_herenciaDescendiente');
    //var record = clave.findRecord('key',clave.getValue());
    debug('record:',record);
    var splitted=record.get('value').split(' - ');
    debug('splitted:',splitted);
    var clavev    = splitted[0];
    var marcav    = splitted[1];
    var submarcav = splitted[2];
    var modelov   = splitted[3];
    var versionv  = splitted[4];
    
    marca.setValue(marca.findRecord('value',marcav));
    submarca.heredar(true,function()
    {
        submarca.setValue(submarca.findRecord('value',submarcav));
        modelo.heredar(true,function()
        {
            modelo.setValue(modelo.findRecord('value',modelov));
            version.getStore().load(
            {
                params :
                {
                    'params.submarca' : submarca.getValue()
                    ,'params.modelo'  : modelo.getValue()
                }
                ,callback : function()
                {
                    version.setValue(version.findRecord('value',versionv));
                    _p30_cargarSumaAseguradaRamo5();
                }
            });
        });
    });
    
    debug('<_p30_herenciaDescendiente');
}

function _p30_cargarSumaAseguradaRamo5(callback)
{
    debug('>_p30_cargarSumaAseguradaRamo5');
    _p30_windowAuto.setLoading(true);
    Ext.Ajax.request(
    {
        url      : _p30_urlCargarSumaAseguradaRamo5
        ,params  :
        {
            'smap1.cdtipsit'  : _p30_selectedRecord.get('cdtipsit')
            ,'smap1.clave'    : _fieldLikeLabel('VERSI',_p30_windowAuto).getValue()
            ,'smap1.modelo'   : _fieldByLabel('MODELO',_p30_windowAuto).getValue()
            ,'smap1.cdsisrol' : _p30_smap1.cdsisrol
        }
        ,success : function(response)
        {
            _p30_windowAuto.setLoading(false);
            var json = Ext.decode(response.responseText);
            debug('### cargar suma asegurada:',json);
            if(json.exito)
            {
                var sumaseg = _fieldByName('parametros.pv_otvalor13',_p30_windowAuto);
                sumaseg.setValue(json.smap1.sumaseg);
                sumaseg.valorCargado=json.smap1.sumaseg;
                _p30_cargarRangoValorRamo5(callback);
            }
            else
            {
                mensajeError(json.respuesta);
            }           
        }
        ,failure : function()
        {
            _p30_windowAuto.setLoading(false);
            errorComunicacion();
        }
    });
    debug('<_p30_cargarSumaAseguradaRamo5');
}

function _p30_windowAutoAceptarClic(me)
{
    debug('>_p30_windowAutoAceptarClic');
    var form   = _fieldById('_p30_formAuto');
    var valido = form.isValid();
    if(!valido)
    {
        datosIncompletos();
    }
    
    if(valido)
    {
        _p30_windowAuto.hide();
        _p30_windowAuto.miCallback(form.getValues());
    }
    
    debug('<_p30_windowAutoAceptarClic');
}

function _p30_herenciaAscendente(callback)
{
    debug('>_p30_herenciaAscendente');
    var clave      = _fieldLikeLabel('CLAVE'  , _p30_windowAuto);
    var marca      = _fieldByLabel('MARCA'    , _p30_windowAuto);
    var submarca   = _fieldByLabel('SUBMARCA' , _p30_windowAuto);
    var modelo     = _fieldByLabel('MODELO'   , _p30_windowAuto);
    var version    = _fieldLikeLabel('VERSI'  , _p30_windowAuto);
    
    var versionval = version.getValue();
    
    if(!Ext.isEmpty(versionval))
    {
        var versiondes = version.findRecord('key',versionval).get('value');
        clave.getStore().load(
        {
            params :
            {
                'params.cadena' : versiondes
            }
            ,callback : function()
            {
                var valor = versionval
                            +' - '
                            +marca.findRecord('key',marca.getValue()).get('value')
                            +' - '
                            +submarca.findRecord('key',submarca.getValue()).get('value')
                            +' - '
                            +modelo.findRecord('key',modelo.getValue()).get('value')
                            +' - '
                            +version.findRecord('key',versionval).get('value');
                debug('>valor:',valor);
                clave.setValue(clave.findRecord('value',valor));
                _p30_cargarSumaAseguradaRamo5(callback);
            }
        });
    }
    
    debug('<_p30_herenciaAscendente');
}

function _p30_ramo5AgenteSelect(comp,records)
{
    var cdagente = typeof records == 'string' ? records : records[0].get('key');
    debug('>_p30_ramo5AgenteSelect cdagente:',cdagente);
    Ext.Ajax.request(
    {
        url     : _p30_urlCargarCduniecoAgenteAuto
        ,params :
        {
            'smap1.cdagente' : cdagente
        }
        ,success : function(response)
        {
            var json=Ext.decode(response.responseText);
            debug('#### obtener cdunieco agente response:',json);
            if(json.exito)
            {
                _p30_smap1.cdunieco=json.smap1.cdunieco;
                debug('_p30_smap1:',_p30_smap1);
                Ext.Ajax.request(
                {
                    url     : _p30_urlCargarRetroactividadSuplemento
                    ,params :
                    {
                        'smap1.cdunieco'  : _p30_smap1.cdunieco
                        ,'smap1.cdramo'   : _p30_smap1.cdramo
                        ,'smap1.cdtipsup' : 1
                        ,'smap1.cdusuari' : _p30_smap1.cdusuari
                        ,'smap1.cdtipsit' : _p30_smap1.cdtipsit
                    }
                    ,success : function(response)
                    {
                        var json = Ext.decode(response.responseText);
                        debug('### obtener retroactividad:',json);
                        if(json.exito)
                        {
                            var feini = _fieldByName('feini');
                            var fefin = _fieldByName('fefin');
                            
                            feini.setMinValue(Ext.Date.add(new Date(),Ext.Date.DAY,(json.smap1.retroac-0)*-1));
                            feini.setMaxValue(Ext.Date.add(new Date(),Ext.Date.DAY,json.smap1.diferi-0));
                            feini.isValid();
                        }
                        else
                        {
                            mensajeError(json.respuesta);
                        }
                    }
                    ,failure : errorComunicacion
                });
            }
            else
            {
                mensajeError(json.respuesta);
            }
        }
        ,failure : errorComunicacion
    });
    debug('<_p30_ramo5AgenteSelect');
}

function _p30_cargarRangoValorRamo5(callback)
{
    debug('>_p30_cargarRangoValorRamo5');
    var tipovalorName = _fieldById('_p30_grid').down('[text=TIPO VALOR]').dataIndex;
    debug('tipovalorName:',tipovalorName);
    var valor         = _fieldLikeLabel('VALOR VEH',_p30_windowAuto);
    debug('_p30_selectedRecord:',_p30_selectedRecord);
    var tipovalorval = _p30_selectedRecord.get(tipovalorName);
    var valorval     = valor.getValue();
    var valorCargado = valor.valorCargado;
    
    var valido = !Ext.isEmpty(tipovalorval)&&!Ext.isEmpty(valorval)&&!Ext.isEmpty(valorCargado);
    
    if(valido)
    {
        _p30_windowAuto.setLoading(true);
        Ext.Ajax.request(
        {
            url      : _p30_urlCargarParametros
            ,params  :
            {
                'smap1.parametro' : 'RANGO_VALOR'
                ,'smap1.cdramo'   : _p30_smap1.cdramo
                ,'smap1.cdtipsit' : _p30_selectedRecord.get('cdtipsit')
                ,'smap1.clave4'   : tipovalorval
                ,'smap1.clave5'   : _p30_smap1.cdsisrol
            }
            ,success : function(response)
            {
                _p30_windowAuto.setLoading(false);
                var json = Ext.decode(response.responseText);
                debug('### obtener rango valor:',json);
                if(json.exito)
                {
                    valormin = valorCargado*(1+(json.smap1.P1VALOR-0));
                    valormax = valorCargado*(1+(json.smap1.P2VALOR-0));
                    valor.setMinValue(valormin);
                    valor.setMaxValue(valormax);
                    valor.isValid();
                    debug('valor:',valorCargado);
                    debug('valormin:',valormin);
                    debug('valormax:',valormax);
                    
                    if(!Ext.isEmpty(callback))
                    {
                        callback();
                    }
                }
                else
                {
                    mensajeError(json.respuesta);
                }
            } 
            ,failure : function()
            {
                _p30_windowAuto.setLoading(false);
                errorComunicacion();
            }
        });
    }
    debug('<_p30_cargarRangoValorRamo5');
}

function _p30_ramo5ClienteChange()
{
    var combcl  = _fieldLikeLabel('CLIENTE NUEVO');
    
    debug('>_p30_ramo5ClienteChange value:',combcl.getValue());
    
    var nombre  = _fieldLikeLabel('NOMBRE CLIENTE');
    var tipoper = _fieldByLabel('TIPO PERSONA');
    var codpos  = _fieldLikeLabel('CP CIRCULACI');
    
    //cliente nuevo
    if(combcl.getValue()=='S')
    {
        nombre.reset();
        tipoper.reset();
        codpos.reset();
        
        nombre.setReadOnly(false);
        tipoper.setReadOnly(false);
        codpos.setReadOnly(false);
        
        _p30_recordClienteRecuperado=null;
    }
    //recuperar cliente
    else if(combcl.getValue()=='N' && ( Ext.isEmpty(combcl.semaforo)||combcl.semaforo==false ) )
    {
        nombre.reset();
        tipoper.reset();
        codpos.reset();
        
        nombre.setReadOnly(true);
        tipoper.setReadOnly(true);
        codpos.setReadOnly(true);
        
        var ventana=Ext.create('Ext.window.Window',
        {
            title      : 'Recuperar cliente'
            ,modal     : true
            ,width     : 600
            ,height    : 400
            ,items     :
            [
                {
                    layout    : 'hbox'
                    ,defaults : { style : 'margin : 5px;' }
                    ,items    :
                    [
                        {
                            xtype       : 'textfield'
                            ,name       : '_p30_recuperaRfc'
                            ,fieldLabel : 'RFC'
                            ,minLength  : 9
                            ,maxLength  : 13
                        }
                        ,{
                            xtype    : 'button'
                            ,text    : 'Buscar'
                            ,icon    : '${ctx}/resources/fam3icons/icons/zoom.png'
                            ,handler : function(button)
                            {
                                debug('recuperar cliente buscar');
                                var rfc=_fieldByName('_p30_recuperaRfc').getValue();
                                var valido=true;
                                if(valido)
                                {
                                    valido = !Ext.isEmpty(rfc)
                                             &&rfc.length>8
                                             &&rfc.length<14;
                                    if(!valido)
                                    {
                                        mensajeWarning('Introduza un RFC v&aacute;lido');
                                    }
                                }
                                
                                if(valido)
                                {
                                    button.up('window').down('grid').getStore().load(
                                    {
                                        params :
                                        {
                                            'map1.pv_rfc_i'       : rfc
                                            ,'map1.cdtipsit'      : _p30_smap1.cdtipsit
                                            ,'map1.pv_cdtipsit_i' : _p30_smap1.cdtipsit
                                            ,'map1.pv_cdunieco_i' : _p30_smap1.cdunieco
                                            ,'map1.pv_cdramo_i'   : _p30_smap1.cdramo
                                            ,'map1.pv_estado_i'   : 'W'
                                            ,'map1.pv_nmpoliza_i' : _fieldByName('nmpoliza').getValue()
                                        }
                                    });
                                }
                            }
                        }
                    ]
                }
                ,Ext.create('Ext.grid.Panel',
                {
                    title    : 'Resultados'
                    ,columns :
                    [
                        {
                            xtype    : 'actioncolumn'
                            ,width   : 30
                            ,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
                            ,handler : function(view,row,col,item,e,record)
                            {
                                debug('recuperar cliente handler record:',record);
                                _p30_recordClienteRecuperado=record;
                                nombre.setValue(record.raw.NOMBRECLI);
                                tipoper.setValue(record.raw.TIPOPERSONA);
                                codpos.setValue(record.raw.CODPOSTAL);
                                ventana.destroy();
                            }
                        }
                        ,{
                            text       : 'Nombre'
                            ,dataIndex : 'NOMBRECLI'
                            ,width     : 200
                        }
                        ,{
                            text       : 'Direcci&oacute;n'
                            ,dataIndex : 'DIRECCIONCLI'
                            ,flex      : 1
                        }
                    ]
                    ,store : Ext.create('Ext.data.Store',
                    {
                        model     : '_p30_modeloRecuperado'
                        ,autoLoad : false
                        ,proxy    :
                        {
                            type    : 'ajax'
                            ,url    : _p30_urlRecuperarCliente
                            ,reader :
                            {
                                type  : 'json'
                                ,root : 'slist1'
                            }
                        }
                    })
                })
            ]
            ,listeners :
            {
                close : function()
                {
                    combcl.setValue('S');
                }
            }
        }).show();
        centrarVentanaInterna(ventana);
    }
    debug('<_p30_ramo5ClienteChange');
}

function _p30_cotizar(sinTarificar)
{
    debug('>_p30_cotizar sinTarificar:',sinTarificar,'DUMMY');
    
    var valido = _fieldById('_p30_form').isValid();
    if(!valido)
    {
        datosIncompletos();
    }
    
    if(valido)
    {
        var error  = '';
        for(var i=0;i<_f1_botones.length;i++)
        {
            var boton    = _f1_botones[i];
            var cdtipsit = boton.cdtipsit;
            var panel    = _p30_paneles[cdtipsit];
            debug('buscando en panel:',panel);
            if(panel.valores==false)
            {
                valido = false;
                error  = error+'FALTA DEFINIR: '+boton.text+'<br/>';
            }
        }
        if(!valido)
        {
            mensajeWarning(error);
        }
    }
    
    if(valido)
    {
        valido = _p30_store.getCount()>0;
        if(!valido)
        {
            mensajeWarning('No se han capturado incisos. Debe capturar al menos uno');
        }
    }
    
    if(valido)
    {
        var valorName = _fieldById('_p30_grid').down('[text*=VALOR VEH]').dataIndex;
        var error     = '';
        _p30_store.each(function(record)
        {
            if(record.get(valorName)+'x'=='x')
            {
                error  = error + 'Debe actualizar el valor del veh&iacute;culo '+(_p30_store.indexOf(record)+1)+'</br>';
                valido = false;
            }
        });
        if(!valido)
        {
            mensajeWarning(error);
        }
    }
    
    if(valido)
    {
        valido = _fieldByName('nmpoliza').sucio==false;
        if(!valido)
        {
            _fieldByName('nmpoliza').semaforo = true;
            _fieldByName('nmpoliza').setValue('');
            _fieldByName('nmpoliza').semaforo = false;
            valido = true;
        }
    }
    
    debug('_p30_paneles:',_p30_paneles,'valido:',valido);
    if(valido)
    {
        debug('length:',_p30_paneles.length,'type:',typeof _p30_paneles);
        var recordsCdtipsit = [];
        for(var cdtipsitPanel in _p30_paneles)
        {
            var panel      = _p30_paneles[cdtipsitPanel];
            var recordBase = new _p30_modelo(panel.valores);
            recordBase.set('cdtipsit',cdtipsitPanel);
            debug('cdtipsitPanel:',cdtipsitPanel,'recordBase:',recordBase);
            recordsCdtipsit[cdtipsitPanel] = recordBase;
        }
        debug('recordsCdtipsit:',recordsCdtipsit);
        var storeTvalosit = Ext.create('Ext.data.Store',
        {
            model : '_p30_modelo'
        });
        var formValuesAux = _fieldById('_p30_form').getValues();
        var formValues    = {};
        for(var prop in formValuesAux)
        {
            if(prop+'x'!='x'
                &&prop.slice(0,5)=='param')
            {
                formValues[prop]=formValuesAux[prop];
            }
        }
        debug('formValues:',formValues);
        _p30_store.each(function(record)
        {
            var cdtipsit       = record.get('cdtipsit');
            var cdtipsitPanel  = _p30_smap1['destino_'+cdtipsit];
            var recordBase     = recordsCdtipsit[cdtipsitPanel];
            var recordTvalosit = new _p30_modelo(record.data);
            for(var prop in recordTvalosit.data)
            {
                var valor = recordTvalosit.get(prop);
                var base  = recordBase.get(prop);
                if(valor+'x'=='x'&&base+'x'!='x')
                {
                    recordTvalosit.set(prop,base);
                }
            }
            for(var prop in formValues)
            {
                recordTvalosit.set(prop,formValues[prop]);
            }
            storeTvalosit.add(recordTvalosit);
            debug('record:',record.data,'tvalosit:',recordTvalosit.data);
        });
        debug('_p30_store:',_p30_store);
        debug('storeTvalosit:',storeTvalosit);
        
        var json =
        {
            smap1 :
            {
                cdunieco     : _p30_smap1.cdunieco
                ,cdramo      : _p30_smap1.cdramo
                ,estado      : 'W'
                ,nmpoliza    : _fieldByName('nmpoliza').getValue()
                ,cdtipsit    : _p30_smap1.cdtipsit
                ,cdpersonCli : Ext.isEmpty(_p30_recordClienteRecuperado) ? '' : _p30_recordClienteRecuperado.raw.CLAVECLI
                ,cdideperCli : Ext.isEmpty(_p30_recordClienteRecuperado) ? '' : _p30_recordClienteRecuperado.raw.CDIDEPER
                ,feini       : Ext.Date.format(_fieldByName('feini').getValue(),'d/m/Y')
                ,fefin       : Ext.Date.format(_fieldByName('fefin').getValue(),'d/m/Y')
                ,cdagente    : _fieldByLabel('AGENTE').getValue()
            }
            ,slist1 : []
            ,slist2 : []
            ,slist3 : []
        };
        
        for(var cdtipsitPanel in recordsCdtipsit)
        {
            json.slist3.push(recordsCdtipsit[cdtipsitPanel].data);
        }
        
        _p30_store.each(function(record)
        {
            json.slist2.push(record.data);
        });
        
        storeTvalosit.each(function(record)
        {
            json.slist1.push(record.data);
        });
        
        debug('>>> json a enviar:',json);
        
        var panelpri = _fieldById('_p30_panelpri');
        panelpri.setLoading(true);
        Ext.Ajax.request(
        {
            url       : _p30_urlCotizar
            ,jsonData : json
            ,success  : function(response)
            {
                panelpri.setLoading(false);
                json = Ext.decode(response.responseText);
                debug('### cotizar:',json);
                if(json.exito)
                {
                }
                else
                {
                    mensajeError(json.respuesta);
                }
            }
            ,failure  : function()
            {
                panelpri.setLoading(false);
                errorComunicacion();
            }
        });
    }
    
    debug('<_p30_cotizar');
}

function _p30_nmpolizaChange(me)
{
    var sem = me.semaforo;
    if(Ext.isEmpty(sem)||sem==false)
    {
        me.sucio = true;
    }
    else
    {
        me.sucio = false;
    }
}
////// funciones //////
</script>
</head>
<body><div id="_p30_divpri" style="height:1000px;"</body>
</html>