package com.codejstudio.lin.samples;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;

import com.codejstudio.lim.common.util.InitializationUtil;
import com.codejstudio.lim.pojo.argument.PureHypotheticalSyllogism;
import com.codejstudio.lim.pojo.condition.Condition;
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
 * sample: Example10_1 
 * in "Theory of Logical Information Model & Logical Information Network"
 * from https://github.com/jhjiang/lim_lin
 * 
 * @author <ul><li>Jeffrey Jiang</li></ul>
 * @see     com.codejstudio.lim.pojo.Root
 * @see     com.codejstudio.lim.pojo.argument.PureHypotheticalSyllogism
 * @see     com.codejstudio.lim.pojo.condition.Condition
 * @see     com.codejstudio.lim.pojo.condition.HypotheticalCondition
 * @see     com.codejstudio.lim.pojo.statement.HypotheticalProposition
 * @see     com.codejstudio.lim.pojo.statement.JudgedStatement
 * @see     com.codejstudio.lim.pojo.statement.Proposition
 * @see     com.codejstudio.lim.pojo.statement.Statement
 * @since   lin4j_v1.0.0
 */
public class Example10_1 {

	/* static methods */

	public static void main(String[] args) throws Exception {
		InitializationUtil.initSampleMode(LINAutoInitConstant.AUTO_INITS);

		doDbMarshal(PersistenceTest.GRAPH_DB);
	}

	static void doDbMarshal(final GraphDatabaseService graphDb) throws Exception {
		PureHypotheticalSyllogism phsy1 = new PureHypotheticalSyllogism("① If the first soldier tells the truth, then this is the gate of life. "
				+ "② If this is the gate of life, then the other gate is the gate of death. "
				+ "Therefore, if the first soldier tells the truth, then the other gate is the gate of death.");

		HypotheticalProposition hpp1 = new HypotheticalProposition("If the first soldier tells the truth, then this is the gate of life.");
		HypotheticalProposition hpp2 = new HypotheticalProposition("If this is the gate of life, then the other gate is the gate of death.");
		HypotheticalProposition hpp3 = new HypotheticalProposition("If the first soldier tells the truth, then the other gate is the gate of death.");

		Statement s1 = new Statement("the first soldier tells the truth");
		Statement s2 = new Statement("this is the gate of life");
		Statement s3 = new Statement("There are two gates, one for life and one for death.");

		JudgedStatement js1 = new Proposition("this is the gate of life.");
		JudgedStatement js2 = new Proposition("the other gate is the gate of death.");

		HypotheticalCondition hcd1 = new HypotheticalCondition(s1);
		HypotheticalCondition hcd2 = new HypotheticalCondition(s2);
		Condition cd1 = new Condition(s3, true);

		hpp1.addConsequentAndAntecedent(js1, hcd1);
		hpp2.addConsequentAndAntecedent(js2, hcd2);
		hpp3.addConsequentAndAntecedent(js2, hcd1);
		hpp2.addCondition(cd1);

		phsy1.setElementsOfSyllogism(hpp1, hpp2, hpp3);
		phsy1.addCondition(hcd1, hcd2);



		try (Transaction tx = graphDb.beginTx()) {
			AbstractFactory.create(graphDb, phsy1);
			System.out.println();
			Neo4jDBUtil.prettyPrintAll(graphDb, System.out);
			System.out.println();
			tx.success();
		}
		Neo4jDBUtil.registerShutdownHook(graphDb);
	}

}
