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
 * sample: Example02 
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
public class Example02 {

	/* static methods */

	public static void main(String[] args) throws Exception {
		InitializationUtil.initSampleMode(LINAutoInitConstant.AUTO_INITS);

		doDbMarshal(PersistenceTest.GRAPH_DB);
	}

	static void doDbMarshal(final GraphDatabaseService graphDb) throws Exception {
		Argument a1 = new Argument("① Because the greatest mitochondrial variations occurred in African people, "
				+ "② scientists concluded that they had the longest evolutionary history, "
				+ "③ indicating a probable African origin for modern humans.");

		Argument a2 = new Argument("① Because the greatest mitochondrial variations occurred in African people, "
				+ "② scientists concluded that they had the longest evolutionary history,");

		Argument a3 = new Argument("② (scientists concluded that )they had the longest evolutionary history, "
				+ "③ indicating a probable African origin for modern humans.");

		JudgedStatement js1 = new Proposition("the greatest mitochondrial variations occurred in African people,");
		JudgedStatement js2 = new Proposition("scientists concluded that they had the longest evolutionary history,");
		JudgedStatement js3 = new Proposition("(there is )a probable African origin for modern humans.");

		a2.addConclusionAndEvidence(js2, js1);
		a3.addConclusionAndEvidence(js3, js2);
		a1.addSubArgument(a2, a3);



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
