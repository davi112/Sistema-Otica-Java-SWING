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

/**
 *
 * @author DAVI
 */
public class DAOCategoria {

    public ArrayList<Categoria> getLista() {
        String sql = "select * from categoria order by IdCategoria";
        ArrayList<Categoria> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Categoria categoria = new Categoria();
                categoria.setIdCategoria(rs.getInt("idCategoria"));
                categoria.setNome(rs.getString("nome"));
                lista.add(categoria);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return lista;
    }

    public boolean salvar(Categoria c) {
        if (c.getIdCategoria() == null) {
            return incluir(c);
        } else {
            return alterar(c);
        }

    }

    public boolean incluir(Categoria c) {
        String sql = "insert into categoria (nome) values (?)";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setString(1, c.getNome());
            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Categoria incluída com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Categoria não incluída");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(Categoria c) {
        String sql = "update categoria set nome = ? where idCategoria = ?";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setString(1, c.getNome());
            stmt.setInt(2, c.getIdCategoria());
            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Categoria alterada com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Categoria não alterada");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }

    public Categoria localizar(int codigo) {
        String sql = "select * from categoria where idCategoria = ?";
        Categoria c = new Categoria();
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                c.setIdCategoria(rs.getInt("idCategoria"));
                c.setNome(rs.getString("nome"));
                return c;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());

        }
        return null;
    }

    public boolean remover(Categoria c) {
        String sql = "delete from categoria where idCategoria = ?";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, c.getIdCategoria());
            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Categoria excluída com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Categoria não excluída!");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }

}
