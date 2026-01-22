package cl.rvillalon.Modelo;

import java.io.IOException;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

public class Modelo {
    public static String indexDir = System.getProperty("java.io.tmpdir"),dataDir = "",sugerencia;
    public String[] rutaDocumentos,textos;
    public int numeroDocumento;
    Tika tika = new Tika();
    Indexador indexador;
    public Buscador buscador;
    public int numeroPalabrasIndexadas = 0,numeroPalabrasEncontradas = 0;
    public long tiempoIndexado,tiempoBusqueda;
    
    public int CrearIndice(){
        try {
            indexador = new Indexador(indexDir);
        } catch (IOException ex) {}
      long tiempoInicial = System.currentTimeMillis();	
        try {
            numeroPalabrasIndexadas = indexador.crearIndexacion(dataDir);
        } catch (IOException | TikaException ex) {
            ex.getStackTrace();
        }
        
        long tiempoFinal = System.currentTimeMillis();
        if(numeroPalabrasEncontradas == -1){
            try {
                indexador.close();
                tiempoIndexado = 0;
                return -1;
            } catch (IOException ex) {
                ex.getStackTrace();
            }
      }
      
      if(numeroPalabrasIndexadas != -1){
            try {
                indexador.close();
                tiempoIndexado = tiempoFinal-tiempoInicial;
                return 1;                
            } catch (IOException ex) {
                ex.getStackTrace();
            }
      }
	
      return 0;
   }
    
    public int Buscar(String palabra) throws IOException, ParseException, InvalidTokenOffsetsException {
        buscador = new Buscador(indexDir);
        long tiempoInicial = System.currentTimeMillis();
        TopDocs hits = buscador.Busqueda(palabra);
        tiempoBusqueda = System.currentTimeMillis() - tiempoInicial;
        numeroPalabrasEncontradas = (int)hits.totalHits.value;
        textos = buscador.Highlights(palabra);
        rutaDocumentos = buscador.ObtenerRutas(palabra);
        
        return 0;
     }
    
    public String CortarRuta(String ruta){
        String nuevaRuta = ruta;
        nuevaRuta = nuevaRuta.substring(0, nuevaRuta.length() - 4);
        return nuevaRuta;
    }
    
}

