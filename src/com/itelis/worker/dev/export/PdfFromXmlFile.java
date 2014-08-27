package com.itelis.worker.dev.export;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

import org.apache.log4j.Logger;

import airbrake.AirbrakeNotice;
import airbrake.AirbrakeNoticeBuilder;
import airbrake.AirbrakeNotifier;

import com.itelis.worker.dev.arsbridge.ArsBridge;
import com.itelis.worker.dev.arsbridge.DataBean;
import com.itelis.worker.dev.arsbridge.DataBeanList;
import com.itelis.worker.dev.experteofeed.JesquePublisher;
import com.itelis.worker.dev.jesque.Job;
import com.remedy.arsys.api.ARException;
import com.remedy.arsys.api.Value;

import net.greghaines.jesque.Config;
import net.greghaines.jesque.ConfigBuilder;
import net.greghaines.jesque.worker.Worker;
import net.greghaines.jesque.worker.WorkerImpl;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;


/*
 * cree un fichier PDF  partant du .jrxml pour le template
 * et d'ars(ArsBridge) plus le wsdl Experteo(Experteofeed) pour donnees
 */

/**
 * 
 * PDF FROM XML 
 * 
 * @author echokodjeu
 *
 */


public class PdfFromXmlFile {

	/**
	 * 
	 * @param args
	 * @throws JRException
	 * @throws IOException
	 */

	static String wsdl="";
	static String queue="experteo-development";
	static ArsBridge arsbridge;
	static JesquePublisher jesquePublisher;
	private static final Logger logger = Logger.getLogger(PdfFromXmlFile.class);
	public static void main(String [] args) throws JRException , IOException, ARException{

		//Publisher
		jesquePublisher=new JesquePublisher();
		//Recuperation du flux
		ReportAction rpAct0=new ReportAction();
		queue=new String(rpAct0.queue);

		// Configuration
		final Config config = new ConfigBuilder().withJobPackage("com.itelis.worker.dev.jesque").withHost("192.168.8.30").withPort(6390).build();

		// Start a worker to run jobs from the queue
		final Worker worker = new WorkerImpl(config, Arrays.asList(queue), Arrays.asList(Job.class));


		final Thread workerThread = new Thread(worker);

		workerThread.start();

		// Normally, we'd just keep running but for demo purposes we'll just wait a few secs then shutdown
		try { Thread.sleep(100); } catch (Exception e){} // Give ourselves time to process
		worker.end();
		try { workerThread.join(); } catch (Exception e){ e.printStackTrace(); }


		//Recuperation du flux
		ReportAction rpAct=new ReportAction();
		wsdl=new String(rpAct.filename);


		String reportFileName ="";
		String outFileName = new String(wsdl);


		DataBeanList DataBeanList = new DataBeanList();
		ArrayList<DataBean> dataList = DataBeanList.getDataBeanList(wsdl);


		if (("Dentaire".equals(DataBeanList.getDataBean().getCODE_TYPE())
				&&("Accepté".equals(DataBeanList.getDataBean().getSTATUT())))){
			if(DataBeanList.getDataBean().getPARTENAIRE()!=null){
				if("RPDI".equals(DataBeanList.getDataBean().getPARTENAIRE().toString())
						||"RPII".equals(DataBeanList.getDataBean().getPARTENAIRE().toString())
						||"RPII et RPDI".equals(DataBeanList.getDataBean().getPARTENAIRE().toString())){

					// ARS Bridge DataBeanList
					arsbridge=new ArsBridge();

					Value fvalas=new Value("file:N:\\experteo-development\\".concat(outFileName).concat("as").concat(".pdf"));
					Value fvalps=new Value("file:N:\\experteo-development\\".concat(outFileName).concat("ps").concat(".pdf"));

					//TODO:Gestion de risques Flux xml
					String finess=new String("Non Renseigné");
					String con_reg=new String("Non Renseigné");
					String numdemande=new String("Non Renseigné");
					String statut=new String("Non Renseigné");
					String login=new String("Non Renseigné");
					String codetype=new String("Non Renseigné");
					String dossierid=new String("Non Renseigné");
					String nom=new String("Non Renseigné");
					String prenom=new String("Non Renseigné");


					try{
						con_reg = DataBeanList.getDataBean().getRO().toString();
						numdemande= DataBeanList.getDataBean().getNUM_DEMANDE().toString();
						statut =DataBeanList.getDataBean().getSTATUT().toString();
						login=DataBeanList.getDataBean().getLOGIN_UTILISATEUR().toString();
						codetype=DataBeanList.getDataBean().getCODE_TYPE().toString();
						dossierid=DataBeanList.getDataBean().getNUMDOSSIER().toString();
						nom=DataBeanList.getDataBean().getNOM().toString();
						prenom= DataBeanList.getDataBean().getPRENOM().toString();
						finess= DataBeanList.getDataBean().getID().toString();

					}
					catch(Throwable e ){
						System.out.println("Exception thrown  WSDL:" + e);
					}

					//Insertion DEMAT
					HashMap<Long,Value> mapping=new HashMap<Long,Value>();


					String typeExperteo=new String("");
					String adresse=new String("");
					String ville=new String("");
					String codepos=new String("");
					String ddnj=new String("");
					String ddnm=new String("");
					String ddna=new String("");

					if("DEVIS".equals(DataBeanList.getDataBean().getNIVEAU_SERVICE().toString())){typeExperteo="Devis";}
					if("DPEC".equals(DataBeanList.getDataBean().getNIVEAU_SERVICE().toString())){typeExperteo="PEC";}

					mapping.put(new Long(536870913), new Value(finess));
					mapping.put(new Long(536870914), new Value(nom));
					mapping.put(new Long(536870915), new Value(prenom));
					mapping.put(new Long(536870918), new Value(login));
					mapping.put(new Long(536871067), new Value(dossierid));
					mapping.put(new Long(536870916), new Value(DataBeanList.getDataBean().getDEMAT()));
					mapping.put(new Long(536870917), new Value("Intégration"));
					mapping.put(new Long(536871070), new Value(typeExperteo));
					mapping.put(new Long(536870920), new Value(DataBeanList.getDataBean().getANNULATION()));
					mapping.put(new Long(536870921), new Value(DataBeanList.getDataBean().getDDN()));
					mapping.put(new Long(536870922), new Value(ddnj));
					mapping.put(new Long(536870923), new Value(ddnm));
					mapping.put(new Long(536870924), new Value(ddna));
					mapping.put(new Long(536870927), new Value(codetype.toLowerCase()));
					mapping.put(new Long(536870925), new Value(DataBeanList.getDataBean().getCONTRAT()));
					mapping.put(new Long(536870929), new Value(statut));
					mapping.put(new Long(536870941), new Value(adresse));
					mapping.put(new Long(536870942), new Value(ville));
					mapping.put(new Long(536870943), new Value(codepos));

					arsbridge.createHashEntryItems("ITELIS:DEMAT",mapping);

					String operateur=new String("Non Renseigné");
					String numdossier=new String("Non Renseigné");
					String contratligne_id=new String("Non Renseigné");
					String partenairedentaire_id=new String("Non Renseigné");
					String amc_id=new String("Non Renseigné");
					String do_type=new String("Non Renseigné");
					String ars_dossier_id=new String("Non Renseigné");
					String ars_finess =new String("Non Renseigné");
					String raisonsociale=new String("Non Renseigné");

					String nomContrat=new String("Non Renseigné");
					String garantie_nom=new String("Non Renseigné");;
					String description_ps=new String("Non Renseigné");
					String datereception=new String("Non Renseigné");
					String datedevis=new String("Non Renseigné");

					String enseigne=new String("Non Renseigné");
					String tel_ps=new String("Non Renseigné");
					String fax=new String("Non Renseigné");

					SimpleDateFormat datereceptionFlag= new SimpleDateFormat("dd.MM.yyyy");
					datereceptionFlag.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));

					SimpleDateFormat datedevisFlag=new SimpleDateFormat("dd.MM.yyyy");
					datedevisFlag.setTimeZone(TimeZone.getTimeZone("Europe/Paris"));

					try{
						datereception= datereceptionFlag.format(new Date(new Long(arsbridge.getEntryItems("ITELIS:DO", new Long(536871059), numdemande))*1000));
						datedevis=datedevisFlag.format(new Date(new Long(arsbridge.getEntryItems("ITELIS:DO", new Long(536871058), numdemande))*1000));

						operateur=arsbridge.getEntryItems("ITELIS:DO", new Long(536871057), numdemande);
						numdossier=arsbridge.getEntryItems("ITELIS:DO", new Long(536870938), numdemande);
						ars_dossier_id = arsbridge.getEntryItems("ITELIS:DO", new Long(536870945), numdemande);
						contratligne_id= arsbridge.getEntryItems("ITELIS:DO", new Long(536871080), numdemande);
						partenairedentaire_id= arsbridge.getEntryItems("ITELIS:DO", new Long(536870940), numdemande);
						amc_id= arsbridge.getEntryItems("ITELIS:DO", new Long(536871080), numdemande);
						do_type=arsbridge.getEntryItems("ITELIS:DO", new Long(536870927), numdemande);
						ars_finess = arsbridge.getEntryItems("ITELIS:DO", new Long(536870913), numdemande);
						garantie_nom=arsbridge.getEntryItems("ITELIS:DO", new Long(536871081), numdemande);
						description_ps=arsbridge.getEntryItems("ITELIS:DO", new Long(536870955), numdemande);
						nomContrat=arsbridge.getEntryItems("ITELIS:DO", new Long(536871064), numdemande);
						raisonsociale=arsbridge.getEntryItems("ITELIS:DO", new Long(536870960), numdemande);
						enseigne=arsbridge.getEntryItems("ITELIS:DO", new Long(536870955), numdemande);
						tel_ps=arsbridge.getEntryItems("ITELIS:DO", new Long(536870956), numdemande);
						fax=arsbridge.getEntryItems("ITELIS:DO", new Long(536870959), numdemande);
					}
					catch(Throwable e ){

						AirbrakeNotice notice = new AirbrakeNoticeBuilder("YOUR_KEY_HERE", e, "env").newNotice();
						AirbrakeNotifier notifier = new AirbrakeNotifier();
						//notifier.notify(notice);
						System.out.println("Exception thrown  DO:" + e);
					}


					//String partenaireoptique_id= arsbridge.getEntryItems("ITELIS:DO", new Long(536870964), numdemande);
					//String demat_id= arsbridge.getEntryItems("ITELIS:DO", new Long(536871075), numdemande);

					String tel_service=new String("Non Renseigné");
					//			String ddnj=new String("Non Renseigné");
					//			String ddnm=new String("Non Renseigné");
					//			String ddna=new String("Non Renseigné");
					//			String adresse=new String("Non Renseigné");
					//			String ville=new String("Non Renseigné");
					//			String codepos=new String("Non Renseigné");
					String civiliteFlag=new String("Non Renseigné");
					String ddn_as=new String("Non Renseigné");
					String numero_as=new String("Non Renseigné");
					String nom_as=new String("Non Renseigné");
					String prenom_as=new String("Non Renseigné");
					String adresse_as=new String("Non Renseigné");
					String createur_dossier=new String("Non Renseigné");
					try{
						tel_service=arsbridge.getEntryItems("ITELIS:Dossier", new Long(536871015), ars_dossier_id);
						ddnj=arsbridge.getEntryItems("ITELIS:Dossier", new Long(536870932), ars_dossier_id);
						ddnm=arsbridge.getEntryItems("ITELIS:Dossier", new Long(536870933), ars_dossier_id);
						ddna=arsbridge.getEntryItems("ITELIS:Dossier", new Long(536870934), ars_dossier_id);
						adresse=arsbridge.getEntryItems("ITELIS:Dossier", new Long(536870951), ars_dossier_id);
						ville=arsbridge.getEntryItems("ITELIS:Dossier", new Long(536870967), ars_dossier_id);
						codepos=arsbridge.getEntryItems("ITELIS:Dossier", new Long(536870966), ars_dossier_id);
						civiliteFlag=arsbridge.getEntryItems("ITELIS:Dossier", new Long(536870964), ars_dossier_id);
						ddn_as= arsbridge.getEntryItems("ITELIS:Dossier", new Long(536871031), ars_dossier_id);
						numero_as= arsbridge.getEntryItems("ITELIS:Dossier", new Long(536870924), ars_dossier_id);
						nom_as= arsbridge.getEntryItems("ITELIS:Dossier", new Long(536870916), ars_dossier_id);
						prenom_as= arsbridge.getEntryItems("ITELIS:Dossier", new Long(536870919), ars_dossier_id);
						adresse_as= arsbridge.getEntryItems("ITELIS:Dossier", new Long(536870951), ars_dossier_id);
						createur_dossier= arsbridge.getEntryItems("ITELIS:Dossier", new Long(536870914), ars_dossier_id);

					}
					catch(Throwable  e){
						AirbrakeNotice notice = new AirbrakeNoticeBuilder("YOUR_KEY_HERE", e, "env").newNotice();
						AirbrakeNotifier notifier = new AirbrakeNotifier();
						//notifier.notify(notice);
						System.out.println("Exception thrown  Dossier:" + e);
					}
					String valid=new String("Non Renseigné");
					String amc=new String("Non Renseigné");
					String amcIDDNIS=new String("Non Renseigné");
					String amcLogin=new String("Non Renseigné");
					String amcMDP=new String("Non Renseigné");
					String amcPilote=new String("Non Renseigné");
					String amcDNIS=new String("Non Renseigné");
					String amcAdresse=new String("Non Renseigné");
					String amcCentre=new String("Non Renseigné");
					String amcCorrespondance=new String("Non Renseigné");
					String majDentaire=new String("Non Renseigné");
					try{
						//valid=arsbridge.getEntryItems("ITELIS:AMC", new Long(536870948), amc_id);
						//amc=arsbridge.getEntryItems("ITELIS:AMC", new Long(536870913), amc_id); 
						//amcIDDNIS=arsbridge.getEntryItems("ITELIS:AMC", new Long(536870930), amc_id); 
						//amcLogin=arsbridge.getEntryItems("ITELIS:AMC", new Long(536870914), amc_id);
						//amcMDP=arsbridge.getEntryItems("ITELIS:AMC", new Long(536870915), amc_id);
						//amcPilote=arsbridge.getEntryItems("ITELIS:AMC", new Long(536870916), amc_id); 
						//amcDNIS=arsbridge.getEntryItems("ITELIS:AMC", new Long(536870917), amc_id); 
						//String amcTel=arsbridge.getEntryItems("ITELIS:AMC", new Long(536870918), amc_id); 
						//String amcEmail=arsbridge.getEntryItems("ITELIS:AMC", new Long(536870919), amc_id); 
						//String amcConnexion=arsbridge.getEntryItems("ITELIS:AMC", new Long(536870920), amc_id);
						//String amcEmailType=arsbridge.getEntryItems("ITELIS:AMC", new Long(536870923), amc_id); 
						//amcAdresse=arsbridge.getEntryItems("ITELIS:AMC", new Long(536870921), amc_id);
						//amcCentre=arsbridge.getEntryItems("ITELIS:AMC", new Long(536870922), amc_id); 
						//amcCorrespondance=arsbridge.getEntryItems("ITELIS:AMC", new Long(536870927), amc_id);
						//majDentaire=arsbridge.getEntryItems("ITELIS:AMC", new Long(536870931), amc_id); 
						//String majAudio=arsbridge.getEntryItems("ITELIS:AMC", new Long(536870933), amc_id); 
						//String majOptique=arsbridge.getEntryItems("ITELIS:AMC", new Long(536870932), amc_id); 
					}
					catch(Throwable e){
						AirbrakeNotice notice = new AirbrakeNoticeBuilder("YOUR_KEY_HERE", e, "env").newNotice();
						AirbrakeNotifier notifier = new AirbrakeNotifier();
						//notifier.notify(notice);
						System.out.println("Exception thrown  AMC:" + e);
					}
					String cor_IDAMC=new String("Non Renseigné");
					String cor_ReferenceContrat=new String("Non Renseigné");
					String cor_Souscripteur= new String("Non Renseigné");
					String cor_NomContrat=new String("Non Renseigné");
					String cor_Groupement=new String("Non Renseigné");
					String cor_Logo=new String("Non Renseigné");

					try{
						//Cas Gras Savoie uniquement
						cor_IDAMC=arsbridge.getEntryItems("ITELIS:Correspondance", new Long(536870917), amc_id);
						cor_ReferenceContrat=arsbridge.getEntryItems("ITELIS:Correspondance", new Long(536870913), amc_id); 
						cor_Souscripteur=arsbridge.getEntryItems("ITELIS:Correspondance", new Long(536870914), amc_id);
						cor_NomContrat=arsbridge.getEntryItems("ITELIS:Correspondance", new Long(536870915), amc_id);
						cor_Groupement=arsbridge.getEntryItems("ITELIS:Correspondance", new Long(536870916), amc_id);
						cor_Logo=arsbridge.getEntryItems("ITELIS:Correspondance", new Long(536870919), amc_id);
						String cor_Telephone=arsbridge.getEntryItems("ITELIS:Correspondance", new Long(536870920), amc_id);
					}catch(Throwable  e){
						AirbrakeNotice notice = new AirbrakeNoticeBuilder("YOUR_KEY_HERE", e, "env").newNotice();
						AirbrakeNotifier notifier = new AirbrakeNotifier();
						notifier.notify(notice);
						System.out.println("Exception thrown  ITELIS:Correspondance:" + e);  
					}


					//String enseigne=new String("Non Renseigné");
					String adresse_ps=new String("Non Renseigné");
					//String tel_ps=new String("Non Renseigné");
					//String fax=new String("Non Renseigné");

					try{
						//enseigne=arsbridge.getEntryItems("ITELIS:PARTENAIRES", new Long(536870925), partenairedentaire_id);
						//raisonsociale=arsbridge.getEntryItems("ITELIS:PARTENAIRES", new Long(536870924),partenairedentaire_id);
						adresse_ps=arsbridge.getEntryItems("ITELIS:PARTENAIRES", new Long(536870920),partenairedentaire_id);
						//fax=arsbridge.getEntryItems("ITELIS:PARTENAIRES", new Long(536870922), partenairedentaire_id);
						//tel_ps= arsbridge.getEntryItems("ITELIS:PARTENAIRES", new Long(536870921),partenairedentaire_id);
						//			String email_ps= arsbridge.getEntryItems("ITELIS:PARTENAIRES", new Long(536870926),partenairedentaire_id);
						//			String adresse1_as= arsbridge.getEntryItems("ITELIS:PARTENAIRES", new Long(536870916),partenairedentaire_id);
						//			String adresse2_as= arsbridge.getEntryItems("ITELIS:PARTENAIRES", new Long(536870917), partenairedentaire_id);
						//			String ville_as=  arsbridge.getEntryItems("ITELIS:PARTENAIRES", new Long(536870919), partenairedentaire_id);
						//			String ville1_as= arsbridge.getEntryItems("ITELIS:PARTENAIRES", new Long(536870920), partenairedentaire_id);
					}catch(Throwable  e){
						AirbrakeNotice notice = new AirbrakeNoticeBuilder("YOUR_KEY_HERE", e, "env").newNotice();
						AirbrakeNotifier notifier = new AirbrakeNotifier();
						//notifier.notify(notice);
						System.out.println("Exception thrown  ITELIS:PARTENAIRES:" + e);  
					}


					String logo =null;
					String clause=new String("Non Renseigné");
					try{
						//Garantie ---- Correspondance pour +sieurs contrats concenant AMC
						//if(arsbridge.getEntryItems("ITELIS:Garantie", new Long(536870917), contratligne_id)!=null)
						//logo=new String(arsbridge.getEntryItems("ITELIS:Garantie", new Long(536870917), contratligne_id));
						//nomGarantie=arsbridge.getEntryItems("ITELIS:Garantie", new Long(8), contratligne_id);
						//clause=arsbridge.getEntryItems("ITELIS:Garantie", new Long(536870927), contratligne_id);
					}catch(Throwable  e){
						AirbrakeNotice notice = new AirbrakeNoticeBuilder("YOUR_KEY_HERE", e, "env").newNotice();
						AirbrakeNotifier notifier = new AirbrakeNotifier();
						//notifier.notify(notice);
						System.out.println("Exception thrown  ITELIS:Garantie:" + e);  
					}

					//Mapping
					String civilite_complet=new String("");
					String civilite=new String("");

					if(civiliteFlag.equals("0")) {civilite_complet="Monsieur";civilite="Mr";}
					if(civiliteFlag.equals("1")) {civilite_complet="Madame";civilite="Me";}
					if(civiliteFlag.equals("2")) {civilite_complet="Mademoiselle";civilite="Mlle";}


					if("DEVIS".equals(DataBeanList.getDataBean().getNIVEAU_SERVICE().toString())){reportFileName ="Devis.jrxml";}
					if("DPEC".equals(DataBeanList.getDataBean().getNIVEAU_SERVICE().toString())){reportFileName ="AvisDAF.jrxml";}

					// TODO:Test non corruption
					// MAJ URL et STATUT=En cours de traitement
					arsbridge.setEntryItems("ITELIS:DO", new Long(536870932),fvalas, numdemande);
					arsbridge.setEntryItems("ITELIS:DO", new Long(536871099),fvalps, numdemande);
					arsbridge.setEntryItems("ITELIS:DO", new Long(7),new Value("4"), numdemande);



					//Uniquement pour les parametres de mapping
					File reportFile = new File(wsdl);

					Map<String, Object>  hm = new HashMap<String, Object>();
					Map<String, Object>  hmAS = new HashMap<String, Object>();

					// Insertion dynamique des valeurs parametrees experteo dans le template hm 
					hm.put("OPERATEUR",operateur);
					hm.put("STATUT", statut);
					hm.put("CODE_TYPE", codetype);
					hm.put("GESTIONNAIRE", createur_dossier); // gestionnaire
					hm.put("DOSSIER_ID",dossierid);
					hm.put("DO_TYPE", do_type);
					hm.put("TEL_SERVICE",tel_service);
					hm.put("CONTRAT",nomContrat);
					hm.put("GARANTIE",garantie_nom );
					hm.put("RO", con_reg);
					hm.put("DATEDEVIS",datedevis);
					hm.put("DATERECEPTION",datereception);

					//Data AMC
					hm.put("DUREE", valid);

					//Data Partenaire
					hm.put("FINESS", finess);
					hm.put("NOM_PS",description_ps);
					hm.put("RAISONSOCIALE",raisonsociale);
					hm.put("DESCRIPTION_PS",enseigne);
					hm.put("ADRESSE_PS",adresse_ps);
					hm.put("TEL",tel_ps);
					hm.put("FAX", fax); 
					hm.put("LOGO",logo);//logo || corlogo
					//hm.put("CIVILITE_PS",civilite_ps);
					//hm.put("EMAIL_PS",email_ps);


					//Data Benef
					hm.put("NOM",nom);
					hm.put("PRENOM",prenom);
					hm.put("DDNJ",ddnj);
					hm.put("DDNM",ddnm);
					hm.put("DDNA",ddna);
					hm.put("NUMDOSSIER",numdossier);
					hm.put("ADRESSE",adresse);
					hm.put("VILLE",ville);
					hm.put("CODEPOS",codepos);
					hm.put("CIVILITE",civilite);
					hm.put("CIVILITE_COMPLET",civilite_complet);

					//Data Assure
					hm.put("NOM_AS", nom_as);
					hm.put("PRENOM_AS",prenom_as);
					hm.put("DDN_AS", ddn_as);
					hm.put("NUMERO_AS", numero_as);
					hm.put("NUMDOSSIER_AS",numdossier);
					hm.put("ADRESSE_AS", adresse_as);
					//hm.put("ADRESSE2_AS", adresse2_as);
					//hm.put("VILLE_AS",ville_as);
					//hm.put("VILLE1_AS", ville1_as);
					//hm.put("CIVILITE_AS",civilite_as);


					JRBeanCollectionDataSource beanColDataSource =
							new JRBeanCollectionDataSource(dataList);

					try
					{

						//Compilation sub
						if("AvisDAF.jrxml".equals(reportFileName)){
							JasperCompileManager.compileReportToFile("sub1AvisDAF.jrxml", "sub1AvisDAF.jasper");
							JasperCompileManager.compileReportToFile("sub2AvisDAF.jrxml", "sub2AvisDAF.jasper");
						}
						if("Devis.jrxml".equals(reportFileName))
							JasperCompileManager.compileReportToFile("subDevis.jrxml", "subDevis.jasper");

						//Compilation du jrxml
						JasperReport jasperReport = 
								JasperCompileManager.compileReport(reportFileName);

						//Fill Jasper data source
						//JasperPrint print = 
						//JasperFillManager.fillReport(jasperReport, hm, jrxmlds);
						JasperPrint printFileName=JasperFillManager.fillReport(jasperReport, hm, beanColDataSource);

						//TODO:Export multiple document:PS, Assuré en fonction AMC
						if ((printFileName != null)) {

							JRExporter exporter = new JRPdfExporter();

							//1-export PS
							exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,"\\\\itepart\\C$\\workers\\files\\experteo-development\\".concat(outFileName).concat("ps").concat(".pdf"));
							exporter.setParameter(JRExporterParameter.JASPER_PRINT,printFileName);

							exporter.exportReport();

						}
						if ((printFileName != null)) {

							JRExporter exporter = new JRPdfExporter();

							//2-export ASSURE
							exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME,"\\\\itepart\\C$\\workers\\files\\experteo-development\\".concat(outFileName).concat("as").concat(".pdf"));
							exporter.setParameter(JRExporterParameter.JASPER_PRINT,printFileName);

							exporter.exportReport();

						}

					}
					catch (JRException e)
					{
						e.printStackTrace();
						System.exit(1);
					}

					//TODO:MAJ STATUT=Validé ou MAJ affichage Erreur
					arsbridge.setEntryItems("ITELIS:DO", new Long(7),new Value("3"), numdemande);
					//Log out ARS
					arsbridge.deconnection();
				}
			}
		}else
			if(("Dentaire".equals(DataBeanList.getDataBean().getCODE_TYPE())
					&&("Refusé".equals(DataBeanList.getDataBean().getSTATUT()))))
			{System.out.println("Non traité");  }
			else{
				//Transfert via JesquePublisher
				jesquePublisher.publisher(wsdl);
			}

	}
}


