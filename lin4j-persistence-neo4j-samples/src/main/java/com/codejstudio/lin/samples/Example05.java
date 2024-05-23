package com.codejstudio.lin.samples;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;

import com.codejstudio.lim.common.util.InitializationUtil;
import com.codejstudio.lim.pojo.concept.Concept;
import com.codejstudio.lim.pojo.statement.Definition;
import com.codejstudio.lin.common.LINAutoInitConstant;
import com.codejstudio.lin.persistence.neo4j.Neo4jDBUtil;
import com.codejstudio.lin.persistence.neo4j.entityFactory.AbstractFactory;
import com.codejstudio.lin.test.PersistenceTest;

/**
 * sample: Example05 
 * in "Theory of Logical Information Model & Logical Information Network"
 * from https://github.com/jhjiang/lim_lin
 * 
 * @author <ul><li>Jeffrey Jiang</li></ul>
 * @see     com.codejstudio.lim.pojo.Root
 * @see     com.codejstudio.lim.pojo.concept.Concept
 * @see     com.codejstudio.lim.pojo.statement.Definition
 * @since   lin4j_v1.0.0
 */
public class Example05 {

	/* static methods */

	public static void main(String[] args) throws Exception {
		InitializationUtil.initSampleMode(LINAutoInitConstant.AUTO_INITS);

		doDbMarshal(PersistenceTest.GRAPH_DB);
	}

	static void doDbMarshal(final GraphDatabaseService graphDb) throws Exception {
		Definition df1 = new Definition("Bird is a kind of warm-blooded vertebrate with feathers.");

		Concept c1 = new Concept("bird");
		Concept c2 = new Concept("is (be)");
		Concept c3 = new Concept("a kind of warm-blooded vertebrate with feathers");

		df1.setDefiniendumAndDefiniens(c1, c3, c2);



		try (Transaction tx = graphDb.beginTx()) {
			AbstractFactory.create(graphDb, df1);
			System.out.println();
			Neo4jDBUtil.prettyPrintAll(graphDb, System.out);
			System.out.println();
			tx.success();
		}
		Neo4jDBUtil.registerShutdownHook(graphDb);
	}

}
