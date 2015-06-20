
/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package xml.project.wsdl.bwsdl;

import java.util.logging.Logger;

import javax.rmi.CORBA.Util;

import rest.util.RESTUtil;
import xml.project.faktura.Faktura.ZaglavljeFakture.Racun;
import xml.project.globals.StatusCode;
import xml.project.globals.TBanke;
import xml.project.mt102.MT102;
import xml.project.mt103.MT103;
import xml.project.mt910.MT910;
import xml.project.presek.Presek;
import xml.project.uplatnica.NalogZaPrenos;
import xml.project.zahtev_za_izovd.Zahtev;

/**
 * This class was generated by Apache CXF 2.6.5
 * 2015-06-19T14:18:53.426+02:00
 * Generated source version: 2.6.5
 * 
 */

@javax.jws.WebService(
                      serviceName = "FirmaBankaService",
                      portName = "FirmaBanci",
                      targetNamespace = "http://www.project.xml/wsdl/bwsdl",
                      wsdlLocation = "WEB-INF/wsdl/Banka.wsdl",
                      endpointInterface = "xml.project.wsdl.bwsdl.FirmaBanci")
                      
public class FirmaBanciImpl implements FirmaBanci {

	TBanke banka;
    private static final Logger LOG = Logger.getLogger(FirmaBanciImpl.class.getName());

    public void init(){
    	this.banka = new TBanke();
    }
    /* (non-Javadoc)
     * @see xml.project.wsdl.bwsdl.FirmaBanci#doClearing(*
     */
    public StatusCode doClearing() { 
        LOG.info("Executing operation doClearing");
        try {
            StatusCode _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see xml.project.wsdl.bwsdl.FirmaBanci#acceptMT910(xml.project.mt910.MT910  mt910 )*
     */
    public StatusCode acceptMT910(MT910 mt910) { 
        LOG.info("Executing operation acceptMT910");
        System.out.println(mt910);
        
        
        
        try {
            StatusCode _return = new StatusCode();
            //RESTUtil.objectToDB("Banka/MT910", mt910.getIDPoruke(), mt910);
            
            String idPorukeNaloga = mt910.getIDPorukeNaloga(); // obrazac MT103
            MT103 mt103Temp = new MT103();
            
            
            
            
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see xml.project.wsdl.bwsdl.FirmaBanci#acceptMT102(xml.project.mt102.MT102  mt102 )*
     */
    public StatusCode acceptMT102(MT102 mt102) { 
        LOG.info("Executing operation acceptMT102");
        System.out.println(mt102);
        try {
            StatusCode _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see xml.project.wsdl.bwsdl.FirmaBanci#acceptMT103(xml.project.mt103.MT103  mt103 )*
     */
    public StatusCode acceptMT103(MT103 mt103) { 
        LOG.info("Executing operation acceptMT103");
        System.out.println(mt103);
        try {
            StatusCode _return = new StatusCode();
            
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see xml.project.wsdl.bwsdl.FirmaBanci#primiNalog(xml.project.uplatnica.NalogZaPrenos  nalogZaPrenos )*
     */
    public StatusCode primiNalog(NalogZaPrenos nalogZaPrenos) { 
        LOG.info("Executing operation primiNalog");
        System.out.println(nalogZaPrenos);
        try {
            StatusCode _return = null;
            return _return;
        } catch (java.lang.Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }

    /* (non-Javadoc)
     * @see xml.project.wsdl.bwsdl.FirmaBanci#traziIzvod(xml.project.zahtev_za_izovd.Zahtev  zaDatum )*
     */
    public Presek traziIzvod(Zahtev zaDatum) { 
        LOG.info("Executing operation traziIzvod");
        System.out.println(zaDatum);
        try {
            Presek _return = null;
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
    }
    
    public static void main(String[] args){
    	
    }

}
