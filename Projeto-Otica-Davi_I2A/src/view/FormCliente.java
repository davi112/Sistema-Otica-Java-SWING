/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DAOCliente;
import java.awt.Toolkit;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import model.Cliente;
import validations.ValidaCPF;

/**
 *
 * @author DAVI
 */
public class FormCliente extends javax.swing.JDialog {

    //Declaração de atributos
    DAOCliente dao = new DAOCliente();

    //Método que carrega todos os dados do banco na JTable, 
    //Inicializa o form com a primeira linha selecionada,
    //Seleciona a linha Atual ao clicar em editar um registro e cancelar,
    //Seleciona a última linha da tabela ao clicar em novo e cancelar
    public void atualizaTabela() {

        int linhaTabelaAux = tblClientes.getSelectedRow();
        listaClientes.clear();
        listaClientes.addAll(dao.getLista());

        if (!listaClientes.isEmpty()) {
            if (linhaTabelaAux < 0) {
                tblClientes.changeSelection(0, 0, false, false);
            } else if (listaClientes.size() == linhaTabelaAux) {
                tblClientes.changeSelection(listaClientes.size() - 1, 0, false, false);
            } else if (listaClientes.get(linhaTabelaAux).getIdCliente() != null) {
                tblClientes.changeSelection(linhaTabelaAux, 0, false, false);
            }
        }

    }

    //Método chamado no construtor para definir o ícone que aparece na barra
    //de tarefas durante a execução do programa e também o ícone da
    //parte superior da janela, substituindo assim o logo default do java.
    public void setIcon() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().
                getResource("/images/oculos.png")));
    }

    //Verifica se existe algum cliente na lista que
    //recebe os dados do banco. Se estiver vazia, o método 
    // bloqueia o botão de cadastro das receitas dos clientes, 
    // caso contrário,libera o botão.
    public void verificarBotoesParaOutrasTabelas() {
        if (listaClientes.isEmpty()) {
            btnReceitas.setEnabled(false);
        } else {
            btnReceitas.setEnabled(true);
        }

    }

    //Método chamado nos eventos de Action Performed
    //dos botões excluir e cancelar. Se não houver
    //registros no banco de dados, esse metódo apaga as informações
    //da tela bloqueia os botões de editar 
    //e excluir, já que não há registros para editar excluir.
    public void limpaCamposCasoNaoHajaCadastros() {
        if (this.listaClientes.isEmpty()) {
            txtIdCliente.setText("");
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
            btnEditar.setEnabled(false);
            btnExcluir.setEnabled(false);
        }
    }

    //Método que libera os botões de pesquisa por cpf
    //caso a lista que recebe os dados do banco não esteja vazia,
    //caso contrário, os botões são liberados.
    public void HabilitarBotoesPesquisa() {
        if (listaClientes.isEmpty()) {
            txtCpfPesquisa.setEnabled(false);
            btnPesquisa.setEnabled(false);
        } else {
            txtCpfPesquisa.setEnabled(true);
            btnPesquisa.setEnabled(true);
        }
    }

    //Método que altera o estado dos botões primeiro, próximo,
    //último e anterior. Se a tabela possuir nenhum ou apenas um registro,
    //todos os botões são bloqueados. Se a primeira linha da tabela
    //estiver selecionada, os botões anterior e primeiro são bloqueados.
    //Se a última linha da tabela estiver selecionada, os botões próximo
    //e último são bloquados. Caso não entre em nenhuma dessas condições.
    //todos os botões são liberados.
    public void modificarBotoesdeNavegacao() {

        if (tblClientes.getRowCount() == 1
                || listaClientes.isEmpty()) {
            btnPrimeiro.setEnabled(false);
            btnAnterior.setEnabled(false);
            btnProximo.setEnabled(false);
            btnUltimo.setEnabled(false);

        } else if (tblClientes.getSelectedRow() == 0) {
            btnPrimeiro.setEnabled(false);
            btnAnterior.setEnabled(false);
            btnProximo.setEnabled(true);
            btnUltimo.setEnabled(true);
        } else if (tblClientes.getSelectedRow() == listaClientes.size() - 1) {
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

    //Método que libera ou bloqueia determinados campos
    //quando o usuário estiver editando as informações 
    //de um registro do banco de dados
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
        txtCpfPesquisa.setEditable(!editando);
        btnPesquisa.setEnabled(!editando);
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
        tblClientes.setEnabled(!editando);
        abas.setEnabledAt(0, !editando);
        cbxUf.setEnabled(editando);

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

    //Método que valida os campos antes de salvar um registro no banco,
    //a fim de garantir que os dados sejam informados de acordo com as
    //especificades do banco de dados
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
                mensagem.append("Informe a data de Nascimento do cliente!\n");
                txtDataNascimento.requestFocus();
            }

        }

        if (ValidaCPF.isCPF(txtCpf.getText()) == false) {
            mensagem.append("Informe um CPF válido!\n");
            txtCpf.requestFocus();
        } else {
            for (Cliente c : listaClientes) {
                if (c != listaClientes.get(tblClientes.getSelectedRow())) {
                    if (c.getCpf().equals(txtCpf.getText())) {
                        mensagem.append("o CPF não pode ser igual ao de outro cliente!\n");
                        txtCpf.requestFocus();
                    }
                }

            }
        }

        if (txtNome.getText().isEmpty()) {
            mensagem.append("Informe o nome do cliente!\n");
            txtNome.requestFocus();
        }
        if (mensagem.toString().length() != 0) {
            JOptionPane.showMessageDialog(this, mensagem.toString(), "Aviso", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    //Método que dispara um evento quando uma linha da tabela for selecionada:
    //O evento chama o método modificarBotoesdeNavegacao para bloquear ou liberar
    //os botões próximo, primeiro, último ou anterior.
    public void eventoMudancaDeSelecao() {
        tblClientes.getSelectionModel().addListSelectionListener((ListSelectionEvent evt) -> {
            if (evt.getValueIsAdjusting()) {
                return;
            }
            modificarBotoesdeNavegacao();
        });
    }

    /**
     * Creates new form FormCidade
     *
     * @param parent
     * @param modal
     */
    //Construtor da classe
    public FormCliente(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setIcon();
        atualizaTabela();
        HabilitarBotoesPesquisa();
        trataEdicao(false);
        verificarBotoesParaOutrasTabelas();
        limpaCamposCasoNaoHajaCadastros();
        modificarBotoesdeNavegacao();
        eventoMudancaDeSelecao();
        txtIdCliente.setEnabled(false);
        btnPesquisa.setEnabled(false);
        abas.setSelectedIndex(1);
    }

    //Método initComponents -> não modificável, uma vez que o seu conteúdo
    //é gerado automaticamente quando coloca-se um componente na tela manualmente,
    //evitando que tenha-se que digitar os códigos para o funcionamento dos botões,
    //campos de texto, listas, entre outros.
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        bindingGroup = new org.jdesktop.beansbinding.BindingGroup();

        listaClientes = org.jdesktop.observablecollections.ObservableCollections.observableList(new ArrayList<Cliente>());
        converterData2 = new converter.ConverterData();
        painelNavegacao = new javax.swing.JPanel();
        btnPrimeiro = new javax.swing.JButton();
        btnAnterior = new javax.swing.JButton();
        btnProximo = new javax.swing.JButton();
        btnUltimo = new javax.swing.JButton();
        btnFechar = new javax.swing.JButton();
        abas = new javax.swing.JTabbedPane();
        abaListagem = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
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
        painelInformacoesPessoais = new javax.swing.JPanel();
        txtIdCliente = new javax.swing.JTextField();
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
        painelOutrasInformacoes = new javax.swing.JPanel();
        btnReceitas = new javax.swing.JButton();
        painelPesquisa = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        btnPesquisa = new javax.swing.JButton();
        javax.swing.text.MaskFormatter maskCpf1 = null;
        try{
            maskCpf1 = new javax.swing.text.MaskFormatter("###.###.###-##");
            maskCpf1.setPlaceholder("_");
        }catch(Exception ex){}
        txtCpfPesquisa = new javax.swing.JFormattedTextField(maskCpf1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Cadastro de Clientes");
        setMaximumSize(new java.awt.Dimension(21474836, 21474836));
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

        abaListagem.setLayout(new java.awt.BorderLayout());

        tblClientes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        tblClientes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblClientes.getTableHeader().setReorderingAllowed(false);

        org.jdesktop.swingbinding.JTableBinding jTableBinding = org.jdesktop.swingbinding.SwingBindings.createJTableBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, listaClientes, tblClientes);
        org.jdesktop.swingbinding.JTableBinding.ColumnBinding columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${idCliente}"));
        columnBinding.setColumnName("Id Cliente");
        columnBinding.setColumnClass(Integer.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nome}"));
        columnBinding.setColumnName("Nome");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${email}"));
        columnBinding.setColumnName("Email");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        columnBinding = jTableBinding.addColumnBinding(org.jdesktop.beansbinding.ELProperty.create("${nascimentoFormatado}"));
        columnBinding.setColumnName("Data Nascimento");
        columnBinding.setColumnClass(String.class);
        columnBinding.setEditable(false);
        bindingGroup.addBinding(jTableBinding);
        jTableBinding.bind();
        jScrollPane1.setViewportView(tblClientes);

        abaListagem.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        abas.addTab("Listagem", abaListagem);

        painelAcoes.setBorder(javax.swing.BorderFactory.createTitledBorder("Ações"));
        painelAcoes.setLayout(new java.awt.GridLayout(1, 0));

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/adicionar.png"))); // NOI18N
        btnNovo.setText("Novo Cliente");
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
        painelContato.setMaximumSize(new java.awt.Dimension(122, 92));

        lblTelefone.setText("TELEFONE");

        lblEmail.setText("E-MAIL");

        org.jdesktop.beansbinding.Binding binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblClientes, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.telefone}"), txtTelefone, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        txtEmail.setMaximumSize(new java.awt.Dimension(6, 20));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblClientes, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.email}"), txtEmail, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout painelContatoLayout = new javax.swing.GroupLayout(painelContato);
        painelContato.setLayout(painelContatoLayout);
        painelContatoLayout.setHorizontalGroup(
            painelContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelContatoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblEmail)
                    .addComponent(lblTelefone))
                .addGap(34, 34, 34)
                .addGroup(painelContatoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtTelefone))
                .addContainerGap())
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

        painelEndereco.setBorder(javax.swing.BorderFactory.createTitledBorder("Endereço"));

        lblRua.setText("RUA");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblClientes, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.rua}"), txtRua, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblBairro.setText("BAIRRO");

        lblCep.setText("CEP");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblClientes, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.complemento}"), txtComplemento, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblClientes, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.cep}"), txtCep, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblClientes, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.bairro}"), txtBairro, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblNúmero.setText("NÚMERO");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblClientes, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.numero}"), txtNumero, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtNumero.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNumeroKeyTyped(evt);
            }
        });

        lblComplemento.setText("COMPLEMENTO");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblClientes, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.cidade}"), txtCidade, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblCidade.setText("CIDADE");

        lblUf.setText("UF");

        cbxUf.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AC", "AM", "AP", "RO", "RR", "DF", "MG", "SP", "RJ", "ES", "SE", "CE", "BA", "RS", "MT", "MS", "GO", "SC", "TO", "PR", "PA", "RN", "AL", "MA", "PB", "PI", "PE" }));
        cbxUf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblClientes, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.uf}"), cbxUf, org.jdesktop.beansbinding.BeanProperty.create("selectedItem"));
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout painelEnderecoLayout = new javax.swing.GroupLayout(painelEndereco);
        painelEndereco.setLayout(painelEnderecoLayout);
        painelEnderecoLayout.setHorizontalGroup(
            painelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelEnderecoLayout.createSequentialGroup()
                .addGroup(painelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblComplemento, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(painelEnderecoLayout.createSequentialGroup()
                        .addGroup(painelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNúmero)
                            .addComponent(lblCep)
                            .addComponent(lblBairro)
                            .addComponent(lblRua)
                            .addComponent(lblCidade)
                            .addComponent(lblUf))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxUf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 168, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCep)
                    .addComponent(txtBairro)
                    .addComponent(txtRua))
                .addGap(164, 164, 164))
        );
        painelEnderecoLayout.setVerticalGroup(
            painelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelEnderecoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRua)
                    .addComponent(txtRua, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(painelEnderecoLayout.createSequentialGroup()
                        .addComponent(lblBairro)
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
                    .addComponent(lblComplemento))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(painelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCidade))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUf)
                    .addComponent(cbxUf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(33, Short.MAX_VALUE))
        );

        painelInformacoesPessoais.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações pessoais"));

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblClientes, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.idCliente}"), txtIdCliente, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        lblCodigo.setText("CÓDIGO");

        lblNome.setText("NOME");

        lblCpf.setText("CPF");

        lblNascimento.setText(" NASCIMENTO");

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblClientes, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.nome}"), txtNome, org.jdesktop.beansbinding.BeanProperty.create("text"));
        bindingGroup.addBinding(binding);

        txtNome.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtNomeKeyTyped(evt);
            }
        });

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblClientes, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.cpf}"), txtCpf, org.jdesktop.beansbinding.BeanProperty.create("value"));
        bindingGroup.addBinding(binding);

        binding = org.jdesktop.beansbinding.Bindings.createAutoBinding(org.jdesktop.beansbinding.AutoBinding.UpdateStrategy.READ_WRITE, tblClientes, org.jdesktop.beansbinding.ELProperty.create("${selectedElement.dataNascimento}"), txtDataNascimento, org.jdesktop.beansbinding.BeanProperty.create("value"));
        binding.setConverter(converterData2);
        bindingGroup.addBinding(binding);

        javax.swing.GroupLayout painelInformacoesPessoaisLayout = new javax.swing.GroupLayout(painelInformacoesPessoais);
        painelInformacoesPessoais.setLayout(painelInformacoesPessoaisLayout);
        painelInformacoesPessoaisLayout.setHorizontalGroup(
            painelInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInformacoesPessoaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNome)
                    .addComponent(lblCpf)
                    .addComponent(lblNascimento)
                    .addComponent(lblCodigo))
                .addGap(18, 18, 18)
                .addGroup(painelInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtDataNascimento)
                    .addGroup(painelInformacoesPessoaisLayout.createSequentialGroup()
                        .addGroup(painelInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtNome, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                            .addComponent(txtIdCliente)
                            .addComponent(txtCpf))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        painelInformacoesPessoaisLayout.setVerticalGroup(
            painelInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelInformacoesPessoaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCodigo)
                    .addComponent(txtIdCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNome))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblCpf))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(painelInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        painelOutrasInformacoes.setBorder(javax.swing.BorderFactory.createTitledBorder("Outras Informações"));

        btnReceitas.setText("Gerenciar Receitas do Cliente");
        btnReceitas.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnReceitas.setEnabled(false);
        btnReceitas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnReceitasActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout painelOutrasInformacoesLayout = new javax.swing.GroupLayout(painelOutrasInformacoes);
        painelOutrasInformacoes.setLayout(painelOutrasInformacoesLayout);
        painelOutrasInformacoesLayout.setHorizontalGroup(
            painelOutrasInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelOutrasInformacoesLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addComponent(btnReceitas, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        painelOutrasInformacoesLayout.setVerticalGroup(
            painelOutrasInformacoesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, painelOutrasInformacoesLayout.createSequentialGroup()
                .addContainerGap(26, Short.MAX_VALUE)
                .addComponent(btnReceitas)
                .addContainerGap())
        );

        javax.swing.GroupLayout abaDadosLayout = new javax.swing.GroupLayout(abaDados);
        abaDados.setLayout(abaDadosLayout);
        abaDadosLayout.setHorizontalGroup(
            abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(painelAcoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(abaDadosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(painelInformacoesPessoais, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(painelContato, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(painelOutrasInformacoes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(35, 35, 35)
                .addComponent(painelEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(56, Short.MAX_VALUE))
        );
        abaDadosLayout.setVerticalGroup(
            abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(abaDadosLayout.createSequentialGroup()
                .addGap(5, 5, 5)
                .addComponent(painelAcoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(abaDadosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(abaDadosLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(painelInformacoesPessoais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(painelContato, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(painelOutrasInformacoes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(abaDadosLayout.createSequentialGroup()
                        .addGap(36, 36, 36)
                        .addComponent(painelEndereco, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        abas.addTab("Dados", abaDados);

        painelPesquisa.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisar Cliente"));

        jLabel15.setText("CPF");

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
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtCpfPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(btnPesquisa)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        painelPesquisaLayout.setVerticalGroup(
            painelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(painelPesquisaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(painelPesquisaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(btnPesquisa)
                    .addComponent(txtCpfPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(abas)
            .addComponent(painelNavegacao, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(230, 230, 230)
                .addComponent(painelPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(painelNavegacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(painelPesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(abas, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        bindingGroup.bind();

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFecharActionPerformed
        // TODO add your handling code here:
        dispose();
    }//GEN-LAST:event_btnFecharActionPerformed

    //Evento que seleciona a primeira linha da tabela ao clicar no botão Primeiro
    private void btnPrimeiroActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrimeiroActionPerformed
        // TODO add your handling code here:
        btnAnterior.setEnabled(false);
        btnPrimeiro.setEnabled(false);
        btnProximo.setEnabled(true);
        btnUltimo.setEnabled(true);
        tblClientes.setRowSelectionInterval(0, 0);
        tblClientes.scrollRectToVisible(tblClientes.getCellRect(0, 0, true));

    }//GEN-LAST:event_btnPrimeiroActionPerformed

    //Evento que seleciona a linha anterior a que está selecioanda
    //na tabela ao clicar no botão anterior
    private void btnAnteriorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnteriorActionPerformed
        // TODO add your handling code here:
        btnProximo.setEnabled(true);
        btnUltimo.setEnabled(true);
        int linha = tblClientes.getSelectedRow();
        if ((linha - 1) >= 0) {
            linha--;
            if (linha == 0) {
                btnAnterior.setEnabled(false);
                btnPrimeiro.setEnabled(false);
            }
        }
        tblClientes.setRowSelectionInterval(linha, linha);
        tblClientes.scrollRectToVisible(tblClientes.getCellRect(linha, 0, true));

    }//GEN-LAST:event_btnAnteriorActionPerformed

   //Evento que seleciona a próxima linha com relação a que está selecioanda
   //na tabela ao clicar no botão próximo
    private void btnProximoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProximoActionPerformed
        // TODO add your handling code here:
        btnAnterior.setEnabled(true);
        btnPrimeiro.setEnabled(true);
        int linha = tblClientes.getSelectedRow();
        if ((linha + 1) <= (tblClientes.getRowCount() - 1)) {
            linha++;
            if (linha == tblClientes.getRowCount() - 1) {
                btnProximo.setEnabled(false);
                btnUltimo.setEnabled(false);
            }
        }
        tblClientes.setRowSelectionInterval(linha, linha);
        tblClientes.scrollRectToVisible(tblClientes.getCellRect(linha, 0, true));
    }//GEN-LAST:event_btnProximoActionPerformed

    //Evento que seleciona a última linha da tabela ao clicar no botão Último
    private void btnUltimoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUltimoActionPerformed
        // TODO add your handling code here:
        btnPrimeiro.setEnabled(true);
        btnUltimo.setEnabled(false);
        btnProximo.setEnabled(false);
        btnAnterior.setEnabled(true);
        int linha = tblClientes.getRowCount() - 1;
        tblClientes.setRowSelectionInterval(linha, linha);
        tblClientes.scrollRectToVisible(tblClientes.getCellRect(linha, 0, true));
    }//GEN-LAST:event_btnUltimoActionPerformed

    //Método que exclui umr registro do banco de dados
    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here
        int option = JOptionPane.showOptionDialog(this, "Deseja realmente excluir essse cliente ?",
                "Caixa de confirmação",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null, new String[]{"Sim", "Não"}, "Sim");
        if (option == 0) {

            int linhaSelecionada = tblClientes.getSelectedRow();
            Cliente cliente = listaClientes.get(linhaSelecionada);
            dao.remover(cliente);
            atualizaTabela();

        }
        limpaCamposCasoNaoHajaCadastros();
        verificarBotoesParaOutrasTabelas();
        modificarBotoesdeNavegacao();
        HabilitarBotoesPesquisa();
    }//GEN-LAST:event_btnExcluirActionPerformed

    //Método que salva um novo registro no banco de dados ou salva alguma alteração
    //de algum registro
    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        // TODO add your handling code here:
        if (validaCampos()) {
            int linhaSelecionada = tblClientes.getSelectedRow();
            Cliente obj = listaClientes.get(linhaSelecionada);
            dao.salvar(obj);
            trataEdicao(false);
            int lastIndex = listaClientes.size() - 1;
            atualizaTabela();
            tblClientes.changeSelection(lastIndex, 0, false, false);
            verificarBotoesParaOutrasTabelas();
            HabilitarBotoesPesquisa();
            modificarBotoesdeNavegacao();
        }
    }//GEN-LAST:event_btnSalvarActionPerformed

    //Método que cancela a alteração ou inserção de algum registro no 
    //banco de dados
    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        // TODO add your handling code here:
        trataEdicao(false);
        atualizaTabela();
        modificarBotoesdeNavegacao();
        limpaCamposCasoNaoHajaCadastros();
        verificarBotoesParaOutrasTabelas();
        HabilitarBotoesPesquisa();

    }//GEN-LAST:event_btnCancelarActionPerformed

    //Método que habilita os campos para a edição dos registros do banco
    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        // TODO add your handling code here:
        trataEdicao(true);
        txtNome.requestFocus();
        verificarBotoesParaOutrasTabelas();

    }//GEN-LAST:event_btnEditarActionPerformed

    //Método que cria um novo objeto á ser salvo no banco de dados
    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        // TODO add your handling code here:
       
        Cliente x = new Cliente();
        listaClientes.add((Cliente) x);
        int linha = listaClientes.size() - 1;
        tblClientes.setRowSelectionInterval(linha, linha);
        trataEdicao(true);
        btnReceitas.setEnabled(false);
        txtIdCliente.setText("gerado automaticamente");
        txtNome.requestFocus();
    }//GEN-LAST:event_btnNovoActionPerformed

    //Método que abre a tela de cadastro de receitas do cliente
    private void btnReceitasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReceitasActionPerformed
        // TODO add your handling code here:
        Cliente cliente = this.listaClientes.get(this.tblClientes.getSelectedRow());
        FormReceita formReceita = new FormReceita(new javax.swing.JFrame(), true, cliente);
        formReceita.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        formReceita.setTitle("Manutenção de Clientes");
        formReceita.setLocationRelativeTo(null);
        formReceita.setResizable(false);
        formReceita.setVisible(true);
    }//GEN-LAST:event_btnReceitasActionPerformed

    //Método que pesquisa o cliente por cpf e seleciona a linha que
    //corresponde ao cliente encontrado na tabela
    private void btnPesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisaActionPerformed
        // TODO add your handling code here

        int index = listaClientes.indexOf(dao.pesquisar(txtCpfPesquisa.getText()));
        tblClientes.changeSelection(index, 1, false, false);

        if (index < 0) {
            JOptionPane.showMessageDialog(this, "Não foi possível localizar esse cliente!",
                    "Aviso!", JOptionPane.INFORMATION_MESSAGE);
        }

    }//GEN-LAST:event_btnPesquisaActionPerformed

    //Evento que dispara quando se digita uma tecla no campo txtNumero,
    //evitando que sejam digitadas letras
    private void txtNumeroKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNumeroKeyTyped
        // TODO add your handling code here:
        digitarSomenteNumeros(evt);
    }//GEN-LAST:event_txtNumeroKeyTyped

    //Método que habilita o botão de pesquisar o cliente somente quando
    //o cpf for validado pelo método estático isCpf da classe ValidaCPF
    //(dispara quando a tecla for clicada)
    private void txtCpfPesquisaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCpfPesquisaKeyTyped
        // TODO add your handling code here:
        if (ValidaCPF.isCPF(txtCpfPesquisa.getText()) == true) {
            btnPesquisa.setEnabled(true);
        } else {
            btnPesquisa.setEnabled(false);
        }
    }//GEN-LAST:event_txtCpfPesquisaKeyTyped
    
    //Método que habilita o botão de pesquisar o cliente somente quando
    //o cpf for validado pelo método estático isCpf da classe ValidaCPF.
    //(dispara quando ao soltar a tecla)
    private void txtCpfPesquisaKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCpfPesquisaKeyReleased
        // TODO add your handling code here:
        if (ValidaCPF.isCPF(txtCpfPesquisa.getText()) == true) {
            btnPesquisa.setEnabled(true);
        } else {
            btnPesquisa.setEnabled(false);
        }
    }//GEN-LAST:event_txtCpfPesquisaKeyReleased

    //Método que permite digitar somente letras no campo nome
    private void txtNomeKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeKeyTyped
        // TODO add your handling code here:
        digitarSomenteLetras(evt);
    }//GEN-LAST:event_txtNomeKeyTyped

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
            java.util.logging.Logger.getLogger(FormCliente.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormCliente.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormCliente.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormCliente.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FormCliente dialog = new FormCliente(new javax.swing.JFrame(), true);
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
    private javax.swing.JButton btnReceitas;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JButton btnUltimo;
    private javax.swing.JComboBox<String> cbxUf;
    private converter.ConverterData converterData2;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblBairro;
    private javax.swing.JLabel lblCep;
    private javax.swing.JLabel lblCidade;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblComplemento;
    private javax.swing.JLabel lblCpf;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblNascimento;
    private javax.swing.JLabel lblNome;
    private javax.swing.JLabel lblNúmero;
    private javax.swing.JLabel lblRua;
    private javax.swing.JLabel lblTelefone;
    private javax.swing.JLabel lblUf;
    private java.util.List<Cliente> listaClientes;
    private javax.swing.JPanel painelAcoes;
    private javax.swing.JPanel painelContato;
    private javax.swing.JPanel painelEndereco;
    private javax.swing.JPanel painelInformacoesPessoais;
    private javax.swing.JPanel painelNavegacao;
    private javax.swing.JPanel painelOutrasInformacoes;
    private javax.swing.JPanel painelPesquisa;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtBairro;
    private javax.swing.JFormattedTextField txtCep;
    private javax.swing.JTextField txtCidade;
    private javax.swing.JTextField txtComplemento;
    private javax.swing.JFormattedTextField txtCpf;
    private javax.swing.JFormattedTextField txtCpfPesquisa;
    private javax.swing.JFormattedTextField txtDataNascimento;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtIdCliente;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JTextField txtRua;
    private javax.swing.JFormattedTextField txtTelefone;
    private org.jdesktop.beansbinding.BindingGroup bindingGroup;
    // End of variables declaration//GEN-END:variables
}
