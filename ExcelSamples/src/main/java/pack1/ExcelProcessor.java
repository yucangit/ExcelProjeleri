package pack1;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;

public class ExcelProcessor 
{
    public static void main(String[] args) 
    {
        String filePath = "D:\\KorayBey\\WStatR_TRT_XX_DC2026_v00.m02b.xlsm";

        try 
        { 
        	// Dynamic creation works for both xlsx and xlsm
        	
        	FileInputStream fis = new FileInputStream(new File(filePath));        		
            Workbook workbook = WorkbookFactory.create(fis);
            fis.close();                                           
            
            FileOutputStream fos = new FileOutputStream(new File(filePath));    //dosyaya veri yazmak için kullanılır.
            
            // Get the 9th worksheet   --SheetName = "Table_1 (COPY-PASTING)"
            Sheet sheet = workbook.getSheetAt(9);   
            
            
            System.out.printf("%-8s   %-10s   %-15s   %-12s   %-10s  %-10s %10s   %-20s       %10s     %-10s \n", "HucreAdresi", "AtikKodu",  "Nitelik", "Yontem", "Renk", "HucreTuru", "RowIndex", "Birlesik", "Miktar", "Gizlilik");
            
            /*
                6 -> "Recovery - energy recovery (R1)"
                11-> "Disposal - incineration (D10)"
                16-> "Recovery - recycling"
                21-> "Recovery - backfilling"                ->miktar verisi hep 0 olacak
                26-> "Recovery - recycling and backfilling"
                31-> "Disposal - landfill (D1, D5, D12)"
                37-> "Disposal - other(D2-D4, D6-D7)"
            */
            int []colIdxList = {6,11,16,21,26,31,37};   //miktar verisinin yazılacağı kolonların excel indexleri (0-based)
                                                        //Gizlilik kolonları iki kolon sonrası olduğu için ayrıca bir dizi oluşturulmadı.
            
            
            // Iterate over all rows
            
            OuterLoop:                         //Bu etiket, nested loop yapılarında iç döngü içinde işlem yapılırken, gerekirse dış döngüden de çıkılması imkanı veriyor.
            for (Row row : sheet) 
            {    
            	            	            	
            	Cell cellMiktar = null;
            	Cell cellGizlilik = null;
            	
            	int rowIdx = row.getRowNum();            	            	
            	
            	for(int colIdx : colIdxList )
            	{
            		cellMiktar = row.getCell(colIdx);
            		cellGizlilik = row.getCell(colIdx+2);
            	            		            	            		            	
	            	String color = getCellColor(cellMiktar);
	            	CellType hucreTuru = cellMiktar.getCellType();            	            	            	            	
	            	
	            	String atikKodu = getCellValue2(sheet, rowIdx, 1);       //W011, ...            	
	            	            	
	            	if( !color.equals("FFFFFFFF") )              //mevcut hücre beyaz değilse bir sonraki satira geçilir.
	            		break;            
	            	
	            	if(atikKodu.equals("TOTAL"))                // Bu değer dosyada son veri satırlarını gösteriyor. Bu satırdan sonrasına bakılmasın. (Dış döngüden de çıkılır.)
	            		break OuterLoop;
	            	
	            	if( hucreTuru == CellType.FORMULA )         //CellType possible values = {NUMERIC, STRING, FORMULA, BLANK, BOOLEAN, ERROR}
	            		break;
	            	
	            	String birlesik_kod = getCellKeyValue(sheet, row, cellMiktar);
	            	
	            	String value = lookupValue("D:\\KorayBey\\YUSUF_SONUC.xlsx", birlesik_kod, 0, 6 );   //Kolon0:"ATIK_KOD_NITELIK_YONTEM", Kolon6 : "MIKTAR_GIZLILIK");
	            	String []arr = value.split(" ");
	            		            	
	            	
	            	int miktar = 0;
	            	String gizlilik="";
	            	if(!arr[0].equals("Value_Not_Found")) 
	            	{
	            		miktar = Integer.parseInt(arr[0]);
		            	gizlilik = arr[1];
	            	}
	            	else 
	            	{
	            		miktar =0;
	            		gizlilik = "";
	            	}
	            	 
	            	
	            	if(!arr[0].equals("Value_Not_Found")) 
	            	{
		            	//int miktar = Integer.parseInt(arr[0]);
		            	//String gizlilik = arr[1];
		            	
		            	System.out.printf("%10d  %10s \n", miktar, gizlilik);
		            	cellMiktar.setCellValue(miktar);
		            	if(!arr[1].equals("1"))
		            		cellGizlilik.setCellValue(gizlilik);		
		            	
		            	//break OuterLoop;
	            	}
	            	else 
	            	{
	            		
	            		System.out.print("\n");
	            	}	            	
	            	
            	}            	            	            	                            	
            }   
            
            
            /*
             	Excel açıldığında formül olan hücreler otomatik görünmüyor.
              	Bu durumu düzeltmek için formül olan her bir hücre için ilgili hücreye gidip önce "F2" sonra "Enter" tuşlarına basmak gerekiyor.
            	Bu extra iş yükünü elimine edebilmek için aşağıdaki kod bloğu kullanılmıştır.             
            */
            
            FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();

            // Tüm formülleri tek seferde hesaplatın
            evaluator.evaluateAll();
            
            //Excel dosyası açıldığında gelmesi istenen tab belirlemek için aşağıdaki iki satır eklendi.
            workbook.setActiveSheet(9);            
            workbook.setSelectedTab(9);
            
            
            workbook.write(fos);
            workbook.close();
            
        } 
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    
    public static String lookupValue(String filePath, String searchKey, int keyColumn, int returnColumn) 
    {
        try 
        {
        	FileInputStream fis = new FileInputStream(new File(filePath));
            Workbook workbook = new XSSFWorkbook(fis);
            
            Sheet sheet = workbook.getSheetAt(0);
            DataFormatter formatter = new DataFormatter();
            FormulaEvaluator objFormulaEval = workbook.getCreationHelper().createFormulaEvaluator();
            
            //System.out.println(formatter.formatCellValue( sheet.getRow(1).getCell(0),objFormulaEval));

            for (Row row : sheet) 
            {
                Cell keyCell = row.getCell(keyColumn);
                String currKey = formatter.formatCellValue(keyCell, objFormulaEval);
                //if(row.getRowNum()==73)
            
                if (keyCell != null && currKey.equals(searchKey)) 
                {
                    Cell returnCell = row.getCell(returnColumn);
                    if (returnCell != null) 
                    {
                    	String currVal = formatter.formatCellValue(returnCell, objFormulaEval);
                        return currVal;
                    }
                }
            }
            workbook.close();
            fis.close();
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        return "Value_Not_Found";
    }
        
    public static String getCellKeyValue(Sheet sheet, Row row, Cell cell) 
    {
    	//returns concatenated properties
    	
    	int rowIdx = cell.getRowIndex();            	
    	
    	String color = getCellColor(cell);
    	
    	CellType hucreTuru = cell.getCellType();            	            	            	            	
    	
    	String atikKodu = getCellValue2(sheet, rowIdx, 1);       //W011, ...            	    	            	    	               	    	    	
    	
    	String nitelik  = getCellValue(row.getCell(5));          //Nitelik(Hazardous, Non-Hazardous, ...
    	String nitelikKodu ="";
    	if(nitelik.trim().equals("Hazardous"))           
    		nitelikKodu = "1";
    	else if(nitelik.trim().equals("Non-hazardous"))  
    		nitelikKodu = "2";    	
    
    	//column Type
    	String yontem = "";  
    	if(cell.getColumnIndex()==6)         yontem = "EKYAKMA";        //"Recovery - energy recovery (R1)"
    	else if(cell.getColumnIndex()==11)   yontem = "YAKMA";          //"Disposal - incineration   (D10)"
    	else if(cell.getColumnIndex()==16)   yontem = "GERI KAZANIM";   //"Recovery - recycling"
    	else if(cell.getColumnIndex()==31)   yontem = "DDEPOLAMA";      //""Disposal - landfill(D1, D5, D12)"
    	else if(cell.getColumnIndex()==37)   yontem = "SULU_ORTAM";      //""Disposal - landfill(D1, D5, D12)"
    	      
    	
    	String birlesik_kod = atikKodu + " " + nitelikKodu + " " + yontem; 
    			
    	System.out.printf("%-8s      %-10s   %-15s   %-12s   %-10s  %-10s  %5d       %-25s", cell.getAddress() , atikKodu,   nitelik,   yontem,   color, hucreTuru.toString(), rowIdx, birlesik_kod );    	
    	
    	return birlesik_kod;
    }
    
    private static String getCellValue(Cell cell) 
    {
    	//Bu fonksiyon birleştirilmiş hücrelerde sadece ilk hücrenin değerini doğru getiriyor.
    	//Diğerlerini boş getiriyor. 
    	
        // DataFormatter cleanly stringifies numbers, dates, and text styles automatically
        DataFormatter formatter = new DataFormatter();
        String cellValue = formatter.formatCellValue(cell);       
        
        return cellValue;        
    }
    
    public static String getCellValue2(Sheet sheet, int rowIdx, int colIdx) 
    {
    	//Birleştirilmiş hücrelerin değerini de doğru getiriyor.
    	
        Row row = sheet.getRow(rowIdx);
        if (row == null) return "";
        
        Cell cell = row.getCell(colIdx);
        if (cell == null) return "";

        // Birleştirilmiş hücre kontrolü
        for (CellRangeAddress range : sheet.getMergedRegions()) {
            if (range.isInRange(rowIdx, colIdx)) {
                // Değer her zaman aralığın sol üst köşesindedir
                Row mergedRow = sheet.getRow(range.getFirstRow());
                Cell mergedCell = mergedRow.getCell(range.getFirstColumn());
                return getFormattedValue(mergedCell);
            }
        }
        
        return getFormattedValue(cell);
    }
    
    private static String getFormattedValue(Cell cell) 
    {
        DataFormatter formatter = new DataFormatter();
        return formatter.formatCellValue(cell);
    }    
    
    public static String getCellColor(Cell cell) 
    {
        if (cell == null) return null;

        String colorStr="";
        String cellAdres = cell.getAddress()+"";
        
        if(cellAdres.equals("G107"))
        	System.out.print("");
        
        // 1. Get the cell style
        CellStyle cellStyle = cell.getCellStyle();
        
        // 2. Retrieve the fill foreground color object
        Color color = cellStyle.getFillForegroundColorColor();

        if (color != null) {
            // Handle modern .xlsx format
            if (color instanceof XSSFColor) {
                XSSFColor xssfColor = (XSSFColor) color;
                // Returns ARGB Hex string (e.g., "FFFF0000" for Red)
                //System.out.println("HEX Color: " + xssfColor.getARGBHex()); 
                colorStr = xssfColor.getARGBHex()+" ";
            } 
            // Handle legacy .xls format
            else if (color instanceof HSSFColor) {
                HSSFColor hssfColor = (HSSFColor) color;
                //if (!(hssfColor instanceof HSSFColor.HSSFColorPredefined.AUTOMATIC)) {
                if (hssfColor.getIndex() != HSSFColor.HSSFColorPredefined.AUTOMATIC.getIndex()) 
                {
                    // Returns RGB triplet format (e.g., "0:0:0")
                    //System.out.println("RGB Triplet: " + hssfColor.getHexString());
                    colorStr = hssfColor.getHexString();
                } 
                else 
                {
                	colorStr = "Color is set to Automatic (Default)";
                    //System.out.println("Color is set to Automatic (Default)");
                }
            }
        } 
        else 
        {
        	colorStr = "Color is set to Automatic (Default)";
            //System.out.println("No background fill color applied.");
        }
        
        return colorStr.trim();
    }            

    public static void setCellValueSample() 
    {
    	//Bu fonksiyon herhangi bir yerde kullanılmıyor. 
    	//Basit dosyaya yazma denemeleri için oluşturuldu.
    	
    	String filePath = "D:\\KorayBey\\WStatR_TRT_XX_DC2026_v00.m02b.xlsm";

        try 
        { // Dynamic creation works for both xlsx and xlsm
        	
        	FileInputStream fis = new FileInputStream(new File(filePath));
        	Workbook workbook = new XSSFWorkbook(fis);
        	fis.close();
        	
        	FileOutputStream fos = new FileOutputStream(new File(filePath));        			            

            // Get the 9th worksheet   --ShettName = "Table_1 (COPY-PASTING)"
            Sheet sheet = workbook.getSheetAt(9);   
            
            Cell cell = sheet.getRow(7).getCell(6);
            cell.setCellValue(555.0);
            
            workbook.write(fos);
            
            String val = getCellValue(cell);
            
            System.out.println(val);
            
            workbook.close();
            fos.close();
        }
        catch(Exception e) 
        {
        	e.printStackTrace();        	
        }            	
    }
}

