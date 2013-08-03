
/* ********************************** JSONREADERS *************************** */
var elJson_GetNotifi= new Ext.data.JsonReader(
{
    root:'MEstructuraList',
    totalProperty: 'totalCount',
    successProperty : '@success'
},
[
    {name: 'cdNotificacion',  mapping:'cdNotificacion',  type: 'string'},
    {name: 'dsNotificacion',  mapping:'dsNotificacion',  type: 'string'},
    {name: 'cdRegion',  mapping:'cdRegion',  type: 'string'},
    {name: 'dsRegion',  mapping:'dsRegion',  type: 'string'},
    {name: 'cdProceso',  mapping:'cdProceso',  type: 'string'},
    {name: 'dsProceso',  mapping:'dsProceso',  type: 'string'},
    {name: 'cdEstado',  mapping:'cdEstado',  type: 'string'},
    {name: 'dsEstado',  mapping:'dsEstado',  type: 'string'},
    {name: 'cdMetEnv',  mapping:'cdMetEnv',  type: 'string'},
    {name: 'dsMensaje',  mapping:'dsMensaje',  type: 'string'},
    {name: 'dsMetEnv',  mapping:'dsMetEnv',  type: 'string'}
]
);

var elJson_CmbRegion = new Ext.data.JsonReader(
{
root: 'comboNotificacionRegion',
id: 'codigo'
},
[
{name: 'codigo', mapping:'cdRegion', type: 'string'},
{name: 'descripcion', mapping:'dsRegion', type: 'string'}
]
);

var elJson_CmbMetEnvFrm = new Ext.data.JsonReader(
{
    root: 'comboTipoMetodoEnvio',
    id: 'codigo'
},
[
    {name: 'codigo',mapping:'codigo', type: 'string'},
    {name: 'descripcion',mapping:'descripcion', type: 'string'}
]
);

/* ********************************** STORES *************************** */ 

//estore del get
var storeGetNotifi = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_OBTENER_NOTIFICACIONES,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
        }),
reader:elJson_GetNotifi
});

//store de la busqueda de la izquierda
var storeGetProceNotifi = new Ext.data.Store({
    url:_ACTION_BUSCAR_PROCESOS_NOTIFICACIONES,
    reader: new Ext.data.JsonReader({
    	id: 'cdProceso',
    	root: 'MEstructuraList',
    	totalProperty: 'totalCount',
    	successProperty: 'success'
    	},[
    	    {name: 'cdProceso',  type: 'string', mapping: 'cdProceso'},
    	    {name: 'dsProceso',  type: 'string', mapping: 'dsProceso'}
    	]
    )
});

//store que se llena junto con del get la derecha
var el_storeDer = new Ext.data.Store({
	url: _ACTION_BUSCAR_NOTIFICACIONES_PROCESOS,
	reader: new Ext.data.JsonReader({
		id: 'cdProceso',
		root: 'MEstructuraList',
    	totalProperty: 'totalCount',
    	successProperty: 'success'
    	},[
    	    {name: 'cdProceso',  type: 'string', mapping: 'cdProceso'},
    	    {name: 'dsProceso',  type: 'string', mapping: 'dsProceso'}
    	]
	)
});

//store de la busqueda de la izquierda
var storeGetEdosCaso = new Ext.data.Store({
    url:_ACTION_BUSCAR_ESTADOS_CASO,
    reader: new Ext.data.JsonReader({
    	id: 'cdProceso',
    	root: 'MEstructuraList',
    	totalProperty: 'totalCount',
    	successProperty: 'success'
    	},[
    	    {name: 'cdEstado',  type: 'string', mapping: 'cdEstado'},
    	    {name: 'dsEstado',  type: 'string', mapping: 'dsEstado'}
    	]
    )
});

//store que se llena junto con del get la derecha
var storeGetEdosNotifi = new Ext.data.Store({
	url: _ACTION_BUSCAR_ESTADOS_NOTIFICACIONES,
	reader: new Ext.data.JsonReader({
		id: 'cdEstado',
		root: 'MEstructuraList',
    	totalProperty: 'totalCount',
    	successProperty: 'success'
    	},[
    	    {name: 'cdEstado',  type: 'string', mapping: 'cdEstado'},
    	    {name: 'dsEstado',  type: 'string', mapping: 'dsEstado'}
    	]
	)
});
//store de las regiones
var dsRegion = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_OBTENER_NOTIFICACIONES_REGION,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
           }),
reader: elJson_CmbRegion
});

//store del combo metodo envio
var dsTipoMetodoEnvio = new Ext.data.Store({
proxy: new Ext.data.HttpProxy({
url: _ACTION_OBTENER_NOTIFICACIONES_TIPO_METODO_ENVIO,
        waitMsg : getLabelFromMap('400027', helpMap, 'Espere por favor....')
           }),
reader: elJson_CmbMetEnvFrm 
});




