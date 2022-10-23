/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author DAVI
 */
public class ItensDaCompra implements Serializable{

    private Integer idItensDaCompra;
    private double subTotal;
    private int quantidadeComprada;
    private Compra compra;
    private Produto produto;
    private double precoUnitario;

    public Integer getIdItensDaCompra() {
        return idItensDaCompra;
    }

    public void setIdItensDaCompra(Integer idItensDaCompra) {
        this.idItensDaCompra = idItensDaCompra;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public int getQuantidadeComprada() {
        return quantidadeComprada;
    }

    public void setQuantidadeComprada(int quantidadeComprada) {
        this.quantidadeComprada = quantidadeComprada;
    }

    public Compra getCompra() {
        return compra;
    }

    public void setCompra(Compra compra) {
        this.compra = compra;
    }

    public Produto getProduto() {
        return produto;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public double getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(double precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.idItensDaCompra);
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
        final ItensDaCompra other = (ItensDaCompra) obj;
        if (!Objects.equals(this.idItensDaCompra, other.idItensDaCompra)) {
            return false;
        }
        return true;
    }

}
