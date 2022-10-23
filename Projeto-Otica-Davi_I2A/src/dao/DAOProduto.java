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
import model.Categoria;
import model.Compra;
import model.Fornecedor;
import model.Produto;
import model.Venda;

/**
 *
 * @author DAVI
 */
public class DAOProduto {

    DAOFornecedor daoFornecedor = new DAOFornecedor();
    DAOCategoria daoCategoria = new DAOCategoria();

    public ArrayList<Produto> getLista() {
        String sql = "select * from produto";
        ArrayList<Produto> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Produto p = new Produto();
                p.setIdProduto(rs.getInt("idProduto"));
                p.setNome(rs.getString("nome"));
                p.setCategoria(daoCategoria.localizar(rs.getInt("categoria")));
                p.setDescricao(rs.getString("descricao"));
                p.setFornecedor(daoFornecedor.localizar(rs.getInt("fornecedor")));
                p.setPrecoCusto(rs.getDouble("precoCusto"));
                p.setPrecoVenda(rs.getDouble("precoVenda"));
                p.setQuantidadeEmEstoque(rs.getInt("estoque"));
                p.setUnidade(rs.getString("unidade"));
                lista.add(p);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());

        }
        return lista;
    }

    public boolean salvar(Produto p) {
        if (p.getIdProduto() == null) {
            return incluir(p);
        } else {
            return alterar(p);
        }

    }

    public boolean incluir(Produto p) {
        String sql = "insert into produto (nome, descricao, estoque, precoVenda, precoCusto, unidade, categoria, fornecedor)"
                + " values (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setString(1, p.getNome());
            stmt.setString(2, p.getDescricao());
            stmt.setInt(3, p.getQuantidadeEmEstoque());
            stmt.setDouble(4, p.getPrecoVenda());
            stmt.setDouble(5, p.getPrecoCusto());
            stmt.setString(6, p.getUnidade());
            stmt.setInt(7, p.getCategoria().getIdCategoria());
            stmt.setInt(8, p.getFornecedor().getIdFornecedor());

            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Produto incluído com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Produto não incluído");
                return false;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(Produto p) {
        String sql = "update produto set nome =?, descricao =?, estoque =?, precoVenda =?, precoCusto =?,"
                + "unidade = ?, categoria = ?, fornecedor = ? where idProduto = ?";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setString(1, p.getNome());
            stmt.setInt(7, p.getCategoria().getIdCategoria());
            stmt.setString(2, p.getDescricao());
            stmt.setDouble(5, p.getPrecoCusto());
            stmt.setDouble(4, p.getPrecoVenda());
            stmt.setInt(3, p.getQuantidadeEmEstoque());
            stmt.setString(6, p.getUnidade());
            stmt.setInt(8, p.getFornecedor().getIdFornecedor());
            stmt.setInt(9, p.getIdProduto());
            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Produto alterado com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Produto não alterado!");
                return false;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }

    public Produto localizar(int codigo) {
        String sql = "select * from produto where idProduto = ?";
        Produto p = new Produto();
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                p.setIdProduto(rs.getInt("idProduto"));
                p.setNome(rs.getString("nome"));
                p.setCategoria(daoCategoria.localizar(rs.getInt("categoria")));
                p.setDescricao(rs.getString("descricao"));
                p.setFornecedor(daoFornecedor.localizar(rs.getInt("fornecedor")));
                p.setPrecoCusto(rs.getDouble("precoCusto"));
                p.setPrecoVenda(rs.getDouble("precoVenda"));
                p.setQuantidadeEmEstoque(rs.getInt("estoque"));
                p.setUnidade(rs.getString("unidade"));
                return p;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return null;
    }

    public boolean remover(Produto p) {
        String sql = "delete from produto where idProduto = ?";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, p.getIdProduto());
            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Produto excluído com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Produto não excluído!");
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }

    //Método que filtra os produtos por categoria
    public ArrayList<Produto> pesquisaPorCategoria(Categoria c) {
        String sql = "select p.* from produto p, categoria c where p.categoria = c.idCategoria"
                + " and c.idCategoria = ?";
        ArrayList<Produto> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, c.getIdCategoria());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Produto p = new Produto();
                p.setIdProduto(rs.getInt("idProduto"));
                p.setNome(rs.getString("nome"));
                p.setCategoria(daoCategoria.localizar(rs.getInt("categoria")));
                p.setDescricao(rs.getString("descricao"));
                p.setFornecedor(daoFornecedor.localizar(rs.getInt("fornecedor")));
                p.setPrecoCusto(rs.getDouble("precoCusto"));
                p.setPrecoVenda(rs.getDouble("precoVenda"));
                p.setQuantidadeEmEstoque(rs.getInt("estoque"));
                p.setUnidade(rs.getString("unidade"));
                lista.add(p);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return lista;
    }

    //Método que filtra os produtos por fornecedor
     public ArrayList<Produto> pesquisaPorFornecedor (Fornecedor f) {
        String sql = "select p.* from produto p, fornecedor f where p.fornecedor = f.idFornecedor"
                + " and f.idFornecedor = ?";
        ArrayList<Produto> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, f.getIdFornecedor());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Produto p = new Produto();
                p.setIdProduto(rs.getInt("idProduto"));
                p.setNome(rs.getString("nome"));
                p.setCategoria(daoCategoria.localizar(rs.getInt("categoria")));
                p.setDescricao(rs.getString("descricao"));
                p.setFornecedor(daoFornecedor.localizar(rs.getInt("fornecedor")));
                p.setPrecoCusto(rs.getDouble("precoCusto"));
                p.setPrecoVenda(rs.getDouble("precoVenda"));
                p.setQuantidadeEmEstoque(rs.getInt("estoque"));
                p.setUnidade(rs.getString("unidade"));
                lista.add(p);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return lista;
    }
     
    //Método que diminui a quantidade em estoque quando uma venda é feita
    public boolean diminuirQuantidadeEmEstoque(Produto produto, Venda venda) {
        String sql = "update produto set estoque = "
                + "produto.estoque - (select sum(quantidadeVendida) "
                + "from itens_da_venda i, venda v, produto p where v.idVenda = i.idVenda "
                + "and i.idProduto = p.idProduto and p.idProduto = ? and v.idVenda = ?)"
                + "where idProduto = ? ";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, produto.getIdProduto());
            stmt.setInt(2, venda.getIdVenda());
            stmt.setInt(3, produto.getIdProduto());
            if (stmt.executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }

    }

    //Método que aumenta a quantidade em estoque quando uma compra é feita
    public boolean aumentarQuantidadeEmEstoque(Produto produto, Compra compra) {
        String sql = "update produto set estoque = produto.estoque +"
                + "(select sum(quantidadeComprada) from itens_da_compra i, compra c,"
                + "produto p where c.idCompra = i.idCompra and "
                + "i.idProduto = p.idProduto and p.idProduto = ? and c.idCompra = ?) "
                + "where idProduto = ?;";

        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, produto.getIdProduto());
            stmt.setInt(2, compra.getIdCompra());
            stmt.setInt(3, produto.getIdProduto());
            if (stmt.executeUpdate() > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }
}
