import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.table.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import java.io.*;

public class InternalFrameCadastrar extends JInternalFrame {
    private JLabel lblTitulo;
    private JLabel lblNome;
    private JTextField tNome;
    private JLabel lblSexo;
    private JComboBox cmbSexo;
    private JLabel lblEstCivil;
    private JComboBox cmbEstCivil;
    private JLabel lblDtNasc;
    private JTextField tDtNasc;
    private JLabel lblCpf;
    private JTextField tCpf;
    private JLabel lblRg;
    private JTextField tRg;
    private JLabel lblEndereco;
    private JTextField tEndereco;
    private JLabel lblBairro;
    private JComboBox cmbBairro;
    private JLabel lblZona;
    private JComboBox cmbZona;
    private JLabel lblTelefone;
    private JTextField tTelefone;
    private JLabel lblNatural;
    private JTextField tNatural;
    private JLabel lblTempo;
    private JTextField tTempo;
    private JLabel lblOcupacao;
    private JTextField tOcupacao;
    private JLabel lblRemuneracao;
    private JTextField tRemuneracao;
    private JLabel lblOtRendas;
    private JTextField tOtRendas;
    private JLabel lblCadUnico;
    private JComboBox cmbCadUnico;
    private JLabel lblNis;
    private JTextField tNis;
    private JLabel lblBlFam;
    private JComboBox cmbBlFam;
    private JLabel lblBpc;
    private JComboBox cmbBpc;
    private JLabel lblEscolarid;
    private JComboBox cmbEscolarid;
    private JLabel lblImovel;
    private JComboBox cmbImovel;
    private JLabel lblComodos;
    private JTextField tComodos;
    private JLabel lblAluguel;
    private JTextField tAluguel;
    private JLabel lblRisco;
    private JComboBox cmbRisco;
    private JLabel lblEmail;
    private JTextField tEmail;
    private JLabel lblDeficiencia;
    private JComboBox cmbDeficiencia;
    private JLabel lblObserv;
    private JTextArea cmpObserv;
    private JScrollPane scrObserv;
    private JLabel lblDependentes;
    private DefaultTableModel dmParentesco;
    private JTable tblParentesco;
    private JScrollPane scrParentesco;
    private JTextField tNomeTbl;
    private JTextField tDocTbl;
    private JTextField tIdadeTbl;
    private JComboBox cmbParentescoTbl;
    private JTextField tOcupacaoTbl;
    private JTextField tRealTbl;
    private JLabel lblRenda;
    private JTextField tRenda;
    private JLabel lblObs;
    private JButton btnCadastrar;
    private JButton btnCancelar;
    private String ip;
    private Connection con;
    public Statement stm;
    public ResultSet rs;

    public InternalFrameCadastrar() {
        super("CADASTRO",
              true, //resizable
               false, //closable
              true, //maximizable
               true);//iconifiable
        this.setLocation(0, 67);
        this.setSize(1080, 630);

        JPanel panel = new JPanel();
        panel.setLayout(null);

        lblTitulo = new JLabel("CADASTRO DE DIAGNÓSTICO HABITACIONAL");
        lblTitulo.setBounds(new Rectangle(90, 10, 1000, 45));
        lblTitulo.setFont(new java.awt.Font("Arial", Font.BOLD, 40));
        this.getContentPane().add(lblTitulo, null);

        lblNome = new JLabel("NOME:");
        lblNome.setBounds(new Rectangle(50, 70, 80, 20));
        this.getContentPane().add(lblNome, null);
        tNome = new JTextField();
        tNome.setBounds(new Rectangle(95, 70, 500, 20));
        tNome.setDocument(new TamanhoMaximo(50));
        this.getContentPane().add(tNome, null);
        addHierarchyListener(new HierarchyListener() {
            public void hierarchyChanged(HierarchyEvent e) {
                if ((e.getChangeFlags() & HierarchyEvent.SHOWING_CHANGED) != 0) {
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            if (isVisible()) {
                                tNome.requestFocus();
                            }
                        }
                    });
                }
            }
        });

        lblSexo = new JLabel("SEXO:");
        lblSexo.setBounds(new Rectangle(640, 70, 80, 20));
        this.getContentPane().add(lblSexo, null);
        String sexo[] = {"Masculino", "Feminino"};
        cmbSexo = new JComboBox(sexo);
        cmbSexo.setBounds(new Rectangle(690, 70, 90, 20));
        cmbSexo.setBackground(Color.white);
        cmbSexo.setSelectedIndex(-1);
        this.getContentPane().add(cmbSexo, null);

        lblEstCivil = new JLabel("ESTADO CIVIL:");
        lblEstCivil.setBounds(new Rectangle(820, 70, 100, 20));
        this.getContentPane().add(lblEstCivil, null);
        String estCivil[] = {"Solteiro(a)", "Casado(a)", "União Estável", "Separado(a)", "Divorciado(a)", "Viúvo(a)"};
        cmbEstCivil = new JComboBox(estCivil);
        cmbEstCivil.setBounds(new Rectangle(910, 70, 100, 20));
        cmbEstCivil.setBackground(Color.white);
        cmbEstCivil.setSelectedIndex(-1);
        this.getContentPane().add(cmbEstCivil, null);

        lblDtNasc = new JLabel("DATA DE NASCIMENTO:");
        lblDtNasc.setBounds(new Rectangle(50, 110, 140, 20));
        this.getContentPane().add(lblDtNasc, null);
        try {
            tDtNasc = new JFormattedTextField(new MaskFormatter("##/##/####"));
            tDtNasc.setBounds(new Rectangle(190, 110, 80, 20));
            this.getContentPane().add(tDtNasc, null);
        } catch (Exception e) {
        }

        lblCpf = new JLabel("CPF:");
        lblCpf.setBounds(new Rectangle(330, 110, 60, 20));
        this.getContentPane().add(lblCpf, null);
        try {
            tCpf = new JFormattedTextField(new MaskFormatter("###.###.###-##"));
            tCpf.setBounds(new Rectangle(370, 110, 120, 20));
            this.getContentPane().add(tCpf, null);
        } catch (Exception e) {
        }

        lblRg = new JLabel("RG:");
        lblRg.setBounds(new Rectangle(550, 110, 40, 20));
        this.getContentPane().add(lblRg, null);
        tRg = new JTextField();
        tRg.setBounds(new Rectangle(580, 110, 90, 20));
        tRg.setDocument(new TamanhoMaximo(10));
        this.getContentPane().add(tRg, null);
        
        lblTelefone = new JLabel("TELEFONE (fixo ou celular):");
        lblTelefone.setBounds(new Rectangle(730, 110, 160, 20));
        this.getContentPane().add(lblTelefone, null);
        try {
            tTelefone = new JFormattedTextField(new MaskFormatter("(##) ####-####"));
            tTelefone.setBounds(new Rectangle(890, 110, 122, 20));
            this.getContentPane().add(tTelefone, null);
        } catch (Exception e) {
        }

        lblEndereco = new JLabel("ENDEREÇO:");
        lblEndereco.setBounds(new Rectangle(50, 150, 80, 20));
        this.getContentPane().add(lblEndereco, null);
        tEndereco = new JTextField();
        tEndereco.setBounds(new Rectangle(120, 150, 640, 20));
        tEndereco.setDocument(new TamanhoMaximo(100));
        this.getContentPane().add(tEndereco, null);

        lblBairro = new JLabel("BAIRRO:");
        lblBairro.setBounds(new Rectangle(780, 150, 80, 20));
        this.getContentPane().add(lblBairro, null);
        String bairros[] = {"Altino Barbosa", "Bela Vista", "Belvedere", "Castro Pires", "Centro", "Cidade Alta", "Concórdia", "Dr. Laerte Laender", "Eldorado", "Eucalipto" +
                "", "Fátima", "Fazenda Arnô", "Felicidade", "Filadélfia", "Floresta", "Frei Dimas", "Frei Júlio", "Frimusa", "Funcionários", "Gangorrinha" +
                "", "Grão Pará", "Indaiá", "Ipiranga", "Itaguaçu", "Jardim das Acácias", "Jardim Iracema", "Jardim São Paulo", "Jardim Serra Verde", "Joaquim Pedrosa", "Lourival Soares da Costa" +
                "", "Manoel Pimenta", "Marajoara", "Matinha", "Mucuri", "Monte Carlo", "Novo Horizonte", "Olga Corrêa Prates", "Olga Corrêa Ribeiro", "Palmeiras", "Pampulhinha" +
                "", "Santa Clara", "São Benedito", "São Cristovão", "São Diogo", "São Francisco", "São Jacinto", "Solidariedade", "Tabajaras", "Taquara", "Teófilo Rocha" +
                "", "Turma 37", "Turma 38", "Vila Barreiros", "Vila Betel", "Vila da Palha", "Vila Esperança", "Vila Jacaré", "Vilinha", "Vila Ramos", "Vila São João" +
                "", "Viriato", "Outro:"};
        cmbBairro = new JComboBox(bairros);
        cmbBairro.setBounds(new Rectangle(832, 150, 180, 20));
        cmbBairro.setBackground(Color.white);
        cmbBairro.setSelectedIndex(-1);
        this.getContentPane().add(cmbBairro, null);

        lblZona = new JLabel("ZONA:");
        lblZona.setBounds(new Rectangle(50, 190, 60, 20));
        this.getContentPane().add(lblZona, null);
        String zona[] = {"Urbana", "Rural"};
        cmbZona = new JComboBox(zona);
        cmbZona.setBounds(new Rectangle(95, 190, 80, 20));
        cmbZona.setBackground(Color.white);
        cmbZona.setSelectedIndex(-1);
        this.getContentPane().add(cmbZona, null);

        lblNatural = new JLabel("NATURALIDADE:");
        lblNatural.setBounds(new Rectangle(210, 190, 100, 20));
        this.getContentPane().add(lblNatural, null);
        tNatural = new JTextField();
        tNatural.setBounds(new Rectangle(308, 190, 330, 20));
        tNatural.setDocument(new TamanhoMaximo(40));
        this.getContentPane().add(tNatural, null);

        lblTempo = new JLabel("TEMPO DE MORADIA EM TEÓFILO OTONI (em anos):");
        lblTempo.setBounds(new Rectangle(675, 190, 300, 20));
        this.getContentPane().add(lblTempo, null);
        tTempo = new JTextField();
        tTempo.setBounds(new Rectangle(973, 190, 40, 20));
        tTempo.setDocument(new TamanhoMaximo(2));
        tTempo.addKeyListener(new KeyAdapter() {  // cria um listener ouvinte de digitação do fieldNumero
            public void keyReleased(KeyEvent evt) {  // cria um ouvinte para cada tecla pressionada
                tTempo.setText(tTempo.getText().replaceAll("[^0-9]", "")); // faz com que pegue o texto a cada tecla digitada, e substituir tudo que não(^) seja numero  por ""
            }
        });
        this.getContentPane().add(tTempo, null);
        
        lblOcupacao = new JLabel("OCUPAÇÃO:");
        lblOcupacao.setBounds(new Rectangle(50, 230, 80, 20));
        this.getContentPane().add(lblOcupacao, null);
        tOcupacao = new JTextField();
        tOcupacao.setBounds(new Rectangle(130, 230, 280, 20));
        tOcupacao.setDocument(new TamanhoMaximo(30));
        this.getContentPane().add(tOcupacao, null);

        lblRemuneracao = new JLabel("REMUNERAÇÃO:   R$");
        lblRemuneracao.setBounds(new Rectangle(485, 230, 120, 20));
        this.getContentPane().add(lblRemuneracao, null);
        tRemuneracao = new TextFieldMoedaReal();
        tRemuneracao.setBounds(new Rectangle(607, 230, 100, 20));
        tRemuneracao.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent arg0) {}
            public void focusLost(FocusEvent arg0) {
                somarRenda();
            }
        });
        this.getContentPane().add(tRemuneracao, null);
        
        lblOtRendas = new JLabel("OUTRAS RENDAS:   R$");
        lblOtRendas.setBounds(new Rectangle(780, 230, 130, 20));
        this.getContentPane().add(lblOtRendas, null);
        tOtRendas = new TextFieldMoedaReal();
        tOtRendas.setBounds(new Rectangle(913, 230, 100, 20));
        tOtRendas.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent arg0) {}
            public void focusLost(FocusEvent arg0) {
                somarRenda();
            }
        });
        this.getContentPane().add(tOtRendas, null);

        lblCadUnico = new JLabel("CADÚNICO:");
        lblCadUnico.setBounds(new Rectangle(50, 270, 90, 20));
        this.getContentPane().add(lblCadUnico, null);
        String cadUnico[] = {"Sim", "Não"};
        cmbCadUnico = new JComboBox(cadUnico);
        cmbCadUnico.setBounds(new Rectangle(118, 270, 50, 20));
        cmbCadUnico.setBackground(Color.white);
        cmbCadUnico.setSelectedIndex(-1);
        this.getContentPane().add(cmbCadUnico, null);

        lblNis = new JLabel("NIS:");
        lblNis.setBounds(new Rectangle(210, 270, 50, 20));
        this.getContentPane().add(lblNis, null);
        try {
            tNis = new JFormattedTextField(new MaskFormatter("###.#####.##-#"));
            tNis.setBounds(new Rectangle(240, 270, 120, 20));
            this.getContentPane().add(tNis, null);
        } catch (Exception e) {
        }

        lblBlFam = new JLabel("BOLSA FAMÍLIA:");
        lblBlFam.setBounds(new Rectangle(407, 270, 110, 20));
        this.getContentPane().add(lblBlFam, null);
        String bolsaFam[] = {"Sim", "Não"};
        cmbBlFam = new JComboBox(bolsaFam);
        cmbBlFam.setBounds(new Rectangle(505, 270, 50, 20));
        cmbBlFam.setBackground(Color.white);
        cmbBlFam.setSelectedIndex(-1);
        this.getContentPane().add(cmbBlFam, null);

        lblBpc = new JLabel("BPC:");
        lblBpc.setBounds(new Rectangle(605, 270, 40, 20));
        this.getContentPane().add(lblBpc, null);
        String bpc[] = {"Sim", "Não"};
        cmbBpc = new JComboBox(bpc);
        cmbBpc.setBounds(new Rectangle(640, 270, 50, 20));
        cmbBpc.setBackground(Color.white);
        cmbBpc.setSelectedIndex(-1);
        this.getContentPane().add(cmbBpc, null);

        lblEscolarid = new JLabel("ESCOLARIDADE:");
        lblEscolarid.setBounds(new Rectangle(740, 270, 120, 20));
        this.getContentPane().add(lblEscolarid, null);
        String escolaridade[] = {"Sem escolaridade", "Ensino fundamental", "Ensino médio", "Superior imcompleto", "Superior completo", "Pós-graduado", "Mestrado", "Doutorado"};
        cmbEscolarid = new JComboBox(escolaridade);
        cmbEscolarid.setBounds(new Rectangle(842, 270, 170, 20));
        cmbEscolarid.setBackground(Color.white);
        cmbEscolarid.setSelectedIndex(-1);
        this.getContentPane().add(cmbEscolarid, null);

        lblImovel = new JLabel("SITUAÇÃO DO IMÓVEL:");
        lblImovel.setBounds(new Rectangle(50, 310, 130, 20));
        this.getContentPane().add(lblImovel, null);
        String imovel[] = {"Próprio", "Cedido", "Com familiares", "Invadido", "Alugado"};
        cmbImovel = new JComboBox(imovel);
        cmbImovel.setBounds(new Rectangle(185, 310, 115, 20));
        cmbImovel.setBackground(Color.white);
        cmbImovel.setSelectedIndex(-1);
        this.getContentPane().add(cmbImovel, null);

        lblComodos = new JLabel("QUANTIDADE DE CÔMODOS:");
        lblComodos.setBounds(new Rectangle(355, 310, 160, 20));
        this.getContentPane().add(lblComodos, null);
        tComodos = new JTextField();
        tComodos.setBounds(new Rectangle(520, 310, 40, 20));
        tComodos.setDocument(new TamanhoMaximo(2));
        tComodos.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                tComodos.setText(tComodos.getText().replaceAll("[^0-9]", ""));
            }
        });
        this.getContentPane().add(tComodos, null);

        lblAluguel = new JLabel("ALUGUEL:   R$");
        lblAluguel.setBounds(new Rectangle(620, 310, 100, 20));
        this.getContentPane().add(lblAluguel, null);
        tAluguel = new TextFieldMoedaReal();
        tAluguel.setBounds(new Rectangle(705, 310, 100, 20));
        this.getContentPane().add(tAluguel, null);

        lblRisco = new JLabel("ÁREA DE RISCO:");
        lblRisco.setBounds(new Rectangle(862, 310, 110, 20));
        this.getContentPane().add(lblRisco, null);
        String risco[] = {"Sim", "Não"};
        cmbRisco = new JComboBox(risco);
        cmbRisco.setBounds(new Rectangle(962, 310, 50, 20));
        cmbRisco.setBackground(Color.white);
        cmbRisco.setSelectedIndex(-1);
        this.getContentPane().add(cmbRisco, null);

        lblEmail = new JLabel("E-MAIL:");
        lblEmail.setBounds(new Rectangle(50, 350, 70, 20));
        this.getContentPane().add(lblEmail, null);
        tEmail = new JTextField();
        tEmail.setBounds(new Rectangle(100, 350, 320, 20));
        tEmail.setDocument(new TamanhoMaximo(40));
        this.getContentPane().add(tEmail, null);
        
        lblDeficiencia = new JLabel("TIPO DE DEFICIÊNCIA ENCONTRADO EM MEMBRO DA FAMÍLIA:");
        lblDeficiencia.setBounds(new Rectangle(480, 350, 350, 20));
        this.getContentPane().add(lblDeficiencia, null);
        String deficiencia[] = {"Nenhuma", "Motora (cadeirante)", "Motora (não cadeirante)", "Visual", "Auditiva", "Mental"};
        cmbDeficiencia = new JComboBox(deficiencia);
        cmbDeficiencia.setBounds(new Rectangle(832, 350, 180, 20));
        cmbDeficiencia.setBackground(Color.white);
        cmbDeficiencia.setSelectedIndex(-1);
        this.getContentPane().add(cmbDeficiencia, null);

        lblObserv = new JLabel("OBSERVAÇÕES:");
        lblObserv.setBounds(new Rectangle(50, 395, 100, 20));
        this.getContentPane().add(lblObserv, null);
        cmpObserv = new JTextArea();
        cmpObserv.setLineWrap(true);
        cmpObserv.setWrapStyleWord(true);
        cmpObserv.setDocument(new TamanhoMaximo(1200));
        scrObserv = new JScrollPane(cmpObserv);
        scrObserv.setBounds(new Rectangle(150, 380, 863, 50));
        scrObserv.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        this.getContentPane().add(scrObserv, null);
        
        lblDependentes = new JLabel("COMPOSIÇÃO FAMILIAR: *");
        lblDependentes.setBounds(new Rectangle(460, 430, 150, 20));
        this.getContentPane().add(lblDependentes, null);
        String[] coluna = {"NOME", "IDADE", "PARENTESCO", "DOCUMENTO", "OCUPAÇÃO", "REMUNERAÇÃO", "OUTRAS RENDAS"};
        String[][] dados = new String[10][7];
        dmParentesco = new DefaultTableModel(dados, coluna);
        tblParentesco = new JTable(dmParentesco);
        DefaultTableCellRenderer centralizado = new DefaultTableCellRenderer();
        centralizado.setHorizontalAlignment(SwingConstants.CENTER);
        tNomeTbl = new JTextField();
        tNomeTbl.setDocument(new TamanhoMaximo(50));
        tblParentesco.getColumnModel().getColumn(0).setCellEditor(new DefaultCellEditor(tNomeTbl));
        tblParentesco.getColumnModel().getColumn(0).setMaxWidth(335);
        try {
            tIdadeTbl = new JFormattedTextField(new MaskFormatter("###"));
            tblParentesco.getColumnModel().getColumn(1).setCellEditor(new DefaultCellEditor(tIdadeTbl));
        } catch (Exception e) {
        }
        tblParentesco.getColumnModel().getColumn(1).setMaxWidth(50);
        tblParentesco.getColumnModel().getColumn(1).setCellRenderer(centralizado);
        //COLOCAR COMBO BOX NA TABELA
        String parentesco[] = {"Cônjuge", "Companheiro(a)", "Filho(a)", "Enteado(a)", "Genro/Nora", "Pai/Mãe", "Sogro(a)", "Neto(a)", "Bisneto(a)", "Irmão/Irmã", "Avô/Avó", "Tio(a)", "Primo(a)", "Cunhado(a)", "Outro parentesco", "Agregado(a)", "Pensionista", "Convivente"};
        cmbParentescoTbl = new JComboBox(parentesco);
        tblParentesco.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(cmbParentescoTbl));
        tblParentesco.getColumnModel().getColumn(2).setMaxWidth(100);
        tDocTbl = new JTextField();
        tDocTbl.setDocument(new TamanhoMaximo(15));
        tblParentesco.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(tDocTbl));
        tblParentesco.getColumnModel().getColumn(3).setMaxWidth(100);
        tblParentesco.getColumnModel().getColumn(3).setCellRenderer(centralizado);
        tOcupacaoTbl = new JTextField();
        tOcupacaoTbl.setDocument(new TamanhoMaximo(30));
        tblParentesco.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(tOcupacaoTbl));
        tblParentesco.getColumnModel().getColumn(4).setMaxWidth(155);
        try {
            tRealTbl = new JFormattedTextField(new MaskFormatter("####,00"));
            tblParentesco.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(tRealTbl));
            tblParentesco.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(tRealTbl));
        } catch (Exception e) {
        }
        tblParentesco.getColumnModel().getColumn(5).setMaxWidth(110);
        tblParentesco.getColumnModel().getColumn(5).setCellRenderer(centralizado);
        tblParentesco.getColumnModel().getColumn(6).setMaxWidth(110);
        tblParentesco.getColumnModel().getColumn(6).setCellRenderer(centralizado);
        //VERIFICA QUANDO OCORRE MUDANÇA NA TABELA
        tblParentesco.getModel().addTableModelListener(new TableModelListener() {
            public void tableChanged(TableModelEvent e) {
                somarRenda();
            }
        });
        tblParentesco.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
        scrParentesco = new JScrollPane(tblParentesco);
        scrParentesco.setBounds(new Rectangle(50, 450, 963, 85));
        this.getContentPane().add(scrParentesco, null);

        lblObs = new JLabel("* Obs.: Todos os campos são de preenchimento obrigatório para cadastro do dependente.");
        lblObs.setBounds(new Rectangle(50, 530, 450, 20));
        lblObs.setFont(new java.awt.Font(null, Font.ROMAN_BASELINE, 10));
        this.getContentPane().add(lblObs, null);

        lblRenda = new JLabel("RENDA FAMILIAR:   R$");
        lblRenda.setBounds(new Rectangle(783, 540, 140, 20));
        this.getContentPane().add(lblRenda, null);
        tRenda = new JTextField("0,00");
        tRenda.setBounds(new Rectangle(913, 540, 100, 20));
        tRenda.setEditable(false);
        tRenda.setBackground(Color.blue);
        tRenda.setForeground(Color.white);
        this.getContentPane().add(tRenda, null);

        btnCadastrar = new JButton("Cadastrar", new ImageIcon("imagem\\confirmar.png"));
        btnCadastrar.setBounds(new Rectangle(410, 555, 120, 30));
        btnCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                String cpf = tCpf.getText();
                if (!cpf.equals("   .   .   -  ") && validaCPF(cpf.replace(".", "").replace("-", "")) == true) {
                    if (validaData(tDtNasc.getText()) == true) {
                        cadastrar();
                    } else {
                        JOptionPane.showMessageDialog(null, "Data inválida!\nCorrija a data e tente novamente.", "M.C.M.V", JOptionPane.WARNING_MESSAGE);
                        lblDtNasc.setForeground(Color.red);
                    }
                } else {
                    JOptionPane.showMessageDialog(null, "CPF inválido ou inexistente!\nCorrija o CPF e tente novamente.", "M.C.M.V", JOptionPane.WARNING_MESSAGE);
                    lblCpf.setForeground(Color.red);
                }
            }
        });
        this.getContentPane().add(btnCadastrar, null);
        this.getRootPane().setDefaultButton(btnCadastrar);

        btnCancelar = new JButton("Cancelar", new ImageIcon("imagem\\cancelar.png"));
        btnCancelar.setBounds(new Rectangle(540, 555, 120, 30));
        btnCancelar.addActionListener(new ActionListener() {
            public void actionPerformed (ActionEvent e) {
                limparCampos();
                doDefaultCloseAction();
            }
        });
        this.getContentPane().add(btnCancelar, null);

        Container container = getContentPane();
        container.add(panel);
    }

    public void cadastrar() {
        String nome = tNome.getText();
        int sexo = cmbSexo.getSelectedIndex();
        String estCivil = "";
        String endereco = tEndereco.getText();
        String bairro = "";
        int zona = cmbZona.getSelectedIndex();
        String naturalidade = tNatural.getText();
        String ocupacao = tOcupacao.getText();
        int cadUnico = cmbCadUnico.getSelectedIndex();
        int blFam = cmbBlFam.getSelectedIndex();
        int bpc = cmbBpc.getSelectedIndex();
        String escolaridade = "";
        String imovel = "";
        String deficiencia = "";
        int risco = cmbRisco.getSelectedIndex();
        try {
            estCivil = cmbEstCivil.getSelectedItem().toString();
            bairro = cmbBairro.getSelectedItem().toString();
            escolaridade = cmbEscolarid.getSelectedItem().toString();
            imovel = cmbImovel.getSelectedItem().toString();
            deficiencia = cmbDeficiencia.getSelectedItem().toString();
        } catch (Exception e) {
        }
        
        //VERIFICAÇÃO SE CAMPOS OBRIGATÓRIOS FORAM PREENCHIDOS
        if (nome.equals("") || sexo == -1 || estCivil.equals("") || endereco.equals("") || bairro.equals("") || zona == -1 || naturalidade.equals("") || tTempo.getText().equals("") || ocupacao.equals("") || cadUnico == -1 || bpc == -1 || blFam == -1 || escolaridade.equals("") || imovel.equals("") || tComodos.getText().equals("") || risco == -1 || deficiencia.equals("")) {
            JOptionPane.showMessageDialog(null, "Um ou mais campos não foram preenchidos!", "M.C.M.V", JOptionPane.ERROR_MESSAGE);
            lblNome.setForeground(Color.blue);
            lblSexo.setForeground(Color.blue);
            lblEstCivil.setForeground(Color.blue);
            lblEndereco.setForeground(Color.blue);
            lblBairro.setForeground(Color.blue);
            lblZona.setForeground(Color.blue);
            lblNatural.setForeground(Color.blue);
            lblTempo.setForeground(Color.blue);
            lblOcupacao.setForeground(Color.blue);
            lblCadUnico.setForeground(Color.blue);
            lblBlFam.setForeground(Color.blue);
            lblBpc.setForeground(Color.blue);
            lblEscolarid.setForeground(Color.blue);
            lblImovel.setForeground(Color.blue);
            lblComodos.setForeground(Color.blue);
            lblRisco.setForeground(Color.blue);
            lblDeficiencia.setForeground(Color.blue);
        } else {
            String dtNasc = tDtNasc.getText();
            String cpf = tCpf.getText();
            String rg = tRg.getText();
            String telefone = tTelefone.getText();
            int tempo = Integer.parseInt(tTempo.getText());
            String remuneracao = tRemuneracao.getText().replace(".", "").replace(",", "."); //CONVERTENDO VALOR DO TIPO 2.222,22 EM 2222.22
            String otRendas = tOtRendas.getText().replace(".", "").replace(",", ".");
            String nis = tNis.getText();
            int comodos = Integer.parseInt(tComodos.getText());
            String aluguel = tAluguel.getText().replace(".", "").replace(",", ".");
            String email = tEmail.getText();
            String observ = cmpObserv.getText();
            //INSERINDO DADOS NA BASE DE DADOS
            try {
                SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
                Date data = (Date)formato.parse(dtNasc);
                formato = new SimpleDateFormat("yyyy-MM-dd");
                InputStream is = new FileInputStream("conf.inf");
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                ip = br.readLine();
                br.close();
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://"+ip+"/db_mcmv", "user_mcmv", "admin");
                stm = con.createStatement();
                stm.executeUpdate("insert into principal (nome, sexo, est_civil, dt_nasc, cpf, rg, endereco, bairro, zona, telefone, email, naturalidade, tempo, ocupacao, remuneracao, outras_rendas, cadunico, nis, bolsa_familia, bpc, escolaridade, imovel, comodos, aluguel, risco, deficiencia, observ) values ('"+nome+"', '"+sexo+"', '"+estCivil+"', '"+formato.format(data)+"', '"+cpf+"', '"+rg+"', '"+endereco+"', '"+bairro+"', "+zona+", '"+telefone+"', '"+email+"', '"+naturalidade+"', "+tempo+", '"+ocupacao+"', "+remuneracao+", "+otRendas+", "+cadUnico+", '"+nis+"', "+blFam+", "+bpc+", '"+escolaridade+"', '"+imovel+"', "+comodos+", "+aluguel+", "+risco+", '"+deficiencia+"', '"+observ+"')");
                    
                //INSERINDO DADOS DOS DEPENDENTES
                rs = stm.executeQuery("select id from principal where cpf = '"+cpf+"'");
                rs.next();
                String principal = rs.getString("id");
                int num = 0;
                Object nomeDep = "";
                Object idadeDep = "";
                Object parentesco = "";
                Object documento = "";
                Object ocupacaoDep = "";
                Object remuneracaoDep = "";
                Object otRendasDep = "";
                for (int i = 0; i < 10; i++) {
                    nomeDep = dmParentesco.getValueAt(i, 0);
                    idadeDep = dmParentesco.getValueAt(i, 1);
                    parentesco = dmParentesco.getValueAt(i, 2);
                    documento = dmParentesco.getValueAt(i, 3);
                    ocupacaoDep = dmParentesco.getValueAt(i, 4);
                    remuneracaoDep = dmParentesco.getValueAt(i, 5);
                    otRendasDep = dmParentesco.getValueAt(i, 6);
                    try {
                        if (!nomeDep.equals("") && !idadeDep.equals("") && !parentesco.equals("") && !documento.equals("") && !ocupacaoDep.equals("") && !remuneracaoDep.equals("") && !otRendasDep.equals("")) {
                            stm.executeUpdate("insert into dependentes (fk_id, dependente, nome, idade, parentesco, documento, ocupacao, remuneracao, outras_rendas) values ("+principal+", "+num+", '"+nomeDep+"', "+idadeDep+", '"+parentesco+"', '"+documento+"', '"+ocupacaoDep+"', "+remuneracaoDep.toString().replace(" ", "").replace(",", ".")+", "+otRendasDep.toString().replace(" ", "").replace(",", ".")+")");
                            num++;
                        }
                    } catch (Exception e) {
                    }
                }
            
                JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!", "M.C.M.V.", JOptionPane.INFORMATION_MESSAGE);
                new RelatorioPDF(Integer.parseInt(principal));
                limparCampos();
                doDefaultCloseAction();
            } catch(SQLException e) {
                if(e.getErrorCode() == 1062) {
                    //ERRO RETORNADO QUANDO CAMPO COM ATRIBUTO UNIQUE JÁ ESTÁ CADASTRADO
                    JOptionPane.showMessageDialog(null, "CPF já cadastrado!", "M.C.M.V.", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro na inserção no banco de dados!\nEntre em contato com o CPD.", "M.C.M.V.", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public void somarRenda () {
        Double renda = Double.parseDouble(tRemuneracao.getText().replace(".", "").replace(",", ".")) + Double.parseDouble(tOtRendas.getText().replace(".", "").replace(",", "."));
        Object remum, otRenda;
        for (int i = 0; i < 10; i++) {
            try {
                remum = dmParentesco.getValueAt(i, 5).toString().replace(" ", "").replace(",", ".");
            } catch (Exception e) {
                remum = "0";
            }
            try {
                otRenda = dmParentesco.getValueAt(i, 6).toString().replace(" ", "").replace(",", ".");
            } catch (Exception e) {
                otRenda = "0";
            }
            renda = renda + Double.parseDouble(remum.toString()) + Double.parseDouble(otRenda.toString());
        }
        if (renda > 1635) {
            tRenda.setBackground(Color.red);
        } else {
            tRenda.setBackground(Color.blue);
        }
        DecimalFormat df = new DecimalFormat("0.00");
        tRenda.setText(df.format(renda).toString().replace(".", ","));
    }

    public boolean validaCPF(String strCpf) {
        if (strCpf.equals("")) {
            return false;
        }
        int d1, d2;
        int digito1, digito2, resto;
        int digitoCPF;
        String nDigResult;
        d1 = d2 = 0;
        digito1 = digito2 = resto = 0;
        for (int nCount = 1; nCount < strCpf.length() - 1; nCount++) {
            digitoCPF = Integer.valueOf(strCpf.substring(nCount - 1, nCount)).intValue();
            //multiplique a ultima casa por 2 a seguinte por 3 a seguinte por 4 e assim por diante.
            d1 = d1 + (11 - nCount) * digitoCPF;
            //para o segundo digito repita o procedimento incluindo o primeiro digito calculado no passo anterior.
            d2 = d2 + (12 - nCount) * digitoCPF;
        }
        //Primeiro resto da divisão por 11.
        resto = (d1 % 11);
        //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.
        if (resto < 2) {
            digito1 = 0;
        } else {
            digito1 = 11 - resto;
        }
        d2 += 2 * digito1;
        //Segundo resto da divisão por 11.
        resto = (d2 % 11);
        //Se o resultado for 0 ou 1 o digito é 0 caso contrário o digito é 11 menos o resultado anterior.
        if (resto < 2) {
            digito2 = 0;
        } else {
            digito2 = 11 - resto;
        }
        //Digito verificador do CPF que está sendo validado.
        String nDigVerific = strCpf.substring(strCpf.length() - 2, strCpf.length());
        //Concatenando o primeiro resto com o segundo.
        nDigResult = String.valueOf(digito1) + String.valueOf(digito2);
        //comparar o digito verificador do cpf com o primeiro resto + o segundo resto.
        return nDigVerific.equals(nDigResult);
    }

    private boolean validaData(String date) {
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        df.setLenient(false);
        try {
            df.parse(date);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void limparCampos() {
        tNome.setText(null);
        cmbSexo.setSelectedIndex(-1);
        cmbEstCivil.setSelectedIndex(-1);
        tDtNasc.setText(null);
        tCpf.setText(null);
        tRg.setText(null);
        tEndereco.setText(null);
        cmbBairro.setSelectedIndex(-1);
        cmbZona.setSelectedIndex(-1);
        tTelefone.setText(null);
        tNatural.setText(null);
        tTempo.setText(null);
        tOcupacao.setText(null);
        tRemuneracao.setText("0");
        tOtRendas.setText("0");
        cmbCadUnico.setSelectedIndex(-1);
        tNis.setText(null);
        cmbBlFam.setSelectedIndex(-1);
        cmbBpc.setSelectedIndex(-1);
        cmbEscolarid.setSelectedIndex(-1);
        cmbImovel.setSelectedIndex(-1);
        tComodos.setText(null);
        tAluguel.setText("0");
        cmbRisco.setSelectedIndex(-1);
        tEmail.setText(null);
        cmpObserv.setText(null);
        for (int i = 0; i < 10; i++) {
            dmParentesco.setValueAt(null, i, 0);
            dmParentesco.setValueAt(null, i, 1);
            dmParentesco.setValueAt(null, i, 2);
            dmParentesco.setValueAt(null, i, 3);
            dmParentesco.setValueAt(null, i, 4);
            dmParentesco.setValueAt(null, i, 5);
            dmParentesco.setValueAt(null, i, 6);
        }
        lblNome.setForeground(Color.black);
        lblSexo.setForeground(Color.black);
        lblEstCivil.setForeground(Color.black);
        lblDtNasc.setForeground(Color.black);
        lblEndereco.setForeground(Color.black);
        lblBairro.setForeground(Color.black);
        lblZona.setForeground(Color.black);
        lblNatural.setForeground(Color.black);
        lblTempo.setForeground(Color.black);
        lblOcupacao.setForeground(Color.black);
        lblCadUnico.setForeground(Color.black);
        lblBlFam.setForeground(Color.black);
        lblBpc.setForeground(Color.black);
        lblEscolarid.setForeground(Color.black);
        lblImovel.setForeground(Color.black);
        lblComodos.setForeground(Color.black);
        lblRisco.setForeground(Color.black);
        lblDeficiencia.setForeground(Color.black);
        lblCpf.setForeground(Color.black);
        doDefaultCloseAction();
    }
}