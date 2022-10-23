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
import model.Cargo;
import model.Funcionario;

/**
 *
 * @author DAVI
 */
public class DAOFuncionario {

    DAOCargo daoCargo = new DAOCargo();

    public ArrayList<Funcionario> getLista() {
        String sql = "select * from funcionario";
        ArrayList<Funcionario> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setIdFuncionario(rs.getInt("idFuncionario"));
                f.setNome(rs.getString("nome"));
                f.setCargo(daoCargo.localizar(rs.getInt("cargo")));
                f.setBairro(rs.getString("bairro"));
                f.setCep(rs.getString("cep"));
                f.setComplemento(rs.getString("complemento"));
                f.setEmail(rs.getString("email"));
                f.setNumero(rs.getInt("numero"));
                f.setTelefone(rs.getString("telefone"));
                f.setUf(rs.getString("uf"));
                f.setCpf(rs.getString("cpf"));
                f.setRua(rs.getString("rua"));
                f.setCidade(rs.getString("cidade"));
                f.setSalario(rs.getDouble("salario"));

                java.sql.Date data = rs.getDate("dataNascimento");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(data);
                f.setDataNascimento(calendar);

                java.sql.Date data1 = rs.getDate("dataAdmissao");
                Calendar calendar1 = Calendar.getInstance();
                calendar.setTime(data1);
                f.setDataAdmissao(calendar);

                lista.add(f);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return lista;
    }

    public boolean salvar(Funcionario f) {
        if (f.getIdFuncionario() == null) {
            return incluir(f);
        } else {
            return alterar(f);
        }

    }

    //Método que retorna a lista dos vendedores para serem carregados 
    //na tela de vendas
    public ArrayList<Funcionario> getListaVendedores() {
        String sql = "select * from funcionario f, cargo c where f.cargo = c.idCargo and c.idCargo = 1";
        ArrayList<Funcionario> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setIdFuncionario(rs.getInt("idFuncionario"));
                f.setNome(rs.getString("nome"));
                f.setCargo(daoCargo.localizar(rs.getInt("cargo")));
                f.setBairro(rs.getString("bairro"));
                f.setCep(rs.getString("cep"));
                f.setComplemento(rs.getString("complemento"));
                f.setEmail(rs.getString("email"));
                f.setNumero(rs.getInt("numero"));
                f.setTelefone(rs.getString("telefone"));
                f.setUf(rs.getString("uf"));
                f.setCpf(rs.getString("cpf"));
                f.setRua(rs.getString("rua"));
                f.setCidade(rs.getString("cidade"));
                f.setSalario(rs.getDouble("salario"));

                java.sql.Date data = rs.getDate("dataNascimento");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(data);
                f.setDataNascimento(calendar);

                java.sql.Date data1 = rs.getDate("dataAdmissao");
                Calendar calendar1 = Calendar.getInstance();
                calendar.setTime(data1);
                f.setDataAdmissao(calendar1);

                lista.add(f);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return lista;
    }

    //Método que filtra os funcionários por cargo
    public ArrayList<Funcionario> pesquisaPorCargo(Cargo c) {
        String sql = "select * from funcionario f, cargo c where f.cargo = c.idCargo and c.idCargo = ?";
        ArrayList<Funcionario> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, c.getIdCargo());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setIdFuncionario(rs.getInt("idFuncionario"));
                f.setNome(rs.getString("nome"));
                f.setCargo(daoCargo.localizar(rs.getInt("cargo")));
                f.setBairro(rs.getString("bairro"));
                f.setCep(rs.getString("cep"));
                f.setComplemento(rs.getString("complemento"));
                f.setEmail(rs.getString("email"));
                f.setNumero(rs.getInt("numero"));
                f.setTelefone(rs.getString("telefone"));
                f.setUf(rs.getString("uf"));
                f.setCpf(rs.getString("cpf"));
                f.setRua(rs.getString("rua"));
                f.setCidade(rs.getString("cidade"));
                f.setSalario(rs.getDouble("salario"));

                java.sql.Date data = rs.getDate("dataNascimento");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(data);
                f.setDataNascimento(calendar);

                java.sql.Date data1 = rs.getDate("dataAdmissao");
                Calendar calendar1 = Calendar.getInstance();
                calendar.setTime(data1);
                f.setDataAdmissao(calendar1);

                lista.add(f);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return lista;
    }

    public boolean incluir(Funcionario f) {
        String sql = "insert into funcionario (CPF, cargo, nome, dataNascimento, dataAdmissao,"
                + "rua, bairro, CEP, numero, complemento, cidade, uf, telefone, email, salario)"
                + "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setString(1, f.getCpf());
            stmt.setInt(2, f.getCargo().getIdCargo());
            stmt.setString(3, f.getNome());
            stmt.setDate(4, new java.sql.Date(f.getDataNascimento().getTimeInMillis()));
            stmt.setDate(5, new java.sql.Date(f.getDataAdmissao().getTimeInMillis()));
            stmt.setString(6, f.getRua());
            stmt.setString(7, f.getBairro());
            stmt.setString(8, f.getCep());
            stmt.setInt(9, f.getNumero());
            stmt.setString(10, f.getComplemento());
            stmt.setString(11, f.getCidade());
            stmt.setString(12, f.getUf());
            stmt.setString(13, f.getTelefone());
            stmt.setString(14, f.getEmail());
            stmt.setDouble(15, f.getSalario());
            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Funcionário incluído com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Funcionário não incluído");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(Funcionario f) {
        String sql = "update funcionario set CPF = ?, cargo = ?, nome = ?, dataNascimento = ?, dataAdmissao = ?,"
                + "rua = ?, bairro = ?, CEP = ?, numero = ?, complemento = ?, cidade = ?, uf = ?, telefone = ?, email = ?, salario = ?"
                + "where idFuncionario = ?";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setString(1, f.getCpf());
            stmt.setInt(2, f.getCargo().getIdCargo());
            stmt.setString(3, f.getNome());
            stmt.setDate(4, new java.sql.Date(f.getDataNascimento().getTimeInMillis()));
            stmt.setDate(5, new java.sql.Date(f.getDataAdmissao().getTimeInMillis()));
            stmt.setString(6, f.getRua());
            stmt.setString(7, f.getBairro());
            stmt.setString(8, f.getCep());
            stmt.setInt(9, f.getNumero());
            stmt.setString(10, f.getComplemento());
            stmt.setString(11, f.getCidade());
            stmt.setString(12, f.getUf());
            stmt.setString(13, f.getTelefone());
            stmt.setString(14, f.getEmail());
            stmt.setDouble(15, f.getSalario());
            stmt.setInt(16, f.getIdFuncionario());
            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Funcionário alterado com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Funcionário não alterado");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }

    public Funcionario localizar(int codigo) {
        String sql = "select * from funcionario where idFuncionario = ?";
        Funcionario f = new Funcionario();
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                f.setIdFuncionario(rs.getInt("idFuncionario"));
                f.setNome(rs.getString("nome"));
                f.setCargo(daoCargo.localizar(rs.getInt("cargo")));
                f.setBairro(rs.getString("bairro"));
                f.setCep(rs.getString("cep"));
                f.setComplemento(rs.getString("complemento"));
                f.setEmail(rs.getString("email"));
                f.setNumero(rs.getInt("numero"));
                f.setTelefone(rs.getString("telefone"));
                f.setUf(rs.getString("uf"));
                f.setCpf(rs.getString("cpf"));
                f.setRua(rs.getString("rua"));
                f.setCidade(rs.getString("cidade"));
                f.setSalario(rs.getDouble("salario"));

                java.sql.Date data = rs.getDate("dataNascimento");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(data);
                f.setDataNascimento(calendar);

                java.sql.Date data1 = rs.getDate("dataAdmissao");
                Calendar calendar1 = Calendar.getInstance();
                calendar.setTime(data1);
                f.setDataAdmissao(calendar1);

                return f;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return null;
    }

    public Funcionario pesquisar(String cpf) {
        String sql = "select * from funcionario where cpf = ?";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Funcionario f = new Funcionario();
                f.setIdFuncionario(rs.getInt("idFuncionario"));
                f.setNome(rs.getString("nome"));
                f.setCargo(daoCargo.localizar(rs.getInt("cargo")));
                f.setBairro(rs.getString("bairro"));
                f.setCep(rs.getString("cep"));
                f.setComplemento(rs.getString("complemento"));
                f.setEmail(rs.getString("email"));
                f.setNumero(rs.getInt("numero"));
                f.setTelefone(rs.getString("telefone"));
                f.setUf(rs.getString("uf"));
                f.setCpf(rs.getString("cpf"));
                f.setRua(rs.getString("rua"));
                f.setCidade(rs.getString("cidade"));
                f.setSalario(rs.getDouble("salario"));

                java.sql.Date data = rs.getDate("dataNascimento");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(data);
                f.setDataNascimento(calendar);

                java.sql.Date data1 = rs.getDate("dataAdmissao");
                Calendar calendar1 = Calendar.getInstance();
                calendar.setTime(data1);
                f.setDataAdmissao(calendar1);

                return f;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return null;
    }

    public boolean remover(Funcionario f) {
        String sql = "delete from funcionario where idFuncionario = ?";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, f.getIdFuncionario());
            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Funcionário excluído com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Funcionário não excluído!");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }
}
