<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<script>
    ///////////////////////
    //////variables //////
    /*///////////////////*/
    var storeCoberturasActuales_p3;
    var storeCoberturasPorEliminar_p3;
    var storeCoberturasPorAgregar_p3;
    var storeCoberturasDisponibles_p3;
    var storeIncisos_p3;
    var panelCoberturasp3;
    var urlCargarCoberturasp3 = '<s:url namespace="/" action="cargarPantallaCoberturas" />';
    var urlCargarCoberturasDispp3 = '<s:url namespace="/endosos" action="obtenerCoberturasDisponibles" />';
    var _endcob_urlObtenerComponenteSituacionCobertura = '<s:url namespace="/endosos" action="obtenerComponenteSituacionCobertura" />';
    var inputCduniecop3 = '<s:property value="smap1.pv_cdunieco" />';
    var inputCdramop3 = '<s:property value="smap1.pv_cdramo" />';
    var inputEstadop3 = '<s:property value="smap1.pv_estado" />';
    var inputNmpolizap3 = '<s:property value="smap1.pv_nmpoliza" />';
    var inputCdpersonap3 = '<s:property value="smap1.pv_cdperson" />';
    var inputNtramitep3  = '<s:property value="smap1.ntramite" />';
    var inputAltabajap3  = '<s:property value="smap1.altabaja" />';
    var inputCdtipsitp3  = '<s:property value="smap1.cdtipsit" />';
    var inputFenacimip3  = '<s:property value="smap1.fenacimi" />';
    var columnasTatrisit = [<s:property value="columnas" escapeHtml="false" />];
    var urlGuardarCoberturasp3 = '<s:url namespace="/" action="guardarCoberturasUsuario" />';
    var urlTatrip3 = '<s:url namespace="/" action="obtenerCamposTatrigar" />';
    var urlLoadTatrip3 = '<s:url namespace="/" action="obtenerValoresTatrigar" />';
    var urlSaveTatrip3 = '<s:url namespace="/" action="guardarValoresTatrigar" />';
    var urlRecuperacionSimpleListap3 = '<s:url namespace="/emision"    action="recuperacionSimpleLista" />';
    var endcobUrlDoc   = '<s:url namespace="/documentos" action="ventanaDocumentosPoliza" />';
    var endcobUrlGuardar = '<s:url namespace="/endosos" action="guardarEndosoCoberturas" />';
    debug('inputCduniecop3',inputCduniecop3);
    debug('inputCdramop3',inputCdramop3);
    debug('inputEstadop3',inputEstadop3);
    debug('inputNmpolizap3',inputNmpolizap3);
    debug('inputCdpersonap3',inputCdpersonap3);
    debug('inputNtramitep3',inputNtramitep3);
    debug('inputAltabajap3',inputAltabajap3);
    debug('inputCdtipsitp3',inputCdtipsitp3);
    /*///////////////////*/
    //////variables //////
    ///////////////////////
    
    ///////////////////////
    ////// funciones //////
    /*///////////////////*/
    function endcobSumit(form,confirmar)
    {
        debug('generar endoso');
        //var form=this.up().up();
        if(form.isValid())
        {
            // Eliminamos los filtros para que enviemos todas las coberturas editadas:
            storeCoberturasPorAgregar_p3.clearFilter();
            storeCoberturasPorEliminar_p3.clearFilter();
        	
            form.setLoading(true);
            var json={};
            json['omap1']=form.getValues();
            json['omap1']['pv_cdunieco_i'] = inputCduniecop3;
            json['omap1']['pv_cdramo_i']   = inputCdramop3;
            json['omap1']['pv_estado_i']   = inputEstadop3;
            json['omap1']['pv_nmpoliza_i'] = inputNmpolizap3;
            var slist1=[];
            storeCoberturasPorEliminar_p3.each(function(record)
            {
                slist1.push(
                {
                    garantia  : record.get('GARANTIA')
                    ,cdcapita : record.get('CDCAPITA')
                    ,status   : record.get('status')
                    ,ptcapita : record.get('SUMA_ASEGURADA')
                    ,ptreduci : record.get('ptreduci')
                    ,fereduci : record.get('fereduci')
                    ,swrevalo : record.get('swrevalo')
                    ,cdagrupa : record.get('cdagrupa')
                    ,cdtipbca : record.get('cdtipbca')
                    ,ptvalbas : record.get('ptvalbas')
                    ,swmanual : record.get('swmanual')
                    ,swreas   : record.get('swreas')
                    ,nmsituac : record.get('nmsituac')
                });
            });
            json['slist1']=slist1;
            var slist2=[];
            storeCoberturasPorAgregar_p3.each(function(record)
            {
                slist2.push(
                {
                    garantia  : record.get('GARANTIA')
                    ,cdcapita : record.get('CDCAPITA')
                    ,status   : record.get('status')
                    ,ptcapita : record.get('SUMA_ASEGURADA')
                    ,ptreduci : record.get('ptreduci')
                    ,fereduci : record.get('fereduci')
                    ,swrevalo : record.get('swrevalo')
                    ,cdagrupa : record.get('cdagrupa')
                    ,cdtipbca : record.get('cdtipbca')
                    ,ptvalbas : record.get('ptvalbas')
                    ,swmanual : record.get('swmanual')
                    ,swreas   : record.get('swreas')
                    ,cdatribu : record.get('cdatribu')
                    ,otvalor  : record.get('otvalor')
                    ,nmsituac : record.get('nmsituac')
                });
            });
            json['slist2']=slist2;
            json['smap1']={};
            json['smap1']['cdperson']  = inputCdpersonap3;
            json['smap1']['altabaja']  = inputAltabajap3;
            json['smap1']['cdtipsit']  = inputCdtipsitp3;
            json['smap1']['confirmar'] = confirmar;
            json['smap1']['fenacimi']  = inputFenacimip3;
            debug(json);
            Ext.Ajax.request(
            {
                url       : endcobUrlGuardar
                ,jsonData : json
                ,timeout  : 180000
                ,success  : function(response)
                {
                    form.setLoading(false);
                    json=Ext.decode(response.responseText);
                    debug(json);
                    if(json.success==true)
                    {
                        Ext.Msg.show(
                        {
                            title   : 'Endoso generado',
                            msg     : json.mensaje,
                            buttons : Ext.Msg.OK
                        });
                        if(confirmar=='si')
                        {
                            //////////////////////////////////
                            ////// usa codigo del padre //////
                            /*//////////////////////////////*/
                            marendNavegacion(2);
                            /*//////////////////////////////*/
                            ////// usa codigo del padre //////
                            //////////////////////////////////
                        }
                        else
                        {
                            //////////////////////////////////
                            ////// usa codigo del padre //////
                            /*//////////////////////////////*/
                            marendNavegacion(4);
                            /*//////////////////////////////*/
                            ////// usa codigo del padre //////
                            //////////////////////////////////
                        }
                    }
                    else
                    {
                        mensajeError(json.error);
                    }
                }
                ,failure  : function()
                {
                    form.setLoading(false);
                    Ext.Msg.show(
                    {
                        title   : 'Error',
                        icon    : Ext.Msg.ERROR,
                        msg     : 'Error de comunicaci&oacute;n',
                        buttons : Ext.Msg.OK
                    });
                }
            });
        }
        else
        {
            Ext.Msg.show(
            {
                title   : 'Datos imcompletos',
                icon    : Ext.Msg.WARNING,
                msg     : 'Favor de llenar los campos requeridos',
                buttons : Ext.Msg.OK
            });
        }
    }
    /*///////////////////*/
    ////// funciones //////
    ///////////////////////
    
    Ext.onReady(function() {

                /////////////////////
                ////// Modelos //////
                /*/////////////////*/
                Ext.define('Modelo1p3', {
                    extend : 'Ext.data.Model',
                    fields : [ {
                        name : 'GARANTIA'
                    }, {
                        name : 'NOMBRE_GARANTIA'
                    }, {
                        name : 'SWOBLIGA'
                    }, {
                        name : 'SUMA_ASEGURADA'
                    }, {
                        name : 'CDCAPITA'
                    }, {
                        name : 'status'
                    }, {
                        name : 'cdtipbca'
                    }, {
                        name : 'ptvalbas'
                    }, {
                        name : 'swmanual'
                    }, {
                        name : 'swreas'
                    }, {
                        name : 'cdagrupa'
                    }, {
                        name : 'ptreduci'
                    }, {
                        name : 'fereduci',
                        type : 'date',
                        dateFormat : 'd/m/Y'
                    }, {
                        name : 'swrevalo'
                    }
                    ,'cdatribu'
                    ,'otvalor'
                    ,'nmsituac'
                    ]//,
                    //idProperty: 'nmsituac'
                });
                
                Ext.define('ModelInciso_p3',{
                    extend  : 'Ext.data.Model',
                    fields :[
                        //MPOLISIT
                        "CDUNIECO"    , "CDRAMO"   , "ESTADO"     , "NMPOLIZA"
                        ,"NMSITUAC"   , "NMSUPLEM" , "STATUS"     , "CDTIPSIT"
                        ,"SWREDUCI"   , "CDAGRUPA" , "CDESTADO"   , "CDGRUPO"
                        ,"NMSITUAEXT" , "NMSITAUX" , "NMSBSITEXT" , "CDPLAN"
                        ,"CDASEGUR"   , "DSGRUPO"
                        ,{ name : 'FEFECSIT' , type : 'date' , dateFormat : 'd/m/Y' }
                        ,{ name : 'FECHAREF' , type : 'date' , dateFormat : 'd/m/Y' }
                        //TVALOSIT
                        ,'NMSUPLEM_TVAL'
                        ,"OTVALOR01" , "OTVALOR02" , "OTVALOR03" , "OTVALOR04" , "OTVALOR05" , "OTVALOR06" , "OTVALOR07" , "OTVALOR08" , "OTVALOR09" , "OTVALOR10"
                        ,"OTVALOR11" , "OTVALOR12" , "OTVALOR13" , "OTVALOR14" , "OTVALOR15" , "OTVALOR16" , "OTVALOR17" , "OTVALOR18" , "OTVALOR19" , "OTVALOR20"
                        ,"OTVALOR21" , "OTVALOR22" , "OTVALOR23" , "OTVALOR24" , "OTVALOR25" , "OTVALOR26" , "OTVALOR27" , "OTVALOR28" , "OTVALOR29" , "OTVALOR30"
                        ,"OTVALOR31" , "OTVALOR32" , "OTVALOR33" , "OTVALOR34" , "OTVALOR35" , "OTVALOR36" , "OTVALOR37" , "OTVALOR38" , "OTVALOR39" , "OTVALOR40"
                        ,"OTVALOR41" , "OTVALOR42" , "OTVALOR43" , "OTVALOR44" , "OTVALOR45" , "OTVALOR46" , "OTVALOR47" , "OTVALOR48" , "OTVALOR49" , "OTVALOR50"
                        ,"OTVALOR51" , "OTVALOR52" , "OTVALOR53" , "OTVALOR54" , "OTVALOR55" , "OTVALOR56" , "OTVALOR57" , "OTVALOR58" , "OTVALOR59" , "OTVALOR60"
                        ,"OTVALOR61" , "OTVALOR62" , "OTVALOR63" , "OTVALOR64" , "OTVALOR65" , "OTVALOR66" , "OTVALOR67" , "OTVALOR68" , "OTVALOR69" , "OTVALOR70"
                        ,"OTVALOR71" , "OTVALOR72" , "OTVALOR73" , "OTVALOR74" , "OTVALOR75" , "OTVALOR76" , "OTVALOR77" , "OTVALOR78" , "OTVALOR79" , "OTVALOR80"
                        ,"OTVALOR81" , "OTVALOR82" , "OTVALOR83" , "OTVALOR84" , "OTVALOR85" , "OTVALOR86" , "OTVALOR87" , "OTVALOR88" , "OTVALOR89" , "OTVALOR90"
                        ,"OTVALOR91" , "OTVALOR92" , "OTVALOR93" , "OTVALOR94" , "OTVALOR95" , "OTVALOR96" , "OTVALOR97" , "OTVALOR98" , "OTVALOR99"
                        ,"DSVALOR01" , "DSVALOR02" , "DSVALOR03" , "DSVALOR04" , "DSVALOR05" , "DSVALOR06" , "DSVALOR07" , "DSVALOR08" , "DSVALOR09" , "DSVALOR10"
                        ,"DSVALOR11" , "DSVALOR12" , "DSVALOR13" , "DSVALOR14" , "DSVALOR15" , "DSVALOR16" , "DSVALOR17" , "DSVALOR18" , "DSVALOR19" , "DSVALOR20"
                        ,"DSVALOR21" , "DSVALOR22" , "DSVALOR23" , "DSVALOR24" , "DSVALOR25" , "DSVALOR26" , "DSVALOR27" , "DSVALOR28" , "DSVALOR29" , "DSVALOR30"
                        ,"DSVALOR31" , "DSVALOR32" , "DSVALOR33" , "DSVALOR34" , "DSVALOR35" , "DSVALOR36" , "DSVALOR37" , "DSVALOR38" , "DSVALOR39" , "DSVALOR40"
                        ,"DSVALOR41" , "DSVALOR42" , "DSVALOR43" , "DSVALOR44" , "DSVALOR45" , "DSVALOR46" , "DSVALOR47" , "DSVALOR48" , "DSVALOR49" , "DSVALOR50"
                        ,"DSVALOR51" , "DSVALOR52" , "DSVALOR53" , "DSVALOR54" , "DSVALOR55" , "DSVALOR56" , "DSVALOR57" , "DSVALOR58" , "DSVALOR59" , "DSVALOR60"
                        ,"DSVALOR61" , "DSVALOR62" , "DSVALOR63" , "DSVALOR64" , "DSVALOR65" , "DSVALOR66" , "DSVALOR67" , "DSVALOR68" , "DSVALOR69" , "DSVALOR70"
                        ,"DSVALOR71" , "DSVALOR72" , "DSVALOR73" , "DSVALOR74" , "DSVALOR75" , "DSVALOR76" , "DSVALOR77" , "DSVALOR78" , "DSVALOR79" , "DSVALOR80"
                        ,"DSVALOR81" , "DSVALOR82" , "DSVALOR83" , "DSVALOR84" , "DSVALOR85" , "DSVALOR86" , "DSVALOR87" , "DSVALOR88" , "DSVALOR89" , "DSVALOR90"
                        ,"DSVALOR91" , "DSVALOR92" , "DSVALOR93" , "DSVALOR94" , "DSVALOR95" , "DSVALOR96" , "DSVALOR97" , "DSVALOR98" , "DSVALOR99"
                        //MPERSONA
                        ,"CDPERSON"    , "CDTIPIDE"  , "CDIDEPER"   , "DSNOMBRE"
                        ,"CDTIPPER"    , "OTFISJUR"  , "OTSEXO"     , "CDRFC"
                        ,"FOTO"        , "DSEMAIL"   , "DSNOMBRE1"  , "DSAPELLIDO"
                        ,"DSAPELLIDO1" , "CDNACION"  , "DSCOMNOM"   , "DSRAZSOC"
                        ,"DSNOMUSU"    , "CDESTCIV"  , "CDGRUECO"   , "CDSTIPPE"
                        ,"NMNUMNOM"    , "CURP"      , "CANALING"   , "CONDUCTO"
                        ,"PTCUMUPR"    , "STATUSPER" , "RESIDENCIA" , "NONGRATA"
                        ,"CDIDEEXT"    , "CDSUCEMI"
                        ,{ name : 'FENACIMI'  , type : 'date' , dateFormat : 'd/m/Y' }
                        ,{ name : 'FEINGRESO' , type : 'date' , dateFormat : 'd/m/Y' }
                        ,{ name : 'FEACTUAL'  , type : 'date' , dateFormat : 'd/m/Y' }
                        //MPOLIPER
                        ,"CDROL" , "NMORDDOM" , "SWRECLAM" , "SWEXIPER" , "CDPARENT" , "PORBENEF"
                        //CUSTOM
                        ,'ATRIBUTOS','NOMBRECOMPLETO'
                    ]
                });
                /*/////////////////*/
                ////// Modelos //////
                /////////////////////
                ////////////////////
                ////// Stores //////
                /*////////////////*/
                storeCoberturasActuales_p3 = Ext.create('Ext.data.Store', {
                    storeId : 'storeCoberturasActuales_p3',
                    model : 'Modelo1p3',
                    proxy : {
                        type : 'ajax',
                        url : urlCargarCoberturasp3,
                        extraParams : {
                            'smap1.pv_cdunieco_i' : inputCduniecop3,
                            'smap1.pv_cdramo_i' : inputCdramop3,
                            'smap1.pv_estado_i' : inputEstadop3,
                            'smap1.pv_nmpoliza_i' : inputNmpolizap3
                        },
                        reader : {
                            type : 'json',
                            root : 'slist1'
                        }
                    },
                    autoLoad : false
                });

                storeCoberturasPorEliminar_p3 = Ext.create('Ext.data.Store', {
                    storeId : 'storeCoberturasPorEliminar_p3',
                    model : 'Modelo1p3'
                });
                
                storeCoberturasPorAgregar_p3 = Ext.create('Ext.data.Store', {
                    storeId : 'storeCoberturasPorAgregar_p3',
                    model : 'Modelo1p3'
                });
                
                storeCoberturasDisponibles_p3 = Ext.create('Ext.data.Store', {
                    storeId : 'storeCoberturasDisponibles_p3',
                    model : 'Modelo1p3',
                    proxy : {
                        type : 'ajax',
                        url : urlCargarCoberturasDispp3,
                        extraParams : {
                            'smap1.pv_cdunieco_i' : inputCduniecop3,
                            'smap1.pv_cdramo_i' : inputCdramop3,
                            'smap1.pv_estado_i' : inputEstadop3,
                            'smap1.pv_nmpoliza_i' : inputNmpolizap3
                        },
                        reader : {
                            type : 'json',
                            root : 'slist1'
                        }
                    },
                    autoLoad : false
                });
                
                storeIncisos_p3=Ext.create('Ext.data.Store', {
                    model    : 'ModelInciso_p3',
                    autoLoad : false,
                    proxy    : {
                        type   : 'ajax',
                        url    : urlRecuperacionSimpleListap3,
                        extraParams :{
                            'smap1.procedimiento' : 'RECUPERAR_INCISOS_POLIZA_GRUPO_FAMILIA',
                            'smap1.cdunieco'      : inputCduniecop3,
                            'smap1.cdramo'        : inputCdramop3,
                            'smap1.estado'        : inputEstadop3,
                            'smap1.nmpoliza'      : inputNmpolizap3,
                            'smap1.cdgrupo'       : '',
                            'smap1.nmfamili'      : '',
                            'smap1.nivel'         : 'DUMMY'
                        },
                        reader :{
                            type             : 'json',
                            root            : 'slist1',
                            successProperty : 'success',
                            messageProperty : 'respuesta'
                        }
                    }
                });
                storeIncisos_p3.load({
                    callback: function(records, operation, success) {
                        if(success){
                            debug('records', records);
                            Ext.Array.each(records, function(record, index, recordsItSelf) {
                                
                                
                                debug('SITUAC=', record.get("NMSITUAC"));
                                console.log('record=', record);
                                console.log('index=', index);
                                console.log('recordsItSelf=', recordsItSelf);
                                
                                storeCoberturasActuales_p3.load({
                                    addRecords: true,
                                    params: {'smap1.pv_nmsituac_i' : record.get("NMSITUAC")}
                                });
                                
                                storeCoberturasDisponibles_p3.load({
                                    addRecords: true,
                                    params: {'smap1.pv_nmsituac_i' : record.get("NMSITUAC")}
                                });
                                
                                debug('storeCoberturasActuales_p3=', storeCoberturasActuales_p3);
                            });
                            
                        } else {
                            showMessage('Error', 'No hay incisos para la p&oacute;liza', Ext.Msg.OK, Ext.Msg.ERROR)
                        }
                    }
                });
                /*////////////////*/
                ////// Stores //////
                ////////////////////
                
                /////////////////////////
                ////// Componentes //////
                /*/////////////////////*/
                /*/////////////////////*/
                ////// Componentes //////
                /////////////////////////
                
                ///////////////////////
                ////// Contenido //////
                /*///////////////////*/
                Ext.create('Ext.panel.Panel', {
                    defaults :{
                        style : 'margin : 5px;'
                    },
                    renderTo : 'pan_usu_cob_divgrid',
                    border   : 0,
                    items    :
                    [{
                        layout: 'hbox',
                        items: [//{
                            Ext.create('Ext.grid.Panel',{
                                //xtype    : 'grid',
                                itemId   : 'grdIncisosp3',
                                columns  : columnasTatrisit,
                                width    : 910,
                                selModel : {
                                    selType   : 'checkboxmodel',
                                    mode      : 'SINGLE',
                                    listeners : {
                                        selectionchange : function(me, selected, eOpts) {
                                            debug('nmsituac seleccionado:', me.getSelection()[0].get('NMSITUAC'));
                                            
                                            //Filtramos el contenido de las coberturas de acuerdo al inciso elegido:
                                            storeCoberturasDisponibles_p3.clearFilter();
                                            storeCoberturasDisponibles_p3.filter("nmsituac", me.getSelection()[0].get('NMSITUAC'));
                                            
                                            storeCoberturasActuales_p3.clearFilter();
                                            storeCoberturasActuales_p3.filter("nmsituac", me.getSelection()[0].get('NMSITUAC'));
                                            
                                            storeCoberturasPorAgregar_p3.clearFilter();
                                            storeCoberturasPorAgregar_p3.filter("nmsituac", me.getSelection()[0].get('NMSITUAC'));
                                            
                                            storeCoberturasPorEliminar_p3.clearFilter();
                                            storeCoberturasPorEliminar_p3.filter("nmsituac", me.getSelection()[0].get('NMSITUAC'));
                                        }
                                    }
                                },
                                store : storeIncisos_p3
                            })
                        //}
                        ]
                    },{
                        layout   : {
                            type    : 'table',
                            columns : 2
                        },
                        items: [
                            Ext.create('Ext.grid.Panel', {
                                title          : 'Coberturas actuales'
                                ,icon          : '${ctx}/resources/fam3icons/icons/accept.png'
                                ,store         : storeCoberturasActuales_p3
                                ,buttonAlign   : 'center'
                                ,titleCollapse : true
                                ,collapsible   : false
                                ,height        : 250
                                ,width         : 370
                                ,tools         :
                                [
                                   {
                                       type     : 'help'
                                       ,tooltip : 'Coberturas que tiene actualmente el asegurado'
                                   }
                                ]
                                ,columns       :
                                [
                                    {
                                        header     : 'Cobertura'
                                        ,dataIndex : 'NOMBRE_GARANTIA'
                                        ,width     : 180
                                    }
                                    ,{
                                        header     : 'Suma asegurada'
                                        ,dataIndex : 'SUMA_ASEGURADA'
                                        ,width     : 110
                                    }
                                    ,{
                                        menuDisabled : true
                                        ,width       : 30
                                        ,dataIndex   : 'SWOBLIGA'
                                        ,renderer    : function(value)
                                        {
                                            var rvalue = '';
                                            if (value == 'N'&&inputAltabajap3=='baja')
                                            {
                                                rvalue = '<img src="${ctx}/resources/fam3icons/icons/delete.png" data-qtip="Eliminar" style="cursor:pointer;">';
                                            }
                                            return rvalue;
                                        }
                                    },{
                                        header     : 'No.'
                                        ,dataIndex : 'nmsituac'
                                        ,width     : 15
                                    }
                                ]
                                ,listeners :
                                {
                                    cellclick : function(grid, td, cellIndex, record)
                                    {
                                        debug('cellclick');
                                        if(cellIndex==2&&record.get('SWOBLIGA')=='N'&&inputAltabajap3=='baja')
                                        {
                                            storeCoberturasPorEliminar_p3.add(record);
                                            storeCoberturasActuales_p3.remove(record)
                                        }
                                    }
                                }
                            }),
                            Ext.create('Ext.grid.Panel', {
                                title          : 'Coberturas eliminadas'
                                ,icon          : '${ctx}/resources/fam3icons/icons/delete.png'
                                ,store         : storeCoberturasPorEliminar_p3
                                ,buttonAlign   : 'center'
                                ,hidden        : inputAltabajap3=='alta'
                                ,titleCollapse : true
                                ,collapsible   : false
                                ,tools         :
                                [
                                    {
                                        type     : 'help'
                                        ,tooltip : 'Coberturas que ten&iacute;a el asegurado y ser&aacute;n eliminadas'
                                    }
                                ]
                                ,height        : 250
                                ,width         : 370
                                ,columns       :
                                [
                                    {
                                        header     : 'Cobertura'
                                        ,dataIndex : 'NOMBRE_GARANTIA'
                                        ,width     : 180
                                    }
                                    ,{
                                        header     : 'Suma asegurada'
                                        ,dataIndex : 'SUMA_ASEGURADA'
                                        ,width     : 110
                                    }
                                    ,{
                                        menuDisabled : true
                                        ,width       : 30
                                        ,icon        : '${ctx}/resources/fam3icons/icons/cancel.png'
                                        ,renderer    : function(value)
                                        {
                                            return '<img src="${ctx}/resources/fam3icons/icons/control_rewind_blue.png" data-qtip="Volver a agregar" style="cursor:pointer;">';
                                        }
                                    },{
                                        header     : 'No.'
                                        ,dataIndex : 'nmsituac'
                                        ,width     : 15
                                    }
                                ]
                                ,listeners :
                                {
                                    cellclick : function(grid, td, cellIndex, record)
                                    {
                                        debug('cellclick');
                                        if(cellIndex==2)
                                        {
                                            storeCoberturasActuales_p3.add(record);
                                            storeCoberturasPorEliminar_p3.remove(record)
                                        }
                                    }
                                }
                            })
                            ,Ext.create('Ext.grid.Panel',
                            {
                                title          : 'Coberturas disponibles'
                                ,icon          : '${ctx}/resources/fam3icons/icons/zoom.png'
                                ,titleCollapse : true
                                ,collapsible   : false
                                ,hidden        : inputAltabajap3=='baja'
                                ,tools         :
                                [
                                   {
                                       type     : 'help'
                                       ,tooltip : 'Coberturas disponibles para agregar al asegurado'
                                   }
                                ]
                                ,store         : storeCoberturasDisponibles_p3
                                ,height        : 250
                                ,width         : 370
                                ,columns       :
                                [
                                    {
                                        header     : 'Cobertura'
                                        ,dataIndex : 'NOMBRE_GARANTIA'
                                        ,width     : 180
                                    }
                                    ,{
                                        header     : 'Suma asegurada'
                                        ,dataIndex : 'SUMA_ASEGURADA'
                                        ,width     : 110
                                    }
                                    ,{
                                        menuDisabled : true
                                        ,width       : 30
                                        ,renderer    : function(value)
                                        {
                                            return '<img src="${ctx}/resources/fam3icons/icons/add.png" data-qtip="Agregar" style="cursor:pointer;">';
                                        }
                                    },{
                                        header     : 'No.'
                                        ,dataIndex : 'nmsituac'
                                        ,width     : 15
                                    }
                                 ]
                                 ,listeners :
                                 {
                                     cellclick : function(grid, td, cellIndex, record)
                                     {
                                         debug('cellclick');
                                         debug('grid=', Ext.ComponentQuery.query('#grdIncisosp3'));
                                         debug('grid selModel=', Ext.ComponentQuery.query('#grdIncisosp3')[0].getSelectionModel());
                                         var hayIncisoSeleccionado = Ext.ComponentQuery.query('#grdIncisosp3')[0].getSelectionModel().hasSelection();
                                         var incisoSelected =        Ext.ComponentQuery.query('#grdIncisosp3')[0].getSelectionModel().getSelection()[0];
                                         debug('inciso seleccionado?', hayIncisoSeleccionado);
                                         debug('incisoSelected=', incisoSelected);
                                         debug('cellIndex=', cellIndex);
                                         if(cellIndex==2 && hayIncisoSeleccionado)
                                         {
                                             Ext.Ajax.request(
                                             {
                                                 url     : _endcob_urlObtenerComponenteSituacionCobertura
                                                 ,params :
                                                 {
                                                     'smap1.cdramo'    : inputCdramop3
                                                     ,'smap1.cdtipsit' : inputCdtipsitp3
                                                     ,'smap1.cdgarant' : record.get('GARANTIA')
                                                     ,'smap1.cdtipsup' : inputAltabajap3=='alta'?'6':'7'
                                                 }
                                                 ,success : function(response)
                                                 {
                                                     var json = Ext.decode(response.responseText);
                                                     debug('### obtener componente situacion cobertura:',json);
                                                     if(json.exito)
                                                     {
                                                         if(json.smap1.CONITEM=='true')
                                                         {
                                                             centrarVentanaInterna(
                                                                 Ext.create('Ext.window.Window',
                                                                 {
                                                                     title   : 'Valor de cobertura'
                                                                     ,modal  : true
                                                                     ,width  : 300
                                                                     ,height : 150
                                                                     ,items  :
                                                                     [
                                                                         Ext.decode(json.smap1.item)
                                                                     ]
                                                                     ,buttonAlign : 'center'
                                                                     ,buttons     :
                                                                     [
                                                                         {
                                                                             text     : 'Aceptar'
                                                                             ,icon    : '${ctx}/resources/fam3icons/icons/accept.png'
                                                                             ,handler : function(me)
                                                                             {
                                                                                 var item=me.up().up().items.items[0];
                                                                                 var valido = !Ext.isEmpty(item.getValue());
                                                                                 if(!valido)
                                                                                 {
                                                                                     datosIncompletos();
                                                                                 }
                                                                                 
                                                                                 if(valido)
                                                                                 {
                                                                                     storeCoberturasPorAgregar_p3.add(record);
                                                                                     storeCoberturasDisponibles_p3.remove(record);
                                                                                     record.set('cdatribu' , item.cdatribu);
                                                                                     record.set('otvalor'  , item.getValue());
                                                                                     debug('record:',record);
                                                                                     me.up().up().destroy();
                                                                                 }
                                                                             }
                                                                         }
                                                                     ]
                                                                 }).show()
                                                             );
                                                         }
                                                         else
                                                         {
                                                             storeCoberturasPorAgregar_p3.add(record);
                                                             storeCoberturasDisponibles_p3.remove(record);
                                                         }
                                                     }
                                                     else
                                                     {
                                                         mensajeError(json.respuesta);
                                                     }
                                                 }
                                                 ,failure : function()
                                                 {
                                                     errorComunicacion();
                                                 }
                                             });
                                         } else {
                                            mensajeWarning('Debe seleccionar un inciso para continuar');
                                         }
                                     }
                                 }
                            })
                            ,Ext.create('Ext.grid.Panel',
                            {
                                title          : 'Coberturas agregadas'
                                ,icon          : '${ctx}/resources/fam3icons/icons/add.png'
                                ,store         : storeCoberturasPorAgregar_p3
                                ,buttonAlign   : 'center'
                                ,colspan       : inputAltabajap3=='alta'?2:1
                                ,titleCollapse : true
                                ,collapsible   : false
                                ,hidden        : inputAltabajap3=='baja'
                                ,height        : 250
                                ,width         : 370
                                ,tools         :
                                [
                                   {
                                       type     : 'help'
                                       ,tooltip : 'Nuevas coberturas que se van a agregar al asegurado'
                                   }
                                ]
                                ,columns       :
                                [
                                    {
                                        header     : 'Cobertura'
                                        ,dataIndex : 'NOMBRE_GARANTIA'
                                        ,width     : 180
                                    }
                                    ,{
                                        header     : 'Suma asegurada'
                                        ,dataIndex : 'SUMA_ASEGURADA'
                                        ,width     : 110
                                    }
                                    ,{
                                        menuDisabled : true
                                        ,width       : 30
                                        ,renderer    : function(value)
                                        {
                                            return '<img src="${ctx}/resources/fam3icons/icons/delete.png" data-qtip="No agregar" style="cursor:pointer;">';
                                        }
                                    },
                                    {
                                        header     : 'No.'
                                        ,dataIndex : 'nmsituac'
                                        ,width     : 15
                                    }
                                ]
                                ,listeners :
                                {
                                    cellclick : function(grid, td, cellIndex, record)
                                    {
                                        debug('cellclick');
                                        if(cellIndex==2)
                                        {
                                            storeCoberturasDisponibles_p3.add(record);
                                            storeCoberturasPorAgregar_p3.remove(record);
                                        }
                                    }
                                }
                            })
                            ,Ext.create('Ext.form.Panel',
                            {
                                title        : 'Informaci&oacute;n del endoso'
                                ,heigth      : 200
                                ,buttonAlign : 'center'
                                ,style       : 'margin : 5px; margin-bottom : 200px;'
                                ,colspan     : 2
                                ,layout      :
                                {
                                    type     : 'table'
                                    ,columns : 2
                                }
                                ,defaults    :
                                {
                                    style : 'margin : 5px;'
                                }
                                ,items       :
                                [
                                    {
                                        xtype       : 'datefield'
                                        ,fieldLabel : 'Fecha de efecto'
                                        ,format     : 'd/m/Y'
                                        ,value      : new Date()
                                        ,allowBlank : false
                                        ,name       : 'pv_fecha_i'
                                    }
                                ]
                                ,buttons     :
                                [
                                    {
                                        text     : 'Guardar endoso'
                                        ,icon    : '${ctx}/resources/fam3icons/icons/disk.png'
                                        ,handler : function(me)
                                        {
                                            var form=me.up().up();
                                            endcobSumit(form,'no');
                                        }
                                    }
                                    ,{
                                        text     : 'Confirmar endoso'
                                        ,icon    : '${ctx}/resources/fam3icons/icons/key.png'
                                        ,handler : function(me)
                                        {
                                            var form=me.up().up();
                                            endcobSumit(form,'si');
                                        }
                                    }
                                    ,{
                                        text     : 'Documentos'
                                        ,icon    : '${ctx}/resources/fam3icons/icons/printer.png'
                                        ,handler : function()
                                        {
                                            Ext.create('Ext.window.Window',
                                            {
                                                title        : 'Documentos del tr&aacute;mite '+inputNtramitep3
                                                ,modal       : true
                                                ,buttonAlign : 'center'
                                                ,width       : 600
                                                ,height      : 400
                                                ,autoScroll  : true
                                                ,loader      :
                                                {
                                                    url       : endcobUrlDoc
                                                    ,params   :
                                                    {
                                                        'smap1.nmpoliza'  : inputNmpolizap3
                                                        ,'smap1.cdunieco' : inputCduniecop3
                                                        ,'smap1.cdramo'   : inputCdramop3
                                                        ,'smap1.estado'   : inputEstadop3
                                                        ,'smap1.nmsuplem' : '0'
                                                        ,'smap1.ntramite' : inputNtramitep3
                                                        ,'smap1.nmsolici' : ''
                                                        ,'smap1.tipomov'  : '0'
                                                    }
                                                    ,scripts  : true
                                                    ,autoLoad : true
                                                }
                                            }).show();
                                        }
                                    }
                                ]
                            })
                        ]
                    }]
                });
                /*///////////////////*/
                ////// Contenido //////
                ///////////////////////
            });
</script>
<div id="pan_usu_cob_divgrid"></div>