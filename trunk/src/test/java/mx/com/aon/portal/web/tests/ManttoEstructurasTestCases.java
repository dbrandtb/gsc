package mx.com.aon.portal.web.tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import mx.com.aon.portal.model.EstructuraVO;
import mx.com.aon.portal.service.EstructuraManager;
import mx.com.aon.portal.service.PagedList;
import mx.com.aon.portal.util.WrapperResultados;

public class ManttoEstructurasTestCases extends AbstractTestCases {


	protected EstructuraManager estructuraManager;
	
	
	//*****************************************************************************
    //
	/*
    @SuppressWarnings("unchecked")
	public void testGetEstructura() throws Exception {
    	try {
    		PagedList listaEstructuras = new PagedList(); 
    		listaEstructuras = estructuraManager.getEstructura("5");
            List listaResultado = listaEstructuras.getItemsRangeList();
            for (int i = 0; i < listaResultado.size(); i++) {
                EstructuraVO o =  (EstructuraVO)listaResultado.get(i);
                System.out.println("codigo "+ o.getCodigo());
                System.out.println("descripcion "+ o.getDescripcion());
            }
    		logger.info("Total Items: " + listaEstructuras.getTotalItems());
    	} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
	*/
	
	//*****************************************************************************
    //FUNCIONA CORRECTAMENTE, PERO FALTA LA DEVOLUCI�N DE LOS DOS PARAMETROS DE RETORNO
    @SuppressWarnings("unchecked")
	public void testObtieneEstructuras() throws Exception {
    	try {
    		PagedList pagedList  =  estructuraManager.buscarEstructuras(0, 10, "Lista");
            List listaResultado = pagedList.getItemsRangeList();
            for (int i = 0; i < listaResultado.size(); i++) {
                EstructuraVO o =  (EstructuraVO)listaResultado.get(i);
                System.out.println("codigo "+ o.getCodigo());
                System.out.println("descripcion "+ o.getDescripcion());

            }
    		logger.info("Total Items: " + pagedList.getTotalItems());
    	} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }

  //*****************************************************************************
    //
	
    @SuppressWarnings("unchecked")
	public void testGetEstructura() throws Exception {
    	try {
    		EstructuraVO estructuraVO =  estructuraManager.getEstructura("123455");
            
            logger.info("C�digo: "+estructuraVO.getCodigo()+ " Descripci�n: " + estructuraVO.getDescripcion());
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
    
    
	//*****************************************************************************
	//FUNCIONA CORRECTAMENTE BAJO 10 PRUEBAS
	/*
    public void testGuardarEstructura() throws Exception {
    	try {
    		EstructuraVO estructuraVO = new EstructuraVO();
    		estructuraVO.setCodigo("4");
    		estructuraVO.setDescripcion("Lista corporativa4 JUE 17 DE ABR");
    		WrapperResultados wrapperResultados = estructuraManager.saveOrUpdateEstructura(estructuraVO, "GUARDA_ESTRUCT");
			if(wrapperResultados.getMsg().equals("1")){
    			logger.info("--->"+wrapperResultados.getMsgId() + " ERROR: " + wrapperResultados.getMsg()+ " PROBLEMA!! NO SE ACTUALIZ� EL REGISTRO ");
    		}else{	
    			logger.info("--->"+wrapperResultados.getMsgId() + " EXITO: "+wrapperResultados.getMsg()+" SE ACTUALIZ� EL REGISTRO ");
    		}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
    */
	
	//*****************************************************************************
	//FUNCIONA CORRECTAMENTE BAJO 20 PRUEBAS
    /*
    public void testInsertaEstructura() throws Exception {
    	try {
    		EstructuraVO estructuraVO = new EstructuraVO();
    		estructuraVO.setDescripcion("Lista corporativa 10000");
    		WrapperResultados wrapperResultados =  estructuraManager.saveOrUpdateEstructura(estructuraVO, "INSERTA_ESTRUCT");
    		if(wrapperResultados.getMsg().equals("1")){
    			logger.info("--->"+wrapperResultados.getMsgId() + " ERROR: " + wrapperResultados.getMsg()+ " PROBLEMA!! DESCRIPCI�N DUPLICADA. NO SE INSERTO EL REGISTRO ");
    		}else{	
    			logger.info("--->"+wrapperResultados.getMsgId() + " EXITO: "+wrapperResultados.getMsg()+" SE INSERT� EL REGISTRO ");
    		}
    	} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
    */
	
	//*****************************************************************************
  //FUNCIONA CORRECTAMENTE BAJO 7 PRUEBAS
	/*
    public void testBorraEstructura() throws Exception {
    	try {
			WrapperResultados wrapperResultados =  estructuraManager.borraEstructura("16");
			if(wrapperResultados.getMsg().equals("1")){
    			logger.info("--->"+wrapperResultados.getMsgId() + " ERROR: " + wrapperResultados.getMsg()+ " PROBLEMA!! NO SE PUDO BORRAR EL REGISTRO, EXISTEN ELEMENTOS DEPENDIENTES ");
    		}else{	
    			logger.info("--->"+wrapperResultados.getMsgId() + " EXITO: "+wrapperResultados.getMsg()+" EL REGISTRO SE BORR� SATISFACTORIAMENTE");
    		}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
   */
    
  //*****************************************************************************
  //FUNCIONA CORRECTAMENTE BAJO 2 PRUEBAS
	/*
	public void testCopiaEstructura() throws Exception {
    	try {
    		EstructuraVO estructuraVO = new EstructuraVO();
    		estructuraVO.setCodigo("19");
			WrapperResultados wrapperResultados =  estructuraManager.copiaEstructura(estructuraVO);
			if(wrapperResultados.getMsg().equals("1")){
    			logger.info("--->"+wrapperResultados.getMsgId() + " ERROR: " + wrapperResultados.getMsg()+ " PROBLEMA!! NO SE PUDO COPIAR EL REGISTRO");
    		}else{	
    			logger.info("--->"+wrapperResultados.getMsgId() + " EXITO: "+wrapperResultados.getMsg()+" EL REGISTRO SE COPI� SATISFACTORIAMENTE");
    		}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
    }
   */
}
