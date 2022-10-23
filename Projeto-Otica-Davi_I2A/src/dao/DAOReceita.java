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
import model.Cliente;
import model.Receita;

/**
 *
 * @author DAVI
 */
public class DAOReceita {

    DAOCliente daoCliente = new DAOCliente();

    public ArrayList<Receita> getLista() {
        String sql = "select * from receita";
        ArrayList<Receita> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Receita r = new Receita();
                r.setIdReceita(rs.getInt("idReceita"));
                r.setOlhoDireitoCilindricoLonge(rs.getDouble("olhoDireitoCilindricoLonge"));
                r.setOlhoDireitoCilindricoPerto(rs.getDouble("olhoDireitoCilindricoPerto"));
                r.setOlhoDireitoDPLonge(rs.getDouble("olhoDireitoDPLonge"));
                r.setOlhoDireitoDPPerto(rs.getDouble("olhoDireitoDPPerto"));
                r.setOlhoDireitoEixoLonge(rs.getDouble("olhoDireitoEixoLonge"));
                r.setOlhoDireitoEixoPerto(rs.getDouble("olhoDireitoEixoPerto"));
                r.setOlhoDireitoEsfericoLonge(rs.getDouble("olhoDireitoEsfericoLonge"));
                r.setOlhoDireitoEsfericoPerto(rs.getDouble("olhoDireitoEsfericoPerto"));
                r.setOlhoEsquerdoCilindricoLonge(rs.getDouble("olhoEsquerdoCilindricoLonge"));
                r.setOlhoEsquerdoCilindricoPerto(rs.getDouble("olhoEsquerdoCilindricoPerto"));
                r.setOlhoEsquerdoDPLonge(rs.getDouble("olhoEsquerdoDPLonge"));
                r.setOlhoEsquerdoDPPerto(rs.getDouble("olhoEsquerdoDPPerto"));
                r.setOlhoEsquerdoEixoLonge(rs.getDouble("olhoEsquerdoEixoLonge"));
                r.setOlhoEsquerdoEixoPerto(rs.getDouble("olhoEsquerdoEixoPerto"));
                r.setOlhoEsquerdoEsfericoLonge(rs.getDouble("olhoEsquerdoEsfericoLonge"));
                r.setOlhoEsquerdoEsfericoPerto(rs.getDouble("olhoEsquerdoEsfericoPerto"));

                r.setAdicao(rs.getDouble("adicao"));
                r.setCliente(daoCliente.localizar(rs.getInt("idCliente")));
                r.setObservacao(rs.getString("observacao"));
                java.sql.Date data = rs.getDate("dataVencimento");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(data);
                r.setDataVencimento(calendar);

                lista.add(r);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return lista;
    }

    //Método que filtra as receitas por cliente
    public ArrayList<Receita> getReceitasPorCliente(Cliente cliente) {
        String sql = "select * from receita r, cliente c where r.cliente = c.idCliente and c.idCliente = ?";
        ArrayList<Receita> lista = new ArrayList<>();
        
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, cliente.getIdCliente());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Receita r = new Receita();
                r.setIdReceita(rs.getInt("idReceita"));
                r.setOlhoDireitoCilindricoLonge(rs.getDouble("olhoDireitoCilindricoLonge"));
                r.setOlhoDireitoCilindricoPerto(rs.getDouble("olhoDireitoCilindricoPerto"));
                r.setOlhoDireitoDPLonge(rs.getDouble("olhoDireitoDPLonge"));
                r.setOlhoDireitoDPPerto(rs.getDouble("olhoDireitoDPPerto"));
                r.setOlhoDireitoEixoLonge(rs.getDouble("olhoDireitoEixoLonge"));
                r.setOlhoDireitoEixoPerto(rs.getDouble("olhoDireitoEixoPerto"));
                r.setOlhoDireitoEsfericoLonge(rs.getDouble("olhoDireitoEsfericoLonge"));
                r.setOlhoDireitoEsfericoPerto(rs.getDouble("olhoDireitoEsfericoPerto"));
                r.setOlhoEsquerdoCilindricoLonge(rs.getDouble("olhoEsquerdoCilindricoLonge"));
                r.setOlhoEsquerdoCilindricoPerto(rs.getDouble("olhoEsquerdoCilindricoPerto"));
                r.setOlhoEsquerdoDPLonge(rs.getDouble("olhoEsquerdoDPLonge"));
                r.setOlhoEsquerdoDPPerto(rs.getDouble("olhoEsquerdoDPPerto"));
                r.setOlhoEsquerdoEixoLonge(rs.getDouble("olhoEsquerdoEixoLonge"));
                r.setOlhoEsquerdoEixoPerto(rs.getDouble("olhoEsquerdoEixoPerto"));
                r.setOlhoEsquerdoEsfericoLonge(rs.getDouble("olhoEsquerdoEsfericoLonge"));
                r.setOlhoEsquerdoEsfericoPerto(rs.getDouble("olhoEsquerdoEsfericoPerto"));

                r.setAdicao(rs.getDouble("adicao"));
                r.setCliente(daoCliente.localizar(rs.getInt("idCliente")));
                r.setObservacao(rs.getString("observacao"));
                java.sql.Date data = rs.getDate("dataVencimento");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(data);
                r.setDataVencimento(calendar);

                lista.add(r);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return lista;
    }

    public boolean salvar(Receita r) {
        if (r.getIdReceita() == null) {
            return incluir(r);
        } else {
            return alterar(r);
        }
    }

    public boolean incluir(Receita r) {
        String sql = "insert into receita (olhoDireitoEsfericoPerto, olhoDireitoEsfericoLonge, "
                + "olhoEsquerdoEsfericoPerto, olhoEsquerdoEsfericoLonge, olhoEsquerdoCilindricoLonge, "
                + "olhoEsquerdoCilindricoPerto, olhoDireitoCilindricoPerto, olhoDireitoCilindricoLonge, "
                + "olhoDireitoEixoPerto, olhoEsquerdoEixoLonge, olhoDireitoEixoLonge, "
                + "olhoEsquerdoEixoPerto, olhoEsquerdoDPPerto, olhoEsquerdoDPLonge, "
                + "olhoDireitoDPLonge, olhoDireitoDPPerto, adicao, dataVencimento, "
                + "observacao, cliente) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setDouble(1, r.getOlhoDireitoEsfericoPerto());
            stmt.setDouble(2, r.getOlhoDireitoEsfericoLonge());
            stmt.setDouble(3, r.getOlhoEsquerdoEsfericoPerto());
            stmt.setDouble(4, r.getOlhoEsquerdoEsfericoLonge());
            stmt.setDouble(5, r.getOlhoEsquerdoCilindricoLonge());
            stmt.setDouble(6, r.getOlhoEsquerdoCilindricoPerto());
            stmt.setDouble(7, r.getOlhoDireitoCilindricoPerto());
            stmt.setDouble(8, r.getOlhoDireitoCilindricoLonge());
            stmt.setDouble(9, r.getOlhoDireitoEixoPerto());
            stmt.setDouble(10, r.getOlhoEsquerdoEixoLonge());
            stmt.setDouble(11, r.getOlhoDireitoEixoLonge());
            stmt.setDouble(12, r.getOlhoEsquerdoEixoPerto());
            stmt.setDouble(13, r.getOlhoEsquerdoDPPerto());
            stmt.setDouble(14, r.getOlhoEsquerdoDPLonge());
            stmt.setDouble(15, r.getOlhoDireitoDPLonge());
            stmt.setDouble(16, r.getOlhoDireitoDPPerto());
            stmt.setDouble(17, r.getAdicao());
            stmt.setDate(18, new java.sql.Date(r.getDataVencimento().getTimeInMillis()));
            stmt.setString(19, r.getObservacao());
            stmt.setInt(20, r.getCliente().getIdCliente());
            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Receita incluída com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Receita não incluída");
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(Receita r) {
        String sql = "update receita set olhoDireitoEsfericoPerto = ?, olhoDireitoEsfericoLonge = ?, "
                + "olhoEsquerdoEsfericoPerto = ?, olhoEsquerdoEsfericoLonge = ?, olhoEsquerdoCilindricoLonge = ?, "
                + "olhoEsquerdoCilindricoPerto = ?, olhoDireitoCilindricoPerto = ?, olhoDireitoCilindricoLonge = ?, "
                + "olhoDireitoEixoPerto = ?, olhoEsquerdoEixoLonge = ?, olhoDireitoEixoLonge = ?, "
                + "olhoEsquerdoEixoPerto = ?, olhoEsquerdoDPPerto = ?, olhoEsquerdoDPLonge = ?, "
                + "olhoDireitoDPLonge = ?, olhoDireitoDPPerto = ?, adicao = ?, dataVencimento = ?, "
                + "observacao = ?, cliente = ? where idReceita = ?";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setDouble(1, r.getOlhoDireitoEsfericoPerto());
            stmt.setDouble(2, r.getOlhoDireitoEsfericoLonge());
            stmt.setDouble(3, r.getOlhoEsquerdoEsfericoPerto());
            stmt.setDouble(4, r.getOlhoEsquerdoEsfericoLonge());
            stmt.setDouble(5, r.getOlhoEsquerdoCilindricoLonge());
            stmt.setDouble(6, r.getOlhoEsquerdoCilindricoPerto());
            stmt.setDouble(7, r.getOlhoDireitoCilindricoPerto());
            stmt.setDouble(8, r.getOlhoDireitoCilindricoLonge());
            stmt.setDouble(9, r.getOlhoDireitoEixoPerto());
            stmt.setDouble(10, r.getOlhoEsquerdoEixoLonge());
            stmt.setDouble(11, r.getOlhoDireitoEixoLonge());
            stmt.setDouble(12, r.getOlhoEsquerdoEixoPerto());
            stmt.setDouble(13, r.getOlhoEsquerdoDPPerto());
            stmt.setDouble(14, r.getOlhoEsquerdoDPLonge());
            stmt.setDouble(15, r.getOlhoDireitoDPLonge());
            stmt.setDouble(16, r.getOlhoDireitoDPPerto());
            stmt.setDouble(17, r.getAdicao());
            stmt.setDate(18, new java.sql.Date(r.getDataVencimento().getTimeInMillis()));
            stmt.setString(19, r.getObservacao());
            stmt.setInt(20, r.getCliente().getIdCliente());
            stmt.setInt(21, r.getIdReceita());
            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Receita alterada com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Receita não alterada");
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }

    public boolean remover(Receita r) {
        String sql = "delete from receita where idReceita = ?";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, r.getIdReceita());
            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Receita excluída com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Receita não excluída!");
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }
}
