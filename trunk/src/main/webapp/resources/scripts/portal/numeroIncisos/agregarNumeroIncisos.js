// Funcion de Agregar Desglose de Polizas
agregar = function() {


      var dsAseguradora = new Ext.data.Store ({
						proxy: new Ext.data.HttpProxy({url: _ACTION_OBTENER_ASEGURADORAS_CLIENTE}),
						reader: new Ext.data.JsonReader({
								root: 'aseguradoraComboBox',
								id: 'cdUniEco',
								successProperty: '@success'
							}, [
								{name: 'cdUniEco', type: 'string', mapping: 'cdUniEco'},
								{name: 'dsUniEco', type: 'string', mapping: 'dsUniEco'} 
							]),
						remoteSort: true
				});


    var dsClientesCorp = new Ext.data.Store({
           proxy: new Ext.data.HttpProxy({
               url: _ACTION_OBTENER_CLIENTE_CORPO
           }),
           reader: new Ext.data.JsonReader({
           root: 'clientesCorp',
           id: 'clientesCorps'
           },[
           {name: 'cdElemento', type: 'string',mapping:'cdElemento'},
           {name: 'cdPerson', type: 'string',mapping:'cdPerson'},
           {name: 'dsElemen', type: 'string',mapping:'dsElemen'}
       ]),
       remoteSort: true
       });



    var dsProductos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_PRODUCTOS_ASEGURADORA_CLIENTE
            }),
            reader: new Ext.data.JsonReader({
            root: 'productosAseguradoraCliente',
            id: 'codigo'
            },[
           {name: 'codigo', type: 'string',mapping:'codigo'},
           {name: 'descripcion', type: 'string',mapping:'descripcion'}
        ]),
        remoteSort: true
    });
    
    
     var dsFormaCalculoFolio = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_FORMA_CALCULO_FOLIO
            }),
            reader: new Ext.data.JsonReader({
            root: 'formaCalculoFolioNroIncisos',
            id: 'id'
            },[
           {name: 'id', type: 'string',mapping:'id'},
           {name: 'texto', type: 'string',mapping:'texto'}
        ]),
        remoteSort: true
    });


   var dsIndicadorNumeracion = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_INDICADOR_NUMERACION
            }),
            reader: new Ext.data.JsonReader({
            root: 'indicadorNumeracionNroIncisos',
            id: 'id'
            },[
            {name: 'id', type: 'string',mapping:'id'},
            {name: 'texto', type: 'string',mapping:'texto'}
        ]),
        remoteSort: true
    });
    
    var dsIndicadorSP = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_COMBO_INDICADOR_SP
            }),
            reader: new Ext.data.JsonReader({
            root: 'indicador_SP_NroIncisos',
            id: 'id'
            },[
            {name: 'id', type: 'string',mapping:'id'},
            {name: 'texto', type: 'string',mapping:'texto'}
        ]),
        remoteSort: true
    });
    
    
     var dsTramos = new Ext.data.Store({
            proxy: new Ext.data.HttpProxy({
                url: _ACTION_OBTENER_TRAMO
            }),
            reader: new Ext.data.JsonReader({
            root: 'numeroIncisosManagerListLoadTramo',
            id: 'swSubInc',
            successProperty:"success"
            },[
            {name: 'swSubInc', type: 'string',mapping:'swSubInc'}
              ]),
        remoteSort: true
    });
    
 
//se define el formulario
var formPanel = new Ext.FormPanel ({
            labelWidth : 100,
            bodyStyle : 'padding:5px 5px 0',
            width : 350,
            defaults : {width : 200 },
            defaultType : 'textfield',
            store:dsTramos,
            //se definen los campos del formulario
            items : [
                {
                    xtype: 'hidden',
                    name : 'cdPerson',
                    id: 'cdPerson'
                },{
                    xtype: 'hidden',
                    name : 'swSubInc',
                    id: 'swSubInc'
                },
                {
                    xtype: 'combo',
                    labelWidth: 50,
                    tpl: '<tpl for="."><div ext:qtip="{cdElemento}.{cdPerson}.{dsElemen}" class="x-combo-list-item">{dsElemen}</div></tpl>',
                    store: dsClientesCorp,
                    displayField:'dsElemen',
                    valueField: 'cdElemento',
                    hiddenName: 'cdElemento',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: "Cliente",
                    forceSelection: true,
                    width: 200,
                    emptyText:'Seleccione Cliente ...',
                    selectOnFocus:true,
                    labelSeparator:'',
                    allowBlank : false,
                    id:'cdElementoId',
                    onSelect: function (record) {
                    	
                        formPanel.findById(('cdPerson')).setValue(record.get("cdPerson"));
                        formPanel.findById(('cdRamoId')).setValue('');
                        dsProductos.removeAll();
	                    dsProductos.load({
	                                    	params: {cdElemento: record.get("cdElemento") ,cdunieco: formPanel.findById(('cdUniEcoId')).getValue()},
	                         	            waitMsg: 'Espere por favor....'
	                            		 });
	                    formPanel.findById(('cdUniEcoId')).setValue('');
                        dsAseguradora.removeAll();
                        dsAseguradora.load({
	                                    	params: {cdElemento: record.get("cdElemento")},
	                         	            waitMsg: 'Espere por favor....'
	                            		 });
                        this.collapse();
                        this.setValue(record.get("cdElemento"));
                        }
                },
                {
                    xtype: 'combo', 
                    labelWidth: 70, 
                    tpl: '<tpl for="."><div ext:qtip="{cdUniEco}. {dsUniEco}" class="x-combo-list-item">{dsUniEco}</div></tpl>',
	                store: dsAseguradora,
	                displayField:'dsUniEco', 
	                valueField:'cdUniEco', 
	                hiddenName: 'cdUniEco', 
	                typeAhead: true,
	                mode: 'local', 
	                triggerAction: 'all', 
	                fieldLabel: "Aseguradora", 
	                forceSelection: true,
	                width: 200, 
	                emptyText:'Seleccione Aseguradora...',
	                selectOnFocus:true, 
	                labelSeparator:'', 
	                allowBlank : false,
	                id: 'cdUniEcoId',
	                onSelect: function (record) {
	                            				
	                            				dsProductos.removeAll();
	                            				dsProductos.load({
	                            						params: {cdElemento: formPanel.findById(('cdElementoId')).getValue() ,cdunieco: record.get('cdUniEco')}
	                            					});
	                            				formPanel.findById("cdRamoId").setValue('');
	                            				this.setValue(record.get('cdUniEco'));
	                            				this.collapse();	
	                            		        }
	             },

                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{codigo}. {descripcion}" class="x-combo-list-item">{descripcion}</div></tpl>',
                    store: dsProductos,
                    displayField:'descripcion',
                    valueField:'codigo',
                    hiddenName: 'cdRamo',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: "Producto",
                    forceSelection: true,
                    width: 300,
                    emptyText:'Seleccione Producto...',
                    selectOnFocus:true,
                    labelSeparator:'',
                    allowBlank : false,
                    id:'cdRamoId',
                    onSelect: function (record) {
	                            				this.setValue(record.get('codigo'));
	                            				 var mStore = dsTramos;
										        var o = {start: 0};
										        mStore.baseParams = mStore.baseParams || {};
										        mStore.baseParams['cdRamo'] = formPanel.findById("cdRamoId").getValue();
										        
										        mStore.reload(
										                  {
										                      params:{start:0,limit:20},
										                       
										                      callback : function(r,options,success) {
										                          if (success) {
										                                 if (mStore.getAt(0).get('swSubInc') == "N"){
						                            					 Ext.MessageBox.alert('Aviso','Producto No permite manejo de Sub-Incisos.');
						                            					 formPanel.findById("indSituacId").setValue('I');
						                            					 formPanel.findById("indSituacId").setDisabled(true);    
				                            				              }else{
				                            				              		formPanel.findById("indSituacId").setDisabled(false);
				                            				              }
										                           
										                          }else{
										                          		alert("fallo");
										                          }
										                      }
										
										                  }
										                );
	                            				
	                            				this.setValue(record.get('codigo'));
	                            				this.collapse();	
	                            		        }
	                            		        
                } ,
                 {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsIndicadorNumeracion,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'indSituac',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: "Indicador de Numeración",
                    forceSelection: true,
                    width: 300,
                    emptyText:'Seleccione Indicador de Numeración...',
                    selectOnFocus:true,
                    labelSeparator:'',
                    allowBlank : false,
                    id:'indSituacId'
                } ,
                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsIndicadorSP,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'indSufPre',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: "Indicador (S/P)",
                    forceSelection: true,
                    width: 300,
                    emptyText:'Seleccione Indicador (S/P)',
                    selectOnFocus:true,
                    labelSeparator:'',
                    id:'indSufPreId',
                    onSelect: function (record) {
	                            				
	                            				if (record.get("id")=="N"){
	                            				          
	                            							formPanel.findById("dsSufPre").setDisabled(true);
	                            							formPanel.findById("dsSufPre").allowBlank = true;
	                            							formPanel.findById("dsSufPre").setValue('');
	                            							
	                            				}
	                            				
	                            				if (record.get("id")=="S" || record.get("id")=="P"){
	                            				
	                            							formPanel.findById("dsSufPre").setDisabled(false);
	                            							formPanel.findById("dsSufPre").allowBlank = false;
	                            							
	                            				}
	                            				
	                            				this.setValue(record.get('id'));
	                            				this.collapse();
	                            				}
	                            					
                } ,
                
                {xtype: 'textfield', fieldLabel: "Valor", name: 'dsSufPre', id: 'dsSufPre'},
                
                {
                    xtype: 'combo',
                    tpl: '<tpl for="."><div ext:qtip="{id}. {texto}" class="x-combo-list-item">{texto}</div></tpl>',
                    store: dsFormaCalculoFolio,
                    displayField:'texto',
                    valueField:'id',
                    hiddenName: 'indCalc',
                    typeAhead: true,
                    mode: 'local',
                    triggerAction: 'all',
                    fieldLabel: "Forma Calculo Folio",
                    forceSelection: true,
                    width: 300,
                    emptyText:'Seleccione Forma Calculo Folio...',
                    selectOnFocus:true,
                    labelSeparator:'',
                    allowBlank : false,
                    id:'indCalcId',
                    onSelect: function (record) {
	                            				
	                            				if (record.get("id")=="1"){
	                            				
	                            				formPanel.findById("nmFolioIni").setDisabled(false);
	                            				
	                            							formPanel.findById("nmFolioIni").setDisabled(false);
	                            							formPanel.findById("nmFolioFin").setDisabled(false);
	                            								                            							
	                            							formPanel.findById("nmFolioIni").allowBlank = false;
	                            							formPanel.findById("nmFolioFin").allowBlank = false;
	                            							
	                            							formPanel.findById("dsCalculo").setValue('');
	                            							formPanel.findById("dsCalculo").allowBlank = true;
	                            							formPanel.findById("dsCalculo").setDisabled(true);
	                            							
	                            							
	                            							
	                            						   
	                            				}
	                            				
	                            				if (record.get("id")=="2"){
	                            				
	                            							
	                            						    formPanel.findById("nmFolioIni").allowBlank = true;
	                            							formPanel.findById("nmFolioFin").allowBlank = true;
	                            							
	                            							formPanel.findById("nmFolioIni").setValue('');
	                            							formPanel.findById("nmFolioFin").setValue('');
	                            							
	                            							formPanel.findById("nmFolioIni").setDisabled(true);
	                            							formPanel.findById("nmFolioFin").setDisabled(true);
	                            							
	                            							formPanel.findById("dsCalculo").allowBlank = false;
	                            							formPanel.findById("dsCalculo").setDisabled(false);
	                            				}
	                            				
	                            				
	                            				
	                            				this.setValue(record.get('id'));
	                            				this.collapse();	
	                            		        }
                } ,
                
                {xtype: 'textarea', fieldLabel: "Formula de Calculo de Folio", name: 'dsCalculo',heigth:150, id: 'dsCalculo'},
               
								
               {xtype: 'textfield', fieldLabel: "Folio Inicial", name: 'nmFolioIni', id: 'nmFolioIni',
				listeners: {
						    'change': function(){
						    	 validarFolios();
						        
						    }
						  }
				},
				
				{xtype: 'textfield', fieldLabel: "Folio Final", name: 'nmFolioFin', id: 'nmFolioFin',
				listeners: {
						    'change': function(){
						         validarFolios();
						    }
						  }
				}
				
			
            ]
        });


//Windows donde se van a visualizar la pantalla
var window = new Ext.Window({
        title: 'Agregar Numero de Inciso',
        width: 500,
        height:450,
        minWidth: 300,
        minHeight: 100,
        modal: true,
        layout: 'fit',
        plain:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: formPanel,
        //se definen los botones del formulario
            buttons : [ {
                text : 'Guardar',
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_INSERTAR_NUMERO_INCISO,
                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert('Aviso','Guardado satisfactoriamente');
                                window.close();
                                },
                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert('Error','Problemas al Guardar: ' + action.result.errorMessages[0]);
                                },
                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : 'Guardando Actualizacion de Datos ...'
                        });
                     } else {
                        Ext.Msg.alert('Informacion', 'Por favor complete la informacion requerida!');
                     }
                }
            },{
                text : 'Guardar y Agregar',
                disabled : false,
                handler : function() {
                    if (formPanel.form.isValid()) {
                        formPanel.form.submit( {
                            //action a invocar cuando el formulario haga submit
                            url : _ACTION_INSERTAR_NUMERO_INCISO,
                            //funcion a ejecutar cuando la llamada a la action retorna satisfactoriamente
                            success : function(from, action) {
                                Ext.MessageBox.alert('Aviso','Guardado satisfactoriamente');
                                    
                                    
                                    dsProductos.removeAll();
                                    dsAseguradora.removeAll();
                                    
                                    dsFormaCalculoFolio.removeAll();
								    dsIndicadorNumeracion.removeAll();
								    dsIndicadorSP.removeAll();
								    
                                    
                                    dsFormaCalculoFolio.load();
								    dsIndicadorNumeracion.load();
								    dsIndicadorSP.load();
								    
								    
								    
								    formPanel.findById("cdElementoId").setValue('');
								    formPanel.findById("cdUniEcoId").setValue('');
								    formPanel.findById("cdRamoId").setValue('');
								    formPanel.findById("indSituacId").setValue('');
								    formPanel.findById("indSufPreId").setValue('');      
								    formPanel.findById("indCalcId").setValue('');   
								    
								    formPanel.findById("indSituacId").setDisabled(false);  
								    formPanel.findById("dsCalculo").setDisabled(false);  
								    formPanel.findById("nmFolioIni").setDisabled(false);  
	                            	formPanel.findById("nmFolioFin").setDisabled(false);  
	                            	 
								    formPanel.findById("cdPerson").setValue('');
								    formPanel.findById("dsCalculo").setValue('');
								    formPanel.findById("dsSufPre").setValue('');
								    formPanel.findById("nmFolioIni").setValue('');
								    formPanel.findById("nmFolioFin").setValue('');      
                                },
                            //funcion a ejecutar cuando la llamada a la action no se ejecuta satisfactoriamente
                            failure : function(form, action) {
                                Ext.MessageBox.alert('Error','Problemas al Guardar: ' + action.result.errorMessages[0]);
                                },
                            //mensaje a mostrar mientras se guardan los datos
                            waitMsg : 'Guardando Actualizacion de Datos ...'
                        });
                     } else {
                        Ext.Msg.alert('Informacion', 'Por favor complete la informacion requerida!');
                     }
                }
            },
             {
                 text : 'Regresar',
                 handler : function() {
                 window.close();
                    }
            }]
    	});

   function validarFolios(){
   		 
   		if (formPanel.findById("nmFolioIni").getValue()!="" && formPanel.findById("nmFolioFin").getValue()!="") {
   			if (eval(formPanel.findById("nmFolioIni").getValue()) > eval(formPanel.findById("nmFolioFin").getValue())){
   			       Ext.Msg.alert('Informacion', 'El folio de Inicio debe ser menor que el folio de Fin.');
   			       var folFin = eval(formPanel.findById("nmFolioIni").getValue()) + 1;
   			       formPanel.findById("nmFolioFin").setValue(folFin);	
   			}
   		}
   }; 
   
    dsClientesCorp.load();
    dsFormaCalculoFolio.load();
    dsIndicadorNumeracion.load();
    dsIndicadorSP.load();

    window.show();

};

