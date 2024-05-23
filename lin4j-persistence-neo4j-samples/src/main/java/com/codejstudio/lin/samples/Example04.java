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
 * sample: Example04 
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
public class Example04 {

	/* static methods */

	public static void main(String[] args) throws Exception {
		InitializationUtil.initSampleMode(LINAutoInitConstant.AUTO_INITS);

		doDbMarshal(PersistenceTest.GRAPH_DB);
	}

	static void doDbMarshal(final GraphDatabaseService graphDb) throws Exception {
		Argument a1 = new Argument("① The Big Bang theory is crumbling .... "
				+ "② According to orthodox wisdom, the cosmos began with the Big Bang-an immense, perfectly symmetrical explosion 20 billion years ago. "
				+ "③ (The problem is that) astronomers have confirmed by observation the existence of huge conglomerations of galaxies that are simply too big to have been formed in a mere 20 billion years .... "
				+ "④ (Studies based on new data collected by satellite, and backed up by earlier ground surveys, show that) galaxies are clustered into vast ribbons that stretch billions of light years, "
				+ "⑤ and are separated by voids hundreds of millions of light years across. "
				+ "⑥ Galaxies are observed to travel at only a small fraction of the speed of light. "
				+ "⑦ Mathematics shows that such large clumps of matter must have taken at least one hundred billion years to come together -- five times as long as the time since the hypothetical Big Bang.");

		Argument a2 = new Argument("④ (Studies based on new data collected by satellite, and backed up by earlier ground surveys, show that) galaxies are clustered into vast ribbons that stretch billions of light years, "
				+ "⑤ and are separated by voids hundreds of millions of light years across. "
				+ "⑥ Galaxies are observed to travel at only a small fraction of the speed of light. "
				+ "⑦ Mathematics shows that such large clumps of matter must have taken at least one hundred billion years to come together -- five times as long as the time since the hypothetical Big Bang.");

		Argument a3 = new Argument("③ Astronomers have confirmed by observation the existence of huge conglomerations of galaxies that are simply too big to have been formed in a mere 20 billion years .... "
				+ "⑦ Mathematics shows that such large clumps of matter must have taken at least one hundred billion years to come together -- five times as long as the time since the hypothetical Big Bang.");

		Argument a4 = new Argument("① The Big Bang theory is crumbling .... "
				+ "② According to orthodox wisdom, the cosmos began with the Big Bang-an immense, perfectly symmetrical explosion 20 billion years ago. "
				+ "③ (The problem is that) astronomers have confirmed by observation the existence of huge conglomerations of galaxies that are simply too big to have been formed in a mere 20 billion years .... ");

		JudgedStatement js1 = new Proposition("The Big Bang theory is crumbling.");
		JudgedStatement js2 = new Proposition("According to orthodox wisdom, the cosmos began with the Big Bang-an immense, perfectly symmetrical explosion 20 billion years ago.");
		JudgedStatement js3 = new Proposition("Astronomers have confirmed by observation the existence of huge conglomerations of galaxies that are simply too big to have been formed in a mere 20 billion years.");
		JudgedStatement js4 = new Proposition("(Studies based on new data collected by satellite, and backed up by earlier ground surveys, show that) galaxies are clustered into vast ribbons that stretch billions of light years,");
		JudgedStatement js5 = new Proposition("Galaxies are separated by voids hundreds of millions of light years across.");
		JudgedStatement js6 = new Proposition("Galaxies are observed to travel at only a small fraction of the speed of light.");
		JudgedStatement js7 = new Proposition("Mathematics shows that such large clumps of matter must have taken at least one hundred billion years to come together -- five times as long as the time since the hypothetical Big Bang.");

		a2.addConclusionAndEvidence(js7, js4, js5, js6);
		a3.addConclusionAndEvidence(js3, js7);
		a4.addConclusionAndEvidence(js1, js2, js3);
		a1.addSubArgument(a2, a3, a4);



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
