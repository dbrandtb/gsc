<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<s:if test="false">
<script>
</s:if>
debug('###################################');
debug('###### scriptMesaEmision.jsp ######');
debug('###################################');

///////////////////////
////// variables //////
var mesConUrlDocu             = '<s:url namespace="/documentos"  action="ventanaDocumentosPoliza"     />';
var mesConUrlDatCom           = '<s:url namespace="/"            action="datosComplementarios"        />';
var mesConUrlCotizar          = '<s:url namespace="/emision"     action="cotizacion"                  />';
var mesConUrlDetMC            = '<s:url namespace="/mesacontrol" action="obtenerDetallesTramite"      />';
var mesConUrlFinDetalleMC     = '<s:url namespace="/mesacontrol" action="finalizarDetalleTramiteMC"   />';
var mesConUrlComGrupo         = '<s:url namespace="/emision"     action="cotizacionGrupo"             />';
var mesConUrlUpdateStatus     = '<s:url namespace="/mesacontrol" action="actualizarStatusTramite"     />';
var mesConUrlCargarParametros = '<s:url namespace="/emision"     action="obtenerParametrosCotizacion" />';

var ROL_MESA_DE_CONTROL    = '<s:property value="@mx.com.gseguros.portal.general.util.RolSistema@MESA_DE_CONTROL.cdsisrol" />';
var ROL_SUSCRIPTOR    = '<s:property value="@mx.com.gseguros.portal.general.util.RolSistema@SUSCRIPTOR.cdsisrol" />';

////// variables //////
///////////////////////

///////////////////////
////// funciones //////
function _4_onFolderClick(rowIndex)
{
    debug(rowIndex);
    var record=mcdinStore.getAt(rowIndex);
    debug(record);
    Ext.create('Ext.window.Window',
    {
        title        : 'Documentaci&oacute;n'
        ,modal       : true
        ,buttonAlign : 'center'
        ,width       : 600
        ,height      : 400
        ,autoScroll  : true
        ,loader      :
        {
            url       : mesConUrlDocu
            ,params   :
            {
                'smap1.nmpoliza'  : record.get('nmpoliza')&&record.get('nmpoliza').length>0?record.get('nmpoliza'):'0'
                ,'smap1.cdunieco' : record.get('cdunieco')
                ,'smap1.cdramo'   : record.get('cdramo')
                ,'smap1.estado'   : record.get('estado')
                ,'smap1.nmsuplem' : '0'
                ,'smap1.ntramite' : record.get('ntramite')
                ,'smap1.nmsolici' : record.get('nmsolici')&&record.get('nmsolici').length>0?record.get('nmsolici'):'0'
                ,'smap1.tipomov'  : '0'
            }
            ,scripts  : true
            ,autoLoad : true
        }
    }).show();
}

function _4_rechazar(row)
{
    _4_turnar(row,4,'Rechazar tr&aacute;mite');
}

function _4_solicitarEmision(row)
{
    _4_turnar(row,18,'Solicitar emisi&oacute;n');
}

function _4_turnar(row,status,titulo)
{
    debug('>_4_turnar',row,status,titulo);
    var record = mcdinStore.getAt(row);
    debug('record:',record);
    var ventana=Ext.create('Ext.window.Window',
    {
        title        : titulo
        ,width       : 500
        ,height      : 300
        ,modal       : true
        ,items       :
        [
            {
                xtype       : 'textarea'
                ,labelAlign : 'top'
                ,fieldLabel : 'Comentarios'
                ,itemId     : 'mesConObsSus'
                ,width      : 480
                ,height     : 200
            }
        ]
        ,buttonAlign : 'center'
        ,buttons     :
        [
            {
                text     : 'Aceptar'
                ,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
                ,handler : function(button)
                {
                    ventana.setLoading(true);
                    Ext.Ajax.request(
                    {
                        url     : mesConUrlUpdateStatus
                        ,params :
                        {
                            'smap1.status'    : status
                            ,'smap1.ntramite' : record.get('ntramite')
                            ,'smap1.comments' : Ext.ComponentQuery.query('#mesConObsSus')[0].getValue()
                        }
                        ,success : function(response)
                        {
                            ventana.setLoading(false);
                            var json=Ext.decode(response.responseText);
                            debug('json response:',json);
                            if(json.success)
                            {
                                ventana.setLoading(true);
                                Ext.Ajax.request(
                                {
                                    url      : mesConUrlCargarParametros
                                    ,params  :
                                    {
                                        'smap1.parametro' : 'MENSAJE_TURNAR'
                                        ,'smap1.cdramo'   : record.get('cdramo')
                                        ,'smap1.cdtipsit' : record.get('cdtipsit')
                                        ,'smap1.clave4'   : status
                                    }
                                    ,success : function(response)
                                    {
                                        ventana.setLoading(false);
                                        var json=Ext.decode(response.responseText);
                                        debug('### json response parametro mensaje turnar:',json);
                                        if(json.exito)
                                        {
                                            mensajeCorrecto('Tr&aacute;mite guardado',json.smap1.P1VALOR);
                                            button.up().up().destroy();
                                            loadMcdinStore();
                                        }
                                        else
                                        {
                                            mensajeWarning(json.respuesta);
                                        }
                                    }
                                    ,failure : function()
                                    {
                                        ventana.setLoading(false);
                                        errorComunicacion();
                                    }
                                });
                            }
                            else
                            {
                                mensajeError(json.mensaje);
                            }
                        }
                        ,failure : function()
                        {
                            ventana.setLoading(false);
                            errorComunicacion();
                        }
                    });
                }
            }
        ]
    }).show();
    centrarVentanaInterna(ventana);
    debug('<_4_turnar');
}

function _4_onSuscripcionClick(row)
{
	debug('>_4_onSuscripcionClick',row);
	var record = mcdinStore.getAt(row);
	debug('record:',record);
	var ventana=Ext.create('Ext.window.Window',
    {
        title        : 'Turnar a suscripci&oacute;n'
        ,width       : 500
        ,height      : 300
        ,modal       : true
        ,items       :
        [
            {
                xtype       : 'textarea'
                ,labelAlign : 'top'
                ,fieldLabel : 'Comentarios'
                ,itemId     : 'mesConObsSus'
                ,width      : 480
                ,height     : 200
            }
        ]
        ,buttonAlign : 'center'
        ,buttons     :
        [
            {
                text     : 'Turnar'
                ,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
                ,handler : function(button)
                {
                    ventana.setLoading(true);
                    Ext.Ajax.request(
                    {
                        url     : mesConUrlUpdateStatus
                        ,params :
                        {
                            'smap1.status'    : 13
                            ,'smap1.ntramite' : record.get('ntramite')
                            ,'smap1.comments' : Ext.ComponentQuery.query('#mesConObsSus')[0].getValue()
                        }
                        ,success : function(response)
                        {
                            ventana.setLoading(false);
                            var json=Ext.decode(response.responseText);
                            debug('json response:',json);
                            if(json.success)
                            {
                                mensajeCorrecto('Turnado','Tr&aacute;mite turnado');
                                button.up().up().destroy();
                                loadMcdinStore();
                            }
                            else
                            {
                                mensajeError(json.mensaje);
                            }
                        }
                        ,failure : function()
                        {
                            ventana.setLoading(false);
                            errorComunicacion();
                        }
                    });
                }
            }
        ]
    }).show();
	centrarVentanaInterna(ventana);
	debug('<_4_onSuscripcionClick');
}

function _4_onComplementariosClick(rowIndex)
{
    debug(rowIndex);
    var record=mcdinStore.getAt(rowIndex);
    debug(record);
    if(record.get('estado')=='W'&&record.get('status')!='4'&&record.get('status')!='11')
    {
        if(record.get('nmsolici')>0)
        {
        	if(EstatusTramite.EnRevisionMedica == record.get('status') && (ROL_MESA_DE_CONTROL == mcdinSesion['rol'] || ROL_SUSCRIPTOR == mcdinSesion['rol'] )){
        		mensajeWarning('Usted no puede realizar esta acci&oacute;n.');
        		return;
        	}
        	if(record.get('cdtipsit')=='MSC')
        	{
        		Ext.create('Ext.form.Panel').submit(
                {
                    url             : mesConUrlComGrupo
                    ,standardSubmit : true
                    ,params         :
                    {
                        'smap1.cdunieco'  : record.get('cdunieco')
                        ,'smap1.cdramo'   : record.get('cdramo')
                        ,'smap1.cdtipsit' : record.get('cdtipsit')
                        ,'smap1.estado'   : record.get('estado')
                        ,'smap1.nmpoliza' : record.get('nmsolici')
                        ,'smap1.ntramite' : record.get('ntramite')
                        ,'smap1.cdagente' : record.get('cdagente')
                        ,'smap1.status'   : record.get('status')
                    }
                });
        	}
        	else
        	{
	            Ext.create('Ext.form.Panel').submit(
	            {
	                url             : mesConUrlDatCom
	                ,standardSubmit : true
	                ,params         :
	                {
	                    cdunieco  : record.get('cdunieco')
	                    ,cdramo   : record.get('cdramo')
	                    ,estado   : record.get('estado')
	                    ,nmpoliza : record.get('nmsolici')
	                    ,'map1.ntramite' : record.get('ntramite')
	                    ,cdtipsit : record.get('cdtipsit')
	                }
	            });
        	}
        }
        else
        {
            debug('cotizar');
            if(record.get('cdtipsit')=='MSC')
            {
                Ext.create('Ext.form.Panel').submit(
                {
                    url             : mesConUrlComGrupo
                    ,standardSubmit : true
                    ,params         :
                    {
                        'smap1.cdunieco'       : record.get('cdunieco')
                        ,'smap1.cdramo'        : record.get('cdramo')
                        ,'smap1.cdtipsit'      : record.get('cdtipsit')
                        ,'smap1.estado'        : record.get('estado')
                        ,'smap1.nmpoliza'      : ''
                        ,'smap1.ntramiteVacio' : record.get('ntramite')
                        ,'smap1.cdagente'      : record.get('cdagente')
                        ,'smap1.status'        : record.get('status')
                    }
                });
            }
            else
            {
	            Ext.create('Ext.form.Panel').submit(
	            {
	                url             : mesConUrlCotizar
	                ,standardSubmit : true
	                ,params         :
	                {
	                    'smap1.ntramite'  : record.get('ntramite')
	                    ,'smap1.cdunieco' : record.get('cdunieco')
	                    ,'smap1.cdramo'   : record.get('cdramo')
	                    ,'smap1.cdtipsit' : record.get('cdtipsit')
	                }
	            });
            }
        }
    }
    else
    {
        var msg=Ext.Msg.show({
            title: 'Error',
            msg: 'Esta p&oacute;liza ya no puede editarse',
            buttons: Ext.Msg.OK,
            icon: Ext.Msg.WARNING
        });
        msg.setY(50);
    }
}

function _4_onClockClick(rowIndex)
{
    var record=mcdinStore.getAt(rowIndex);
    debug(record);
    var window=Ext.create('Ext.window.Window',
    {
        title        : 'Detalles del tr&aacute;mite '+record.get('ntramite')
        ,modal       : true
        ,buttonAlign : 'center'
        ,width       : 700
        ,height      : 400
        
        ,items       :
        [
            Ext.create('Ext.grid.Panel',
            {
                height      : 190
                ,autoScroll : true
                ,store      : new Ext.data.Store(
                {
                    model     : 'DetalleMC'
                    ,autoLoad : true
                    ,proxy    :
                    {
                        type         : 'ajax'
                        ,url         : mesConUrlDetMC
                        ,extraParams :
                        {
                            'smap1.pv_ntramite_i' : record.get('ntramite')
                        }
                        ,reader      :
                        {
                            type  : 'json'
                            ,root : 'slist1'
                        }
                    }
                })
                ,columns : 
                [
                    {
                        header     : 'Tr&aacute;mite'
                        ,dataIndex : 'NTRAMITE'
                        ,width     : 60
                    }
                    ,{
                        header     : 'Consecutivo'
                        ,dataIndex : 'NMORDINA'
                        ,width     : 80
                    }
                    ,{
                        header     : 'Fecha de inicio'
                        ,xtype     : 'datecolumn'
                        ,dataIndex : 'FECHAINI'
                        ,format    : 'd M Y'
                        ,width     : 90
                    }
                    ,{
                        header     : 'Usuario inicio'
                        ,dataIndex : 'usuario_ini'
                        ,width     : 150
                    }
                    ,{
                        header     : 'Fecha de fin'
                        ,xtype     : 'datecolumn'
                        ,dataIndex : 'FECHAFIN'
                        ,format    : 'd M Y'
                        ,width     : 90
                    }
                    ,{
                        header     : 'Usuario fin'
                        ,dataIndex : 'usuario_fin'
                        ,width     : 150
                    }
                    ,{
                        width         : 30
                        ,menuDisabled : true
                        ,dataIndex    : 'FECHAFIN'
                        ,renderer     : function(value)
                        {
                            debug(value);
                            if(value&&value!=null)
                            {
                                value='';
                            }
                            else
                            {
                                value='<img src="${ctx}/resources/fam3icons/icons/accept.png" style="cursor:pointer;" data-qtip="Finalizar" />';
                            }
                            return value;
                        }
                    }
                    /*,{
                        width         : 30
                        ,menuDisabled : true
                        ,dataIndex    : 'CDCLAUSU'
                        ,renderer     : function(value)
                        {
                            debug(value);
                            if(value&&value!=null&&value.length>0)
                            {
                                value='<img src="${ctx}/resources/fam3icons/icons/printer.png" style="cursor:pointer;" data-qtip="Imprimir" />';
                            }
                            else
                            {
                                value='';
                            }
                            return value;
                        }
                    }*/
                ]
                ,listeners :
                {
                    cellclick : function(grid, td,
                            cellIndex, record, tr,
                            rowIndex, e, eOpts)
                    {
                        debug(record);
                        if(cellIndex<6)
                        {
                            Ext.getCmp('inputReadDetalleHtmlVisor').setValue(record.get('COMMENTS'));
                        }
                        else if(cellIndex==6&&$(td).find('img').length>0)
                        {
                            debug('finalizar');
                            centrarVentanaInterna(Ext.create('Ext.window.Window',
                            {
                                title        : 'Finalizar detalle'
                                ,width       : 600
                                ,height      : 400
                                ,buttonAlign : 'center'
                                ,modal       : true
                                ,closable    : false
                                ,autoScroll  : true
                                ,items       :
                                [
                                    Ext.create('Ext.form.HtmlEditor', {
                                        id      : 'inputHtmlEditorFinalizarDetalleMesCon'
                                        ,width  : 570
                                        ,height : 300
                                        ,value  : record.get('COMMENTS')
                                    })
                                ]
                                ,buttons     :
                                [
                                    {
                                        text     : 'Guardar'
                                        ,icon    : '${ctx}/resources/fam3icons/icons/disk.png'
                                        ,handler : function()
                                        {
                                            var win=this.up().up();
                                            win.setLoading(true);
                                            Ext.Ajax.request
                                            ({
                                                url      : mesConUrlFinDetalleMC
                                                ,params  :
                                                {
                                                    'smap1.pv_ntramite_i'  : record.get('NTRAMITE')
                                                    ,'smap1.pv_nmordina_i' : record.get('NMORDINA')
                                                    ,'smap1.pv_comments_i' : Ext.getCmp('inputHtmlEditorFinalizarDetalleMesCon').getValue()
                                                }
                                                ,success : function (response)
                                                {
                                                    var json=Ext.decode(response.responseText);
                                                    if(json.success==true)
                                                    {
                                                        win.destroy();
                                                        window.destroy();
                                                        Ext.Msg.show({
                                                            title:'Detalle actualizado',
                                                            msg: 'Se finaliz&oacute; el detalle',
                                                            buttons: Ext.Msg.OK
                                                        });
                                                    }
                                                    else
                                                    {
                                                        win.setLoading(false);
                                                        Ext.Msg.show({
                                                            title:'Error',
                                                            msg: 'Error al finalizar detalle',
                                                            buttons: Ext.Msg.OK,
                                                            icon: Ext.Msg.ERROR
                                                        });
                                                    }
                                                }
                                                ,failure : function()
                                                {
                                                    win.setLoading(false);
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
                                    ,{
                                        text     : 'Cancelar'
                                        ,icon    : '${ctx}/resources/fam3icons/icons/cancel.png'
                                        ,handler : function()
                                        {
                                            this.up().up().destroy();
                                        }
                                    }
                                ]
                            }).show());
                        }
                        /*else if(cellIndex==5&&$(td).find('img').length>0)
                        {
                            debug("APRETASTE EL BOTON IMPRIMIR PARA EL RECORD:",record);
                        }*/
                    }
                }
            })
            ,Ext.create('Ext.form.HtmlEditor',
            {
                id        : 'inputReadDetalleHtmlVisor'
                ,width    : 690
                ,height   : 200
                ,readOnly : true
            })
        ]
    }).show();
    centrarVentanaInterna(window);
    Ext.getCmp('inputReadDetalleHtmlVisor').getToolbar().hide();
}
////// funciones //////
///////////////////////

Ext.onReady(function()
{
	
	/////////////////////
	////// modelos //////
	Ext.define('DetalleMC',{
        extend:'Ext.data.Model',
        fields:
        [
            "NTRAMITE"
            ,"NMORDINA"
            ,"CDTIPTRA"
            ,"CDCLAUSU"
            ,{name:"FECHAINI",type:'date',dateFormat:'d/m/Y'}
            ,{name:"FECHAFIN",type:'date',dateFormat:'d/m/Y'}
            ,"COMMENTS"
            ,"CDUSUARI_INI"
            ,"CDUSUARI_FIN"
            ,"usuario_ini"
            ,"usuario_fin"
        ]
    });
    ////// modelos //////
	/////////////////////
	
});
<s:if test="false">
</script>
</s:if>