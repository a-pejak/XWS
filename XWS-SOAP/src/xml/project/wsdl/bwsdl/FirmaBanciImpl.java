/**
 * Please modify this class to meet your needs
 * This class is not complete
 */

package xml.project.wsdl.bwsdl;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.logging.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import rest.bundle.RequestMethod;
import rest.util.RESTUtil;
import rest.util.Validation;
import xml.project.globals.StatusCode;
import xml.project.globals.TBanke;
import xml.project.globals.TOsobe;
import xml.project.globals.TSequence;
import xml.project.mt102.MT102;
import xml.project.mt103.MT103;
import xml.project.mt900.MT900;
import xml.project.mt910.MT910;
import xml.project.presek.Presek;
import xml.project.racuni.Racuni;
import xml.project.racuni.Racuni.FirmaRacun;
import xml.project.racuni.Racuni.FirmaRacun.Racun;
import xml.project.uplatnica.NalogZaPrenos;
import xml.project.wsdl.cbwsdl.CentralnaBanka;
import xml.project.zahtev_za_izovd.Zahtev;

/**
 * This class was generated by Apache CXF 2.6.5 2015-06-19T14:18:53.426+02:00
 * Generated source version: 2.6.5
 * 
 */

@javax.jws.WebService(serviceName = "FirmaBankaService", portName = "FirmaBanci", targetNamespace = "http://www.project.xml/wsdl/bwsdl", wsdlLocation = "WEB-INF/wsdl/Banka.wsdl", endpointInterface = "xml.project.wsdl.bwsdl.FirmaBanci")
public class FirmaBanciImpl implements FirmaBanci {

	TBanke currentBank = new TBanke();
	
	public static final String ID = "100";
	public static final String MT910_Putanja = "BMT910"+ID;
	public static final String MT900_Putanja = "BMT900"+ID;
	public static final String MT103_Putanja = "BMT103"+ID;
	public static final String MT102_Putanja = "BMT102"+ID;
	public static final String Racuni_Putanja = "BRacuni"+ID;

	public static final String CB = "http://www.project.xml/wsdl/CBwsdl";
	public static final String CBSERVICE = "CentralnaBankaService";
	public static final String CBPORT = "CentralnaBankaPort";
	public static final String CBURL = "http://localhost:8080/XWS-SOAP-CB/CentralnaBankaService?wsdl";
	
	private URL cbwsdl;
	private QName serviceName;
	private QName portName;
	private Service service;
	private CentralnaBanka cetralnaBanka;
	
	private List<FirmaRacun> racuni;
	TBanke banka;
	private static final Logger LOG = Logger.getLogger(FirmaBanciImpl.class
			.getName());

	/*
	 * (non-Javadoc)
	 * 
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

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * xml.project.wsdl.bwsdl.FirmaBanci#acceptMT910(xml.project.mt910.MT910
	 * mt910 )*
	 */
	public StatusCode acceptMT910(MT910 mt910) {
		LOG.info("Executing operation acceptMT910");
		System.out.println(mt910);
		StatusCode _return = new StatusCode();
		try {
			RESTUtil.objectToDB(MT910_Putanja, mt910.getIDPoruke(), mt910);
			MT103 mt103Temp = new MT103();
			// rtgs nalog
			mt103Temp = (MT103) RESTUtil.doUnmarshall("*", MT103_Putanja
					+ mt910.getIDPorukeNaloga(), mt103Temp);
			// String racunPoverioca =
			// mt103Temp.getBankaPoverilac().getObracunskiRacunBanke();
			BigDecimal iznos = mt103Temp.getIznos();
			// update racuna banke
			FirmaRacun racun = findFirmu(mt103Temp.getPrimalacPoverilac()
					.getRacun());
			if (racun != null) {
				racun.setRaspoloziviNovac(racun.getRaspoloziviNovac()
						.add(iznos));
				saveRacuni();
			}
			_return.setCode(200);
			_return.setMessage("OK");
			return _return;
		} catch (Exception ex) {
			_return.setCode(404);
			_return.setMessage("Something is kinda bad");
			return _return;
		}
	}

	@Override
	public StatusCode acceptMT900(MT900 mt900) {
		LOG.info("Executing operation acceptMT910");
        System.out.println(mt900);
        StatusCode _return = new StatusCode();
        try {
            RESTUtil.objectToDB(MT900_Putanja, mt900.getIDPoruke(), mt900);
            MT103 mt103Temp = new MT103();
            // rtgs nalog
            mt103Temp = (MT103) RESTUtil.doUnmarshall("*", MT103_Putanja + mt900.getIDPorukeNaloga(), mt103Temp);
            if(mt103Temp == null) {
            	MT102 mt102Temp = new MT102();
            	mt102Temp = (MT102) RESTUtil.doUnmarshall("*", MT102_Putanja + mt900.getIDPorukeNaloga(), mt102Temp);
            	for(TSequence seq : mt102Temp.getSekvenca()) {
            		TOsobe t1 = ((TOsobe) seq.getContent().get(1).getValue());
            		BigDecimal iznos = new BigDecimal(((Double) seq.getContent().get(4).getValue()));
            		FirmaRacun racun = findFirmu(t1.getRacun());
            		if(racun != null)
            		racun.setRaspoloziviNovac(racun.getRaspoloziviNovac().add(iznos));
            	}
            } else {
            	BigDecimal iznos = mt103Temp.getIznos();
            	FirmaRacun racun = findFirmu(mt103Temp.getPrimalacPoverilac().getRacun());
            	racun.setRaspoloziviNovac(racun.getRaspoloziviNovac().add(iznos));
            }
            saveRacuni();
            _return.setCode(200);
            _return.setMessage("OK");
            return _return;
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new RuntimeException(ex);
        }
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * xml.project.wsdl.bwsdl.FirmaBanci#acceptMT102(xml.project.mt102.MT102
	 * mt102 )*
	 */
	public StatusCode acceptMT102(MT102 mt102) {
		LOG.info("Executing operation acceptMT102");
		System.out.println(mt102);
		BankaChecker bc = new BankaChecker();
		StatusCode _return = new StatusCode();
		try {
			if (bc.checkTBanka(mt102.getBankaDuznik())
					&& bc.checkTBanka(mt102.getBankaPoverilac())) {
				_return.setCode(400);
				_return.setMessage("Bad Request");
				//throw new Exception("Invalid banks in transaction.");
			}
			if (bc.checkTBanka(mt102.getBankaDuznik())
					&& bc.checkTBanka(mt102.getBankaPoverilac())) {
				_return.setCode(400);
				_return.setMessage("Bad Request");
				//throw new Exception("Invalid participants in transaction.");
			}
			for (TSequence sequence : mt102.getSekvenca()) {
				FirmaRacun racun = findFirmu(((TOsobe) sequence.getContent()
						.get(1).getValue()).getRacun());
				if (racun == null) {
					_return.setCode(404);
					_return.setMessage("Not Found");
					//throw new Exception("Not existing firma.");
				}
			}
			RESTUtil.objectToDB(MT102_Putanja, mt102.getIDPoruke().toString(),
					mt102);
			_return.setCode(200);
			_return.setMessage("OK");
			return _return;
		} catch (Exception ex) {
			_return.setCode(400);
			_return.setMessage("Bad Request");
			return _return;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * xml.project.wsdl.bwsdl.FirmaBanci#acceptMT103(xml.project.mt103.MT103
	 * mt103 )*
	 */
	public StatusCode acceptMT103(MT103 mt103) {
		LOG.info("Executing operation acceptMT103");
		System.out.println(mt103);
		BankaChecker bc = new BankaChecker();
		StatusCode _return = new StatusCode();
		try {
			if (bc.checkTBanka(mt103.getBankaDuznik())
					&& bc.checkTBanka(mt103.getBankaPoverilac())) {
				_return.setCode(400);
				_return.setMessage("Bad Request");
				//throw new Exception("Invalid banks in transaction.");
			}
			if (bc.checkTOsoba(mt103.getDuznikNalogodavac())
					&& bc.checkTOsoba(mt103.getPrimalacPoverilac())) {
				_return.setCode(400);
				_return.setMessage("Bad Request");
				//throw new Exception("Invalid participants in transaction.");
			}
			FirmaRacun racun = findFirmu(mt103.getPrimalacPoverilac()
					.getRacun());
			if (racun == null) {
				_return.setCode(404);
				_return.setMessage("Not Found");
				//throw new Exception("Not existing firma.");
			}
			RESTUtil.objectToDB(MT103_Putanja, mt103.getIDPoruke().toString(),
					mt103);
			_return.setCode(200);
			_return.setMessage("OK");
			return _return;
		} catch (Exception ex) {
			_return.setCode(400);
			_return.setMessage("ERROR");
			return _return;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see xml.project.wsdl.bwsdl.FirmaBanci#primiNalog(xml.project.uplatnica.
	 * NalogZaPrenos nalogZaPrenos )*
	 */
	public StatusCode primiNalog(NalogZaPrenos nalogZaPrenos) {
		LOG.info("Executing operation primiNalog");
		System.out.println(nalogZaPrenos);
		StatusCode _return = new StatusCode();
		try {
			// Ista banka
			FirmaRacun racun = findFirmu(nalogZaPrenos.getPrimalacPoverilac()
					.getRacun());
			if (racun != null) {
				racun.setRaspoloziviNovac(racun.getRaspoloziviNovac().add(nalogZaPrenos.getIznos()));
				saveRacuni();
				// kreiraj izvjestaj, npr 103
				_return.setCode(200);
				_return.setMessage("OK");
				return _return;
			}
			// Preko CB
			if ((nalogZaPrenos.getIznos().doubleValue() >= 25000.00)
					|| (nalogZaPrenos.isHitno())) {
				// RTGS
			} else {
				// Clearing & Settlement
			}
			_return.setMessage("OK");
			return _return;
		} catch (java.lang.Exception ex) {
			_return.setCode(400);
			_return.setMessage("Bad Request");
			return _return;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * xml.project.wsdl.bwsdl.FirmaBanci#traziIzvod(xml.project.zahtev_za_izovd
	 * .Zahtev zaDatum )*
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

	public void init() {

		this.banka = new TBanke();
		this.racuni = new ArrayList<Racuni.FirmaRacun>();
		
		try {
			this.banka = new TBanke();
			this.racuni = new ArrayList<Racuni.FirmaRacun>();
			this.cbwsdl = new URL(CBURL);
			this.serviceName = new QName(CB, CBSERVICE);
			this.portName = new QName(CB, CBPORT);
			this.service = Service.create(this.cbwsdl, serviceName);
			this.cetralnaBanka = service.getPort(this.portName, CentralnaBanka.class);
			
			System.out.println(this.cetralnaBanka.TEST());
			System.out.println(this.cetralnaBanka.getSWIFT(ID));
			InputStream in = RESTUtil.retrieveResource("*", Racuni_Putanja,
					RequestMethod.GET);
			JAXBContext context = JAXBContext.newInstance(Racuni.class,
					Racuni.class);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			Marshaller marshaller = context.createMarshaller();
			// set optional properties
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			String xml = "";
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			for (String line; (line = br.readLine()) != null;) {
				xml = xml + line + "\n";
			}
			StringReader reader = new StringReader(xml);
			Racuni rac = (Racuni) unmarshaller.unmarshal(reader);
			for (FirmaRacun k : rac.getFirmaRacun()) {
				racuni.add(k);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public FirmaRacun findFirmu(String string) {
		BigInteger racunBroj = new BigInteger(string);
		for (FirmaRacun racun : racuni) {
			if (racun.getRacun().getBrojRacuna().equals(racunBroj)) {
				return racun;
			}
		}
		return null;
	}

	public void saveRacuni() {
		RESTUtil.objectToDB("//"+Racuni_Putanja, "", racuni);
	}

	public void createInitial() {
		try {
			RESTUtil.createSchema(MT102_Putanja);
			RESTUtil.createSchema(MT103_Putanja);
			RESTUtil.createSchema(MT900_Putanja);
			RESTUtil.createSchema(MT910_Putanja);
			RESTUtil.createSchema(Racuni_Putanja);

			Racuni rac = new Racuni();
			Racuni.FirmaRacun fr = new FirmaRacun();
			fr.setNaziv("Pejak");
			Racun r = new Racun();
			String deoRac = "1004567890123456";
			r.setBrojRacuna(new BigInteger(deoRac
					+ Validation.generateChecksum(deoRac)));
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(new Date());
			XMLGregorianCalendar date2 = DatatypeFactory.newInstance()
					.newXMLGregorianCalendar(c);
			r.setDatumRacuna(date2);
			fr.setRacun(r);
			fr.setRaspoloziviNovac(new BigDecimal(100));
			fr.setValuta("RSD");
			FirmaRacun fr2 = new FirmaRacun();
			fr2.setNaziv("Alex");
			Racun r2 = new Racun();
			String deoRac2 = "1004567891234567";
			r2.setBrojRacuna(new BigInteger(deoRac2
					+ Validation.generateChecksum(deoRac2)));
			r2.setDatumRacuna(date2);
			fr2.setRacun(r2);
			fr2.setRaspoloziviNovac(new BigDecimal(200));
			fr2.setValuta("RSD");
			ArrayList<FirmaRacun> racc = new ArrayList<FirmaRacun>();
			racc.add(fr);
			racc.add(fr2);
			rac.setFirmaRacuni(racc);
			RESTUtil.objectToDB("//" + Racuni_Putanja, "", rac);
//			Racuni temp = new Racuni();
//			temp = (Racuni) RESTUtil.doUnmarshall("*", Racuni_Putanja, temp);
//			System.out.println(temp + " " + temp.getFirmaRacun().size());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		FirmaBanciImpl imp = new FirmaBanciImpl();
		imp.createInitial();
		//imp.init();
	}

}
