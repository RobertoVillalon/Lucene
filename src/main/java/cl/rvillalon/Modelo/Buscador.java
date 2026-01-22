package cl.rvillalon.Modelo;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.InvalidTokenOffsetsException;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.search.highlight.TextFragment;
import org.apache.lucene.search.highlight.TokenSources;
import org.apache.lucene.search.spell.Dictionary;
import org.apache.lucene.search.spell.LuceneDictionary;
import org.apache.lucene.search.suggest.Lookup;
import org.apache.lucene.search.suggest.analyzing.FreeTextSuggester;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class Buscador {
   IndexSearcher indexSearcher;
   QueryParser parserConsulta;
   IndexReader lector;
   Query consulta;
   
   public Buscador(String direccionCarpetaContenedora) throws IOException {
      Directory indexDirectory = FSDirectory.open(Paths.get(direccionCarpetaContenedora));
      lector = DirectoryReader.open(indexDirectory);
     
      indexSearcher = new IndexSearcher(lector);
      parserConsulta = new QueryParser("contents",new StandardAnalyzer());
   }
   
   public TopDocs Busqueda(String searchQuery) throws IOException, ParseException{
      consulta = parserConsulta.parse(searchQuery);
      return indexSearcher.search(consulta, 10);
   }
   
    public String[] Highlights(String palabra) throws ParseException, IOException, InvalidTokenOffsetsException{
          TopDocs hits = Busqueda(palabra);
          char comillas = '"';
          ArrayList<String> textoParcial = new ArrayList<>();
          String[] textos = null;
          
          Highlighter highlighter = new Highlighter(new SimpleHTMLFormatter(""+comillas,""+comillas), new QueryScorer(consulta));
       for (ScoreDoc scoreDoc : hits.scoreDocs) {
           int id = scoreDoc.doc;
           Document doc = indexSearcher.doc(id);
           String text = doc.get("contents");
           TokenStream tokenStream = TokenSources.getAnyTokenStream(indexSearcher.getIndexReader(), id, "contents", new StandardAnalyzer());
           TextFragment[] frag = highlighter.getBestTextFragments(tokenStream, text, false, 10000);
           String aux = "";
           for (TextFragment frag1 : frag) {
               aux += frag1.toString();
           }
           textoParcial.add(aux);
       }
          
        textos = new String[textoParcial.size()];
        
        for(int i = 0; i < textoParcial.size(); i++){
            textos[i] = textoParcial.get(i);
        }
          
        return textos;
    }
    
    public String Sugerencia(String key) throws IOException{
        Dictionary diccionario = new LuceneDictionary(lector, "contents");
        long valor = 0;
        String sugerencia = "";
        
        FreeTextSuggester suggest = new FreeTextSuggester(new StandardAnalyzer());
        suggest.build(diccionario);
        
        List<Lookup.LookupResult> lookupResultList = suggest.lookup(key, false, 10);
        
        for (Lookup.LookupResult lookupResult : lookupResultList) {
            if(lookupResult.value > valor){
                valor = lookupResult.value;
                sugerencia = lookupResult.key.toString();
            }
        }
        
        return sugerencia;
    }
   
   
   public Document ObtencionDocumento(ScoreDoc scoreDoc) throws CorruptIndexException, IOException {
       
      return indexSearcher.doc(scoreDoc.doc);	
   }

    String[] ObtenerRutas(String palabra) throws IOException, ParseException{
        String[] rutas;
        ArrayList<String> rutasParcial = new ArrayList<>();
        TopDocs hits = Busqueda(palabra);
        
        for (int i = 0; i < hits.scoreDocs.length; i++) {
            int id = hits.scoreDocs[i].doc;
            Document doc = indexSearcher.doc(id);
            String text = doc.get("filename");
            rutasParcial.add(text);
        }
        
        rutas = new String[rutasParcial.size()];
        
        for(int i = 0; i < rutasParcial.size(); i++){
            rutas[i] = rutasParcial.get(i);
        }
        
        return rutas;
    }
    

}
