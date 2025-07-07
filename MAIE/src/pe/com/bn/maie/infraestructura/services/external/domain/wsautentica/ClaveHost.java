
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
 *         &lt;element name="info1" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "info1"
})
@XmlRootElement(name = "claveHost")
public class ClaveHost {

    @XmlElement(required = true, nillable = true)
    protected String info1;

    /**
     * Obtiene el valor de la propiedad info1.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInfo1() {
        return info1;
    }

    /**
     * Define el valor de la propiedad info1.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInfo1(String value) {
        this.info1 = value;
    }

}
