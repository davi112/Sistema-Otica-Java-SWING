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
import model.Venda;

/**
 *
 * @author DAVI
 */
public class DAOVenda {

    DAOFuncionario daoFuncionario = new DAOFuncionario();
    DAOCliente daoCliente = new DAOCliente();

    public ArrayList<Venda> getLista() {
        String sql = "select * from venda";
        ArrayList<Venda> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Venda v = new Venda();
                v.setIdVenda(rs.getInt("idVenda"));

                java.sql.Date data = rs.getDate("dataVenda");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(data);
                v.setDataVenda(calendar);

                v.setVendedor(daoFuncionario.localizar(rs.getInt("vendedor")));
                v.setValorTotal(rs.getDouble("valorTotal"));
                v.setValorBruto(rs.getDouble("valorBruto"));
                v.setParcelas(rs.getInt("parcelas"));
                v.setFormaPagamento(rs.getString("formaPagamento"));
                v.setDesconto(rs.getDouble("desconto"));
                v.setCliente(daoCliente.localizar(rs.getInt("cliente")));

                lista.add(v);

            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return lista;
    }

    public boolean salvar(Venda v) {
        if (v.getIdVenda() == null) {
            return incluir(v);
        } else {
            return alterar(v);
        }
    }

    public boolean incluir(Venda v) {
        String sql = "insert into venda (dataVenda, vendedor, valorTotal, valorBruto, parcelas, formaPagamento, desconto, cliente)"
                + "values (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setDate(1, new java.sql.Date(v.getDataVenda().getTimeInMillis()));
            stmt.setInt(2, v.getVendedor().getIdFuncionario());
            stmt.setDouble(3, v.getValorTotal());
            stmt.setDouble(4, v.getValorBruto());
            stmt.setInt(5, v.getParcelas());
            stmt.setString(6, v.getFormaPagamento());
            stmt.setDouble(7, v.getDesconto());
            stmt.setInt(8, v.getCliente().getIdCliente());
            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Venda incluída com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Venda não incluída");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }

    public boolean alterar(Venda v) {
        String sql = "update venda set dataVenda = ?, vendedor = ?, valorTotal = ?, valorBruto = ?, parcelas = ?, "
                + "formaPagamento = ?, desconto = ?, cliente = ? where idVenda = ?";

        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setDate(1, new java.sql.Date(v.getDataVenda().getTimeInMillis()));
            stmt.setInt(2, v.getVendedor().getIdFuncionario());
            stmt.setDouble(3, v.getValorTotal());
            stmt.setDouble(4, v.getValorBruto());
            stmt.setInt(5, v.getParcelas());
            stmt.setString(6, v.getFormaPagamento());
            stmt.setDouble(7, v.getDesconto());
            stmt.setInt(8, v.getCliente().getIdCliente());
            stmt.setInt(9, v.getIdVenda());
            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Venda alterada com sucesso!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Venda não alterada");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }
    }

    public Venda localizar(int codigo) {
        String sql = "select * from venda where idVenda = ?";
        Venda v = new Venda();
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, codigo);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                v.setIdVenda(rs.getInt("idVenda"));

                java.sql.Date data = rs.getDate("dataVenda");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(data);
                v.setDataVenda(calendar);

                v.setVendedor(daoFuncionario.localizar(rs.getInt("vendedor")));
                v.setValorTotal(rs.getDouble("valorTotal"));
                v.setValorBruto(rs.getDouble("valorBruto"));
                v.setParcelas(rs.getInt("parcelas"));
                v.setFormaPagamento(rs.getString("formaPagamento"));
                v.setDesconto(rs.getDouble("desconto"));
                v.setCliente(daoCliente.localizar(rs.getInt("cliente")));
                return v;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return null;
    }

    public boolean remover(Venda v) {
        String sql = "delete from venda where idVenda = ?";
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, v.getIdVenda());
            if (stmt.executeUpdate() > 0) {
                JOptionPane.showMessageDialog(null, "Venda excluída!");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Venda não excluída!");
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
            return false;
        }

    }

    //Método que calcula o valor total da venda com base na 
    //soma dos subtotais dos itens de venda
    public double calculaValorTotal(Venda obj) {
        String sql = "select sum(i.subtotal) as total from itens_da_venda i, venda v where v.idVenda = i.idVenda and v.idVenda = ?";
        double valorTotal = 0.0;
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setInt(1, obj.getIdVenda());
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                valorTotal = rs.getDouble("total");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return valorTotal;
    }

    //Método que filtra as vendas por intervalos de data específicos
    public ArrayList<Venda> filtrarPorData(Calendar dataInicial, Calendar dataFinal) {
        String sql = "select * from venda where dataVenda between ? and ?";
        ArrayList<Venda> lista = new ArrayList<>();
        try {
            PreparedStatement stmt = ConnectionFactory.getPreparedStatement(sql);
            stmt.setDate(1, new java.sql.Date(dataInicial.getTimeInMillis()));
            stmt.setDate(2, new java.sql.Date(dataFinal.getTimeInMillis()));
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Venda v = new Venda();
                v.setIdVenda(rs.getInt("idVenda"));

                java.sql.Date data = rs.getDate("dataVenda");
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(data);
                v.setDataVenda(calendar);

                v.setVendedor(daoFuncionario.localizar(rs.getInt("vendedor")));
                v.setValorTotal(rs.getDouble("valorTotal"));
                v.setValorBruto(rs.getDouble("valorBruto"));
                v.setParcelas(rs.getInt("parcelas"));
                v.setFormaPagamento(rs.getString("formaPagamento"));
                v.setDesconto(rs.getDouble("desconto"));
                v.setCliente(daoCliente.localizar(rs.getInt("cliente")));

                lista.add(v);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Erro de SQL: " + e.getMessage());
        }
        return lista;
    }

}
