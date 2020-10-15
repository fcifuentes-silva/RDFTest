package cl.fcifuentes.rdf;

import java.util.Iterator;

import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Property;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.rdf.model.Statement;
import org.apache.jena.rdf.model.StmtIterator;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.reasoner.ValidityReport;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.util.PrintUtil;
import org.apache.jena.vocabulary.RDF;
import org.apache.jena.vocabulary.RDFS;
import org.junit.Test;

public class TestOWL {

	/**
	 * Documentación de RDFS
	 * https://www.w3.org/TR/rdf-schema/#
	 */
	
	@Test
	public void prueba1() {
		
		System.out.println("Prueba 1 (Transitividad)");
		System.out.println("=============");
		
		Model schema = RDFDataMgr.loadModel("file:test-files/owl/modelo.n3");
		Model data = RDFDataMgr.loadModel("file:test-files/owl/prueba1/datos.n3");
		Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
		reasoner = reasoner.bindSchema(schema);
		InfModel infmodel = ModelFactory.createInfModel(reasoner, data);
		
		
		Resource juan = infmodel.getResource("http://www.ejemplo.com/juan");
		System.out.println("juan tiene triples:");
		printStatements(infmodel, juan, null, null);
		

		
		
		//printStatements(infmodel, temuco, null, null);
		
	}
	@Test
	public void prueba2() {
		
		System.out.println("Prueba 2");
		System.out.println("=============");
		
		Model schema = RDFDataMgr.loadModel("file:test-files/owl/modelo.n3");
		Model data = RDFDataMgr.loadModel("file:test-files/owl/prueba2/datos.n3");
		//InfModel infmodel = ModelFactory.createRDFSModel(schema, data);
		Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
		reasoner = reasoner.bindSchema(schema);
		InfModel infmodel = ModelFactory.createInfModel(reasoner, data);
		
		
		Resource juan = infmodel.getResource("http://www.ejemplo.com/juan");
		System.out.println("juan tiene tipos:");
		printStatements(infmodel, juan, null, null);

		Resource deportista = infmodel.getResource("http://www.ejemplo.com/modelo/Deportista");
		
		System.out.println("deportista tiene triples:");
		printStatements(infmodel, deportista, null, null);
		
		
		Resource temuco = infmodel.getResource("http://www.ejemplo.com/temuco");
		System.out.println("temuco tiene tipos:");
		printStatements(infmodel, temuco, null, null);
		
		
		Resource futbol = infmodel.getResource("http://www.ejemplo.com/futbol");
		System.out.println("futbol tiene tipos:");
		printStatements(infmodel, futbol, RDF.type, null);
		
		
		//printStatements(infmodel, temuco, null, null);
		
	}

	@Test
	public void testValidarOWL() {
		System.out.println("Prueba 3");
		System.out.println("=============");
		Model schema = RDFDataMgr.loadModel("file:test-files/modeloowl.n3");
		Model data = RDFDataMgr.loadModel("file:test-files/datos.n3");
		
		Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
		reasoner = reasoner.bindSchema(schema);
		InfModel infmodel = ModelFactory.createInfModel(reasoner, data);
		
		
		ValidityReport validity = infmodel.validate();
		if (validity.isValid()) {
			System.out.println("OK");
		} else {
			System.out.println("Conflicts");
			for (Iterator i = validity.getReports(); i.hasNext();) {
				System.out.println(" - " + i.next());
			}
		}
	}

	public void printStatements(Model m, Resource s, Property p, Resource o) {
		for (StmtIterator i = m.listStatements(s, p, o); i.hasNext();) {
			Statement stmt = i.nextStatement();
			System.out.println(" - " + PrintUtil.print(stmt));
		}
	}
}
