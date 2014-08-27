package com.itelis.worker.dev.arsbridge;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

import com.itelis.worker.dev.template.designer.ActeBean;


/**
 * 
 * @author echokodjeu
 *
 */
public class DataBean implements Serializable{

	private static final long serialVersionUID = 1L;
	

	public DataBean() {
		super();
	}
	
	//Constructeur

	public DataBean(String id,
			String operateur,
			String gestionnaire,
			String partenaire,
			String nom_ps, 
			String prenom_ps,
			String adresse_ps, 
			String firstName, 
			String lastName,
			String ddn,
			String date_devis,
			String date_reception,
			String num_dossier,
			String num_demande,
			String adresse,
			String finess,
			String statut,
			String demat,
			String statut_code,
			String code_type,
			String annulation,
			String codreg,
			String contrat,
			String duree,
			ArrayList<ActeBean> actesList) {
		super();
		
		this.setID(id);
		this.setOPERATEUR(operateur);
		this.setNOM_PS(nom_ps);
		this.setPRENOM_PS(prenom_ps);
		this.setADRESSE_PS(adresse_ps);
		this.setNOM(firstName);
		this.setPRENOM(lastName);
		this.setDDN(ddn);
		this.setDATE_DEVIS(date_devis);
		this.setDATERECEPTION(date_reception);
		this.setADRESSE(adresse);
		this.setNUMDOSSIER(num_dossier);
		this.setNUM_DEMANDE(num_demande);
		this.setFINESS(finess);
		this.setSTATUT_CODE(statut_code);
		this.setSTATUT(statut);
		this.setCODE_TYPE(code_type);
		this.setDEMAT(demat);
		this.setGESTIONNAIRE(gestionnaire);
		this.setPARTENAIRE(partenaire);
		this.setANNULATION(annulation);
		this.setRO(codreg);
		this.setCONTRAT(contrat);
		this.setDUREE(duree);
	    this.setActesList(actesList);
	}
 	   
	
	//ARS
    private String DUREE;
    private String GARANTIE;
    private String TEL_SERVICE;
	
	//OPERATEUR 
	private String OPERATEUR;
	private String GESTIONNAIRE;
	private String LOGO;
	
	//USER
	private String LOGIN_UTILISATEUR;
	
	//STATUT
	private String STATUT_CODE;
	private String ANNULATION;
	private String STATUT;
	private String STATUT_MOTIF;
	private String DEMAT;
	private String SATUT_DOSSIER_SUSPECT;

	
	//IDFLUX
	private String CODE_TYPE;
	private String DO_TYPE;
	private String NUMDOSSIER;
	private String DOSSIER_ID;
	private String DATE_DEVIS;
	private String DATERECEPTION;
	private String NUM_DEMANDE;
	private String NIVEAU_SERVICE;


	//BENEF
	private String PRENOM;
	private String NOM;
	private String CONTRAT;
	private String ADRESSE;
	private String DDN;
	private String RO;
	
    //ASSURE
	private String NOM_AS;
	private String PRENOM_AS;
	private String NUMDOSSIER_AS;
	private String NUMERO_AS;
	private String ADRESSE_AS;
		
	
	//PS
    private String ID;
    private String NOM_PS;
    private String PRENOM_PS;
	private String ENSEIGNE;
	private String RAISONSOCIALE;
	private String DESCRIPTION_PS;
	private String FAX;
	private String TEL;
	private String ADRESSE_PS;
	private String VILLE;
	private String FINESS;
	private String PARTENAIRE;
	
	//ACTES
    private ArrayList<ActeBean> actesList;

	


	public String getFAX() {
		return FAX;
	}

	public void setFAX(String fAX) {
		FAX = fAX;
	}

	public String getTEL() {
		return TEL;
	}

	public void setTEL(String tEL) {
		TEL = tEL;
	}

	public String getFINESS() {
		return FINESS;
	}

	public void setFINESS(String fINESS) {
		FINESS = fINESS;
	}



	public String getLOGIN_UTILISATEUR() {
		return LOGIN_UTILISATEUR;
	}



	public void setLOGIN_UTILISATEUR(String lOGIN_UTILISATEUR) {
		LOGIN_UTILISATEUR = lOGIN_UTILISATEUR;
	}



	public String getNIVEAU_SERVICE() {
		return NIVEAU_SERVICE;
	}



	public void setNIVEAU_SERVICE(String nIVEAU_SERVICE) {
		NIVEAU_SERVICE = nIVEAU_SERVICE;
	}



	public String getADRESSE() {
		return ADRESSE;
	}



	public void setADRESSE(String aDRESSE) {
		ADRESSE = aDRESSE;
	}





	public String getADRESSE_PS() {
		return ADRESSE_PS;
	}



	public void setADRESSE_PS(String aDRESSE_PS) {
		ADRESSE_PS = aDRESSE_PS;
	}




	public String getPRENOM() {
		return PRENOM;
	}



	public void setPRENOM(String prenom) {
		PRENOM = prenom;
	}



	public String getNOM() {
		return NOM;
	}



	public void setNOM(String nom) {
		NOM = nom;
	}

	public String getENSEIGNE() {
		return ENSEIGNE;
	}

	public void setENSEIGNE(String eNSEIGNE) {
		ENSEIGNE = eNSEIGNE;
	}

	public String getRAISONSOCIALE() {
		return RAISONSOCIALE;
	}

	public void setRAISONSOCIALE(String rAISONSOCIALE) {
		RAISONSOCIALE = rAISONSOCIALE;
	}



	public String getNUM_DEMANDE() {
		return NUM_DEMANDE;
	}

	public void setNUM_DEMANDE(String nUM_DEMANDE) {
		NUM_DEMANDE = nUM_DEMANDE;
	}


	public String getOPERATEUR() {
		return OPERATEUR;
	}

	public void setOPERATEUR(String oPERATEUR) {
		OPERATEUR = oPERATEUR;
	}

	public ArrayList<ActeBean> getActesList() {
		return actesList;
	}

	public void setActesList(ArrayList<ActeBean> actesList) {
		this.actesList = actesList;
	}

	public String getNOM_AS() {
		return NOM_AS;
	}

	public void setNOM_AS(String nOM_AS) {
		NOM_AS = nOM_AS;
	}

	public String getPRENOM_AS() {
		return PRENOM_AS;
	}

	public void setPRENOM_AS(String pRENOM_AS) {
		PRENOM_AS = pRENOM_AS;
	}


	public String getGESTIONNAIRE() {
		return GESTIONNAIRE;
	}

	public void setGESTIONNAIRE(String gESTIONNAIRE) {
		GESTIONNAIRE = gESTIONNAIRE;
	}

	public String getCODE_TYPE() {
		return CODE_TYPE;
	}

	public void setCODE_TYPE(String cODE_TYPE) {
		CODE_TYPE = cODE_TYPE;
	}

	public String getSTATUT() {
		return STATUT;
	}

	public void setSTATUT(String sTATUT) {
		STATUT = sTATUT;
	}

	public String getSTATUT_MOTIF() {
		return STATUT_MOTIF;
	}

	public void setSTATUT_MOTIF(String sTATUT_MOTIF) {
		STATUT_MOTIF = sTATUT_MOTIF;
	}

	public String getSTATUT_CODE() {
		return STATUT_CODE;
	}

	public void setSTATUT_CODE(String sTATUT_CODE) {
		STATUT_CODE = sTATUT_CODE;
	}

	public String getANNULATION() {
		return ANNULATION;
	}

	public void setANNULATION(String aNNULATION) {
		ANNULATION = aNNULATION;
	}

	public String getDEMAT() {
		return DEMAT;
	}

	public void setDEMAT(String dEMAT) {
		DEMAT = dEMAT;
	}

	public String getSATUT_DOSSIER_SUSPECT() {
		return SATUT_DOSSIER_SUSPECT;
	}

	public void setSATUT_DOSSIER_SUSPECT(String sATUT_DOSSIER_SUSPECT) {
		SATUT_DOSSIER_SUSPECT = sATUT_DOSSIER_SUSPECT;
	}

	public String getDUREE() {
		return DUREE;
	}

	public void setDUREE(String dUREE) {
		DUREE = dUREE;
	}

	public String getDDN() {
		return DDN;
	}

	public void setDDN(String dDN) {
		DDN = dDN;
	}

	public String getID() {
		return ID;
	}

	public void setID(String iD) {
		ID = iD;
	}

	public String getPARTENAIRE() {
		return PARTENAIRE;
	}

	public void setPARTENAIRE(String pARTENAIRE) {
		PARTENAIRE = pARTENAIRE;
	}

	public String getDO_TYPE() {
		return DO_TYPE;
	}

	public void setDO_TYPE(String dO_TYPE) {
		DO_TYPE = dO_TYPE;
	}

	public String getNUMDOSSIER() {
		return NUMDOSSIER;
	}

	public void setNUMDOSSIER(String nUMDOSSIER) {
		NUMDOSSIER = nUMDOSSIER;
	}

	public String getDATE_DEVIS() {
		return DATE_DEVIS;
	}

	public void setDATE_DEVIS(String dATE_DEVIS) {
		DATE_DEVIS = dATE_DEVIS;
	}

	public String getDATERECEPTION() {
		return DATERECEPTION;
	}

	public void setDATERECEPTION(String dATERECEPTION) {
		DATERECEPTION = dATERECEPTION;
	}

	public String getCONTRAT() {
		return CONTRAT;
	}

	public void setCONTRAT(String cONTRAT) {
		CONTRAT = cONTRAT;
	}


	public String getNOM_PS() {
		return NOM_PS;
	}

	public void setNOM_PS(String nOM_PS) {
		NOM_PS = nOM_PS;
	}

	public String getPRENOM_PS() {
		return PRENOM_PS;
	}

	public void setPRENOM_PS(String pRENOM_PS) {
		PRENOM_PS = pRENOM_PS;
	}

	public String getLOGO() {
		return LOGO;
	}

	public void setLOGO(String lOGO) {
		LOGO = lOGO;
	}

	public String getVILLE() {
		return VILLE;
	}

	public void setVILLE(String vILLE) {
		VILLE = vILLE;
	}

	public String getDOSSIER_ID() {
		return DOSSIER_ID;
	}

	public void setDOSSIER_ID(String dOSSIER_ID) {
		DOSSIER_ID = dOSSIER_ID;
	}

	public String getRO() {
		return RO;
	}

	public void setRO(String rO) {
		RO = rO;
	}

	public String getNUMDOSSIER_AS() {
		return NUMDOSSIER_AS;
	}

	public void setNUMDOSSIER_AS(String nUMDOSSIER_AS) {
		NUMDOSSIER_AS = nUMDOSSIER_AS;
	}

	public String getNUMERO_AS() {
		return NUMERO_AS;
	}

	public void setNUMERO_AS(String nUMERO_AS) {
		NUMERO_AS = nUMERO_AS;
	}

	public String getADRESSE_AS() {
		return ADRESSE_AS;
	}

	public void setADRESSE_AS(String aDRESSE_AS) {
		ADRESSE_AS = aDRESSE_AS;
	}

	public String getDESCRIPTION_PS() {
		return DESCRIPTION_PS;
	}

	public void setDESCRIPTION_PS(String dESCRIPTION_PS) {
		DESCRIPTION_PS = dESCRIPTION_PS;
	}

	public String getGARANTIE() {
		return GARANTIE;
	}

	public void setGARANTIE(String gARANTIE) {
		GARANTIE = gARANTIE;
	}

	public String getTEL_SERVICE() {
		return TEL_SERVICE;
	}

	public void setTEL_SERVICE(String tEL_SERVICE) {
		TEL_SERVICE = tEL_SERVICE;
	}


}
