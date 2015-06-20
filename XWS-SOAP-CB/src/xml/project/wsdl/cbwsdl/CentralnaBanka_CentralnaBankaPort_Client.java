
package xml.project.wsdl.cbwsdl;

/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 2.6.5
 * 2015-06-19T14:18:44.937+02:00
 * Generated source version: 2.6.5
 * 
 */
public final class CentralnaBanka_CentralnaBankaPort_Client {

    private static final QName SERVICE_NAME = new QName("http://www.project.xml/wsdl/CBwsdl", "CentralnaBankaService");

    private CentralnaBanka_CentralnaBankaPort_Client() {
    }

    public static void main(String args[]) throws java.lang.Exception {
        URL wsdlURL = CentralnaBankaService.WSDL_LOCATION;
        if (args.length > 0 && args[0] != null && !"".equals(args[0])) { 
            File wsdlFile = new File(args[0]);
            try {
                if (wsdlFile.exists()) {
                    wsdlURL = wsdlFile.toURI().toURL();
                } else {
                    wsdlURL = new URL(args[0]);
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }
      
        CentralnaBankaService ss = new CentralnaBankaService(wsdlURL, SERVICE_NAME);
        CentralnaBanka port = ss.getCentralnaBankaPort();  
        
        {
        System.out.println("Invoking acceptMT103...");
        xml.project.mt103.MT103 _acceptMT103_mt103 = new xml.project.mt103.MT103();
        try {
            xml.project.mt910.MT910 _acceptMT103__return = port.acceptMT103(_acceptMT103_mt103);
            System.out.println("acceptMT103.result=" + _acceptMT103__return);

        } catch (AcceptMT103Fault e) { 
            System.out.println("Expected exception: acceptMT103Fault has occurred.");
            System.out.println(e.toString());
        }
            }
        {
        System.out.println("Invoking acceptMT102...");
        xml.project.mt102.MT102 _acceptMT102_mt102 = new xml.project.mt102.MT102();
        xml.project.globals.StatusCode _acceptMT102__return = port.acceptMT102(_acceptMT102_mt102);
        System.out.println("acceptMT102.result=" + _acceptMT102__return);


        }

        System.exit(0);
    }

}