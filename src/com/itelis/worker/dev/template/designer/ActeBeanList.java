package com.itelis.worker.dev.template.designer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRXmlDataSource;
import net.sf.jasperreports.engine.design.JRDesignField;

/**Parser la balise Acte**/
public class ActeBeanList {
	
	public ArrayList<ActeBean> getActeBeanList(String wsdl) throws JRException {
		
		
		String wsdlFile=wsdl;
		ArrayList<ActeBean> acteBeanList;
		
		
		/**E0:Recuperation des donnees de l'XML: NUM_LIGNES**/
		JRXmlDataSource dataSourceLignes=fetchData(wsdlFile,"/DOSSIER/ACTES/NB_LIGNES");
		
		JRXmlDataSource subDs5 = dataSourceLignes.dataSource("/DOSSIER/ACTES");
	    JRDesignField nbLines = new JRDesignField();
		nbLines.setDescription("NB_LIGNES");
		nbLines.setValueClass(String.class);

		subDs5.next();
		String nblignes = (String) subDs5.getFieldValue(nbLines);
		/**Taille du tableau**/
		acteBeanList = new ArrayList<ActeBean>();//new ArrayList<ActeBean>(4);
		
		/**E1:Recuperation des donnees de l'XML: MONTANT_GLOBAL**/
		JRXmlDataSource dataSourcemTGlobal=fetchData(wsdlFile,"/DOSSIER/ACTES");
		
		JRXmlDataSource subDs4 = dataSourcemTGlobal.dataSource("/DOSSIER/ACTES");
		
		JRDesignField mTGlobal = new JRDesignField();
		mTGlobal.setDescription("MONTANT_GLOBAL");
		mTGlobal.setValueClass(String.class);

		subDs4.next();
		String mTGl = (String) subDs4.getFieldValue(mTGlobal);
		
		
		/**E2:Recuperation des donnees de l'XML: RO_GLOBAL**/
		JRXmlDataSource dataSourcerOGlobal=fetchData(wsdlFile,"/DOSSIER/ACTES");
		
		JRXmlDataSource subDs3 = dataSourcerOGlobal.dataSource("/DOSSIER/ACTES");
		JRDesignField rOGlobal = new JRDesignField();
		rOGlobal.setDescription("RO_GLOBAL");
		rOGlobal.setValueClass(String.class);

		subDs3.next();
		String rOGl = (String) subDs3.getFieldValue(rOGlobal);
		
		
		/**E3:Recuperation des donnees de l'XML: RC_GLOBAL**/
		JRXmlDataSource dataSourcerCGlobal=fetchData(wsdlFile,"/DOSSIER/ACTES");
		
		JRXmlDataSource subDs1 = dataSourcerCGlobal.dataSource("/DOSSIER/ACTES");
		JRDesignField rCGlobal = new JRDesignField();
		rCGlobal.setDescription("RC_GLOBAL");
		rCGlobal.setValueClass(String.class);

		subDs1.next();
		String rCGl = (String) subDs1.getFieldValue(rCGlobal);
		
		
		/**E4:Recuperation des donnees de l'XML: RAC_GLOBAL**/
		JRXmlDataSource dataSourceracGlobal=fetchData(wsdlFile,"/DOSSIER/ACTES");
		
		JRXmlDataSource subDs2 = dataSourceracGlobal.dataSource("/DOSSIER/ACTES");
		JRDesignField racGlobal = new JRDesignField();
		racGlobal.setDescription("RAC_GLOBAL");
		racGlobal.setValueClass(String.class);

		subDs2.next();
		String racGl = (String) subDs2.getFieldValue(racGlobal);
		
		
		/**E5:Recuperation des donnees de l'XML: ACTES**/
		JRXmlDataSource dataSourceActes=fetchData(wsdlFile,"/DOSSIER/ACTES/ACTE");
				    
		JRDesignField actes = new JRDesignField();
			          actes.setDescription("ACTES");
			         actes.setValueClass(String.class);
	          
		dataSourceActes.next();
		String vActes = (String) dataSourceActes.getFieldValue(actes);
		

		JRXmlDataSource subDs = dataSourceActes.dataSource("/DOSSIER/ACTES/ACTE");
		 try { 
			    
		   while(subDs.next()){ 
		JRDesignField numLine = new JRDesignField();
		numLine.setDescription("NUM_LIGNE");
		numLine.setValueClass(String.class);

		//subDs.next();
		String numline=(String)subDs.getFieldValue(numLine);
		int  nl= new Long(numline).intValue();
		

		JRDesignField codeCCAM = new JRDesignField();
		codeCCAM.setDescription("CODE_CCAM");
		codeCCAM.setValueClass(String.class);

		//subDs.next();
		String vccam1 = (String) subDs.getFieldValue(codeCCAM);
		
		JRDesignField codeNGAP = new JRDesignField();
		codeNGAP.setDescription("CODEACTESS");
		codeNGAP.setValueClass(String.class);

		//subDs.next();
		String vngap2=(String)subDs.getFieldValue(codeNGAP);
		
		JRDesignField libelle = new JRDesignField();
		libelle.setDescription("LIBELLE");
		libelle.setValueClass(String.class);

		//subDs.next();
		String lib = (String) subDs.getFieldValue(libelle);
		
		JRDesignField typeControle = new JRDesignField();
		typeControle.setDescription("TYPE_CONTROLE");
		typeControle.setValueClass(String.class);

		//subDs.next();
		String typeCtl = (String) subDs.getFieldValue(typeControle);
		
		
		JRDesignField rembRAC = new JRDesignField();
		rembRAC.setDescription("REMBOURSEMENT_RAC");
		rembRAC.setValueClass(String.class);

		//subDs.next();
		String rembRA=(String)subDs.getFieldValue(rembRAC);
		BigDecimal v7=new BigDecimal(rembRA);
		
		
		JRDesignField rembRC1 = new JRDesignField();
		rembRC1.setDescription("REMBOURSEMENT_RC1");
		rembRC1.setValueClass(String.class);

		//subDs.next();
		String rembrc1=(String)subDs.getFieldValue(rembRC1);
		BigDecimal v8=new BigDecimal(rembrc1);
		
		
		
		JRDesignField rembRC2 = new JRDesignField();
		rembRC2.setDescription("REMBOURSEMENT_RC2");
		rembRC2.setValueClass(String.class);

		//subDs.next();
		String rembrc2=(String)subDs.getFieldValue(rembRC2);
		BigDecimal v9=new BigDecimal(rembrc2);
		
		JRDesignField valPrix = new JRDesignField();
		valPrix.setDescription("VALEUR_PRIX");
		valPrix.setValueClass(BigDecimal.class);

		//subDs.next();
		BigDecimal vPrix=(BigDecimal)subDs.getFieldValue(valPrix);
		
		JRDesignField rembBSS = new JRDesignField();
		rembBSS.setDescription("REMBOURSEMENT_BSS");
		rembBSS.setValueClass(BigDecimal.class);

		//subDs.next();
		BigDecimal rbBSS=(BigDecimal)subDs.getFieldValue(rembBSS);
		
		
		JRDesignField rembSS = new JRDesignField();
		rembSS.setDescription("REMBOURSEMENT_SS");
		rembSS.setValueClass(BigDecimal.class);

		//subDs.next();
		BigDecimal rbSS=(BigDecimal)subDs.getFieldValue(rembSS);
		
		
		
				
		//Controle para
		String ctl="";
		String v1="";
		String v2="";
		BigDecimal divisor=new BigDecimal(100);
		if(vccam1==null){ v1="-";}else{v1=vccam1;}
		if(vngap2==null){ v2="-";}else{v1=vngap2;}
		if(("RPDI".equals(typeCtl))||("RPII".equals(typeCtl))){ctl="OUI";} else{ctl="NON";}
		
		//lib.substring(0, 16)
		acteBeanList.add(produce(v1,v2,lib,ctl,
				                 (new BigDecimal(rOGl)).divide(divisor),
				                 (new BigDecimal(rCGl)).divide(divisor),
				                 (new BigDecimal(racGl)).divide(divisor),
				                 v8.divide(divisor),v9.divide(divisor),v7.divide(divisor),
				                rbBSS.divide(divisor),rbSS.divide(divisor),vPrix.divide(divisor),
				                (new BigDecimal(mTGl)).divide(divisor)
				                ));
	
		 }
	   } catch (Exception ex) { 
	          ex.printStackTrace(); 
	    } 
		return acteBeanList;
		
	}

	
private ActeBean produce (String code_ccam, 
        String code_ngap,
        String libelle,
        String type_controle,
        BigDecimal  ro_global,
        BigDecimal  rc_global,
        BigDecimal  rac_global,
        BigDecimal  remboursement_rc1, 
        BigDecimal  remboursement_rc2,
        BigDecimal  remboursement_rac,
        BigDecimal  remboursement_bss,
        BigDecimal  remboursement_ss,
        BigDecimal  valeur_prix,
        BigDecimal  montant_global){
	
	
	ActeBean acteBean=new ActeBean();
	
	acteBean.setCODE_CCAM(code_ccam);
	acteBean.setCODEACTESS(code_ngap);
	acteBean.setLIBELLE(libelle);
	acteBean.setTYPE_CONTROLE(type_controle);
	acteBean.setRO_GLOBAL(ro_global);
	acteBean.setRC_GLOBAL(rc_global);
	acteBean.setRAC_GLOBAL(rac_global);
	acteBean.setREMBOURSEMENT_RC1(remboursement_rc1);
	acteBean.setREMBOURSEMENT_RC2(remboursement_rc2);
	acteBean.setREMBOURSEMENT_RAC(remboursement_rac);
	acteBean.setREMBOURSEMENT_BSS(remboursement_bss);
	acteBean.setREMBOURSEMENT_SS(remboursement_ss);
	acteBean.setVALEUR_PRIX(valeur_prix);
	acteBean.setMONTANT_GLOBAL(montant_global);
	
	return acteBean;
	
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
