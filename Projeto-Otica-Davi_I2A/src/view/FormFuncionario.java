/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DAOCargo;
import dao.DAOFuncionario;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import model.Cargo;
import model.Funcionario;
import validations.ValidaCPF;

/**
 *
 * @author DAVI
 */
public class FormFuncionario extends javax.swing.JDialog {

    /**
     * Creates new form FormFuncionario
     */
    DAOFuncionario daoFuncionario = new DAOFuncionario();
    DAOCargo daoCargo = new DAOCargo();

    public void atualizaTabela() {

        int linhaTabelaAux = tblFuncionarios.getSelectedRow();
        listaFuncionarios.clear();
        listaFuncionarios.addAll(daoFuncionario.getLista());

        if (!listaFuncionarios.isEmpty()) {
            if (linhaTabelaAux < 0) {
                tblFuncionarios.changeSelection(0, 0, false, false);
            } else if (listaFuncionarios.size() == linhaTabelaAux) {
                tblFuncionarios.changeSelection(listaFuncionarios.size() - 1, 0, false, false);
            } else if (listaFuncionarios.get(linhaTabelaAux).getIdFuncionario() != null) {
                tblFuncionarios.changeSelection(linhaTabelaAux, 0, false, false);
            }
        }
    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/oculos.png")));
    }

    public void verificarBotoesParaOutrasTabelas() {
        if (listaFuncionarios.isEmpty()) {
            btnDependentes.setEnabled(false);
        } else {
            btnDependentes.setEnabled(true);
        }

    }

    public void HabilitarBotoesPesquisa() {
        if (listaFuncionarios.isEmpty()) {
            txtCpfPesquisa.setEnabled(false);
            btnPesquisa.setEnabled(false);
            cbxCargoParaFiltro.setEnabled(false);
            btnFiltrar.setEnabled(false);
        } else {
            txtCpfPesquisa.setEnabled(true);
            btnPesquisa.setEnabled(true);
            cbxCargoParaFiltro.setEnabled(true);
            btnFiltrar.setEnabled(true);
        }
    }

    public void limpaCamposCasoNaoHajaCadastros() {
        if (this.tblFuncionarios.getRowCount() == 0) {
            txtIdFuncionario.setText("");
            txtBairro.setText("");
            txtCep.setText("");
            txtCidade.setText("");
            txtComplemento.setText("");
            txtCpf.setText("");
            txtDataNascimento.setText("");
            txtEmail.setText("");
            txtNome.setText("");
            txtNumero.setText("");
            txtRua.setText("");
            txtTelefone.setText("");
            cbxUf.setSelectedItem("");
            txtDataAdmissao.setText("");
            cbxCargo.setSelectedItem("");
            txtSalario.setText("");
            btnEditar.setEnabled(false);
            btnExcluir.setEnabled(false);
        }
    }

    public void modificarBotoesdeNavegacao() {

        if (tblFuncionarios.getRowCount() == 1
                || listaFuncionarios.isEmpty()) {
            btnPrimeiro.setEnabled(false);
            btnAnterior.setEnabled(false);
            btnProximo.setEnabled(false);
            btnUltimo.setEnabled(false);

        } else if (tblFuncionarios.getSelectedRow() == 0) {
            btnPrimeiro.setEnabled(false);
            btnAnterior.setEnabled(false);
            btnProximo.setEnabled(true);
            btnUltimo.setEnabled(true);
        } else if (tblFuncionarios.getSelectedRow()
                == listaFuncionarios.size() - 1) {
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
        btnPesquisa.setEnabled(!editando);
        btnUltimo.setEnabled(!editando);
        txtCpfPesquisa.setEditable(!editando);
        txtBairro.setEditable(editando);
        txtCep.setEditable(editando);
        txtCidade.setEditable(editando);
        txtComplemento.setEditable(editando);
        txtCpf.setEditable(editando);
        txtDataNascimento.setEditable(editando);
        txtEmail.setEditable(editando);
        txtNome.setEditable(editando);
        txtNumero.setEditable(editando);
        txtRua.setEditable(editando);
        txtTelefone.setEditable(editando);
        tblFuncionarios.setEnabled(!editando);
        cbxUf.setEnabled(editando);
        txtDataAdmissao.setEditable(editando);
        cbxCargo.setEnabled(editando);
        txtSalario.setEditable(editando);
        btnFiltrar.setEnabled(!editando);
        cbxCargoParaFiltro.setEnabled(!editando);
        abas.setEnabledAt(0, !editando);

    }

    public void digitarSomenteNumeros(java.awt.event.KeyEvent evt) {
        String caracteres = "0123456789.";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }

    public void digitarSomenteLetras(java.awt.event.KeyEvent evt) {
        String caracteres = "0123456789.";
        if (caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }

    public void eventoMudancaDeSelecao() {
        tblFuncionarios.getSelectionModel().addListSelectionListener((ListSelectionEvent evt) -> {
            if (evt.getValueIsAdjusting()) {
                return;
            }
            modificarBotoesdeNavegacao();
        });
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
        if ((txtDataNascimento.getText().length() > 0)) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            try {
                sdf.parse(txtDataNascimento.getText());
            } catch (Exception e) {
                mensagem.append("Informe a data de Nascimento do funcionário!\n");
                txtDataNascimento.requestFocus();
            }

        }
        if ((txtDataAdmissao.getText().length() > 0)) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            try {
                sdf.parse(txtDataAdmissao.getText());
            } catch (Exception e) {
                mensagem.append("Informe a data de admissão do funcionário!\n");
                txtDataAdmissao.requestFocus();
            }

        }
        if (ValidaCPF.isCPF(txtCpf.getText()) == false) {
            mensagem.append("Informe um CPF válido!\n");
            txtCpf.requestFocus();
        } else {
            for (Funcionario f : listaFuncionarios) {
                if (f != listaFuncionarios.get(tblFuncionarios.getSelectedRow())) {
                    if (f.getCpf().equals(txtCpf.getText())) {
                        mensagem.append("o CPF não pode ser igual ao de outro funcionário!\n");
                        txtCpf.requestFocus();
                    }
                }

            }
        }
        if (txtNome.getText().isEmpty()) {
            mensagem.append("Informe o nome do funcionário!\n");
            txtNome.requestFocus();
        }
        if (txtSalario.getText().length() > 0) {
            try {
                Double.parseDouble(txtSalario.getText());
                if (txtSalario.getText().equals("0.0")) {
                    mensagem.append("Informe o salario do funcionário!\n");
                }
            } catch (Exception e) {
                mensagem.append("Informe o salario do funcionário!\n");
            }
        }
        if (cbxCargo.getSelectedItem() == null) {
            mensagem.append("Informe o cargo do funcionário!\n");
        }
        if (mensagem.toString().length() != 0) {
            JOptionPane.showMessageDialog(this, mensagem.toString(), "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public FormFuncionario(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setIcon();
        atualizaTabela();
        trataEdicao(false);
        HabilitarBotoesPesquisa();
        verificarBotoesParaOutrasTabelas();
        modificarBotoesdeNavegacao();
        limpaCamposCasoNaoHajaCadastros();
        eventoMudancaDeSelecao();
        listaCargos.clear();
        listaCargos.addAll(daoCargo.getLista());
        abas.setSelectedIndex(1);
        txtIdFuncionario.setEnabled(false);
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

        listaFuncionarios = org.jdesktop.observablecollections.ObservableCollections.observableList(new ArrayList<Funcionario>());
        listaCargos = org.jdesktop.observablecollections.ObservableCollections.observableList(new ArrayList<Cargo>());
        converterData1 = new converter.ConverterData();
        painelNavegacao = new javax.swing.JPanel();
        btnPrimeiro = new javax.swing.JButton();
        btnAnterior = new javax.swing.JButton();
        btnProximo = new javax.swing.JButton();
        btnUltimo = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();
        painelPesquisa = new javax.swing.JPanel();
        lblCpfPesquisa = new javax.swing.JLabel();
        btnPesquisa = new javax.swing.JButton();
        javax.swing.text.MaskFormatter maskCpf1 = null;
        try{
            maskCpf1 = new javax.swing.text.MaskFormatter("###.###.###-##");
            maskCpf1.setPlaceholder("_");
        }catch(Exception ex){}
        txtCpfPesquisa = new javax.swing.JFormattedTextField(maskCpf1);
        abas = new javax.swing.JTabbedPane();
        abaListagem = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFuncionarios = new javax.swing.JTable();
        abaDados = new javax.swing.JPanel();
        painelAcoes = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
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
        painelInformacoes = new javax.swing.JPanel();
        txtIdFuncionario = new javax.swing.JTextField();
        lblCodigo = new javax.swing.JLabel();
        lblNome = new javax.swing.JLabel();
        lblCpf = new javax.swing.JLabel();
        lblNascimento = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        javax.swing.text.MaskFormatter maskCpf = null;
        try{
            maskCpf = new javax.swing.text.MaskFormatter("###.###.###-##");
            maskCpf.setPlaceholder("_");
        }catch(Exception ex){}
        txtCpf = new javax.swing.JFormattedTextField(maskCpf);
        javax.swing.text.MaskFormatter maskData = null;
        try{
            maskData = new javax.swing.text.MaskFormatter("##/##/####");
            maskData.setPlaceholder("_");
        }catch(Exception ex){}
        txtDataNascimento = new javax.swing.JFormattedTextField(maskData);
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
        lblNumero = new javax.swing.JLabel();
        txtNumero = new javax.swing.JTextField();
        lblComplemento = new javax.swing.JLabel();
        txtCidade = new javax.swing.JTextField();
        lblCidade = new javax.swing.JLabel();
        lblUf = new javax.swing.JLabel();
        cbxUf = new javax.swing.JComboBox<>();
        painelControle = new javax.swing.JPanel();
        lblCargo = new javax.swing.JLabel();
        lblAdmissao = new javax.swing.JLabel();
        javax.swing.text.MaskFormatter maskDataAdmissao = null;
        try{
            maskDataAdmissao = new javax.swing.text.MaskFormatter("##/##/####");
            maskDataAdmissao.setPlaceholder("_");
        }catch(Exception ex){}
        txtDataAdmissao = new javax.swing.JFormattedTextField(maskDataAdmissao);
        cbxCargo = new javax.swing.JComboBox<>();
        lblSalario = new javax.swing.JLabel();
        txtSalario = new javax.swing.JTextField();
        btnDependentes = new javax.swing.JButton();
        painelPesquisa1 = new javax.swing.JPanel();
        btnFiltrar = new javax.swing.JButton();
        btnRemoverFiltro = new javax.swing.JButton();
        cbxCargoParaFiltro = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setResizable(false);

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

        painelPesquisa.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisar Funcionário"));

        lblCpfPesquisa.setText("CPF");

        btnPesquisa.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/pesquisa.png"))); // NOI18N
        btnPesquisa.setText("Pesquisar");
        btnPesquisa.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPesquisa.setEnabled(false);
        btnPesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisaActionPerformed(evt);
            }
        });

        txtCpfPesquisa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCpfPesquisaKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCpfPesquisaKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout painelPesquisaLayout = new javax.swing.GroupLayout(painelPesquisa);
        painelPesquisa.setLayout(painelPesquisaLayout);
        painelPesquisaLayout.setHorizontalGroup(
            painelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPesquisaLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lblCpfPesquisa)
                .addGap(16, 16, 16)
                .addComponent(txtCpfPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPesquisa, javax.swing.GroupLayout.DEFAULT_SIZE, 165, Short.MAX_VALUE))
        );
        painelPesquisaLayout.setVerticalGroup(
            painelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPesquisaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCpfPesquisa)
                    .addComponent(btnPesquisa)
                    .addComponent(txtCpfPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        abaListagem.setLayout(new java.awt.BorderLayout());

        tblFuncionarios.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tblFuncionarios.getTableHeader().setReorderingAllowed(false);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listaFuncionarios, tblFuncionarios);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idFuncionario}"));
        columnBinding.setColumnName("Código");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nome}"));
        columnBinding.setColumnName("Nome");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${cargo}"));
        columnBinding.setColumnName("Cargo");
        columnBinding.setColumnClass(model.Cargo.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${salario}"));
        columnBinding.setColumnName("Salario");
        columnBinding.setColumnClass(Double.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nascimentoFormatado}"));
        columnBinding.setColumnName("Nascimento");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(tblFuncionarios);

        abaListagem.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        abas.addTab("Listagem", abaListagem);

        painelAcoes.setBorder(javax.swing.BorderFactory.createTitledBorder("Ações"));
        painelAcoes.setLayout(new java.awt.GridLayout(1, 0));

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adicionar.png"))); // NOI18N
        btnNovo.setText("Novo Funcionário");
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

        painelContato.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações de Contato"));

        lblTelefone.setText("TELEFONE");

        lblEmail.setText("E-MAIL");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblFuncionarios, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.telefone}"), txtTelefone, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblFuncionarios, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.email}"), txtEmail, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout painelContatoLayout = new javax.swing.GroupLayout(painelContato);
        painelContato.setLayout(painelContatoLayout);
        painelContatoLayout.setHorizontalGroup(
            painelContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelContatoLayout.createSequentialGroup()
                .addGroup(painelContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTelefone)
                    .addComponent(lblEmail, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGap(18, 18, 18)
                .addGroup(painelContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(58, Short.MAX_VALUE))
        );
        painelContatoLayout.setVerticalGroup(
            painelContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelContatoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTelefone)
                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEmail))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painelInformacoes.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações pessoais"));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblFuncionarios, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.idFuncionario}"), txtIdFuncionario, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblCodigo.setText("CÓDIGO");

        lblNome.setText("NOME");

        lblCpf.setText("CPF");

        lblNascimento.setText(" NASCIMENTO");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblFuncionarios, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.nome}"), txtNome, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomeKeyTyped(evt);
            }
        });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblFuncionarios, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.cpf}"), txtCpf, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblFuncionarios, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.dataNascimento}"), txtDataNascimento, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setConverter(converterData1);
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout painelInformacoesLayout = new javax.swing.GroupLayout(painelInformacoes);
        painelInformacoes.setLayout(painelInformacoesLayout);
        painelInformacoesLayout.setHorizontalGroup(
            painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInformacoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCpf)
                    .addComponent(lblNome)
                    .addComponent(lblCodigo)
                    .addComponent(lblNascimento))
                .addGap(28, 28, 28)
                .addGroup(painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtIdFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        painelInformacoesLayout.setVerticalGroup(
            painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInformacoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtIdFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCodigo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNome)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCpf)
                    .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNascimento)
                    .addComponent(txtDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painelEndereco.setBorder(javax.swing.BorderFactory.createTitledBorder("Endereço"));

        lblRua.setText("RUA");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblFuncionarios, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.rua}"), txtRua, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblBairro.setText("BAIRRO");

        lblCep.setText("CEP");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblFuncionarios, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.complemento}"), txtComplemento, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblFuncionarios, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.cep}"), txtCep, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblFuncionarios, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.bairro}"), txtBairro, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblNumero.setText("NÚMERO");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblFuncionarios, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.numero}"), txtNumero, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtNumero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroKeyTyped(evt);
            }
        });

        lblComplemento.setText("COMPLEMENTO");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblFuncionarios, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.cidade}"), txtCidade, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblCidade.setText("CIDADE");

        lblUf.setText("UF");

        cbxUf.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AC", "AM", "AP", "RO", "RR", "DF", "MG", "SP", "RJ", "ES", "SE", "CE", "BA", "RS", "MT", "MS", "GO", "SC", "TO", "PR", "PA", "RN", "AL", "MA", "PB", "PI", "PE" }));
        cbxUf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout painelEnderecoLayout = new javax.swing.GroupLayout(painelEndereco);
        painelEndereco.setLayout(painelEnderecoLayout);
        painelEnderecoLayout.setHorizontalGroup(
            painelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelEnderecoLayout.createSequentialGroup()
                .addGroup(painelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelEnderecoLayout.createSequentialGroup()
                        .addGroup(painelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNumero)
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
                .addGap(18, 49, Short.MAX_VALUE)
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
                    .addComponent(lblNumero))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblComplemento)))
        );

        painelControle.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações de controle"));

        lblCargo.setText("CARGO");

        lblAdmissao.setText("DATA ADMISSÃO");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblFuncionarios, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.dataAdmissao}"), txtDataAdmissao, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setConverter(converterData1);
        bindingGroup.addBinding(binding);

        cbxCargo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listaCargos, cbxCargo);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblFuncionarios, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.cargo}"), cbxCargo, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        lblSalario.setText("SALÁRIO");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblFuncionarios, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.salario}"), txtSalario, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtSalario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSalarioKeyTyped(evt);
            }
        });

        btnDependentes.setText("Gerenciar Dependentes");
        btnDependentes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDependentes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDependentesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelControleLayout = new javax.swing.GroupLayout(painelControle);
        painelControle.setLayout(painelControleLayout);
        painelControleLayout.setHorizontalGroup(
            painelControleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelControleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelControleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCargo)
                    .addComponent(lblAdmissao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelControleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDataAdmissao, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(painelControleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelControleLayout.createSequentialGroup()
                        .addComponent(lblSalario)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtSalario, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnDependentes, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        painelControleLayout.setVerticalGroup(
            painelControleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelControleLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelControleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAdmissao)
                    .addComponent(txtDataAdmissao, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSalario)
                    .addComponent(txtSalario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelControleLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCargo)
                    .addComponent(cbxCargo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnDependentes))
                .addContainerGap(21, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout abaDadosLayout = new javax.swing.GroupLayout(abaDados);
        abaDados.setLayout(abaDadosLayout);
        abaDadosLayout.setHorizontalGroup(
            abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaDadosLayout.createSequentialGroup()
                .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(abaDadosLayout.createSequentialGroup()
                        .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(abaDadosLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addComponent(painelContato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(abaDadosLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(painelInformacoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(painelEndereco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(painelControle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(painelAcoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        abaDadosLayout.setVerticalGroup(
            abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaDadosLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(painelAcoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 16, Short.MAX_VALUE)
                .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(painelEndereco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(painelInformacoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(painelContato, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(painelControle, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        abas.addTab("Dados", abaDados);

        painelPesquisa1.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtragem"));

        btnFiltrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addFiltro.png"))); // NOI18N
        btnFiltrar.setText("Aplicar Fitro");
        btnFiltrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrarActionPerformed(evt);
            }
        });

        btnRemoverFiltro.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/removeFiltro.png"))); // NOI18N
        btnRemoverFiltro.setText("Remover Filtro");
        btnRemoverFiltro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRemoverFiltro.setEnabled(false);
        btnRemoverFiltro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverFiltroActionPerformed(evt);
            }
        });

        cbxCargoParaFiltro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listaCargos, cbxCargoParaFiltro);
        bindingGroup.addBinding(jComboBoxBinding);

        javax.swing.GroupLayout painelPesquisa1Layout = new javax.swing.GroupLayout(painelPesquisa1);
        painelPesquisa1.setLayout(painelPesquisa1Layout);
        painelPesquisa1Layout.setHorizontalGroup(
            painelPesquisa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelPesquisa1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(cbxCargoParaFiltro, 0, 164, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnFiltrar)
                .addGap(10, 10, 10)
                .addComponent(btnRemoverFiltro)
                .addContainerGap())
        );
        painelPesquisa1Layout.setVerticalGroup(
            painelPesquisa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPesquisa1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelPesquisa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRemoverFiltro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFiltrar)
                    .addComponent(cbxCargoParaFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(abas, javax.swing.GroupLayout.DEFAULT_SIZE, 990, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(painelPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(painelPesquisa1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60))
            .addComponent(painelNavegacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(painelNavegacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(14, 14, 14)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(painelPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(painelPesquisa1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(17, 17, 17)
                .addComponent(abas, javax.swing.GroupLayout.DEFAULT_SIZE, 429, Short.MAX_VALUE))
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
        tblFuncionarios.setRowSelectionInterval(0, 0);
        tblFuncionarios.scrollRectToVisible(tblFuncionarios.getCellRect(0, 0, true));
    }//GEN-LAST:event_btnPrimeiroActionPerformed

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorActionPerformed
        // TODO add your handling code here:
        btnProximo.setEnabled(true);
        btnUltimo.setEnabled(true);
        int linha = tblFuncionarios.getSelectedRow();
        if ((linha - 1) >= 0) {
            linha--;
            if (linha == 0) {
                btnAnterior.setEnabled(false);
                btnPrimeiro.setEnabled(false);
            }
        }
        tblFuncionarios.setRowSelectionInterval(linha, linha);
        tblFuncionarios.scrollRectToVisible(tblFuncionarios.getCellRect(linha, 0, true));
    }//GEN-LAST:event_btnAnteriorActionPerformed

    private void btnProximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProximoActionPerformed
        // TODO add your handling code here:
        btnAnterior.setEnabled(true);
        btnPrimeiro.setEnabled(true);
        int linha = tblFuncionarios.getSelectedRow();
        if ((linha + 1) <= (tblFuncionarios.getRowCount() - 1)) {
            linha++;
            if (linha == tblFuncionarios.getRowCount() - 1) {
                btnProximo.setEnabled(false);
                btnUltimo.setEnabled(false);
            }
        }
        tblFuncionarios.setRowSelectionInterval(linha, linha);
        tblFuncionarios.scrollRectToVisible(tblFuncionarios.getCellRect(linha, 0, true));
    }//GEN-LAST:event_btnProximoActionPerformed

    private void btnUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoActionPerformed
        // TODO add your handling code here:
        btnPrimeiro.setEnabled(true);
        btnUltimo.setEnabled(false);
        btnProximo.setEnabled(false);
        btnAnterior.setEnabled(true);
        int linha = tblFuncionarios.getRowCount() - 1;
        tblFuncionarios.setRowSelectionInterval(linha, linha);
        tblFuncionarios.scrollRectToVisible(tblFuncionarios.getCellRect(linha, 0, true));
    }//GEN-LAST:event_btnUltimoActionPerformed

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnFecharActionPerformed

    private void btnPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisaActionPerformed
        // TODO add your handling code here
        int index = listaFuncionarios.indexOf(daoFuncionario.pesquisar(txtCpfPesquisa.getText()));
        tblFuncionarios.changeSelection(index, 1, false, false);

        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Não foi possível localizar esse cliente!",
                    "Aviso!", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnPesquisaActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        // TODO add your handling code here:
        Funcionario x = new Funcionario();
        listaFuncionarios.add((Funcionario) x);
        int linha = listaFuncionarios.size() - 1;
        tblFuncionarios.setRowSelectionInterval(linha, linha);
        trataEdicao(true);
        txtIdFuncionario.setText("gerado automaticamente");
        btnDependentes.setEnabled(false);
        txtNome.requestFocus();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        trataEdicao(true);
        txtNome.requestFocus();
        verificarBotoesParaOutrasTabelas();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        trataEdicao(false);
        atualizaTabela();
        limpaCamposCasoNaoHajaCadastros();
        modificarBotoesdeNavegacao();
        verificarBotoesParaOutrasTabelas();
        HabilitarBotoesPesquisa();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        // TODO add your handling code here:
        if (validaCampos()) {
            int linhaSelecionada = tblFuncionarios.getSelectedRow();
            Funcionario obj = listaFuncionarios.get(linhaSelecionada);
            daoFuncionario.salvar(obj);
            trataEdicao(false);
            atualizaTabela();
            verificarBotoesParaOutrasTabelas();
            HabilitarBotoesPesquisa();

        }
        modificarBotoesdeNavegacao();


    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here
        int option = JOptionPane.showOptionDialog(this, "Deseja realmente excluir esse funcionário ?",
                "Caixa de confirmação",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, new String[]{"Sim", "Não"}, "Sim");
        if (option == 0) {
            int linhaSelecionada = tblFuncionarios.getSelectedRow();
            Funcionario funcionario = listaFuncionarios.get(linhaSelecionada);
            daoFuncionario.remover(funcionario);
            atualizaTabela();

        }
        limpaCamposCasoNaoHajaCadastros();
        modificarBotoesdeNavegacao();
        verificarBotoesParaOutrasTabelas();
        HabilitarBotoesPesquisa();
        //tentar implementar try catch de erro de exclusão de foreign key
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnDependentesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDependentesActionPerformed
        // TODO add your handling code here:
        int linhaSelecionada = tblFuncionarios.getSelectedRow();
        Funcionario f = listaFuncionarios.get(linhaSelecionada);
        FormDependentes form = new FormDependentes(new javax.swing.JFrame(), true, f);
        form.setTitle("Manutenção de Dependentes");
        form.setVisible(true);
    }//GEN-LAST:event_btnDependentesActionPerformed

    private void txtNumeroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroKeyTyped
        // TODO add your handling code here:
        digitarSomenteNumeros(evt);
    }//GEN-LAST:event_txtNumeroKeyTyped

    private void btnFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarActionPerformed
        // TODO add your handling code here:

        ArrayList<Funcionario> listaFiltrada = daoFuncionario.pesquisaPorCargo((Cargo) cbxCargoParaFiltro.getSelectedItem());
        if (!listaFiltrada.isEmpty()) {
            listaFuncionarios.clear();
            listaFuncionarios.addAll(listaFiltrada);
            tblFuncionarios.setRowSelectionInterval(0, 0);
            btnRemoverFiltro.setEnabled(true);
            abas.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(this, "Não há nenhum funcionário com este cargo");
        }

    }//GEN-LAST:event_btnFiltrarActionPerformed

    private void btnRemoverFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverFiltroActionPerformed
        // TODO add your handling code here:  
        atualizaTabela();
        JOptionPane.showMessageDialog(this, "Filtro removido");
        btnRemoverFiltro.setEnabled(false);
    }//GEN-LAST:event_btnRemoverFiltroActionPerformed

    private void txtNomeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyTyped
        // TODO add your handling code here:
        digitarSomenteLetras(evt);
    }//GEN-LAST:event_txtNomeKeyTyped

    private void txtSalarioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSalarioKeyTyped
        // TODO add your handling code here:
        digitarSomenteNumeros(evt);
    }//GEN-LAST:event_txtSalarioKeyTyped

    private void txtCpfPesquisaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCpfPesquisaKeyTyped
        // TODO add your handling code here:
        digitarSomenteNumeros(evt);
    }//GEN-LAST:event_txtCpfPesquisaKeyTyped

    private void txtCpfPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCpfPesquisaKeyReleased
        // TODO add your handling code here:
        if (ValidaCPF.isCPF(txtCpfPesquisa.getText()) == true) {
            btnPesquisa.setEnabled(true);
        } else {
            btnPesquisa.setEnabled(false);
        }
    }//GEN-LAST:event_txtCpfPesquisaKeyReleased

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
            java.util.logging.Logger.getLogger(FormFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FormFuncionario dialog = new FormFuncionario(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnDependentes;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnFiltrar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisa;
    private javax.swing.JButton btnPrimeiro;
    private javax.swing.JButton btnProximo;
    private javax.swing.JButton btnRemoverFiltro;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnUltimo;
    private javax.swing.JComboBox<String> cbxCargo;
    private javax.swing.JComboBox<String> cbxCargoParaFiltro;
    private javax.swing.JComboBox<String> cbxUf;
    private converter.ConverterData converterData1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAdmissao;
    private javax.swing.JLabel lblBairro;
    private javax.swing.JLabel lblCargo;
    private javax.swing.JLabel lblCep;
    private javax.swing.JLabel lblCidade;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblComplemento;
    private javax.swing.JLabel lblCpf;
    private javax.swing.JLabel lblCpfPesquisa;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblNascimento;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblNumero;
    private javax.swing.JLabel lblRua;
    private javax.swing.JLabel lblSalario;
    private javax.swing.JLabel lblTelefone;
    private javax.swing.JLabel lblUf;
    private java.util.List<Cargo> listaCargos;
    private java.util.List<Funcionario> listaFuncionarios;
    private javax.swing.JPanel painelAcoes;
    private javax.swing.JPanel painelContato;
    private javax.swing.JPanel painelControle;
    private javax.swing.JPanel painelEndereco;
    private javax.swing.JPanel painelInformacoes;
    private javax.swing.JPanel painelNavegacao;
    private javax.swing.JPanel painelPesquisa;
    private javax.swing.JPanel painelPesquisa1;
    private javax.swing.JTable tblFuncionarios;
    private javax.swing.JTextField txtBairro;
    private javax.swing.JFormattedTextField txtCep;
    private javax.swing.JTextField txtCidade;
    private javax.swing.JTextField txtComplemento;
    private javax.swing.JFormattedTextField txtCpf;
    private javax.swing.JFormattedTextField txtCpfPesquisa;
    private javax.swing.JFormattedTextField txtDataAdmissao;
    private javax.swing.JFormattedTextField txtDataNascimento;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtIdFuncionario;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JTextField txtRua;
    private javax.swing.JTextField txtSalario;
    private javax.swing.JFormattedTextField txtTelefone;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
