package cl.fcifuentes.rdf;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdf.model.InfModel;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.rdf.model.Resource;
import org.apache.jena.reasoner.Reasoner;
import org.apache.jena.reasoner.ReasonerRegistry;
import org.apache.jena.riot.RDFDataMgr;
import org.apache.jena.vocabulary.RDF;
import org.junit.Test;

public class TestSPARQL extends AbstractTest{

	@Test
	public void prueba1() {

		System.out.println("Prueba 1");
		System.out.println("=============");

		Model schema = RDFDataMgr.loadModel("file:test-files/owl/modelo.n3");
		Model data = RDFDataMgr.loadModel("file:test-files/owl/prueba2/datos.n3");
		
		Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
		reasoner = reasoner.bindSchema(schema);
		InfModel infmodel = ModelFactory.createInfModel(reasoner, data);


		
		String prefix = " ";
		String szQuery = prefix + " select ?x ?y ?z WHERE {?x ?y ?z} ";
		System.out.println("Execute query " + szQuery);

		Query query = QueryFactory.create(szQuery);
		QueryExecution qexec = QueryExecutionFactory.create(query, infmodel);

		try {
			ResultSet results = qexec.execSelect();
			ResultSetFormatter.out(System.out, results, query);
		}

		finally {
			qexec.close();
		}


		infmodel.close();
	}
	
	@Test
	public void prueba2() {

		System.out.println("Prueba 2");
		System.out.println("=============");

		Model schema = RDFDataMgr.loadModel("file:test-files/owl/modelo.n3");
		Model data = RDFDataMgr.loadModel("file:test-files/owl/prueba2/datos.n3");
		
		Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
		reasoner = reasoner.bindSchema(schema);
		InfModel infmodel = ModelFactory.createInfModel(reasoner, data);


		
		String prefix = " ";
		String szQuery = prefix + " select count(*) WHERE {?x ?y ?z} ";
		System.out.println("Execute query " + szQuery);

		Query query = QueryFactory.create(szQuery);
		QueryExecution qexec = QueryExecutionFactory.create(query, infmodel);

		try {
			ResultSet results = qexec.execSelect();
			ResultSetFormatter.out(System.out, results, query);
		}

		finally {
			qexec.close();
		}


		infmodel.close();
	}
	
	@Test
	public void prueba3() {

		System.out.println("Prueba 3");
		System.out.println("=============");

		Model schema = RDFDataMgr.loadModel("file:test-files/sparql/modelo.n3");
		Model data = RDFDataMgr.loadModel("file:test-files/sparql/datos.n3");
		
		Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
		reasoner = reasoner.bindSchema(schema);
		InfModel infmodel = ModelFactory.createInfModel(reasoner, data);


		
		String prefix = "PREFIX foaf: <http://xmlns.com/foaf/0.1/> ";
		String szQuery = prefix + " select distinct ?x WHERE {?x a foaf:Person . } ";
		System.out.println("Execute query " + szQuery);

		Query query = QueryFactory.create(szQuery);
		QueryExecution qexec = QueryExecutionFactory.create(query, infmodel);

		try {
			ResultSet results = qexec.execSelect();
			ResultSetFormatter.out(System.out, results, query);
		}

		finally {
			qexec.close();
		}


		infmodel.close();
	}
	
	
	@Test
	public void prueba4() {

		System.out.println("Prueba 4");
		System.out.println("=============");

		Model schema = RDFDataMgr.loadModel("file:test-files/sparql/modelo.n3");
		Model data = RDFDataMgr.loadModel("file:test-files/sparql/datos.n3");
		
		Reasoner reasoner = ReasonerRegistry.getOWLReasoner();
		reasoner = reasoner.bindSchema(schema);
		InfModel infmodel = ModelFactory.createInfModel(reasoner, data);


		
		String prefix = "PREFIX mod: <http://www.ejemplo.com/modelo/> PREFIX foaf: <http://xmlns.com/foaf/0.1/> ";
		String szQuery = prefix + " select distinct ?x WHERE {<http://www.ejemplo.com/temuco> mod:seRelacionaCon ?x . ?x a foaf:Person . } ";
		System.out.println("Execute query " + szQuery);

		Query query = QueryFactory.create(szQuery);
		QueryExecution qexec = QueryExecutionFactory.create(query, infmodel);

		try {
			ResultSet results = qexec.execSelect();
			ResultSetFormatter.out(System.out, results, query);
		}

		finally {
			qexec.close();
		}


		infmodel.close();
	}
}
