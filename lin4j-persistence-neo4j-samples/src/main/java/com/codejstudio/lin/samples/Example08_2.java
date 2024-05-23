package com.codejstudio.lin.samples;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;

import com.codejstudio.lim.common.util.InitializationUtil;
import com.codejstudio.lim.pojo.concept.Concept;
import com.codejstudio.lim.pojo.condition.Condition;
import com.codejstudio.lim.pojo.condition.ConditionGroup;
import com.codejstudio.lim.pojo.condition.FactorCondition;
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
 * sample: Example08_2 
 * in "Theory of Logical Information Model & Logical Information Network"
 * from https://github.com/jhjiang/lim_lin
 * 
 * @author <ul><li>Jeffrey Jiang</li></ul>
 * @see     com.codejstudio.lim.pojo.Root
 * @see     com.codejstudio.lim.pojo.condition.Condition
 * @see     com.codejstudio.lim.pojo.condition.ConditionGroup
 * @see     com.codejstudio.lim.pojo.condition.FactorCondition
 * @see     com.codejstudio.lim.pojo.condition.HypotheticalCondition
 * @see     com.codejstudio.lim.pojo.statement.HypotheticalProposition
 * @see     com.codejstudio.lim.pojo.statement.JudgedStatement
 * @see     com.codejstudio.lim.pojo.statement.Proposition
 * @see     com.codejstudio.lim.pojo.statement.Statement
 * @since   lin4j_v1.0.0
 */
public class Example08_2 {

	/* static methods */

	public static void main(String[] args) throws Exception {
		InitializationUtil.initSampleMode(LINAutoInitConstant.AUTO_INITS);

		doDbMarshal(PersistenceTest.GRAPH_DB);
	}

	static void doDbMarshal(final GraphDatabaseService graphDb) throws Exception {
		HypotheticalProposition hpp1 = new HypotheticalProposition("If oil consumption continues to increase, "
				+ "the domestic oil reserves will be exhausted in 50 years.");

		Statement s1 = new Statement("oil consumption continues to increase");

		JudgedStatement js1 = new Proposition("the domestic oil reserves will be exhausted in 50 years.");

		Concept c1 = new Concept("in 50 years");

		HypotheticalCondition hcd1 = new HypotheticalCondition(s1);
		Condition cd1 = new FactorCondition(c1, FactorCondition.TIME);
		Condition cd2 = new ConditionGroup(hcd1, cd1);

		hpp1.addConsequentAndAntecedent(js1, hcd1);
		hpp1.addCondition(cd2);



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
