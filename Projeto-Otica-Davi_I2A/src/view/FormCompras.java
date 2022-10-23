/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DAOCompra;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import model.Compra;

/**
 *
 * @author DAVI
 */
public class FormCompras extends javax.swing.JDialog {

    /**
     * Creates new form FormCompras
     */
    DAOCompra dao = new DAOCompra();

    public void atualizaTabela() {

        int linhaTabelaAux = tblCompras.getSelectedRow();
        listaCompras.clear();
        listaCompras.addAll(dao.getLista());

        if (!listaCompras.isEmpty()) {
            if (linhaTabelaAux < 0) {
                tblCompras.changeSelection(0, 0, false, false);
            } else if (listaCompras.size() == linhaTabelaAux) {
                tblCompras.changeSelection(listaCompras.size() - 1, 0, false, false);
            } else if (listaCompras.get(linhaTabelaAux).getIdCompra()!= null) {
                tblCompras.changeSelection(linhaTabelaAux, 0, false, false);
            }
        }

    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/oculos.png")));
    }

    public void verificarBotoesParaOutrasTabelas() {
        if (listaCompras.isEmpty()) {
            btnAddItem.setEnabled(false);
        } else {
            btnAddItem.setEnabled(true);
        }

    }

    public void limpaCamposCasoNaoHajaCadastros() {
        if (this.tblCompras.getRowCount() == 0) {
            txtIdCompra.setText("");
            txtDataCompra.setText("");
            txtValorTotal.setText("");
            btnEditar.setEnabled(false);
            btnExcluir.setEnabled(false);
        }
    }

    public void HabilitarBotoesPesquisa() {
        if (listaCompras.isEmpty()) {
            txtDataInicial.setEnabled(false);
            txtDataFinal.setEnabled(false);
            btnFiltrar.setEnabled(false);
            txtIdPesquisa.setEnabled(false);
            btnPesquisa.setEnabled(false);
        } else {
            txtDataInicial.setEnabled(true);
            txtDataFinal.setEnabled(true);
            btnFiltrar.setEnabled(true);
            txtIdPesquisa.setEnabled(true);
            btnPesquisa.setEnabled(true);
        }
    }

    public void modificarBotoesdeNavegacao() {
        if (tblCompras.getRowCount() == 0 || tblCompras.getRowCount() == 1) {
            btnPrimeiro.setEnabled(false);
            btnProximo.setEnabled(false);
            btnAnterior.setEnabled(false);
            btnUltimo.setEnabled(false);
        }

        if (tblCompras.getSelectedRow() == 0) {
            btnPrimeiro.setEnabled(false);
            btnAnterior.setEnabled(false);
            btnProximo.setEnabled(true);
            btnUltimo.setEnabled(true);
        } else {
            btnPrimeiro.setEnabled(true);
            btnAnterior.setEnabled(true);
        }

        if (tblCompras.getSelectedRow() == listaCompras.size() - 1) {
            btnProximo.setEnabled(false);
            btnUltimo.setEnabled(false);
            btnPrimeiro.setEnabled(true);
            btnAnterior.setEnabled(true);
        } else {
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
        abas.setEnabledAt(0, !editando);
        txtIdCompra.setEnabled(editando);
        txtDataCompra.setEnabled(editando);
        txtValorTotal.setEnabled(editando);
        btnPesquisa.setEnabled(!editando);
        txtIdPesquisa.setEnabled(!editando);
        btnFiltrar.setEnabled(!editando);
        txtDataInicial.setEnabled(!editando);
        txtDataFinal.setEnabled(!editando);
    }

    public void digitarSomenteNumeros(java.awt.event.KeyEvent evt) {
        String caracteres = "0123456789";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }

    public boolean validaCampos() {
        StringBuilder mensagem = new StringBuilder();
        if ((txtDataCompra.getText().length() > 0)) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            try {
                sdf.parse(txtDataCompra.getText());
            } catch (Exception e) {
                mensagem.append("Informe a data da Compra!\n");
                txtDataCompra.requestFocus();
            }
        }
        if (mensagem.toString().length() != 0) {
            JOptionPane.showMessageDialog(this, mensagem.toString(), "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    public void eventoMudancaDeSelecao() {
        tblCompras.getSelectionModel().addListSelectionListener((ListSelectionEvent evt) -> {
            if (evt.getValueIsAdjusting()) {
                return;
            }
            modificarBotoesdeNavegacao();
        });
    }

    public FormCompras(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        atualizaTabela();
        setIcon();
        HabilitarBotoesPesquisa();
        trataEdicao(false);
        modificarBotoesdeNavegacao();
        verificarBotoesParaOutrasTabelas();
        limpaCamposCasoNaoHajaCadastros();
        txtIdCompra.setEnabled(false);
        abas.setSelectedIndex(1);
        eventoMudancaDeSelecao();
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

        listaCompras = org.jdesktop.observablecollections.ObservableCollections.observableList(new ArrayList<Compra>());
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
        tblCompras = new javax.swing.JTable();
        abaDados = new javax.swing.JPanel();
        painelAcoes = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        painelInformacoes = new javax.swing.JPanel();
        lblCodigo = new javax.swing.JLabel();
        txtIdCompra = new javax.swing.JTextField();
        lblDataCompra = new javax.swing.JLabel();
        javax.swing.text.MaskFormatter maskData = null;
        try{
            maskData = new javax.swing.text.MaskFormatter("##/##/####");
            maskData.setPlaceholder("_");
        }catch(Exception ex){}
        txtDataCompra = new javax.swing.JFormattedTextField(maskData);
        btnAddItem = new javax.swing.JButton();
        lblValorTotal = new javax.swing.JLabel();
        txtValorTotal = new javax.swing.JTextField();
        painelPesquisa = new javax.swing.JPanel();
        lblIdPesquisa = new javax.swing.JLabel();
        btnPesquisa = new javax.swing.JButton();
        txtIdPesquisa = new javax.swing.JTextField();
        painelFiltragem = new javax.swing.JPanel();
        lblDataInicial = new javax.swing.JLabel();
        javax.swing.text.MaskFormatter maskData1 = null;
        try{
            maskData1 = new javax.swing.text.MaskFormatter("##/##/####");
            maskData1.setPlaceholder("_");
        }catch(Exception ex){}
        txtDataInicial = new javax.swing.JFormattedTextField(maskData1);
        lblDataFinal = new javax.swing.JLabel();
        javax.swing.text.MaskFormatter maskData2 = null;
        try{
            maskData2 = new javax.swing.text.MaskFormatter("##/##/####");
            maskData2.setPlaceholder("_");
        }catch(Exception ex){}
        txtDataFinal = new javax.swing.JFormattedTextField(maskData2);
        btnFiltrar = new javax.swing.JButton();
        btnRemoverFiltro1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        painelNavegacao.setBorder(javax.swing.BorderFactory.createTitledBorder("Navegação"));
        painelNavegacao.setLayout(new java.awt.GridLayout(1, 0));

        btnPrimeiro.setText("Primeiro");
        btnPrimeiro.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrimeiroActionPerformed(evt);
            }
        });
        painelNavegacao.add(btnPrimeiro);

        btnAnterior.setText("Anterior");
        btnAnterior.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnteriorActionPerformed(evt);
            }
        });
        painelNavegacao.add(btnAnterior);

        btnProximo.setText("Próximo");
        btnProximo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProximoActionPerformed(evt);
            }
        });
        painelNavegacao.add(btnProximo);

        btnUltimo.setText("Último");
        btnUltimo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUltimoActionPerformed(evt);
            }
        });
        painelNavegacao.add(btnUltimo);

        btnFechar.setText("Fechar");
        btnFechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFecharActionPerformed(evt);
            }
        });
        painelNavegacao.add(btnFechar);

        abaListagem.setLayout(new java.awt.BorderLayout());

        tblCompras.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tblCompras.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblCompras.getTableHeader().setReorderingAllowed(false);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listaCompras, tblCompras);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idCompra}"));
        columnBinding.setColumnName("Id Compra");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${dataCompraFormatada}"));
        columnBinding.setColumnName("Data Compra");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${valorTotal}"));
        columnBinding.setColumnName("Valor Total");
        columnBinding.setColumnClass(Double.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        tblCompras.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblComprasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblCompras);

        abaListagem.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        abas.addTab("Listagem", abaListagem);

        painelAcoes.setBorder(javax.swing.BorderFactory.createTitledBorder("Ações"));
        painelAcoes.setLayout(new java.awt.GridLayout(1, 0));

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adicionar.png"))); // NOI18N
        btnNovo.setText("Nova Compra");
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });
        painelAcoes.add(btnNovo);

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/editar.png"))); // NOI18N
        btnEditar.setText("Editar");
        btnEditar.setEnabled(false);
        btnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditarActionPerformed(evt);
            }
        });
        painelAcoes.add(btnEditar);

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/cancelar.png"))); // NOI18N
        btnCancelar.setText("Cancelar");
        btnCancelar.setEnabled(false);
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });
        painelAcoes.add(btnCancelar);

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/salvar.png"))); // NOI18N
        btnSalvar.setText("Salvar");
        btnSalvar.setEnabled(false);
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });
        painelAcoes.add(btnSalvar);

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remover.png"))); // NOI18N
        btnExcluir.setText("Excluir");
        btnExcluir.setEnabled(false);
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });
        painelAcoes.add(btnExcluir);

        painelInformacoes.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações da Venda"));

        lblCodigo.setText("CÓDIGO");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblCompras, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.idCompra}"), txtIdCompra, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblDataCompra.setText("DATA DA COMPRA");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblCompras, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.dataCompra}"), txtDataCompra, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setConverter(converterData1);
        bindingGroup.addBinding(binding);

        btnAddItem.setText("GERENCIAR ITENS  DE COMPRA");
        btnAddItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddItemActionPerformed(evt);
            }
        });

        lblValorTotal.setText("VALOR TOTAL");

        txtValorTotal.setEditable(false);
        txtValorTotal.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblCompras, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.valorTotal}"), txtValorTotal, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout painelInformacoesLayout = new javax.swing.GroupLayout(painelInformacoes);
        painelInformacoes.setLayout(painelInformacoesLayout);
        painelInformacoesLayout.setHorizontalGroup(
            painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInformacoesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblValorTotal)
                    .addComponent(lblCodigo)
                    .addComponent(lblDataCompra))
                .addGap(18, 18, 18)
                .addGroup(painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtValorTotal)
                    .addComponent(txtIdCompra)
                    .addComponent(txtDataCompra))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelInformacoesLayout.createSequentialGroup()
                .addContainerGap(50, Short.MAX_VALUE)
                .addComponent(btnAddItem)
                .addGap(48, 48, 48))
        );
        painelInformacoesLayout.setVerticalGroup(
            painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInformacoesLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(txtIdCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDataCompra)
                    .addComponent(txtDataCompra, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(painelInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblValorTotal)
                    .addComponent(txtValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnAddItem)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout abaDadosLayout = new javax.swing.GroupLayout(abaDados);
        abaDados.setLayout(abaDadosLayout);
        abaDadosLayout.setHorizontalGroup(
            abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelAcoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, abaDadosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(painelInformacoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(192, 192, 192))
        );
        abaDadosLayout.setVerticalGroup(
            abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaDadosLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(painelAcoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(painelInformacoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        abas.addTab("Dados", abaDados);

        painelPesquisa.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisar Compra"));

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

        javax.swing.GroupLayout painelPesquisaLayout = new javax.swing.GroupLayout(painelPesquisa);
        painelPesquisa.setLayout(painelPesquisaLayout);
        painelPesquisaLayout.setHorizontalGroup(
            painelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPesquisaLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblIdPesquisa)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtIdPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPesquisa)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        painelPesquisaLayout.setVerticalGroup(
            painelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPesquisaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblIdPesquisa)
                    .addComponent(btnPesquisa)
                    .addComponent(txtIdPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        painelFiltragem.setBorder(javax.swing.BorderFactory.createTitledBorder("Filtrar por Data"));

        lblDataInicial.setText("DATA INICIAL");

        lblDataFinal.setText("DATA FINAL");

        btnFiltrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/addFiltro.png"))); // NOI18N
        btnFiltrar.setText("Aplicar Fitro");
        btnFiltrar.setToolTipText("Informe apenas a data inicial caso deseje a lista das vendas em uma única data");
        btnFiltrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnFiltrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFiltrarActionPerformed(evt);
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

        javax.swing.GroupLayout painelFiltragemLayout = new javax.swing.GroupLayout(painelFiltragem);
        painelFiltragem.setLayout(painelFiltragemLayout);
        painelFiltragemLayout.setHorizontalGroup(
            painelFiltragemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelFiltragemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelFiltragemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelFiltragemLayout.createSequentialGroup()
                        .addComponent(lblDataInicial)
                        .addGap(18, 18, 18)
                        .addComponent(txtDataInicial))
                    .addGroup(painelFiltragemLayout.createSequentialGroup()
                        .addComponent(lblDataFinal)
                        .addGap(27, 27, 27)
                        .addComponent(txtDataFinal, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)))
                .addGap(30, 30, 30)
                .addComponent(btnFiltrar)
                .addGap(18, 18, 18)
                .addComponent(btnRemoverFiltro1)
                .addGap(2, 2, 2))
        );
        painelFiltragemLayout.setVerticalGroup(
            painelFiltragemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelFiltragemLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelFiltragemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelFiltragemLayout.createSequentialGroup()
                        .addGroup(painelFiltragemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDataInicial)
                            .addComponent(txtDataInicial, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(painelFiltragemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDataFinal)
                            .addComponent(txtDataFinal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelFiltragemLayout.createSequentialGroup()
                        .addGroup(painelFiltragemLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnFiltrar)
                            .addComponent(btnRemoverFiltro1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(25, 25, 25))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelNavegacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(abas)
            .addGroup(layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addComponent(painelPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(painelFiltragem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painelNavegacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(painelFiltragem, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(painelPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(abas, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        // TODO add your handling code here:
        Compra x = new Compra();
        listaCompras.add((Compra) x);
        int linha = listaCompras.size() - 1;
        tblCompras.setRowSelectionInterval(linha, linha);
        trataEdicao(true);
        txtIdCompra.setText("gerado automaticamente");
        btnAddItem.setEnabled(false);
        txtDataCompra.requestFocus();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        trataEdicao(true);
        txtDataCompra.requestFocus();
        verificarBotoesParaOutrasTabelas();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        trataEdicao(false);
        atualizaTabela();
        modificarBotoesdeNavegacao();
        limpaCamposCasoNaoHajaCadastros();
        verificarBotoesParaOutrasTabelas();
        HabilitarBotoesPesquisa();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        // TODO add your handling code here:
        if (validaCampos()) {
            int linhaSelecionada = tblCompras.getSelectedRow();
            Compra obj = listaCompras.get(linhaSelecionada);
            dao.salvar(obj);
            atualizaTabela();
            trataEdicao(false);
            verificarBotoesParaOutrasTabelas();
            modificarBotoesdeNavegacao();
            HabilitarBotoesPesquisa();
        }

    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here
        int option = JOptionPane.showOptionDialog(this, "Deseja realmente excluir essa compra ?",
                "Caixa de confirmação",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, new String[]{"Sim", "Não"}, "Sim");
        if (option == 0) {

            int linhaSelecionada = tblCompras.getSelectedRow();
            Compra compra = listaCompras.get(linhaSelecionada);
            dao.remover(compra);
            atualizaTabela();
            limpaCamposCasoNaoHajaCadastros();
            modificarBotoesdeNavegacao();
            verificarBotoesParaOutrasTabelas();
            HabilitarBotoesPesquisa();
        }

    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnAddItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddItemActionPerformed
        // TODO add your handling code here:

        int linhaSelecionada = tblCompras.getSelectedRow();
        Compra compraSelecionada = listaCompras.get(linhaSelecionada);
        FormItensDeCompra form = new FormItensDeCompra(new javax.swing.JFrame(), true, compraSelecionada);
        form.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        form.setTitle("Manutenção dos produtos da compra");
        form.setLocationRelativeTo(null);
        form.setResizable(false);
        form.setVisible(true);
        if (!form.isShowing()) {
            Compra obj = listaCompras.get(linhaSelecionada);
            txtValorTotal.setText(Double.toString(dao.calculaValorTotal(obj)));
            dao.salvar(obj);
            atualizaTabela();
        }
    }//GEN-LAST:event_btnAddItemActionPerformed

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnFecharActionPerformed

    private void btnUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoActionPerformed
        // TODO add your handling code here:
        btnPrimeiro.setEnabled(true);
        btnUltimo.setEnabled(false);
        btnProximo.setEnabled(false);
        btnAnterior.setEnabled(true);
        int linha = tblCompras.getRowCount() - 1;
        tblCompras.setRowSelectionInterval(linha, linha);
        tblCompras.scrollRectToVisible(tblCompras.getCellRect(linha, 0, true));
    }//GEN-LAST:event_btnUltimoActionPerformed

    private void btnProximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProximoActionPerformed
        // TODO add your handling code here:
        btnAnterior.setEnabled(true);
        btnPrimeiro.setEnabled(true);
        int linha = tblCompras.getSelectedRow();
        if ((linha + 1) <= (tblCompras.getRowCount() - 1)) {
            linha++;
            if (linha == tblCompras.getRowCount() - 1) {
                btnProximo.setEnabled(false);
                btnUltimo.setEnabled(false);
            }
        }
        tblCompras.setRowSelectionInterval(linha, linha);
        tblCompras.scrollRectToVisible(tblCompras.getCellRect(linha, 0, true));
    }//GEN-LAST:event_btnProximoActionPerformed

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorActionPerformed
        // TODO add your handling code here:
        btnProximo.setEnabled(true);
        btnUltimo.setEnabled(true);
        int linha = tblCompras.getSelectedRow();
        if ((linha - 1) >= 0) {
            linha--;
            if (linha == 0) {
                btnAnterior.setEnabled(false);
                btnPrimeiro.setEnabled(false);
            }
        }
        tblCompras.setRowSelectionInterval(linha, linha);
        tblCompras.scrollRectToVisible(tblCompras.getCellRect(linha, 0, true));
    }//GEN-LAST:event_btnAnteriorActionPerformed

    private void btnPrimeiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimeiroActionPerformed
        // TODO add your handling code here:
        btnAnterior.setEnabled(false);
        btnPrimeiro.setEnabled(false);
        btnProximo.setEnabled(true);
        btnUltimo.setEnabled(true);
        tblCompras.setRowSelectionInterval(0, 0);
        tblCompras.scrollRectToVisible(tblCompras.getCellRect(0, 0, true));
    }//GEN-LAST:event_btnPrimeiroActionPerformed

    private void tblComprasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblComprasMouseClicked
        // TODO add your handling code here:
        modificarBotoesdeNavegacao();
    }//GEN-LAST:event_tblComprasMouseClicked

    private void btnPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisaActionPerformed
        // TODO add your handling code here

        int index = listaCompras.indexOf(dao.localizar(Integer.parseInt(txtIdPesquisa.getText())));

        tblCompras.changeSelection(index, 1, false, false);

        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Não foi possível localizar essa venda!",
                    "Aviso!", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnPesquisaActionPerformed

    private void txtIdPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdPesquisaKeyReleased
        // TODO add your handling code here:
        digitarSomenteNumeros(evt);
        if (txtIdPesquisa.getText().length() > 0) {
            btnPesquisa.setEnabled(true);
        } else {
            btnPesquisa.setEnabled(false);
        }
    }//GEN-LAST:event_txtIdPesquisaKeyReleased

    private void btnFiltrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFiltrarActionPerformed

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

        ArrayList<Compra> listaFiltrada = null;
        try {
            listaFiltrada = dao.filtrarPorData(dataInicial, dataFinal);
            if (!listaFiltrada.isEmpty()) {
                listaCompras.clear();
                listaCompras.addAll(listaFiltrada);
                tblCompras.setRowSelectionInterval(0, 0);
                btnRemoverFiltro1.setEnabled(true);
                abas.setSelectedIndex(0);
            } else {
                JOptionPane.showMessageDialog(this, "Não há nenhuma compra com essa data.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Informe ao menos a data inicial para filtrar.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnFiltrarActionPerformed

    private void btnRemoverFiltro1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoverFiltro1ActionPerformed
        // TODO add your handling code here:
        atualizaTabela();
        txtDataInicial.setText("");
        txtDataFinal.setText("");
        JOptionPane.showMessageDialog(this, "Filtro removido");
        btnRemoverFiltro1.setEnabled(false);
    }//GEN-LAST:event_btnRemoverFiltro1ActionPerformed

    private void txtIdPesquisaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtIdPesquisaKeyTyped
        // TODO add your handling code here:
        digitarSomenteNumeros(evt);
    }//GEN-LAST:event_txtIdPesquisaKeyTyped

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
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormCompras.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FormCompras dialog = new FormCompras(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnCancelar;
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnFechar;
    private javax.swing.JButton btnFiltrar;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisa;
    private javax.swing.JButton btnPrimeiro;
    private javax.swing.JButton btnProximo;
    private javax.swing.JButton btnRemoverFiltro1;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnUltimo;
    private converter.ConverterData converterData1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblDataCompra;
    private javax.swing.JLabel lblDataFinal;
    private javax.swing.JLabel lblDataInicial;
    private javax.swing.JLabel lblIdPesquisa;
    private javax.swing.JLabel lblValorTotal;
    private java.util.List<Compra> listaCompras;
    private javax.swing.JPanel painelAcoes;
    private javax.swing.JPanel painelFiltragem;
    private javax.swing.JPanel painelInformacoes;
    private javax.swing.JPanel painelNavegacao;
    private javax.swing.JPanel painelPesquisa;
    private javax.swing.JTable tblCompras;
    private javax.swing.JFormattedTextField txtDataCompra;
    private javax.swing.JFormattedTextField txtDataFinal;
    private javax.swing.JFormattedTextField txtDataInicial;
    private javax.swing.JTextField txtIdCompra;
    private javax.swing.JTextField txtIdPesquisa;
    private javax.swing.JTextField txtValorTotal;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
