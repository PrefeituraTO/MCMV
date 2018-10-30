import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import java.sql.*;
import java.io.*;

public class Principal extends JFrame implements ActionListener {
    public static void main(String args[]) {
        Principal p = new Principal();
	p.setVisible(true);
    }

    private JButton btnCadastrar;
    private JButton btnConsultar;
    private JButton btnSair;
    private JLabel lblLinha;
    private JDesktopPane desktopPane;
    private InternalFrameCadastrar frameCadastrar;
    private InternalFrameConsultar frameConsultar;
        
    public Principal() {
        desktopPane = new JDesktopPane();
        this.setContentPane(desktopPane);

        ImageIcon imageicon = new ImageIcon("imagem\\logo.png");
        Image icon = imageicon.getImage();
        this.setIconImage(icon);

        this.setLayout(null);
        this.getContentPane().setBackground(new Color(0, 130, 180));
        this.setSize(1095, 735);
        this.setLocationRelativeTo(null);
        this.setTitle("Sistema de Cadastramento Geral da Habitação");

        ImageIcon cadastrar = new ImageIcon("imagem\\cadastrar.png");
        cadastrar.setImage(cadastrar.getImage().getScaledInstance(30, 30, 100));
        btnCadastrar = new JButton("CADASTRAR", cadastrar);
        btnCadastrar.setBounds(new Rectangle(290, 10, 160, 45));
        btnCadastrar.addActionListener(this);
        this.getContentPane().add(btnCadastrar, null);

        ImageIcon consultar = new ImageIcon("imagem\\consultar.png");
        consultar.setImage(consultar.getImage().getScaledInstance(30, 30, 100));
        btnConsultar = new JButton("CONSULTAR", consultar);
        btnConsultar.setBounds(new Rectangle(460, 10, 160, 45));
        btnConsultar.addActionListener(this);
        this.getContentPane().add(btnConsultar, null);

        ImageIcon sair = new ImageIcon("imagem\\sair.png");
        sair.setImage(sair.getImage().getScaledInstance(30, 30, 100));
        btnSair = new JButton("SAIR", sair);
        btnSair.setBounds(new Rectangle(630, 10, 160, 45));
        btnSair.addActionListener(this);
        this.getContentPane().add(btnSair, null);

        lblLinha = new JLabel("_____________________________________________________________________________________________");
        lblLinha.setBounds(new Rectangle(0, 40, 1500, 30));
        lblLinha.setFont(new java.awt.Font(null, Font.BOLD, 25));
        this.getContentPane().add(lblLinha, null);

        //TECLA DE ATALHO F1 PARA AJUDA
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F1"), "AJUDA");
        getRootPane().getActionMap().put("AJUDA", new AbstractAction("AJUDA") {
            public void actionPerformed(ActionEvent evt) {
                Ajuda a = new Ajuda();
                a.setVisible(true);
            }
        });

        //TECLA DE ATALHO F8 PARA ALTERAR IP DO SERVIDOR
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F8"), "IP-SERVIDOR");
        getRootPane().getActionMap().put("IP-SERVIDOR", new AbstractAction("IP-SERVIDOR") {
            public void actionPerformed(ActionEvent evt) {
                IPServidor i = new IPServidor();
                i.setVisible(true);
            }
        });

        //TECLA DE ATALHO F12 PARA ENVIAR DADOS DO BANCO DE DADOS PARA O EXCEL
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("F12"), "EXCEL");
        getRootPane().getActionMap().put("EXCEL", new AbstractAction("EXCEL") {
            public void actionPerformed(ActionEvent evt) {
                excel();
            }
        });

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
	    }
	});
    }

    public void excel() {
        try {
            InputStream is = new FileInputStream("conf.inf");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String ip = br.readLine();
            br.close();
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://"+ip+"/db_mcmv", "user_mcmv", "admin");
            Statement stm = con.createStatement();
            Statement stm2 = con.createStatement();
            //GRAVANDO DADOS DA TABELA PRINCIPAL PARA O EXCEL
            ResultSet rs = stm.executeQuery("select id, nome, sexo, est_civil, dt_nasc, cpf, rg, endereco, bairro, zona, telefone, email, naturalidade, tempo, ocupacao, remuneracao, outras_rendas, cadunico, nis, bolsa_familia, bpc, escolaridade, imovel, comodos, aluguel, risco, deficiencia from principal");
            ResultSet rs2 = stm2.executeQuery("select fk_id, count(fk_id) as quant, sum(remuneracao) as remun, sum(outras_rendas) as rendas from dependentes group by fk_id");
            rs.next();
            StringBuffer sb = new StringBuffer("");
            ResultSetMetaData rsMeta = rs.getMetaData();
            for (int i = 1; i <= rsMeta.getColumnCount(); i++) {
                sb.append(rsMeta.getColumnLabel(i)+"\t");
            }
            sb.append("quant_depend\t");
            sb.append("renda_familiar\n");
            rs.beforeFirst();
            rs2.next();
            while(rs.next()) {
                for(int i = 1; i <= rsMeta.getColumnCount(); i++) {
                    sb.append(rs.getString(i)+"\t");
		}
                if (rs.getInt("id") == rs2.getInt("fk_id")) {
                    sb.append(rs2.getInt("quant")+"\t");
                    sb.append((Math.round((rs.getDouble("remuneracao")+rs.getDouble("outras_rendas")+rs2.getDouble("remun")+rs2.getDouble("rendas"))*100.0)/100.0)+"\n");
                    rs2.next();
                } else {
                    sb.append("0\t");
                    sb.append((Math.round((rs.getDouble("remuneracao")+rs.getDouble("outras_rendas"))*100.0)/100.0)+"\n");
                }
            }
            FileWriter excelFile = new FileWriter("Dados\\M.C.M.V.Principal.xls");
            excelFile.write(new String(sb));
            excelFile.close();
            //GRAVANDO DADOS DA TABELA DEPENDENTES PARA O EXCEL
            rs = stm.executeQuery("select * from dependentes");
            rs.next();
            sb = new StringBuffer("");
            rsMeta = rs.getMetaData();
            for (int i = 1; i <= rsMeta.getColumnCount(); i++) {
                sb.append(rsMeta.getColumnLabel(i)+"\t");
            }
            sb.append("\n");
            rs.beforeFirst();
            while(rs.next()) {
                for(int i = 1; i <= rsMeta.getColumnCount(); i++) {
                    sb.append(rs.getString(i)+"\t");
		}
		sb.append("\n");
            }
            excelFile = new FileWriter("Dados\\M.C.M.V.Dependentes.xls");
            excelFile.write(new String(sb));
            excelFile.close();
            JOptionPane.showMessageDialog(null, "Dados gerados no Excel com sucesso!", "M.C.M.V", JOptionPane.INFORMATION_MESSAGE);
            Runtime.getRuntime().exec("cmd.exe /C start Dados");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível gerar planilhas no Excel!\nEntre em contato com o CPD.", "M.C.M.V", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void actionPerformed(ActionEvent ev) {
        if(ev.getSource() == btnCadastrar) {
            if (frameCadastrar == null) {
                frameCadastrar = new InternalFrameCadastrar();
                frameCadastrar.setVisible(true);
                desktopPane.add(frameCadastrar);
            } else if (!frameCadastrar.isVisible()) {
                frameCadastrar = new InternalFrameCadastrar();
                frameCadastrar.setVisible(true);
                desktopPane.add(frameCadastrar);
            }
            frameCadastrar.moveToFront();
        }
        if(ev.getSource() == btnConsultar) {
            if (frameConsultar == null) {
                frameConsultar = new InternalFrameConsultar();
                frameConsultar.setVisible(true);
                desktopPane.add(frameConsultar);
            } else if (!frameConsultar.isVisible()) {
                frameConsultar = new InternalFrameConsultar();
                frameConsultar.setVisible(true);
                desktopPane.add(frameConsultar);
            }
            frameConsultar.moveToFront();
        }
        if(ev.getSource() == btnSair) {
            System.exit(0);
        }
    }
}

class TamanhoMaximo extends PlainDocument {
    int tamMax;
    public TamanhoMaximo(int tam) {
        tamMax = tam;
    }
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException {
        if((getLength()+str.length()) <= tamMax) {
            super.insertString(offs, str, a);
        }
    }
}