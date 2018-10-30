import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Ajuda extends JFrame implements ActionListener {
    private JLabel lblInfo;
    private JButton btnOk;

    public Ajuda () {
        this.getContentPane().setLayout(null);
        this.setSize(225, 200);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("Ajuda M.C.M.V.");

        lblInfo = new JLabel("<html><center>M.C.M.V. versão: 1.0<br>" +
                "Desenvolvido por: Arthur Laender<br><br>" +
                "F8 - Altera IP do servidor<br>" +
                "F12 - Gera dados em planilha Excel</center></html>");
        lblInfo.setBounds(new Rectangle(10, 10, 200, 120));
        this.getContentPane().add(lblInfo, null);

        btnOk = new JButton("Ok");
        btnOk.setBounds(new Rectangle(65, 130, 90, 30));
        btnOk.addActionListener(this);
        this.getContentPane().add(btnOk, null);
        this.getRootPane().setDefaultButton(btnOk);

        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                setVisible(false);
	    }
	});
    }

    public void actionPerformed(ActionEvent ev) {
        if(ev.getSource() == btnOk) {
            setVisible(false);
        }
    }
}