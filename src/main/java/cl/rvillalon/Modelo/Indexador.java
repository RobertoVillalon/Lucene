package cl.rvillalon.Modelo;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JOptionPane;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

public class Indexador{
    Tika tika;
    IndexWriter writer;
   
   public Indexador(String rutaArchivo) throws IOException {
      Directory directorio = FSDirectory.open(Paths.get(rutaArchivo));
      IndexWriterConfig iwc = new IndexWriterConfig(new StandardAnalyzer());
      writer = new IndexWriter(directorio, iwc);
      tika = new Tika();
   }
   
   public void close() throws CorruptIndexException, IOException {
      writer.close();
   }
   
   private Document getDocument(File archivo) throws IOException, TikaException {
        Document documento = new Document();
        String contenido;
        
        if(!archivo.getName().toLowerCase().endsWith(".txt")){
            archivo = conversor(archivo);
        }
        
        contenido = LeerArchivo(archivo.toString(),Charset.defaultCharset());
        documento.add(new TextField("contents",contenido,TextField.Store.YES));
        documento.add(new TextField("filename",archivo.getName(),TextField.Store.YES));
        documento.add(new TextField("filepath",archivo.getCanonicalPath(),TextField.Store.YES));
        
      return documento;
   }   
   
static String LeerArchivo(String path, Charset encoding) throws IOException{
    byte[] encoded = Files.readAllBytes(Paths.get(path));
    
    return new String(encoded, encoding);
}
   
   
   private File conversor(File archivo) throws IOException, TikaException{
       String contenido = tika.parseToString(archivo).trim();
       contenido = contenido.replaceAll("\n\n","\n");
       
       
       File nuevoArchivo = null; 
       try {
            String ruta = archivo.getPath()+".txt";
            
            nuevoArchivo = new File(ruta);
            
            if (!nuevoArchivo.exists()) {
                nuevoArchivo.createNewFile();
            }
            FileWriter fw = new FileWriter(nuevoArchivo);
           try (BufferedWriter bw = new BufferedWriter(fw)) {
               bw.write(contenido);
           }
        } catch (IOException e) {
            e.getStackTrace();
        }
       
       return nuevoArchivo;
    }
   
   private void indexarArchivo(File archivo) throws IOException, TikaException {
      Document documento = getDocument(archivo);
      writer.addDocument(documento);
   }
   
   public int crearIndexacion(String directorio) throws IOException, TikaException {
      if(directorio.equals("")){
          JOptionPane.showMessageDialog(null, "Ningun directorio seleccionado","Advertencia",2);
          return -1;
      } 
      else{ 
        File[] archivos = new File(directorio).listFiles();
        
        for (File archivo : archivos) {
           if(!archivo.isDirectory() && !archivo.isHidden() && archivo.exists() && archivo.canRead()){
              indexarArchivo(archivo);
           }
        }
      }
      
      return writer.numRamDocs();
   }
} 