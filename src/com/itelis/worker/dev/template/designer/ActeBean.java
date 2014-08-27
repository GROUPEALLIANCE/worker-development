package com.itelis.worker.dev.template.designer;

import java.io.Serializable;
import java.math.BigDecimal;

public class ActeBean implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String ACTE_NUM_DENT;
	private String CODE_CCAM;
	private BigDecimal  VALEUR_PRIX;
	private BigDecimal  REMBOURSEMENT_BSS;
	private BigDecimal  REMBOURSEMENT_SS;
	private BigDecimal  REMBOURSEMENT_RC1;
	private BigDecimal  REMBOURSEMENT_RC2;
	private BigDecimal  REMBOURSEMENT_RAC;
	private BigDecimal  RC_GLOBAL;
	private BigDecimal  RO_GLOBAL;
	private BigDecimal  RAC_GLOBAL;
	private BigDecimal  MONTANT_GLOBAL;
	private String CODEACTESS;
	private String LIBELLE;
	private String TYPE_CONTROLE;


	public String getCODE_CCAM() {
		return CODE_CCAM;
	}

	public void setCODE_CCAM(String cODE_CCAM) {
		CODE_CCAM = cODE_CCAM;
	}


	public String getLIBELLE() {
		return LIBELLE;
	}

	public void setLIBELLE(String lIBELLE) {
		LIBELLE = lIBELLE;
	}


	public String getCODEACTESS() {
		return CODEACTESS;
	}

	public void setCODEACTESS(String cODEACTESS) {
		CODEACTESS = cODEACTESS;
	}

	public String getTYPE_CONTROLE() {
		return TYPE_CONTROLE;
	}

	public void setTYPE_CONTROLE(String tYPE_CONTROLE) {
		TYPE_CONTROLE = tYPE_CONTROLE;
	}

	public BigDecimal getVALEUR_PRIX() {
		return VALEUR_PRIX;
	}

	public void setVALEUR_PRIX(BigDecimal vALEUR_PRIX) {
		VALEUR_PRIX = vALEUR_PRIX;
	}

	public BigDecimal getREMBOURSEMENT_BSS() {
		return REMBOURSEMENT_BSS;
	}

	public void setREMBOURSEMENT_BSS(BigDecimal rEMBOURSEMENT_BSS) {
		REMBOURSEMENT_BSS = rEMBOURSEMENT_BSS;
	}

	public BigDecimal getREMBOURSEMENT_SS() {
		return REMBOURSEMENT_SS;
	}

	public void setREMBOURSEMENT_SS(BigDecimal rEMBOURSEMENT_SS) {
		REMBOURSEMENT_SS = rEMBOURSEMENT_SS;
	}

	public BigDecimal getREMBOURSEMENT_RC1() {
		return REMBOURSEMENT_RC1;
	}

	public void setREMBOURSEMENT_RC1(BigDecimal rEMBOURSEMENT_RC1) {
		REMBOURSEMENT_RC1 = rEMBOURSEMENT_RC1;
	}

	public BigDecimal getREMBOURSEMENT_RC2() {
		return REMBOURSEMENT_RC2;
	}

	public void setREMBOURSEMENT_RC2(BigDecimal rEMBOURSEMENT_RC2) {
		REMBOURSEMENT_RC2 = rEMBOURSEMENT_RC2;
	}

	public BigDecimal getREMBOURSEMENT_RAC() {
		return REMBOURSEMENT_RAC;
	}

	public void setREMBOURSEMENT_RAC(BigDecimal rEMBOURSEMENT_RAC) {
		REMBOURSEMENT_RAC = rEMBOURSEMENT_RAC;
	}

	public BigDecimal getRC_GLOBAL() {
		return RC_GLOBAL;
	}

	public void setRC_GLOBAL(BigDecimal rC_GLOBAL) {
		RC_GLOBAL = rC_GLOBAL;
	}

	public BigDecimal getRO_GLOBAL() {
		return RO_GLOBAL;
	}

	public void setRO_GLOBAL(BigDecimal rO_GLOBAL) {
		RO_GLOBAL = rO_GLOBAL;
	}

	public BigDecimal getRAC_GLOBAL() {
		return RAC_GLOBAL;
	}

	public void setRAC_GLOBAL(BigDecimal rAC_GLOBAL) {
		RAC_GLOBAL = rAC_GLOBAL;
	}

	public BigDecimal getMONTANT_GLOBAL() {
		return MONTANT_GLOBAL;
	}

	public void setMONTANT_GLOBAL(BigDecimal mONTANT_GLOBAL) {
		MONTANT_GLOBAL = mONTANT_GLOBAL;
	}

	public String getACTE_NUM_DENT() {
		return ACTE_NUM_DENT;
	}

	public void setACTE_NUM_DENT(String aCTE_NUM_DENT) {
		ACTE_NUM_DENT = aCTE_NUM_DENT;
	}

}
