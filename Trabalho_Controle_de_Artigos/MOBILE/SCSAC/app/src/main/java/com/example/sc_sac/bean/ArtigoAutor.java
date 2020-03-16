package com.example.sc_sac.bean;

import com.example.sc_sac.database.DatabaseBean;

public abstract class ArtigoAutor implements DatabaseBean {
    private Integer artigoAutorId;
    private Integer usuarioId;
    private Integer artigoId;
    private String email_autor;

    public ArtigoAutor() {}

    public Integer getArtigoAutorId() {
        return artigoAutorId;
    }

    public void setArtigoAutorId(Integer artigoAutorId) {
        this.artigoAutorId = artigoAutorId;
    }

    public Integer getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(Integer usuarioId) {
        this.usuarioId = usuarioId;
    }

    public Integer getArtigoId() {
        return artigoId;
    }

    public void setArtigoId(Integer artigoId) {
        this.artigoId = artigoId;
    }

    public String getEmail_autor() {
        return email_autor;
    }

    public void setEmail_autor(String email_autor) {
        this.email_autor = email_autor;
    }
}
