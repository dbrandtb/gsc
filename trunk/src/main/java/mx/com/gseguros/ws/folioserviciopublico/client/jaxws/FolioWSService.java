
package mx.com.gseguros.ws.folioserviciopublico.client.jaxws;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.8
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "FolioWSService", targetNamespace = "http://com.gs.folioserviciopublico.soap.folio", wsdlLocation = "http://10.1.1.134:8000/folioserviciopublico-ws/servicios?wsdl")
public class FolioWSService
    extends Service
{

    private final static URL FOLIOWSSERVICE_WSDL_LOCATION;
    private final static WebServiceException FOLIOWSSERVICE_EXCEPTION;
    private final static QName FOLIOWSSERVICE_QNAME = new QName("http://com.gs.folioserviciopublico.soap.folio", "FolioWSService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("http://10.1.1.134:8000/folioserviciopublico-ws/servicios?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        FOLIOWSSERVICE_WSDL_LOCATION = url;
        FOLIOWSSERVICE_EXCEPTION = e;
    }

    public FolioWSService() {
        super(__getWsdlLocation(), FOLIOWSSERVICE_QNAME);
    }

    public FolioWSService(WebServiceFeature... features) {
        super(__getWsdlLocation(), FOLIOWSSERVICE_QNAME, features);
    }

    public FolioWSService(URL wsdlLocation) {
        super(wsdlLocation, FOLIOWSSERVICE_QNAME);
    }

    public FolioWSService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, FOLIOWSSERVICE_QNAME, features);
    }

    public FolioWSService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public FolioWSService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns FolioWS
     */
    @WebEndpoint(name = "FolioWSPort")
    public FolioWS getFolioWSPort() {
        return super.getPort(new QName("http://com.gs.folioserviciopublico.soap.folio", "FolioWSPort"), FolioWS.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns FolioWS
     */
    @WebEndpoint(name = "FolioWSPort")
    public FolioWS getFolioWSPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://com.gs.folioserviciopublico.soap.folio", "FolioWSPort"), FolioWS.class, features);
    }

    private static URL __getWsdlLocation() {
        if (FOLIOWSSERVICE_EXCEPTION!= null) {
            throw FOLIOWSSERVICE_EXCEPTION;
        }
        return FOLIOWSSERVICE_WSDL_LOCATION;
    }

}
