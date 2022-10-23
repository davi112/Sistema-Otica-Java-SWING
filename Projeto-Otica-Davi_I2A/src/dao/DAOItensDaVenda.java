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
import model.ItensDaVenda;
import model.Produto;
import model.Venda;

/**
 *
 * @author DAVI
 */
public class DAOItensDaVenda {

    DAOVenda daoVenda = new DAOVenda();
    DAOProduto daoProduto = new DAOProduto();

    public ArrayList<ItensDaVenda> getLista() {
        String sql = "select * from itens_da_venda";
        ArrayList<ItensDaVenda> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ItensDaVenda i = new ItensDaVenda();
                i.setIdItensDaVenda(rs.getInt("idItem"));
                i.setVenda(daoVenda.localizar(rs.getInt("idVenda")));
                i.setProduto(daoProduto.localizar(rs.getInt("idProduto")));
                i.setQuantidadeVendida(rs.getInt("quantidadeVendida"));
                i.setSubTotal(rs.getDouble("subtotal"));
                i.setPrecoUnitario(daoProduto.localizar(rs.getInt("idProduto")).getPrecoVenda());
                lista.add(i);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return lista;
    }

    public boolean salvar(ItensDaVenda i) {
        if (i.getIdItensDaVenda() == null) {
            return incluir(i);
        } else {
            return alterar(i);
        }
    }

    private boolean incluir(ItensDaVenda i) {
        String sql = "insert into itens_da_venda (idVenda, idProduto, precoUnitario, quantidadeVendida,"
                + "subtotal) values (?,?,?,?,?)";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, i.getVenda().getIdVenda());
            stmt.setInt(2, i.getProduto().getIdProduto());
            stmt.setDouble(3, i.getPrecoUnitario());
            stmt.setInt(4, i.getQuantidadeVendida());
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

    private boolean alterar(ItensDaVenda i) {
        String sql = "update itens_da_venda set idVenda = ?, idProduto = ?, precoUnitario = ?, quantidadeVendida = ?,"
                + "subtotal = ? where idItem = ?";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, i.getVenda().getIdVenda());
            stmt.setInt(2, i.getProduto().getIdProduto());
            stmt.setDouble(3, i.getPrecoUnitario());
            stmt.setInt(4, i.getQuantidadeVendida());
            stmt.setDouble(5, i.getSubTotal());
            stmt.setInt(6, i.getIdItensDaVenda());

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
    
    //Método que remove um item de venda e volta o valor do estoque do produto
    //para a quantidade que estava antes do item ser adicionado a venda
    public boolean remover(ItensDaVenda i) {
        String sql = "delete from itens_da_venda where idItem = ?";
        String sql1 = "update produto set estoque = "
                + "produto.estoque + ? where idProduto = ?";
        try {

            Produto auxProduto = i.getProduto();
            int quantidadeAux = i.getQuantidadeVendida();
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, i.getIdItensDaVenda());

            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Item excluído com sucesso!");
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

    public ArrayList<ItensDaVenda> getItensPorVenda(Venda venda) {
        String sql = "select i.* from itens_da_venda i, Venda v where i.idVenda = v.idVenda and v.idVenda = ?";
        ArrayList<ItensDaVenda> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, venda.getIdVenda());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ItensDaVenda i = new ItensDaVenda();
                i.setIdItensDaVenda(rs.getInt("idItem"));
                i.setVenda(daoVenda.localizar(rs.getInt("idVenda")));
                i.setProduto(daoProduto.localizar(rs.getInt("idProduto")));
                i.setQuantidadeVendida(rs.getInt("quantidadeVendida"));
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
