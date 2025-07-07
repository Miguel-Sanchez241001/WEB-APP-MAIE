
package pe.com.bn.maie.infraestructura.services.external.domain.wsautentica;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="claveHostReturn" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "claveHostReturn"
})
@XmlRootElement(name = "claveHostResponse")
public class ClaveHostResponse {

    @XmlElement(required = true, nillable = true)
    protected String claveHostReturn;

    /**
     * Obtiene el valor de la propiedad claveHostReturn.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getClaveHostReturn() {
        return claveHostReturn;
    }

    /**
     * Define el valor de la propiedad claveHostReturn.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setClaveHostReturn(String value) {
        this.claveHostReturn = value;
    }

}
