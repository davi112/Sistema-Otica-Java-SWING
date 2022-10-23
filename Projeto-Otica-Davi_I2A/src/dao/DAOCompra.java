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
import java.util.Calendar;
import javax.swing.JOptionPane;
import model.Compra;


/**
 *
 * @author DAVI
 */
public class DAOCompra {

    public ArrayList<Compra> getLista() {
        String sql = "Select * from compra";
        ArrayList<Compra> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Compra c = new Compra();
                c.setIdCompra(rs.getInt("idCompra"));

                java.sql.Date data = rs.getDate("dataCompra");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(data);
                c.setDataCompra(calendar);

                c.setValorTotal(rs.getDouble("valorTotal"));
                lista.add(c);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return lista;
    }

    public boolean salvar(Compra c) {
        if (c.getIdCompra() == null) {
            return incluir(c);
        } else {
            return alterar(c);
        }

    }

    public boolean incluir(Compra c) {
        String sql = "insert into compra (dataCompra, valorTotal) values (?,?)";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setDate(1, new java.sql.Date(c.getDataCompra().getTimeInMillis()));
            stmt.setDouble(2, c.getValorTotal());
            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Compra incluída com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Compra não incluída");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(Compra c) {
        String sql = "update compra set dataCompra = ?, valorTotal = ? where idCompra = ? ";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setDate(1, new java.sql.Date(c.getDataCompra().getTimeInMillis()));
            stmt.setDouble(2, c.getValorTotal());
            stmt.setInt(3, c.getIdCompra());
            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Compra alterada com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Compra não alterada");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }

    public Compra localizar(int codigo) {
        String sql = "select * from compra where idCompra = ?";
        Compra c = new Compra();
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                c.setIdCompra(rs.getInt("idCompra"));
                java.sql.Date data = rs.getDate("dataCompra");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(data);
                c.setDataCompra(calendar);

                c.setValorTotal(rs.getDouble("valorTotal"));
                return c;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return null;
    }

    public boolean remover(Compra c) {
        String sql = "delete from compra where idCompra = ?";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, c.getIdCompra());
            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Compra excluída!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Compra não excluída!");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }

    //Método de calcular o valorTotal com base na soma dos subtotais dos
    //itens da venda
    public double calculaValorTotal(Compra c) {
        String sql = "select sum(i.subtotal) as total from itens_da_compra i, compra c where c.idCompra = i.idCompra and c.idCompra = ?";
        double valorTotal = 0.0;
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, c.getIdCompra());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                valorTotal = rs.getDouble("total");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return valorTotal;
    }

    //Método de filtrar a compra por intervalos de data
    public ArrayList<Compra> filtrarPorData(Calendar dataInicial, Calendar dataFinal) {
        String sql = "select * from compra where dataCompra between ? and ?";
        ArrayList<Compra> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setDate(1, new java.sql.Date(dataInicial.getTimeInMillis()));
            stmt.setDate(2, new java.sql.Date(dataFinal.getTimeInMillis()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Compra c = new Compra();
                c.setIdCompra(rs.getInt("idCompra"));

                java.sql.Date data = rs.getDate("dataCompra");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(data);
                c.setDataCompra(calendar);

                c.setValorTotal(rs.getDouble("valorTotal"));
                lista.add(c);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return lista;
    }

}
