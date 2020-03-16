package com.example.sc_sac.bean;

import com.example.sc_sac.database.DatabaseBean;

public abstract class Artigo implements DatabaseBean {
    private Integer artigoId;
    private String  artigoNome;
    private String  artigoResumo;
    private String  artigoArquivo;
    private Integer artigoQuantidadeRevisores;
    private Float   artigoMedia;

    public Artigo() {}

    public Integer getArtigoId() {
        return artigoId;
    }

    public void setArtigoId(Integer artigoId) {
        this.artigoId = artigoId;
    }

    public String getArtigoNome() {
        return artigoNome;
    }

    public void setArtigoNome(String artigoNome) {
        this.artigoNome = artigoNome;
    }

    public String getArtigoResumo() {
        return artigoResumo;
    }

    public void setArtigoResumo(String artigoResumo) {
        this.artigoResumo = artigoResumo;
    }

    public String getArtigoArquivo() {
        return artigoArquivo;
    }

    public void setArtigoArquivo(String artigoArquivo) {
        this.artigoArquivo = artigoArquivo;
    }


    public Integer getArtigoQuantidadeRevisores() {
        return artigoQuantidadeRevisores;
    }

    public void setArtigoQuantidadeRevisores(Integer artigoQuantidadeRevisores) {
        this.artigoQuantidadeRevisores = artigoQuantidadeRevisores;
    }

    public Float getArtigoMedia() {
        return artigoMedia;
    }

    public void setArtigoMedia(Float artigoMedia) {
        this.artigoMedia = artigoMedia;
    }
}
