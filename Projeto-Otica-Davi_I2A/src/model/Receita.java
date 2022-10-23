/*
 * To change this license header; choose License Headers in Project Properties.
 * To change this template file; choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Objects;

/**
 *
 * @author DAVI
 */
public class Receita implements Serializable{

    private Integer idReceita;
    private double olhoDireitoEsfericoPerto;
    private double olhoDireitoEsfericoLonge;
    private double olhoEsquerdoEsfericoPerto;
    private double olhoEsquerdoEsfericoLonge;
    private double olhoEsquerdoCilindricoLonge;
    private double olhoEsquerdoCilindricoPerto;
    private double olhoDireitoCilindricoPerto;
    private double olhoDireitoCilindricoLonge;
    private double olhoDireitoEixoPerto;
    private double olhoEsquerdoEixoLonge;
    private double olhoDireitoEixoLonge;
    private double olhoEsquerdoEixoPerto;
    private double olhoEsquerdoDPPerto;
    private double olhoEsquerdoDPLonge;
    private double olhoDireitoDPLonge;
    private double olhoDireitoDPPerto;
    private double adicao;
    private Calendar dataVencimento = Calendar.getInstance();
    private String observacao;
    private Cliente cliente;

    public Receita() {
    }

    public Integer getIdReceita() {
        return idReceita;
    }

    public void setIdReceita(Integer idReceita) {
        this.idReceita = idReceita;
    }

    public double getOlhoDireitoEsfericoPerto() {
        return olhoDireitoEsfericoPerto;
    }

    public void setOlhoDireitoEsfericoPerto(double olhoDireitoEsfericoPerto) {
        this.olhoDireitoEsfericoPerto = olhoDireitoEsfericoPerto;
    }

    public double getOlhoDireitoEsfericoLonge() {
        return olhoDireitoEsfericoLonge;
    }

    public void setOlhoDireitoEsfericoLonge(double olhoDireitoEsfericoLonge) {
        this.olhoDireitoEsfericoLonge = olhoDireitoEsfericoLonge;
    }

    public double getOlhoEsquerdoEsfericoPerto() {
        return olhoEsquerdoEsfericoPerto;
    }

    public void setOlhoEsquerdoEsfericoPerto(double olhoEsquerdoEsfericoPerto) {
        this.olhoEsquerdoEsfericoPerto = olhoEsquerdoEsfericoPerto;
    }

    public double getOlhoEsquerdoEsfericoLonge() {
        return olhoEsquerdoEsfericoLonge;
    }

    public void setOlhoEsquerdoEsfericoLonge(double olhoEsquerdoEsfericoLonge) {
        this.olhoEsquerdoEsfericoLonge = olhoEsquerdoEsfericoLonge;
    }

    public double getOlhoEsquerdoCilindricoLonge() {
        return olhoEsquerdoCilindricoLonge;
    }

    public void setOlhoEsquerdoCilindricoLonge(double olhoEsquerdoCilindricoLonge) {
        this.olhoEsquerdoCilindricoLonge = olhoEsquerdoCilindricoLonge;
    }

    public double getOlhoEsquerdoCilindricoPerto() {
        return olhoEsquerdoCilindricoPerto;
    }

    public void setOlhoEsquerdoCilindricoPerto(double olhoEsquerdoCilindricoPerto) {
        this.olhoEsquerdoCilindricoPerto = olhoEsquerdoCilindricoPerto;
    }

    public double getOlhoDireitoCilindricoPerto() {
        return olhoDireitoCilindricoPerto;
    }

    public void setOlhoDireitoCilindricoPerto(double olhoDireitoCilindricoPerto) {
        this.olhoDireitoCilindricoPerto = olhoDireitoCilindricoPerto;
    }

    public double getOlhoDireitoCilindricoLonge() {
        return olhoDireitoCilindricoLonge;
    }

    public void setOlhoDireitoCilindricoLonge(double olhoDireitoCilindricoLonge) {
        this.olhoDireitoCilindricoLonge = olhoDireitoCilindricoLonge;
    }

    public double getOlhoDireitoEixoPerto() {
        return olhoDireitoEixoPerto;
    }

    public void setOlhoDireitoEixoPerto(double olhoDireitoEixoPerto) {
        this.olhoDireitoEixoPerto = olhoDireitoEixoPerto;
    }

    public double getOlhoEsquerdoEixoLonge() {
        return olhoEsquerdoEixoLonge;
    }

    public void setOlhoEsquerdoEixoLonge(double olhoEsquerdoEixoLonge) {
        this.olhoEsquerdoEixoLonge = olhoEsquerdoEixoLonge;
    }

    public double getOlhoDireitoEixoLonge() {
        return olhoDireitoEixoLonge;
    }

    public void setOlhoDireitoEixoLonge(double olhoDireitoEixoLonge) {
        this.olhoDireitoEixoLonge = olhoDireitoEixoLonge;
    }

    public double getOlhoEsquerdoEixoPerto() {
        return olhoEsquerdoEixoPerto;
    }

    public void setOlhoEsquerdoEixoPerto(double olhoEsquerdoEixoPerto) {
        this.olhoEsquerdoEixoPerto = olhoEsquerdoEixoPerto;
    }

    public double getOlhoEsquerdoDPPerto() {
        return olhoEsquerdoDPPerto;
    }

    public void setOlhoEsquerdoDPPerto(double olhoEsquerdoDPPerto) {
        this.olhoEsquerdoDPPerto = olhoEsquerdoDPPerto;
    }

    public double getOlhoEsquerdoDPLonge() {
        return olhoEsquerdoDPLonge;
    }

    public void setOlhoEsquerdoDPLonge(double olhoEsquerdoDPLonge) {
        this.olhoEsquerdoDPLonge = olhoEsquerdoDPLonge;
    }

    public double getOlhoDireitoDPLonge() {
        return olhoDireitoDPLonge;
    }

    public void setOlhoDireitoDPLonge(double olhoDireitoDPLonge) {
        this.olhoDireitoDPLonge = olhoDireitoDPLonge;
    }

    public double getOlhoDireitoDPPerto() {
        return olhoDireitoDPPerto;
    }

    public void setOlhoDireitoDPPerto(double olhoDireitoDPPerto) {
        this.olhoDireitoDPPerto = olhoDireitoDPPerto;
    }

    public double getAdicao() {
        return adicao;
    }

    public void setAdicao(double adicao) {
        this.adicao = adicao;
    }

    public Calendar getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Calendar dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.idReceita);
        return hash;
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Receita other = (Receita) obj;
        if (this.idReceita != other.idReceita) {
            return false;
        }
        return true;
    }


}
