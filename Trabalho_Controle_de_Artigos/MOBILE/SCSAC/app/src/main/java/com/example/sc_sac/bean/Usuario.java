package com.example.sc_sac.bean;

import com.example.sc_sac.database.DatabaseBean;

public abstract class Usuario implements DatabaseBean {
    private Integer    usuarioId;
    private String     usuarioNome;
    private String     usuarioEndereco;
    private String     usuarioTelefone;
    private String     usuarioEmail;
    private String     localTrabalho;
    private Boolean    isRevisor;
    private Boolean    isAutor;

    public Usuario() {}

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuarioNome() {
        return usuarioNome;
    }

    public void setUsuarioNome(String usuarioNome) {
        this.usuarioNome = usuarioNome;
    }

    public String getUsuarioEndereco() {
        return usuarioEndereco;
    }

    public void setUsuarioEndereco(String usuarioEndereco) {
        this.usuarioEndereco = usuarioEndereco;
    }

    public String getUsuarioTelefone() {
        return usuarioTelefone;
    }

    public void setUsuarioTelefone(String usuarioTelefone) {
        this.usuarioTelefone = usuarioTelefone;
    }

    public String getUsuarioEmail() {
        return usuarioEmail;
    }

    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }

    public String getLocalTrabalho() {
        return localTrabalho;
    }

    public void setLocalTrabalho(String localTrabalho) {
        this.localTrabalho = localTrabalho;
    }

    public Boolean getRevisor() {
        return isRevisor;
    }

    public void setRevisor(Boolean revisor) {
        isRevisor = revisor;
    }

    public Boolean getAutor() {
        return isAutor;
    }

    public void setAutor(Boolean autor) {
        isAutor = autor;
    }
}
