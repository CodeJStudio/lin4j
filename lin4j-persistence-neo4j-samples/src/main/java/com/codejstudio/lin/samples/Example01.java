package com.codejstudio.lin.samples;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;

import com.codejstudio.lim.common.util.InitializationUtil;
import com.codejstudio.lim.pojo.argument.Argument;
import com.codejstudio.lim.pojo.statement.JudgedStatement;
import com.codejstudio.lim.pojo.statement.Proposition;
import com.codejstudio.lin.common.LINAutoInitConstant;
import com.codejstudio.lin.persistence.neo4j.Neo4jDBUtil;
import com.codejstudio.lin.persistence.neo4j.entityFactory.AbstractFactory;
import com.codejstudio.lin.test.PersistenceTest;

/**
 * sample: Example01 
 * in "Theory of Logical Information Model & Logical Information Network"
 * from https://github.com/jhjiang/lim_lin
 * 
 * @author <ul><li>Jeffrey Jiang</li></ul>
 * @see     com.codejstudio.lim.pojo.Root
 * @see     com.codejstudio.lim.pojo.argument.Argument
 * @see     com.codejstudio.lim.pojo.statement.JudgedStatement
 * @see     com.codejstudio.lim.pojo.statement.Proposition
 * @since   lin4j_v1.0.0
 */
public class Example01 {

	/* static methods */

	public static void main(String[] args) throws Exception {
		InitializationUtil.initSampleMode(LINAutoInitConstant.AUTO_INITS);

		doDbMarshal(PersistenceTest.GRAPH_DB);
	}

	static void doDbMarshal(final GraphDatabaseService graphDb) throws Exception {
		Argument a1 = new Argument("① All mammals are warm-blooded animals. "
				+ "② No lizards are warm-blooded animals. "
				+ "③ Therefore all lizards are nonmammals.");

		JudgedStatement js1 = new Proposition("All mammals are warm-blooded animals.");
		JudgedStatement js2 = new Proposition("No lizards are warm-blooded animals.");
		JudgedStatement js3 = new Proposition("All lizards are nonmammals.");

		a1.addConclusionAndEvidence(js3, js1, js2);



		try (Transaction tx = graphDb.beginTx()) {
			AbstractFactory.create(graphDb, a1);
			System.out.println();
			Neo4jDBUtil.prettyPrintAll(graphDb, System.out);
			System.out.println();
			tx.success();
		}
		Neo4jDBUtil.registerShutdownHook(graphDb);
	}

}
