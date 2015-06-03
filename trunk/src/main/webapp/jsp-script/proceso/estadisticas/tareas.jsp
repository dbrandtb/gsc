<%@ include file="/taglibs.jsp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<script>
////// urls //////
var _p47_urlRecuperarTareas = '<s:url namespace="/estadisticas" action="recuperarTareas" />';
////// urls //////

////// variables //////
////// variables //////

////// overrides //////
////// overrides //////

////// componentes dinamicos //////
var _p47_formItems = [<s:property value="items.itemsFiltro" escapeHtml="false" />];
////// componentes dinamicos //////

Ext.onReady(function()
{   
    ////// modelos //////
    ////// modelos //////
    
    ////// stores //////
    ////// stores //////
    
    ////// componentes //////
    ////// componentes //////
    
    ////// contenido //////
    Ext.create('Ext.panel.Panel',
    {
        renderTo  : '_p47_divpri'
        ,itemId   : '_p47_panelpri'
        ,defaults : { style : 'margin:5px;' }
        ,border   : 0
        ,items    :
        [
            Ext.create('Ext.form.Panel',
            {
                itemId       : '_p47_form'
                ,title       : 'INDICADORES DE PROCESO'
                ,defaults    : { style : 'margin:5px;' }
                ,layout      :
                {
                    type     : 'table'
                    ,columns : 2
                }
                ,items       : _p47_formItems
                ,minHeight   : 100
                ,buttonAlign : 'center'
                ,buttons     :
                [
                    {
                        itemId   : '_p47_botonBuscar'
                        ,text    : 'Buscar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/zoom.png'
                        ,handler : _p47_buscar
                    }
                    ,{
                        itemId   : '_p47_botonLimpiar'
                        ,text    : 'Limpiar'
                        ,icon    : '${ctx}/resources/fam3icons/icons/arrow_refresh.png'
                        ,handler : function(me)
                        {
                            me.up('form').getForm().reset();
                        }
                    }
                ]
            })
        ]
    });
    ////// contenido //////
    
    ////// custom //////
    ////// custom //////
    
    ////// loaders //////
    ////// loaders //////
});

////// funciones //////
function _p47_buscar(me)
{
    var ck   = '';
    var form = me.up('form');
    try
    {
        ck = 'Validando datos';
        if(!form.isValid())
        {
            throw 'Favor de revisar los datos';
        }
        
        ck = 'Construyendo parametros';
        var values = form.getValues();
        var params = {};
        for(var i in values)
        {
            params['params.'+i]=values[i];
        }
        debug('params para buscar:',params);
        
        form.setLoading(true);
        Ext.Ajax.request(
        {
            url      : _p47_urlRecuperarTareas
            ,params  : params
            ,success : function(response)
            {
                form.setLoading(false);
                var ck = '';
                try
                {
                    ck = 'Decodificando respuesta';
                    var json = Ext.decode(response.responseText);
                    debug('### filtros:',json);
                    if(json.success)
                    {
                        alert('richi');
                    }
                    else
                    {
                        mensajeError(json.respuesta);
                    }
                }
                catch(e)
                {
                    manejaException(e,ck);
                }
            }
            ,failure : function()
            {
                form.setLoading(false);
                errorComunicacion(null,'Al buscar indicadores');
            }
        });
    }
    catch(e)
    {
        manejaException(e,ck,form);
    }
}
////// funciones //////
</script>
</head>
<body><div id="_p47_divpri" style="height:600px; border:1px solid #CCCCCC;"></div></body>
</html>