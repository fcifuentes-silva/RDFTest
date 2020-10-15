package cl.fcifuentes.rdf;

import java.util.Iterator;

import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.ValidityReport;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.util.PrintUtil;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.junit.Test;

public class TestRDFS {

	/**
	 * Documentación de RDFS
	 * https://www.w3.org/TR/rdf-schema/#
	 */
	
	
	@Test
	public void prueba1() {
		System.out.println("Prueba 1");
		System.out.println("=============");
		
		Model schema = RDFDataMgr.loadModel("file:test-files/rdfs/modelo.n3");
		Model data = RDFDataMgr.loadModel("file:test-files/rdfs/prueba1/datos.n3");
		InfModel infmodel = ModelFactory.createRDFSModel(schema, data);

		/*Resource juan = infmodel.getResource("http://www.ejemplo.com/juan");
		System.out.println("juan tiene tipos:");
		printStatements(infmodel, juan, RDF.type, null);

		Resource deportista = infmodel.getResource("http://www.ejemplo.com/modelo/Deportista");
		System.out.println("ser vivo tiene tipos:");
		printStatements(infmodel, deportista, RDF.type, null);
		
		System.out.println("ser vivo tiene tipos:");
		printStatements(infmodel, deportista, RDFS.subClassOf, null);
		
		*/
		Resource temuco = infmodel.getResource("http://www.ejemplo.com/temuco");
		System.out.println("temuco tiene tipos:");
		printStatements(infmodel, temuco, RDF.type, null);
		
		
		Resource futbol = infmodel.getResource("http://www.ejemplo.com/futbol");
		System.out.println("futbol tiene tipos:");
		printStatements(infmodel, futbol, RDF.type, null);
		
		
		
	}
	
	
	@Test
	public void prueba2() {
		System.out.println("Prueba 2");
		System.out.println("=============");
		
		Model schema = RDFDataMgr.loadModel("file:test-files/rdfs/modelo.n3");
		Model data = RDFDataMgr.loadModel("file:test-files/rdfs/prueba2/datos.n3");
		InfModel infmodel = ModelFactory.createRDFSModel(schema, data);

		Resource juan = infmodel.getResource("http://www.ejemplo.com/juan");
		System.out.println("juan tiene tipos:");
		printStatements(infmodel, juan, null, null);

		Resource deportista = infmodel.getResource("http://www.ejemplo.com/modelo/Deportista");
		System.out.println("deportista tipos:");
		printStatements(infmodel, deportista, null, null);
		
		System.out.println("deportista sub clases:");
		printStatements(infmodel, deportista, RDFS.subClassOf, null);
		
		
		Resource temuco = infmodel.getResource("http://www.ejemplo.com/temuco");
		System.out.println("temuco tiene tipos:");
		printStatements(infmodel, temuco, null, null);
		
		
		Resource futbol = infmodel.getResource("http://www.ejemplo.com/futbol");
		System.out.println("futbol tiene tipos:");
		printStatements(infmodel, futbol, null, null);
		
		
		//printStatements(infmodel, temuco, null, null);
		
	}

	
	public void printStatements(Model m, Resource s, Property p, Resource o) {
		for (StmtIterator i = m.listStatements(s, p, o); i.hasNext();) {
			Statement stmt = i.nextStatement();
			System.out.println(" - " + PrintUtil.print(stmt));
		}
	}
}
