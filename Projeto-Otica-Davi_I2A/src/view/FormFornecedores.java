/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DAOFornecedor;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import model.Fornecedor;
import validations.ValidaCNPJ;

/**
 *
 * @author DAVI
 */
public class FormFornecedores extends javax.swing.JDialog {

    DAOFornecedor daoFornecedor = new DAOFornecedor();

    /**
     * Creates new form FormFornecedores
     */
    public void atualizaTabela() {

        int linhaTabelaAux = tblFornecedores.getSelectedRow();
        listaFornecedores.clear();
        listaFornecedores.addAll(daoFornecedor.getLista());

        if (!listaFornecedores.isEmpty()) {
            if (linhaTabelaAux < 0) {
                tblFornecedores.changeSelection(0, 0, false, false);
            } else if (listaFornecedores.size() == linhaTabelaAux) {
                tblFornecedores.changeSelection(listaFornecedores.size() - 1, 0, false, false);
            } else if (listaFornecedores.get(linhaTabelaAux).getIdFornecedor() != null) {
                tblFornecedores.changeSelection(linhaTabelaAux, 0, false, false);
            }
        }
    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/oculos.png")));
    }

    public void limpaCamposCasoNaoHajaCadastros() {
        if (this.listaFornecedores.isEmpty()) {
            txtIdFornecedor.setText("");
            txtBairro.setText("");
            txtCep.setText("");
            txtCidade.setText("");
            txtComplemento.setText("");
            txtCnpj.setText("");
            txtEmail.setText("");
            txtRazaoSocial.setText("");
            txtnomeFantasia.setText("");
            txtNumero.setText("");
            txtRua.setText("");
            txtTelefone.setText("");
            cbxUf.setSelectedItem("");
            btnEditar.setEnabled(false);
            btnExcluir.setEnabled(false);
        }
    }

    public void HabilitarBotoesPesquisa() {
        if (listaFornecedores.isEmpty()) {
            txtCnpjPesquisa.setEnabled(false);
            btnPesquisa.setEnabled(false);
        } else {
            txtCnpjPesquisa.setEnabled(true);
            btnPesquisa.setEnabled(true);
        }
    }

    public void modificarBotoesdeNavegacao() {
        if (tblFornecedores.getRowCount() == 1
                || listaFornecedores.isEmpty()) {
            btnPrimeiro.setEnabled(false);
            btnAnterior.setEnabled(false);
            btnProximo.setEnabled(false);
            btnUltimo.setEnabled(false);

        } else if (tblFornecedores.getSelectedRow() == 0) {
            btnPrimeiro.setEnabled(false);
            btnAnterior.setEnabled(false);
            btnProximo.setEnabled(true);
            btnUltimo.setEnabled(true);
        } else if (tblFornecedores.getSelectedRow()
                == listaFornecedores.size() - 1) {
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
        txtBairro.setEditable(editando);
        txtCep.setEditable(editando);
        txtCidade.setEditable(editando);
        txtComplemento.setEditable(editando);
        txtEmail.setEditable(editando);
        txtNumero.setEditable(editando);
        txtRua.setEditable(editando);
        txtTelefone.setEditable(editando);
        abas.setEnabledAt(0, !editando);
        cbxUf.setEnabled(editando);
        txtCnpj.setEditable(editando);
        txtRazaoSocial.setEditable(editando);
        txtnomeFantasia.setEditable(editando);
        txtCnpjPesquisa.setEditable(!editando);
        btnPesquisa.setEnabled(!editando);

    }

    public boolean validaCampos() {
        StringBuilder mensagem = new StringBuilder();

        if (txtEmail.getText().length() > 0 && txtEmail.getText().contains("@") == false) {
            mensagem.append("Informe um email válido!\n");
            txtEmail.requestFocus();
        }
        if (txtTelefone.getText().trim().replace(" ", "").length() != 14) {
            mensagem.append("Informe um telefone válido!\n");
            txtTelefone.requestFocus();
        }
        if (ValidaCNPJ.isCNPJ(txtCnpj.getText()) == false) {
            mensagem.append("Informe um CNPJ válido!\n");
            txtCnpj.requestFocus();
        } else {
            for (Fornecedor f : listaFornecedores) {
                if (f != listaFornecedores.get(tblFornecedores.getSelectedRow())) {
                    if (f.getCnpj().equals(txtCnpj.getText())) {
                        mensagem.append("o CNPJ não pode ser igual ao de outro fornecedors!\n");
                        txtCnpj.requestFocus();
                    }
                }

            }
        }
        if (txtnomeFantasia.getText().isEmpty()) {
            mensagem.append("Informe o nome fantasia do fornecedor!\n");
            txtnomeFantasia.requestFocus();
        }
        if (txtRazaoSocial.getText().isEmpty()) {
            mensagem.append("Informe a razão social do fornecedor!\n");
            txtnomeFantasia.requestFocus();
        }
        if (mensagem.toString().length() != 0) {
            JOptionPane.showMessageDialog(this, mensagem.toString(), "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public void eventoMudancaDeSelecao() {
        tblFornecedores.getSelectionModel().addListSelectionListener((ListSelectionEvent evt) -> {
            if (evt.getValueIsAdjusting()) {
                return;
            }
            modificarBotoesdeNavegacao();
        });
    }

    public FormFornecedores(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setIcon();
        atualizaTabela();
        HabilitarBotoesPesquisa();
        trataEdicao(false);
        modificarBotoesdeNavegacao();
        limpaCamposCasoNaoHajaCadastros();
        eventoMudancaDeSelecao();
        txtIdFornecedor.setEnabled(false);
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

        listaFornecedores = org.jdesktop.observablecollections.ObservableCollections.observableList(new ArrayList<Fornecedor>());
        painelNavegacao = new javax.swing.JPanel();
        btnPrimeiro = new javax.swing.JButton();
        btnAnterior = new javax.swing.JButton();
        btnProximo = new javax.swing.JButton();
        btnUltimo = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();
        abas = new javax.swing.JTabbedPane();
        abaListagem = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFornecedores = new javax.swing.JTable();
        abaDados = new javax.swing.JPanel();
        painelAcoes = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        painelEndereco = new javax.swing.JPanel();
        lblRua = new javax.swing.JLabel();
        txtRua = new javax.swing.JTextField();
        lblBairro = new javax.swing.JLabel();
        lblCep = new javax.swing.JLabel();
        txtComplemento = new javax.swing.JTextField();
        javax.swing.text.MaskFormatter maskCep = null;
        try{
            maskCep = new javax.swing.text.MaskFormatter("#####-###");
            maskCep.setPlaceholder("_");
        }catch(Exception ex){}
        txtCep = new javax.swing.JFormattedTextField(maskCep);
        txtBairro = new javax.swing.JTextField();
        lblNúmero = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        lblComplemento = new javax.swing.JLabel();
        txtCidade = new javax.swing.JTextField();
        lblCidade = new javax.swing.JLabel();
        lblUf = new javax.swing.JLabel();
        cbxUf = new javax.swing.JComboBox<>();
        painelInformacoes = new javax.swing.JPanel();
        lblCodigo = new javax.swing.JLabel();
        txtIdFornecedor = new javax.swing.JTextField();
        lblCnpj = new javax.swing.JLabel();
        javax.swing.text.MaskFormatter maskCnpj = null;
        try{
            maskCnpj = new javax.swing.text.MaskFormatter("##.###.###/####-##");
            maskCnpj.setPlaceholder("_");
        }catch(Exception ex){}
        txtCnpj = new javax.swing.JFormattedTextField(maskCnpj);
        lblRazaoSocial = new javax.swing.JLabel();
        lblNomeFantasia = new javax.swing.JLabel();
        txtnomeFantasia = new javax.swing.JTextField();
        txtRazaoSocial = new javax.swing.JTextField();
        painelContato = new javax.swing.JPanel();
        lblTelefone = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        javax.swing.text.MaskFormatter maskTelefone = null;
        try{
            maskTelefone = new javax.swing.text.MaskFormatter("(##) # ####-####");
            maskTelefone.setPlaceholder("_");
        }catch(Exception ex){}
        txtTelefone = new javax.swing.JFormattedTextField(maskTelefone);
        txtEmail = new javax.swing.JTextField();
        painelPesquisa = new javax.swing.JPanel();
        lblCnpjPesquisa = new javax.swing.JLabel();
        btnPesquisa = new javax.swing.JButton();
        javax.swing.text.MaskFormatter maskCnpj1 = null;
        try{
            maskCnpj1 = new javax.swing.text.MaskFormatter("##.###.###/####-##");
            maskCnpj1.setPlaceholder("_");
        }catch(Exception ex){}
        txtCnpjPesquisa = new javax.swing.JFormattedTextField(maskCnpj1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

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

        tblFornecedores.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tblFornecedores.getTableHeader().setReorderingAllowed(false);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listaFornecedores, tblFornecedores);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idFornecedor}"));
        columnBinding.setColumnName("Id Fornecedor");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nomeFantasia}"));
        columnBinding.setColumnName("Nome Fantasia");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${razaoSocial}"));
        columnBinding.setColumnName("Razao Social");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${telefone}"));
        columnBinding.setColumnName("Telefone");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(tblFornecedores);

        abaListagem.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        abas.addTab("Listagem", abaListagem);

        painelAcoes.setBorder(javax.swing.BorderFactory.createTitledBorder("Ações"));
        painelAcoes.setLayout(new java.awt.GridLayout(1, 0));

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adicionar.png"))); // NOI18N
        btnNovo.setText("Novo Fornecedor");
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

        painelEndereco.setBorder(javax.swing.BorderFactory.createTitledBorder("Endereço"));

        lblRua.setText("RUA");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblFornecedores, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.rua}"), txtRua, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblBairro.setText("BAIRRO");

        lblCep.setText("CEP");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblFornecedores, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.complemento}"), txtComplemento, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblFornecedores, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.cep}"), txtCep, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblFornecedores, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.bairro}"), txtBairro, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblNúmero.setText("NÚMERO");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblFornecedores, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.numero}"), txtNumero, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtNumero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroKeyTyped(evt);
            }
        });

        lblComplemento.setText("COMPLEMENTO");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblFornecedores, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.cidade}"), txtCidade, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblCidade.setText("CIDADE");

        lblUf.setText("UF");

        cbxUf.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AC", "AM", "AP", "RO", "RR", "DF", "MG", "SP", "RJ", "ES", "SE", "CE", "BA", "RS", "MT", "MS", "GO", "SC", "TO", "PR", "PA", "RN", "AL", "MA", "PB", "PI", "PE" }));
        cbxUf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblFornecedores, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.uf}"), cbxUf, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout painelEnderecoLayout = new javax.swing.GroupLayout(painelEndereco);
        painelEndereco.setLayout(painelEnderecoLayout);
        painelEnderecoLayout.setHorizontalGroup(
            painelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelEnderecoLayout.createSequentialGroup()
                .addGroup(painelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelEnderecoLayout.createSequentialGroup()
                        .addGroup(painelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNúmero)
                            .addComponent(lblCep)
                            .addComponent(lblBairro)
                            .addComponent(lblRua))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelEnderecoLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblComplemento)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCep, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                    .addComponent(txtBairro)
                    .addComponent(txtRua)
                    .addComponent(txtComplemento))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(painelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblUf)
                    .addComponent(lblCidade))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxUf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        painelEnderecoLayout.setVerticalGroup(
            painelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelEnderecoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(painelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblRua)
                        .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(lblCidade))
                    .addComponent(txtRua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(painelEnderecoLayout.createSequentialGroup()
                        .addGroup(painelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblBairro)
                            .addComponent(lblUf)
                            .addComponent(cbxUf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addComponent(lblCep))
                    .addGroup(painelEnderecoLayout.createSequentialGroup()
                        .addComponent(txtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(txtCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNúmero))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblComplemento)))
        );

        painelInformacoes.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações do Fornecedor"));

        lblCodigo.setText("CÓDIGO");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblFornecedores, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.idFornecedor}"), txtIdFornecedor, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblCnpj.setText("CNPJ");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblFornecedores, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.cnpj}"), txtCnpj, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        lblRazaoSocial.setText("RAZÃO SOCIAL");

        lblNomeFantasia.setText("NOME FANTASIA");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblFornecedores, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.nomeFantasia}"), txtnomeFantasia, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblFornecedores, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.razaoSocial}"), txtRazaoSocial, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout painelInformacoesLayout = new javax.swing.GroupLayout(painelInformacoes);
        painelInformacoes.setLayout(painelInformacoesLayout);
        painelInformacoesLayout.setHorizontalGroup(
            painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInformacoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCodigo)
                    .addComponent(lblCnpj)
                    .addComponent(lblRazaoSocial)
                    .addComponent(lblNomeFantasia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtIdFornecedor, javax.swing.GroupLayout.DEFAULT_SIZE, 134, Short.MAX_VALUE)
                    .addComponent(txtCnpj)
                    .addComponent(txtnomeFantasia)
                    .addComponent(txtRazaoSocial))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        painelInformacoesLayout.setVerticalGroup(
            painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInformacoesLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(txtIdFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCnpj)
                    .addComponent(txtCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRazaoSocial)
                    .addComponent(txtRazaoSocial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNomeFantasia)
                    .addComponent(txtnomeFantasia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        painelContato.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações de Contato"));

        lblTelefone.setText("TELEFONE");

        lblEmail.setText("E-MAIL");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblFornecedores, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.telefone}"), txtTelefone, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblFornecedores, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.email}"), txtEmail, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout painelContatoLayout = new javax.swing.GroupLayout(painelContato);
        painelContato.setLayout(painelContatoLayout);
        painelContatoLayout.setHorizontalGroup(
            painelContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelContatoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(painelContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblTelefone)
                    .addComponent(lblEmail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtTelefone, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                    .addComponent(txtEmail))
                .addGap(22, 22, 22))
        );
        painelContatoLayout.setVerticalGroup(
            painelContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelContatoLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(painelContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTelefone)
                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(7, 7, 7)
                .addGroup(painelContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmail))
                .addContainerGap())
        );

        javax.swing.GroupLayout abaDadosLayout = new javax.swing.GroupLayout(abaDados);
        abaDados.setLayout(abaDadosLayout);
        abaDadosLayout.setHorizontalGroup(
            abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelAcoes, javax.swing.GroupLayout.DEFAULT_SIZE, 937, Short.MAX_VALUE)
            .addGroup(abaDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(painelContato, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(painelInformacoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(37, 37, 37)
                .addComponent(painelEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        abaDadosLayout.setVerticalGroup(
            abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaDadosLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(painelAcoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(abaDadosLayout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(painelInformacoes, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(painelContato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(abaDadosLayout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(painelEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        abas.addTab("Dados", abaDados);

        painelPesquisa.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisar Fornecedor"));

        lblCnpjPesquisa.setText("CNPJ");

        btnPesquisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pesquisa.png"))); // NOI18N
        btnPesquisa.setText("Pesquisar");
        btnPesquisa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPesquisa.setEnabled(false);
        btnPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisaActionPerformed(evt);
            }
        });

        txtCnpjPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCnpjPesquisaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCnpjPesquisaKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout painelPesquisaLayout = new javax.swing.GroupLayout(painelPesquisa);
        painelPesquisa.setLayout(painelPesquisaLayout);
        painelPesquisaLayout.setHorizontalGroup(
            painelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPesquisaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblCnpjPesquisa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCnpjPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnPesquisa)
                .addContainerGap())
        );
        painelPesquisaLayout.setVerticalGroup(
            painelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPesquisaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCnpjPesquisa)
                    .addComponent(btnPesquisa)
                    .addComponent(txtCnpjPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(painelNavegacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(abas))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(painelPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(290, 290, 290))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painelNavegacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(painelPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(abas, javax.swing.GroupLayout.DEFAULT_SIZE, 382, Short.MAX_VALUE))
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
        tblFornecedores.setRowSelectionInterval(0, 0);
        tblFornecedores.scrollRectToVisible(tblFornecedores.getCellRect(0, 0, true));
    }//GEN-LAST:event_btnPrimeiroActionPerformed

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorActionPerformed
        // TODO add your handling code here:
        btnProximo.setEnabled(true);
        btnUltimo.setEnabled(true);
        int linha = tblFornecedores.getSelectedRow();
        if ((linha - 1) >= 0) {
            linha--;
            if (linha == 0) {
                btnAnterior.setEnabled(false);
                btnPrimeiro.setEnabled(false);
            }
        }
        tblFornecedores.setRowSelectionInterval(linha, linha);
        tblFornecedores.scrollRectToVisible(tblFornecedores.getCellRect(linha, 0, true));
    }//GEN-LAST:event_btnAnteriorActionPerformed

    private void btnProximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProximoActionPerformed
        // TODO add your handling code here:
        btnAnterior.setEnabled(true);
        btnPrimeiro.setEnabled(true);
        int linha = tblFornecedores.getSelectedRow();
        if ((linha + 1) <= (tblFornecedores.getRowCount() - 1)) {
            linha++;
            if (linha == tblFornecedores.getRowCount() - 1) {
                btnProximo.setEnabled(false);
                btnUltimo.setEnabled(false);
            }
        }
        tblFornecedores.setRowSelectionInterval(linha, linha);
        tblFornecedores.scrollRectToVisible(tblFornecedores.getCellRect(linha, 0, true));
    }//GEN-LAST:event_btnProximoActionPerformed

    private void btnUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoActionPerformed
        // TODO add your handling code here:
        btnPrimeiro.setEnabled(true);
        btnUltimo.setEnabled(false);
        btnProximo.setEnabled(false);
        btnAnterior.setEnabled(true);
        int linha = tblFornecedores.getRowCount() - 1;
        tblFornecedores.setRowSelectionInterval(linha, linha);
        tblFornecedores.scrollRectToVisible(tblFornecedores.getCellRect(linha, 0, true));
    }//GEN-LAST:event_btnUltimoActionPerformed

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnFecharActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        // TODO add your handling code here:
        Fornecedor x = new Fornecedor();
        listaFornecedores.add((Fornecedor) x);
        int linha = listaFornecedores.size() - 1;
        tblFornecedores.setRowSelectionInterval(linha, linha);
        trataEdicao(true);
        txtIdFornecedor.setText("gerado automaticamente");
        txtCnpj.requestFocus();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        trataEdicao(true);
        txtCnpj.requestFocus();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        trataEdicao(false);
        atualizaTabela();
        modificarBotoesdeNavegacao();
        limpaCamposCasoNaoHajaCadastros();
        HabilitarBotoesPesquisa();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        // TODO add your handling code here:
        if (validaCampos()) {
            int linhaSelecionada = tblFornecedores.getSelectedRow();
            Fornecedor obj = listaFornecedores.get(linhaSelecionada);
            daoFornecedor.salvar(obj);
            trataEdicao(false);
            atualizaTabela();
            modificarBotoesdeNavegacao();
            HabilitarBotoesPesquisa();
        }

    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here
        int option = JOptionPane.showOptionDialog(this, "Deseja realmente excluir esse fornecedor ?",
                "Caixa de confirmação",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, new String[]{"Sim", "Não"}, "Sim");
        if (option == 0) {

            int linhaSelecionada = tblFornecedores.getSelectedRow();
            Fornecedor fornecedor = listaFornecedores.get(linhaSelecionada);
            daoFornecedor.remover(fornecedor);
            atualizaTabela();

        }
        modificarBotoesdeNavegacao();
        limpaCamposCasoNaoHajaCadastros();
        HabilitarBotoesPesquisa();

    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisaActionPerformed
        // TODO add your handling code here

        int index = listaFornecedores.indexOf(daoFornecedor.pesquisar(txtCnpjPesquisa.getText()));
        tblFornecedores.changeSelection(index, 1, false, false);

        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Não foi possível localizar esse fornecedor!",
                    "Aviso!", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnPesquisaActionPerformed

    private void txtCnpjPesquisaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCnpjPesquisaKeyTyped
        // TODO add your handling code here:
        if (ValidaCNPJ.isCNPJ(txtCnpjPesquisa.getText()) == true) {
            btnPesquisa.setEnabled(true);
        } else {
            btnPesquisa.setEnabled(false);
        }
    }//GEN-LAST:event_txtCnpjPesquisaKeyTyped

    private void txtCnpjPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCnpjPesquisaKeyReleased
        // TODO add your handling code here:
        if (ValidaCNPJ.isCNPJ(txtCnpjPesquisa.getText()) == true) {
            btnPesquisa.setEnabled(true);
        } else {
            btnPesquisa.setEnabled(false);
        }
    }//GEN-LAST:event_txtCnpjPesquisaKeyReleased

    private void txtNumeroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroKeyTyped
        // TODO add your handling code here:
        String caracteres = "0123456789";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_txtNumeroKeyTyped

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
            java.util.logging.Logger.getLogger(FormFornecedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormFornecedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormFornecedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormFornecedores.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FormFornecedores dialog = new FormFornecedores(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnAnterior;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisa;
    private javax.swing.JButton btnPrimeiro;
    private javax.swing.JButton btnProximo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnUltimo;
    private javax.swing.JComboBox<String> cbxUf;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBairro;
    private javax.swing.JLabel lblCep;
    private javax.swing.JLabel lblCidade;
    private javax.swing.JLabel lblCnpj;
    private javax.swing.JLabel lblCnpjPesquisa;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblComplemento;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblNomeFantasia;
    private javax.swing.JLabel lblNúmero;
    private javax.swing.JLabel lblRazaoSocial;
    private javax.swing.JLabel lblRua;
    private javax.swing.JLabel lblTelefone;
    private javax.swing.JLabel lblUf;
    private java.util.List<Fornecedor> listaFornecedores;
    private javax.swing.JPanel painelAcoes;
    private javax.swing.JPanel painelContato;
    private javax.swing.JPanel painelEndereco;
    private javax.swing.JPanel painelInformacoes;
    private javax.swing.JPanel painelNavegacao;
    private javax.swing.JPanel painelPesquisa;
    private javax.swing.JTable tblFornecedores;
    private javax.swing.JTextField txtBairro;
    private javax.swing.JFormattedTextField txtCep;
    private javax.swing.JTextField txtCidade;
    private javax.swing.JFormattedTextField txtCnpj;
    private javax.swing.JFormattedTextField txtCnpjPesquisa;
    private javax.swing.JTextField txtComplemento;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtIdFornecedor;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JTextField txtRazaoSocial;
    private javax.swing.JTextField txtRua;
    private javax.swing.JFormattedTextField txtTelefone;
    private javax.swing.JTextField txtnomeFantasia;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
