/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.tcc.util;

import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Roberto
 */
 @XmlRootElement
public class JaxbBeanJson {
    private int codigo;
    private String descricao; 
     public JaxbBeanJson(){
         
     }

    public JaxbBeanJson(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
     
     
     
}
