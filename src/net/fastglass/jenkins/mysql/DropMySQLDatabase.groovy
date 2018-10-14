package net.fastglass.jenkins.mysql

class DropMySQLDatabase {
  def steps
  DropMySQLDatabase(steps) { this.steps = steps }

  /**
   *
   * @param rootuser
   * @param rootpass
   * @param dbName
   * @param mysqlPath
   * @param mysqlPort
   * @param dbUser
   * @param dbPass
   * @return
   */
  def dropMySQLDatabase(String rootuser, String rootpass, String dbName = '', String mysqlPath = '', String mysqlPort = '', String dbUser = '', String dbPass = '') {
    EvaluateMySQLDatabaseConfiguration config = new EvaluateMySQLDatabaseConfiguration()
    def credentialsConfig = config.check mysqlPath, mysqlPort, dbName, dbUser, dbPass, this.steps
    // Run shell commands to drop the database here
    steps.echo "Dropping database: ${dbName}"
    GString dropSchemaStatement = "DROP DATABASE IF EXISTS ${dbName};"
    GString command = "\"${credentialsConfig.mysqlPath}\" -u \"${rootuser}\" --password=\"${rootpass}\" <<-EOF\n${dropSchemaStatement}\nEOF"
    steps.sh "${command}"
  }
}
