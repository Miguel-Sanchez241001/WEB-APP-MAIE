
package pe.com.bn.maie.infraestructura.services.external.domain.wsautentica;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;

@WebService(name = "AutenticaReg", targetNamespace = "http://service.seguridad")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface AutenticaReg {


    /**
     * 
     * @param info1
     * @return
     *     returns java.lang.String
     */
    @WebMethod
    @WebResult(name = "claveHostReturn", targetNamespace = "")
    @RequestWrapper(localName = "claveHost", targetNamespace = "http://service.seguridad", className = "pe.com.bn.maie.infraestructura.services.external.domain.wsautentica.ClaveHost")
    @ResponseWrapper(localName = "claveHostResponse", targetNamespace = "http://service.seguridad", className = "pe.com.bn.maie.infraestructura.services.external.domain.wsautentica.ClaveHostResponse")
    public String claveHost(
        @WebParam(name = "info1", targetNamespace = "")
        String info1);

}
