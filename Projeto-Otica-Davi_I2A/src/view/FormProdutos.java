/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DAOCategoria;
import dao.DAOFornecedor;
import dao.DAOProduto;
import java.awt.Toolkit;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import model.Categoria;
import model.Fornecedor;
import model.Produto;

/**
 *
 * @author DAVI
 */
public class FormProdutos extends javax.swing.JDialog {

    /**
     * Creates new form FormProdutos
     */
    DAOProduto daoProduto = new DAOProduto();
    DAOFornecedor daoFornecedor = new DAOFornecedor();
    DAOCategoria daoCategoria = new DAOCategoria();

    public void atualizaTabela() {

        int linhaTabelaAux = tblProdutos.getSelectedRow();
        listaProdutos.clear();
        listaProdutos.addAll(daoProduto.getLista());

        if (!listaProdutos.isEmpty()) {
            if (linhaTabelaAux < 0) {
                tblProdutos.changeSelection(0, 0, false, false);
            } else if (listaProdutos.size() == linhaTabelaAux) {
                tblProdutos.changeSelection(listaProdutos.size() - 1, 0, false, false);
            } else if (listaProdutos.get(linhaTabelaAux).getIdProduto() != null) {
                tblProdutos.changeSelection(linhaTabelaAux, 0, false, false);
            }
        }

    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/oculos.png")));
    }

    public void limpaCamposCasoNaoHajaCadastros() {
        if (this.listaProdutos.isEmpty()) {
            txtIdProduto.setText("");
            txtDescricao.setText("");
            txtNome.setText("");
            txtPrecoCusto.setText("");
            txtPrecoVenda.setText("");
            txtUnidade.setText("");
            cbxFornecedor.setSelectedItem("");
            cbxCategoria.setSelectedItem("");
            spnEstoque.setValue(0);
            btnEditar.setEnabled(false);
            btnExcluir.setEnabled(false);
        }
    }

    public void modificarBotoesdeNavegacao() {
        if (tblProdutos.getRowCount() == 1
                || listaProdutos.isEmpty()) {
            btnPrimeiro.setEnabled(false);
            btnAnterior.setEnabled(false);
            btnProximo.setEnabled(false);
            btnUltimo.setEnabled(false);

        } else if (tblProdutos.getSelectedRow() == 0) {
            btnPrimeiro.setEnabled(false);
            btnAnterior.setEnabled(false);
            btnProximo.setEnabled(true);
            btnUltimo.setEnabled(true);
        } else if (tblProdutos.getSelectedRow() == listaProdutos.size() - 1) {
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

    public void HabilitarBotoesPesquisa() {
        if (listaProdutos.isEmpty()) {
            cbxCategoriaParaFiltro.setEnabled(false);
            cbxFornecedorParaFiltro.setEnabled(false);
            btnFiltrar1.setEnabled(false);
            btnFiltrar.setEnabled(false);
            txtIdPesquisa.setEnabled(false);
            btnPesquisa.setEnabled(false);
        } else {
            cbxCategoriaParaFiltro.setEnabled(true);
            cbxFornecedorParaFiltro.setEnabled(true);
            btnFiltrar1.setEnabled(true);
            btnFiltrar.setEnabled(true);
            txtIdPesquisa.setEnabled(true);
            btnPesquisa.setEnabled(true);
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
        txtNome.setEditable(editando);
        txtDescricao.setEditable(editando);
        txtPrecoCusto.setEditable(editando);
        txtPrecoVenda.setEditable(editando);
        txtUnidade.setEditable(editando);
        cbxFornecedor.setEnabled(editando);
        cbxCategoria.setEnabled(editando);
        spnEstoque.setEnabled(editando);
        btnFiltrar.setEnabled(!editando);
        btnFiltrar1.setEnabled(!editando);
        cbxCategoriaParaFiltro.setEnabled(!editando);
        cbxFornecedorParaFiltro.setEnabled(!editando);
        txtIdPesquisa.setEnabled(!editando);
        btnPesquisa.setEnabled(!editando);

    }

    public void digitarSomenteNumeros(java.awt.event.KeyEvent evt) {
        String caracteres = "0123456789.";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }

    public boolean validaCampos() {
        StringBuilder mensagem = new StringBuilder();
        if (cbxFornecedor.getSelectedItem() == null) {
            mensagem.append("Selecione o fornecedor!\n");
        }
        if (cbxCategoria.getSelectedItem() == null) {
            mensagem.append("Selecione a categoria!\n");
        }
        if (txtPrecoVenda.getText().length() > 0 || txtPrecoVenda.getText().equals("0.0")) {
            try {
                double precoVenda = Double.parseDouble(txtPrecoVenda.getText());
                if (precoVenda <= Double.parseDouble(txtPrecoCusto.getText())) {
                    mensagem.append("O preço de venda não pode ser menor que o preço de custo!\n");
                }
            } catch (Exception e) {
                mensagem.append("Informe o preço de venda!\n");
            }

        }
        if (txtPrecoCusto.getText().length() > 0 || txtPrecoCusto.getText().equals("0.0")) {
            try {
                Double.parseDouble(txtPrecoCusto.getText());
            } catch (Exception e) {
                mensagem.append("Informe o preço de custo!\n");
            }
        }
        if (txtNome.getText().isEmpty()) {
            mensagem.append("Informe o nome do produto!\n");
        }
        if (spnEstoque.getValue().toString().length() > 0) {
            try {
                Double.parseDouble(spnEstoque.getValue().toString());
            } catch (Exception e) {
                mensagem.append("Informe a quantidade em estoque!\n");
            }
        }
        if (mensagem.toString().length() != 0) {
            JOptionPane.showMessageDialog(this, mensagem.toString(), "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public void eventoMudancaDeSelecao() {
        tblProdutos.getSelectionModel().addListSelectionListener((ListSelectionEvent evt) -> {
            if (evt.getValueIsAdjusting()) {
                return;
            }
            modificarBotoesdeNavegacao();
        });
    }

    public FormProdutos(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        atualizaTabela();
        setIcon();
        listaFornecedores.clear();
        listaFornecedores.addAll(daoFornecedor.getLista());
        listaCategorias.clear();
        listaCategorias.addAll(daoCategoria.getLista());
        txtIdProduto.setEnabled(false);
        trataEdicao(false);
        modificarBotoesdeNavegacao();
        limpaCamposCasoNaoHajaCadastros();
        HabilitarBotoesPesquisa();
        eventoMudancaDeSelecao();
        abas.setSelectedIndex(1);
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

        listaProdutos = org.jdesktop.observablecollections.ObservableCollections.observableList(new ArrayList<Produto>());
        listaFornecedores = org.jdesktop.observablecollections.ObservableCollections.observableList(new ArrayList<Fornecedor>());
        listaCategorias = org.jdesktop.observablecollections.ObservableCollections.observableList(new ArrayList<Categoria>());
        painelNavegacao = new javax.swing.JPanel();
        btnPrimeiro = new javax.swing.JButton();
        btnAnterior = new javax.swing.JButton();
        btnProximo = new javax.swing.JButton();
        btnUltimo = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();
        abas = new javax.swing.JTabbedPane();
        abaListagem = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblProdutos = new javax.swing.JTable();
        abaDados = new javax.swing.JPanel();
        painelAcoes = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lblCodigo = new javax.swing.JLabel();
        lblNome = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        lblUnidade = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescricao = new javax.swing.JTextArea();
        lblDescricao = new javax.swing.JLabel();
        lblCategoria = new javax.swing.JLabel();
        txtUnidade = new javax.swing.JTextField();
        cbxCategoria = new javax.swing.JComboBox<>();
        txtIdProduto = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        lblEstoque = new javax.swing.JLabel();
        lblPrecoVenda = new javax.swing.JLabel();
        lblPrecoCusto = new javax.swing.JLabel();
        lblFornecedor = new javax.swing.JLabel();
        txtPrecoVenda = new javax.swing.JTextField();
        txtPrecoCusto = new javax.swing.JTextField();
        spnEstoque = new javax.swing.JSpinner();
        cbxFornecedor = new javax.swing.JComboBox<>();
        painelPesquisa1 = new javax.swing.JPanel();
        btnFiltrar = new javax.swing.JButton();
        btnRemoverFiltro = new javax.swing.JButton();
        cbxCategoriaParaFiltro = new javax.swing.JComboBox<>();
        painelPesquisa2 = new javax.swing.JPanel();
        btnFiltrar1 = new javax.swing.JButton();
        btnRemoverFiltro1 = new javax.swing.JButton();
        cbxFornecedorParaFiltro = new javax.swing.JComboBox<>();
        painelPesquisa5 = new javax.swing.JPanel();
        lblIdPesquisa = new javax.swing.JLabel();
        btnPesquisa = new javax.swing.JButton();
        txtIdPesquisa = new javax.swing.JTextField();

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

        tblProdutos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tblProdutos.getTableHeader().setReorderingAllowed(false);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listaProdutos, tblProdutos);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idProduto}"));
        columnBinding.setColumnName("Id Produto");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nome}"));
        columnBinding.setColumnName("Nome");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${precoCusto}"));
        columnBinding.setColumnName("Preco Custo");
        columnBinding.setColumnClass(Double.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${precoVenda}"));
        columnBinding.setColumnName("Preco Venda");
        columnBinding.setColumnClass(Double.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${quantidadeEmEstoque}"));
        columnBinding.setColumnName("Quantidade Em Estoque");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${unidade}"));
        columnBinding.setColumnName("Unidade");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${fornecedor}"));
        columnBinding.setColumnName("Fornecedor");
        columnBinding.setColumnClass(model.Fornecedor.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${categoria}"));
        columnBinding.setColumnName("Categoria");
        columnBinding.setColumnClass(model.Categoria.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(tblProdutos);

        abaListagem.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        abas.addTab("Listagem", abaListagem);

        painelAcoes.setBorder(javax.swing.BorderFactory.createTitledBorder("Ações"));
        painelAcoes.setLayout(new java.awt.GridLayout(1, 0));

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adicionar.png"))); // NOI18N
        btnNovo.setText("Novo Produto");
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

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações do Produto"));

        lblCodigo.setText("CÓDIGO");

        lblNome.setText("NOME");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblProdutos, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.nome}"), txtNome, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblUnidade.setText("UNIDADE");

        txtDescricao.setColumns(20);
        txtDescricao.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        txtDescricao.setRows(5);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblProdutos, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.descricao}"), txtDescricao, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jScrollPane2.setViewportView(txtDescricao);

        lblDescricao.setText("DESCRIÇÃO");

        lblCategoria.setText("CATEGORIA");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblProdutos, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.unidade}"), txtUnidade, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        cbxCategoria.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listaCategorias, cbxCategoria);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblProdutos, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.categoria}"), cbxCategoria, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblProdutos, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.idProduto}"), txtIdProduto, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNome)
                            .addComponent(lblCodigo)
                            .addComponent(lblUnidade)
                            .addComponent(lblCategoria))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtNome, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                            .addComponent(cbxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtIdProduto)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblDescricao)))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(txtIdProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUnidade)
                    .addComponent(txtUnidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCategoria)
                    .addComponent(cbxCategoria, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addComponent(lblDescricao)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações de controle"));

        lblEstoque.setText("QUANTIDADE EM ESTOQUE");

        lblPrecoVenda.setText("PREÇO DE VENDA");

        lblPrecoCusto.setText("PREÇO DE CUSTO");

        lblFornecedor.setText("FORNECEDOR");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblProdutos, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.precoVenda}"), txtPrecoVenda, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtPrecoVenda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecoVendaKeyTyped(evt);
            }
        });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblProdutos, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.precoCusto}"), txtPrecoCusto, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtPrecoCusto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPrecoCustoKeyTyped(evt);
            }
        });

        spnEstoque.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));
        spnEstoque.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblProdutos, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.quantidadeEmEstoque}"), spnEstoque, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        spnEstoque.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                spnEstoqueKeyTyped(evt);
            }
        });

        cbxFornecedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listaFornecedores, cbxFornecedor);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblProdutos, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.fornecedor}"), cbxFornecedor, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblPrecoVenda)
                    .addComponent(lblEstoque)
                    .addComponent(lblFornecedor)
                    .addComponent(lblPrecoCusto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cbxFornecedor, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtPrecoVenda, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(spnEstoque, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                    .addComponent(txtPrecoCusto))
                .addContainerGap(47, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecoCusto)
                    .addComponent(txtPrecoCusto, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPrecoVenda)
                    .addComponent(txtPrecoVenda, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEstoque)
                    .addComponent(spnEstoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFornecedor)
                    .addComponent(cbxFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout abaDadosLayout = new javax.swing.GroupLayout(abaDados);
        abaDados.setLayout(abaDadosLayout);
        abaDadosLayout.setHorizontalGroup(
            abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaDadosLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 145, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(51, 51, 51))
            .addComponent(painelAcoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        abaDadosLayout.setVerticalGroup(
            abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaDadosLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(painelAcoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(39, Short.MAX_VALUE))
        );

        abas.addTab("Dados", abaDados);

        painelPesquisa1.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtragem por Categoria"));

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

        cbxCategoriaParaFiltro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listaCategorias, cbxCategoriaParaFiltro);
        bindingGroup.addBinding(jComboBoxBinding);

        javax.swing.GroupLayout painelPesquisa1Layout = new javax.swing.GroupLayout(painelPesquisa1);
        painelPesquisa1.setLayout(painelPesquisa1Layout);
        painelPesquisa1Layout.setHorizontalGroup(
            painelPesquisa1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelPesquisa1Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(cbxCategoriaParaFiltro, 0, 164, Short.MAX_VALUE)
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
                    .addComponent(cbxCategoriaParaFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2))
        );

        painelPesquisa2.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtragem por Fornecedor"));

        btnFiltrar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addFiltro.png"))); // NOI18N
        btnFiltrar1.setText("Aplicar Fitro");
        btnFiltrar1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFiltrar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrar1ActionPerformed(evt);
            }
        });

        btnRemoverFiltro1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remover.png"))); // NOI18N
        btnRemoverFiltro1.setText("Remover Filtro");
        btnRemoverFiltro1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRemoverFiltro1.setEnabled(false);
        btnRemoverFiltro1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoverFiltro1ActionPerformed(evt);
            }
        });

        cbxFornecedorParaFiltro.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listaFornecedores, cbxFornecedorParaFiltro);
        bindingGroup.addBinding(jComboBoxBinding);

        javax.swing.GroupLayout painelPesquisa2Layout = new javax.swing.GroupLayout(painelPesquisa2);
        painelPesquisa2.setLayout(painelPesquisa2Layout);
        painelPesquisa2Layout.setHorizontalGroup(
            painelPesquisa2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelPesquisa2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addComponent(cbxFornecedorParaFiltro, 0, 164, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnFiltrar1)
                .addGap(10, 10, 10)
                .addComponent(btnRemoverFiltro1)
                .addContainerGap())
        );
        painelPesquisa2Layout.setVerticalGroup(
            painelPesquisa2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPesquisa2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelPesquisa2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRemoverFiltro1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnFiltrar1)
                    .addComponent(cbxFornecedorParaFiltro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2))
        );

        painelPesquisa5.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisar Produto"));

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

        javax.swing.GroupLayout painelPesquisa5Layout = new javax.swing.GroupLayout(painelPesquisa5);
        painelPesquisa5.setLayout(painelPesquisa5Layout);
        painelPesquisa5Layout.setHorizontalGroup(
            painelPesquisa5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPesquisa5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIdPesquisa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtIdPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPesquisa)
                .addContainerGap(24, Short.MAX_VALUE))
        );
        painelPesquisa5Layout.setVerticalGroup(
            painelPesquisa5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPesquisa5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelPesquisa5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIdPesquisa)
                    .addComponent(btnPesquisa)
                    .addComponent(txtIdPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(painelPesquisa5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(painelPesquisa2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(painelPesquisa1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(painelNavegacao, javax.swing.GroupLayout.PREFERRED_SIZE, 830, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addComponent(abas, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painelNavegacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(painelPesquisa1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(painelPesquisa2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(painelPesquisa5, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(47, 47, 47)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(abas, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        tblProdutos.setRowSelectionInterval(0, 0);
        tblProdutos.scrollRectToVisible(tblProdutos.getCellRect(0, 0, true));
    }//GEN-LAST:event_btnPrimeiroActionPerformed

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorActionPerformed
        // TODO add your handling code here:
        btnProximo.setEnabled(true);
        btnUltimo.setEnabled(true);
        int linha = tblProdutos.getSelectedRow();
        if ((linha - 1) >= 0) {
            linha--;
            if (linha == 0) {
                btnAnterior.setEnabled(false);
                btnPrimeiro.setEnabled(false);
            }
        }
        tblProdutos.setRowSelectionInterval(linha, linha);
        tblProdutos.scrollRectToVisible(tblProdutos.getCellRect(linha, 0, true));
    }//GEN-LAST:event_btnAnteriorActionPerformed

    private void btnProximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProximoActionPerformed
        // TODO add your handling code here:
        btnAnterior.setEnabled(true);
        btnPrimeiro.setEnabled(true);
        int linha = tblProdutos.getSelectedRow();
        if ((linha + 1) <= (tblProdutos.getRowCount() - 1)) {
            linha++;
            if (linha == tblProdutos.getRowCount() - 1) {
                btnProximo.setEnabled(false);
                btnUltimo.setEnabled(false);
            }
        }
        tblProdutos.setRowSelectionInterval(linha, linha);
        tblProdutos.scrollRectToVisible(tblProdutos.getCellRect(linha, 0, true));
    }//GEN-LAST:event_btnProximoActionPerformed

    private void btnUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoActionPerformed
        // TODO add your handling code here:
        btnPrimeiro.setEnabled(true);
        btnUltimo.setEnabled(false);
        btnProximo.setEnabled(false);
        btnAnterior.setEnabled(true);
        int linha = tblProdutos.getRowCount() - 1;
        tblProdutos.setRowSelectionInterval(linha, linha);
        tblProdutos.scrollRectToVisible(tblProdutos.getCellRect(linha, 0, true));
    }//GEN-LAST:event_btnUltimoActionPerformed

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnFecharActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        // TODO add your handling code here:

        Produto x = new Produto();
        listaProdutos.add((Produto) x);
        int linha = listaProdutos.size() - 1;
        tblProdutos.setRowSelectionInterval(linha, linha);
        trataEdicao(true);
        txtIdProduto.setText("gerado automaticamente");
        txtNome.requestFocus();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        trataEdicao(true);
        txtNome.requestFocus();
        spnEstoque.setEnabled(false);
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
            int linhaSelecionada = tblProdutos.getSelectedRow();
            Produto obj = listaProdutos.get(linhaSelecionada);
            daoProduto.salvar(obj);
            trataEdicao(false);
            atualizaTabela();
            spnEstoque.setEnabled(false);
            HabilitarBotoesPesquisa();
        }
        modificarBotoesdeNavegacao();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here
        int option = JOptionPane.showOptionDialog(this, "Deseja realmente excluir esse produto ?",
                "Caixa de confirmação",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, new String[]{"Sim", "Não"}, "Sim");
        if (option == 0) {
            int linhaSelecionada = tblProdutos.getSelectedRow();
            Produto produto = listaProdutos.get(linhaSelecionada);
            daoProduto.remover(produto);
            atualizaTabela();
        }
        modificarBotoesdeNavegacao();
        limpaCamposCasoNaoHajaCadastros();
        HabilitarBotoesPesquisa();
        //tentar implementar try catch de erro de exclusão de foreign key
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarActionPerformed
        // TODO add your handling code here:
        int indexSelecionado = cbxCategoriaParaFiltro.getSelectedIndex();
        Categoria c = listaCategorias.get(indexSelecionado);
        ArrayList<Produto> listaFiltrada = daoProduto.pesquisaPorCategoria(c);
        if (!listaFiltrada.isEmpty()) {
            listaProdutos.clear();
            listaProdutos.addAll(listaFiltrada);
            tblProdutos.setRowSelectionInterval(0, 0);
            btnRemoverFiltro.setEnabled(true);
            abas.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(this, "Não há nenhum produto com essa categoria.");
        }
    }//GEN-LAST:event_btnFiltrarActionPerformed

    private void btnRemoverFiltroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverFiltroActionPerformed
        // TODO add your handling code here:
        atualizaTabela();
        JOptionPane.showMessageDialog(this, "Filtro removido");
        btnRemoverFiltro.setEnabled(false);
    }//GEN-LAST:event_btnRemoverFiltroActionPerformed

    private void btnFiltrar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrar1ActionPerformed
        // TODO add your handling code here:
        int indexSelecionado = cbxFornecedorParaFiltro.getSelectedIndex();
        Fornecedor f = listaFornecedores.get(indexSelecionado);
        ArrayList<Produto> listaFiltrada = daoProduto.pesquisaPorFornecedor(f);
        if (!listaFiltrada.isEmpty()) {
            listaProdutos.clear();
            listaProdutos.addAll(listaFiltrada);
            tblProdutos.setRowSelectionInterval(0, 0);
            btnRemoverFiltro1.setEnabled(true);
            abas.setSelectedIndex(0);
        } else {
            JOptionPane.showMessageDialog(this, "Não há nenhum produto com esse fornecedor.");
        }
    }//GEN-LAST:event_btnFiltrar1ActionPerformed

    private void btnRemoverFiltro1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverFiltro1ActionPerformed
        // TODO add your handling code here:
        atualizaTabela();
        JOptionPane.showMessageDialog(this, "Filtro removido");
        btnRemoverFiltro1.setEnabled(false);
    }//GEN-LAST:event_btnRemoverFiltro1ActionPerformed

    private void txtPrecoCustoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecoCustoKeyTyped
        // TODO add your handling code here:
        digitarSomenteNumeros(evt);
    }//GEN-LAST:event_txtPrecoCustoKeyTyped

    private void txtPrecoVendaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPrecoVendaKeyTyped
        // TODO add your handling code here:
        digitarSomenteNumeros(evt);
    }//GEN-LAST:event_txtPrecoVendaKeyTyped

    private void spnEstoqueKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_spnEstoqueKeyTyped
        // TODO add your handling code here:
        digitarSomenteNumeros(evt);
    }//GEN-LAST:event_spnEstoqueKeyTyped

    private void txtIdPesquisaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdPesquisaKeyTyped
        // TODO add your handling code here:
        String caracteres = "0123456789";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }//GEN-LAST:event_txtIdPesquisaKeyTyped

    private void txtIdPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdPesquisaKeyReleased
        // TODO add your handling code here:
        if (txtIdPesquisa.getText().length() > 0) {
            btnPesquisa.setEnabled(true);
        } else {
            btnPesquisa.setEnabled(false);
        }
    }//GEN-LAST:event_txtIdPesquisaKeyReleased

    private void btnPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisaActionPerformed
        // TODO add your handling code here

        int index = listaProdutos.indexOf(daoProduto.localizar(Integer.parseInt(txtIdPesquisa.getText())));

        tblProdutos.changeSelection(index, 1, false, false);

        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Não foi possível localizar essa venda!",
                    "Aviso!", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnPesquisaActionPerformed

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
            java.util.logging.Logger.getLogger(FormProdutos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormProdutos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormProdutos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormProdutos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FormProdutos dialog = new FormProdutos(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnFiltrar;
    private javax.swing.JButton btnFiltrar1;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisa;
    private javax.swing.JButton btnPrimeiro;
    private javax.swing.JButton btnProximo;
    private javax.swing.JButton btnRemoverFiltro;
    private javax.swing.JButton btnRemoverFiltro1;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnUltimo;
    private javax.swing.JComboBox<String> cbxCategoria;
    private javax.swing.JComboBox<String> cbxCategoriaParaFiltro;
    private javax.swing.JComboBox<String> cbxFornecedor;
    private javax.swing.JComboBox<String> cbxFornecedorParaFiltro;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCategoria;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblDescricao;
    private javax.swing.JLabel lblEstoque;
    private javax.swing.JLabel lblFornecedor;
    private javax.swing.JLabel lblIdPesquisa;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblPrecoCusto;
    private javax.swing.JLabel lblPrecoVenda;
    private javax.swing.JLabel lblUnidade;
    private java.util.List<Categoria> listaCategorias;
    private java.util.List<Fornecedor> listaFornecedores;
    private java.util.List<Produto> listaProdutos;
    private javax.swing.JPanel painelAcoes;
    private javax.swing.JPanel painelNavegacao;
    private javax.swing.JPanel painelPesquisa1;
    private javax.swing.JPanel painelPesquisa2;
    private javax.swing.JPanel painelPesquisa5;
    private javax.swing.JSpinner spnEstoque;
    private javax.swing.JTable tblProdutos;
    private javax.swing.JTextArea txtDescricao;
    private javax.swing.JTextField txtIdPesquisa;
    private javax.swing.JTextField txtIdProduto;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtPrecoCusto;
    private javax.swing.JTextField txtPrecoVenda;
    private javax.swing.JTextField txtUnidade;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
