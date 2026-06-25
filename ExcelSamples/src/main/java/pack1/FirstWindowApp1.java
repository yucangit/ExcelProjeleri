package pack1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.border.EtchedBorder;
import java.awt.Font;

public class FirstWindowApp1 extends JFrame implements ActionListener 
{
	private JPanel contentPane;
	private JButton btnNihaiDosyaOlustur;
	private JLabel lblDoldurulacakDosya_1;
	private JLabel lblVerilerinBulunduguDosya_1;
	private JButton btnDosyaSec3;
	private JButton btnDosyaSec4;
	private JLabel lblSecilenDosya1Path3;
	private JLabel lblSecilenDosya2Path4;
	private JPanel panel3;
	private JPanel panel2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FirstWindowApp1 frame = new FirstWindowApp1();
					//frame.getContentPane().setLayout();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FirstWindowApp1() 
	{
		this.setTitle("EuroStat Verilerinin Oluşturulması");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 854, 580);
		
		contentPane = new JPanel();		
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		JPanel panel1 = new JPanel();
		panel1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Girdi Dosyalar\u0131", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel1.setBounds(10, 28, 793, 112);
		contentPane.add(panel1);
		panel1.setLayout(null);
		
		lblDoldurulacakDosya_1 = new JLabel("Doldurulacak Dosya :");
		lblDoldurulacakDosya_1.setBounds(10, 27, 160, 19);
		panel1.add(lblDoldurulacakDosya_1);
		
		lblVerilerinBulunduguDosya_1 = new JLabel("Verilerin Bulunduğu Dosya :");
		lblVerilerinBulunduguDosya_1.setBounds(10, 59, 160, 23);
		panel1.add(lblVerilerinBulunduguDosya_1);
		
		btnDosyaSec3 = new JButton("Dosya Seç ...");
		btnDosyaSec3.setBounds(180, 25, 116, 23);
		btnDosyaSec3.addActionListener(this);
		panel1.add(btnDosyaSec3);
		
		btnDosyaSec4 = new JButton("Dosya Seç ...");
		btnDosyaSec4.setBounds(180, 59, 116, 23);
		btnDosyaSec4.addActionListener(this);
		panel1.add(btnDosyaSec4);
		
		lblSecilenDosya1Path3 = new JLabel("Seçilen Dosya1 Path : ");
		lblSecilenDosya1Path3.setBounds(306, 29, 411, 14);
		panel1.add(lblSecilenDosya1Path3);
		
		lblSecilenDosya2Path4 = new JLabel("Seçilen Dosya2 Path : ");
		lblSecilenDosya2Path4.setFont(new Font("Dialog", Font.PLAIN, 10));
		lblSecilenDosya2Path4.setBounds(306, 63, 450, 14);
		panel1.add(lblSecilenDosya2Path4);
		
		panel3 = new JPanel();
		panel3.setLayout(null);
		panel3.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Kontrol \u0130\u015Flemleri", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel3.setBounds(10, 151, 793, 112);
		contentPane.add(panel3);
		
		panel2 = new JPanel();
		panel2.setLayout(null);
		panel2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Nihai Verilerin Olu\u015Fturulmas\u0131", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel2.setBounds(10, 278, 793, 112);
		contentPane.add(panel2);
		
		btnNihaiDosyaOlustur = new JButton("Nihai Dosyayı oluştur");
		btnNihaiDosyaOlustur.setBounds(10, 25, 177, 38);
		panel2.add(btnNihaiDosyaOlustur);
		btnNihaiDosyaOlustur.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==btnDosyaSec3) 
		{
			JFileChooser chooser= new JFileChooser();
			chooser.setDialogTitle("Dosya1'yi seçiniz");

			int choice = chooser.showOpenDialog(contentPane);

			if (choice != JFileChooser.APPROVE_OPTION) return;

			File chosenFile = chooser.getSelectedFile();
			lblSecilenDosya1Path3.setText(chosenFile.getAbsolutePath());					
		}
		if(e.getSource()==btnDosyaSec4) 
		{
			JFileChooser chooser= new JFileChooser();
			chooser.setDialogTitle("Dosya2'yi seçiniz");

			int choice = chooser.showOpenDialog(contentPane);

			if (choice != JFileChooser.APPROVE_OPTION) return;

			File chosenFile = chooser.getSelectedFile();
			lblSecilenDosya2Path4.setText(chosenFile.getAbsolutePath());					
		}
		
		
		if(e.getSource()==btnNihaiDosyaOlustur) 
		{
			//Bu kısımda ilgili dosya oluşturulacak.
			JFileChooser chooser= new JFileChooser();
			chooser.setDialogTitle("Dosya2'yi seçiniz");

			int choice = chooser.showOpenDialog(contentPane);

			if (choice != JFileChooser.APPROVE_OPTION) return;

			//File chosenFile = chooser.getSelectedFile();
			//lblSecilenDosya2Path.setText(chosenFile.getAbsolutePath());
			
			
		}
	}
}
