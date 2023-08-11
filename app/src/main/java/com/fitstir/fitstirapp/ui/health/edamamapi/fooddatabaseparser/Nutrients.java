package com.fitstir.fitstirapp.ui.health.edamamapi.fooddatabaseparser;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Nutrients {
    @JsonProperty("CA")
    private float CA;
    @JsonProperty("CHOCDF")
    private float CHOCDF;
    @JsonProperty("CHOCDF_NET")
    private float CHOCDF_NET;
    @JsonProperty("CHOLE")
    private float CHOLE;
    @JsonProperty("ENERC_KCAL")
    private float ENERC_KCAL;
    @JsonProperty("FAMS")
    private float FAMS;
    @JsonProperty("FAPU")
    private float FAPU;
    @JsonProperty("FASAT")
    private float FASAT;
    @JsonProperty("FAT")
    private float FAT;
    @JsonProperty("FATRN")
    private float FATRN;
    @JsonProperty("FE")
    private float FE;
    @JsonProperty("FIBTG")
    private float FIBTG;
    @JsonProperty("FOLAC")
    private float FOLAC;
    @JsonProperty("FOLDFE")
    private float FOLDFE;
    @JsonProperty("K")
    private float K;
    @JsonProperty("MG")
    private float MG;
    @JsonProperty("NA")
    private float NA;
    @JsonProperty("NIA")
    private float NIA;
    @JsonProperty("P")
    private float P;
    @JsonProperty("PROCNT")
    private float PROCNT;
    @JsonProperty("RIBF")
    private float RIBF;
    @JsonProperty("SUGAR")
    private float SUGAR;
    @JsonProperty("SUGAR_ADDED")
    private float SUGAR_ADDED;
    @JsonProperty("SUGAR_ALCOHOL")
    private float SUGAR_ALCOHOL;
    @JsonProperty("THIA")
    private float THIA;
    @JsonProperty("TOCPHA")
    private float TOCPHA;
    @JsonProperty("VITA_RAE")
    private float VITA_RAE;
    @JsonProperty("VITB12")
    private float VITB12;
    @JsonProperty("VITB6A")
    private float VITB6A;
    @JsonProperty("VITC")
    private float VITC;
    @JsonProperty("VITD")
    private float VITD;
    @JsonProperty("VITK1")
    private float VITK1;
    @JsonProperty("WATER")
    private float WATER;
    @JsonProperty("ZN")
    private float ZN;

    public Nutrients() { }

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

    @Override
    public String toString() {
        String string = "{" +
                "\"ENERC_KCAL\":" + ENERC_KCAL + "," +
                "\"PROCNT\":" + PROCNT + "," +
                "\"FAT\":" + FAT + "," +
                "\"CHOCDF\":" + CHOCDF + "," +
                "\"FIBTG\":" + FIBTG + ",";

        if (CA != 0) {
            string += "\"CA\":" + CA + ",";
        }
        if (CHOCDF_NET != 0) {
            string += "\"CHOCDF_NET\":" + CHOCDF_NET + ",";
        }
        if (CHOLE != 0) {
            string += "\"CHOLE\":" + CHOLE + ",";
        }
        if (FAMS != 0) {
            string += "\"FAMS\":" + FAMS + ",";
        }
        if (FAPU != 0) {
            string += "\"FAPU\":" + FAPU + ",";
        }
        if (FASAT != 0) {
            string += "\"FASAT\":" + FASAT + ",";
        }
        if (FATRN != 0) {
            string += "\"FATRN\":" + FATRN + ",";
        }
        if (FE != 0) {
            string += "\"FE\":" + FE + ",";
        }
        if (FOLDFE != 0) {
            string += "\"FOLDFE\":" + FOLDFE + ",";
        }
        if (K != 0) {
            string += "\"K\":" + K + ",";
        }
        if (MG != 0) {
            string += "\"MG\":" + MG + ",";
        }
        if (NA != 0) {
            string += "\"NA\":" + NA + ",";
        }
        if (NIA != 0) {
            string += "\"NIA\":" + NIA + ",";
        }
        if (P != 0) {
            string += "\"P\":" + P + ",";
        }
        if (RIBF != 0) {
            string += "\"RIBF\":" + RIBF + ",";
        }
        if (SUGAR != 0) {
            string += "\"SUGAR\":" + SUGAR + ",";
        }
        if (SUGAR_ADDED != 0) {
            string += "\"SUGAR_ADDED\":" + SUGAR_ADDED + ",";
        }
        if (SUGAR_ALCOHOL != 0) {
            string += "\"SUGAR_ALCOHOL\":" + SUGAR_ALCOHOL + ",";
        }
        if (THIA != 0) {
            string += "\"THIA\":" + THIA + ",";
        }
        if (TOCPHA != 0) {
            string += "\"TOCPHA\":" + TOCPHA + ",";
        }
        if (VITA_RAE != 0) {
            string += "\"VITA_RAE\":" + VITA_RAE + ",";
        }
        if (VITB12 != 0) {
            string += "\"VITB12\":" + VITB12 + ",";
        }
        if (VITB6A != 0) {
            string += "\"VITB6A\":" + VITB6A + ",";
        }
        if (VITC != 0) {
            string += "\"VITC\":" + VITC + ",";
        }
        if (VITD != 0) {
            string += "\"VITD\":" + VITD + ",";
        }
        if (WATER != 0) {
            string += "\"WATER\":" + WATER + ",";
        }
        if (ZN != 0) {
            string += "\"ZN\":" + ZN + ",";
        }

        string = string.substring(0, string.length() - 1);
        string += "}";

        return string;
    }

    public float getCA() { return CA; }
    public float getCHOCDF() { return CHOCDF; }
    public float getCHOCDF_NET() { return CHOCDF_NET; }
    public float getCHOLE() { return CHOLE; }
    public float getENERC_KCAL() { return ENERC_KCAL; }
    public float getFAMS() { return FAMS; }
    public float getFAPU() { return FAPU; }
    public float getFASAT() { return FASAT; }
    public float getFAT() { return FAT; }
    public float getFATRN() { return FATRN; }
    public float getFE() { return FE; }
    public float getFIBTG() { return FIBTG; }
    public float getFOLAC() { return FOLAC; }
    public float getFOLDFE() { return FOLDFE; }
    public float getK() { return K; }
    public float getMG() { return MG; }
    public float getNA() { return NA; }
    public float getNIA() { return NIA; }
    public float getP() { return P; }
    public float getPROCNT() { return PROCNT; }
    public float getRIBF() { return RIBF; }
    public float getSUGAR() { return SUGAR; }
    public float getSUGAR_ADDED() { return SUGAR_ADDED; }
    public float getSUGAR_ALCOHOL() { return SUGAR_ALCOHOL; }
    public float getTHIA() { return THIA; }
    public float getTOCPHA() { return TOCPHA; }
    public float getVITA_RAE() { return VITA_RAE; }
    public float getVITB12() { return VITB12; }
    public float getVITB6A() { return VITB6A; }
    public float getVITC() { return VITC; }
    public float getVITD() { return VITD; }
    public float getVITK1() { return VITK1; }
    public float getWATER() { return WATER; }
    public float getZN() { return ZN; }

    public void setCA(float CA) { this.CA = CA; }
    public void setCHOCDF(float CHOCDF) { this.CHOCDF = CHOCDF; }
    public void setCHOCDF_NET(float CHOCDF_NET) { this.CHOCDF_NET = CHOCDF_NET; }
    public void setCHOLE(float CHOLE) { this.CHOLE = CHOLE; }
    public void setENERC_KCAL(float ENERC_KCAL) { this.ENERC_KCAL = ENERC_KCAL; }
    public void setFAMS(float FAMS) { this.FAMS = FAMS; }
    public void setFAPU(float FAPU) { this.FAPU = FAPU; }
    public void setFASAT(float FASAT) { this.FASAT = FASAT; }
    public void setFAT(float FAT) { this.FAT = FAT; }
    public void setFATRN(float FATRN) { this.FATRN = FATRN; }
    public void setFE(float FE) { this.FE = FE; }
    public void setFIBTG(float FIBTG) { this.FIBTG = FIBTG; }
    public void setFOLAC(float FOLAC) { this.FOLAC = FOLAC; }
    public void setFOLDFE(float FOLDFE) { this.FOLDFE = FOLDFE; }
    public void setK(float K) { this.K = K; }
    public void setMG(float MG) { this.MG = MG; }
    public void setNA(float NA) { this.NA = NA; }
    public void setNIA(float NIA) { this.NIA = NIA; }
    public void setP(float P) { this.P = P; }
    public void setPROCNT(float PROCNT) { this.PROCNT = PROCNT; }
    public void setRIBF(float RIBF) { this.RIBF = RIBF; }
    public void setSUGAR(float SUGAR) { this.SUGAR = SUGAR; }
    public void setSUGAR_ADDED(float SUGAR_ADDED) { this.SUGAR_ADDED = SUGAR_ADDED; }
    public void setSUGAR_ALCOHOL(float SUGAR_ALCOHOL) { this.SUGAR_ALCOHOL = SUGAR_ALCOHOL; }
    public void setTHIA(float THIA) { this.THIA = THIA; }
    public void setTOCPHA(float TOCPHA) { this.TOCPHA = TOCPHA; }
    public void setVITA_RAE(float VITA_RAE) { this.VITA_RAE = VITA_RAE; }
    public void setVITB12(float VITB12) { this.VITB12 = VITB12; }
    public void setVITB6A(float VITB6A) { this.VITB6A = VITB6A; }
    public void setVITC(float VITC) { this.VITC = VITC; }
    public void setVITD(float VITD) { this.VITD = VITD; }
    public void setVITK1(float VITK1) { this.VITK1 = VITK1; }
    public void setWATER(float WATER) { this.WATER = WATER; }
    public void setZN(float ZN) { this.ZN = ZN; }
}
