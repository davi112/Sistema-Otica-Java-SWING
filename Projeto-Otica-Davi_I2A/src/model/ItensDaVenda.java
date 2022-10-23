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
public class ItensDaVenda implements Serializable{

    private Integer idItensDaVenda;
    private double subTotal;
    private int quantidadeVendida;
    private Venda venda;
    private Produto produto;
    private double precoUnitario;

    public ItensDaVenda() {
    }

    public Integer getIdItensDaVenda() {
        return idItensDaVenda;
    }

    public void setIdItensDaVenda(Integer idItensDaVenda) {
        this.idItensDaVenda = idItensDaVenda;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }

    public int getQuantidadeVendida() {
        return quantidadeVendida;
    }

    public void setQuantidadeVendida(int quantidadeVendida) {
        this.quantidadeVendida = quantidadeVendida;
    }

    public Venda getVenda() {
        return venda;
    }

    public void setVenda(Venda venda) {
        this.venda = venda;
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
        int hash = 3;
        hash = 73 * hash + Objects.hashCode(this.idItensDaVenda);
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
        final ItensDaVenda other = (ItensDaVenda) obj;
        if (!Objects.equals(this.idItensDaVenda, other.idItensDaVenda)) {
            return false;
        }
        return true;
    }


}
