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
import model.Funcionario;
import model.Dependente;

/**
 *
 * @author DAVI
 */
public class DAODependentes {

    DAOFuncionario daoFunc = new DAOFuncionario();

    public ArrayList<Dependente> getLista() {
        String sql = "select * from dependentes";
        ArrayList<Dependente> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Dependente d = new Dependente();
                d.setIdDependente(rs.getInt("idDependente"));
                d.setCpf(rs.getString("cpf"));
                d.setNome(rs.getString("nome"));

                java.sql.Date data = rs.getDate("nascimento");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(data);
                d.setDataNascimento(calendar);

                d.setRelacionamento("relacionamento");

                d.setNome(sql);
                d.setParente(daoFunc.localizar(rs.getInt("parente")));
                lista.add(d);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return lista;
    }

    public boolean salvar(Dependente d) {
        if (d.getIdDependente() == null) {
            return incluir(d);
        } else {
            return alterar(d);
        }
    }

    public boolean incluir(Dependente d) {
        String sql = "insert into dependentes (cpf, nome, nascimento, relacionamento, parente)"
                + "values (?,?,?,?,?)";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setString(1, d.getCpf());
            stmt.setString(2, d.getNome());
            stmt.setDate(3, new java.sql.Date(d.getDataNascimento().getTimeInMillis()));
            stmt.setString(4, d.getRelacionamento());
            stmt.setInt(5, d.getParente().getIdFuncionario());
            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Dependente incluído com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Dependente não incluído");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(Dependente d) {
        String sql = "update dependentes set cpf = ?, nome = ?, nascimento = ?, relacionamento = ?,"
                + "parente = ? where idDependente = ?";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setString(1, d.getCpf());
            stmt.setString(2, d.getNome());
            stmt.setDate(3, new java.sql.Date(d.getDataNascimento().getTimeInMillis()));
            stmt.setString(4, d.getRelacionamento());
            stmt.setInt(5, d.getParente().getIdFuncionario());
            stmt.setInt(6, d.getIdDependente());
            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Dependente alterado com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Dependente não alterado");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }

    public boolean remover(Dependente d) {
        String sql = "delete from dependentes where idDependente = ?";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, d.getIdDependente());
            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Dependente excluído!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Dependente não excuído!");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return true;
    }

    public ArrayList<Dependente> getDependentesPorFuncionario(Funcionario funcionario) {
        String sql = "select d.* from dependentes d, funcionario f where d.parente = f.idFuncionario and "
                + "f.idFuncionario = ?";
        ArrayList<Dependente> lista = new ArrayList<>();

        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, funcionario.getIdFuncionario());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Dependente d = new Dependente();
                d.setIdDependente(rs.getInt("idDependente"));
                d.setCpf(rs.getString("cpf"));
                d.setNome(rs.getString("nome"));

                java.sql.Date data = rs.getDate("nascimento");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(data);
                d.setDataNascimento(calendar);

                d.setRelacionamento(rs.getString("relacionamento"));

                d.setParente(daoFunc.localizar(rs.getInt("parente")));
                lista.add(d);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return lista;
    }
}
