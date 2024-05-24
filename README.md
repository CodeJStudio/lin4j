# Logical Information Network for Java (LIN4J) v1.0.0

Refer to: 

[Theory of Logical Information Model & Logical Information Network / 《逻辑信息模型与逻辑信息网络》](https://github.com/jhjiang/lim_lin)

[From Logical Information Model, to Logical Information Network, to the realization of Artificial General Intelligence (AGI)](https://www.reddit.com/user/JeffreyJiang/comments/upcloh/from_logical_information_model_to_logical/)

[《从逻辑信息模型，到逻辑信息网络，直至实现通用人工智能》](https://zhuanlan.zhihu.com/p/497443483)

---------

Preparation: 

1. install & config "neo4j" (Recommanded Version: 3.5.35 or later)

2. update configuration: "lin4j-persistence-neo4j\pom.xml", 
                         "lin4j-persistence-neo4j\src\main\resources\properties\db-config.properties"

---------

# LIN4J Samples

The code of "Hello World" is as follows:

```java
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
```

View the data in neo4j-db via "PersistenceTest":

```java
	static void prettyPrintAll(final GraphDatabaseService graphDb) {
		System.out.println("prettyPrintAll(graphDb(\"" + DB + "\"), System.out);");
		Neo4jDBUtil.prettyPrintAll(graphDb, System.out);
	}

	static void doDbUnmarshal(final GraphDatabaseService graphDb) throws LIMException {
		System.out.println("doDbUnmarshal(graphDb(\"" + DB + "\"));");

		Collection<org.neo4j.graphdb.Entity> entities = Neo4jDBUtil.loadAllEntityCollection(graphDb);
		List<BaseElement> elementList = AbstractFactory.load(entities);

		List<GenericActionableElement> gael 
				= CollectionUtil.convertToList(GenericActionableElement.class, elementList);
		List<GenericElement> gel = CollectionUtil.convertToList(GenericElement.class, elementList);
		Root root = new Root(gael, gel, true);
		root.redecorate();
		System.out.println();
		root.marshalToXml(System.out);
	}
```
