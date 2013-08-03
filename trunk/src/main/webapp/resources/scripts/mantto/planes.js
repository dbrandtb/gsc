Ext.onReady(function(){
Ext.QuickTips.init();
Ext.QuickTips.enable();
Ext.form.Field.prototype.msgTarget = "side";
 function test(){        		        				
 				url='mantto/planes.action'+'?dsPlanB='+nombreLista.getValue()+'&cdRamoB='+productoLista.getValue(); 			 		 			
 				store = new Ext.data.Store({
    				proxy: new Ext.data.HttpProxy({
						url: url
        			}),
        			reader: new Ext.data.JsonReader({
            			root:'planes',   
            			totalProperty: 'totalCount'         	
	        		},[
	        			{name: 'cdPlan',  type: 'string',  mapping:'cdPlan'},
	        			{name: 'dsPlan',  type: 'string',  mapping:'dsPlan'},
	        			{name: 'cdRamo',  type: 'string',  mapping:'cdRamo'},
	        			{name: 'dsRamo',  type: 'string',  mapping:'dsRamo'}	            
					]),
					remoteSort: true
    			});
    			store.setDefaultSort('totales', 'desc');
    			store.load();
				return store;
		}
		var ds = new Ext.data.Store({
        	proxy: new Ext.data.HttpProxy({
				url: 'mantto/ProductosLista.action'
        	}),
        	reader: new Ext.data.JsonReader({
            	root: 'productos',
            	id: 'listas'
	        },[
           		{name: 'cdRamoP',  	type: 'string',  mapping:'cdRamoP'},
           		{name: 'dsRamo',   	type: 'string',  mapping:'dsRamo'}    
        	]),
			remoteSort: true
    	});
		ds.setDefaultSort('listas', 'desc');

/***************************************
*Se crea la ventana de agregar planes.	
****************************************/
	agregar = function() {
		var nombrePlan = new Ext.form.TextField({
        	fieldLabel: 'Nombre',
        	allowBlank: false,
        	name: 'dsPlanLista',
        	width: 300 
    	});
    	var formPanel = new Ext.form.FormPanel({
  		url:'mantto/InsertaProductos.action',
        baseCls: 'x-plain',
        labelWidth: 75,
        defaultType: 'textfield',
        items: [            
            new Ext.form.ComboBox({
            	tpl: '<tpl for="."><div ext:qtip="{dsRamo}. {cdRamoP}" class="x-combo-list-item">{dsRamo}</div></tpl>',
    			store: ds,
				width: 300,
    			mode: 'local',
				name: 'descripcionRamo',
    			typeAhead: true,
				labelSeparator:'',			
    			triggerAction: 'all',    		
    			displayField:'dsRamo',
				fieldLabel: "Producto*:",
				emptyText:'Seleccionar producto...',
				selectOnFocus:true
			}),
			nombrePlan           
    		]
    	});

    	var window = new Ext.Window({
        	title: 'Agregar Planes',
        	plain:true,
        	width: 500,
        	height:150,
        	minWidth: 300,
        	minHeight: 100,
        	layout: 'fit',
        	bodyStyle:'padding:5px;',
        	buttonAlign:'center',
        	modal:true,
        	items: formPanel,
        	buttons: [{
            	text: 'Guardar', 
            	handler: function() {                
                	if (formPanel.form.isValid()) {
	 		        	formPanel.form.submit({	
	 		        		waitTitle:'Espere',
			            	waitMsg:'Procesando...',
			            	failure: function(form, action) {
						    		Ext.MessageBox.alert('Error','Error agregando un plan');
						    		window.hide();						    
							},
							success: function(form, action) {
						    		Ext.MessageBox.alert('Estado','Plan Creado Exitosamente');
						    		window.hide();
						    		grid2.destroy();
									createGrid();
									store.load();
							}
			        	});                   
                	}else{
						Ext.MessageBox.alert('Error', 'Porfavor revise los errores!');
						}             
	        		}
        		},{
            		text: 'Regresar',
            		handler: function(){
            			window.hide();
            		}
        		}]
    		});
			window.show();
	};

 /*************************************
*Crea la ventana de editar
 **************************************/
editarMantto= function(){	
	var dsPlan = new Ext.form.TextField({
        fieldLabel: 'Nombre',
        allowBlank: false,
        name:'dsPlan',
      	anchor:'90%'	
    });            
                
    var cdRamo = new Ext.form.NumberField({
        fieldLabel: '',
        allowBlank: false,   
        name:'cdRamo',
        anchor:'90%',
        hiddeParent:true,
      	labelSeparator:'',
      	hidden:true
    });
    
    var dsRamo = new Ext.form.TextField({
        fieldLabel: "Producto",
        allowBlank: false,   
        name:'dsRamo',
        anchor:'90%',
        disabled:true
                      
    });
    
    var cdPlan = new Ext.form.TextField({
        fieldLabel: '',
        allowBlank: false,
        name:'cdPlan',
      	anchor:'90%',
      	hiddeParent:true,
      	labelSeparator:'',
      	hidden:true    	
    });
    
    var editForm= new Ext.FormPanel({
    	id:'editForm',
    	labelWidth: 75,
    	baseCls: 'x-plain',
    	url:'mantto/EditaProductos.action',
    	items:[dsRamo, dsPlan, cdRamo, cdPlan ]
    });
    
    var window2 = new Ext.Window({
        title: 'Editar Planes',
        minHeight: 100,
        minWidth: 300,
        width: 500,
        height:160,
        layout: 'fit',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: editForm,
        buttons: [{
            		text: 'Guardar', 
            		handler: function() {
            			if (editForm.form.isValid()) {
	 		        			editForm.form.submit({
	 		        				waitTitle:'Espere',
			            			waitMsg:'Procesando...',
			            			failure: function(form, action) {
						    			Ext.MessageBox.alert('Estado','Error en la Edicion ');
						    			window2.close();
									},
									success: function(form, action) {
						    			Ext.MessageBox.alert('Estado','Plan Editado Exitosamente');
						    			window2.close();
						    			grid2.destroy();
										createGrid();
										store.load();							    	   
									}
			        			});                   
                		}else{
							Ext.MessageBox.alert('Error', 'Porfavor revise los errores!');
						}             
	        		}
        		},{
            		text: 'Regresar',
            		handler: function(){
            			window2.close();
            		}
        		}]
    	});
  window2.show();  	
};
/***se crea la ventana de copiar**/
	var msg = new Ext.form.TextField({
				fieldLabel:'�Esta seguro que desea Copiarlo?',
				labelSeparator:'',
				hidden:true
	});

	var dsPlan = new Ext.form.TextField({
        fieldLabel: '',
        name:'dsPlan',
      	anchor:'90%',
      	hiddeParent:true,
      	labelSeparator:'',
      	hidden:true    	
    });                        
    
    var cdRamo = new Ext.form.NumberField({
        fieldLabel: '',   
        name:'cdRamo',
        anchor:'90%',
        hiddeParent:true,
      	labelSeparator:'',
      	hidden:true
    });  	

	var copiarForm= new Ext.FormPanel({
    	id:'copiarForm',
    	labelAlign:'top',
    	baseCls: 'x-plain',
    	anchor:'100%',
    	url:'mantto/copiaPlan.action',
    	items:[	msg, dsPlan, cdRamo]
    });
	var windowCopy = new Ext.Window({
        title: 'Copiar Plan',
        minHeight: 40,
        minWidth: 220,
        width: 240,
        height:100,
        layout: 'fit',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: copiarForm,
        buttons: [{
            	text: 'Copiar', 
            	handler: function() {
            		if (copiarForm.form.isValid()) {
	 		        		copiarForm.form.submit({
	 		        			waitTitle:'Espere',
			            		waitMsg:'Procesando...',
			            		failure: function(form, action) {
						    		Ext.MessageBox.alert('Estado', 'Error Copiando Plan' );
						    		windowCopy.hide();
								},
								success: function(form, action) {
						    		Ext.MessageBox.alert('Estado', 'Plan Copiado Exitosamente');
						    		windowCopy.hide();
						    		grid2.destroy();
									createGrid();
									store.load();							    	   
								}
			        		});                   
                	} else{
						Ext.MessageBox.alert('Error', 'Verifique!');
					}             
	        	}
        	},{
            	text: 'Cancelar',
            	handler: function(){windowCopy.hide();}
        	}]
    	});
/*****se crea la ventana de borrar un plan***/

	var msgBorrar = new Ext.form.TextField({
				fieldLabel:'�Esta seguro que desea Eliminar el plan?',
				labelSeparator:'',
				hidden:true
	});                   
   
    var cdRamo = new Ext.form.NumberField({
        fieldLabel: '',   
        name:'cdRamo',
        anchor:'90%',
        hiddeParent:true,
      	labelSeparator:'',
      	hidden:true
    });  	
	var cdPlan = new Ext.form.TextField({
        fieldLabel: '',
        allowBlank: false,
        name:'cdPlan',
      	anchor:'90%',
      	hiddeParent:true,
      	labelSeparator:'',
      	hidden:true    	
    });
	var borrarForm= new Ext.FormPanel({
    	id:'borrarForm',
    	labelAlign:'top',
    	baseCls: 'x-plain',
    	anchor:'100%',
    	url:'mantto/borraPlan.action',
    	items:[	msgBorrar, cdRamo, cdPlan]
    });

	var windowDel = new Ext.Window({
        title: 'Eliminar Plan',
        minHeight: 50,
        minWidth: 250,
        width: 250,
        height:100,
        layout: 'fit',
        plain:true,
        modal:true,
        bodyStyle:'padding:5px;',
        buttonAlign:'center',
        items: borrarForm,
        buttons: [{
            	text: 'Eliminar', 
            	handler: function() {
            		if (borrarForm.form.isValid()) {
	 		        		borrarForm.form.submit({
	 		        			waitTitle:'Espere',
			            		waitMsg:'Procesando...',
			            		failure: function(form, action) {
						    		Ext.MessageBox.alert('Estado', 'Error Eliminando Plan ');
						    		windowDel.hide();
								},
								success: function(form, action) {
						    		Ext.MessageBox.alert('Estado', 'Plan Eliminado');
						    		windowDel.hide();
						    		grid2.destroy();
									createGrid();
									store.load();							    	   
								}
			        		});                   
                	}else{
						Ext.MessageBox.alert('Error', 'Verifique!');
					}             
	        	}
        	},{
            	text: 'Cancelar',
            	handler: function(){windowDel.hide();}
        	}]
    	});
/*****/

/******************
*Form de busqueda para el grid. 
*******************/   

    var productoLista = new Ext.form.TextField({
        fieldLabel: 'Producto',
        name:'cdRamoB',
        width: 200
    });
    var nombreLista = new Ext.form.TextField({
        fieldLabel: 'Nombre',
        name:'dsPlanB',
        width: 200
    });    

    
    var incisosForm = new Ext.form.FormPanel({       		
    	el:'formPlanes',
		title: '<span style="color:black;font-size:14px;">Mantenimiento de Planes</span>',
        iconCls:'logo',
        bodyStyle:'background: white',
        buttonAlign: "center",
        labelAlign: 'right',   
        frame:true,   
        url:_ACTION_MANTTO,            	           	
        width: 500,
        height:150,
        items: [{
        		layout:'form',
				border: false,
				items:[{
        			title :'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">B�squeda</span>',
                	bodyStyle:'background: white',
		        	labelWidth: 50,            	
                	layout: 'form',
					frame:true,
		       		baseCls: '',
		       		buttonAlign: "center",
        		        items: [{
        		        		layout:'column',
		 				    	border:false,
		 				    	columnWidth: 1,
        		    			items:[{
						    			columnWidth:.6,
                						layout: 'form',                		
		                				border: false,                		
        		        				items:[	
        		        						productoLista,
        		        						nombreLista        		        				        		        					       		                  			    		
		       							]
								},{
									columnWidth:.4,
                					layout: 'form'
                				}]
                		}],
                		buttons:[{        							
        						text:'Buscar',	
        						handler: function() {		                					 
				               			if (incisosForm.form.isValid()) {
						 			    	incisosForm.form.submit({
						 			    			waitTitle:'Espere',
				        		    				waitMsg:'Procesando...',
							            			failure: function(form, action) {
											    		Ext.MessageBox.alert('Estado','No se encontraron registros');
													},
													success: function(form, action) {
											    		grid2.destroy();
											    		createGrid();
											    		store.load();						    
													}
									        	});                   
                							}else{
												Ext.MessageBox.alert('Error', 'Inserte Parametro de B�squeda!');
											}  
										}	       							        			
        						},{
        							text:'Cancelar',
        							handler: function() {
        								incisosForm.form.reset();
        							}
        						}]
        				}]
        		}]	
		});
		incisosForm.render();
		
	function toggleDetails(btn, pressed){
       		var view = grid.getView();
       		view.showPreview = pressed;
       		view.refresh();
    	}
		var store;
		var cm = new Ext.grid.ColumnModel([
		    new Ext.grid.RowNumberer(),
			{header: "Clave", 	   dataIndex:'cdPlan',	width: 120, sortable:true, 	locked:false,	id:'cdPlan', hidden:true},		    
		    {header: "Producto",   dataIndex:'cdRamo',	width: 120, sortable:true, hidden:true},
		    {header: "Descripci�n",dataIndex:'dsRamo', 	width: 120, sortable:true},
		    {header: "Plan",	   dataIndex:'dsPlan',	width: 120, sortable:true}
		   	                	
        ]);
        var grid2;
        var selectedId;
          var codigoPlan;
        var codigoProducto;
        var descripcionProducto;
        var descripcionPlan;
/*********************
*Se crea el grid de Planes
***********************/        
        function getSelectedRecord(){
             var m = grid2.getSelections();
             if (m.length == 1 ) {
                return m[0];
             }
        }
function createGrid(){
	grid2= new Ext.grid.GridPanel({
		store:test(),
		border:true,
		baseCls:' background:white ',
		cm: cm,
		buttons:[
        		{text:'Agregar',
            	tooltip:'Agregar un nuevo plan',
            	handler:function(){
            	agregar(ds);}
            	},{
            	id:'editar',
            	text:'Editar',
            	tooltip:'Edita plan seleccionado',
            	handler:function(){
                	if (getSelectedRecord() != null) {
                    	editarMantto();                                	
                        Ext.getCmp('editForm').getForm().loadRecord(getSelectedRecord());   
	                } else {
    	                Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operacion');
        	        }
                }
            	},{
            	text:'Copiar',
            	id:'copiar',
            	tooltip:'Copia plan seleccionado',
            	handler:function(){
                	if (getSelectedRecord() != null) {
                    	windowCopy.show();
                        Ext.getCmp('copiarForm').getForm().loadRecord(getSelectedRecord());
	                } else {
    	                Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operacion');
        	        }
                }
            	},{
            	text:'Configurar',
            	id:'configurarPlan',
            	tooltip:'Configura plan seleccionado',
            	handler: function(){
            		if(codigoProducto != null){
            		alert("SIGUIENTE JSP");
            		}else{
            			Ext.Msg.alert('� Aviso !', 'Debe seleccionar un Plan ');
            		}
            	}
            	},{
            	text:'Eliminar',
            	id:'borrar',
            	tooltip:'Elimina plan seleccionado',
            	handler:function(){
                	if (getSelectedRecord() != null) {
                    	windowDel.show();
                        Ext.getCmp('borrarForm').getForm().loadRecord(getSelectedRecord());
	                } else {
    	                Ext.MessageBox.alert('Aviso','Debe seleccionar un registro para realizar esta operacion');
        	        }
                }
            	},{
            	text:'Exportar',
            	tooltip:'Exporta planes',
            	handler:exportButton(_ACTION_EXPORT_PLAN)
            	}],      							        	    	    
    	width:500,
    	frame:true,
		height:320,        
		title:'<span style="color:#98012e;font-size:14px;font-family:Arial,Helvetica,sans-serif;">Listado</span>',
		//renderTo:document.body,
		
		sm: new Ext.grid.RowSelectionModel({
			singleSelect: true,
			listeners: {                            
                        rowselect: function(sm, row, rec) {                                                                                                                                     
                                
                                Ext.getCmp('configurarPlan').on('click', function(){
                                	rec =store.getAt(row);
                                	codigoPlan = rec.get('cdPlan');
                                	codigoProducto = rec.get('cdRamo');
                                	descripcionProducto = rec.get('dsRamo');
                                	descripcionPlan = rec.get('dsPlan');
                                
                                });
                        }
                }
		
		}),
		viewConfig: {autoFill: true,forceFit:true},                
		bbar: new Ext.PagingToolbar({
			pageSize:25,
			store: store,									            
			displayInfo: true,
			displayMsg: 'Mostrando registros {0} - {1} de {2}',
			emptyMsg: "No hay resultados que mostrar"      		    	
			})        							        				        							 
		}); 	
	grid2.render('gridPlanes');
	}
	createGrid();
	ds.load();
});
