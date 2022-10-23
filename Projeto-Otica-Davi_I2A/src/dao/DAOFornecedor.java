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
import model.Fornecedor;

/**
 *
 * @author DAVI
 */
public class DAOFornecedor {

    public ArrayList<Fornecedor> getLista() {
        String sql = "select * from fornecedor";
        ArrayList<Fornecedor> lista = new ArrayList<>();

        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Fornecedor f = new Fornecedor();
                f.setIdFornecedor(rs.getInt("idFornecedor"));
                f.setBairro(rs.getString("bairro"));
                f.setCep(rs.getString("cep"));
                f.setCidade(rs.getString("cidade"));
                f.setCnpj(rs.getString("CNPJ"));
                f.setEmail(rs.getString("email"));
                f.setNomeFantasia(rs.getString("nomeFantasia"));
                f.setNumero(rs.getInt("numero"));
                f.setRazaoSocial(rs.getString("razaoSocial"));
                f.setRua(rs.getString("rua"));
                f.setTelefone(rs.getString("telefone"));
                f.setUf(rs.getString("uf"));
                f.setComplemento(rs.getString("complemento"));
                lista.add(f);
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return lista;
    }

    public boolean salvar(Fornecedor f) {
        if (f.getIdFornecedor() == null) {
            return incluir(f);
        } else {
            return alterar(f);
        }

    }

    public boolean incluir(Fornecedor f) {
        String sql = "insert into fornecedor (CNPJ, nomeFantasia,razaoSocial, rua,"
                + "bairro, CEP, numero, cidade, uf, email, telefone, complemento)"
                + "values(?,?,?,?,?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setString(1, f.getCnpj());
            stmt.setString(2, f.getNomeFantasia());
            stmt.setString(3, f.getRazaoSocial());
            stmt.setString(4, f.getRua());
            stmt.setString(5, f.getBairro());
            stmt.setString(6, f.getCep());
            stmt.setInt(7, f.getNumero());
            stmt.setString(8, f.getCidade());
            stmt.setString(9, f.getUf());
            stmt.setString(10, f.getEmail());
            stmt.setString(11, f.getTelefone());
            stmt.setString(12, f.getComplemento());
            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Fornecedor incluído com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Fornecedor não incluído");
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(Fornecedor f) {
        String sql = "update fornecedor set CNPJ = ? , nomeFantasia = ?, razaoSocial = ?,"
                + "rua = ?, bairro = ?, CEP = ?, numero = ?, cidade = ?, uf = ?, email = ?, "
                + "telefone = ?, complemento = ? where idFornecedor = ?";

        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setString(1, f.getCnpj());
            stmt.setString(2, f.getNomeFantasia());
            stmt.setString(3, f.getRazaoSocial());
            stmt.setString(4, f.getRua());
            stmt.setString(5, f.getBairro());
            stmt.setString(6, f.getCep());
            stmt.setInt(7, f.getNumero());
            stmt.setString(8, f.getCidade());
            stmt.setString(9, f.getUf());
            stmt.setString(10, f.getEmail());
            stmt.setString(11, f.getTelefone());
            stmt.setString(12, f.getComplemento());
            stmt.setInt(13, f.getIdFornecedor());
            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Fornecedor alterado com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Fornecedor não alterado");
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }

    public Fornecedor localizar(int codigo) {
        String sql = "select * from fornecedor where idFornecedor = ?";
        Fornecedor f = new Fornecedor();
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                f.setIdFornecedor(rs.getInt("idFornecedor"));
                f.setBairro(rs.getString("bairro"));
                f.setCep(rs.getString("cep"));
                f.setCidade(rs.getString("cidade"));
                f.setCnpj(rs.getString("CNPJ"));
                f.setEmail(rs.getString("email"));
                f.setNomeFantasia(rs.getString("nomeFantasia"));
                f.setNumero(rs.getInt("numero"));
                f.setRazaoSocial(rs.getString("razaoSocial"));
                f.setRua(rs.getString("rua"));
                f.setTelefone(rs.getString("telefone"));
                f.setUf(rs.getString("uf"));
                f.setComplemento(rs.getString("complemento"));
            }

            return f;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            System.out.println("morrere");
        }
        return null;
    }

    public boolean remover(Fornecedor f) {
        String sql = "delete from fornecedor where idFornecedor = ?";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, f.getIdFornecedor());
            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Fornecedor excluído com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Fornecedor não excluído!");
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }

    public Fornecedor pesquisar(String cnpj) {
        String sql = "select * from fornecedor where cnpj = ?";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setString(1, cnpj);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Fornecedor f = new Fornecedor();
                f.setIdFornecedor(rs.getInt("idFornecedor"));
                f.setBairro(rs.getString("bairro"));
                f.setCep(rs.getString("cep"));
                f.setCidade(rs.getString("cidade"));
                f.setCnpj(rs.getString("CNPJ"));
                f.setEmail(rs.getString("email"));
                f.setNomeFantasia(rs.getString("nomeFantasia"));
                f.setNumero(rs.getInt("numero"));
                f.setRazaoSocial(rs.getString("razaoSocial"));
                f.setRua(rs.getString("rua"));
                f.setTelefone(rs.getString("telefone"));
                f.setUf(rs.getString("uf"));
                f.setComplemento(rs.getString("complemento"));

                return f;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return null;
    }
}
