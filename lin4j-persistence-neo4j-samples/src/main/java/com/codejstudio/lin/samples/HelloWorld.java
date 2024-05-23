package com.codejstudio.lin.samples;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Transaction;

import com.codejstudio.lim.common.util.InitializationUtil;
import com.codejstudio.lim.pojo.concept.Concept;
import com.codejstudio.lim.pojo.relation.BaseRelation;
import com.codejstudio.lim.pojo.relation.EquivalenceRelation;
import com.codejstudio.lim.pojo.statement.Statement;
import com.codejstudio.lin.common.LINAutoInitConstant;
import com.codejstudio.lin.persistence.neo4j.Neo4jDBUtil;
import com.codejstudio.lin.persistence.neo4j.entityFactory.AbstractFactory;
import com.codejstudio.lin.test.PersistenceTest;

/**
 * sample: HelloWorld
 * 
 * @author <ul><li>Jeffrey Jiang</li></ul>
 * @see     com.codejstudio.lim.pojo.Root
 * @see     com.codejstudio.lim.pojo.concept.Concept
 * @see     com.codejstudio.lim.pojo.relation.BaseRelation
 * @see     com.codejstudio.lim.pojo.relation.EquivalenceRelation
 * @see     com.codejstudio.lim.pojo.statement.Statement
 * @since   lin4j_v1.0.0
 */
public class HelloWorld {

	/* static methods */

	public static void main(String[] args) throws Exception {
		InitializationUtil.initSampleMode(LINAutoInitConstant.AUTO_INITS);

		doDbMarshal(PersistenceTest.GRAPH_DB);
	}

	static void doDbMarshal(final GraphDatabaseService graphDb) throws Exception {
		Statement s1 = new Statement("Hello, world!");

		Concept c1 = new Concept("hello");
		Concept c2 = new Concept(",");
		Concept c3 = new Concept("world");
		Concept c4 = new Concept("!");
		Concept c5 = new Concept("H");
		Concept c6 = new Concept("h");
		Concept c7 = new Concept("e");
		Concept c8 = new Concept("l");
		Concept c9 = new Concept("o");
		Concept c10 = new Concept("w");
		Concept c11 = new Concept("r");
		Concept c12 = new Concept("d");
		Concept c13 = null;

		s1.addConcept(c1, c2, c3, c4, c5); //No position of sub-concepts sample
		s1.addConcept(c13); //NullPointerException tolerance sample
		c1.addSubConceptWithPosition(c6, c7, c8, c9); //Auto positions of sub-concepts sample
		c1.addSubConcept(); //NullPointerException tolerance sample
		c3.addSubConcept(new Integer[] {7, 8, 9, 10, 11}, c10, c9, c11, c8, c12); //Manual positions of sub-concepts sample

		c5.addIncompatibleAttribute("case", new Concept("Uppercase"));
		c5.addCompatibleAttribute("case", new Concept("大写"));

		BaseRelation br1 = new EquivalenceRelation(c5, c6);



		try (Transaction tx = graphDb.beginTx()) {
			AbstractFactory.create(graphDb, s1, br1);
			System.out.println();
			Neo4jDBUtil.prettyPrintAll(graphDb, System.out);
			System.out.println();
			tx.success();
		}
		Neo4jDBUtil.registerShutdownHook(graphDb);
	}

}
