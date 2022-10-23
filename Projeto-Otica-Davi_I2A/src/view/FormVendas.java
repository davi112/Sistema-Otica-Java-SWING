/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DAOCliente;
import dao.DAOFuncionario;
import dao.DAOItensDaVenda;
import dao.DAOProduto;
import dao.DAOVenda;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import model.Cliente;
import model.Funcionario;
import model.Produto;
import model.Venda;

/**
 *
 * @author DAVI
 */
public class FormVendas extends javax.swing.JDialog {

    /**
     * Creates new form FormVendas
     */
    DAOVenda daoVenda = new DAOVenda();
    DAOFuncionario daoFuncionario = new DAOFuncionario();
    DAOCliente daoCliente = new DAOCliente();
    DAOItensDaVenda daoItens = new DAOItensDaVenda();
    DAOProduto daoProduto = new DAOProduto();

    public void atualizaTabela() {

        int linhaTabelaAux = tblVendas.getSelectedRow();
        listaVendas.clear();
        listaVendas.addAll(daoVenda.getLista());

        if (!listaVendas.isEmpty()) {
            if (linhaTabelaAux < 0) {
                tblVendas.changeSelection(0, 0, false, false);
            } else if (listaVendas.size() == linhaTabelaAux) {
                tblVendas.changeSelection(listaVendas.size() - 1, 0, false, false);
            } else if (listaVendas.get(linhaTabelaAux).getIdVenda() != null) {
                tblVendas.changeSelection(linhaTabelaAux, 0, false, false);
            }
        }

    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/oculos.png")));
    }

    public void limpaCamposCasoNaoHajaCadastros() {
        if (this.listaVendas.isEmpty()) {
            txtIdVenda.setText("");
            txtParcelas.setText("");
            txtDataVenda.setText("");
            txtDesconto.setText("");
            cbxFormaPagamento.setSelectedItem("");
            cbxClientes.setSelectedItem("");
            cbxVendedores.setSelectedItem("");
            txtValorParcial.setText("");
            txtValorTotal.setText("");
            btnEditar.setEnabled(false);
            btnExcluir.setEnabled(false);
        }
    }

    public void verificarBotoesParaOutrasTabelas() {
        if (listaVendas.isEmpty()) {
            btnAddItem.setEnabled(false);
        } else {
            btnAddItem.setEnabled(true);
        }

    }

    public void HabilitarBotoesPesquisa() {
        if (listaVendas.isEmpty()) {
            txtDataInicial.setEnabled(false);
            txtDataFinal.setEnabled(false);
            btnFiltrar1.setEnabled(false);
            txtIdPesquisa.setEnabled(false);
            btnPesquisa.setEnabled(false);
        } else {
            txtDataInicial.setEnabled(true);
            txtDataFinal.setEnabled(true);
            btnFiltrar1.setEnabled(true);
            txtIdPesquisa.setEnabled(true);
            btnPesquisa.setEnabled(true);
        }
    }

    public void modificarBotoesdeNavegacao() {
        if (tblVendas.getRowCount() == 1
                || listaVendas.isEmpty()) {
            btnPrimeiro.setEnabled(false);
            btnAnterior.setEnabled(false);
            btnProximo.setEnabled(false);
            btnUltimo.setEnabled(false);

        } else if (tblVendas.getSelectedRow() == 0) {
            btnPrimeiro.setEnabled(false);
            btnAnterior.setEnabled(false);
            btnProximo.setEnabled(true);
            btnUltimo.setEnabled(true);
        } else if (tblVendas.getSelectedRow() == listaVendas.size() - 1) {
            btnProximo.setEnabled(false);
            btnUltimo.setEnabled(false);
            btnPrimeiro.setEnabled(true);
            btnAnterior.setEnabled(true);
        } else {
            btnPrimeiro.setEnabled(true);
            btnAnterior.setEnabled(true);
            btnProximo.setEnabled(true);
            btnUltimo.setEnabled(true);
        }

    }

    public void permitirDigitarParcelas() {
        if (cbxFormaPagamento.getSelectedIndex() == 0
                || cbxFormaPagamento.getSelectedIndex() == 1) {
            txtParcelas.setEnabled(false);
            txtDesconto.setEditable(true);
            txtDesconto.setEnabled(true);
            btnAplicarDesconto.setEnabled(true);

        }

        if (cbxFormaPagamento.getSelectedIndex() == 2) {
            txtParcelas.setEnabled(true);
            txtDesconto.setEditable(false);
            txtDesconto.setEnabled(false);
            btnAplicarDesconto.setEnabled(false);
            txtDesconto.setText("0.0");
            txtValorTotal.setText(txtValorParcial.getText());
        }
    }

    public void trataEdicao(boolean editando) {
        btnCancelar.setEnabled(editando);
        btnSalvar.setEnabled(editando);
        btnEditar.setEnabled(!editando);
        btnNovo.setEnabled(!editando);
        btnExcluir.setEnabled(!editando);
        btnFechar.setEnabled(!editando);
        btnPrimeiro.setEnabled(!editando);
        btnProximo.setEnabled(!editando);
        btnAnterior.setEnabled(!editando);
        btnUltimo.setEnabled(!editando);
        abas.setEnabledAt(0, !editando);
        txtParcelas.setEnabled(editando);
        txtDataVenda.setEnabled(editando);
        txtDesconto.setEnabled(editando);
        cbxFormaPagamento.setEnabled(editando);
        cbxClientes.setEnabled(editando);
        cbxVendedores.setEnabled(editando);
        txtValorParcial.setEnabled(editando);
        txtValorTotal.setEnabled(editando);
        btnAplicarDesconto.setEnabled(editando);
        btnFiltrar1.setEnabled(!editando);
        txtDataInicial.setEnabled(!editando);
        txtDataFinal.setEnabled(!editando);
        btnPesquisa.setEnabled(!editando);
        txtIdPesquisa.setEnabled(!editando);
    }

    public void digitarSomenteNumeros(java.awt.event.KeyEvent evt) {
        String caracteres = "0123456789.";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }

    public boolean validaCampos() {
        StringBuilder mensagem = new StringBuilder();
        if (cbxFormaPagamento.getSelectedItem() == null) {
            mensagem.append("Informe a forma de pagamento!\n");
        }
        if (cbxVendedores.getSelectedItem() == null) {
            mensagem.append("Informe o vendedor!\n");
        }
        if (cbxClientes.getSelectedItem() == null) {
            mensagem.append("Informe o cliente!\n");
        }
        if ((txtDataVenda.getText().length() > 0)) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            try {
                sdf.parse(txtDataVenda.getText());
            } catch (Exception e) {
                mensagem.append("Informe a data da Venda!\n");
                txtDataVenda.requestFocus();
            }
        }
        if (mensagem.toString().length() != 0) {
            JOptionPane.showMessageDialog(this, mensagem.toString(), "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public void eventoMudancaDeSelecao() {
        tblVendas.getSelectionModel().addListSelectionListener((ListSelectionEvent evt) -> {
            if (evt.getValueIsAdjusting()) {
                return;
            }
            modificarBotoesdeNavegacao();
        });
    }

    public FormVendas(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();

        atualizaTabela();
        HabilitarBotoesPesquisa();
        trataEdicao(false);
        limpaCamposCasoNaoHajaCadastros();
        verificarBotoesParaOutrasTabelas();
        setIcon();
        modificarBotoesdeNavegacao();
        eventoMudancaDeSelecao();

        listaVendedores.clear();
        listaVendedores.addAll(daoFuncionario.getListaVendedores());

        listaClientes.clear();
        listaClientes.addAll(daoCliente.getLista());

        listaProdutos.clear();
        listaProdutos.addAll(daoProduto.getLista());
        txtIdVenda.setEnabled(false);
        abas.setSelectedIndex(1);

        btnPesquisa.setEnabled(false);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        listaVendas = org.jdesktop.observablecollections.ObservableCollections.observableList(new ArrayList<Venda>());
        listaClientes = org.jdesktop.observablecollections.ObservableCollections.observableList(new ArrayList<Cliente>());
        listaVendedores = org.jdesktop.observablecollections.ObservableCollections.observableList(new ArrayList<Funcionario>());
        listaProdutos = org.jdesktop.observablecollections.ObservableCollections.observableList(new ArrayList<Produto>());
        converterData1 = new converter.ConverterData();
        painelNavegacao = new javax.swing.JPanel();
        btnPrimeiro = new javax.swing.JButton();
        btnAnterior = new javax.swing.JButton();
        btnProximo = new javax.swing.JButton();
        btnUltimo = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();
        abas = new javax.swing.JTabbedPane();
        abaListagem = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblVendas = new javax.swing.JTable();
        abaDados = new javax.swing.JPanel();
        painelAcoes = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        painelInformacoes = new javax.swing.JPanel();
        lblCodigo = new javax.swing.JLabel();
        txtIdVenda = new javax.swing.JTextField();
        lblDataVenda = new javax.swing.JLabel();
        javax.swing.text.MaskFormatter maskData = null;
        try{
            maskData = new javax.swing.text.MaskFormatter("##/##/####");
            maskData.setPlaceholder("_");
        }catch(Exception ex){}
        txtDataVenda = new javax.swing.JFormattedTextField(maskData);
        lblVendedor = new javax.swing.JLabel();
        lblCliente = new javax.swing.JLabel();
        cbxVendedores = new javax.swing.JComboBox<>();
        cbxClientes = new javax.swing.JComboBox<>();
        btnAddItem = new javax.swing.JButton();
        painelPagamento = new javax.swing.JPanel();
        lblValorBruto = new javax.swing.JLabel();
        cbxFormaPagamento = new javax.swing.JComboBox<>();
        lblFormaPagamento = new javax.swing.JLabel();
        txtValorParcial = new javax.swing.JTextField();
        lblDesconto = new javax.swing.JLabel();
        txtDesconto = new javax.swing.JTextField();
        lblParcelas = new javax.swing.JLabel();
        lblValorTotal = new javax.swing.JLabel();
        txtValorTotal = new javax.swing.JTextField();
        btnAplicarDesconto = new javax.swing.JButton();
        txtParcelas = new javax.swing.JTextField();
        painelPesquisa = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        javax.swing.text.MaskFormatter maskData1 = null;
        try{
            maskData1 = new javax.swing.text.MaskFormatter("##/##/####");
            maskData1.setPlaceholder("_");
        }catch(Exception ex){}
        txtDataInicial = new javax.swing.JFormattedTextField(maskData1);
        jLabel16 = new javax.swing.JLabel();
        javax.swing.text.MaskFormatter maskData2 = null;
        try{
            maskData2 = new javax.swing.text.MaskFormatter("##/##/####");
            maskData2.setPlaceholder("_");
        }catch(Exception ex){}
        txtDataFinal = new javax.swing.JFormattedTextField(maskData2);
        btnFiltrar1 = new javax.swing.JButton();
        btnRemoverFiltro1 = new javax.swing.JButton();
        painelPesquisa1 = new javax.swing.JPanel();
        lblIdPesquisa = new javax.swing.JLabel();
        btnPesquisa = new javax.swing.JButton();
        txtIdPesquisa = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                formMouseEntered(evt);
            }
        });

        painelNavegacao.setBorder(javax.swing.BorderFactory.createTitledBorder("Navegação"));
        painelNavegacao.setLayout(new java.awt.GridLayout(1, 0));

        btnPrimeiro.setText("Primeiro");
        btnPrimeiro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrimeiro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrimeiroActionPerformed(evt);
            }
        });
        painelNavegacao.add(btnPrimeiro);

        btnAnterior.setText("Anterior");
        btnAnterior.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorActionPerformed(evt);
            }
        });
        painelNavegacao.add(btnAnterior);

        btnProximo.setText("Próximo");
        btnProximo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnProximo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProximoActionPerformed(evt);
            }
        });
        painelNavegacao.add(btnProximo);

        btnUltimo.setText("Último");
        btnUltimo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnUltimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUltimoActionPerformed(evt);
            }
        });
        painelNavegacao.add(btnUltimo);

        btnFechar.setText("Fechar");
        btnFechar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharActionPerformed(evt);
            }
        });
        painelNavegacao.add(btnFechar);

        abaListagem.setLayout(new java.awt.BorderLayout());

        tblVendas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tblVendas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblVendas.getTableHeader().setReorderingAllowed(false);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listaVendas, tblVendas);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idVenda}"));
        columnBinding.setColumnName("Id Venda");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${valorBruto}"));
        columnBinding.setColumnName("Valor Bruto");
        columnBinding.setColumnClass(Double.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${valorTotal}"));
        columnBinding.setColumnName("Valor Total");
        columnBinding.setColumnClass(Double.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${vendedor}"));
        columnBinding.setColumnName("Vendedor");
        columnBinding.setColumnClass(model.Funcionario.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${cliente}"));
        columnBinding.setColumnName("Cliente");
        columnBinding.setColumnClass(model.Cliente.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${dataVendaFormatada}"));
        columnBinding.setColumnName("Data Venda");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(tblVendas);

        abaListagem.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        abas.addTab("Listagem", abaListagem);

        painelAcoes.setBorder(javax.swing.BorderFactory.createTitledBorder("Ações"));
        painelAcoes.setLayout(new java.awt.GridLayout(1, 0));

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adicionar.png"))); // NOI18N
        btnNovo.setText("Nova Venda");
        btnNovo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });
        painelAcoes.add(btnNovo);

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editar.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditar.setEnabled(false);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        painelAcoes.add(btnEditar);

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancelar.setEnabled(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        painelAcoes.add(btnCancelar);

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salvar.png"))); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalvar.setEnabled(false);
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        painelAcoes.add(btnSalvar);

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remover.png"))); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluir.setEnabled(false);
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });
        painelAcoes.add(btnExcluir);

        painelInformacoes.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações da Venda"));

        lblCodigo.setText("CÓDIGO");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblVendas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.idVenda}"), txtIdVenda, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblDataVenda.setText("DATA DA VENDA");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblVendas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.dataVenda}"), txtDataVenda, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setConverter(converterData1);
        bindingGroup.addBinding(binding);

        lblVendedor.setText("VENDEDOR");

        lblCliente.setText("CLIENTE");

        cbxVendedores.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listaVendedores, cbxVendedores);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblVendas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.vendedor}"), cbxVendedores, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbxClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listaClientes, cbxClientes);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblVendas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.cliente}"), cbxClientes, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        btnAddItem.setText("GERENCIAR ITENS  DE VENDA");
        btnAddItem.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddItem.setEnabled(false);
        btnAddItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddItemActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelInformacoesLayout = new javax.swing.GroupLayout(painelInformacoes);
        painelInformacoes.setLayout(painelInformacoesLayout);
        painelInformacoesLayout.setHorizontalGroup(
            painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInformacoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelInformacoesLayout.createSequentialGroup()
                        .addGroup(painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDataVenda)
                            .addComponent(lblVendedor)
                            .addComponent(lblCliente)
                            .addComponent(lblCodigo))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtDataVenda)
                            .addComponent(txtIdVenda)
                            .addComponent(cbxVendedores, 0, 136, Short.MAX_VALUE)
                            .addComponent(cbxClientes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(btnAddItem))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        painelInformacoesLayout.setVerticalGroup(
            painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInformacoesLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(txtIdVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDataVenda)
                    .addComponent(txtDataVenda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVendedor)
                    .addComponent(cbxVendedores, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCliente))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAddItem)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painelPagamento.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações de Pagamento"));

        lblValorBruto.setText("VALOR SEM DESCONTO");

        cbxFormaPagamento.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Á VISTA - DINHEIRO", "CARTÃO DE DÉBITO", "CARTÃO DE CRÉDITO" }));
        cbxFormaPagamento.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblVendas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.formaPagamento}"), cbxFormaPagamento, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        cbxFormaPagamento.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cbxFormaPagamentoItemStateChanged(evt);
            }
        });

        lblFormaPagamento.setText("FORMA DE PAGAMENTO");

        txtValorParcial.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblVendas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.valorBruto}"), txtValorParcial, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblDesconto.setText("DESCONTO");

        txtDesconto.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblVendas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.desconto}"), txtDesconto, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtDesconto.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtDescontoFocusLost(evt);
            }
        });
        txtDesconto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescontoKeyTyped(evt);
            }
        });

        lblParcelas.setText("QUANTIDADE DE PARCELAS");

        lblValorTotal.setText("VALOR TOTAL");

        txtValorTotal.setEditable(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblVendas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.valorTotal}"), txtValorTotal, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        btnAplicarDesconto.setText("APLICAR");
        btnAplicarDesconto.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAplicarDesconto.setEnabled(false);
        btnAplicarDesconto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAplicarDescontoActionPerformed(evt);
            }
        });

        txtParcelas.setEnabled(false);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblVendas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.parcelas}"), txtParcelas, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtParcelas.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtParcelasKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout painelPagamentoLayout = new javax.swing.GroupLayout(painelPagamento);
        painelPagamento.setLayout(painelPagamentoLayout);
        painelPagamentoLayout.setHorizontalGroup(
            painelPagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPagamentoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelPagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFormaPagamento)
                    .addComponent(lblDesconto)
                    .addComponent(lblValorTotal)
                    .addComponent(lblParcelas)
                    .addComponent(lblValorBruto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelPagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelPagamentoLayout.createSequentialGroup()
                        .addGroup(painelPagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtValorTotal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                            .addComponent(txtDesconto, javax.swing.GroupLayout.Alignment.LEADING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAplicarDesconto))
                    .addComponent(txtValorParcial, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxFormaPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(128, Short.MAX_VALUE))
        );
        painelPagamentoLayout.setVerticalGroup(
            painelPagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPagamentoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelPagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFormaPagamento)
                    .addComponent(cbxFormaPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelPagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtValorParcial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblValorBruto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(painelPagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDesconto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDesconto)
                    .addComponent(btnAplicarDesconto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelPagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblValorTotal))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelPagamentoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblParcelas)
                    .addComponent(txtParcelas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34))
        );

        javax.swing.GroupLayout abaDadosLayout = new javax.swing.GroupLayout(abaDados);
        abaDados.setLayout(abaDadosLayout);
        abaDadosLayout.setHorizontalGroup(
            abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelAcoes, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 890, Short.MAX_VALUE)
            .addGroup(abaDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(painelInformacoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(painelPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41))
        );
        abaDadosLayout.setVerticalGroup(
            abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaDadosLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(painelAcoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(painelInformacoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(painelPagamento, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        abas.addTab("Dados", abaDados);

        painelPesquisa.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtrar por Data"));

        jLabel15.setText("DATA INICIAL");

        jLabel16.setText("DATA FINAL");

        btnFiltrar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addFiltro.png"))); // NOI18N
        btnFiltrar1.setText("Aplicar Fitro");
        btnFiltrar1.setToolTipText("Informe apenas a data inicial caso deseje a lista das vendas em uma única data");
        btnFiltrar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFiltrar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrar1ActionPerformed(evt);
            }
        });

        btnRemoverFiltro1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/removeFiltro.png"))); // NOI18N
        btnRemoverFiltro1.setText("Remover Filtro");
        btnRemoverFiltro1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRemoverFiltro1.setEnabled(false);
        btnRemoverFiltro1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverFiltro1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelPesquisaLayout = new javax.swing.GroupLayout(painelPesquisa);
        painelPesquisa.setLayout(painelPesquisaLayout);
        painelPesquisaLayout.setHorizontalGroup(
            painelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPesquisaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelPesquisaLayout.createSequentialGroup()
                        .addComponent(jLabel15)
                        .addGap(18, 18, 18)
                        .addComponent(txtDataInicial))
                    .addGroup(painelPesquisaLayout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(27, 27, 27)
                        .addComponent(txtDataFinal, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)))
                .addGap(30, 30, 30)
                .addComponent(btnFiltrar1)
                .addGap(18, 18, 18)
                .addComponent(btnRemoverFiltro1)
                .addGap(2, 2, 2))
        );
        painelPesquisaLayout.setVerticalGroup(
            painelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPesquisaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelPesquisaLayout.createSequentialGroup()
                        .addGroup(painelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(txtDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(painelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel16)
                            .addComponent(txtDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelPesquisaLayout.createSequentialGroup()
                        .addGroup(painelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnFiltrar1)
                            .addComponent(btnRemoverFiltro1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(25, 25, 25))))
        );

        painelPesquisa1.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisar Venda"));

        lblIdPesquisa.setText("ID");

        btnPesquisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pesquisa.png"))); // NOI18N
        btnPesquisa.setText("Pesquisar");
        btnPesquisa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPesquisa.setEnabled(false);
        btnPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisaActionPerformed(evt);
            }
        });

        txtIdPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtIdPesquisaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtIdPesquisaKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout painelPesquisa1Layout = new javax.swing.GroupLayout(painelPesquisa1);
        painelPesquisa1.setLayout(painelPesquisa1Layout);
        painelPesquisa1Layout.setHorizontalGroup(
            painelPesquisa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPesquisa1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIdPesquisa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtIdPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPesquisa)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        painelPesquisa1Layout.setVerticalGroup(
            painelPesquisa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPesquisa1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelPesquisa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIdPesquisa)
                    .addComponent(btnPesquisa)
                    .addComponent(txtIdPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(abas)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(painelPesquisa1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(painelPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(painelNavegacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painelNavegacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(painelPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(painelPesquisa1, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(abas, javax.swing.GroupLayout.PREFERRED_SIZE, 331, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnPrimeiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimeiroActionPerformed
        // TODO add your handling code here:
        btnAnterior.setEnabled(false);
        btnPrimeiro.setEnabled(false);
        btnProximo.setEnabled(true);
        btnUltimo.setEnabled(true);
        tblVendas.setRowSelectionInterval(0, 0);
        tblVendas.scrollRectToVisible(tblVendas.getCellRect(0, 0, true));
        btnAplicarDesconto.setEnabled(false);
        txtDesconto.setEnabled(false);
        txtParcelas.setEnabled(false);
    }//GEN-LAST:event_btnPrimeiroActionPerformed

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorActionPerformed
        // TODO add your handling code here:
        btnProximo.setEnabled(true);
        btnUltimo.setEnabled(true);
        int linha = tblVendas.getSelectedRow();
        if ((linha - 1) >= 0) {
            linha--;
            if (linha == 0) {
                btnAnterior.setEnabled(false);
                btnPrimeiro.setEnabled(false);
            }
        }
        tblVendas.setRowSelectionInterval(linha, linha);
        tblVendas.scrollRectToVisible(tblVendas.getCellRect(linha, 0, true));
        btnAplicarDesconto.setEnabled(false);
        txtDesconto.setEnabled(false);
        txtParcelas.setEnabled(false);

    }//GEN-LAST:event_btnAnteriorActionPerformed

    private void btnProximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProximoActionPerformed
        // TODO add your handling code here:
        btnAnterior.setEnabled(true);
        btnPrimeiro.setEnabled(true);
        int linha = tblVendas.getSelectedRow();
        if ((linha + 1) <= (tblVendas.getRowCount() - 1)) {
            linha++;
            if (linha == tblVendas.getRowCount() - 1) {
                btnProximo.setEnabled(false);
                btnUltimo.setEnabled(false);
            }
        }
        tblVendas.setRowSelectionInterval(linha, linha);
        tblVendas.scrollRectToVisible(tblVendas.getCellRect(linha, 0, true));
        btnAplicarDesconto.setEnabled(false);
        txtDesconto.setEnabled(false);
        txtParcelas.setEnabled(false);

    }//GEN-LAST:event_btnProximoActionPerformed

    private void btnUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoActionPerformed
        // TODO add your handling code here:
        btnPrimeiro.setEnabled(true);
        btnUltimo.setEnabled(false);
        btnProximo.setEnabled(false);
        btnAnterior.setEnabled(true);
        int linha = tblVendas.getRowCount() - 1;
        tblVendas.setRowSelectionInterval(linha, linha);
        tblVendas.scrollRectToVisible(tblVendas.getCellRect(linha, 0, true));
        btnAplicarDesconto.setEnabled(false);
        txtDesconto.setEnabled(false);
        txtParcelas.setEnabled(false);

    }//GEN-LAST:event_btnUltimoActionPerformed

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnFecharActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        // TODO add your handling code here:
        Venda x = new Venda();
        listaVendas.add((Venda) x);
        int linha = listaVendas.size() - 1;
        tblVendas.setRowSelectionInterval(linha, linha);
        trataEdicao(true);
        btnAddItem.setEnabled(false);
        cbxFormaPagamento.setSelectedIndex(1);
        txtIdVenda.setText("gerado automaticamente");
        txtDataVenda.requestFocus();
        txtDesconto.setEnabled(false);
        txtParcelas.setEnabled(false);
        btnAplicarDesconto.setEnabled(false);
        cbxFormaPagamento.setEnabled(false);
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        verificarBotoesParaOutrasTabelas();
        trataEdicao(true);
        permitirDigitarParcelas();
        txtDataVenda.requestFocus();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:   

        trataEdicao(false);
        atualizaTabela();
        modificarBotoesdeNavegacao();
        limpaCamposCasoNaoHajaCadastros();
        verificarBotoesParaOutrasTabelas();
        HabilitarBotoesPesquisa();
        txtParcelas.setEnabled(false);

    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        // TODO add your handling code here:
        if (validaCampos()) {
            int linhaSelecionada = tblVendas.getSelectedRow();
            Venda obj = listaVendas.get(linhaSelecionada);
            if (Double.parseDouble(txtDesconto.getText()) == 0.0) {
                txtValorTotal.setText(txtValorParcial.getText());
            }
            daoVenda.salvar(obj);
            atualizaTabela();
            verificarBotoesParaOutrasTabelas();
            trataEdicao(false);
            HabilitarBotoesPesquisa();
            modificarBotoesdeNavegacao();
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here
        int option = JOptionPane.showOptionDialog(this, "Deseja realmente excluir essa venda ?",
                "Caixa de confirmação",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, new String[]{"Sim", "Não"}, "Sim");
        if (option == 0) {

            int linhaSelecionada = tblVendas.getSelectedRow();
            Venda venda = listaVendas.get(linhaSelecionada);
            daoVenda.remover(venda);
            atualizaTabela();

        }
        modificarBotoesdeNavegacao();
        limpaCamposCasoNaoHajaCadastros();
        verificarBotoesParaOutrasTabelas();

    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnAddItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddItemActionPerformed
        // TODO add your handling code here:

        int linhaSelecionada = tblVendas.getSelectedRow();
        Venda vendaSelecionada = listaVendas.get(linhaSelecionada);
        FormItensDaVenda form = new FormItensDaVenda(new javax.swing.JFrame(),
                true, vendaSelecionada);
        form.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        form.setTitle("Manutenção dos produtos da venda");
        form.setLocationRelativeTo(null);
        form.setResizable(false);
        form.setVisible(true);
        if (!form.isShowing()) {
            Venda obj = listaVendas.get(linhaSelecionada);
            txtValorParcial.setText(Double.toString(daoVenda.calculaValorTotal(obj)));
            txtValorTotal.setText(txtValorParcial.getText());
            txtDesconto.setText("0.0");
            daoVenda.salvar(obj);
            atualizaTabela();
        }

    }//GEN-LAST:event_btnAddItemActionPerformed

    private void btnAplicarDescontoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAplicarDescontoActionPerformed
        // TODO add your handling code here:
        double desconto = Double.parseDouble(txtDesconto.getText());
        double valorSemDesconto = Double.parseDouble(txtValorParcial.getText());
        if (desconto < valorSemDesconto) {
            txtValorTotal.setText(Double.toString(valorSemDesconto - desconto));
        } else if (desconto == 0.0) {
            txtValorTotal.setText(Double.toString(valorSemDesconto));
        } else {
            JOptionPane.showMessageDialog(this, "Não foi possível aplicar o desconto!");
        }
    }//GEN-LAST:event_btnAplicarDescontoActionPerformed

    private void formMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseEntered
        // TODO add your handling code here:
        if (txtValorParcial.getText().equals("0.0") || txtValorParcial.getText().equals("")) {
            txtDesconto.setEnabled(false);
            btnAplicarDesconto.setEnabled(false);
        }
    }//GEN-LAST:event_formMouseEntered

    private void cbxFormaPagamentoItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cbxFormaPagamentoItemStateChanged
        // TODO add your handling code here:
        if (!btnEditar.isEnabled()) {

            if (cbxFormaPagamento.getSelectedIndex() == 0
                    || cbxFormaPagamento.getSelectedIndex() == 1) {
                txtParcelas.setEnabled(false);
                txtDesconto.setEditable(true);
                txtDesconto.setEnabled(true);
                btnAplicarDesconto.setEnabled(true);

            }

            if (cbxFormaPagamento.getSelectedIndex() == 2) {
                txtParcelas.setEnabled(true);
                txtDesconto.setEditable(false);
                txtDesconto.setEnabled(false);
                btnAplicarDesconto.setEnabled(false);
                txtDesconto.setText("0.0");
                txtValorTotal.setText(txtValorParcial.getText());
            }
        }

    }//GEN-LAST:event_cbxFormaPagamentoItemStateChanged

    private void btnFiltrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrar1ActionPerformed

        // TODO add your handling code here:
        Calendar dataInicial = null;
        Calendar dataFinal = null;

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        sdf.setLenient(false);
        try {
            sdf.parse(txtDataFinal.getText());
            sdf.parse(txtDataInicial.getText());
            dataInicial = (Calendar) converterData1.convertReverse(txtDataInicial.getText());
            dataFinal = (Calendar) converterData1.convertReverse(txtDataFinal.getText());
        } catch (Exception e) {
            dataInicial = (Calendar) converterData1.convertReverse(txtDataInicial.getText());
            dataFinal = dataInicial;
        }

        ArrayList<Venda> listaFiltrada = null;
        try {
            listaFiltrada = daoVenda.filtrarPorData(dataInicial, dataFinal);
            if (!listaFiltrada.isEmpty()) {
                listaVendas.clear();
                listaVendas.addAll(listaFiltrada);
                tblVendas.setRowSelectionInterval(0, 0);
                btnRemoverFiltro1.setEnabled(true);
                abas.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(this, "Não há nenhuma venda com essa data.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Informe ao menos a data inicial para filtrar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
        }

    }//GEN-LAST:event_btnFiltrar1ActionPerformed

    private void btnRemoverFiltro1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverFiltro1ActionPerformed
        // TODO add your handling code here:
        atualizaTabela();
        txtDataInicial.setText("");
        txtDataFinal.setText("");
        JOptionPane.showMessageDialog(this, "Filtro removido");
        btnRemoverFiltro1.setEnabled(false);
    }//GEN-LAST:event_btnRemoverFiltro1ActionPerformed

    private void btnPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisaActionPerformed
        // TODO add your handling code here

        int index = listaVendas.indexOf(daoVenda.localizar(Integer.parseInt(txtIdPesquisa.getText())));

        tblVendas.changeSelection(index, 1, false, false);

        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Não foi possível localizar essa venda!",
                    "Aviso!", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnPesquisaActionPerformed

    private void txtIdPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdPesquisaKeyReleased
        // TODO add your handling code here:
        if (txtIdPesquisa.getText().length() > 0) {
            btnPesquisa.setEnabled(true);
        } else {
            btnPesquisa.setEnabled(false);
        }
    }//GEN-LAST:event_txtIdPesquisaKeyReleased

    private void txtDescontoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescontoKeyTyped
        // TODO add your handling code here:
        digitarSomenteNumeros(evt);

    }//GEN-LAST:event_txtDescontoKeyTyped

    private void txtParcelasKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtParcelasKeyTyped
        // TODO add your handling code here:
        digitarSomenteNumeros(evt);
    }//GEN-LAST:event_txtParcelasKeyTyped

    private void txtIdPesquisaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdPesquisaKeyTyped
        // TODO add your handling code here:
        String caracteres = "0123456789";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_txtIdPesquisaKeyTyped

    private void txtDescontoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txtDescontoFocusLost
        // TODO add your handling code here:
        double desconto = Double.parseDouble(txtDesconto.getText());
        double valorSemDesconto = Double.parseDouble(txtValorParcial.getText());
        if (desconto < valorSemDesconto) {
            txtValorTotal.setText(Double.toString(valorSemDesconto - desconto));
        } else if (desconto == 0.0) {
            txtValorTotal.setText(Double.toString(valorSemDesconto));
        } else {
            JOptionPane.showMessageDialog(this, "Não foi possível aplicar o desconto!");
        }
    }//GEN-LAST:event_txtDescontoFocusLost

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;

                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormVendas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormVendas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormVendas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormVendas.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FormVendas dialog = new FormVendas(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel abaDados;
    private javax.swing.JPanel abaListagem;
    private javax.swing.JTabbedPane abas;
    private javax.swing.JButton btnAddItem;
    private javax.swing.JButton btnAnterior;
    private javax.swing.JButton btnAplicarDesconto;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnFiltrar1;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisa;
    private javax.swing.JButton btnPrimeiro;
    private javax.swing.JButton btnProximo;
    private javax.swing.JButton btnRemoverFiltro1;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnUltimo;
    private javax.swing.JComboBox<String> cbxClientes;
    private javax.swing.JComboBox<String> cbxFormaPagamento;
    private javax.swing.JComboBox<String> cbxVendedores;
    private converter.ConverterData converterData1;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblDataVenda;
    private javax.swing.JLabel lblDesconto;
    private javax.swing.JLabel lblFormaPagamento;
    private javax.swing.JLabel lblIdPesquisa;
    private javax.swing.JLabel lblParcelas;
    private javax.swing.JLabel lblValorBruto;
    private javax.swing.JLabel lblValorTotal;
    private javax.swing.JLabel lblVendedor;
    private java.util.List<Cliente> listaClientes;
    private java.util.List<Produto> listaProdutos;
    private java.util.List<Venda> listaVendas;
    private java.util.List<Funcionario> listaVendedores;
    private javax.swing.JPanel painelAcoes;
    private javax.swing.JPanel painelInformacoes;
    private javax.swing.JPanel painelNavegacao;
    private javax.swing.JPanel painelPagamento;
    private javax.swing.JPanel painelPesquisa;
    private javax.swing.JPanel painelPesquisa1;
    private javax.swing.JTable tblVendas;
    private javax.swing.JFormattedTextField txtDataFinal;
    private javax.swing.JFormattedTextField txtDataInicial;
    private javax.swing.JFormattedTextField txtDataVenda;
    private javax.swing.JTextField txtDesconto;
    private javax.swing.JTextField txtIdPesquisa;
    private javax.swing.JTextField txtIdVenda;
    private javax.swing.JTextField txtParcelas;
    private javax.swing.JTextField txtValorParcial;
    private javax.swing.JTextField txtValorTotal;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
