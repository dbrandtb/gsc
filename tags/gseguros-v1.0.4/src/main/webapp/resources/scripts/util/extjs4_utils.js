/**
 * Funciones comunes
 * @author rbautista
 */


/**
 * Muestra un mensaje emergente
 * @param title   String
 * @param msg     String
 * @param buttons Object/Object[]
 * @param icon    String 
 */
function showMessage(title, msg, buttons, icon){
    Ext.Msg.show({
        title: title,
        msg: msg,
        buttons: buttons,
        icon: icon
    });
}

/**
** Funcion para implementar Paginacion,
** carga un Store y pagina sus datos de forma local.
** Se debe de tener un Store con un proxy 
** de la siguiente forma:

var store=Ext.create('Ext.data.Store',
    {
        pageSize : 10,
        autoLoad : true,
        model    : 'modelPersonalizado',
        proxy    :
        {
            enablePaging : true,
            reader       : 'json',
            type         : 'memory',
            data         : []
        }
    });

 * @param _store
 * @param _url
 * @param _root
 * @param _params
 * @param _callback
 */
function cargaStorePaginadoLocal(_store, _url, _root, _params, _callback, _grid) {
	
	if(_grid){
		_grid.setLoading(true);
		if(_grid.down('pagingtoolbar')){
			_grid.down('pagingtoolbar').moveFirst();
		}
	}
    Ext.Ajax.request(
    {
        url       : _url,
        params    : _params,
        callback  : _callback,
        success   : function(response)
        {
        	if(_grid){
        		_grid.setLoading(false);
        	}
        	_store.removeAll();
            var jsonResponse = Ext.decode(response.responseText);
            _store.setProxy({
                type         : 'memory',
                enablePaging : true,
                reader       : 'json',
                data         : jsonResponse[_root]
            });
            _store.load();
        },
        failure   : function()
        {
        	if(_grid){
        		_grid.setLoading(false);
        	}
        	_store.removeAll();
            Ext.Msg.show({
                title   : 'Error',
                icon    : Ext.Msg.ERROR,
                //msg     : 'Error cargando los datos de ' + _url,
                msg     : 'Error al obener los datos, intente m\u00E1s tarde',
                buttons : Ext.Msg.OK
            });
        }
    });
}