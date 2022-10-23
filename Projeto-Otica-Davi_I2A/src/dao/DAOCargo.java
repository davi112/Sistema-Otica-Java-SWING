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
import model.Cargo;

/**
 *
 * @author DAVI
 */
public class DAOCargo {

    public ArrayList<Cargo> getLista() {
        String sql = "select * from cargo order by IdCargo";
        ArrayList<Cargo> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Cargo cargo = new Cargo();
                cargo.setIdCargo(rs.getInt("idCargo"));
                cargo.setNome(rs.getString("nome"));
                lista.add(cargo);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return lista;
    }

    public boolean salvar(Cargo c) {
        if (c.getIdCargo() == null) {
            return incluir(c);
        } else {
            return alterar(c);
        }

    }

    public boolean incluir(Cargo c) {
        String sql = "insert into cargo (nome) values (?)";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setString(1, c.getNome());
            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Cargo incluído com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Cargo não incluído");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(Cargo c) {
        String sql = "update cargo set nome = ? where idCargo = ?";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setString(1, c.getNome());
            stmt.setInt(2, c.getIdCargo());
            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Cargo alterado com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Cargo não alterado");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }

    public Cargo localizar(int codigo) {
        String sql = "select * from cargo where idCargo = ?";
        Cargo c = new Cargo();
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                c.setIdCargo(rs.getInt("idCargo"));
                c.setNome(rs.getString("nome"));
                return c;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());

        }
        return null;
    }

    public boolean remover(Cargo c) {
        String sql = "delete from cargo where idCargo = ?";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, c.getIdCargo());
            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Cargo excluído com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Cargo não excluído!");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }

}
