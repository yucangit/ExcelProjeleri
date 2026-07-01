package pack1;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class Utils 
{
	public static void copyFileFromJar(String jarResourcePath, String targetPath) throws IOException 
    {
    	
    	//String jarResourcePath = "/resources/Dosyalar/Parametreler.xlsx";
        
        // Define where you want to copy the file on the host system
        //Path targetPath = Paths.get("D:\\Parametreler.xlsx");
		Path targetPath2 = Paths.get(targetPath);

            	
        // 1. Ensure target directories exist on the host filesystem
        if (targetPath2.getParent() != null) 
        {
            Files.createDirectories(targetPath2.getParent());
        }

        // 2. Fetch the file stream from inside the running JAR
        try (InputStream in = Utils.class.getResourceAsStream(jarResourcePath)) 
        {
            if (in == null) 
            {
                throw new IOException("Resource not found inside JAR: " + jarResourcePath);
            }
            
            // 3. Stream the file directly to the external directory
            Files.copy(in, targetPath2, StandardCopyOption.REPLACE_EXISTING);
        }        
    }
	
	public static String getProtokol() 
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
	
	public static void isFileExists(String filePath) 
	{
		
		boolean result = false;
		
        Path path = Paths.get(filePath);

        // Checks if the path exists (can be a file OR a directory)
        if (Files.exists(path)) {
            // Checks if it is specifically a regular file
            if (Files.isRegularFile(path)) {
                System.out.println("File exists!");
            }
        } else {
            System.out.println("File does not exist.");
        }
    }

}
