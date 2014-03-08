<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Consulta Clausulas</title>
        
        <script type="text/javascript">
            ////// variables //////
            var _CONTEXT = '${ctx}';
            var _URL_CONSULTA_DATOS_TARIFA_POLIZA = '<s:url namespace="/consultasPoliza" action="consultaDatosTarifaPoliza" />';
            //var _URL_CARGA_CLAVES_CLAU =    '<s:url namespace="/catalogos" action="cargaClausulas" />';
            //var _URL_CONSULTA_CLAUSU =      '<s:url namespace="/catalogos" action="consultaClausulas" />';
            //var _URL_CONSULTA_CLAUSU_DETALLE =      '<s:url namespace="/catalogos" action="consultaClausulaDetalle" />';
            //var _URL_INSERTA_CLAUSU =      '<s:url namespace="/catalogos" action="insertaClausula" />';
            //var _URL_ACTUALIZA_CLAUSU =      '<s:url namespace="/catalogos" action="actualizaClausula" />';
            var _11_params = <s:property value='%{getParams().toString().replace("=",":\'").replace(",","\',").replace("}","\'}")}' />;
            
            var _11_itemsForm =
            [
                <s:property value="imap.itemsForm" />
                ,{
                	xtype   : 'label'
                	,hidden : true
                }
            ];
            
            var _11_itemsRechazo = [ <s:property value="imap.itemsCancelar" /> ];
            _11_itemsRechazo[2]['width']  = 500;
            _11_itemsRechazo[2]['height'] = 150;
            
            var _11_urlGuardar                  = '<s:url namespace="/siniestros"  action="guardarAfiliadosAfectados"     />';
            var _11_urlIniciarSiniestroTworksin = '<s:url namespace="/siniestros"  action="iniciarSiniestroTworksin"      />';
            var _11_urlActualizarSiniestro      = '<s:url namespace="/siniestros"  action="actualizarMultiSiniestro"      />';
            var _11_urlTabbedPanel              = '<s:url namespace="/siniestros"  action="detalleSiniestro"              />';
            var _11_urlCancelar                 = '<s:url namespace="/siniestros"  action="includes/rechazoReclamaciones" />';
            var _11_urlMesaControl              = '<s:url namespace="/mesacontrol" action="mcdinamica"                    />';
            
            var _11_form;
            var _11_recordActivo;
            var _11_windowPedirAut;
            var _11_formPedirAuto;
            var _11_textfieldAsegurado;
            var _11_textfieldNmautserv;
            var _11_windowEdicion;
            var _11_formEdicion;
            var _11_windowRechazoSiniestro;
            var _11_formRechazo;
            
            var recordsStore = [];
            
            <s:set name="contador" value="0" />
            <s:iterator value="slist1">
                recordsStore.push(
                {
                	IdReclamacion    : '<s:property value='%{getSlist1().get(#contador).get("NMSINIES")}'  />'
                	,NoAutorizacion  : '<s:property value='%{getSlist1().get(#contador).get("NMAUTSER")}'  />'
                	,codAfiliado     : '<s:property value='%{getSlist1().get(#contador).get("CDPERSON")}'  />'
                	,nombre          : '<s:property value='%{getSlist1().get(#contador).get("NOMBRE")}'    />'
                	,fechaOcurrencia : '<s:property value='%{getSlist1().get(#contador).get("FEOCURRE")}'  />'
                	,noPoliza        : '<s:property value='%{getSlist1().get(#contador).get("NMPOLIZA")}'  />'
                	,VoBoAuto        : '<s:property value='%{getSlist1().get(#contador).get("VOBOAUTO")}'  />'
                	,icd             : '<s:property value='%{getSlist1().get(#contador).get("CDICD")}'     />'
                	,icdSecundario   : '<s:property value='%{getSlist1().get(#contador).get("CDICD2")}'    />'
                	<%--,porcDescuento   : '<s:property value='%{getSlist1().get(#contador).get("DESCPORC")}' />'
                	,impoDescuento   : '<s:property value='%{getSlist1().get(#contador).get("DESCNUME")}' />'--%>
                	,copago          : '<s:property value='%{getSlist1().get(#contador).get("COPAGO")}'    />'
                	,impFacturado    : '<s:property value='%{getSlist1().get(#contador).get("PTIMPORT")}'  />'
                	,autoFacturado   : '<s:property value='%{getSlist1().get(#contador).get("AUTRECLA")}'  />'
                	,noReclamo       : '<s:property value='%{getSlist1().get(#contador).get("NMRECLAMO")}' />'
                	,AUTRECLA        : '<s:property value='%{getSlist1().get(#contador).get("AUTRECLA")}'  />'
                	,COMMENAR        : '<s:property value='%{getSlist1().get(#contador).get("COMMENAR")}'  />'
                	,AUTMEDIC        : '<s:property value='%{getSlist1().get(#contador).get("AUTMEDIC")}'  />'
                    ,COMMENME        : '<s:property value='%{getSlist1().get(#contador).get("COMMENME")}'  />'
                    ,AAAPERTU        : '<s:property value='%{getSlist1().get(#contador).get("AAAPERTU")}'  />'
                    ,STATUS          : '<s:property value='%{getSlist1().get(#contador).get("STATUS")}'    />'
                    ,CDUNIECO        : '<s:property value='%{getSlist1().get(#contador).get("CDUNIECO")}'  />'
                    ,CDRAMO          : '<s:property value='%{getSlist1().get(#contador).get("CDRAMO")}'    />'
                    ,NMSUPLEM        : '<s:property value='%{getSlist1().get(#contador).get("NMSUPLEM")}'  />'
                    ,NMSITUAC        : '<s:property value='%{getSlist1().get(#contador).get("NMSITUAC")}'  />'
                    ,ESTADO          : '<s:property value='%{getSlist1().get(#contador).get("ESTADO")}'    />'
                });
                <s:set name="contador" value="#contador+1" />
            </s:iterator>
            
            var _11_columnas = [
								{
									xtype         : 'actioncolumn'
									,menuDisabled : true
									,width        : 50
									,align        : 'center'
									,items        :
									[
									    {
									    	icon     : '${ctx}/resources/fam3icons/icons/pencil.png'
									    	,tooltip : 'Editar'
									    	,handler : _11_editar
									    }
									    ,{
									    	icon     : '${ctx}/resources/fam3icons/icons/folder.png'
									    	,tooltip : 'Capturar Detalle'
									    	,handler : revisarDocumento
									    }
									]//,flex:1
								},
                                {   
                                    text            :'Id<br/>Sini.',          width           : 50,
                                    align           :'center',                              dataIndex       :'IdReclamacion'                
                                },
                                {   
                                    text            :'# Auto.',          width           : 50,
                                    align           :'center',                                          dataIndex       :'NoAutorizacion'              
                                }
                                ,
                                {   
                                    text            :'Clave<br/>asegu.',        width           : 70,
                                    align           :'center',                              dataIndex       :'codAfiliado'              
                                },
                                {   
                                    text            :'Nombre<br/>Asegurado',              width           : 110,
                                    align           :'center',                              dataIndex       :'nombre'
                                },
                                {
                                    text            :'Fecha<br/>Ocurrencia',                    width           : 75,
                                    align           :'center',                              dataIndex       :'fechaOcurrencia',
                                    format          :'d/m/y',                               xtype           :'datecolumn'
                                },
                                {
                                    text            :'P&oacute;liza',                       width           : 50,
                                    align           :'center',                              dataIndex       :'noPoliza'
                                },
                                {
                                    text            :'Vo.Bo.<br/>Auto.',      width           : 50,
                                    align           :'center',                              dataIndex       :'VoBoAuto',
                                    renderer        : function(v)
                                    {
                                        var r=v;
                                        if(v=='S'||v=='s')
                                        {
                                            r='SI';
                                        }
                                        else if(v=='N'||v=='n')
                                        {
                                            r='NO';
                                        }
                                        return r;
                                    }
                                },
                                {
                                    text            :'ICD',                                 width           : 50,
                                    align           :'center',                              dataIndex       :'icd'
                                    
                                },
                                {
                                    text            :'ICD2',                     width           : 50, 
                                    align           :'center',                                  dataIndex       :'icdSecundario'
                                },
                                /*{
                                    text            : 'CPT/HCPC',                       width           : 110,
                                    align           :'center',                          dataIndex       :'cpthcpc'
                                },
                                {
                                    text            :'Cantidad',                        width           : 110,  
                                    align           :'center',                          dataIndex       :'cantidad'
                                },
                                {
                                    text            :'Importe <br/>Arancel',            width           : 110,          renderer        :Ext.util.Format.usMoney,
                                    align           :'center',                          dataIndex       :'importeArancel'
                                },
                                {
                                    text            :'Subtotal <br/>Arancel',           width           : 110,          renderer        :Ext.util.Format.usMoney, 
                                    align           :'center',                          dataIndex       :'subtoArancel'
                                },
                                {
                                    text            :'%<br/>Desc.',  
                                    dataIndex       :'porcDescuento',
                                    align           :'center',
                                    width           :50
                                },
                                {
                                    text            :'$<br/>Desc.',  
                                    dataIndex       :'impoDescuento',
                                    align           :'center',
                                    width           :80
                                    ,renderer       :Ext.util.Format.usMoney
                                },*/
                                {
                                    text            :'Copago',                          width           : 50,          
                                    align           :'center',
                                    dataIndex       :'copago'
                                },
                                {
                                    text            :'$<br/>Facturado', 
                                    align           :'center',
                                    dataIndex       :'impFacturado',
                                    	width           :80
                                        ,renderer       :Ext.util.Format.usMoney
                                },
                                {
                                    text            :'#<br/>Reclamo',
                                    align           :'center',
                                    dataIndex       :'noReclamo',
                                    width           :60
                                },
                                <s:property value="imap.columnas" />
                            ];
            
            debug('_11_params:',_11_params);
            ////// variables //////
            
////// funciones //////
function revisarDocumento(grid,rowIndex)
{
	var record = grid.getStore().getAt(rowIndex);
	debug('record.raw:',record.raw);
	
	var valido = true;
	
	if(valido)
	{
	    valido = _11_validaAutorizacion(record);
	}
	
	if(valido)
	{
		valido = record.get('VoBoAuto')!='n'&&record.get('VoBoAuto')!='N';
		if(!valido)
		{
			mensajeError('El siniestro no se puede continuar porque no tiene el visto bueno autom&aacute;tico');
		}
	}
	
	if(valido)
	{
		valido = record.get('AUTRECLA')!='n'&&record.get('AUTRECLA')!='N';
		if(!valido)
	    {
	        mensajeError('El siniestro no se puede continuar porque no tiene el visto bueno del &aacute;rea de reclamaciones');
	    }
	}
	
	if(valido)
	{
		valido = record.get('AUTMEDIC')!='n'&&record.get('AUTMEDIC')!='N';
	    if(!valido)
	    {
	        mensajeError('El siniestro no se puede continuar porque no tiene el visto bueno del &aacute;rea m&eacute;dica');
	    }
	}
	
	if(valido)
	{
		Ext.create('Ext.form.Panel').submit(
        {
        	url             : _11_urlTabbedPanel
            ,standardSubmit : true
            ,params         :
            {
                'params.ntramite'  : _11_params.NTRAMITE
                ,'params.cdunieco' : record.get('CDUNIECO')
                ,'params.cdramo'   : record.get('CDRAMO')
                ,'params.estado'   : record.get('ESTADO')
                ,'params.nmpoliza' : record.get('noPoliza')
                ,'params.nmsuplem' : record.get('NMSUPLEM')
                ,'params.nmsituac' : record.get('NMSITUAC')
                ,'params.aaapertu' : record.get('AAAPERTU')
                ,'params.status'   : record.get('STATUS')
                ,'params.nmsinies' : record.get('IdReclamacion')
            }
        });
	}
}

function _11_editar(grid,rowindex)
{
	var record = grid.getStore().getAt(rowindex);
	debug('_11_editar:',record.raw);
	
	var valido = _11_validaAutorizacion(record);
	
	if(valido)
	{
		_11_abrirEditor(record);
	}
	
	debug('!_11_editar');
}

function _11_validaAutorizacion(record)
{
	debug('_11_validaAutorizacion: ',record.raw);
	
	var valido = true;
	var nAut = record.get('NoAutorizacion');
	valido = nAut && nAut>0;
	if(!valido)
	{
		_11_pedirAutorizacion(record);
	}
	debug('!_11_validaAutorizacion: ',valido?'si':'no');
	return valido;
}

function _11_pedirAutorizacion(record)
{
	_11_recordActivo = record;
	debug('_11_recordActivo:',_11_recordActivo.data);
	
	_11_textfieldAsegurado.setValue(_11_recordActivo.get('nombre'));
	_11_textfieldNmautserv.setValue('');
	
	_11_windowPedirAut.show();
	centrarVentanaInterna(_11_windowPedirAut);
}

function _11_asociarAutorizacion()
{
	var valido = _11_formPedirAuto.isValid();
	if(!valido)
	{
		datosIncompletos();
	}
	
	if(valido)
	{
		var json =
		{
			'params.nmautser'  : _11_textfieldNmautserv.getValue()
			,'params.nmpoliza' : _11_recordActivo.get('noPoliza')
			,'params.cdperson' : _11_recordActivo.get('codAfiliado')
			,'params.ntramite' : _11_params.NTRAMITE
		};
		debug('datos a enviar:',json);
		
		_11_formPedirAuto.setLoading(true);
		Ext.Ajax.request(
		{
			url      : _11_urlIniciarSiniestroTworksin
			,params  : json
			,success : function(response)
			{
				_11_formPedirAuto.setLoading(false);
				json = Ext.decode(response.responseText);
				debug('respuesta:',json);
				if(json.success==true)
				{
					mensajeCorrecto('Datos guardados',json.mensaje,function()
					{
						Ext.create('Ext.form.Panel').submit(
				        {
				            standardSubmit :true
				            ,params        :
				            {
				                'params.ntramite' : _11_params.NTRAMITE
				            }
				        });
					});
				}
				else
				{
					mensajeError(json.mensaje);
				}
			}
		    ,failure : function()
		    {
		    	_11_formPedirAuto.setLoading(false);
		    	errorComunicacion();
		    }
		});
	}
}

function _11_abrirEditor(record)
{
	_11_recordActivo = record;
	debug('_11_abrirEditor _11_recordActivo:',_11_recordActivo.raw);
	_11_llenaFormulario();
	_11_windowEdicion.show();
	centrarVentanaInterna(_11_windowEdicion);
	debug('!_11_abrirEditor');
}

function _11_llenaFormulario()
{
	debug('_11_llenaFormulario');
	
	var itemNtramite = _11_formEdicion.items.items[0];
	var itemCdunieco = _11_formEdicion.items.items[1];
	var itemNmsinies = _11_formEdicion.items.items[2];
	var itemNautoriz = _11_formEdicion.items.items[3];
	var itemCdperson = _11_formEdicion.items.items[4];
	var itemNombre   = _11_formEdicion.items.items[5];
	var itemNmpoliza = _11_formEdicion.items.items[6];
	var itemFeocurre = _11_formEdicion.items.items[7];
	var itemIcd      = _11_formEdicion.items.items[8];
	var itemIcd2     = _11_formEdicion.items.items[10];
	var itemNreclamo = _11_formEdicion.items.items[12];
	var itemAutrecla = _11_formEdicion.items.items[13];
	var itemCommenar = _11_formEdicion.items.items[14];
	var itemAutmedic = _11_formEdicion.items.items[15];
    var itemCommenme = _11_formEdicion.items.items[16];
	
	itemNtramite.setValue(_11_params.NTRAMITE);
	itemNmsinies.setValue(_11_recordActivo.get('IdReclamacion'));
	itemNautoriz.setValue(_11_recordActivo.get('NoAutorizacion'));
	itemCdperson.setValue(_11_recordActivo.get('codAfiliado'));
	itemNombre.setValue(_11_recordActivo.get('nombre'));
	itemCdunieco.setValue(_11_params.CDSUCDOC);
	itemNmpoliza.setValue(_11_recordActivo.get('noPoliza'));
	itemFeocurre.setValue(_11_recordActivo.get('fechaOcurrencia'));
	itemIcd.setValue(_11_recordActivo.get('icd'));
	itemIcd2.setValue(_11_recordActivo.get('icdSecundario'));
	itemNreclamo.setValue(_11_recordActivo.get('noReclamo'));
	itemAutrecla.setValue(_11_recordActivo.get('AUTRECLA'));
	itemCommenar.setValue(_11_recordActivo.get('COMMENAR'));
	itemAutmedic.setValue(_11_recordActivo.get('AUTMEDIC'));
    itemCommenme.setValue(_11_recordActivo.get('COMMENME'));
	
	debug('!_11_llenaFormulario');
}

function _11_guardarEdicion()
{
	debug('_11_guardarEdicion');
	
	var valido = _11_formEdicion.isValid();
	if(!valido)
	{
		datosIncompletos();
	}
	
	var autorizaRecla = _11_formEdicion.items.items[13].getValue();
	var autorizaMedic = _11_formEdicion.items.items[15].getValue();
	debug('autorizaRecla',autorizaRecla);
	debug('autorizaMedic',autorizaMedic);
	var valido = autorizaRecla && autorizaRecla =='S' && autorizaMedic && autorizaMedic=='S';
	if(!valido)
	{
		mensajeWarning(
				'El tr&aacute;mite de pago directo ser&aacute; cancelado debido a que no ha sido autorizado alguno de los siniestros'
				,function(){_11_windowRechazoSiniestro.show();centrarVentanaInterna(_11_windowRechazoSiniestro);}
		);
	}
	
	if(valido)
	{
		_11_formEdicion.setLoading(true);
		var json =
		{
			params : _11_formEdicion.getValues()
		};
		json.params['cdunieco'] = _11_recordActivo.get('CDUNIECO');
		json.params['cdramo']   = _11_recordActivo.get('CDRAMO');
		json.params['estado']   = _11_recordActivo.get('ESTADO');
		json.params['nmpoliza'] = _11_recordActivo.get('noPoliza');
		json.params['nmsituac'] = _11_recordActivo.get('NMSITUAC');
		json.params['nmsuplem'] = _11_recordActivo.get('NMSUPLEM');
		json.params['status']   = _11_recordActivo.get('STATUS');
		json.params['aaapertu'] = _11_recordActivo.get('AAAPERTU');
		json.params['nmsinies'] = _11_recordActivo.get('IdReclamacion');
		debug('datos enviados:',json);
		Ext.Ajax.request(
		{
			url       : _11_urlActualizarSiniestro
			,jsonData : json
			,success  : function(response)
			{
				_11_formEdicion.setLoading(false);
				json = Ext.decode(response.responseText);
				if(json.success == true)
				{
					mensajeCorrecto('Datos guardados',json.mensaje,function()
                    {
                        Ext.create('Ext.form.Panel').submit(
                        {
                            standardSubmit :true
                            ,params        :
                            {
                                'params.ntramite' : _11_params.NTRAMITE
                            }
                        });
                    });
				}
				else
				{
					mensajeError(json.mensaje);
				}
			}
		    ,failure  : function()
		    {
		    	_11_formEdicion.setLoading(false);
		    	errorComunicacion();
		    }
		});
	}
	
	debug('!_11_guardarEdicion');
}

function _11_rechazoSiniestro()
{
	debug('_11_rechazoSiniestro');
	
	var valido = _11_formRechazo.isValid();
	if(!valido)
	{
		datosIncompletos();
	}
	
	if(valido)
	{
		//_11_formEdicion.setLoading(true);
        var json =
        {
            params : _11_formEdicion.getValues()
        };
        json.params['cdunieco'] = _11_recordActivo.get('CDUNIECO');
        json.params['cdramo']   = _11_recordActivo.get('CDRAMO');
        json.params['estado']   = _11_recordActivo.get('ESTADO');
        json.params['nmpoliza'] = _11_recordActivo.get('noPoliza');
        json.params['nmsituac'] = _11_recordActivo.get('NMSITUAC');
        json.params['nmsuplem'] = _11_recordActivo.get('NMSUPLEM');
        json.params['status']   = _11_recordActivo.get('STATUS');
        json.params['aaapertu'] = _11_recordActivo.get('AAAPERTU');
        json.params['nmsinies'] = _11_recordActivo.get('IdReclamacion');
        
        json.params['cancelar']       = 'si';
        json.params['cdmotivo']       = _11_itemsRechazo[0].getValue();
        json.params['cdsubmotivo']    = _11_itemsRechazo[1].getValue();
        json.params['rechazocomment'] = _11_itemsRechazo[2].getValue();
        
        debug('datos enviados:',json);
        _11_windowRechazoSiniestro.setLoading(true);
        Ext.Ajax.request(
        {
            url       : _11_urlActualizarSiniestro
            ,jsonData : json
            ,success  : function(response)
            {
            	_11_windowRechazoSiniestro.setLoading(false);
                json = Ext.decode(response.responseText);
                if(json.success == true)
                {
                    mensajeCorrecto('Datos guardados',json.mensaje,function()
                    {
                        Ext.create('Ext.form.Panel').submit(
                        {
                        	url             : _11_urlMesaControl
                            ,standardSubmit :true
                            ,params         :
                            {
                                'smap1.gridTitle'      : 'Siniestros'
                                ,'smap2.pv_cdtiptra_i' : 16
                            }
                        });
                    });
                }
                else
                {
                    mensajeError(json.mensaje);
                }
            }
            ,failure  : function()
            {
            	_11_windowRechazoSiniestro.setLoading(false);
                errorComunicacion();
            }
        });
	}
}
////// funciones //////

Ext.onReady(function()
{
	////// componentes //////
	Ext.define('_11_FormPedirAuto',
	{
		extend         : 'Ext.form.Panel'
		,initComponent : function()
        {
            debug('_11_FormPedirAuto initComponent');
            Ext.apply(this,
            {
            	border : 0
                ,items :
                [
                     {
                    	 xtype : 'label'
                    	 ,text : 'Se requiere el n�mero de autorizaci�n para continuar'
                     }
                     ,_11_textfieldAsegurado
                     ,_11_textfieldNmautserv
                ]
            });
            this.callParent();
        }
	});
	
	Ext.define('_11_FormEdicion',
	{
		extend         : 'Ext.form.Panel'
        ,initComponent : function()
        {
            debug('_11_FormEdicion initComponent');
            Ext.apply(this,
            {
                border  : 0
                ,layout :
                {
                	type     : 'table'
                	,columns : 2
                }
                ,items  : [ <s:property value="imap.itemsEdicion" /> ]
            });
            this.callParent();
        }
	});
	
	Ext.define('_11_FormRechazo',
    {
        extend         : 'Ext.form.Panel'
        ,initComponent : function()
        {
            debug('_11_FormRechazo initComponent');
            Ext.apply(this,
            {
                border  : 0
                ,items  : _11_itemsRechazo
            });
            this.callParent();
        }
    });
	
	
	Ext.define('_11_WindowPedirAut',
	{
		extend         : 'Ext.window.Window'
		,initComponent : function()
		{
			debug('_11_windowPedirAut initComponent');
			Ext.apply(this,
			{
				title        : 'Autorizaci&oacute;n de servicios'
				,icon        : '${ctx}/resources/fam3icons/icons/tick.png'
				,width       : 350
				,height      : 200
				,closeAction : 'hide'
				,modal       : true
				,defaults    : { style : 'margin : 5px; ' }
				,items       : _11_formPedirAuto
				,buttonAlign : 'center'
				,buttons     :
				[
				    {
				    	text     : 'Asociar autorizaci&oacute;n'
				    	,icon    : '${ctx}/resources/fam3icons/icons/disk.png'
				    	,handler : _11_asociarAutorizacion
				    }
				]
			});
			this.callParent();
		}
	});
	
	Ext.define('_11_WindowEdicion',
    {
        extend         : 'Ext.window.Window'
        ,initComponent : function()
        {
            debug('_11_WindowEdicion initComponent');
            Ext.apply(this,
            {
                title        : 'Asegurado afectado'
                ,icon        : '${ctx}/resources/fam3icons/icons/user.png'
                ,width       : 700
                ,height      : 400
                ,autoScroll  : true
                ,closeAction : 'hide'
                ,modal       : true
                ,defaults    : { style : 'margin : 5px; ' }
                ,items       : _11_formEdicion
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        text     : 'Guardar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/disk.png'
                        ,handler : _11_guardarEdicion
                    }
                ]
            });
            this.callParent();
        }
    });
	
	Ext.define('_11_WindowRechazoSiniestro',
    {
        extend         : 'Ext.window.Window'
        ,initComponent : function()
        {
            debug('_11_WindowRechazoSiniestro initComponent');
            Ext.apply(this,
            {
                title        : 'Rechazo de tr&aacute;mite'
                ,width       : 600
                ,height      : 350
                ,autoScroll  : true
                ,closeAction : 'hide'
                ,modal       : true
                ,defaults    : { style : 'margin : 5px; ' }
                ,items       : _11_formRechazo
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        text     : 'Rechazar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/delete.png'
                        ,handler : _11_rechazoSiniestro
                    }
                ]
            });
            this.callParent();
        }
    });
	////// componentes //////
	
	////// contenido //////
	_11_textfieldAsegurado = Ext.create('Ext.form.TextField',
	{
		fieldLabel : 'Asegurado'
		,readOnly  : true
	});
	_11_textfieldNmautserv = Ext.create('Ext.form.NumberField',
    {
        fieldLabel  : 'No. de autorizaci&oacute;n'
        ,readOnly   : false
        ,allowBlank : false
        ,minLength  : 1
    });
	_11_formPedirAuto  = new _11_FormPedirAuto();
	_11_windowPedirAut = new _11_WindowPedirAut();
	_11_formEdicion    = new _11_FormEdicion();
	_11_windowEdicion  = new _11_WindowEdicion();
	
	_11_formRechazo            = new _11_FormRechazo();
	_11_windowRechazoSiniestro = new _11_WindowRechazoSiniestro();
	////// contenido //////
	
	////// loader //////
	_11_formRechazo.items.items[1].on('select',function(combo, records, eOpts)
	{    
		_11_formRechazo.items.items[2].setValue(records[0].get('value'));
	});
	////// loader //////
});
        </script>
        <!-- <script type="text/javascript" src="${ctx}/resources/scripts/util/extjs4_utils.js"></script>-->
        <script type="text/javascript" src="${ctx}/js/proceso/siniestros/afiliadosAfectados.js"></script>
        
<script>
Ext.onReady(function()
{
    _11_form.items.items[3].on('blur',function()
    {
        var comboCoberturas = _11_form.items.items[4];
        comboCoberturas.getStore().load(
        {
            params :
            {
                'params.cdramo'    : _11_form.items.items[2].getValue()
                ,'params.cdtipsit' : _11_form.items.items[3].getValue()
            }
        });
    });
    
    _11_form.items.items[4].on('focus',function()
    {
        var comboCoberturas = _11_form.items.items[4];
        comboCoberturas.getStore().load(
        {
            params :
            {
                'params.cdramo'    : _11_form.items.items[2].getValue()
                ,'params.cdtipsit' : _11_form.items.items[3].getValue()
            }
        });
    });
    
    //recarga al render
    var comboCoberturas = _11_form.items.items[4];
    comboCoberturas.getStore().load(
    {
        params :
        {
            'params.cdramo'    : _11_form.items.items[2].getValue()
            ,'params.cdtipsit' : _11_form.items.items[3].getValue()
        }
    });
    //recarga al render
    
    
});

</script>
        
    </head>
    <body>
    <!-- <div style="height:500px;">
            <div id="div_clau"></div>
   </div>-->
    <div style="height:900px;">
            <div id="div_clau"></div>
            <div id="divResultados" style="margin-top:10px;"></div>
        </div>
    </body>
</html>