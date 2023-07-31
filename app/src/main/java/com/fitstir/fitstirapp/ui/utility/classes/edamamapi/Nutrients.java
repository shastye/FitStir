package com.fitstir.fitstirapp.ui.utility.classes.edamamapi;

public class Nutrients {
    private int CA;
    private int CHOCDF;
    private int CHOCDF_NET;
    private int CHOLE;
    private int ENERC_KCAL;
    private int FAMS;
    private int FAPU;
    private int FASAT;
    private int FAT;
    private int FATRN;
    private int FE;
    private int FIBTG;
    private int FOLAC;
    private int FOLDFE;
    private int K;
    private int MG;
    private int NA;
    private int NIA;
    private int P;
    private int PROCNT;
    private int RIBF;
    private int SUGAR;
    private int SUGAR_ADDED;
    private int SUGAR_ALCOHOL;
    private int THIA;
    private int TOCPHA;
    private int VITA_RAE;
    private int VITB12;
    private int VITB6A;
    private int VITC;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof Nutrients)) {
            return false;
        } else {
            Nutrients nutrients = (Nutrients) o;
            return CA == nutrients.CA && CHOCDF == nutrients.CHOCDF && CHOCDF_NET == nutrients.CHOCDF_NET && CHOLE == nutrients.CHOLE && ENERC_KCAL == nutrients.ENERC_KCAL && FAMS == nutrients.FAMS && FAPU == nutrients.FAPU && FASAT == nutrients.FASAT && FAT == nutrients.FAT && FATRN == nutrients.FATRN && FE == nutrients.FE && FIBTG == nutrients.FIBTG && FOLAC == nutrients.FOLAC && FOLDFE == nutrients.FOLDFE && K == nutrients.K && MG == nutrients.MG && NA == nutrients.NA && NIA == nutrients.NIA && P == nutrients.P && PROCNT == nutrients.PROCNT && RIBF == nutrients.RIBF && SUGAR == nutrients.SUGAR && SUGAR_ADDED == nutrients.SUGAR_ADDED && SUGAR_ALCOHOL == nutrients.SUGAR_ALCOHOL && THIA == nutrients.THIA && TOCPHA == nutrients.TOCPHA && VITA_RAE == nutrients.VITA_RAE && VITB12 == nutrients.VITB12 && VITB6A == nutrients.VITB6A && VITC == nutrients.VITC && VITD == nutrients.VITD && VITK1 == nutrients.VITK1 && WATER == nutrients.WATER && ZN == nutrients.ZN;
        }
    }

    private int VITD;
    private int VITK1;
    private int WATER;
    private int ZN;

    public Nutrients() { }

    public int getCA() { return CA; }
    public int getCHOCDF() { return CHOCDF; }
    public int getCHOCDF_NET() { return CHOCDF_NET; }
    public int getCHOLE() { return CHOLE; }
    public int getENERC_KCAL() { return ENERC_KCAL; }
    public int getFAMS() { return FAMS; }
    public int getFAPU() { return FAPU; }
    public int getFASAT() { return FASAT; }
    public int getFAT() { return FAT; }
    public int getFATRN() { return FATRN; }
    public int getFE() { return FE; }
    public int getFIBTG() { return FIBTG; }
    public int getFOLAC() { return FOLAC; }
    public int getFOLDFE() { return FOLDFE; }
    public int getK() { return K; }
    public int getMG() { return MG; }
    public int getNA() { return NA; }
    public int getNIA() { return NIA; }
    public int getP() { return P; }
    public int getPROCNT() { return PROCNT; }
    public int getRIBF() { return RIBF; }
    public int getSUGAR() { return SUGAR; }
    public int getSUGAR_ADDED() { return SUGAR_ADDED; }
    public int getSUGAR_ALCOHOL() { return SUGAR_ALCOHOL; }
    public int getTHIA() { return THIA; }
    public int getTOCPHA() { return TOCPHA; }
    public int getVITA_RAE() { return VITA_RAE; }
    public int getVITB12() { return VITB12; }
    public int getVITB6A() { return VITB6A; }
    public int getVITC() { return VITC; }
    public int getVITD() { return VITD; }
    public int getVITK1() { return VITK1; }
    public int getWATER() { return WATER; }
    public int getZN() { return ZN; }

    public void setCA(int CA) { this.CA = CA; }
    public void setCHOCDF(int CHOCDF) { this.CHOCDF = CHOCDF; }
    public void setCHOCDF_NET(int CHOCDF_NET) { this.CHOCDF_NET = CHOCDF_NET; }
    public void setCHOLE(int CHOLE) { this.CHOLE = CHOLE; }
    public void setENERC_KCAL(int ENERC_KCAL) { this.ENERC_KCAL = ENERC_KCAL; }
    public void setFAMS(int FAMS) { this.FAMS = FAMS; }
    public void setFAPU(int FAPU) { this.FAPU = FAPU; }
    public void setFASAT(int FASAT) { this.FASAT = FASAT; }
    public void setFAT(int FAT) { this.FAT = FAT; }
    public void setFATRN(int FATRN) { this.FATRN = FATRN; }
    public void setFE(int FE) { this.FE = FE; }
    public void setFIBTG(int FIBTG) { this.FIBTG = FIBTG; }
    public void setFOLAC(int FOLAC) { this.FOLAC = FOLAC; }
    public void setFOLDFE(int FOLDFE) { this.FOLDFE = FOLDFE; }
    public void setK(int K) { this.K = K; }
    public void setMG(int MG) { this.MG = MG; }
    public void setNA(int NA) { this.NA = NA; }
    public void setNIA(int NIA) { this.NIA = NIA; }
    public void setP(int P) { this.P = P; }
    public void setPROCNT(int PROCNT) { this.PROCNT = PROCNT; }
    public void setRIBF(int RIBF) { this.RIBF = RIBF; }
    public void setSUGAR(int SUGAR) { this.SUGAR = SUGAR; }
    public void setSUGAR_ADDED(int SUGAR_ADDED) { this.SUGAR_ADDED = SUGAR_ADDED; }
    public void setSUGAR_ALCOHOL(int SUGAR_ALCOHOL) { this.SUGAR_ALCOHOL = SUGAR_ALCOHOL; }
    public void setTHIA(int THIA) { this.THIA = THIA; }
    public void setTOCPHA(int TOCPHA) { this.TOCPHA = TOCPHA; }
    public void setVITA_RAE(int VITA_RAE) { this.VITA_RAE = VITA_RAE; }
    public void setVITB12(int VITB12) { this.VITB12 = VITB12; }
    public void setVITB6A(int VITB6A) { this.VITB6A = VITB6A; }
    public void setVITC(int VITC) { this.VITC = VITC; }
    public void setVITD(int VITD) { this.VITD = VITD; }
    public void setVITK1(int VITK1) { this.VITK1 = VITK1; }
    public void setWATER(int WATER) { this.WATER = WATER; }
    public void setZN(int ZN) { this.ZN = ZN; }
}
