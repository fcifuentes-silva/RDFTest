package cl.fcifuentes.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InsertFromFile {

    static Statement stmt;
    /**
     * @param args
     */
    public static void main(String[] args) {

	List<Map<String,String>> noDeleted = new ArrayList<Map<String,String>>();

	Map<String,String> endpoints = new HashMap<String,String>();
	endpoints.put("jdbc:virtuoso://localhost:1111/charset=UTF-8","mipassword");

	Map<String,String> archivos = new HashMap<String,String>();

	archivos.put("URI GRAFO DESTIN", "test-files/datos.ntriples");


	for(String grafo: archivos.keySet()){
	    System.err.println("-------------------------- ");
	    System.err.println("Insertando datos en: "+grafo);
	    System.err.println("-------------------------- ");
	    for(String url: endpoints.keySet()){

		try {
		    FileInputStream fstream = new FileInputStream(archivos.get(grafo));
		    DataInputStream in = new DataInputStream(fstream);
		    BufferedReader br = new BufferedReader(new InputStreamReader(in));
		    String strLine;
		    Class.forName("virtuoso.jdbc4.Driver");
		    Connection conn = DriverManager.getConnection(url, "dba", endpoints.get(url));
		    stmt = conn.createStatement();
		    while ((strLine = br.readLine()) != null) {

			while (strLine.trim().charAt(strLine.trim().length() - 1) != '.') {
			    final String nuevoSegmentoTripleta = br.readLine();
			    if (nuevoSegmentoTripleta != null) {
				strLine = strLine +"\n" +nuevoSegmentoTripleta;
			    } else {
				throw new Exception("RDF mal formado");
			    }
			}

			System.out.println("sparql INSERT DATA INTO <"+grafo+"> { "+strLine.toString()+" }");
			boolean more = stmt.execute("sparql INSERT DATA INTO <"+grafo+"> { "+strLine.toString()+" }");
			if(more){
			    System.out.println("insertado: " + strLine.toString());
			} else {
			    System.out.println("NO insertado: " + strLine.toString());
			    Map<String,String> map = new HashMap<String,String>();
			    map.put(grafo, strLine.toString());
			    noDeleted.add(map);
			}
		    }
		    in.close();
		    conn.close();

		} catch (Exception e) {
		    System.err.println("Error: " + e.getMessage());
		}
	    }
	}

	//imprimir los no insertado
	if(noDeleted.size()>0) {
	    System.err.println("********************************************** ");
	    System.err.println("********************************************** ");
	    for(Map<String,String> map: noDeleted){
		for(String key: map.keySet()){
		    System.err.println(map.get(key)+" " +key );
		}

	    }
	}
    }



}
