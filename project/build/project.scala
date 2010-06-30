import sbt._

object AkkaRepositories {
  val AkkaRepo        = MavenRepository("Akka Repository", "http://scalablesolutions.se/akka/repository")
  val GuiceyFruitRepo = MavenRepository("GuiceyFruit Repo", "http://guiceyfruit.googlecode.com/svn/repo/releases/")
  val JBossRepo       = MavenRepository("JBoss Repo", "https://repository.jboss.org/nexus/content/groups/public/")
  val SunJDMKRepo     = MavenRepository("Sun JDMK Repo", "http://wp5.e-taxonomy.eu/cdmlib/mavenrepo")
  val JavaNetRepo     = MavenRepository("java.net Repo", "http://download.java.net/maven/2")
  val CodehausRepo    = MavenRepository("Codehaus Snapshots", "http://snapshots.repository.codehaus.org")
}

class DemoAkkaParentProject(info: ProjectInfo) extends ParentProject(info) {
  import AkkaRepositories._

  // Module configurations
  val netLagModuleConfig      = ModuleConfiguration("net.lag", AkkaRepo)
  val sbinaryModuleConfig     = ModuleConfiguration("sbinary", AkkaRepo)
  val redisModuleConfig       = ModuleConfiguration("com.redis", AkkaRepo)
  val atmosphereModuleConfig  = ModuleConfiguration("org.atmosphere", AkkaRepo)
  val facebookModuleConfig    = ModuleConfiguration("com.facebook", AkkaRepo)
  val jsr166xModuleConfig     = ModuleConfiguration("jsr166x", AkkaRepo)
  val sjsonModuleConfig       = ModuleConfiguration("sjson.json", AkkaRepo)
  val voldemortModuleConfig   = ModuleConfiguration("voldemort.store.compress", AkkaRepo)
  val cassandraModuleConfig   = ModuleConfiguration("org.apache.cassandra", AkkaRepo)
  val guiceyFruitModuleConfig = ModuleConfiguration("org.guiceyfruit", GuiceyFruitRepo)
  val jbossModuleConfig       = ModuleConfiguration("org.jboss", JBossRepo)
  val nettyModuleConfig       = ModuleConfiguration("org.jboss.netty", JBossRepo)
  val jgroupsModuleConfig     = ModuleConfiguration("jgroups", JBossRepo)
  val jmsModuleConfig         = ModuleConfiguration("javax.jms", SunJDMKRepo)
  val jdmkModuleConfig        = ModuleConfiguration("com.sun.jdmk", SunJDMKRepo)
  val jmxModuleConfig         = ModuleConfiguration("com.sun.jmx", SunJDMKRepo)
  val jerseyModuleConfig      = ModuleConfiguration("com.sun.jersey", JavaNetRepo)
  val jerseyContrModuleConfig = ModuleConfiguration("com.sun.jersey.contribs", JavaNetRepo)
  val grizzlyModuleConfig     = ModuleConfiguration("com.sun.grizzly", JavaNetRepo)
  val liftModuleConfig        = ModuleConfiguration("net.liftweb", ScalaToolsSnapshots)
  val multiverseModuleConfig  = ModuleConfiguration("org.multiverse", CodehausRepo)
  val specsModuleConfig       = ModuleConfiguration("org.scala-tools.testing", ScalaToolsSnapshots)

  // Helper for Akka dependencies
  val AkkaVersion = "0.10"
  def akkaModule(module: String) = "se.scalablesolutions.akka" %% ("akka-" + module) % AkkaVersion

  // Subprojects
  val simpleProject  = project("demo-akka-simple",  "demo-akka-simple",  new DemoAkkaSimpleProject(_))
  val bankingProject = project("demo-akka-banking", "demo-akka-banking", new DemoAkkaBankingProject(_))

  /** Simple subproject. */
  class DemoAkkaSimpleProject(info: ProjectInfo) extends DefaultProject(info) {

    // Dependencies
    lazy val akkaCore = akkaModule("core")
    lazy val specs    = "org.scala-tools.testing" %% "specs" % "1.6.5-SNAPSHOT" % "test"
  }

  /** Banking subproject. */
  class DemoAkkaBankingProject(info: ProjectInfo) extends DefaultProject(info) {

    // Dependencies
    lazy val akkaCore = akkaModule("core")
    lazy val specs    = "org.scala-tools.testing" %% "specs" % "1.6.5-SNAPSHOT" % "test"
  }
}
