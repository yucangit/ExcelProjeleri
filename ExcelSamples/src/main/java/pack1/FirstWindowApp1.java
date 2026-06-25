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
import javax.swing.JTextPane;
import javax.swing.JTextArea;

public class FirstWindowApp1 extends JFrame implements ActionListener 
{
	private JPanel contentPane;
	private JButton btnNihaiDosyaOlustur;
	private JLabel lblDoldurulacakDosya1;
	private JLabel lblVerilerinBulunduguDosya1;
	private JButton btnDosyaSec1;
	private JButton btnDosyaSec2;
	private JLabel lblSecilenDosya1Path;
	private JLabel lblSecilenDosya2Path;
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	

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
		setBounds(100, 100, 836, 531);
		
		contentPane = new JPanel();		
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		panel1 = new JPanel();
		panel1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Girdi Dosyalar\u0131n\u0131n Se\u00E7ilmesi", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel1.setBounds(10, 28, 793, 99);
		contentPane.add(panel1);
		panel1.setLayout(null);
		
		lblDoldurulacakDosya1 = new JLabel("Doldurulacak Dosya :");
		lblDoldurulacakDosya1.setBounds(10, 27, 160, 19);
		panel1.add(lblDoldurulacakDosya1);
		
		lblVerilerinBulunduguDosya1 = new JLabel("Verilerin Bulunduğu Dosya :");
		lblVerilerinBulunduguDosya1.setBounds(10, 59, 160, 23);
		panel1.add(lblVerilerinBulunduguDosya1);
		
		btnDosyaSec1 = new JButton("Dosya Seç ...");
		btnDosyaSec1.setBounds(180, 25, 116, 23);
		btnDosyaSec1.addActionListener(this);
		panel1.add(btnDosyaSec1);
		
		btnDosyaSec2 = new JButton("Dosya Seç ...");
		btnDosyaSec2.setBounds(180, 59, 116, 23);
		btnDosyaSec2.addActionListener(this);
		panel1.add(btnDosyaSec2);
		
		lblSecilenDosya1Path = new JLabel("Seçilen Dosya1 Path : ");
		lblSecilenDosya1Path.setBounds(306, 29, 411, 14);
		panel1.add(lblSecilenDosya1Path);
		
		lblSecilenDosya2Path = new JLabel("Seçilen Dosya2 Path : ");
		lblSecilenDosya2Path.setFont(new Font("Dialog", Font.PLAIN, 10));
		lblSecilenDosya2Path.setBounds(306, 63, 450, 14);
		panel1.add(lblSecilenDosya2Path);
		
		panel3 = new JPanel();
		panel3.setLayout(null);
		panel3.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Kontrol \u0130\u015Flemleri", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel3.setBounds(10, 138, 793, 145);
		contentPane.add(panel3);
		
		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setBounds(179, 29, 268, 105);
		panel3.add(textArea);
		
		JButton btnKontrolislemleriniYap = new JButton("Kontrol İşlemlerini Yap");
		btnKontrolislemleriniYap.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		btnKontrolislemleriniYap.setBounds(10, 38, 145, 63);
		panel3.add(btnKontrolislemleriniYap);
		
		JLabel lblYaplanKontroller = new JLabel("Yapılan Kontroller : ");
		lblYaplanKontroller.setBounds(179, 11, 109, 14);
		panel3.add(lblYaplanKontroller);
		
		panel2 = new JPanel();
		panel2.setLayout(null);
		panel2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Nihai Verilerin Olu\u015Fturulmas\u0131", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel2.setBounds(10, 294, 793, 174);
		contentPane.add(panel2);
		
		btnNihaiDosyaOlustur = new JButton("Nihai Dosyayı oluştur");
		btnNihaiDosyaOlustur.setBounds(10, 40, 139, 67);
		panel2.add(btnNihaiDosyaOlustur);
		
		JTextArea textArea_1 = new JTextArea();
		textArea_1.setBounds(182, 26, 268, 105);
		panel2.add(textArea_1);
		
		JLabel lblIlemSonucuhatalar = new JLabel("İşlem Sonucu(Hatalar) : ");
		lblIlemSonucuhatalar.setBounds(182, 11, 150, 14);
		panel2.add(lblIlemSonucuhatalar);
		btnNihaiDosyaOlustur.addActionListener(this);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==btnDosyaSec1) 
		{
			JFileChooser chooser= new JFileChooser();
			chooser.setDialogTitle("Dosya1'yi seçiniz");

			int choice = chooser.showOpenDialog(contentPane);

			if (choice != JFileChooser.APPROVE_OPTION) return;

			File chosenFile = chooser.getSelectedFile();
			lblSecilenDosya1Path.setText(chosenFile.getAbsolutePath());					
		}
		if(e.getSource()==btnDosyaSec2) 
		{
			JFileChooser chooser= new JFileChooser();
			chooser.setDialogTitle("Dosya2'yi seçiniz");

			int choice = chooser.showOpenDialog(contentPane);

			if (choice != JFileChooser.APPROVE_OPTION) return;

			File chosenFile = chooser.getSelectedFile();
			lblSecilenDosya2Path.setText(chosenFile.getAbsolutePath());					
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
