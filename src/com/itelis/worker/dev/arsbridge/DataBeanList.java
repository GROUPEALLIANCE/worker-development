package com.itelis.worker.dev.arsbridge;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.itelis.worker.dev.experteofeed.JRXMLDataSource;
import com.itelis.worker.dev.template.designer.ActeBean;
import com.itelis.worker.dev.template.designer.ActeBeanList;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.design.JRDesignField;

/**
 * 
 * liste alimentee par des donneees d'ARS et Experteo
 * 
 * @author echokodjeu
 *
 */

public class DataBeanList {

	private DataBean dataBean;
	

	public DataBean getDataBean() {
		return dataBean;
	}

	public void setDataBean(DataBean dataBean) {
		this.dataBean = dataBean;
	}

	public ArrayList<DataBean> getDataBeanList(String wsdl) throws JRException {
		/**Debut parsing:BENEF, PS, IDFlux**/

		String wsdlFile=wsdl;
		
		/**E1:Recuperation des donnees de l'XML: BENEF**/
		JRXmlDataSource dataSourceBenef=fetchData(wsdlFile,"/DOSSIER/BENEF");
		JRDesignField benef = new JRDesignField();
		benef.setDescription("BENEF");
		benef.setValueClass(String.class);

		dataSourceBenef.next();
		String vBenef = (String) dataSourceBenef.getFieldValue(benef);
		

		JRXmlDataSource subDs = dataSourceBenef.dataSource("/DOSSIER/BENEF");

		JRDesignField nom = new JRDesignField();
		nom.setDescription("NOM");
		nom.setValueClass(String.class);

		subDs.next();
		String v1 = (String) subDs.getFieldValue(nom);
		

		JRDesignField prenom = new JRDesignField();
		prenom.setDescription("PRENOM");
		prenom.setValueClass(String.class);

		subDs.next();
		String v2 = (String) subDs.getFieldValue(prenom);
		
		JRDesignField contrat = new JRDesignField();
		contrat.setDescription("CONTRAT");
		contrat.setValueClass(String.class);

		subDs.next();
		String v93 = (String) subDs.getFieldValue(contrat);
		
		
		JRDesignField ddn = new JRDesignField();
		ddn.setDescription("DDN");
		ddn.setValueClass(String.class);

		subDs.next();
		String dte=(String)subDs.getFieldValue(ddn);
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
		
		
		
		JRDesignField codreg = new JRDesignField();
		codreg.setDescription("CODREG");
		codreg.setValueClass(String.class);

		subDs.next();
		String codre=(String)subDs.getFieldValue(codreg);
	    if("0".equals(codre)){ codre=new String("Régime général");}
	    else if("1".equals(codre)) {codre=new String("Régime Alsace-Moselle");}
	    else codre=new String("Non Renseigné");
	 
	    
	    
		/**E2:Recuperation des donnees de l'XML: PS*/
		JRXmlDataSource dataSourcePs=fetchData(wsdlFile,"/DOSSIER/PS");
		
		JRDesignField ps = new JRDesignField();
		ps.setDescription("PS");
		ps.setValueClass(String.class);

		dataSourcePs.next();
		String vPs = (String) dataSourcePs.getFieldValue(ps);
		

		JRXmlDataSource subDsPs = dataSourcePs.dataSource("/DOSSIER/PS");

		JRDesignField enseigne = new JRDesignField();
		enseigne.setDescription("NOM");
		enseigne.setValueClass(String.class);

		subDsPs.next();
		String v3 = (String) subDsPs.getFieldValue(enseigne);
		

		JRDesignField raisonSo = new JRDesignField();
		raisonSo.setDescription("PRENOM");
		raisonSo.setValueClass(String.class);

		subDsPs.next();
		String v4 = (String) subDsPs.getFieldValue(raisonSo);
		
		JRDesignField idPs = new JRDesignField();
		idPs.setDescription("ID");
		idPs.setValueClass(String.class);

		subDsPs.next();
		String v9 = (String) subDsPs.getFieldValue(idPs);
		
		JRDesignField partenaire = new JRDesignField();
		partenaire.setDescription("PARTENAIRE");
		partenaire.setValueClass(String.class);

		subDsPs.next();
		String v20 = (String) subDsPs.getFieldValue(partenaire);
	

		
		/**E3:Recuperation des donnees de l'XML:IDFlux */
		JRXmlDataSource dataSourceIdFlux=fetchData(wsdlFile,"/DOSSIER/IDFLUX");
		
		JRDesignField idflux = new JRDesignField();
		idflux.setDescription("IDFLUX");
		idflux.setValueClass(String.class);

		dataSourceIdFlux.next();
		String vIdFlux = (String) dataSourceIdFlux.getFieldValue(idflux);
		
		JRXmlDataSource subDsIdFlux = dataSourceIdFlux.dataSource("/DOSSIER/IDFLUX");

		JRDesignField nomNumDossier = new JRDesignField();
		nomNumDossier.setDescription("NUMDOSSIER");
		nomNumDossier.setValueClass(String.class);

		subDsIdFlux.next();
		String v6 = (String) subDsIdFlux.getFieldValue(nomNumDossier);
		
		JRDesignField niveauService = new JRDesignField();
		niveauService.setDescription("NIVEAU_SERVICE");
		niveauService.setValueClass(String.class);

		subDsIdFlux.next();
		String v5 = (String) subDsIdFlux.getFieldValue(niveauService);
		
		JRDesignField numDemande = new JRDesignField();
		numDemande.setDescription("NUM_DEMANDE");
		numDemande.setValueClass(String.class);

		subDsIdFlux.next();
		String v8 = (String) subDsIdFlux.getFieldValue(numDemande);
		
		JRDesignField codetype = new JRDesignField();
		codetype.setDescription("CODE_TYPE");
		codetype.setValueClass(String.class);

		subDsIdFlux.next();
		String v15 = (String) subDsIdFlux.getFieldValue(codetype);
		
		/**E4:Recuperation des donneees de l'XML:USER */
		JRXmlDataSource dataSourceUser=fetchData(wsdlFile,"/DOSSIER/USER");
		
		JRDesignField loginuser = new JRDesignField();
		loginuser.setDescription("USER");
		loginuser.setValueClass(String.class);

		dataSourceUser.next();
		String vUser = (String) dataSourceUser.getFieldValue(loginuser);
		
		JRXmlDataSource subDsUser = dataSourceUser.dataSource("/DOSSIER/USER");

		JRDesignField user = new JRDesignField();
		user.setDescription("LOGIN_UTILISATEUR");
		user.setValueClass(String.class);

		subDsUser.next();
		String v12 = (String) subDsUser.getFieldValue(user);
		
		/**E5:Recuperation des donneees de l'XML:STATUT */
		JRXmlDataSource dataSourceStatut=fetchData(wsdlFile,"/DOSSIER/STATUT");
		
		JRDesignField statut = new JRDesignField();
		statut.setDescription("STATUT");
		statut.setValueClass(String.class);

		dataSourceStatut.next();
		String vStatut = (String) dataSourceUser.getFieldValue(statut);
		
		JRXmlDataSource subDsStatut = dataSourceStatut.dataSource("/DOSSIER/STATUT");

		JRDesignField stat = new JRDesignField();
		stat.setDescription("STATUT");
		stat.setValueClass(String.class);

		subDsStatut.next();
		String v10 = (String) subDsStatut.getFieldValue(stat);
		
		
		JRDesignField stat_cod = new JRDesignField();
		stat_cod.setDescription("STATUT_CODE");
		stat_cod.setValueClass(String.class);

		subDsStatut.next();
		String v99 = (String) subDsStatut.getFieldValue(stat_cod);
		
		JRDesignField annulation = new JRDesignField();
		annulation.setDescription("ANNULATION");
		annulation.setValueClass(String.class);

		subDsStatut.next();
		String v98 = (String) subDsStatut.getFieldValue(annulation);
		
		JRDesignField demat = new JRDesignField();
		demat.setDescription("DEMAT");
		demat.setValueClass(String.class);

		subDsStatut.next();
		String v97 = (String) subDsStatut.getFieldValue(demat);
		
		JRDesignField suspect = new JRDesignField();
		suspect.setDescription("SATUT_DOSSIER_SUSPECT");
		suspect.setValueClass(String.class);

		subDsStatut.next();
		String v96 = (String) subDsStatut.getFieldValue(suspect);
	

	
		
        /**Fin parsing**/

		/**Recuperation des champs parser**/
		
		dataBean = new DataBean();
				
		dataBean.setNOM_PS(v3);
		
		dataBean.setPRENOM_PS(v4);
				
		dataBean.setNOM(v1);
		
		dataBean.setPRENOM(v2);
			
		dataBean.setNUMDOSSIER(v6);
		
		dataBean.setNUM_DEMANDE(v8);
		
		dataBean.setID(v9);
		
		dataBean.setLOGIN_UTILISATEUR(v12);
		
		dataBean.setSTATUT(v10);
		
		dataBean.setCODE_TYPE(v15);
		
		dataBean.setNIVEAU_SERVICE(v5);
		
		dataBean.setSTATUT_CODE(v99);
		
		dataBean.setANNULATION(v98);
		
		dataBean.setDEMAT(v97);
		
		dataBean.setSATUT_DOSSIER_SUSPECT(v96);
		
		dataBean.setCONTRAT(v93);
		
		dataBean.setRO(codre);
		
		dataBean.setDDN(dte);
		
		dataBean.setPARTENAIRE(v20);
		
		//Setter actesList dans dataBean
		
		ActeBeanList ActeBeanList = new ActeBeanList();
		ArrayList<ActeBean> actesList = ActeBeanList.getActeBeanList(wsdl);
		dataBean.setActesList(actesList);
				
		//Add datBean dans dataList
		ArrayList<DataBean> dataList = new ArrayList<DataBean>();
		dataList.add(dataBean);
			
		return dataList;

	}


	/**
	 * 
	 * @return
	 * @throws JRException
	 */
	private JRXmlDataSource fetchData(String wsdlFile, String xpath) throws JRException{
		
		JRXmlDataSource dataSource=new JRXmlDataSource(wsdlFile, xpath);
		return dataSource;
	}


}
