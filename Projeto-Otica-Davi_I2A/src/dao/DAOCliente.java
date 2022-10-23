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

/**
 *
 * @author DAVI
 */
public class DAOCliente {

    //Método de retornar todos os clientes do banco
    public ArrayList<Cliente> getLista() {
        String sql = "select * from cliente";
        ArrayList<Cliente> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("idCliente"));
                c.setNome(rs.getString("nome"));
                c.setBairro(rs.getString("bairro"));
                c.setCep(rs.getString("cep"));
                c.setComplemento(rs.getString("complemento"));
                c.setEmail(rs.getString("email"));
                c.setNumero(rs.getInt("numero"));
                c.setTelefone(rs.getString("telefone"));
                c.setUf(rs.getString("uf"));
                c.setCpf(rs.getString("cpf"));
                c.setRua(rs.getString("rua"));
                c.setCidade(rs.getString("cidade"));

                java.sql.Date data = rs.getDate("dataNascimento");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(data);
                c.setDataNascimento(calendar);

                lista.add(c);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return lista;
    }

    //Chama o método de incluir caso o cliente não exista ou chama o
    //método de alterar caso ele já exista.
    public boolean salvar(Cliente c) {
        if (c.getIdCliente() == null) {
            return incluir(c);

        } else {
            return alterar(c);
        }

    }

    //Método de inserir um cliente no banco
    public boolean incluir(Cliente cliente) {
        String sql = "insert into cliente (cpf, nome,"
                + "dataNascimento, rua, bairro, cep,"
                + "numero, complemento, cidade, uf, email, telefone"
                + ")values(?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getNome());
            stmt.setDate(3, new java.sql.Date(cliente.getDataNascimento().getTimeInMillis()));
            stmt.setString(4, cliente.getRua());
            stmt.setString(5, cliente.getBairro());
            stmt.setString(6, cliente.getCep());
            stmt.setInt(7, cliente.getNumero());
            stmt.setString(8, cliente.getComplemento());
            stmt.setString(9, cliente.getCidade());
            stmt.setString(10, cliente.getUf());
            stmt.setString(11, cliente.getEmail());
            stmt.setString(12, cliente.getTelefone());
            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Cliente incluído com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Cliente não incluído");
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }

    //Método de alterar os dados de um cliente no banco 
    public boolean alterar(Cliente cliente) {
        String sql = "update cliente set cpf = ?, nome = ?, dataNascimento = ?,"
                + "rua = ?, bairro = ?, cep = ?, numero = ?, complemento = ?, cidade = ?,"
                + "uf = ?, email = ?, telefone = ? where idCliente = ?";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setString(1, cliente.getCpf());
            stmt.setString(2, cliente.getNome());
            stmt.setDate(3, new java.sql.Date(cliente.getDataNascimento().getTimeInMillis()));
            stmt.setString(4, cliente.getRua());
            stmt.setString(5, cliente.getBairro());
            stmt.setString(6, cliente.getCep());
            stmt.setInt(7, cliente.getNumero());
            stmt.setString(8, cliente.getComplemento());
            stmt.setString(9, cliente.getCidade());
            stmt.setString(10, cliente.getUf());
            stmt.setString(11, cliente.getEmail());
            stmt.setString(12, cliente.getTelefone());
            stmt.setInt(13, cliente.getIdCliente());
            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Cliente alterado com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Cliente não alterado");
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }

    //Método de excluir um cliente no banco
    public boolean remover(Cliente c) {
        String sql = "delete from cliente where idCliente = ?";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, c.getIdCliente());
            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Cliente excluído com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Cliente não excluído!");
                return false;
            }

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }

    //Método de pesquisar um cliente no banco pelo id e reconstruir o objeto
    public Cliente localizar(int codigo) {
        String sql = "select * from cliente where idCliente= ?";
        Cliente c = new Cliente();
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                c.setIdCliente(rs.getInt("idCliente"));
                c.setNome(rs.getString("nome"));
                c.setBairro(rs.getString("bairro"));
                c.setCep(rs.getString("cep"));
                c.setComplemento(rs.getString("complemento"));
                c.setEmail(rs.getString("email"));
                c.setNumero(rs.getInt("numero"));
                c.setTelefone(rs.getString("telefone"));
                c.setUf(rs.getString("uf"));
                c.setCpf(rs.getString("cpf"));
                c.setRua(rs.getString("rua"));
                c.setCidade(rs.getString("cidade"));

                java.sql.Date data = rs.getDate("dataNascimento");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(data);
                c.setDataNascimento(calendar);
                return c;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return null;
    }

    //Método de pesquisar o cliente no banco por CPF e reconstruir o objeto
    public Cliente pesquisar(String cpf) {
        String sql = "select * from cliente where cpf = ?";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setString(1, cpf);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Cliente c = new Cliente();
                c.setIdCliente(rs.getInt("idCliente"));
                c.setNome(rs.getString("nome"));
                c.setBairro(rs.getString("bairro"));
                c.setCep(rs.getString("cep"));
                c.setComplemento(rs.getString("complemento"));
                c.setEmail(rs.getString("email"));
                c.setNumero(rs.getInt("numero"));
                c.setTelefone(rs.getString("telefone"));
                c.setUf(rs.getString("uf"));
                c.setCpf(rs.getString("cpf"));
                c.setRua(rs.getString("rua"));
                c.setCidade(rs.getString("cidade"));

                java.sql.Date data = rs.getDate("dataNascimento");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(data);
                c.setDataNascimento(calendar);

                return c;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return null;
    }
}
