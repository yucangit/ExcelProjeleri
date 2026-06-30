package pack1;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JFileChooser;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.awt.event.ActionEvent;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.border.EtchedBorder;
import java.awt.Font;
import javax.swing.JTextPane;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

public class FirstWindowApp1 extends JFrame implements ActionListener 
{
	private JPanel contentPane;
	private JButton btnDosyaSec1;
	private JButton btnDosyaSec2;
	private JButton btnKontrolislemleriniYap;
	private JButton btnNihaiDosyaOlustur;
	
	private JLabel lblDoldurulacakDosya1;
	private JLabel lblVerilerinBulunduguDosya1;	
	private JLabel lblSecilenDosya1Path;
	private JLabel lblSecilenDosya2Path;
	private JLabel lblIlemSonucuhatalar;
	private JLabel lblDebug;                      //Debug işlemleri için konulmuştu, kaldırılabilir  
	
	private JPanel panel1;
	private JPanel panel2;
	private JPanel panel3;
	
	private JTextArea txtrBuKsmHenz;
	private JTextArea textArea1;
		
	File chosenFile1, chosenFile2;
    // 2. Create a filter targeting Excel extensions (.xlsx and .xls)
    FileNameExtensionFilter excelFilter;
	
    private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
    
    String doldurulacakDosyaPath;
	String doldurulacakDosyaKopyaPath;
	String veriDosyasiPath;
	String defaultFileDialogDirectory;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) 
	{
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try 
				{
					FirstWindowApp1 frame = new FirstWindowApp1();
					//frame.getContentPane().setLayout();
					frame.setVisible(true);
				} 
				catch (Exception e) 
				{
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
		setBounds(100, 100, 943, 531);
						
		contentPane = new JPanel();		
		contentPane.setBorder(new LineBorder(new Color(0, 0, 0)));		
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		
		panel1 = new JPanel();
		panel1.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Girdi Dosyalar\u0131n\u0131n Se\u00E7ilmesi", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel1.setBounds(10, 28, 652, 99);
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
		panel3.setBounds(10, 138, 652, 168);
		contentPane.add(panel3);
		
		btnKontrolislemleriniYap = new JButton("Kontrol İşlemlerini Yap");		
		btnKontrolislemleriniYap.setBounds(10, 53, 160, 60);
		btnKontrolislemleriniYap.addActionListener(this);
		panel3.add(btnKontrolislemleriniYap);
		
		JLabel lblYaplanKontroller = new JLabel("Yapılan Kontroller : ");
		lblYaplanKontroller.setBounds(180, 11, 109, 14);
		panel3.add(lblYaplanKontroller);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(180, 27, 385, 130);
		panel3.add(scrollPane_1);
		
		txtrBuKsmHenz = new JTextArea();
		scrollPane_1.setViewportView(txtrBuKsmHenz);
		txtrBuKsmHenz.setFont(new Font("Monospaced", Font.PLAIN, 11));
		txtrBuKsmHenz.setEditable(false);
		
		panel2 = new JPanel();
		panel2.setLayout(null);
		panel2.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(255, 255, 255), new Color(160, 160, 160)), "Nihai Verilerin Olu\u015Fturulmas\u0131", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel2.setBounds(10, 317, 652, 164);
		contentPane.add(panel2);
		
		btnNihaiDosyaOlustur = new JButton("Nihai Dosyayı oluştur");
		btnNihaiDosyaOlustur.setBounds(10, 53, 160, 60);
		btnNihaiDosyaOlustur.addActionListener(this);
		panel2.add(btnNihaiDosyaOlustur);
		
		lblIlemSonucuhatalar = new JLabel("İşlem Sonucu(Hatalar) : ");
		lblIlemSonucuhatalar.setBounds(180, 11, 150, 14);
		panel2.add(lblIlemSonucuhatalar);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(180, 29, 388, 124);
		panel2.add(scrollPane);
		
		textArea1 = new JTextArea();
		scrollPane.setViewportView(textArea1);
		
		lblDebug = new JLabel("Seçilen Dosya1 Path : ");
		lblDebug.setBounds(10, 11, 842, 14);
		contentPane.add(lblDebug);
		
		String protocol = getProtokol();
		
		try 
		{
			if( "jar".equals(protocol) || "rsrc".equals(protocol) ) 
			{	
				String sourceFilePath = "/resources/Dosyalar/Parametreler.xlsx";				
				Path path = Paths.get(sourceFilePath);
				
				if (!Files.exists(path)) 
				{ 				
					Utils.copyFileFromJar(sourceFilePath, "D:\\Parametreler.xlsx");
				}
				
				sourceFilePath = "/resources/Dosyalar/log.txt";				
				path = Paths.get(sourceFilePath);
				if (!Files.exists(path)) 
				{ 				
					Utils.copyFileFromJar(sourceFilePath, "D:\\log.txt");
				}											
			}
		} 
		catch (Exception e) 
		{
				e.printStackTrace();
		}
				
		lblDebug.setText("Protokol(IDE-file or JAR) : "+ getProtokol() + ", Path : "+  new File(".").getAbsolutePath() );
		
		
        // 2. Create a filter targeting Excel extensions (.xlsx and .xls)
        //excelFilter = new FileNameExtensionFilter("Excel Files (*.xlsx, *.xls)", "xlsx", "xls", "xlsm");
		excelFilter = new FileNameExtensionFilter("Excel Files (*.xlsx, *.xlsm)", "xlsx", "xlsm");            //Mevcut durumda .xls uzantılı dosyalarda hata oluşuyor.(29.06.2026)  				
	}		
	
	public String getProtokol() 
	{
		//Uygulama jar içinden caçlıştırılıyorsa "jar"
		//         IDE içinden çalıştırılıyorsa da "file" ciktisi oluşuyor.
		
        URL classUrl = FirstWindowApp1.class.getResource("FirstWindowApp1.class");
        String protocol = classUrl.getProtocol();

        if ("jar".equals(protocol) || "rsrc".equals(protocol)) 
        {
            System.out.println("JAR içinden çalıştırıldı.");
        } 
        else if ("file".equals(protocol)) 
        {
            System.out.println("Eclipse / Klasör yapısından çalıştırıldı.");
        }
        
        return protocol;
    }
	
	public String parametersFilePathGetir() 
	{
		//Uygulama jar içinden caçlıştırılıyorsa "jar"
				//         IDE içinden çalıştırılıyorsa da "file" ciktisi oluşuyor. 
		
		String parametersFilePath = ".\\src\\main\\resources\\Dosyalar\\Parametreler.xlsx";
		
		String protocol = getProtokol();
        
        if ("jar".equals(protocol)) 
        {
            System.out.println("Uygulama bir JAR dosyası ile çalışıyor!");
            parametersFilePath = "resources/Dosyalar/Parametreler.xlsx";
        } 
        else 
        {
            System.out.println("Uygulama Eclipse veya IDE üzerinden çalışıyor!");
        }
        
       return parametersFilePath;
        		
	}

	public String getFileDialogLastDirPath(int parameterId) 
	{		
    	//Durum : Yapim aşamasında.
    	
    	FileInputStream fis=null;
    	Workbook workbook=null;
    	        
    	//String parametersFilePath = ".\\src\\main\\resources\\Dosyalar\\Parametreler.xlsx";
    	String parametersFilePath = parametersFilePathGetir();
    	
        String path = "";         //defaultFileDialogDirectory       
    	
        try 
        {
        	fis = new FileInputStream(new File(parametersFilePath));
            workbook = new XSSFWorkbook(fis);
            
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();                                                                          
                        
            for (Row row : sheet) 
            {
            	if(row.getRowNum()==0) continue;
            	
            	int id = Integer.parseInt( formatter.formatCellValue(row.getCell(0)).trim() );      //id kolonu
            	path = formatter.formatCellValue(row.getCell(2)).trim();
            	if(id==parameterId ) 
            	{
            		System.out.println("parameterId = " + parameterId + " olan dosya için deafult path : " + path );
            		break;            	            
            	}
            }
            workbook.close();
            fis.close();
        }
        catch (Exception e) 
        {
        	JOptionPane.showMessageDialog(
				    null, 
				    "Default FileDialog path alınırken hata oluştu.\n" + e.getMessage(),       // Message
				    "Hata",                                 // Dialog Title
				    JOptionPane.ERROR_MESSAGE          // Message Type (Icon)
				);		
			
		}
               
        
        return path;
	}	
	
	
	public void setFileDialogLastDirPath(int parameterId, String newDirPath) 
	{		
		
		//String parametersFilePath = ".\\src\\main\\resources\\Dosyalar\\Parametreler.xlsx";
    	String parametersFilePath = parametersFilePathGetir();
    	
		try
		{
			FileInputStream fis = new FileInputStream(new File(parametersFilePath));
	    	Workbook workbook = new XSSFWorkbook(fis);
	    	fis.close();
	    	
	    	DataFormatter formatter = new DataFormatter();	    	
	    	FileOutputStream fos = new FileOutputStream(new File(parametersFilePath));        			            
	
	        // Get the 9th worksheet   --SheetName = "Table_1 (COPY-PASTING)"
	        Sheet sheet = workbook.getSheetAt(0);   
	        
	        for(Row row : sheet) 
	        {
	        	if(row.getRowNum()==0) continue;
	        	
	        	int id = Integer.parseInt( formatter.formatCellValue(row.getCell(0)).trim() );      //id kolonu
	        	
	        	if(id==parameterId ) 
	        	{
	        		Cell cell = row.getCell(2);
	                cell.setCellValue(newDirPath);        		
	        		break;
	        	}	        	
	        }        	        
	        
	        workbook.write(fos);	        	        
	        
	        System.out.println("newDirPath : " + newDirPath);
	        
	        workbook.close();
	        fos.close();
		}
        catch (Exception e) 
        {
        	JOptionPane.showMessageDialog(
				    null, 
				    "Parametre dosyasında Default FileDialog path güncellenirken hata oluştu.\n"+ e.getMessage(),       // Message
				    "Hata",                                 // Dialog Title
				    JOptionPane.ERROR_MESSAGE          // Message Type (Icon)
				);		
			
		}
                
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if(e.getSource()==btnDosyaSec1) 
		{
			System.out.println("Uygulama caliştirma ortamı : " +  getProtokol());
			
			lblDebug.setText("Protokol(IDE-file or JAR) : "+ getProtokol() + ", Path : "+  getFileDialogLastDirPath(1) );
			
			String file1LastPath = getFileDialogLastDirPath(1).replace("\\", "\\\\");
			
			//System.out.println(file1DefaultPath);
			
			JFileChooser chooser= new JFileChooser();
			chooser.setDialogTitle("Doldurulacak dosyayı seçiniz");	        			
	        chooser.setFileFilter(excelFilter);                              // 3. Apply the filter to the chooser
			
	        //lblDebug.setText( new File(".").getAbsolutePath() );
			//chooser.setCurrentDirectory(new File("."));                       //jar dosyasının çalıştırıldığı dizini default olarak açar.
	        chooser.setCurrentDirectory(new File(file1LastPath));               //jar dosyasının çalıştırıldığı dizini default olarak açar.

			int choice = chooser.showOpenDialog(contentPane);

			if (choice != JFileChooser.APPROVE_OPTION) return;

			chosenFile1 = chooser.getSelectedFile();
			String newFileDirectory = chosenFile1.getParentFile().getAbsolutePath();
			setFileDialogLastDirPath(1,newFileDirectory);
			
			//chooser.getCurrentDirectory();
			
			doldurulacakDosyaPath = chosenFile1.getAbsolutePath();
			lblSecilenDosya1Path.setText(chosenFile1.getAbsolutePath());		
			
			
			//getFileDialogLastDirPath();
		}
		
		else if(e.getSource()==btnDosyaSec2) 
		{
			
			String file2LastPath = getFileDialogLastDirPath(2).replace("\\", "\\\\");
			
			JFileChooser chooser= new JFileChooser();
			chooser.setDialogTitle("Miktar ve Gizlilik değerlerinin bulunduğu dosyayı seçiniz");
			
			// 3. Apply the filter to the chooser
	        chooser.setFileFilter(excelFilter);
	        chooser.setCurrentDirectory(new File(file2LastPath));               //jar dosyasının çalıştırıldığı dizini default olarak açar.
	        
			chooser.setCurrentDirectory(new File("."));

			int choice = chooser.showOpenDialog(contentPane);

			if (choice != JFileChooser.APPROVE_OPTION) return;

			chosenFile2 = chooser.getSelectedFile();
			veriDosyasiPath = chosenFile2.getAbsolutePath();
			lblSecilenDosya2Path.setText(chosenFile2.getAbsolutePath());					
		}
		
		else if(e.getSource()==btnKontrolislemleriniYap) 
		{			
			
			JOptionPane.showMessageDialog(
				    null, 
				    "Bu işlem adımı henüz yapılmadı.",       // Message
				    "Bilgi",                                 // Dialog Title
				    JOptionPane.INFORMATION_MESSAGE          // Message Type (Icon)
				);					
						    		    					
		}
		
		else if(e.getSource()==btnNihaiDosyaOlustur) 
		{
			Exception e2 = null;
			
			if(doldurulacakDosyaPath==null || doldurulacakDosyaPath.trim().isEmpty() || veriDosyasiPath==null || veriDosyasiPath.trim().isEmpty() ) 
			{
				JOptionPane.showMessageDialog
				(
				    null, 
				    "Doldurulacak dosya ve veri dosyası mutlaka seçilmelidir..", // Message
				    "Error",                                         			// Dialog Title
				    JOptionPane.ERROR_MESSAGE                        			// Message Type (Icon)
				);
				return;
			}
			
			
			
			doldurulacakDosyaKopyaPath = ExcelProcessor.copyAndGetNewFileName(doldurulacakDosyaPath);//, "D:\\KorayBey\\WStatR_TRT_XX_DC2026_v00.m02b.xlsm");
	    	System.out.println("Yeni Dosya adı : " + doldurulacakDosyaKopyaPath);
	    	
	    	textArea1.append("Nihai Dosya adı : " + doldurulacakDosyaKopyaPath + "\n");
	    	
	    	e2 = ExcelProcessor.dosyaDoldur(doldurulacakDosyaKopyaPath, veriDosyasiPath);	    	
	    	
	    	
	    	if(Objects.isNull(e2))
	    		textArea1.append("İşlem başarı ile tamamlandı.\n");
	    	else	    						
	    	{
	    		textArea1.append("Hata oluştu:\n" + e2.getStackTrace());
	    		textArea1.append("Hata oluştu:\n" + e2.getMessage());
	    		
	    		JOptionPane.showMessageDialog
				(
				    null, 
				    "İşlem Sırasında hata oluştu:\n" + e2.getStackTrace() + "\n" + e2.getMessage(), // Message
				    "Error",                                         			// Dialog Title
				    JOptionPane.ERROR_MESSAGE                        			// Message Type (Icon)
				);
				return;
				
	    	}
			
	    	
		}
		
		
		
		
	}
}
