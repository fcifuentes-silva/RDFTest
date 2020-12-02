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

public class DeleteFromFile {

	static Statement stmt;
	/**
	 * @param args
	 *
	 * uris provisorias
	 */
	public static void main(String[] args) {
		List<Map<String,String>> noDeleted = new ArrayList<Map<String,String>>();

		Map<String,String> endpoints = new HashMap<String,String>();
		endpoints.put("jdbc:virtuoso://localhost:1111/charset=UTF-8","mipassword");

		Map<String,String> archivos = new HashMap<String,String>();

		archivos.put("URI GRAFO DESTIN", "test-files/datos.ntriples");

		for(String grafo: archivos.keySet()){
			System.err.println("-------------------------- ");
			System.err.println("Borrando datos en: "+grafo);
			System.err.println("-------------------------- ");
			for(String url: endpoints.keySet()){

				try {
					FileInputStream fstream = new FileInputStream(archivos.get(grafo));
					DataInputStream in = new DataInputStream(fstream);
					BufferedReader br = new BufferedReader(new InputStreamReader(in));
					String strLine;
					Class.forName("virtuoso.jdbc4.Driver");
					Connection conn = DriverManager.getConnection(url, "dba",endpoints.get(url));
					stmt = conn.createStatement();
					while ((strLine = br.readLine()) != null) {

						/*String sparql = " sparql DELETE FROM GRAPH <http://ejemplo.com/grafo> {  ?s ?p  ?o .  } WHERE { ?s ?p  ?o . "
								+ "filter(?s = <http://ejemplo.com/dato1> ). } "; */
					        /*String sparql = "sparql DELETE FROM GRAPH  <http://ejemplo.com/grafo>> { <http://ejemplo.com/dato1> ?p ?o  } WHERE { <http://ejemplo.com/dato1> ?p ?o  }" */
						String sparql = "sparql DELETE DATA FROM  <"+grafo+"> { "+strLine+" } "; 
							System.out.println(sparql);
							boolean more = stmt.execute(sparql);
						if(more){
							System.out.println("deleted: " + strLine.toString());
						} else {
							System.out.println("NO deleted: " + strLine.toString());
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

		//imprimir los no eliminados
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

