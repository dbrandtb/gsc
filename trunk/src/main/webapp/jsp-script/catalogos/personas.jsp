<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Personas</title>
<style>
.status{
	font-size:14px; 
	font-weight: bold;
}
</style>

<script>
////// overrides //////
////// overrides //////

////// variables //////
var _p22_urlObtenerPersonas     = '<s:url namespace="/catalogos"  action="obtenerPersonasPorRFC"              />';
var _p22_urlGuardar             = '<s:url namespace="/catalogos"  action="guardarPantallaPersonas"            />';
var _p22_urlObtenerDomicilio    = '<s:url namespace="/catalogos"  action="obtenerDomicilioPorCdperson"        />';
var _p22_urlTatriperTvaloper    = '<s:url namespace="/catalogos"  action="obtenerTatriperTvaloperPorCdperson" />';
var _p22_urlGuadarTvaloper      = '<s:url namespace="/catalogos"  action="guardarDatosTvaloper"               />';
var _p22_urlPantallaDocumentos  = '<s:url namespace="/catalogos"  action="pantallaDocumentosPersona"          />';
var _p22_urlSubirArchivo        = '<s:url namespace="/"           action="subirArchivoPersona"                />';
var _p22_UrlUploadPro           = '<s:url namespace="/"           action="subirArchivoMostrarBarra"           />';
var _p22_urlViewDoc             = '<s:url namespace="/documentos" action="descargaDocInlinePersona"           />';
var _p22_urlCargarNombreArchivo = '<s:url namespace="/catalogos"  action="cargarNombreDocumentoPersona"       />';

var _URL_CARGA_CATALOGO = '<s:url namespace="/catalogos" action="obtieneCatalogo" />';
var _CAT_NACIONALIDAD  = '<s:property value="@mx.com.gseguros.portal.general.util.Catalogos@NACIONALIDAD"/>';

var _UrlCargaAccionistas = '<s:url namespace="/catalogos" action="obtieneAccionistas" />';
var _UrlGuardaAccionista = '<s:url namespace="/catalogos" action="guardaAccionista" />';
var _UrlEliminaAccionistas = '<s:url namespace="/catalogos" action="eliminaAccionistas" />';

var _UrlActualizaStatusPersona = '<s:url namespace="/catalogos" action="actualizaStatusPersona" />';
var _UrlImportaPersonaWS = '<s:url namespace="/catalogos" action="importaPersonaExtWS" />';

/* PARA EL LOADER */
var _p22_urlCargarPersonaCdperson = '<s:url namespace="/catalogos" action="obtenerPersonaPorCdperson" />';
/* PARA EL LOADER */

var _p22_windowAgregarDocu;

var windowAccionistas = undefined;
var accionistasStore;
var gridAccionistas;
var fieldEstCorp;
var _0_botAceptar;

var _statusDataDocsPersona;



var _DocASubir;

/* PARA LOADER */
var _p22_smap1 = <s:property value='%{convertToJSON("smap1")}' escapeHtml="false" />;
debug('_p22_smap1:',_p22_smap1);


var _RFCsel;
var _RFCnomSel;

var _p22_cdperson = false;
var _p22_tipoPersona;
var _p22_nacionalidad;
var _CDIDEPERsel = '';
var _CDIDEEXTsel = '';
var _esSaludDanios;


var _p22_cdpersonTMP;
var _p22_tipoPersonaTMP;
var _p22_nacionalidadTMP;
var _CDIDEPERselTMP;
var _CDIDEEXTselTMP;
var _esSaludDaniosTMP;



var _cargaCdPerson;

if(!Ext.isEmpty(_p22_smap1)){
_cargaCdPerson = _p22_smap1.cdperson;	
}

////// variables //////

Ext.onReady(function()
{
	////// modelos //////
	Ext.define('_p22_modeloGrid',
	{
		extend  : 'Ext.data.Model'
		,fields : [ <s:property value="imap.gridModelFields" /> ]
	});
	
	Ext.define('_p22_modeloDomicilio',
	{
		extend  : 'Ext.data.Model'
		,fields : [ <s:property value="imap.fieldsDomicilio" /> ]
	});
	////// modelos //////
	
	////// componentes //////
	////// componentes //////
	
	////// contenido //////
	Ext.create('Ext.panel.Panel',
	{
		renderTo  : '_p22_divpri'
		,defaults : { style : 'margin:5px;' }
	    ,border   : 0
	    ,itemId   : '_p22_PanelPrincipal'
	    ,items    :
	    [
	        Ext.create('Ext.form.Panel',
	        {
	        	 title        : "Escriba el RFC de la Persona a buscar/agregar y de clic en 'Continuar'. Si selecciona una persona de la lista ser&aacute; editada, de lo contrario se agregar&aacute; una nueva."
	        	 ,itemId      : '_p22_formBusqueda'
	        	 ,hidden     : !Ext.isEmpty(_cargaCdPerson)
//	        	 ,layout      :
//	        	 {
//	        	     type     : 'table'
//	        	     ,columns : 3
//	        	 }
	        	 ,defaults    : { style : 'margin:5px;' }
	        	 ,items       : [{
	        	 					xtype      : 'fieldcontainer',
						            fieldLabel : 'Tipo de Compa&ntilde;ia',
						            defaultType: 'radiofield',
						            id        : 'companiaGroupId',
						            border: true,	
									defaults : { style : 'margin:5px;' },
						            layout: 'hbox',
						            items: [
						                {
						                    boxLabel  : 'General de Seguros',
						                    name      : 'smap1.esSalud',
						                    inputValue: false,
						                    checked   : true,
						                    id        : 'companiaId',
						                    listeners: {
			        	 						change: function(){
			        	 								var form=_p22_formBusqueda();
			        	 								form.down('[name=smap1.rfc]').reset();
						                        		form.down('[name=smap1.nombre]').reset();
			        	 								form.down('[name=smap1.rfc]').getStore().removeAll();
						                        		form.down('[name=smap1.nombre]').getStore().removeAll();
			        	 						}
			        	 					}
						                }, {
						                    boxLabel  : 'General de Salud',
						                    name      : 'smap1.esSalud',
						                    inputValue: true
						                }
						            ]},{
	        	 				xtype: 'combobox',
								fieldLabel:'B&uacute;squeda por RFC',
								labelWidth: 100,
								width:    800,
								queryParam  : 'smap1.rfc',
								queryMode   : 'remote',
								queryCaching: false,
								allQuery    : 'dummyForAllQuery',
            					minChars    : 9,
								minLength   : 2,
//								queryDelay  : 500,
								name          : 'smap1.rfc',
					            valueField    : 'CDRFC',
					            displayField  : 'NOMBRE_COMPLETO',
					            //forceSelection: true,
					            //typeAhead     : true,
					            anyMatch      : true,
					            hideTrigger   : true,
					            tpl: Ext.create('Ext.XTemplate',
					                    '<tpl for=".">',
					                        '<div class="x-boundlist-item">{CDRFC} - {DSNOMBRE} {DSNOMBRE1} {DSAPELLIDO} {DSAPELLIDO1} - {DIRECCIONCLI}</div>',
					                    '</tpl>'
					            ),
					            enableKeyEvents: true,
					            listeners: {
					            	select: function(comb, records){
					            		_RFCsel = records[0].get('CDRFC');
					            		_p22_cdpersonTMP = records[0].get('CDPERSON');
					            		_p22_tipoPersonaTMP = records[0].get('OTFISJUR');
					            		_p22_nacionalidadTMP = records[0].get('CDNACION');
					            		
					            		_CDIDEPERselTMP = records[0].get('CDIDEPER');
					            		_CDIDEEXTselTMP = records[0].get('CDIDEEXT');
					            		_esSaludDaniosTMP = (Ext.ComponentQuery.query('#companiaId')[0].getGroupValue())?'S':'D'; 
					            		
					            		var form=_p22_formBusqueda();
					            		form.down('[name=smap1.nombre]').reset();
					            		Ext.ComponentQuery.query('#btnContinuarId')[0].setText('Editar');
					            	}, 
					            	keydown: function( com, e, eOpts ){
					            		_RFCsel = '';
					            		var form=_p22_formBusqueda();
					            		form.down('[name=smap1.nombre]').reset();
					            		Ext.ComponentQuery.query('#btnContinuarId')[0].setText('Agregar');
					            	}
					            },
					            store         : Ext.create('Ext.data.Store', {
					                model     : '_p22_modeloGrid',
					                proxy     : {
				                            type        : 'ajax'
				                            ,url        : _p22_urlObtenerPersonas
				                            ,reader     :
				                            {
				                                type  : 'json'
				                                ,root : 'slist1'
				                            }
				                        }
				                        ,listeners: {
				                        	beforeload: function( store, operation, eOpts){
				                        		operation.callback = function(records, op, succ){
				                        			
				                        			var jsonResponse = Ext.decode(op.response.responseText);
//				                        			debug(typeof jsonResponse.exito);
//				                        			debug(jsonResponse.exito);
				                        			if(!jsonResponse.exito){
				                        				mensajeError('Error al hacer la consulta, Favor de Reintentar');
				                        				var form=_p22_formBusqueda();
					            						form.down('[name=smap1.rfc]').reset();
				                        				form.down('[name=smap1.nombre]').reset();
				                        			}
				                        		};
				                        		operation.params['smap1.esSalud'] = (Ext.ComponentQuery.query('#companiaId')[0].getGroupValue())?'S':'D'; //SALUD o DAÑOS
				                        		Ext.ComponentQuery.query('#btnContinuarId')[0].disable();
				                        		Ext.ComponentQuery.query('#companiaGroupId')[0].disable();
				                        	},
				                        	load      : function(){
				                        		Ext.ComponentQuery.query('#btnContinuarId')[0].enable();
				                        		Ext.ComponentQuery.query('#companiaGroupId')[0].enable();
				                        	}
				                        }
					            })
								},
								{
								xtype: 'combobox',
								fieldLabel:'B&uacute;squeda por Nombre',
								labelWidth: 100,
								width:    800,
								queryParam  : 'smap1.nombre',
								queryMode   : 'remote',
								queryCaching: false,
								allQuery    : 'dummyForAllQuery',
            					minChars    : 2,
								minLength   : 2,
								name          : 'smap1.nombre',
					            valueField    : 'CDRFC',
					            displayField  : 'NOMBRE_COMPLETO',
					            //forceSelection: true,
					            //                                                                      typeAhead     : true,
					            anyMatch      : true,
					            hideTrigger   : true,
					            tpl: Ext.create('Ext.XTemplate',
					                    '<tpl for=".">',
					                        '<div class="x-boundlist-item">{CDRFC} - {DSNOMBRE} {DSNOMBRE1} {DSAPELLIDO} {DSAPELLIDO1} - {DIRECCIONCLI}</div>',
					                    '</tpl>'
					            ),
					            enableKeyEvents: true,
					            listeners: {
					            	select: function(comb, records){
					            		_RFCnomSel = records[0].get('CDRFC');
					            		_p22_cdpersonTMP = records[0].get('CDPERSON');
					            		_p22_tipoPersonaTMP = records[0].get('OTFISJUR');
					            		_p22_nacionalidadTMP = records[0].get('CDNACION');
					            		
					            		_CDIDEPERselTMP = records[0].get('CDIDEPER');
					            		_CDIDEEXTselTMP = records[0].get('CDIDEEXT');
					            		_esSaludDaniosTMP = (Ext.ComponentQuery.query('#companiaId')[0].getGroupValue())?'S':'D'; 
					            		
					            		var form=_p22_formBusqueda();
					            		form.down('[name=smap1.rfc]').reset();
					            		Ext.ComponentQuery.query('#btnContinuarId')[0].setText('Editar');
					            	}, 
					            	keydown: function(){
					            		_RFCnomSel = '';
					            		var form=_p22_formBusqueda();
					            		form.down('[name=smap1.rfc]').reset();
					            		Ext.ComponentQuery.query('#btnContinuarId')[0].setText('Agregar');
					            	}
					            },
					            store         : Ext.create('Ext.data.Store', {
					                model     : '_p22_modeloGrid',
					                proxy     : {
				                            type        : 'ajax'
				                            ,url        : _p22_urlObtenerPersonas
				                            ,reader     :
				                            {
				                                type  : 'json'
				                                ,root : 'slist1'
				                            }
				                        }
				                        ,listeners: {
				                        	beforeload: function( store, operation, eOpts){
				                        		operation.callback = function(records, op, succ){
				                        			debug('op:',op);
				                        			var jsonResponse = Ext.decode(op.response.responseText);
				                        			if(!jsonResponse.exito){
				                        				mensajeError('Error al hacer la consulta, Favor de Reintentar');	
				                        				var form=_p22_formBusqueda();
					            						form.down('[name=smap1.rfc]').reset();
				                        				form.down('[name=smap1.nombre]').reset();
				                        			}
				                        		}
				                        		operation.params['smap1.esSalud'] = (Ext.ComponentQuery.query('#companiaId')[0].getGroupValue())?'S':'D'; //SALUD o DAÑOS
				                        		Ext.ComponentQuery.query('#btnContinuarId')[0].disable();
				                        	},
				                        	load      : function(){
				                        		Ext.ComponentQuery.query('#btnContinuarId')[0].enable();
				                        	}
				                        }
					            })
								},{
										xtype      : 'hidden',
										name       : 'smap1.snombre',
										value      : ' '  // 1 INSERT, 2 UPDATE
									},
									{
										xtype      : 'hidden',
										name       : 'smap1.apat',
										value      : ' '  // 1 INSERT, 2 UPDATE
									},{
										xtype      : 'hidden',
										name       : 'smap1.amat',
										value      : ' '  // 1 INSERT, 2 UPDATE
									}
								]
	        	 ,buttonAlign : 'center'
	        	 ,buttons     :
	        	 [
	        	     {
                         text     : 'Agregar'
                         ,xtype   : 'button'
                         ,itemId  : 'btnContinuarId'
                         ,disabled: true
                         ,icon    : '${ctx}/resources/fam3icons/icons/building_go.png'
                         ,handler : function (){
										var form = _p22_formBusqueda();
										
										var valorRFC = form.down('[name=smap1.rfc]').getValue(); 
										var valorNombre = form.down('[name=smap1.nombre]').getValue();
										
										if(Ext.isEmpty(valorRFC) && Ext.isEmpty(valorNombre)){
											mensajeWarning('Debe de llenar uno de los dos campos para continuar.');
										}
										
										debug('valorRFC:',valorRFC);
										debug('valorNombre:',valorNombre);
										debug('_RFCsel:',_RFCsel);
										debug('_RFCnomSel:',_RFCnomSel);
										
										if( (!Ext.isEmpty(_RFCsel)&&(_RFCsel == valorRFC)) || ((!Ext.isEmpty(_RFCnomSel))&&(_RFCnomSel == valorNombre))){
											_p22_formDatosGenerales().getForm().reset();
											_p22_formDomicilio().getForm().reset();
											_p22_formDatosGenerales().hide();
								    		_p22_formDomicilio().hide();
								    		_p22_principalDatosAdicionales().hide();
								    		_fieldByName('CDMUNICI').setFieldLabel("MUNICIPIO");
											_fieldByName('CDCOLONI').setFieldLabel("COLONIA");
											
											_p22_cdperson = _p22_cdpersonTMP;
											_p22_tipoPersona = _p22_tipoPersonaTMP;
											_p22_nacionalidad = _p22_nacionalidadTMP;
											_CDIDEPERsel = _CDIDEPERselTMP;
											_CDIDEEXTsel = _CDIDEEXTselTMP;
											_esSaludDanios = _esSaludDaniosTMP;
											
								    		//Si el la persona es proveniente de WS, primero se genera la persona y se inserta los datos del WS para luego ser editada
								    		if("1" == _p22_cdperson){
								    			importaPersonaWS( _esSaludDanios , (_esSaludDanios == 'S') ? _CDIDEEXTsel : _CDIDEPERsel );
								    			return;
								    		}
								    		
//								    		form.down('[name=smap1.rfc]').reset();
//								    		form.down('[name=smap1.nombre]').reset();
											irModoEdicion();
											
										}else if(!Ext.isEmpty(valorRFC)){
											_p22_formDatosGenerales().getForm().reset();
											_p22_formDomicilio().getForm().reset();
											_p22_formDatosGenerales().hide();
						    				_p22_formDomicilio().hide();
										    _p22_principalDatosAdicionales().hide();
											
										    if(form.down('[name=smap1.rfc]').getStore().count() > 0){
										    	mensajeWarning('El RFC ya existe. Favor de seleccionar uno de la lista.');
										    	return;
										    }
										    
											_p22_fieldRFC().setValue(valorRFC);
											_p22_cdperson = '';
											_p22_tipoPersona = '';
											_p22_nacionalidad = '';
											_CDIDEPERsel = '';
											_CDIDEEXTsel = '';
//											form.down('[name=smap1.rfc]').reset();
//											form.down('[name=smap1.nombre]').reset();
											_esSaludDanios = (Ext.ComponentQuery.query('#companiaId')[0].getGroupValue())?'S':'D';
											
											irModoAgregar();
											
										}else if(!Ext.isEmpty(valorNombre)){
											mensajeWarning('Para agregar una persona nueva llene el campo de RFC.');
											return;
										}
									}
                     }
                 ]
	        })
	        ,Ext.create('Ext.form.Panel',
	        	    {
	        	    	title     : 'Datos generales de la Persona'
	        	    	,itemId   : '_p22_formDatosGenerales'
                        ,border   : 0
	        	    	,defaults : { style : 'margin:5px' }
	        	        ,layout   :
	        	        {
	        	        	type     : 'table'
	        	        	,columns : 3
	        	        }
	        	    	,items    : [ <s:property value="imap.datosGeneralesItems" escapeHtml="false" /> ]
	        	    })
	        	    ,Ext.create('Ext.form.Panel',
                    {
                        title     : 'Domicilio de la Persona'
                        ,itemId   : '_p22_formDomicilio'
                        ,border   : 0
                        ,defaults : { style : 'margin:5px' }
                        ,layout   :
                        {
                            type     : 'table'
                            ,columns : 3
                        }
                        ,items    : [ <s:property value="imap.itemsDomicilio" escapeHtml="false" /> ]
                    })
                    ,Ext.create('Ext.form.Panel',
                    {
                        title   : 'Datos adicionales de la Persona'
                    	,itemId : '_p22_principalDatosAdicionales'
                        ,border   : 0
                        //,defaults : { style : 'margin:5px' }
                        ,buttonAlign: 'center'
                        ,buttons    :
	                    [{
	                            text     : 'Guardar datos de Persona'
	                            ,icon    : '${ctx}/resources/fam3icons/icons/disk.png'
	                            ,handler : function(){
	                            			_p22_guardarClic(_p22_guardarDatosAdicionalesClic,false);
	                            }
	                    }]
                    })
                    
	    ]
	});
	////// contenido //////
	
	////// loaders //////
	
	_p22_comboCodPostal().addListener('blur',_p22_heredarColonia);
	_p22_fieldTipoPersona().addListener('change',_p22_tipoPersonaChange);
	_fieldByName('CDNACION').addListener('change',_p22_nacionalidadChange);
	_p22_tipoPersonaChange(_p22_fieldTipoPersona(),'F');
	_p22_nacionalidadChange(_fieldByName('CDNACION'),'001');
	_fieldByName('NMNUMERO').regex = /^[A-Za-z0-9-]*$/;
	_fieldByName('NMNUMERO').regexText = 'Solo d&iacute;gitos, letras y guiones';
    _fieldByName('NMNUMINT').regex = /^[A-Za-z0-9-]*$/;
    _fieldByName('NMNUMINT').regexText = 'Solo d&iacute;gitos, letras y guiones';
	////// loaders //////
    
    
    _p22_fieldCumuloPrima().addListener('select', function(){_p22_guardarClic(_p22_datosAdicionalesClic, true);});
    _p22_fieldResidente().addListener('select', function(){_p22_guardarClic(_p22_datosAdicionalesClic, true);});
    
    _p22_formDatosGenerales().hide();
    _p22_formDomicilio().hide();
    _p22_principalDatosAdicionales().hide();
    
    function irModoAgregar(){
    	var windowTipo;
    	var panelTipoPer = Ext.create('Ext.form.Panel', {
		    defaults : { style : 'margin:5px;' },
		    items: [{	xtype: 'combobox',
						fieldLabel:'Tipo de persona',
						allowBlank:false,
						typeAhead:true,
						anyMatch:true,
						displayField:'value',
						valueField:'key',
						forceSelection:true,
						editable:false,
						queryMode:'local',
						store:Ext.create('Ext.data.Store',{
						model:'Generic',
						autoLoad:true,
						proxy:{type:'ajax',
						url:_URL_CARGA_CATALOGO,
						reader:{type:'json',
						root:'lista',
						rootProperty:'lista'
						},
						extraParams:{catalogo:'TIPOS_PERSONA'}
						}
						}),
						listeners: {
							select: function(combo,records){
								_p22_fieldTipoPersona().setValue(records[0]);
							}
						}
					},{
						xtype:'combobox',
						fieldLabel:'C&uacute;mulo de prima',
						allowBlank:false,
						typeAhead:true,
						anyMatch:true,
						displayField:'value',
						valueField:'key',
						forceSelection:true,
						editable:false,
						queryMode:'local',
						store:Ext.create('Ext.data.Store',{
						model:'Generic',
						autoLoad:true,
						proxy:{type:'ajax',
						url:_URL_CARGA_CATALOGO,
						reader:{type:'json',
						root:'lista',
						rootProperty:'lista'
						},
						extraParams:{catalogo:'TCUMULOS'}
						}
						}),
						listeners: {
							select: function(combo,records){
								_p22_fieldCumuloPrima().setValue(records[0]);
							}
						}
					},{
						xtype:'combobox',
						fieldLabel:'Nacionalidad',
						allowBlank:false,
						typeAhead:true,
						anyMatch:true,
						displayField:'value',
						valueField:'key',
						forceSelection:true,
						editable:false,
						queryMode:'local',
						store:Ext.create('Ext.data.Store',{
						model:'Generic',
						autoLoad:true,
						proxy:{type:'ajax',
						url:_URL_CARGA_CATALOGO,
						reader:{type:'json',
						root:'lista',
						rootProperty:'lista'
						},
						extraParams:{catalogo:'NACIONALIDAD'}
						}
						}),
						listeners: {
							select: function(combo,records){
								_p22_fielCdNacion().setValue(records[0]);
							},
							change:  _p22_nacionalidadChange2
						}
					},{
						xtype:'combobox',
						fieldLabel:'Residente',
						name: 'RESIDENTE2',
						allowBlank:false,
						typeAhead:true,
						anyMatch:true,
						displayField:'value',
						valueField:'key',
						forceSelection:true,
						editable:false,
						queryMode:'local',
						store:Ext.create('Ext.data.Store',{
						model:'Generic',
						autoLoad:true,
						proxy:{type:'ajax',
						url:_URL_CARGA_CATALOGO,
						reader:{type:'json',
						root:'lista',
						rootProperty:'lista'
						},
						extraParams:{catalogo:'TIPO_RESIDENCIA'}
						}
						}),
						listeners: {
							select: function(combo,records){
								_p22_fieldResidente().setValue(records[0]);
							}
						}
					}],
		    buttons: [{
		        text: 'Cancelar',
		        handler: function() {
		            this.up('form').getForm().reset();
		            windowTipo.close();
		        }
		    }, {
		        text: 'Aceptar',
		        formBind: true, //only enabled once the form is valid
		        disabled: true,
		        handler: function() {
		            var form = this.up('form').getForm();
		            if (form.isValid()) {
		            	
		            	if(!validarRFC(_p22_fieldRFC().getValue(),_p22_fieldTipoPersona().getValue())){
		            		return;
		            	}
		            	
		                _p22_formDatosGenerales().show();
		                _p22_formDomicilio().show();
		                _p22_principalDatosAdicionales().show();
		                windowTipo.close();
		                
		                _p22_guardarClic(_p22_datosAdicionalesClic, true);
		            }
		        }
		    }]
		});
		
		
		windowTipo = Ext.create('Ext.window.Window', {
			title: 'Elija el tipo de persona',
		    height: 200,
		    width: 300,
		    closable: false,
		    items: [panelTipoPer]
		}).show();
		centrarVentanaInterna(windowTipo);
    	
    }
    
    function irModoEdicion(){
    	
		if(_p22_cdperson!=false){
			_p22_formDatosGenerales().show();
			_p22_formDomicilio().show();
		    _p22_principalDatosAdicionales().show();

		    _p22_loadRecordCdperson(function(){_p22_guardarClic(_p22_datosAdicionalesClic, true);});
		    
		}else{
			mensajeWarning('Error al cargar datos.');
		}
    }
    
function importaPersonaWS(esSaludD, codigoCliExt){
    	
    Ext.Ajax.request(
	        {
	            url       : _UrlImportaPersonaWS
	            ,params: {
            		'params.esSalud':  esSaludD,
            		'params.codigoCliExt':  codigoCliExt
            	}
	            ,success  : function(response)
	            {
	                //_p22_formDatosAdicionales().setLoading(false);
	                var json = Ext.decode(response.responseText);
	                debug('response text:',json);
	                if(json.exito)
	                {
	                	if(esSaludD == 'S'){
	                		_CDIDEEXTsel = json.params.codigoExterno;
	                	}else{
	                		_CDIDEPERsel = json.params.codigoExterno;
	                	}
	                	
		                _p22_cdperson = json.params.cdperson;
		                
						var form=_p22_formBusqueda();
						form.down('[name=smap1.rfc]').reset();
						form.down('[name=smap1.nombre]').reset();
						
						_fieldByName('CDMUNICI').setFieldLabel(_fieldByName('CDMUNICI').getFieldLabel()+" "+json.params.municipioImp);
						_fieldByName('CDCOLONI').setFieldLabel(_fieldByName('CDCOLONI').getFieldLabel()+" "+json.params.coloniaImp);
						
						irModoEdicion();
	                }
	                else
	                {
	                    mensajeError("Error al Editar Cliente, vuelva a intentarlo.");
	                }
	            }
	            ,failure  : function()
	            {
	                errorComunicacion();
	            }
	});
    
    }
    
    //_fieldByName('CDMUNICI').forceSelection = false;
	_fieldByName('CDCOLONI').forceSelection = false;
	_fieldByName('CDCOLONI').on({
			change: function(me, val){
    				try{
	    				if('string' == typeof val){
	    					debug('mayus de '+val);
	    					me.setValue(val.toUpperCase());
	    				}
    				}
    				catch(e){
    					debug(e);
    				}
			}
	});
    
    if(!Ext.isEmpty(_cargaCdPerson)){
    	
    	setTimeout(function(){
			_p22_cdperson = _cargaCdPerson;
    		irModoEdicion();
		},1000)
    	
    }
});

////// funciones //////

function _p22_formBusqueda()
{
    debug('>_p22_formBusqueda<');
	return Ext.ComponentQuery.query('#_p22_formBusqueda')[0];
}

function _p22_heredarColonia(callbackload)
{
    debug('>_p22_heredarColonia');
    var comboColonias  = _p22_comboColonias();
    var comboCodPostal = _p22_comboCodPostal();
    var codigoPostal   = comboCodPostal.getValue();
    debug('comboColonias:',comboColonias,'comboCodPostal:',comboCodPostal);
    debug('codigoPostal:',codigoPostal);
    comboColonias.getStore().load(
    {
        params :
        {
            'params.cp' : codigoPostal
        }
        ,callback : function()
        {
            var hay=false;
            comboColonias.getStore().each(function(record)
            {
                if(comboColonias.getValue()==record.get('key'))
                {
                    hay=true;
                }
            });
            if(!hay)
            {
                comboColonias.setValue('');
            }
            if(!Ext.isEmpty(callbackload)){
            	callbackload();	
            }
            
        }
    });
    debug('<_p22_heredarColonia');
}

function _p22_nacionalidadChange(combo,value)
{
    debug('>_p22_nacionalidadChange',value);
    if(value!='001')//extranjero
    {
        _fieldByName('RESIDENTE').show();
        _fieldByName('RESIDENTE').allowBlank = false;
        _fieldByName('RESIDENTE').validate();
    }
    else//nacional
    {
        _fieldByName('RESIDENTE').hide();
        _fieldByName('RESIDENTE').allowBlank = true;
        _fieldByName('RESIDENTE').validate();
    }
    debug('<_p22_nacionalidadChange');
}

function _p22_nacionalidadChange2(combo,value)
{
    if(value!='001')//extranjero
    {
        _fieldByName('RESIDENTE2').show();
        _fieldByName('RESIDENTE2').allowBlank = false;
        _fieldByName('RESIDENTE2').validate();
    }
    else//nacional
    {
        _fieldByName('RESIDENTE2').hide();
        _fieldByName('RESIDENTE2').allowBlank = true;
        _fieldByName('RESIDENTE2').validate();
    }
}

function _p22_tipoPersonaChange(combo,value)
{
    debug('>_p22_tipoPersonaChange',value);
    if(value!='F')
    {
        _p22_fieldSegundoNombre().hide();
        _p22_fieldApat().hide();
        _p22_fieldAmat().hide();
        _p22_fieldSexo().hide();
        _fieldByName('DSNOMBRE').setFieldLabel('Raz&oacute;n social');
        _fieldByName('FENACIMI').setFieldLabel('Fecha de constituci&oacute;n');
        
        if(value == 'S'){
        	_fieldByName('FENACIMI').allowBlank = true;
        	_fieldByName('FENACIMI').setValue('');
        	_fieldByName('FENACIMI').hide();
        }else {
        	_fieldByName('FENACIMI').allowBlank = false;
        	_fieldByName('FENACIMI').setValue('');
        	_fieldByName('FENACIMI').show();
        }
    }
    else
    {
        _p22_fieldSegundoNombre().show();
        _p22_fieldApat().show();
        _p22_fieldAmat().show();
        _p22_fieldSexo().show();
        _fieldByName('DSNOMBRE').setFieldLabel('Nombre');
        _fieldByName('FENACIMI').setFieldLabel('Fecha de nacimiento');
        
        _fieldByName('FENACIMI').allowBlank = false;
    	_fieldByName('FENACIMI').setValue('');
    	_fieldByName('FENACIMI').show();
    }
    debug('<_p22_tipoPersonaChange');
}

function _p22_comboColonias()
{
    debug('>_p22_comboColonias<');
    return Ext.ComponentQuery.query('[name=CDCOLONI]')[0];
}

function _p22_comboCodPostal()
{
    debug('>_p22_comboCodPostal<');
    return Ext.ComponentQuery.query('[name=CODPOSTAL]')[0];
}

function _p22_fieldSegundoNombre()
{
    debug('>_p22_fieldSegundoNombre<');
    return Ext.ComponentQuery.query('[name=DSNOMBRE1]')[0];
}

function _p22_fieldApat()
{
    debug('>_p22_fieldApat<');
    return Ext.ComponentQuery.query('[name=DSAPELLIDO]')[0];
}

function _p22_fieldAmat()
{
    debug('>_p22_fieldAmat<');
    return Ext.ComponentQuery.query('[name=DSAPELLIDO1]')[0];
}

function _p22_fieldSexo()
{
    debug('>_p22_fieldSexo<');
    return Ext.ComponentQuery.query('[name=OTSEXO]')[0];
}

function _p22_fieldTipoPersona()
{
    debug('>_p22_fieldTipoPersona<');
    return Ext.ComponentQuery.query('[name=OTFISJUR]')[0];
}

function _p22_fieldCumuloPrima(){
    return Ext.ComponentQuery.query('[name=PTCUMUPR]')[0];
}

function _p22_fielCdNacion(){
    return Ext.ComponentQuery.query('[name=CDNACION]')[0];
}

function _p22_fieldResidente(){
    return Ext.ComponentQuery.query('[name=RESIDENTE]')[0];
}

function _p22_formDatosGenerales()
{
    debug('>_p22_formDatosGenerales<');
    return Ext.ComponentQuery.query('#_p22_formDatosGenerales')[0];
}

/* PARA EL LOADER */
function _p22_loadRecordCdperson(callbackload)
{
    debug('>_p22_loadRecordCdperson');
    _p22_PanelPrincipal().setLoading(true);
    Ext.Ajax.request(
    {
        url     : _p22_urlCargarPersonaCdperson
        ,params :
        {
            'smap1.cdperson' : _p22_cdperson
        }
        ,success : function(response)
        {
            _p22_PanelPrincipal().setLoading(false);
            var json=Ext.decode(response.responseText);
            if(json.exito)
            {
            	var record = new _p22_modeloGrid(json.smap2);
            	_p22_PanelPrincipal().setLoading(true);
			    _p22_formDatosGenerales().loadRecord(record);
			    Ext.Ajax.request(
			    {
			        url      : _p22_urlObtenerDomicilio
			        ,params  :
			        {
			            'smap1.cdperson' : record.get('CDPERSON')
			        }
			        ,success : function(response)
			        {
			            _p22_PanelPrincipal().setLoading(false);
			            var json=Ext.decode(response.responseText);
			            debug('json response:',json);
			            if(json.exito)
			            {
			                _p22_formDomicilio().loadRecord(new _p22_modeloDomicilio(json.smap1));
			                heredarPanel(_p22_formDomicilio());
			                
			                var valor = _fieldByName('CDCOLONI').getValue();
                    		_p22_heredarColonia(function(){
                    				_fieldByName('CDCOLONI').setValue(valor);
                    			}
                    		);
			                
			                if(!Ext.isEmpty(callbackload)){
			                	callbackload();
			                }
			            }
			            else
			            {
			                mensajeError(json.respuesta);
			            }
			        }
			        ,failure : function()
			        {
			            _p22_PanelPrincipal().setLoading(false);
			            errorComunicacion();
			        }
			    });
            }
            else
            {
                mensajeError(json.respuesta);
            }
        }
        ,failure : function()
        {
            _p22_PanelPrincipal().setLoading(false);
            errorComunicacion();
        }
    });
    debug('<_p22_loadRecordCdperson');
}
/* PARA EL LOADER */


function _p22_PanelPrincipal()
{
    debug('>_p22_PanelPrincipal<');
    return Ext.ComponentQuery.query('#_p22_PanelPrincipal')[0];
}

function _p22_guardarClic(callback, autosave)
{
    debug('>_p22_guardarClic');
    var valido = true;
    
    if(valido)
    {
        valido = autosave || _p22_formDatosGenerales().isValid();
        if(!valido)
        {
            mensajeWarning('Favor de verificar los datos generales');
        }
    }
    
//    if(valido)
//    {
//        valido = validarRFC(_p22_fieldRFC().getValue(),_p22_fieldTipoPersona().getValue());
//        if(!valido)
//        {
//        }
//    }
    
    if(valido&&_p22_fieldTipoPersona().getValue()=='F')
    {
        valido = autosave || (!Ext.isEmpty(_p22_fieldApat().getValue())
                 &&!Ext.isEmpty(_p22_fieldAmat().getValue())
                 &&!Ext.isEmpty(_p22_fieldSexo().getValue()));
        if(!valido)
        {
            mensajeWarning('Favor de introducir apellidos y sexo para persona f&iacute;sica');
            ponerActivo(1);
        }
    }
    
    if(valido&&Ext.isEmpty(_p22_fieldConsecutivo().getValue()))
    {
        _p22_fieldConsecutivo().setValue(1);
    }
    
    if(valido)
    {
        valido = autosave || _p22_formDomicilio().isValid();
        if(!valido)
        {
            mensajeWarning('Favor de verificar los datos del domicilio');
        }
    }
    
    if(valido)
    {
        _p22_PanelPrincipal().setLoading(true);
        Ext.Ajax.request(
        {
            url       : _p22_urlGuardar
            ,jsonData :
            {
                smap1  : _p22_formDatosGenerales().getValues()
                ,smap2 : _p22_formDomicilio().getValues()
                /*,params: {
                	esSalud: _esSaludDanios,
                	
                }*/
            }
            ,success : function(response)
            {
                _p22_PanelPrincipal().setLoading(false);
                var json = Ext.decode(response.responseText);
                debug('json response:',json);
                if(json.exito)
                {
                    _p22_fieldCdperson().setValue(json.smap1.CDPERSON);
                    _p22_cdperson = json.smap1.CDPERSON;
                    
                    if(!Ext.isEmpty(callback))
                    {
                        callback();
                    }
                    else
                    {
                        mensajeCorrecto('Datos guardados',json.respuesta);
                    }
                    try{
                    	if(_p22_cdperson!=false&&_p22_parentCallback){
                        	_p22_parentCallback(json);
                    	}
                    }catch(e){
                    	debug('Error',e)
                    }
                    
                }
                else
                {
                    mensajeError(json.respuesta);
                }
            }
            ,failure : function()
            {
                _p22_PanelPrincipal().setLoading(false);
                errorComunicacion();
            }
        });
    }
    
    debug('<_p22_guardarClic');
}

function _p22_formDomicilio()
{
    debug('>_p22_formDomicilio<');
    return Ext.ComponentQuery.query('#_p22_formDomicilio')[0];
}

function _p22_principalDatosAdicionales()
{
    debug('>_p22_principalDatosAdicionales<');
    return Ext.ComponentQuery.query('#_p22_principalDatosAdicionales')[0];
}

function _p22_fieldRFC()
{
    debug('>_p22_fieldRFC<');
    return Ext.ComponentQuery.query('[name=CDRFC]')[0];
}

function _p22_fieldCdperson()
{
    debug('>_p22_fieldCdperson<');
    return Ext.ComponentQuery.query('[name=CDPERSON]')[0];
}

function _p22_fieldConsecutivo()
{
    debug('>_p22_fieldConsecutivo<');
    return Ext.ComponentQuery.query('[name=NMORDDOM]')[0];
}

function _p22_datosAdicionalesClic()
{
    debug('>_p22_datosAdicionalesClic');
    
    windowAccionistas = undefined;
    
    /** PARA ACTUALIZAR EL NUEVO ESTATUS GENERAL DE LA PERSONA **/
    Ext.Ajax.request(
	        {
	            url       : _UrlActualizaStatusPersona
	            ,params: {
            		'params.pv_cdperson_i':  _p22_fieldCdperson().getValue()
            	}
	            ,success  : function(response)
	            {
	                //_p22_formDatosAdicionales().setLoading(false);
	                var json = Ext.decode(response.responseText);
	                debug('response text:',json);
	                if(json.exito)
	                {
	                    debug('Actualizando estatus de Persona: ');
	                    _fieldByName('STATUS').setValue(json.respuesta);
	                    
	                }
	                else
	                {
	                    mensajeError(json.respuesta);
	                }
	            }
	            ,failure  : function()
	            {
	                _p22_formDatosAdicionales().setLoading(false);
	                errorComunicacion();
	            }
	});
    
    _p22_PanelPrincipal().setLoading(true);
    
    _p22_principalDatosAdicionales().removeAll();
    
    Ext.Ajax.request(
    {
        url      : _p22_urlTatriperTvaloper
        ,params  : { 'smap1.cdperson' : _p22_fieldCdperson().getValue() }
        ,success : function(response)
        {
            _p22_PanelPrincipal().setLoading(false);
            var json = Ext.decode(response.responseText);
            debug('json response:',json);
            if(json.exito)
            {
                Ext.define('_p22_modeloTatriper',
                {
                    extend  : 'Ext.data.Model'
                    ,fields : Ext.decode(json.smap1.fieldsTatriper.substring("fields:".length))
                });
                
                
                _p22_principalDatosAdicionales().add({
            	    	layout: 'column',
            	    	border: false,
            	    	html:'<span style="font-size:14px; font-weight: bold;">Para que el estatus de la persona sea completo se requiere que los campos con el s&iacute;mbolo: <img src="${ctx}/resources/fam3icons/icons/transmit_error.png" alt="">, sean capturados.</span><br/><br/>'
            	});
                _p22_principalDatosAdicionales().add(
                Ext.create('Ext.form.Panel',
                        {
                            border    : 0
                            ,itemId   : '_p22_formDatosAdicionales'
//                            ,width    : 570
                            ,defaults : { style : 'margin:5px;' }
                            ,layout   :
                            {
                                type     : 'table'
                                ,columns : 3
                            }
                            ,items    : Ext.decode(json.smap1.itemsTatriper.substring("items:".length))
                        })
                );
                
                
               /* Ext.create('Ext.form.Panel',
                {
                    title   : 'Datos adicionales'
                    ,itemId : '_p22_principalDatosAdicionales'
                    ,width  : 650
                    ,height : 600
                    ,autoScroll : true
                    ,modal  : true
                    ,items  :
                    [{
            	    	layout: 'column',
            	    	border: false,
            	    	html:'<span style="font-size:14px; font-weight: bold;">Para que el estatus de la persona sea completo se requiere que los campos con el s&iacute;mbolo: <img src="${ctx}/resources/fam3icons/icons/transmit_error.png" alt="">, sean capturados.</span><br/><br/>'
            	    },
                        Ext.create('Ext.form.Panel',
                        {
                            border    : 0
                            ,itemId   : '_p22_formDatosAdicionales'
                            ,width    : 570
                            ,defaults : { style : 'margin:5px;' }
                            ,layout   :
                            {
                                type     : 'table'
                                ,columns : 3
                            }
                            ,items    : Ext.decode(json.smap1.itemsTatriper.substring("items:".length))
                        })
                    ]
                    ,bbar    :
                    [
                        '->'
                        ,{
                            text     : 'Guardar'
                            ,icon    : '${ctx}/resources/fam3icons/icons/disk.png'
                            ,handler : _p22_guardarDatosAdicionalesClic
                        }
                        ,{
                        	text: 'Cerrar'
                        	,icon: '${ctx}/resources/fam3icons/icons/cancel.png'
                        	,handler: function(){
                        		_p22_principalDatosAdicionales().close();
                        	}
                        }
                        ,'->'
                    ]
                }).show(); */
                
                fieldMail=_fieldByLabel('Correo electrónico', null, true);
                if(fieldMail)
                {
                    fieldMail.regex = /^[_A-Z0-9-]+(\.[_A-Z0-9-]+)*@[A-Z0-9-]+(\.[A-Z0-9-]+)*(\.[A-Z]{2,4})$/;
                }
                
				fieldEstCorp = _fieldByLabel('Estructura corporativa', null, true);
				var fieldEstCorpAux = Ext.clone(fieldEstCorp);
				
				if(fieldEstCorp){
					var panelDatAdic = fieldEstCorp.up();
					var indEstCorp = panelDatAdic.items.indexOf(fieldEstCorp);
					
					/*debug("fieldEstCorp" , fieldEstCorp);
					
					if(( (indEstCorp) %2) != 0){
						panelDatAdic.insert(indEstCorp,{
	            	    	layout: 'column',
	            	    	border: false,
	            	    	html:'<br/>'
	            	    });
						indEstCorp = indEstCorp + 1;
					}*/
					
					_p22_formDatosAdicionales().items.remove(fieldEstCorp, true);
					fieldEstCorp = fieldEstCorpAux;
					
					panelDatAdic.insert(indEstCorp,{
						xtype      : 'panel',
						//padding    :  '2px 2px 2px 2px',
						defaults : { style : 'margin:3px' },
						border     :  1,
						items      : [fieldEstCorp,{
                       	 xtype    : 'button'
                         	,text     : 'Ver/Editar Accionistas'
                             ,icon     : '${ctx}/resources/fam3icons/icons/award_star_add.png'
                             ,tooltip  : 'Ver/Editar Accionostas'
                             ,handler  : function(button)
                             {
                                verEditarAccionistas(_p22_fieldCdperson().getValue(), fieldEstCorp.getName().substring(fieldEstCorp.getName().length-2, fieldEstCorp.getName().length), fieldEstCorp.getValue());
                             }
 					}]
					});
					
					fieldEstCorp.addListener('beforeselect',function (combo){
						if(windowAccionistas){
							
							var valorAnterior = combo.getValue();
							
							Ext.Msg.show({
		    		            title: 'Confirmar acci&oacute;n',
		    		            msg  : 'Si cambia la Estructura Coorporativa perder&aacute; la lista de Accionistas guardada. &iquest;Desea continuar?',
		    		            buttons: Ext.Msg.YESNO,
		    		            fn: function(buttonId, text, opt) {
		    		            	if(buttonId == 'yes') {
		    		            		
		    		            		windowAccionistas = undefined;
		    		            		
		    		            		Ext.Ajax.request(
		    		                	        {
		    		                	            url       : _UrlEliminaAccionistas
		    		                	            ,params: {
	    		                	            		'params.pv_cdperson_i':   _p22_fieldCdperson().getValue(),
	    		                    	            	'params.pv_cdatribu_i':  fieldEstCorp.getName().substring(fieldEstCorp.getName().length-2, fieldEstCorp.getName().length)
	    		                	            	}
		    		                	            ,success  : function(response)
		    		                	            {
		    		                	                _p22_formDatosAdicionales().setLoading(false);
		    		                	                var json = Ext.decode(response.responseText);
		    		                	                debug('response text:',json);
		    		                	                if(json.exito)
		    		                	                {
		    		                	                    mensajeCorrecto('Aviso','Datos de Accionistas eliminados correctamente.');
		    		                	                }
		    		                	                else
		    		                	                {
		    		                	                    mensajeError(json.respuesta);
		    		                	                }
		    		                	            }
		    		                	            ,failure  : function()
		    		                	            {
		    		                	                _p22_formDatosAdicionales().setLoading(false);
		    		                	                errorComunicacion();
		    		                	            }
		    		                	});
		    		            	
		    		            		
		    		            	}else{
		    		            		combo.setValue(valorAnterior);
		    		            	}
		            			},
		    		            icon: Ext.Msg.QUESTION
		        			});	
						}else {
							return true;
						}
					});
				}
				
				_p22_formDatosAdicionales().items.each(function(item,index,len){
                	if(!item.allowBlank){
                		item.allowBlank = true;
                		if(item.getFieldLabel){
                			item.inicialField = item.getFieldLabel();
                			item.setFieldLabel('<span>'+ item.getFieldLabel() +'<img src="${ctx}/resources/fam3icons/icons/transmit_error.png" alt=""></span>');
                		}
                	}
                });
				
                _p22_formDatosAdicionales().loadRecord(new _p22_modeloTatriper(json.smap2));
                
                var itemsDocumento=Ext.ComponentQuery.query('[tieneDocu]');
                debug('itemsDocumento:',itemsDocumento);
                
                /*debug("agregar espacio docs: ", _p22_formDatosAdicionales().items.getCount());
                if(( (_p22_formDatosAdicionales().items.getCount()) %2 ) == 0){
                	_p22_formDatosAdicionales().add({
            	    	layout: 'column',
            	    	border: false,
            	    	html:'<br/>'
            	     });
                }*/
                
                for(var i=0;i<itemsDocumento.length;i++)
                {
                    itemDocumento=itemsDocumento[i];
                    
                    
                    if('DOC' == itemDocumento.tieneDocu){
	                    itemDocumento.up().add(
	                    		{
	        						xtype      : 'panel',
	        						//padding    :  '2px 2px 2px 2px',
	        						defaults : { style : 'margin:3px' },
	        						border     :  1,
	        						items      : [
	        						         Ext.clone(itemDocumento),
	        						        {
	        	                       		 xtype    : 'panel'
	        		                        ,layout  : 'hbox'
	        		                        ,border  : 0
	        		                        ,items   :
	        		                        [
	        		                            {
	        		                                xtype       : 'displayfield'
	        		                                ,labelWidth : 180
	        		                                ,fieldLabel : 'Documento digital' + (itemDocumento.allowBlank==false ? '<span style="font-size:10px;">(obligatorio)</span>' : '')
	        		                            }
	        		                            ,{
	        		                                xtype     : 'button'
	        		                                ,itemId   : itemDocumento.name
	        		                                ,icon     : '${ctx}/resources/fam3icons/icons/arrow_up.png'
	        		                                ,tooltip  : 'Subir nuevo'
	        		                                ,codidocu : itemDocumento.codidocu
	        		                                ,descrip  : itemDocumento.inicialField
	        		                                ,handler  : function(button)
	        		                                {
	        		                                	_DocASubir = button.itemId;
	        		                                    _p22_subirArchivo(_p22_fieldCdperson().getValue(),button.codidocu,button.descrip);
	        		                                }
	        		                            },{
	        		                                xtype     : 'button'
	        		                                ,icon     : '${ctx}/resources/fam3icons/icons/eye.png'
	        		                                ,tooltip  : 'Descargar'
	        		                                ,codidocu : itemDocumento.codidocu
	        		                                ,descrip  : itemDocumento.inicialField
	        		                                ,handler  : function(button)
	        		                                {
	        		                                    _p22_cargarArchivo(_p22_fieldCdperson().getValue(),button.codidocu,button.descrip);
	        		                                }
	        		                            }
	        		                        ]
	        		                    },{
											xtype: 'tbspacer',        	    	
											height: 15                	
						        	    }]
	        					}
	                    	);
	                }/*else{
	                	itemDocumento.up().add({
                	    	layout: 'column',
                	    	border: false,
                	    	html:'<br/>'
                	     });
	                }*/
                    //itemDocumento.destroy();
                    //itemDocumento.allowBlank = true;
                }
                
                _p22_formDatosAdicionales().add({
					xtype: 'tbspacer',        	    	
					height: 50                	
        	     });
                _p22_formDatosAdicionales().add({
					xtype: 'tbspacer',        	    	
					height: 50                	
        	     });
                _p22_formDatosAdicionales().add({
					xtype: 'tbspacer',        	    	
					height: 50                	
        	     });
                
            }
            else
            {
                mensajeError(json.respuesta);
            }
        }
        ,failure : function()
        {
            _p22_PanelPrincipal().setLoading(false);
            errorComunicacion();
        }
    });
    debug('<_p22_datosAdicionalesClic');
}

function _p22_guardarDatosAdicionalesClic()
{
    debug('>_p22_guardarDatosAdicionalesClic');
    
    var saveList = [];
    var updateList = [];
    var deleteList = [];
    
    if(windowAccionistas){
    	accionistasStore.getRemovedRecords().forEach(function(record,index,arr){
        	deleteList.push(record.data);
    	});
        accionistasStore.getNewRecords().forEach(function(record,index,arr){
    		if(record.dirty) saveList.push(record.data);
    	});
        accionistasStore.getUpdatedRecords().forEach(function(record,index,arr){
    		updateList.push(record.data);
    	});
    }
	
    debug('Accionistas Removed: ' , deleteList);
    debug('Accionistas Added: '   , saveList);
    debug('Accionistas Updated: ' , updateList);
    
    var valido=true;
    
    if(valido)
    {
        valido = _p22_formDatosAdicionales().isValid();
        if(!valido)
        {
            mensajeWarning('Favor de verificar los datos');
        }
    }
    
    if(valido)
    {
        _p22_formDatosAdicionales().setLoading(true);
        var jsonData = _p22_formDatosAdicionales().getValues();
        jsonData['cdperson'] = _p22_fieldCdperson().getValue();
        jsonData['esSalud'] = _esSaludDanios;
        jsonData['codigoExterno'] = (_esSaludDanios=='S')?_CDIDEEXTsel:_CDIDEPERsel;
        jsonData['codigoExterno2'] = (_esSaludDanios=='S')?_CDIDEPERsel:_CDIDEEXTsel;
        
        Ext.Ajax.request(
        {
            url       : _p22_urlGuadarTvaloper
            ,jsonData :
            {
                smap1 : jsonData,
                smap2  : _p22_formDatosGenerales().getValues(),
                params : _p22_formDomicilio().getValues()
            }
            ,success  : function(response)
            {
                _p22_formDatosAdicionales().setLoading(false);
                var json = Ext.decode(response.responseText);
                debug('response text:',json);
                if(json.exito)
                {
                    mensajeCorrecto('Datos guardados',json.respuesta);
                    
                    
                    	if(_esSaludDanios == 'S'){
                    		_CDIDEEXTsel = json.smap1.codigoExterno;
	                	}else{
							_CDIDEPERsel = json.smap1.codigoExterno;
	                	}
                    
	                	_p22_loadRecordCdperson(function(){
								var valor = _fieldByName('CDCOLONI').getValue();
			                    _p22_heredarColonia(function(){
			                    			_fieldByName('CDCOLONI').setValue(valor);
			                    		}
			                    );	                		
	                		}
	                	);
                }
                else
                {
                    mensajeError(json.respuesta);
                }
                
                /** PARA ACTUALIZAR EL NUEVO ESTATUS GENERAL DE LA PERSONA **/
                Ext.Ajax.request(
            	        {
            	            url       : _UrlActualizaStatusPersona
            	            ,params: {
        	            		'params.pv_cdperson_i':  _p22_fieldCdperson().getValue()
        	            	}
            	            ,success  : function(response)
            	            {
            	                //_p22_formDatosAdicionales().setLoading(false);
            	                var json = Ext.decode(response.responseText);
            	                debug('response text:',json);
            	                if(json.exito)
            	                {
            	                    debug('Actualizando estatus de Persona: ');
            	                    _fieldByName('STATUS').setValue(json.respuesta);
            	                    
            	                }
            	                else
            	                {
            	                    mensajeError(json.respuesta);
            	                }
            	            }
            	            ,failure  : function()
            	            {
            	                _p22_formDatosAdicionales().setLoading(false);
            	                errorComunicacion();
            	            }
            	});
            }
            ,failure  : function()
            {
                _p22_formDatosAdicionales().setLoading(false);
                errorComunicacion();
            }
        });
        
    }
    
    if(valido && (deleteList.length > 0 || saveList.length > 0 || updateList.length > 0)){
    	_p22_formDatosAdicionales().setLoading(true);
    	
    	Ext.Ajax.request(
    	        {
    	            url       : _UrlGuardaAccionista
    	            ,jsonData :
    	            {
    	            	params: {
    	            		'pv_cdperson_i':   _p22_fieldCdperson().getValue(),
        	            	'pv_cdatribu_i':  fieldEstCorp.getName().substring(fieldEstCorp.getName().length-2, fieldEstCorp.getName().length),
        	            	'pv_cdtpesco_i':  fieldEstCorp.getValue()
    	            	},
    	                'saveList'   : saveList,
    	                'deleteList' : deleteList,
    	                'updateList' : updateList
    	            }
    	            ,success  : function(response)
    	            {
    	                _p22_formDatosAdicionales().setLoading(false);
    	                var json = Ext.decode(response.responseText);
    	                debug('response text:',json);
    	                if(json.exito)
    	                {
    	                	windowAccionistas = undefined;
    	                    debug('Datos de Accionistas guardados correctamente.');
    	                }
    	                else
    	                {
    	                    mensajeError(json.respuesta);
    	                }
    	            }
    	            ,failure  : function()
    	            {
    	                _p22_formDatosAdicionales().setLoading(false);
    	                errorComunicacion();
    	            }
    	});
	}
    
    debug('<_p22_guardarDatosAdicionalesClic');
}

function _p22_formDatosAdicionales()
{
    debug('>_p22_formDatosAdicionales<');
    return Ext.ComponentQuery.query('#_p22_formDatosAdicionales')[0];
}

function _p22_documentosClic()
{
    debug('>_p22_documentosClic');
    var windowDocsPer = Ext.create('Ext.window.Window',
    {
        title        : 'Documentos'
        ,modal       : true
        ,buttonAlign : 'center'
        ,width       : 600
        ,height      : 400
        ,autoScroll  : true
        ,loader      :
        {
            url       : _p22_urlPantallaDocumentos
            ,params   :
            {
                'smap1.cdperson'  : _p22_fieldCdperson().getValue()
            }
            ,scripts  : true
            ,autoLoad : true
        }
    }).show();
    centrarVentanaInterna(windowDocsPer);
    debug('<_p22_documentosClic');
}

function _p22_subirArchivo(cdperson,codidocu,descrip)
{
    debug('>_p22_subirArchivo',cdperson,codidocu,descrip);
    _p22_windowAgregarDocu=Ext.create('Ext.window.Window',
            {
                id           : '_p22_WinPopupAddDoc'
                ,title       : 'Agregar documento'
                ,closable    : false
                ,modal       : true
                ,width       : 500
                //,height   : 700
                ,bodyPadding : 5
                ,items       :
                [
                    panelSeleccionDocumento= Ext.create('Ext.form.Panel',
                    {
                        border       : 0
                        ,url         : _p22_urlSubirArchivo
                        ,buttonAlign : 'center'
                        ,items       :
                        [
                            {
                                xtype       : 'datefield'
                                ,readOnly   : true
                                ,format     : 'd/m/Y'
                                ,name       : 'smap1.fecha'
                                ,value      : new Date()
                                ,fieldLabel : 'Fecha'
                            }
                            ,{
                                xtype       : 'textfield'
                                ,fieldLabel : 'Descripci&oacute;n'
                                ,name       : 'smap1.descripcion'
                                ,value      : descrip
                                ,readOnly   : true
                                ,width      : 450
                            }
                            ,{
                                xtype       : 'filefield'
                                ,fieldLabel : 'Documento'
                                ,buttonText : 'Examinar...'
                                ,buttonOnly : false
                                ,width      : 450
                                ,name       : 'file'
                                ,cAccept    : ['jpg','png','gif','zip','pdf','rar','jpeg','doc','docx','xls','xlsx','ppt','pptx']
                                ,listeners  :
                                {
                                    change : function(me)
                                    {
                                        var indexofPeriod = me.getValue().lastIndexOf("."),
                                        uploadedExtension = me.getValue().substr(indexofPeriod + 1, me.getValue().length - indexofPeriod).toLowerCase();
                                        if (false&&!Ext.Array.contains(this.cAccept, uploadedExtension))
                                        {
                                            Ext.MessageBox.show(
                                            {
                                                title   : 'Error de tipo de archivo',
                                                msg     : 'Extensiones permitidas: ' + this.cAccept.join(),
                                                buttons : Ext.Msg.OK,
                                                icon    : Ext.Msg.WARNING
                                            });
                                            me.reset();
                                            Ext.getCmp('_p22_botGuaDoc').setDisabled(true);
                                        }
                                        else
                                        {
                                            Ext.getCmp('_p22_botGuaDoc').setDisabled(false);
                                        }
                                    }
                                }
                            }
                            ,Ext.create('Ext.panel.Panel',
                            {
                                html    :'<iframe id="_p22_IframeUploadDoc" name="_p22_IframeUploadDoc"></iframe>'
                                ,hidden : true
                            })
                            ,Ext.create('Ext.panel.Panel',
                            {
                                border  : 0
                                ,html   :'<iframe id="_p22_IframeUploadPro" name="_p22_IframeUploadPro" width="100%" height="30" src="'+_p22_UrlUploadPro+'" frameborder="0"></iframe>'
                                ,hidden : false
                            })
                        ]
                        ,buttons     :
                        [
                            {
                                id        : '_p22_botGuaDoc'
                                ,text     : 'Agregar'
                                ,icon     : '${ctx}/resources/fam3icons/icons/disk.png'
                                ,disabled : true
                                ,handler  : function (button,e)
                                {
                                    debug(button.up().up().getForm().getValues());
                                    button.setDisabled(true);
                                    Ext.getCmp('_p22_BotCanDoc').setDisabled(true);
                                    Ext.create('Ext.form.Panel').submit(
                                    {
                                        url             : _p22_UrlUploadPro
                                        ,standardSubmit : true
                                        ,target         : '_p22_IframeUploadPro'
                                        ,params         :
                                        {
                                            uploadKey : '1'
                                        }
                                    });
                                    button.up().up().getForm().submit(
                                    {
                                        standardSubmit : true
                                        ,target        : '_p22_IframeUploadDoc'
                                        ,params        :
                                        {
                                            'smap1.cdperson'  : cdperson
                                            ,'smap1.codidocu' : codidocu
                                        }
                                    });
                                }
                            }
                            ,{
                                id       : '_p22_BotCanDoc'
                                ,text    : 'Cancelar'
                                ,icon    : '${ctx}/resources/fam3icons/icons/cancel.png'
                                ,handler : function (button,e)
                                {
                                    _p22_windowAgregarDocu.destroy();
                                }
                            }
                        ]
                    })
                ]
            }).show();
            centrarVentanaInterna(_p22_windowAgregarDocu);
    debug('<_p22_subirArchivo');
}

function _p22_cargarArchivo(cdperson,codidocu,dsdocume)
{
    debug('>_p22_cargarArchivo',cdperson,codidocu,dsdocume);
    _p22_principalDatosAdicionales().setLoading(true);
    Ext.Ajax.request(
    {
        url      : _p22_urlCargarNombreArchivo
        ,params  :
        {
            'smap1.cdperson'  : cdperson
            ,'smap1.codidocu' : codidocu
        }
        ,success : function(response)
        {
            _p22_principalDatosAdicionales().setLoading(false);
            var json=Ext.decode(response.responseText);
            debug('json response:',json);
            if(json.exito)
            {
                var numRand=Math.floor((Math.random()*100000)+1);
                debug('numRand a: ',numRand);
                var windowVerDocu=Ext.create('Ext.window.Window',
                {
                    title          : dsdocume
                    ,width         : 700
                    ,height        : 500
                    ,collapsible   : true
                    ,titleCollapse : true
                    ,html          : '<iframe innerframe="'+numRand+'" frameborder="0" width="100" height="100"'
                                     +'src="'+_p22_urlViewDoc+'?idPoliza='+cdperson+'&filename='+json.smap1.cddocume+'">'
                                     +'</iframe>'
                    ,listeners     :
                    {
                        resize : function(win,width,height,opt){
                            debug(width,height);
                            $('[innerframe="'+numRand+'"]').attr({'width':width-20,'height':height-60});
                        }
                    }
                }).show();
                centrarVentanaInterna(windowVerDocu);
            }
            else
            {
                mensajeError(json.respuesta);
            }
        }
        ,failure : function()
        {
            _p22_principalDatosAdicionales().setLoading(false);
            errorComunicacion();
        }
    });
    
    debug('<_p22_cargarArchivo');
}

function panDocSubido()
{
    _p22_windowAgregarDocu.destroy();
    
    var elemento = _fieldByName(_DocASubir,null,true);
    if(!Ext.isEmpty(elemento.store)){
    	elemento.setValue('S');
    }
}


function verEditarAccionistas(cdperson, cdatribu, cdestructcorp){
	
	
	if(Ext.isEmpty(cdestructcorp)){
		mensajeWarning('Debe seleccionar una Estructura Coorporativa.');
		return;
	}
	
	if(!windowAccionistas){
		Ext.define('modeloAccionistas',{
	        extend  : 'Ext.data.Model'
	        ,fields :
	        [
	            'NMORDINA'
	            ,'DSNOMBRE'
	            ,'CDNACION'
	            ,'PORPARTI'
	        ]
		});
		
		accionistasStore = Ext.create('Ext.data.Store',
			    {
					pageSize : 20,
			        autoLoad : true
			        ,model   : 'modeloAccionistas'
			        ,proxy   :
			        {
			            type         : 'memory'
			            ,enablePaging : true
			            ,reader      : 'json'
			            ,data        : []
			        }
			    });
		
		gridAccionistas = Ext.create('Ext.grid.Panel',
		    {
		    title    : 'Para Editar un Accionista de Doble Clic en la fila deseada.'
		    ,height  : 200
		    ,plugins : Ext.create('Ext.grid.plugin.RowEditing',
		    {
		    	pluginId: 'accionistasRowId',
		        clicksToEdit  : 2,
		        errorSummary : false,
		        listeners: {
		    		beforeedit: function(){
		    			_0_botAceptar.disable();
		    		},
		    		edit: function(){
		    			_0_botAceptar.enable();
		    		},
		    		canceledit: function(){
		    			_0_botAceptar.enable();
		    		}
		    	}
		        
		    })
		    ,tbar     :
		        [
		            {
		                text     : 'Agregar'
		                ,icon    : '${ctx}/resources/fam3icons/icons/add.png'
		                ,handler : function(){
		                	accionistasStore.add(new modeloAccionistas());
		                	gridAccionistas.getPlugin('accionistasRowId').startEdit(accionistasStore.getCount()-1,0);
		                }
		            },{
		                text     : 'Eliminar'
			                ,icon    : '${ctx}/resources/fam3icons/icons/delete.png'
			                ,handler : function(){
			                	accionistasStore.remove(gridAccionistas.getSelectionModel().getSelection());
			                }
			            }
		        ]
		    ,columns :
		    [
		        {
		            header     : 'Accionista'
		            ,dataIndex : 'DSNOMBRE'
		            ,flex      : 1
		            ,editor    :
		            {
		                xtype             : 'textfield'
		                ,allowBlank       : false
		            }
		        }
		        ,{
		            header     : 'Nacionalidad'
		            ,dataIndex : 'CDNACION'
		            ,flex      : 1
		            ,renderer  : function(valor){
		            	return rendererColumnasDinamico(valor,'CDNACION'); 
		            }
		            ,editor    :
		            {
		                xtype         : 'combobox',
		                allowBlank    : false,
		                name          : 'CDNACION',
		                valueField    : 'key',
		                displayField  : 'value',
		                forceSelection: true,
		                typeAhead     : true,
		                anyMatch      : true,
		                store         : Ext.create('Ext.data.Store', {
		                    model     : 'Generic',
		                    autoLoad  : true,
		                    proxy     : {
		                        type        : 'ajax'
		                        ,url        : _URL_CARGA_CATALOGO
		                        ,extraParams: {catalogo:_CAT_NACIONALIDAD}
		                        ,reader     :
		                        {
		                            type  : 'json'
		                            ,root : 'lista'
		                        }
		                    }
		                })
		               
		            }
		        }
		        ,{
		            header     : 'Porcentaje Participaci&oacute;n'
		            ,dataIndex : 'PORPARTI'
		            ,flex      : 1
		            ,editor    :
		            {
		                xtype             : 'numberfield'
		                ,allowBlank       : false
		                ,allowDecimals    : true
		                ,minValue         : 0
		                ,negativeText     : 'No se puede introducir valores negativos.'
		                ,decimalSeparator : '.'
		            }
		        }
		    ]
		    ,store : accionistasStore
		});
		
		
		_0_botAceptar = Ext.create('Ext.Button',{
            text: 'Aceptar',
            icon:'${ctx}/resources/fam3icons/icons/accept.png',
            handler: function() {
           	 windowAccionistas.close();
            }
      	});
		
		windowAccionistas = Ext.create('Ext.window.Window', {
	          title: 'Accionistas',
	          closeAction: 'close',
	          modal:true,
	          closable: false,
	          height : 320,
	          width  : 800,
		      items: [gridAccionistas],
	          bodyStyle:'padding:15px;',
	          buttons:[_0_botAceptar]
	        });
		
			var params = {
				'params.pv_cdperson_i' : cdperson,
				'params.pv_cdatribu_i' : cdatribu,
				'params.pv_cdtpesco_i' : cdestructcorp
			};
			
			cargaStorePaginadoLocal(accionistasStore, _UrlCargaAccionistas, 'slist1', params, function (options, success, response){
	    		if(success){
	                var jsonResponse = Ext.decode(response.responseText);
	                
	                if(!jsonResponse.success) {
	                    showMessage(_MSG_SIN_DATOS, _MSG_BUSQUEDA_SIN_DATOS, Ext.Msg.OK, Ext.Msg.INFO);
	                }
	            }else{
	                showMessage('Error', 'Error al obtener los datos.', Ext.Msg.OK, Ext.Msg.ERROR);
	            }
	    	}, gridAccionistas);
	}
	
			windowAccionistas.show();
			
}


////// funciones //////
</script>
</head>
<body>
<div id="_p22_divpri" style="height : 1400px;"></div>
</body>
</html>