package mx.com.gseguros.ws.ice2sigs.service.impl;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import mx.com.aon.kernel.service.KernelManagerSustituto;
import mx.com.aon.portal.model.UserVO;
import mx.com.aon.portal.util.WrapperResultados;
import mx.com.gseguros.exception.ApplicationException;
import mx.com.gseguros.portal.siniestros.service.SiniestrosManager;
import mx.com.gseguros.utils.Constantes;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.Agente;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.AgenteGS;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.AgenteGSE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.AgenteGSResponseE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.AgenteRespuesta;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.AgenteSalud;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.AgenteSaludGS;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.AgenteSaludGSE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.AgenteSaludGSResponseE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.AgenteSaludRespuesta;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.AseguradoRespuesta;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.Ccomision;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.CcomisionRespuesta;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.Cliente;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ClienteGS;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ClienteGSE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ClienteGSResponseE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ClienteRespuesta;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ClienteSalud;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ClienteSaludGS;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ClienteSaludGSE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ClienteSaludGSResponseE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ClienteSaludRespuesta;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ComisionReciboAgenteGS;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ComisionReciboAgenteGSE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ComisionReciboAgenteGSResponseE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.MovimientoAgenteGS;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.MovimientoAgenteGSE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.MovimientoAgenteGSResponseE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.MovimientoAgenteRespuesta;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.MovimientoAseguradoCoberturaGS;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.MovimientoAseguradoCoberturaGSE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.MovimientoAseguradoCoberturaGSResponseE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.MovimientoAseguradoCoberturaRespuesta;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.MovimientoAseguradoEndosoGS;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.MovimientoAseguradoEndosoGSE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.MovimientoAseguradoEndosoGSResponseE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.MovimientoAseguradoEndosoRespuesta;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.MovimientoAseguradoGS;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.MovimientoAseguradoGSE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.MovimientoAseguradoGSResponseE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.MovimientoGS;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.MovimientoGSE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.MovimientoGSResponseE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.MovimientoRespuesta;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.Poliza;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.PolizaGS;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.PolizaGSE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.PolizaGSResponseE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.PolizaMovimiento;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.PolizaMovimientoAgente;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.PolizaMovimientoAsegurado;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.PolizaMovimientoAseguradoCobertura;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.PolizaMovimientoAseguradoEndoso;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.PolizaRespuesta;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.Recibo;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ReciboGS;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ReciboGSE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ReciboGSResponseE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ReciboRespuesta;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.Reclamo;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ReclamoGS;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ReclamoGSE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ReclamoGSResponseE;
import mx.com.gseguros.ws.ice2sigs.client.axis2.ServicioGSServiceStub.ReclamoRespuesta;
import mx.com.gseguros.ws.ice2sigs.client.axis2.callback.impl.ServicioGSServiceCallbackHandlerImpl;
import mx.com.gseguros.ws.ice2sigs.client.model.ReciboWrapper;
import mx.com.gseguros.ws.ice2sigs.service.Ice2sigsService;

import org.apache.axis2.AxisFault;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

public class Ice2sigsServiceImpl implements Ice2sigsService {
	
	private static org.apache.log4j.Logger logger = org.apache.log4j.Logger
			.getLogger(Ice2sigsServiceImpl.class);

	private static final long WS_TIMEOUT =  20000;
	
	private String endpoint;
	
	private String urlImpresionRecibos;
	
	private transient KernelManagerSustituto kernelManager;
	private SiniestrosManager siniestrosManager;

	
	public PolizaRespuesta ejecutaPolizaGS(Operacion operacion,
			Poliza poliza, String endpoint) throws Exception {

		PolizaRespuesta resultado = null;
		ServicioGSServiceStub stubGS = null;

		try {
			logger.info(new StringBuffer("endpoint a invocar=").append(endpoint));
			stubGS = new ServicioGSServiceStub(endpoint);
		} catch (AxisFault e) {
			logger.error(e);
			throw new Exception("Error de preparacion de Axis2: "
					+ e.getMessage());
		}
		stubGS._getServiceClient().getOptions().setTimeOutInMilliSeconds(WS_TIMEOUT);

		PolizaGSResponseE RespuestaGS = null;

		PolizaGS polizaS = new PolizaGS();
		polizaS.setArg0(operacion.getCodigo());
		polizaS.setArg1(poliza);

		PolizaGSE polizaE = new PolizaGSE();
		polizaE.setPolizaGS(polizaS);

		try {
			RespuestaGS = stubGS.polizaGS(polizaE);
			resultado = RespuestaGS.getPolizaGSResponse().get_return();
		} catch (RemoteException re) {
			logger.error(re);
			throw new Exception("Error de conexion: " + re.getMessage());
		}
		
		return resultado;
	}

	private ReciboRespuesta ejecutaReciboGS(Operacion operacion,
			Recibo recibo, HashMap<String, Object> params, boolean async) throws Exception {
		
		ReciboRespuesta resultado = null;
		ServicioGSServiceStub stubGS = null;
		
		try {
			logger.info(new StringBuffer("endpoint a invocar=").append(endpoint));
			stubGS = new ServicioGSServiceStub(endpoint);
		} catch (AxisFault e) {
			logger.error(e);
			throw new Exception("Error de preparacion de Axis2: "
					+ e.getMessage());
		}
		stubGS._getServiceClient().getOptions().setTimeOutInMilliSeconds(WS_TIMEOUT);
		
		ReciboGSResponseE RespuestaGS = null;
		
		ReciboGS reciboS = new ReciboGS();
		reciboS.setArg0(operacion.getCodigo());
		reciboS.setArg1(recibo);
		
		ReciboGSE reciboE = new ReciboGSE();
		reciboE.setReciboGS(reciboS);
		
		try {
			if(async){
				//TODO: RBS Cambiar params por PolizaVO
				//Se genera una nueva instancia en cada llamado, para evitar corrupcion de datos en el handler:
				WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
				ServicioGSServiceCallbackHandlerImpl callback = (ServicioGSServiceCallbackHandlerImpl)context.getBean("servicioGSServiceCallbackHandlerImpl");
				// Se setean los parametros al callback handler:
				callback.setClientData(params);
				
				stubGS.startreciboGS(reciboE, callback);
			} else {
				RespuestaGS = stubGS.reciboGS(reciboE);
				resultado = RespuestaGS.getReciboGSResponse().get_return();
				logger.debug("Resultado sincrono para primer ejecucion de WS: "+resultado.getCodigo()+" - "+resultado.getMensaje());
			}
		} catch (RemoteException re) {
			logger.error(re);
			throw new Exception("Error de conexion: " + re.getMessage());
		}
		
		return resultado;
	}

	public AgenteRespuesta ejecutaAgenteGS(Operacion operacion,
			Agente agente, String endpoint) throws Exception {
		
		AgenteRespuesta resultado = null;
		ServicioGSServiceStub stubGS = null;
		
		try {
			logger.info(new StringBuffer("endpoint a invocar=").append(endpoint));
			stubGS = new ServicioGSServiceStub(endpoint);
		} catch (AxisFault e) {
			logger.error(e);
			throw new Exception("Error de preparacion de Axis2: "
					+ e.getMessage());
		}
		stubGS._getServiceClient().getOptions().setTimeOutInMilliSeconds(WS_TIMEOUT);
		
		AgenteGSResponseE RespuestaGS = null;
		
		AgenteGS agenteS = new AgenteGS();
		agenteS.setArg0(operacion.getCodigo());
		agenteS.setArg1(agente);
		
		AgenteGSE agenteE = new AgenteGSE();
		agenteE.setAgenteGS(agenteS);
		
		try {
			RespuestaGS = stubGS.agenteGS(agenteE);
			resultado = RespuestaGS.getAgenteGSResponse().get_return();
		} catch (RemoteException re) {
			logger.error(re);
			throw new Exception("Error de conexion: " + re.getMessage());
		}
		
		return resultado;
	}
	
	public AgenteSaludRespuesta ejecutaAgenteSaludGS(Operacion operacion,
			AgenteSalud agente, String endpoint) throws Exception {
		
		AgenteSaludRespuesta resultado = null;
		ServicioGSServiceStub stubGS = null;
		
		try {
			logger.info(new StringBuffer("endpoint a invocar=").append(endpoint));
			stubGS = new ServicioGSServiceStub(endpoint);
		} catch (AxisFault e) {
			logger.error(e);
			throw new Exception("Error de preparacion de Axis2: "
					+ e.getMessage());
		}
		stubGS._getServiceClient().getOptions().setTimeOutInMilliSeconds(WS_TIMEOUT);
		
		AgenteSaludGSResponseE RespuestaGS = null;
		
		AgenteSaludGS agenteS = new AgenteSaludGS();
		agenteS.setArg0(operacion.getCodigo());
		agenteS.setArg1(agente);
		
		AgenteSaludGSE agenteE = new AgenteSaludGSE();
		agenteE.setAgenteSaludGS(agenteS);
		
		try {
			RespuestaGS = stubGS.agenteSaludGS(agenteE);
			resultado = RespuestaGS.getAgenteSaludGSResponse().get_return();
		} catch (RemoteException re) {
			logger.error(re);
			throw new Exception("Error de conexion: " + re.getMessage());
		}
		
		return resultado;
	}

	public ClienteRespuesta ejecutaClienteGS(Operacion operacion,
			Cliente cliente, String endpoint) throws Exception {
		
		ClienteRespuesta resultado = null;
		ServicioGSServiceStub stubGS = null;
		
		try {
			logger.info(new StringBuffer("endpoint a invocar=").append(endpoint));
			stubGS = new ServicioGSServiceStub(endpoint);
		} catch (AxisFault e) {
			logger.error(e);
			throw new Exception("Error de preparacion de Axis2: "
					+ e.getMessage());
		}
		stubGS._getServiceClient().getOptions().setTimeOutInMilliSeconds(WS_TIMEOUT);
		
		ClienteGSResponseE RespuestaGS = null;
		
		ClienteGS clienteS = new ClienteGS();
		clienteS.setArg0(operacion.getCodigo());
		clienteS.setArg1(cliente);
		
		ClienteGSE clienteE = new ClienteGSE();
		clienteE.setClienteGS(clienteS);
		
		try {
			RespuestaGS = stubGS.clienteGS(clienteE);
			resultado = RespuestaGS.getClienteGSResponse().get_return();
		} catch (RemoteException re) {
			logger.error(re);
			throw new Exception("Error de conexion: " + re.getMessage());
		}
		
		return resultado;
	}
	
	private ClienteSaludRespuesta ejecutaClienteSaludGS(Operacion operacion,
			ClienteSalud cliente, HashMap<String, Object> params, boolean async) throws Exception {
		
		ClienteSaludRespuesta resultado = null;
		ServicioGSServiceStub stubGS = null;
		
		try {
			logger.info(new StringBuffer("endpoint a invocar=").append(endpoint));
			stubGS = new ServicioGSServiceStub(endpoint);
		} catch (AxisFault e) {
			logger.error(e);
			throw new Exception("Error de preparacion de Axis2: "
					+ e.getMessage());
		}
		stubGS._getServiceClient().getOptions().setTimeOutInMilliSeconds(WS_TIMEOUT);
		
		ClienteSaludGSResponseE RespuestaGS = null;
		
		ClienteSaludGS clienteS = new ClienteSaludGS();
		clienteS.setArg0(operacion.getCodigo());
		clienteS.setArg1(cliente);
		
		ClienteSaludGSE clienteE = new ClienteSaludGSE();
		clienteE.setClienteSaludGS(clienteS);
		
		try {
			if(async){
				//TODO: RBS Cambiar params por PolizaVO
				//Se genera una nueva instancia en cada llamado, para evitar corrupcion de datos en el handler:
				WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
				ServicioGSServiceCallbackHandlerImpl callback = (ServicioGSServiceCallbackHandlerImpl)context.getBean("servicioGSServiceCallbackHandlerImpl");
				// Se setean los parametros al callback handler:
				callback.setClientData(params);
				
				stubGS.startclienteSaludGS(clienteE, callback);
			} else {
				RespuestaGS = stubGS.clienteSaludGS(clienteE);
				resultado = RespuestaGS.getClienteSaludGSResponse().get_return();
				logger.debug("Resultado sincrono para primer ejecucion de WS: "+resultado.getCodigo()+" - "+resultado.getMensaje());
			}
		} catch (RemoteException re) {
			logger.error(re);
			throw new Exception("Error de conexion: " + re.getMessage());
		}
		
		return resultado;
	}
	
	public CcomisionRespuesta ejecutaComisionReciboAgenteGS(Operacion operacion,
			Ccomision comisionReciboAgente, String endpoint) throws Exception {
		
		CcomisionRespuesta resultado = null;
		ServicioGSServiceStub stubGS = null;
		
		try {
			logger.info(new StringBuffer("endpoint a invocar=").append(endpoint));
			stubGS = new ServicioGSServiceStub(endpoint);
		} catch (AxisFault e) {
			logger.error(e);
			throw new Exception("Error de preparacion de Axis2: "
					+ e.getMessage());
		}
		stubGS._getServiceClient().getOptions().setTimeOutInMilliSeconds(WS_TIMEOUT);
		
		ComisionReciboAgenteGSResponseE RespuestaGS = null;
		
		ComisionReciboAgenteGS comisionReciboAgenteS = new ComisionReciboAgenteGS();
		comisionReciboAgenteS.setArg0(operacion.getCodigo());
		comisionReciboAgenteS.setArg1(comisionReciboAgente);
		
		ComisionReciboAgenteGSE comisionReciboAgenteE = new ComisionReciboAgenteGSE();
		comisionReciboAgenteE.setComisionReciboAgenteGS(comisionReciboAgenteS);
		
		try {
			RespuestaGS = stubGS.comisionReciboAgenteGS(comisionReciboAgenteE);
			resultado = RespuestaGS.getComisionReciboAgenteGSResponse().get_return();
		} catch (RemoteException re) {
			logger.error(re);
			throw new Exception("Error de conexion: " + re.getMessage());
		}
		
		return resultado;
	}
	
	
	public MovimientoAgenteRespuesta ejecutaMovimientoAgenteGS(Operacion operacion,
			PolizaMovimientoAgente movimientoAgente, String endpoint) throws Exception {
		
		MovimientoAgenteRespuesta resultado = null;
		ServicioGSServiceStub stubGS = null;
		
		try {
			logger.info(new StringBuffer("endpoint a invocar=").append(endpoint));
			stubGS = new ServicioGSServiceStub(endpoint);
		} catch (AxisFault e) {
			logger.error(e);
			throw new Exception("Error de preparacion de Axis2: "
					+ e.getMessage());
		}
		stubGS._getServiceClient().getOptions().setTimeOutInMilliSeconds(WS_TIMEOUT);
		
		MovimientoAgenteGSResponseE RespuestaGS = null;
		
		MovimientoAgenteGS movimientoAgenteS = new MovimientoAgenteGS();
		movimientoAgenteS.setArg0(operacion.getCodigo());
		movimientoAgenteS.setArg1(movimientoAgente);
		
		MovimientoAgenteGSE movimientoAgenteE = new MovimientoAgenteGSE();
		movimientoAgenteE.setMovimientoAgenteGS(movimientoAgenteS);
		
		try {
			RespuestaGS = stubGS.movimientoAgenteGS(movimientoAgenteE);
			resultado = RespuestaGS.getMovimientoAgenteGSResponse().get_return();
		} catch (RemoteException re) {
			logger.error(re);
			throw new Exception("Error de conexion: " + re.getMessage());
		}
		
		return resultado;
	}

	public AseguradoRespuesta ejecutaMovimientoAseguradoGS(Operacion operacion,
			PolizaMovimientoAsegurado movimientoAsegurado, String endpoint) throws Exception {
		
		AseguradoRespuesta resultado = null;
		ServicioGSServiceStub stubGS = null;
		
		try {
			logger.info(new StringBuffer("endpoint a invocar=").append(endpoint));
			stubGS = new ServicioGSServiceStub(endpoint);
		} catch (AxisFault e) {
			logger.error(e);
			throw new Exception("Error de preparacion de Axis2: "
					+ e.getMessage());
		}
		stubGS._getServiceClient().getOptions().setTimeOutInMilliSeconds(WS_TIMEOUT);
		
		MovimientoAseguradoGSResponseE RespuestaGS = null;
		
		MovimientoAseguradoGS movimientoAseguradoS = new MovimientoAseguradoGS();
		movimientoAseguradoS.setArg0(operacion.getCodigo());
		movimientoAseguradoS.setArg1(movimientoAsegurado);
		
		MovimientoAseguradoGSE movimientoAseguradoE = new MovimientoAseguradoGSE();
		movimientoAseguradoE.setMovimientoAseguradoGS(movimientoAseguradoS);
		
		try {
			RespuestaGS = stubGS.movimientoAseguradoGS(movimientoAseguradoE);
			resultado = RespuestaGS.getMovimientoAseguradoGSResponse().get_return();
		} catch (RemoteException re) {
			logger.error(re);
			throw new Exception("Error de conexion: " + re.getMessage());
		}
		
		return resultado;
	}

	public MovimientoAseguradoCoberturaRespuesta ejecutaMovimientoAseguradoCoberturaGS(Operacion operacion,
			PolizaMovimientoAseguradoCobertura movimientoAseguradoCobertura, String endpoint) throws Exception {
		
		MovimientoAseguradoCoberturaRespuesta resultado = null;
		ServicioGSServiceStub stubGS = null;
		
		try {
			logger.info(new StringBuffer("endpoint a invocar=").append(endpoint));
			stubGS = new ServicioGSServiceStub(endpoint);
		} catch (AxisFault e) {
			logger.error(e);
			throw new Exception("Error de preparacion de Axis2: "
					+ e.getMessage());
		}
		stubGS._getServiceClient().getOptions().setTimeOutInMilliSeconds(WS_TIMEOUT);
		
		MovimientoAseguradoCoberturaGSResponseE RespuestaGS = null;
		
		MovimientoAseguradoCoberturaGS movimientoAseguradoCoberturaS = new MovimientoAseguradoCoberturaGS();
		movimientoAseguradoCoberturaS.setArg0(operacion.getCodigo());
		movimientoAseguradoCoberturaS.setArg1(movimientoAseguradoCobertura);
		
		MovimientoAseguradoCoberturaGSE movimientoAseguradoCoberturaE = new MovimientoAseguradoCoberturaGSE();
		movimientoAseguradoCoberturaE.setMovimientoAseguradoCoberturaGS(movimientoAseguradoCoberturaS);
		
		try {
			RespuestaGS = stubGS.movimientoAseguradoCoberturaGS(movimientoAseguradoCoberturaE);
			resultado = RespuestaGS.getMovimientoAseguradoCoberturaGSResponse().get_return();
		} catch (RemoteException re) {
			logger.error(re);
			throw new Exception("Error de conexion: " + re.getMessage());
		}
		
		return resultado;
	}

	public MovimientoAseguradoEndosoRespuesta ejecutaMovimientoAseguradoEndosoGS(Operacion operacion,
			PolizaMovimientoAseguradoEndoso movimientoAseguradoEndoso, String endpoint) throws Exception {
		
		MovimientoAseguradoEndosoRespuesta resultado = null;
		ServicioGSServiceStub stubGS = null;
		
		try {
			logger.info(new StringBuffer("endpoint a invocar=").append(endpoint));
			stubGS = new ServicioGSServiceStub(endpoint);
		} catch (AxisFault e) {
			logger.error(e);
			throw new Exception("Error de preparacion de Axis2: "
					+ e.getMessage());
		}
		stubGS._getServiceClient().getOptions().setTimeOutInMilliSeconds(WS_TIMEOUT);
		
		MovimientoAseguradoEndosoGSResponseE RespuestaGS = null;
		
		MovimientoAseguradoEndosoGS movimientoAseguradoEndosoS = new MovimientoAseguradoEndosoGS();
		movimientoAseguradoEndosoS.setArg0(operacion.getCodigo());
		movimientoAseguradoEndosoS.setArg1(movimientoAseguradoEndoso);
		
		MovimientoAseguradoEndosoGSE movimientoAseguradoEndosoE = new MovimientoAseguradoEndosoGSE();
		movimientoAseguradoEndosoE.setMovimientoAseguradoEndosoGS(movimientoAseguradoEndosoS);
		
		try {
			RespuestaGS = stubGS.movimientoAseguradoEndosoGS(movimientoAseguradoEndosoE);
			resultado = RespuestaGS.getMovimientoAseguradoEndosoGSResponse().get_return();
		} catch (RemoteException re) {
			logger.error(re);
			throw new Exception("Error de conexion: " + re.getMessage());
		}
		
		return resultado;
	}

	public MovimientoRespuesta ejecutaMovimientoGS(Operacion operacion,
			PolizaMovimiento movimiento, String endpoint) throws Exception {
		
		MovimientoRespuesta resultado = null;
		ServicioGSServiceStub stubGS = null;
		
		try {
			logger.info(new StringBuffer("endpoint a invocar=").append(endpoint));
			stubGS = new ServicioGSServiceStub(endpoint);
		} catch (AxisFault e) {
			logger.error(e);
			throw new Exception("Error de preparacion de Axis2: "
					+ e.getMessage());
		}
		stubGS._getServiceClient().getOptions().setTimeOutInMilliSeconds(WS_TIMEOUT);
		
		MovimientoGSResponseE RespuestaGS = null;
		
		MovimientoGS movimientoS = new MovimientoGS();
		movimientoS.setArg0(operacion.getCodigo());
		movimientoS.setArg1(movimiento);
		
		MovimientoGSE movimientoE = new MovimientoGSE();
		movimientoE.setMovimientoGS(movimientoS);
		
		try {
			RespuestaGS = stubGS.movimientoGS(movimientoE);
			resultado = RespuestaGS.getMovimientoGSResponse().get_return();
		} catch (RemoteException re) {
			logger.error(re);
			throw new Exception("Error de conexion: " + re.getMessage());
		}
		
		return resultado;
	}
	
	private ReclamoRespuesta ejecutaReclamoGS(Operacion operacion,
			Reclamo reclamo, HashMap<String, Object> params, boolean async) throws Exception {
		
		ReclamoRespuesta resultado = null;
		ServicioGSServiceStub stubGS = null;
		
		try {
			logger.info(new StringBuffer("endpoint a invocar=").append(endpoint));
			stubGS = new ServicioGSServiceStub(endpoint);
		} catch (AxisFault e) {
			logger.error(e);
			throw new Exception("Error de preparacion de Axis2: "
					+ e.getMessage());
		}
		stubGS._getServiceClient().getOptions().setTimeOutInMilliSeconds(WS_TIMEOUT);
		
		ReclamoGSResponseE RespuestaGS = null;
		
		ReclamoGS reclamoS = new ReclamoGS();
		reclamoS.setArg0(operacion.getCodigo());
		reclamoS.setArg1(reclamo);
		
		ReclamoGSE reclamoE = new ReclamoGSE();
		reclamoE.setReclamoGS(reclamoS);
		
		try {
			if(async){
				//TODO: RBS Cambiar params por PolizaVO
				//Se genera una nueva instancia en cada llamado, para evitar corrupcion de datos en el handler:
				WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(ServletActionContext.getServletContext());
				ServicioGSServiceCallbackHandlerImpl callback = (ServicioGSServiceCallbackHandlerImpl)context.getBean("servicioGSServiceCallbackHandlerImpl");
				// Se setean los parametros al callback handler:
				callback.setClientData(params);
				
				stubGS.startreclamoGS(reclamoE, callback);
			} else {
				RespuestaGS = stubGS.reclamoGS(reclamoE);
				resultado = RespuestaGS.getReclamoGSResponse().get_return();
				logger.debug("Resultado de WS ejecutaReclamoGS: "+resultado.getCodigo()+" - "+resultado.getMensaje());
			}
		} catch (RemoteException re) {
			logger.error(re);
			throw new Exception("Error de conexion: " + re.getMessage());
		}
		
		return resultado;
	}
	
	
	public boolean ejecutaWSclienteSalud(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem,
			Ice2sigsService.Operacion op, UserVO userVO) {
		
		logger.debug("********************* Entrando a Ejecuta WSclienteSalud ******************************");
		
		boolean exito = true;
		
		WrapperResultados result = null;
		ClienteSalud cliente =  null;
		
		//Se invoca servicio para obtener los datos del cliente
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i", cdramo);
		params.put("pv_estado_i", estado);
		params.put("pv_nmpoliza_i", nmpoliza);
		params.put("pv_nmsuplem_i", nmsuplem);
		try {
			result = kernelManager.obtenDatosClienteWS(params);
			if(result.getItemList() != null && result.getItemList().size() > 0){
				cliente = ((ArrayList<ClienteSalud>) result.getItemList()).get(0);
			}
		} catch (Exception e1) {
			logger.error("Error en llamar al PL de obtencion de ejecutaWSclienteSalud",e1);
			return false;
		}
		
		if(cliente != null){
			
			String usuario = "SIN USUARIO";
			if(userVO != null){
				usuario = userVO.getUser();
			}
			params.put("USUARIO", usuario);
			
			try{
				logger.debug("Ejecutando WS TEST para WS Cliente");
				ejecutaClienteSaludGS(Operacion.INSERTA, null, params, false);
			}catch(Exception e){
				logger.error("Error al ejecutar WS TEST para cliente: " + cliente.getClaveCli(), e);
			}
			try{
				logger.debug(">>>>>>> Enviando el Cliente: " + cliente.getClaveCli());
				ejecutaClienteSaludGS(op, cliente, params, true);
			}catch(Exception e){
				logger.error("Error al insertar el cliente: " + cliente.getClaveCli(), e);
				exito = false;
			}
		}

		return exito;
	}

	
	public boolean ejecutaWSrecibos(String cdunieco, String cdramo,
			String estado, String nmpoliza, String nmsuplem,
			String rutaPoliza, String cdtipsitGS, String sucursal,
			String nmsolici, String ntramite, boolean async,
			String tipoMov, UserVO userVO) {
		
		boolean allInserted = true;
		
		logger.debug("*** Entrando a metodo Recibos WS ice2sigs, para la poliza: " + nmpoliza + " sucursal: " + sucursal + " tipoMov: " + tipoMov + "***");
		
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i", cdramo);
		params.put("pv_estado_i", estado);
		params.put("pv_nmpoliza_i", nmpoliza);
		params.put("pv_nmsuplem_i", nmsuplem);
		
		WrapperResultados result = null;
		ArrayList<ReciboWrapper> recibos =  null;
		try {
			result = kernelManager.obtenDatosRecibos(params);
			recibos = (ArrayList<ReciboWrapper>) result.getItemList();
		} catch (Exception e1) {
			logger.error("Error en llamar al PL de obtencion de RECIBOS",e1);
			return false;
		}

		String usuario = "SIN USUARIO";
		if(userVO != null){
			usuario = userVO.getUser();
		}
		
		if(async){
			params.put("USUARIO", usuario);
		}
		
		Recibo recibo = null;
		for(ReciboWrapper recVO: recibos){
			
			try{
				recibo = recVO.getRecibo();
				Operacion operacion = Operacion.valueOf(recVO.getOperacion());
				
				if(async){
					// Se crea un HashMap por cada invocacion asincrona del WS, para evitar issue (sobreescritura de valores):
					HashMap<String, Object> paramsBitacora = new HashMap<String, Object>();
					paramsBitacora.putAll(params);
					paramsBitacora.put("NumRec", recibo.getNumRec());
					
					ejecutaReciboGS(operacion, recibo, paramsBitacora, async);
				}else{
					ReciboRespuesta respuesta = ejecutaReciboGS(operacion, recibo, null, async);
					logger.debug("Resultado al ejecutar el WS Recibo: " + recibo.getNumRec() + " >>>"
							+ respuesta.getCodigo() + " - " + respuesta.getMensaje());

					if (Estatus.EXITO.getCodigo() != respuesta.getCodigo()) {
						logger.error("Guardando en bitacora el estatus");

						try {
							kernelManager.movBitacobro((String) params.get("pv_cdunieco_i"),
									(String) params.get("pv_cdramo_i"),
									(String) params.get("pv_estado_i"),
									(String) params.get("pv_nmpoliza_i"), "ErrWSrec",
									"Error en Recibo " + params.get("NumRec")
											+ " >>> " + respuesta.getCodigo() + " - "
											+ respuesta.getMensaje(),
									 usuario);
						} catch (ApplicationException e1) {
							logger.error("Error en llamado a PL", e1);
						}
					}
				}
			}catch(Exception e){
				logger.error("Error al insertar recibo: "+recibo.getNumRec()+" tramite: "+ntramite);
				try {
					kernelManager.movBitacobro(
							(String) params.get("pv_cdunieco_i"),
							(String) params.get("pv_cdramo_i"),
							(String) params.get("pv_estado_i"),
							(String) params.get("pv_nmpoliza_i"),
							"ErrWSrecCx",
							"Error en Recibo " + recibo.getNumRec()
									+ " Msg: " + e.getMessage() + " ***Cause: "
									+ e.getCause(),
							 usuario);
				} catch (Exception e1) {
					logger.error("Error en llamado a PL", e1);
				}
			}
		}
		
		/**
		 * PARA EL GUARDADO CADA PDF DE RECIBO
		 */
		logger.debug("*** Empieza generacion de URLs para Recibos ***");
		
		String visible = null;
		for(ReciboWrapper recVO: recibos){
			recibo = recVO.getRecibo();
			
			/**
			 * Si el Recibo Tiene estatus 1 se guarda en tdocupol como documento de la poliza, excepto algunos endosos como el de forma de pago,
			 * donde se generan recibos negativos para cancelar y esos no deben de guardarse, estos casos el estatus es distinto de 1
			 */
			if(!"1".equals(recibo.getStatusr())) continue;
			/**
			 * Por default se permite imprimir solo el primer recibo y los demas se guardan ocultos.
			 */
			visible = (1 == recibo.getNumRec()) ? Constantes.SI : Constantes.NO;
			
			try{
				int numEndoso;
				String tipoEndoso;
				// Si es poliza nueva:
				if(("1").equals(tipoMov)) {
					numEndoso = 0;
					tipoEndoso = "";
				} else {
					numEndoso = recibo.getNumEnd();
					tipoEndoso = recibo.getTipEnd();
				}
				
				//Parametro1:  9999: Recibo
				//Parametro2:  Siempre va en 0
				//Parametro3:  Sucursal
				//Parametro4:  Ramo (213 o 214)
				//Parametro5:  Poliza
				//Parametro6:  Tramite(poner 0)
				//Parametro7:  Numero de endoso (Cuando es poliza nueva poner 0)
				//Parametro8:  Tipo de endoso (Si es vacio no enviar nada en otro caso poner A o D segun sea el caso)
				//Parametro9:  Numero de recibo (1,2,3..segun la forma de pago) Para nuestro caso es siempre el 1
				//if( 1 == recibo.getNumRec()){
					String parametros = "?9999,0,"+sucursal+","+cdtipsitGS+","+nmpoliza+",0,"+numEndoso+","+tipoEndoso+","+recibo.getNumRec();
					logger.debug("URL Generada para Recibo: "+ urlImpresionRecibos + parametros);
					//HttpRequestUtil.generaReporte(this.getText("recibos.impresion.url")+parametros, rutaPoliza+"/Recibo_"+recibo.getRmdbRn()+"_"+recibo.getNumRec()+".pdf");
					
					HashMap<String, Object> paramsR =  new HashMap<String, Object>();
					paramsR.put("pv_cdunieco_i", cdunieco);
					paramsR.put("pv_cdramo_i", cdramo);
					paramsR.put("pv_estado_i", estado);
					paramsR.put("pv_nmpoliza_i", nmpoliza);
					paramsR.put("pv_nmsuplem_i", nmsuplem);
					paramsR.put("pv_feinici_i", new Date());
					paramsR.put("pv_cddocume_i", urlImpresionRecibos + parametros);
					paramsR.put("pv_dsdocume_i", "Recibo "+recibo.getNumRec());
					paramsR.put("pv_nmsolici_i", nmsolici);
					paramsR.put("pv_ntramite_i", ntramite);
					paramsR.put("pv_tipmov_i", tipoMov);
					paramsR.put("pv_swvisible_i", visible);
					
					kernelManager.guardarArchivo(paramsR);
				//}
			}catch(Exception e){
				logger.error("Error al guardar indexaxion de recibo: " + recibo.getRmdbRn(), e);
			}
		}

		return allInserted;
	}

	public boolean ejecutaWSreclamo(String ntramite, String cdunieco, String cdramo, String estado, String nmpoliza, Ice2sigsService.Operacion op, boolean async, UserVO userVO) {
		
		logger.debug("********************* Entrando a Ejecuta WSreclamo ******************************");
		
		boolean exito = true;
		
		List<Reclamo> result = null;
		Reclamo reclamo =  null;
		
		//Se invoca servicio para obtener los datos del cliente
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("pv_ntramite_i", ntramite);
		params.put("pv_cdunieco_i", cdunieco);
		params.put("pv_cdramo_i", cdramo);
		params.put("pv_estado_i", estado);
		params.put("pv_nmpoliza_i", nmpoliza);
		
		try {
			result = siniestrosManager.obtieneDatosReclamoWS(params);
			if(result != null && result.size() > 0){
				reclamo = result.get(0);
			}
		} catch (Exception e1) {
			logger.error("Error en llamar al PL de obtencion de obtieneDatosReclamoWS",e1);
			return false;
		}
		
		if(reclamo != null){
			
			String usuario = "SIN USUARIO";
			if(userVO != null){
				usuario = userVO.getUser();
			}
			params.put("USUARIO", usuario);
			
			try{
				logger.debug(">>>>>>> Enviando el Reclamo: " + reclamo.getIcodreclamo());
				ejecutaReclamoGS(op, reclamo, params, true);
			}catch(Exception e){
				logger.error("Error al enviar el Reclamo: " + reclamo.getIcodreclamo(), e);
				exito = false;
			}
		}

		return exito;
	}

	public String getEndpoint() {
		return endpoint;
	}

	public void setEndpoint(String endpoint) {
		this.endpoint = endpoint;
	}
	
	public String getUrlImpresionRecibos() {
		return urlImpresionRecibos;
	}

	public void setUrlImpresionRecibos(String urlImpresionRecibos) {
		this.urlImpresionRecibos = urlImpresionRecibos;
	}

	
	/**
	 * Setter method
	 * @param kernelManager
	 */
	public void setKernelManager(KernelManagerSustituto kernelManager) {
		this.kernelManager = kernelManager;
	}

	public void setSiniestrosManager(SiniestrosManager siniestrosManager) {
		this.siniestrosManager = siniestrosManager;
	}

}
