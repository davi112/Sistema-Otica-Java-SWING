/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DAOCliente;
import dao.DAOReceita;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import model.Cliente;
import model.Receita;

/**
 *
 * @author DAVI
 */
public class FormReceita extends javax.swing.JDialog {

    DAOReceita daoReceita = new DAOReceita();
    DAOCliente daoCliente = new DAOCliente();

    /**
     * Creates new form FormReceita
     */
    public void atualizaTabela() {

        int linhaTabelaAux = tblReceitas.getSelectedRow();
        listaReceitas.clear();
        Cliente cliente = this.listaClientes.get(0);
        listaReceitas.addAll(daoReceita.getReceitasPorCliente(cliente));

        if (!listaReceitas.isEmpty()) {
            if (linhaTabelaAux < 0) {
                tblReceitas.changeSelection(0, 0, false, false);
            } else if (listaReceitas.size() == linhaTabelaAux) {
                tblReceitas.changeSelection(listaReceitas.size() - 1, 0, false, false);
            } else if (listaReceitas.get(linhaTabelaAux).getIdReceita()!= null) {
                tblReceitas.changeSelection(linhaTabelaAux, 0, false, false);
            }
        }

    }

    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/images/oculos.png")));
    }

    public void limpaCamposCasoNaoHajaCadastros() {
        if (this.listaReceitas.isEmpty()) {
            this.txtAdicao.setText("0.0");
            this.txtODCilLonge.setText("0.0");
            this.txtODCilPerto.setText("0.0");
            this.txtODDNPLonge.setText("0.0");
            this.txtODDNPPerto.setText("0.0");
            this.txtODEixoLonge.setText("0.0");
            this.txtODEixoPerto.setText("0.0");
            this.txtODEsfLonge.setText("0.0");
            this.txtODEsfPerto.setText("0.0");
            this.txtOECilLonge.setText("0.0");
            this.txtOECilPerto.setText("0.0");
            this.txtOEDNPLonge.setText("0.0");
            this.txtOEDNPPerto.setText("0.0");
            this.txtOEEixoLonge.setText("0.0");
            this.txtOEEixoPerto.setText("0.0");
            this.txtOEEsfLonge.setText("0.0");
            this.txtOEEsfPerto.setText("0.0");
            this.txtObservacao.setText(null);
            this.txtVencimento.setText(null);
            btnEditar.setEnabled(false);
            btnExcluir.setEnabled(false);
        }
    }

    public void modificarBotoesdeNavegacao() {
        if (tblReceitas.getRowCount() == 1
                || listaReceitas.isEmpty()) {
            btnPrimeiro.setEnabled(false);
            btnAnterior.setEnabled(false);
            btnProximo.setEnabled(false);
            btnUltimo.setEnabled(false);

        } else if (tblReceitas.getSelectedRow() == 0) {
            btnPrimeiro.setEnabled(false);
            btnAnterior.setEnabled(false);
            btnProximo.setEnabled(true);
            btnUltimo.setEnabled(true);
        } else if (tblReceitas.getSelectedRow() == listaReceitas.size() - 1) {
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
        this.txtAdicao.setEditable(editando);
        this.txtODCilLonge.setEditable(editando);
        this.txtODCilPerto.setEditable(editando);
        this.txtODDNPLonge.setEditable(editando);
        this.txtODDNPPerto.setEditable(editando);
        this.txtODEixoLonge.setEditable(editando);
        this.txtODEixoPerto.setEditable(editando);
        this.txtODEsfLonge.setEditable(editando);
        this.txtODEsfPerto.setEditable(editando);
        this.txtOECilLonge.setEditable(editando);
        this.txtOECilPerto.setEditable(editando);
        this.txtOEDNPLonge.setEditable(editando);
        this.txtOEDNPPerto.setEditable(editando);
        this.txtOEEixoLonge.setEditable(editando);
        this.txtOEEixoPerto.setEditable(editando);
        this.txtOEEsfLonge.setEditable(editando);
        this.txtOEEsfPerto.setEditable(editando);
        this.txtObservacao.setEditable(editando);
        this.txtVencimento.setEditable(editando);
        this.cbxCliente.setEnabled(editando);
        this.btnSalvar.setEnabled(editando);
        this.btnCancelar.setEnabled(editando);
        this.btnEditar.setEnabled(!editando);
        this.btnNovo.setEnabled(!editando);
        this.btnExcluir.setEnabled(!editando);
        this.btnFechar.setEnabled(!editando);
        this.btnPrimeiro.setEnabled(!editando);
        this.btnProximo.setEnabled(!editando);
        this.btnAnterior.setEnabled(!editando);
        this.btnUltimo.setEnabled(!editando);
        this.tblReceitas.setEnabled(!editando);
    }

    public void digitarSomenteNumeros(java.awt.event.KeyEvent evt) {
        String caracteres = "0123456789.-+";
        if (!caracteres.contains(evt.getKeyChar() + "")) {
            evt.consume();
        }
    }

    public boolean validaCampos() {
        StringBuilder mensagem = new StringBuilder();

        if ((txtVencimento.getText().length() > 0)) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            sdf.setLenient(false);
            try {
                sdf.parse(txtVencimento.getText());
            } catch (Exception e) {
                mensagem.append("Informe a data de vencimento da receita!\n");
                txtVencimento.requestFocus();

            }
        }
        if (cbxCliente.getSelectedItem() == null) {
            mensagem.append("Informe o cliente da receita");
        }
        if (mensagem.toString().length() != 0) {
            JOptionPane.showMessageDialog(this, mensagem.toString(), "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }

        return true;
    }

    public void eventoMudancaDeSelecao() {
        tblReceitas.getSelectionModel().addListSelectionListener((ListSelectionEvent evt) -> {
            if (evt.getValueIsAdjusting()) {
                return;
            }
            modificarBotoesdeNavegacao();
        });
    }

    public FormReceita(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        atualizaTabela();
        trataEdicao(false);
        modificarBotoesdeNavegacao();
        limpaCamposCasoNaoHajaCadastros();
        eventoMudancaDeSelecao();
        abas.setSelectedIndex(1);
    }

    public FormReceita(java.awt.Frame parent, boolean modal, Cliente cliente) {
        super(parent, modal);
        initComponents();
        listaClientes.clear();
        listaClientes.add(cliente);
        atualizaTabela();
        trataEdicao(false);
        modificarBotoesdeNavegacao();
        limpaCamposCasoNaoHajaCadastros();
        eventoMudancaDeSelecao();
        cbxCliente.setSelectedItem(cliente);
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

        listaReceitas = org.jdesktop.observablecollections.ObservableCollections.observableList(new ArrayList<Receita>());
        listaClientes = org.jdesktop.observablecollections.ObservableCollections.observableList(new ArrayList<Cliente>());
        converterData1 = new converter.ConverterData();
        painelNavegacao = new javax.swing.JPanel();
        btnPrimeiro = new javax.swing.JButton();
        btnAnterior = new javax.swing.JButton();
        btnProximo = new javax.swing.JButton();
        btnUltimo = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();
        abas = new javax.swing.JTabbedPane();
        abaListagem = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblReceitas = new javax.swing.JTable();
        abaDados = new javax.swing.JPanel();
        painelAcoes = new javax.swing.JPanel();
        btnNovo = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        painelDados = new javax.swing.JPanel();
        lblOlhoEsquerdo = new javax.swing.JLabel();
        lblOlhoDireito = new javax.swing.JLabel();
        lblLonge = new javax.swing.JLabel();
        lblPerto = new javax.swing.JLabel();
        lblLonge1 = new javax.swing.JLabel();
        lblPerto2 = new javax.swing.JLabel();
        txtOEEsfLonge = new javax.swing.JTextField();
        txtOEEixoLonge = new javax.swing.JTextField();
        txtOECilLonge = new javax.swing.JTextField();
        txtOEDNPLonge = new javax.swing.JTextField();
        txtOECilPerto = new javax.swing.JTextField();
        txtODEsfLonge = new javax.swing.JTextField();
        txtOEDNPPerto = new javax.swing.JTextField();
        txtOEEixoPerto = new javax.swing.JTextField();
        txtOEEsfPerto = new javax.swing.JTextField();
        txtODEixoPerto = new javax.swing.JTextField();
        txtODCilLonge = new javax.swing.JTextField();
        txtODEixoLonge = new javax.swing.JTextField();
        txtODDNPLonge = new javax.swing.JTextField();
        txtODEsfPerto = new javax.swing.JTextField();
        lblCilindrico = new javax.swing.JLabel();
        lblEsferico = new javax.swing.JLabel();
        lbLEixo = new javax.swing.JLabel();
        txtODDNPPerto = new javax.swing.JTextField();
        lblDnp = new javax.swing.JLabel();
        txtODCilPerto = new javax.swing.JTextField();
        jSeparator1 = new javax.swing.JSeparator();
        jSeparator5 = new javax.swing.JSeparator();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtObservacao = new javax.swing.JTextArea();
        txtAdicao = new javax.swing.JTextField();
        lblAdicao = new javax.swing.JLabel();
        lblVencimento = new javax.swing.JLabel();
        javax.swing.text.MaskFormatter maskData = null;
        try{
            maskData = new javax.swing.text.MaskFormatter("##/##/####");
            maskData.setPlaceholder("_");
        }catch(Exception ex){}
        txtVencimento = new javax.swing.JFormattedTextField(maskData);
        lblCliente = new javax.swing.JLabel();
        cbxCliente = new javax.swing.JComboBox<>();

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

        tblReceitas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tblReceitas.getTableHeader().setReorderingAllowed(false);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listaReceitas, tblReceitas);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idReceita}"));
        columnBinding.setColumnName("Id Receita");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${cliente}"));
        columnBinding.setColumnName("Cliente");
        columnBinding.setColumnClass(model.Cliente.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${observacao}"));
        columnBinding.setColumnName("Observacao");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${vencimentoFormatado}"));
        columnBinding.setColumnName("Vencimento");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane2.setViewportView(tblReceitas);

        abaListagem.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        abas.addTab("Listagem", abaListagem);

        painelAcoes.setBorder(javax.swing.BorderFactory.createTitledBorder("Ações"));
        painelAcoes.setLayout(new java.awt.GridLayout(1, 0));

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adicionar.png"))); // NOI18N
        btnNovo.setText("Nova Receita");
        btnNovo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });
        painelAcoes.add(btnNovo);

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/remover.png"))); // NOI18N
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

        painelDados.setBorder(javax.swing.BorderFactory.createTitledBorder("Dados da Receita"));

        lblOlhoEsquerdo.setText("OLHO ESQUERDO");

        lblOlhoDireito.setText("OLHO DIREITO");

        lblLonge.setText("LONGE");

        lblPerto.setText("PERTO");

        lblLonge1.setText("LONGE");

        lblPerto2.setText("PERTO");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblReceitas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.olhoEsquerdoEsfericoLonge}"), txtOEEsfLonge, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtOEEsfLonge.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtOEEsfLongeKeyTyped(evt);
            }
        });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblReceitas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.olhoEsquerdoEixoLonge}"), txtOEEixoLonge, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtOEEixoLonge.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtOEEixoLongeKeyTyped(evt);
            }
        });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblReceitas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.olhoEsquerdoCilindricoLonge}"), txtOECilLonge, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtOECilLonge.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtOECilLongeKeyTyped(evt);
            }
        });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblReceitas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.olhoEsquerdoDPLonge}"), txtOEDNPLonge, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtOEDNPLonge.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtOEDNPLongeKeyTyped(evt);
            }
        });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblReceitas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.olhoEsquerdoCilindricoPerto}"), txtOECilPerto, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtOECilPerto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtOECilPertoKeyTyped(evt);
            }
        });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblReceitas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.olhoDireitoEsfericoLonge}"), txtODEsfLonge, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtODEsfLonge.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtODEsfLongeKeyTyped(evt);
            }
        });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblReceitas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.olhoEsquerdoDPPerto}"), txtOEDNPPerto, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtOEDNPPerto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtOEDNPPertoKeyTyped(evt);
            }
        });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblReceitas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.olhoEsquerdoEixoPerto}"), txtOEEixoPerto, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtOEEixoPerto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtOEEixoPertoKeyTyped(evt);
            }
        });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblReceitas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.olhoEsquerdoEsfericoPerto}"), txtOEEsfPerto, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtOEEsfPerto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtOEEsfPertoKeyTyped(evt);
            }
        });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblReceitas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.olhoDireitoEixoPerto}"), txtODEixoPerto, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtODEixoPerto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtODEixoPertoKeyTyped(evt);
            }
        });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblReceitas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.olhoDireitoCilindricoLonge}"), txtODCilLonge, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtODCilLonge.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtODCilLongeKeyTyped(evt);
            }
        });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblReceitas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.olhoDireitoEixoLonge}"), txtODEixoLonge, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtODEixoLonge.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtODEixoLongeKeyTyped(evt);
            }
        });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblReceitas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.olhoDireitoDPLonge}"), txtODDNPLonge, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtODDNPLonge.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtODDNPLongeKeyTyped(evt);
            }
        });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblReceitas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.olhoDireitoEsfericoPerto}"), txtODEsfPerto, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtODEsfPerto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtODEsfPertoKeyTyped(evt);
            }
        });

        lblCilindrico.setText("CILÍNDRICO");

        lblEsferico.setText("ESFÉRICO");

        lbLEixo.setText("EIXO");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblReceitas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.olhoDireitoDPPerto}"), txtODDNPPerto, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtODDNPPerto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtODDNPPertoKeyTyped(evt);
            }
        });

        lblDnp.setText("DNP");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblReceitas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.olhoDireitoCilindricoPerto}"), txtODCilPerto, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtODCilPerto.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtODCilPertoKeyTyped(evt);
            }
        });

        jSeparator5.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout painelDadosLayout = new javax.swing.GroupLayout(painelDados);
        painelDados.setLayout(painelDadosLayout);
        painelDadosLayout.setHorizontalGroup(
            painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jSeparator1)
            .addGroup(painelDadosLayout.createSequentialGroup()
                .addComponent(lblOlhoEsquerdo)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(painelDadosLayout.createSequentialGroup()
                .addComponent(lblOlhoDireito)
                .addGap(33, 33, 33)
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLonge)
                    .addComponent(lblPerto)
                    .addComponent(lblLonge1)
                    .addComponent(lblPerto2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtODEsfPerto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(txtODEsfLonge, javax.swing.GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
                        .addComponent(txtOEEsfPerto))
                    .addComponent(txtOEEsfLonge, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblEsferico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblCilindrico)
                    .addComponent(txtOECilLonge, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOECilPerto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtODCilLonge, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtODCilPerto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelDadosLayout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addComponent(lbLEixo)
                        .addGap(51, 51, 51)
                        .addComponent(lblDnp))
                    .addGroup(painelDadosLayout.createSequentialGroup()
                        .addComponent(txtOEEixoPerto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtOEDNPPerto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(painelDadosLayout.createSequentialGroup()
                        .addComponent(txtODEixoLonge, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtODDNPLonge, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(painelDadosLayout.createSequentialGroup()
                        .addComponent(txtODEixoPerto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtODDNPPerto, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(painelDadosLayout.createSequentialGroup()
                        .addComponent(txtOEEixoLonge, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtOEDNPLonge, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(painelDadosLayout.createSequentialGroup()
                    .addGap(87, 87, 87)
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(332, Short.MAX_VALUE)))
        );
        painelDadosLayout.setVerticalGroup(
            painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelDadosLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEsferico)
                    .addComponent(lblCilindrico)
                    .addComponent(lbLEixo)
                    .addComponent(lblDnp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLonge)
                    .addComponent(txtOEEsfLonge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOEEixoLonge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOECilLonge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtOEDNPLonge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelDadosLayout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblPerto)
                            .addComponent(txtOECilPerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtOEEixoPerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtOEDNPPerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtOEEsfPerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblOlhoEsquerdo))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLonge1)
                    .addComponent(txtODEsfLonge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtODCilLonge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtODEixoLonge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtODDNPLonge, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(painelDadosLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblOlhoDireito))
                    .addGroup(painelDadosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtODEsfPerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtODCilPerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtODEixoPerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtODDNPPerto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPerto2))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(painelDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(painelDadosLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jSeparator5, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(36, Short.MAX_VALUE)))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("OBSERVAÇÃO"));

        txtObservacao.setColumns(20);
        txtObservacao.setFont(new java.awt.Font("Tahoma", 0, 11)); // NOI18N
        txtObservacao.setRows(5);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblReceitas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.observacao}"), txtObservacao, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        jScrollPane1.setViewportView(txtObservacao);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 407, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
        );

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblReceitas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.adicao}"), txtAdicao, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtAdicao.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtAdicaoKeyTyped(evt);
            }
        });

        lblAdicao.setText("ADIÇÃO");

        lblVencimento.setText("DATA DE VENCIMENTO");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblReceitas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.dataVencimento}"), txtVencimento, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setConverter(converterData1);
        bindingGroup.addBinding(binding);

        lblCliente.setText("CLIENTE");

        cbxCliente.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        org.jdesktop.swingbinding.JComboBoxBinding jComboBoxBinding = org.jdesktop.swingbinding.SwingBindings.createJComboBoxBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listaClientes, cbxCliente);
        bindingGroup.addBinding(jComboBoxBinding);
        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblReceitas, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.cliente}"), cbxCliente, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout abaDadosLayout = new javax.swing.GroupLayout(abaDados);
        abaDados.setLayout(abaDadosLayout);
        abaDadosLayout.setHorizontalGroup(
            abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaDadosLayout.createSequentialGroup()
                .addGap(62, 62, 62)
                .addComponent(lblCliente)
                .addGap(18, 18, 18)
                .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(abaDadosLayout.createSequentialGroup()
                        .addComponent(painelDados, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(156, 156, 156))
                    .addGroup(abaDadosLayout.createSequentialGroup()
                        .addComponent(cbxCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(99, 99, 99)
                        .addComponent(lblAdicao)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtAdicao, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(lblVencimento)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtVencimento, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
            .addGroup(abaDadosLayout.createSequentialGroup()
                .addGap(145, 145, 145)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 189, Short.MAX_VALUE))
            .addComponent(painelAcoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        abaDadosLayout.setVerticalGroup(
            abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaDadosLayout.createSequentialGroup()
                .addComponent(painelAcoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(painelDados, javax.swing.GroupLayout.PREFERRED_SIZE, 191, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCliente)
                    .addComponent(lblAdicao)
                    .addComponent(txtAdicao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblVencimento)
                    .addComponent(txtVencimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        abas.addTab("Dados", abaDados);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelNavegacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(abas)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painelNavegacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(abas, javax.swing.GroupLayout.PREFERRED_SIZE, 468, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        tblReceitas.setRowSelectionInterval(0, 0);
        tblReceitas.scrollRectToVisible(tblReceitas.getCellRect(0, 0, true));
    }//GEN-LAST:event_btnPrimeiroActionPerformed

    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorActionPerformed
        // TODO add your handling code here:
        btnProximo.setEnabled(true);
        btnUltimo.setEnabled(true);
        int linha = tblReceitas.getSelectedRow();
        if ((linha - 1) >= 0) {
            linha--;
            if (linha == 0) {
                btnAnterior.setEnabled(false);
                btnPrimeiro.setEnabled(false);
            }
        }
        tblReceitas.setRowSelectionInterval(linha, linha);
        tblReceitas.scrollRectToVisible(tblReceitas.getCellRect(linha, 0, true));
    }//GEN-LAST:event_btnAnteriorActionPerformed

    private void btnProximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProximoActionPerformed
        // TODO add your handling code here:
        btnAnterior.setEnabled(true);
        btnPrimeiro.setEnabled(true);
        int linha = tblReceitas.getSelectedRow();
        if ((linha + 1) <= (tblReceitas.getRowCount() - 1)) {
            linha++;
            if (linha == tblReceitas.getRowCount() - 1) {
                btnProximo.setEnabled(false);
                btnUltimo.setEnabled(false);
            }
        }
        tblReceitas.setRowSelectionInterval(linha, linha);
        tblReceitas.scrollRectToVisible(tblReceitas.getCellRect(linha, 0, true));
    }//GEN-LAST:event_btnProximoActionPerformed

    private void btnUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoActionPerformed
        // TODO add your handling code here:
        btnPrimeiro.setEnabled(true);
        btnUltimo.setEnabled(false);
        btnProximo.setEnabled(false);
        btnAnterior.setEnabled(true);
        int linha = tblReceitas.getRowCount() - 1;
        tblReceitas.setRowSelectionInterval(linha, linha);
        tblReceitas.scrollRectToVisible(tblReceitas.getCellRect(linha, 0, true));
    }//GEN-LAST:event_btnUltimoActionPerformed

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnFecharActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here
        int option = JOptionPane.showOptionDialog(this, "Deseja realmente excluir esta receita ?",
                "Caixa de confirmação",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, new String[]{"Sim", "Não"}, "Sim");
        if (option == 0) {
            int linhaSelecionada = tblReceitas.getSelectedRow();
            Receita receita = listaReceitas.get(linhaSelecionada);
            daoReceita.remover(receita);
            atualizaTabela();

        }
        limpaCamposCasoNaoHajaCadastros();
        modificarBotoesdeNavegacao();
        //tentar implementar try catch de erro de exclusão de foreign key
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        // TODO add your handling code here:
        int linhaSelecionada = tblReceitas.getSelectedRow();
        Receita receita = listaReceitas.get(linhaSelecionada);
        daoReceita.salvar(receita);
        trataEdicao(false);
        atualizaTabela();
        modificarBotoesdeNavegacao();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        //TODO add your handling code here:
        trataEdicao(false);
        if (this.tblReceitas.getRowCount() == 0) {
            this.txtAdicao.setText("");
            this.txtODCilLonge.setText("");
            this.txtODCilPerto.setText("");
            this.txtODDNPLonge.setText("");
            this.txtODDNPPerto.setText("");
            this.txtODEixoLonge.setText("");
            this.txtODEixoPerto.setText("");
            this.txtODEsfLonge.setText("");
            this.txtODEsfPerto.setText("");
            this.txtOECilLonge.setText("");
            this.txtOECilPerto.setText("");
            this.txtOEDNPLonge.setText("");
            this.txtOEDNPPerto.setText("");
            this.txtOEEixoLonge.setText("");
            this.txtOEEixoPerto.setText("");
            this.txtOEEsfLonge.setText("");
            this.txtOEEsfPerto.setText("");
            this.txtObservacao.setText("");
            this.txtVencimento.setText("");
            //this.cbxCliente.setSelectedItem("");
            btnEditar.setEnabled(false);
        }
        modificarBotoesdeNavegacao();
        atualizaTabela();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        trataEdicao(true);
//        txtNome.requestFocus();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        // TODO add your handling code here:
        Receita x = new Receita();
        listaReceitas.add((Receita) x);
        int linha = listaReceitas.size() - 1;
        tblReceitas.setRowSelectionInterval(linha, linha);
        cbxCliente.setSelectedItem(listaClientes.get(0));
        trataEdicao(true);
        //txtIdReceita.setText("gerado automaticamente");
        //        txtNome.requestFocus();
        //        txtCod.setText(null);
        //        txtSalario.setText(null);
    }//GEN-LAST:event_btnNovoActionPerformed

    private void txtOEEsfLongeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOEEsfLongeKeyTyped
        // TODO add your handling code here:
        digitarSomenteNumeros(evt);
    }//GEN-LAST:event_txtOEEsfLongeKeyTyped

    private void txtOECilLongeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOECilLongeKeyTyped
        // TODO add your handling code here:
        digitarSomenteNumeros(evt);
    }//GEN-LAST:event_txtOECilLongeKeyTyped

    private void txtOEEixoLongeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOEEixoLongeKeyTyped
        // TODO add your handling code here:
        digitarSomenteNumeros(evt);
    }//GEN-LAST:event_txtOEEixoLongeKeyTyped

    private void txtOEDNPLongeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOEDNPLongeKeyTyped
        // TODO add your handling code here:
        digitarSomenteNumeros(evt);
    }//GEN-LAST:event_txtOEDNPLongeKeyTyped

    private void txtOEEsfPertoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOEEsfPertoKeyTyped
        // TODO add your handling code here:
        digitarSomenteNumeros(evt);
    }//GEN-LAST:event_txtOEEsfPertoKeyTyped

    private void txtOECilPertoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOECilPertoKeyTyped
        // TODO add your handling code here:
        digitarSomenteNumeros(evt);
    }//GEN-LAST:event_txtOECilPertoKeyTyped

    private void txtOEEixoPertoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOEEixoPertoKeyTyped
        // TODO add your handling code here:
        digitarSomenteNumeros(evt);
    }//GEN-LAST:event_txtOEEixoPertoKeyTyped

    private void txtOEDNPPertoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtOEDNPPertoKeyTyped
        // TODO add your handling code here:
        digitarSomenteNumeros(evt);
    }//GEN-LAST:event_txtOEDNPPertoKeyTyped

    private void txtODEsfLongeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtODEsfLongeKeyTyped
        // TODO add your handling code here:
        digitarSomenteNumeros(evt);
    }//GEN-LAST:event_txtODEsfLongeKeyTyped

    private void txtODCilLongeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtODCilLongeKeyTyped
        // TODO add your handling code here:
        digitarSomenteNumeros(evt);
    }//GEN-LAST:event_txtODCilLongeKeyTyped

    private void txtODEixoLongeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtODEixoLongeKeyTyped
        // TODO add your handling code here:
        digitarSomenteNumeros(evt);
    }//GEN-LAST:event_txtODEixoLongeKeyTyped

    private void txtODDNPLongeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtODDNPLongeKeyTyped
        // TODO add your handling code here:
        digitarSomenteNumeros(evt);
    }//GEN-LAST:event_txtODDNPLongeKeyTyped

    private void txtODEsfPertoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtODEsfPertoKeyTyped
        // TODO add your handling code here:
        digitarSomenteNumeros(evt);
    }//GEN-LAST:event_txtODEsfPertoKeyTyped

    private void txtODCilPertoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtODCilPertoKeyTyped
        // TODO add your handling code here:
        digitarSomenteNumeros(evt);
    }//GEN-LAST:event_txtODCilPertoKeyTyped

    private void txtODEixoPertoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtODEixoPertoKeyTyped
        // TODO add your handling code here:
        digitarSomenteNumeros(evt);
    }//GEN-LAST:event_txtODEixoPertoKeyTyped

    private void txtODDNPPertoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtODDNPPertoKeyTyped
        // TODO add your handling code here:
        digitarSomenteNumeros(evt);
    }//GEN-LAST:event_txtODDNPPertoKeyTyped

    private void txtAdicaoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtAdicaoKeyTyped
        // TODO add your handling code here:
        digitarSomenteNumeros(evt);
    }//GEN-LAST:event_txtAdicaoKeyTyped

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
            java.util.logging.Logger.getLogger(FormReceita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormReceita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormReceita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormReceita.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FormReceita dialog = new FormReceita(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnPrimeiro;
    private javax.swing.JButton btnProximo;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnUltimo;
    private javax.swing.JComboBox<String> cbxCliente;
    private converter.ConverterData converterData1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator5;
    private javax.swing.JLabel lbLEixo;
    private javax.swing.JLabel lblAdicao;
    private javax.swing.JLabel lblCilindrico;
    private javax.swing.JLabel lblCliente;
    private javax.swing.JLabel lblDnp;
    private javax.swing.JLabel lblEsferico;
    private javax.swing.JLabel lblLonge;
    private javax.swing.JLabel lblLonge1;
    private javax.swing.JLabel lblOlhoDireito;
    private javax.swing.JLabel lblOlhoEsquerdo;
    private javax.swing.JLabel lblPerto;
    private javax.swing.JLabel lblPerto2;
    private javax.swing.JLabel lblVencimento;
    private java.util.List<Cliente> listaClientes;
    private java.util.List<Receita> listaReceitas;
    private javax.swing.JPanel painelAcoes;
    private javax.swing.JPanel painelDados;
    private javax.swing.JPanel painelNavegacao;
    private javax.swing.JTable tblReceitas;
    private javax.swing.JTextField txtAdicao;
    private javax.swing.JTextField txtODCilLonge;
    private javax.swing.JTextField txtODCilPerto;
    private javax.swing.JTextField txtODDNPLonge;
    private javax.swing.JTextField txtODDNPPerto;
    private javax.swing.JTextField txtODEixoLonge;
    private javax.swing.JTextField txtODEixoPerto;
    private javax.swing.JTextField txtODEsfLonge;
    private javax.swing.JTextField txtODEsfPerto;
    private javax.swing.JTextField txtOECilLonge;
    private javax.swing.JTextField txtOECilPerto;
    private javax.swing.JTextField txtOEDNPLonge;
    private javax.swing.JTextField txtOEDNPPerto;
    private javax.swing.JTextField txtOEEixoLonge;
    private javax.swing.JTextField txtOEEixoPerto;
    private javax.swing.JTextField txtOEEsfLonge;
    private javax.swing.JTextField txtOEEsfPerto;
    private javax.swing.JTextArea txtObservacao;
    private javax.swing.JFormattedTextField txtVencimento;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
