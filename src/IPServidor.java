import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class IPServidor extends JFrame implements ActionListener {
    private JLabel lblIp;
    private JTextField tIp;
    private JButton btnOk;
    private JButton btnCancelar;
    private String ip;
    
    public IPServidor () {
        this.getContentPane().setLayout(null);
        this.setSize(240, 130);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("IP Servidor M.C.M.V.");

        try {
            InputStream is = new FileInputStream("conf.inf");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            ip = br.readLine();
            br.close();
        } catch (IOException e) {
        }

        lblIp = new JLabel("IP Servidor:");
        lblIp.setBounds(new Rectangle(20, 10, 80, 40));
        this.getContentPane().add(lblIp, null);

        tIp=new JTextField(ip);
        tIp.setBounds(new Rectangle(90, 20, 125, 22));
        this.getContentPane().add(tIp, null);

        btnOk = new JButton("Ok");
        btnOk.setBounds(new Rectangle(25, 60, 90, 30));
        btnOk.addActionListener(this);
        this.getContentPane().add(btnOk, null);
        this.getRootPane().setDefaultButton(btnOk);

        btnCancelar = new JButton("Cancelar");
        btnCancelar.setBounds(new Rectangle(120, 60, 90, 30));
        btnCancelar.addActionListener(this);
        this.getContentPane().add(btnCancelar, null);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setVisible(false);
	    }
	});
    }

    public void actionPerformed(ActionEvent ev) {
        if(ev.getSource() == btnOk) {
            ip = tIp.getText();
            if(!ip.equals("")) {
                try {
                    OutputStream os = new FileOutputStream("conf.inf");
                    OutputStreamWriter osw = new OutputStreamWriter(os);
                    BufferedWriter bw = new BufferedWriter(osw);
                    bw.write(ip);
                    bw.close();
                } catch (IOException e) {
                }
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Digite o IP do Servidor!");
            }
	}
        if(ev.getSource() == btnCancelar) {
            setVisible(false);
        }
    }
}