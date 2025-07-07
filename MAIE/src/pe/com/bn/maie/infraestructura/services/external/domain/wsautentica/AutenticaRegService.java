
package pe.com.bn.maie.infraestructura.services.external.domain.wsautentica;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

@WebServiceClient(name = "AutenticaRegService", targetNamespace = "http://service.seguridad", wsdlLocation = "WEB-INF/wsdl/AutenticaReg.wsdl")
public class AutenticaRegService
    extends Service
{

    private final static URL AUTENTICAREGSERVICE_WSDL_LOCATION;
    private final static WebServiceException AUTENTICAREGSERVICE_EXCEPTION;
    private final static QName AUTENTICAREGSERVICE_QNAME = new QName("http://service.seguridad", "AutenticaRegService");

    static {
            AUTENTICAREGSERVICE_WSDL_LOCATION = pe.com.bn.maie.infraestructura.services.external.domain.wsautentica.AutenticaRegService.class.getResource("/WEB-INF/wsdl/AutenticaReg.wsdl");
        WebServiceException e = null;
        if (AUTENTICAREGSERVICE_WSDL_LOCATION == null) {
            e = new WebServiceException("Cannot find 'WEB-INF/wsdl/AutenticaReg.wsdl' wsdl. Place the resource correctly in the classpath.");
        }
        AUTENTICAREGSERVICE_EXCEPTION = e;
    }

    public AutenticaRegService() {
        super(__getWsdlLocation(), AUTENTICAREGSERVICE_QNAME);
    }

    public AutenticaRegService(WebServiceFeature... features) {
        super(__getWsdlLocation(), AUTENTICAREGSERVICE_QNAME, features);
    }

    public AutenticaRegService(URL wsdlLocation) {
        super(wsdlLocation, AUTENTICAREGSERVICE_QNAME);
    }

    public AutenticaRegService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, AUTENTICAREGSERVICE_QNAME, features);
    }

    public AutenticaRegService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public AutenticaRegService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns AutenticaReg
     */
    @WebEndpoint(name = "AutenticaReg")
    public AutenticaReg getAutenticaReg() {
        return super.getPort(new QName("http://service.seguridad", "AutenticaReg"), AutenticaReg.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns AutenticaReg
     */
    @WebEndpoint(name = "AutenticaReg")
    public AutenticaReg getAutenticaReg(WebServiceFeature... features) {
        return super.getPort(new QName("http://service.seguridad", "AutenticaReg"), AutenticaReg.class, features);
    }

    private static URL __getWsdlLocation() {
        if (AUTENTICAREGSERVICE_EXCEPTION!= null) {
            throw AUTENTICAREGSERVICE_EXCEPTION;
        }
        return AUTENTICAREGSERVICE_WSDL_LOCATION;
    }

}
