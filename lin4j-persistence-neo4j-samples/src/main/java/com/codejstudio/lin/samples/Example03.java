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
 * sample: Example03 
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
public class Example03 {

	/* static methods */

	public static void main(String[] args) throws Exception {
		InitializationUtil.initSampleMode(LINAutoInitConstant.AUTO_INITS);

		doDbMarshal(PersistenceTest.GRAPH_DB);
	}

	static void doDbMarshal(final GraphDatabaseService graphDb) throws Exception {
		Argument a1 = new Argument("① An outstanding advantage of nuclear over fossil fuel energy is how easy it is to deal with the waste it produces. "
				+ "② Burning fossil fuels produces 27,000 million tons of carbon dioxide yearly, enough to make, if solidified, a mountain nearly one mile high with a base twelve miles in circumference. "
				+ "③ The same quantity of energy produced from nuclear fission reactions would generate two million times less waste, and it would occupy a sixteen-meter cube. "
				+ "④ All of the high-level waste produced in a year from a nuclear power station would occupy a space about a cubic meter in size and would fit safely in a concrete pit.");

		JudgedStatement js1 = new Proposition("An outstanding advantage of nuclear over fossil fuel energy is how easy it is to deal with the waste it produces.");
		JudgedStatement js2 = new Proposition("Burning fossil fuels produces 27,000 million tons of carbon dioxide yearly, enough to make, if solidified, a mountain nearly one mile high with a base twelve miles in circumference.");
		JudgedStatement js3 = new Proposition("The same quantity of energy produced from nuclear fission reactions would generate two million times less waste, and it would occupy a sixteen-meter cube.");
		JudgedStatement js4 = new Proposition("All of the high-level waste produced in a year from a nuclear power station would occupy a space about a cubic meter in size and would fit safely in a concrete pit.");

		a1.addConclusionAndEvidence(js1, js2, js3, js4);



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
