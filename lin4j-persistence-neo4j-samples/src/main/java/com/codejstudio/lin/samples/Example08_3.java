package com.codejstudio.lin.samples;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;

import com.codejstudio.lim.common.util.InitializationUtil;
import com.codejstudio.lim.pojo.condition.HypotheticalCondition;
import com.codejstudio.lim.pojo.statement.HypotheticalProposition;
import com.codejstudio.lim.pojo.statement.JudgedStatement;
import com.codejstudio.lim.pojo.statement.Proposition;
import com.codejstudio.lim.pojo.statement.Statement;
import com.codejstudio.lin.common.LINAutoInitConstant;
import com.codejstudio.lin.persistence.neo4j.Neo4jDBUtil;
import com.codejstudio.lin.persistence.neo4j.entityFactory.AbstractFactory;
import com.codejstudio.lin.test.PersistenceTest;

/**
 * sample: Example08_3 
 * in "Theory of Logical Information Model & Logical Information Network"
 * from https://github.com/jhjiang/lim_lin
 * 
 * @author <ul><li>Jeffrey Jiang</li></ul>
 * @see     com.codejstudio.lim.pojo.Root
 * @see     com.codejstudio.lim.pojo.condition.HypotheticalCondition
 * @see     com.codejstudio.lim.pojo.statement.HypotheticalProposition
 * @see     com.codejstudio.lim.pojo.statement.JudgedStatement
 * @see     com.codejstudio.lim.pojo.statement.Proposition
 * @see     com.codejstudio.lim.pojo.statement.Statement
 * @since   lin4j_v1.0.0
 */
public class Example08_3 {

	/* static methods */

	public static void main(String[] args) throws Exception {
		InitializationUtil.initSampleMode(LINAutoInitConstant.AUTO_INITS);

		doDbMarshal(PersistenceTest.GRAPH_DB);
	}

	static void doDbMarshal(final GraphDatabaseService graphDb) throws Exception {
		HypotheticalProposition hpp1 = new HypotheticalProposition("If the growth momentum of the oil consumption remains, "
				+ "even if the oil imports are doubled, "
				+ "it will not change the trend of depletion of domestic oil reserves.");

		Statement s1 = new Statement("the growth momentum of the oil consumption remains");
		Statement s2 = new Statement("the oil imports are doubled");

		JudgedStatement js1 = new Proposition("it will not change the trend of depletion of domestic oil reserves.");

		HypotheticalCondition hcd1 = new HypotheticalCondition(s1);
		HypotheticalCondition hcd2 = new HypotheticalCondition(s2);
//		Condition cd3 = new ConditionGroup(hcd1, hcd2);

//		hpp1.setAntecedentAndConsequent(cd3, js1);
		hpp1.addConsequentAndAntecedent(js1, hcd1, hcd2);



		try (Transaction tx = graphDb.beginTx()) {
			AbstractFactory.create(graphDb, hpp1);
			System.out.println();
			Neo4jDBUtil.prettyPrintAll(graphDb, System.out);
			System.out.println();
			tx.success();
		}
		Neo4jDBUtil.registerShutdownHook(graphDb);
	}

}
