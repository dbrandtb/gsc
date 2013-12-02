Ext.require([ 'Ext.form.*', 'Ext.data.*', 'Ext.chart.*', 'Ext.grid.Panel','Ext.layout.container.Column', 'Ext.selection.CheckboxModel' ]);

Ext.onReady(function() {

    Ext.selection.CheckboxModel.override( {
        mode: 'SINGLE',
        allowDeselect: true
    });
    
    // Conversión para el tipo de moneda
    Ext.util.Format.thousandSeparator = ',';
    Ext.util.Format.decimalSeparator = '.';
    
    Ext.define('KeyValueModel', {
        extend: 'Ext.data.Model',
        fields : [{
            name : 'key',
            type : 'int'
        }, {
            name : 'value',
            type : 'string'
        }]
    });
    
    var storeTiposConsulta = Ext.create('Ext.data.JsonStore', {
        model: 'KeyValueModel',
        proxy: {
            type: 'ajax',
            url: _URL_TIPOS_CONSULTA,
            reader: {
                type: 'json',
                root: 'tiposConsulta'
            }
        }
    });
    storeTiposConsulta.load();

    var listViewOpcionesConsulta = Ext.create('Ext.grid.Panel', {
        collapsible:true,
        collapsed:true,
        store: storeTiposConsulta,
        multiSelect: false,
        hideHeaders:true,
        viewConfig: {
            emptyText: 'No hay tipos de consulta'
        },
        columns: [{
            flex: 1,
            dataIndex: 'value'
        }],
        listeners : {
            selectionchange : function(view, nodes) {
                tabDatosGeneralesPoliza.hide();
                tabPanelAgentes.hide();
            
                if (this.getSelectionModel().hasSelection()) {
                    var tipoConsultaSelected = this.getSelectionModel().getSelection()[0];
                    
                    if (gridSuplementos.getSelectionModel().hasSelection()) {
                    
                        switch (tipoConsultaSelected.get('key')) {
                            case 1: //Consulta de Datos generales
                                
                                //Mostrar session de datos generales:
                                tabDatosGeneralesPoliza.show();
                                
                                //Datos de Tarificación
                                storeDatosTarificacion.load({
                                    params: panelBusqueda.down('form').getForm().getValues(),
                                    callback: function(records, operation, success){
                                        if(!success){
                                            showMessage('Error', 'Error al obtener la tarificaci\u00F3n de la p\u00F3liza', 
                                                Ext.Msg.OK, Ext.Msg.ERROR)
                                        }              
                                    }
                                });                    
                                //Datos para asegurados
                                storeAsegurados.load({
                                    params: panelBusqueda.down('form').getForm().getValues(),
                                    callback: function(records, operation, success){
                                        if(!success){
                                            showMessage('Error', 'Error al obtener los datos del asegurado', Ext.Msg.OK, Ext.Msg.ERROR);
                                        }
                                    }
                                });
                                break;
                                
                                case 2: //Consulta de Agentes
                                    
                                    if (gridSuplementos.getSelectionModel().hasSelection()) {
                                        
                                        //Mostrar seccion de agentes:
                                        tabPanelAgentes.show();
                                    
                                        //console.log('Params busqueda de agente=');console.log(panelBusqueda.down('form').getForm().getValues());
                                        storeDatosAgente.load({
                                            params: panelBusqueda.down('form').getForm().getValues(),
                                            callback: function(records, operation, success) {
                                                if(success){
                                                    if(records.length > 0){
                                                        panelDatosAgente.getForm().loadRecord(records[0]);  
                                                    }else {
                                                        showMessage('Error', 'El Agente no existe, verifique la clave', Ext.Msg.OK, Ext.Msg.ERROR);
                                                    }
                                                }else {
                                                    showMessage('Error', 'Error al obtener los datos del agente, intente m\u00E1s tarde',
                                                    Ext.Msg.OK, Ext.Msg.ERROR);
                                                }
                                            }
                                        });
                                        
                                        // Obtenemos los recibos de los agentes:
                                        storeRecibosAgente.load({
                                            params: panelBusqueda.down('form').getForm().getValues(),
                                            callback: function(records, operation, success){
                                                if(success){
                                                    if(records.length > 0){
                                                        Ext.getCmp('montoTotalRecibos').setText(obtieneMontosRecibo(records));
                                                    }
                                                }else{
                                                    showMessage('Error', 'Error al obtener los recibos de dicho Agente', Ext.Msg.OK, Ext.Msg.ERROR);
                                                }                    
                                            }
                                        });
                                    }
                                    break;
                                case 3: //Consulta de Recibos
                                break;                    
                        }//end switch                
                    } else {
                        showMessage('Aviso', 'Debe seleccionar primero un hist\u00F3rico', Ext.Msg.OK, Ext.Msg.WARN)
                    }
                }
            }
        }
    });
    
    // Historico de movimientos (suplementos):
    Ext.define('SuplementoModel', {
        extend: 'Ext.data.Model',
        fields: [
            {name: 'cdramo'},
            {name: 'cdunieco'},
            {name: 'estado'},
            {name: 'nmpoliza'},
            {name: 'nmsuplem'},
            {name: 'dstipsup'},
            {name: 'feemisio', dateFormat: 'd/m/Y'},
            {name: 'feinival', dateFormat: 'd/m/Y'},
            {name: 'nlogisus'},
            {name: 'nsuplogi'},
            {name: 'ptpritot', type : 'float'}
        ],
    });
    
    
    var storeSuplementos = new Ext.data.Store({
        model: 'SuplementoModel',
        proxy: {
            type: 'ajax',
            url : _URL_CONSULTA_DATOS_SUPLEMENTO,
            reader: {
                type: 'json',
                root: 'datosSuplemento'
            }
        }
    });
    
    var gridSuplementos = Ext.create('Ext.grid.Panel', {
        id : 'suplemento-form',
        store : storeSuplementos,
        selType: 'checkboxmodel',
        autoScroll:true,
        defaults: {sortable : true, width:120, align : 'right'},
        columns : [{
            text : 'N&uacute;mero de endoso',
            dataIndex : 'nsuplogi',
            width:150
        }, {
            id : 'dstipsup',
            text : 'Tipo de endoso',
            dataIndex : 'dstipsup',
            width:200
        }, {
            text : 'Fecha de emisi\u00F3n',
            dataIndex : 'feemisio',
            format: 'd M Y',
            width:150
        }, {
            text : 'Fecha inicio vigencia',
            dataIndex : 'feinival',
            format: 'd M Y',
            width:150
        }, {
            text : 'Prima total',
            dataIndex : 'ptpritot',
            renderer : 'usMoney',
            width:150
        }],

        listeners : {
            selectionchange : function(model, records) {
                
                listViewOpcionesConsulta.up('panel').setTitle('');
                
                //Limpiar seleccion de la lista de opciones de consulta
                limpiaSeleccionTiposConsulta();
                
                //Limpiar la seccion de informacion principal:
                limpiaSeccionInformacionPrincipal();
                
                if(this.getSelectionModel().hasSelection()) {
                    
                    // Mostrar listado de tipos de consulta:
                    listViewOpcionesConsulta.up('panel').setTitle('Elije una consulta:');
                    listViewOpcionesConsulta.expand();
                    
                
                    //Lenar campos de formulario de busqueda:
                    var rowSelected = gridSuplementos.getSelectionModel().getSelection()[0];
                    panelBusqueda.down('form').getForm().findField("params.cdunieco").setValue(rowSelected.get('cdunieco'));
                    panelBusqueda.down('form').getForm().findField("params.cdramo").setValue(rowSelected.get('cdramo'));
                    panelBusqueda.down('form').getForm().findField("params.estado").setValue(rowSelected.get('estado'));
                    panelBusqueda.down('form').getForm().findField("params.nmpoliza").setValue(rowSelected.get('nmpoliza'));
                    panelBusqueda.down('form').getForm().findField("params.suplemento").setValue(rowSelected.get('nmsuplem'));
                    
                    //console.log('Params busqueda de datos grales poliza=');console.log(panelBusqueda.down('form').getForm().getValues());

                    // Consultar Datos Generales de la Poliza:
                    storeDatosPoliza.load({
                        params : panelBusqueda.down('form').getForm().getValues(),
                        callback : function(records, operation, success) {
                            if (success) {
                                if (records.length > 0) {
                                    // Se asigna valor al parametro de busqueda:
                                    panelBusqueda.down('form').getForm().findField("params.cdagente").setValue(records[0].get('cdagente'));
                                    
                                    // Se llenan los datos generales de la poliza elegida
                                    panelDatosPoliza.getForm().loadRecord(records[0]);
                                } else {
                                    showMessage('Aviso', 
                                        'No existen datos generales de la p\u00F3liza elegidaLa P&oacute;iza no existe, verifique sus datos', 
                                        Ext.Msg.OK, Ext.Msg.ERROR);
                                }
                            } else {
                                showMessage('Error', 
                                    'Error al obtener los datos generales de la p\u00F3liza elegida, intente m\u00E1s tarde',
                                    Ext.Msg.OK, Ext.Msg.ERROR);
                            }

                        }
                    });
                }
            }
        }
    });
    
    // Modelo
    Ext.define('DatosPolizaModel', {
        extend: 'Ext.data.Model',
        fields: [
            {type:'string', name:'nmsolici'},
            {type:'string', name:'titular'},
            {type:'string', name:'cdrfc'},
            {type:'date',   name:'feemisio', dateFormat: 'd/m/Y'},
            {type:'date',   name:'feefecto', dateFormat: 'd/m/Y'},
            {type:'date',   name:'feproren', dateFormat: 'd/m/Y'},
            {type:'string', name:'dstarifi'},
            {type:'string', name:'dsmoneda'},
            {type:'string', name:'nmcuadro'},
            {type:'string', name:'dsperpag'},
            {type:'string', name:'dstempot'},
            {type:'string', name:'nmpoliex'},
            {type:'string', name:'cdagente'},
            {type:'string', name:'statuspoliza'}
        ]
    });

    // Store
    var storeDatosPoliza = new Ext.data.Store({
        model: 'DatosPolizaModel',
        proxy: {
            type: 'ajax',
            url : _URL_CONSULTA_DATOS_POLIZA,
            reader: {
                type: 'json',
                root: 'datosPoliza'
            }
        }
    });
    
    // FORMULARIO DATOS DE LA POLIZA
    var panelDatosPoliza = Ext.create('Ext.form.Panel', {
        model : 'DatosPolizaModel',
        width : 820,
        border : false,
        //height : 280,
        defaults : {
            bodyPadding : 5,
            border : false
        },
        items : [ {
            layout : 'hbox',
            items : [
                {xtype: 'textfield', name: 'nmpoliex', fieldLabel: 'N&uacute;mero de P&oacute;liza', readOnly: true, labelWidth: 120},
                {xtype: 'textfield', id: 'nmsolici',  name: 'nmsolici', fieldLabel: 'N&uacute;mero de solicitud', width: 250, labelWidth: 150, readOnly: true}
            ]
        }, {
            layout : 'hbox',
            items : [ 
                {xtype:'textfield', name:'titular', fieldLabel: 'Nombre del titular', readOnly: true, labelWidth: 120, width: 400}, 
                {xtype:'textfield', name:'cdrfc', fieldLabel: 'RFC', readOnly: true, labelWidth: 120, labelAlign: 'right'}
            ]
        }, {
            layout : 'hbox',
            items : [ 
                {xtype: 'datefield', name: 'feemisio', fieldLabel: 'Fecha emisi&oacute;n',    format: 'd/m/Y', readOnly: true, labelWidth: 120, width: 220}, 
                {xtype: 'datefield', name: 'feefecto', fieldLabel: 'Fecha de efecto',         format: 'd/m/Y', readOnly: true, labelWidth: 120, width: 220, labelAlign: 'right'}, 
                {xtype: 'datefield', name: 'feproren', fieldLabel: 'Fecha renovaci&oacute;n', format: 'd/m/Y', readOnly: true, labelWidth: 120, width: 220, labelAlign: 'right'}
            ]
        }, {
            layout : 'hbox',
            items : {xtype: 'textfield', name: 'dstarifi', fieldLabel: 'Tipo de tarificaci&oacute;n', readOnly: true, labelWidth: 120}
        }, {
            layout : 'hbox',
            items : {xtype: 'textfield', name: 'dsmoneda', fieldLabel: 'C&oacute;digo de moneda', readOnly: true, labelWidth: 120}
        }, {
            layout : 'hbox',
            items : {xtype: 'textfield', name: 'nmcuadro', fieldLabel: 'Cuadro de comisiones', readOnly: true, labelWidth: 120}
        }, {
            layout : 'hbox',
            items : {xtype: 'textfield', name: 'dsperpag', fieldLabel: 'Periodicidad de pago', readOnly: true, labelWidth: 120}
        }, {
            layout : 'hbox',
            items : {xtype: 'textfield', name: 'dstempot', fieldLabel: 'Tipo de P&oacute;liza', readOnly: true, labelWidth: 120}
        }, {
            layout : 'hbox',
            items : {xtype: 'textfield', name: 'statuspoliza', fieldLabel: 'ESTATUS', readOnly: true, labelWidth: 120}
        }   
        ]
    });
    
    
    /**INFORMACION DEL GRID DE TARIFICACION**/
    //-------------------------------------------------------------------------------------------------------------    
    //Modelo
    Ext.define('DatosTarificacionModel',{
        extend: 'Ext.data.Model',
        fields: [
              {type:'string',    name:'dsgarant'      },
              {type:'float',    name:'montoComision' },
              {type:'float',    name:'montoPrima'    },
              {type:'string',    name:'sumaAsegurada' }
        ]
    });
    
    // Store
    var storeDatosTarificacion = new Ext.data.Store({
        model: 'DatosTarificacionModel',
        proxy: {
           type: 'ajax',
           url : _URL_CONSULTA_DATOS_TARIFA_POLIZA,
            reader: {
                type: 'json',
                root: 'datosTarifa'
            }
        }
    });
    /**GRID PARA LOS DATOS DE TARIFICACION **/
    var gridDatosTarificacion = Ext.create('Ext.grid.Panel', {
        width   : 820,
        //title   : 'DATOS TARIFICACI&Oacute;N',
        store   : storeDatosTarificacion,
        autoScroll:true,
        id      : 'gridDatosTarificacion',
        features:[{
                    ftype:'summary'
                }],
        columns: [
            {
                text            :'Garant&iacute;a',
                dataIndex       :'dsgarant',
                width           :250,
                summaryRenderer : function(value){return Ext.String.format('TOTALES');}
            },
            {
                text            :'Suma Asegurada',  
                dataIndex       :'sumaAsegurada',
                width           :170 , 
                align           :'right'  
                
            },
            {
                text            :'Monto de la Prima',
                dataIndex       :'montoPrima',
                width           : 170,
                align           :'right',        
                renderer        :Ext.util.Format.usMoney,
                summaryType     :'sum'
            },
            {
                text            : 'Monto de la Comisi&oacute;n',
                dataIndex       :'montoComision',
                width           : 170,
                renderer        :Ext.util.Format.usMoney,
                align           :'right',        
                summaryType     :'sum'
            }
        ]
    });
    gridDatosTarificacion.store.sort([
        { 
            property    : 'dsgarant',
            direction   : 'ASC'
        }
    ]);
    
    
    // Modelo
    Ext.define('AseguradosModel', {
        extend: 'Ext.data.Model',
        fields: [
            {type:'string', name:'cdperson'},
            {type:'string',    name:'cdrfc'},
            {type:'string',    name:'cdrol'},
            {type:'string',    name:'dsrol'},
            {type:'date',    name:'fenacimi', dateFormat: 'd/m/Y'},
            {type:'string',    name:'nmsituac'},
            {type:'string',    name:'sexo'},
            {type:'string',    name:'titular'},
            {type:'string',    name:'status'}
        ]
    });
    
    // Store
    var storeAsegurados = new Ext.data.Store({
     model: 'AseguradosModel',
     proxy:
     {
          type: 'ajax',
          url : _URL_CONSULTA_DATOS_ASEGURADO,
      reader:
      {
           type: 'json',
           root: 'datosAsegurados'
      }
     }
    });
    
    var gridDatosAsegurado = Ext.create('Ext.grid.Panel', {
        title   : 'DATOS DE LOS ASEGURADOS',
        store   : storeAsegurados,
        id      : 'gridDatosAsegurado',
        width   : 800,
        autoScroll:true,
        items:[{
           xtype:'textfield', name:'cdrfc', fieldLabel: 'RFC', readOnly: true, labelWidth: 120
        }],
        columns: [
            {text:'Rol',dataIndex:'dsrol',width:130 , align:'left'},
            {text:'Nombre',dataIndex:'titular',width:270,align:'left'},
            {text:'Estatus',dataIndex:'status',width:100,align:'left'},
            {text:'RFC',dataIndex:'cdrfc',width:120,align:'left'},
            {text:'Sexo',dataIndex:'sexo',width:80 , align:'left'},
            {text:'Fecha Nac.',dataIndex:'fenacimi',width:100, align:'left',renderer: Ext.util.Format.dateRenderer('d/m/Y')}
        ]
    });
    
    
    
    /**INFORMACION DEL GRID DE LA POLIZA DEL ASEGURADO**/
    //-------------------------------------------------------------------------------------------------------------    
    // Modelo
    Ext.define('PolizaAseguradoModel', {
        extend: 'Ext.data.Model',
        fields: [
            {type:'string', name:'cdramo'},
            {type:'string', name:'cdunieco'},
            {type:'string', name:'dsramo'},
            {type:'string', name:'dsunieco'},
            {type:'string', name:'estado'},
            {type:'string', name:'nmpoliex'},
            {type:'string', name:'nmpoliza'},
            {type:'string', name:'nombreAsegurado'}
        ]
    });
    
    var storePolizaAsegurado = Ext.create('Ext.data.Store', {
        pageSize : 10,
        autoLoad : true,
        model: 'PolizaAseguradoModel',
        proxy    : {
            enablePaging : true,
            reader       : 'json',
            type         : 'memory',
            data         : []
        }
    });
    
    
    // GRID PARA LOS DATOS DEL ASEGURADO
    var gridPolizasAsegurado= Ext.create('Ext.grid.Panel', {
        store : storePolizaAsegurado,
        selType: 'checkboxmodel',
        width : 650,
        bbar     :
        {
            displayInfo : true,
            store       : storePolizaAsegurado,
            xtype       : 'pagingtoolbar'
        },
        features:[{
           ftype:'summary'
        }],
        columns: [
            {text: 'P&oacute;liza', dataIndex: 'nmpoliex', width: 150},
            {text: 'Nombre del asegurado', dataIndex: 'nombreAsegurado', width: 200},
            {text: 'Producto', dataIndex: 'dsramo', width:200},
            {text: 'Estado', dataIndex: 'estado', width: 100}
            //{text: 'Compañía', dataIndex: 'dsunieco', width: 150},
            //,{text: '# poliza', dataIndex: 'nmpoliza', width: 70}
        ]
    });
    
    
    var windowPolizas= Ext.create('Ext.window.Window', {
        title : 'Elija una p&oacute;liza:',
        //height: 400,
        modal:true,
        width : 660,
        closeAction: 'hide',
        items:[gridPolizasAsegurado],
        buttons:[{
            text: 'Aceptar',
            handler: function(){
                if (gridPolizasAsegurado.getSelectionModel().hasSelection()) {
                    
                    //Asignar valores de la poliza seleccionada al formulario de busqueda
                    var rowPolizaSelected = gridPolizasAsegurado.getSelectionModel().getSelection()[0];
                    var formBusqueda = panelBusqueda.down('form').getForm();
                    formBusqueda.findField("params.nmpoliex").setValue(rowPolizaSelected.get('nmpoliex'));
                    
                    gridPolizasAsegurado.getStore().removeAll();
                    windowPolizas.close();
                    
                    // Recargar store con busqueda de historicos de la poliza seleccionada
                    cargaStoreSuplementos(formBusqueda.getValues());
                }else{
                    showMessage('Aviso', 'Seleccione un registro', Ext.Msg.OK, Ext.Msg.INFO);
                }
            }
        }]
    });
    
    
    var tabDatosGeneralesPoliza = Ext.create('Ext.tab.Panel', {
        width: 830,
        items: [{
            title : 'DATOS DE LA POLIZA',
            border:false,
            items:[{
                items: [panelDatosPoliza]
                   
            }]
        }, {
            title: 'DATOS TARIFICACION',
            itemId: 'tabDatosTarificacion',
            items:[{
                items: [gridDatosTarificacion]
                   
            }]
        }, {
            title: 'ASEGURADOS',
            itemId: 'tabDatosAsegurados',
            items:[{
                items:[gridDatosAsegurado]
            }]
        }, {
            id: 'tbDocumentos',
            title : 'DOCUMENTACION',
            width: '350',
            loader : {
                url : _URL_CONSULTA_DOCUMENTOS,
                scripts : true,
                autoLoad : false
            },
            listeners : {
                activate : function(tab) {
                    tab.loader.load({
                        params : {
                            'smap1.readOnly' :  true,
                            'smap1.nmpoliza' :  
                            panelBusqueda.down('form').getForm().findField("params.nmpoliza").getValue(),
                            'smap1.cdunieco' :  panelBusqueda.down('form').getForm().findField("params.cdunieco").getValue(),
                            'smap1.cdramo' :  panelBusqueda.down('form').getForm().findField("params.cdramo").getValue(),
                            'smap1.estado' :  panelBusqueda.down('form').getForm().findField("params.estado").getValue(),
                            'smap1.nmsuplem' :  panelBusqueda.down('form').getForm().findField("params.suplemento").getValue(),
                            'smap1.ntramite' : "",
                            'smap1.tipomov'  : 'Usuario'
                        }
                    });
                }
            }
        }]    
    });
    
    
    // DATOS DEL AGENTE:
    // Modelo
    Ext.define('DatosAgenteModel', {
        extend: 'Ext.data.Model',
        fields: [
            {type:'string',        name:'cdagente'      },
            {type:'string',        name:'cdideper'      },
            {type: 'date',      name: 'fedesde' , dateFormat: 'd/m/Y' },
            {type:'string',        name:'nombre'        }
        ]
    });
    // Store
    var storeDatosAgente = new Ext.data.Store({
        model: 'DatosAgenteModel',
        proxy: {
            type: 'ajax',
            url : _URL_CONSULTA_DATOS_AGENTE,
            reader:{
                type: 'json',
                root: 'datosAgente'
            }
        }
    });
    
    // Panel Info Agente
    var panelDatosAgente = Ext.create('Ext.form.Panel', {
        title   : 'INFORMACION DEL AGENTE',
        model   : 'DatosAgenteModel',
        //width      : 750,
        //height     : 100,
        border: false,
        defaults: {
            bodyPadding: 5
        },
        items:[
            {xtype:'textfield', name:'cdideper', fieldLabel: 'RFC', readOnly: true, labelWidth: 120},
            {xtype:'textfield', name:'cdagente', fieldLabel: 'C&oacute;digo', readOnly: true, labelWidth: 120},
            {xtype:'textfield', name:'nombre',   fieldLabel: 'Nombre', readOnly: true, labelWidth: 120, width: 400},
            {xtype: 'datefield',name:'fedesde',  fieldLabel: 'Fecha de ingreso', format: 'd/m/Y', readOnly: true, labelWidth: 120}
        ]
    });

    // INFORMACION DEL GRID DE CONSULTA DE RECIBOS DEL AGENTE:
    //Modelo
    Ext.define('RecibosAgenteModel',{
    extend: 'Ext.data.Model',
    fields: [
        {type:'string',    name:'dsgarant'},
        {type:'date',    name:'fefin',    dateFormat: 'd/m/Y'},
        {type:'date',    name:'feinicio', dateFormat: 'd/m/Y'},
        {type:'string',    name:'nmrecibo'},
        {type:'float',    name:'ptimport'}
    ]
    });

    //Store    
    var storeRecibosAgente = new Ext.data.Store({
        model: 'RecibosAgenteModel',
        groupField: 'dsgarant',
        proxy: {
            type: 'ajax',     
            url : _URL_CONSULTA_RECIBOS_AGENTE,
            reader: {
                type: 'json',
                root: 'recibosAgente'
            }
        }
    });
    
    
    var totalMontoRecibos = Ext.create('Ext.toolbar.Toolbar',{
        buttonAlign:'center',
        width   : 450,
        style:'color:white;',
        items:
        [
            {xtype: 'label', text: 'Monto Total '},
            '->',
            {id:'montoTotalRecibos', xtype: 'label' }
        ]
    });
    
        
    var consultaRecibosAgente = Ext.create('Ext.grid.Panel', {
        title   : 'Ver desglose de coberturas:',
        store   : storeRecibosAgente,
        autoScroll :true,
        isExpanded : true,
        collapsible: true,
        collapsed:true,
        width   : 450,
        height: 250,
        columns: [                   
            {header: 'N&uacute;mero de Recibo',dataIndex: 'nmrecibo', width:150},
            {header: 'Importe', dataIndex:'ptimport', width:250 , align:'right', renderer: Ext.util.Format.usMoney, summaryType: 'sum',summaryRenderer: function(value){ return Ext.String.format('Total     {0}',Ext.util.Format.usMoney( value)); }}
        ],
        features: [{ groupHeaderTpl: '{name} ({rows.length} Item{[values.rows.length > 1 ? "s" : ""]})', ftype:'groupingsummary', summaryType: 'sum', startCollapsed :true }]
    });
    
        
    var tabPanelAgentes = Ext.create('Ext.tab.Panel', {
        items: [{
            title : 'DATOS DEL AGENTE',
            border:false,
            items:[panelDatosAgente]
        }, {
            title: 'RECIBOS DEL AGENTE',
            items:[totalMontoRecibos, consultaRecibosAgente]
            
        }]
    });
    
    
    // SECCION DE INFORMACION GENERAL:
    
    var panelBusqueda = Ext.create('Ext.Panel', {
        id:'main-panel',
        baseCls:'x-plain',
        renderTo: Ext.getBody(),
        layout: {
            type: 'column',
            columns: 2
        },
        autoScroll:true,
        defaults: {frame:true, width:200, height: 200, margin : '2'},
        items:[{
            title:'BUSQUEDA DE POLIZAS',
            colspan:2,
            width:990,
            height:140,
            items: [
                {
                    xtype: 'form',
                    url: _URL_CONSULTA_DATOS_SUPLEMENTO,
                    border: false,
                    layout: {
                        type:'hbox'
                    },
                    margin: '5',
                    defaults: {
                        bubbleEvents: ['change']
                    },
                    items : [
                        {
                            xtype: 'radiogroup',
                            name: 'groupTipoBusqueda',
                            flex: 15,
                            columns: 1,
                            vertical: true,
                            items: [
                                {boxLabel: 'Por n\u00FAmero de p\u00F3liza', name: 'tipoBusqueda', inputValue: 1, checked: true, width: 160},
                                {boxLabel: 'Por RFC', name: 'tipoBusqueda', inputValue: 2},
                                {boxLabel: 'Por clave', name: 'tipoBusqueda', inputValue: 3},
                                {boxLabel: 'Por nombre', name: 'tipoBusqueda', inputValue: 4}
                                
                            ],
                            listeners : {
                                change : function(radiogroup, newValue, oldValue, eOpts) {
                                	Ext.getCmp('subpanelBusquedas').query('panel').forEach(function(c){c.hide();});
                                	//Ext.getCmp('subpanelBusquedas').query('textfield').forEach(function(c){c.setValue('');});
                                	this.up('form').getForm().findField('params.rfc').setValue('');
                                	this.up('form').getForm().findField('params.cdperson').setValue('');
                                	this.up('form').getForm().findField('params.nombre').setValue('');
                                	
                                    switch (newValue.tipoBusqueda) {
                                        case 1:
                                            Ext.getCmp('subpanelBusquedaGral').show();
                                            break;
                                        case 2:
                                            Ext.getCmp('subpanelBusquedaRFC').show();
                                            break;
                                        case 3:
                                            Ext.getCmp('subpanelBusquedaCodigoPersona').show();
                                            break;
                                        case 4:
                                            Ext.getCmp('subpanelBusquedaNombre').show();
                                            break;
                                    }
                                }
                            }
                        },
                        {
                            xtype: 'tbspacer',
                            flex: 2.5
                        },
                        {
                        	id: 'subpanelBusquedas',
                            layout : 'vbox',
                            align:'stretch',
                            flex: 70,
                            border: false,
                            items: [
                                {
                                    id: 'subpanelBusquedaGral',
                                    layout : 'hbox',
                                    align:'stretch',
                                    border: false,
                                    defaults: {
                                        enforceMaxLength: true,
                                        msgTarget: 'side'
                                    },
                                    items : [
                                        {
                                            // Numero de poliza externo
                                            xtype : 'textfield',
                                            name : 'params.nmpoliex',
                                            fieldLabel : 'N&uacute;mero de P&oacute;liza',
                                            labelWidth : 120,
                                            width: 300,
                                            maxLength : 30,
                                            allowBlank: false
                                        },{
                                            xtype : 'hiddenfield',
                                            name : 'params.cdunieco'
                                        },{
                                            xtype : 'hiddenfield',
                                            name : 'params.cdramo'
                                        },{
                                            xtype : 'hiddenfield',
                                            name : 'params.estado'
                                        },{
                                            xtype: 'hiddenfield',
                                            name : 'params.nmpoliza'
                                        },{
                                            xtype: 'hiddenfield',
                                            name : 'params.suplemento'
                                        },{
                                            xtype: 'hiddenfield',
                                            name : 'params.cdagente'
                                        }
                                    ]
                                },
                                {
                                    id: 'subpanelBusquedaRFC',
                                    layout : 'hbox',
                                    align:'stretch',
                                    border: false,
                                    hidden: true,
                                    defaults: {
                                        labelAlign: 'right',
                                        enforceMaxLength: true,
                                        msgTarget: 'side'
                                    },
                                    items : [
                                        {
                                            xtype: 'textfield',
                                            name : 'params.rfc',
                                            fieldLabel : 'RFC',
                                            maxLength : 13,
                                            allowBlank: false
                                        }
                                    ]
                                },
                                {
                                    id: 'subpanelBusquedaCodigoPersona',
                                    layout : 'hbox',
                                    align:'stretch',
                                    border: false,
                                    hidden: true,
                                    defaults: {
                                        labelAlign: 'right',
                                        enforceMaxLength: true,
                                        msgTarget: 'side'
                                    },
                                    items : [
                                        {
                                            xtype: 'numberfield',
                                            name : 'params.cdperson',
                                            fieldLabel : 'C\u00F3digo de persona',
                                            maxLength : 9,
                                            allowBlank: false
                                        }
                                    ]
                                },
                                {
                                    id: 'subpanelBusquedaNombre',
                                    layout : 'hbox',
                                    align:'stretch',
                                    border: false,
                                    hidden: true,
                                    defaults: {
                                        labelAlign: 'right',
                                        enforceMaxLength: true,
                                        msgTarget: 'side'
                                    },
                                    items : [
                                        {
                                            xtype: 'textfield',
                                            name : 'params.nombre',
                                            fieldLabel : 'Nombre',
                                            maxLength : 255,
                                            allowBlank: false
                                        }
                                    ]
                                }
                            ]
                        },
                        {
                            xtype:'tbspacer',
                            flex: 2.5
                        },
                        {
                            xtype : 'button',
                            flex:10,
                            text: 'Buscar',
                            handler: function () {
                                var formBusqueda = this.up('form').getForm();
                                //Obtenemos el valor elegido en 'groupTipoBusqueda' para elegir el tipo de busqueda a realizar.
                                switch (formBusqueda.findField('groupTipoBusqueda').getValue().tipoBusqueda) {
                                    case 1:
                                        // Busqueda por Datos Generales de la poliza:
                                        if(!formBusqueda.findField('params.nmpoliex').isValid()){
                                            showMessage('', _MSG_NMPOLIEX_INVALIDO, Ext.Msg.OK, Ext.Msg.INFO);
                                            return;
                                        }
                                        cargaStoreSuplementos(formBusqueda.getValues());
                                    break;
                                        
                                    case 2:
                                        // Busqueda de polizas por RFC:
                                        if(!formBusqueda.findField('params.rfc').isValid()){
                                            showMessage('', _MSG_RFC_INVALIDO, Ext.Msg.OK, Ext.Msg.INFO);
                                            return;
                                        } 
                                        cargaPolizasAsegurado(formBusqueda);
                                    break;
                                    
                                    case 3:
                                    	// Busqueda de polizas por CDPERSON:
                                        if(!formBusqueda.findField('params.cdperson').isValid()){
                                            showMessage('', _MSG_CDPERSON_INVALIDO, Ext.Msg.OK, Ext.Msg.INFO);
                                            return;
                                        }
                                        cargaPolizasAsegurado(formBusqueda);
                                        
                                    break;
                                    
                                    case 4:
                                    	// Busqueda de polizas por nombre:
                                        if(!formBusqueda.findField('params.nombre').isValid()){
                                            showMessage('', _MSG_NOMBRE_INVALIDO, Ext.Msg.OK, Ext.Msg.INFO);
                                            return;
                                        }
                                        cargaPolizasAsegurado(formBusqueda);
                                        
                                    break;
                                }
                            }
                        }
                    ]
                }
            ]
        },
        {
            title:'HISTORICO DE MOVIMIENTOS',
            width:990,
            height:150,
            colspan:2,
            items : [
                gridSuplementos
            ]
        },
        {
            //title:
            width: 130,
            height: 400,
            items: [
                listViewOpcionesConsulta
            ]
        },
        {
            //title:
            width:850,
            height:400,
            colspan:2,
            autoScroll:true,
            items : [
                tabDatosGeneralesPoliza,
                tabPanelAgentes
            ]
        }]
    });
    
    
    ////Hide elements
    if(tabDatosGeneralesPoliza.isVisible()) {
        tabDatosGeneralesPoliza.hide();
    }
    if(tabPanelAgentes.isVisible()) {
        tabPanelAgentes.hide();
    }
    

    /**
    * 
    * @param String/Object
    */
    function cargaStoreSuplementos(params){
        
        //console.log('Params busqueda de suplemento=');console.log(params);
        
        gridSuplementos.setLoading(true);
        storeSuplementos.load({
            params: params,
            callback: function(records, operation, success) {
                
                gridSuplementos.setLoading(false);
                gridSuplementos.getView().el.focus();
                
                //Limpiar seleccion de la lista de opciones de consulta
                limpiaSeleccionTiposConsulta();
                
                if (!success) {
                    showMessage(_MSG_ERROR, _MSG_BUSQUEDA_SIN_DATOS, Ext.Msg.OK, Ext.Msg.ERROR);
                    return;
                }
                if(records.length == 0){
                    showMessage(_MSG_SIN_DATOS, _MSG_BUSQUEDA_SIN_DATOS, Ext.Msg.OK, Ext.Msg.INFO);
                    return;
                }
            }
        });
        
    }

    // FUNCION PARA OBTENER RECIBOS DEL AGENTE
    function obtieneMontosRecibo(records) {
        var sum=0;
        for(var i=0;i<records.length;i++) {
            sum+=parseFloat(records[i].get("ptimport"));
        }
        return Ext.util.Format.usMoney(sum);
    }
    
    function limpiaSeccionInformacionPrincipal() {
        panelDatosPoliza.getForm().reset();
        gridDatosTarificacion.getStore().removeAll();
        gridDatosAsegurado.getStore().removeAll();
        tabDatosGeneralesPoliza.setActiveTab(0);
        tabPanelAgentes.setActiveTab(0);
        tabDatosGeneralesPoliza.hide();
        tabPanelAgentes.hide();
    }
    
    function limpiaSeleccionTiposConsulta() {
        listViewOpcionesConsulta.getSelectionModel().deselectAll();
        listViewOpcionesConsulta.collapse();
    }
    
    function cargaPolizasAsegurado(formBusqueda) {
    	var callbackGetPolizasAsegurado = function(options, success, response) {
            if(success){
                var jsonResponse = Ext.decode(response.responseText);
                if(jsonResponse.polizasAsegurado && jsonResponse.polizasAsegurado.length == 0) {
                    showMessage(_MSG_SIN_DATOS, _MSG_BUSQUEDA_SIN_DATOS, Ext.Msg.OK, Ext.Msg.INFO);
                    return;
                }
                storePolizaAsegurado.setProxy(
                {
                    type         : 'memory',
                    enablePaging : true,
                    reader       : 'json',
                    data         : jsonResponse.polizasAsegurado
                
                });
                windowPolizas.show();
            }else{
                showMessage('Error', 'Error al obtener las p&oacute;lizas, intente m\u00E1s tarde.', 
                    Ext.Msg.OK, Ext.Msg.ERROR);
            }
        }
        //console.log('Params busqueda de polizas por RFC=');console.log(formBusqueda.getValues());
        cargaStorePaginadoLocal(storePolizaAsegurado, 
            _URL_CONSULTA_POLIZAS_ASEGURADO, 
            'polizasAsegurado', 
            formBusqueda.getValues(), 
            callbackGetPolizasAsegurado);
    }
    
    
});