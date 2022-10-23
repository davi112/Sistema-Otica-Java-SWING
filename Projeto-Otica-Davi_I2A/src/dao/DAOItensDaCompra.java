/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import conecction.ConnectionFactory;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import model.Compra;
import model.ItensDaCompra;
import model.Produto;

/**
 *
 * @author DAVI
 */
public class DAOItensDaCompra {

    DAOCompra daoCompra = new DAOCompra();
    DAOProduto daoProduto = new DAOProduto();

    public ArrayList<ItensDaCompra> getLista() {
        String sql = "select * from itens_da_compra";
        ArrayList<ItensDaCompra> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ItensDaCompra i = new ItensDaCompra();
                i.setIdItensDaCompra(rs.getInt("idItem"));
                i.setCompra(daoCompra.localizar(rs.getInt("idCompra")));
                i.setProduto(daoProduto.localizar(rs.getInt("idProduto")));
                i.setQuantidadeComprada(rs.getInt("quantidadeComprada"));
                i.setSubTotal(rs.getDouble("subtotal"));
                i.setPrecoUnitario(daoProduto.localizar(rs.getInt("idProduto")).getPrecoVenda());
                lista.add(i);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return lista;
    }

    public boolean salvar(ItensDaCompra c) {
        if (c.getIdItensDaCompra() == null) {
            return incluir(c);
        } else {
            return alterar(c);
        }
    }

    public boolean incluir(ItensDaCompra i) {
        String sql = "insert into itens_da_compra (idCompra, idProduto, precoUnitario, quantidadeComprada,"
                + "subtotal) values (?,?,?,?,?)";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, i.getCompra().getIdCompra());
            stmt.setInt(2, i.getProduto().getIdProduto());
            stmt.setDouble(3, i.getPrecoUnitario());
            stmt.setInt(4, i.getQuantidadeComprada());
            stmt.setDouble(5, i.getSubTotal());

            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Item incluído com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Item não incluído!");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(ItensDaCompra i) {
        String sql = "update itens_da_compra set idCompra = ?, idProduto = ?, precoUnitario = ?, quantidadeComprada = ?,"
                + "subtotal = ? where idItem = ?";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, i.getCompra().getIdCompra());
            stmt.setInt(2, i.getProduto().getIdProduto());
            stmt.setDouble(3, i.getPrecoUnitario());
            stmt.setInt(4, i.getQuantidadeComprada());
            stmt.setDouble(5, i.getSubTotal());
            stmt.setInt(6, i.getIdItensDaCompra());

            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Item alterado com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Item não alterado!");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }

    }

    //Método que remove um item de compra e volta o valor do estoque do produto
    //para a quantidade que estava antes do item ser adicionado a compra
    public boolean remover(ItensDaCompra i) {
        String sql = "delete from itens_da_compra where idItem = ?";
        String sql1 = "update produto set estoque = "
                + "produto.estoque - ? where idProduto = ?";
        try {

            Produto auxProduto = i.getProduto();
            int quantidadeAux = i.getQuantidadeComprada();
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, i.getIdItensDaCompra());

            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Item excluúdo com sucesso!");
                PreparedStatement stmt1 = ConnectionFactory.getPreparedStatement(sql1);
                stmt1.setInt(1, quantidadeAux);
                stmt1.setInt(2, auxProduto.getIdProduto());
                stmt1.executeUpdate();
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Item não excluído!");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }

    //Método que filtra os itens por compra
    public ArrayList<ItensDaCompra> getItensPorCompra(Compra compra) {
        String sql = "select i.* from itens_da_compra i, Compra c where i.idCompra = c.idCompra and c.idCompra = ?";
        ArrayList<ItensDaCompra> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, compra.getIdCompra());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ItensDaCompra i = new ItensDaCompra();
                i.setIdItensDaCompra(rs.getInt("idItem"));
                i.setCompra(daoCompra.localizar(rs.getInt("idCompra")));
                i.setProduto(daoProduto.localizar(rs.getInt("idProduto")));
                i.setQuantidadeComprada(rs.getInt("quantidadeComprada"));
                i.setPrecoUnitario(daoProduto.localizar(rs.getInt("idProduto")).getPrecoVenda());
                i.setSubTotal(rs.getDouble("subtotal"));
                lista.add(i);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return lista;
    }

}
