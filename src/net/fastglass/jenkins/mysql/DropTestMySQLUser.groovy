package net.fastglass.jenkins.mysql

class DropTestMySQLUser {
  def steps

  DropTestMySQLUser(steps) { this.steps = steps }

  /**
   *
   * @param rootuser
   * @param rootpass
   * @param dbUser
   * @param mysqlPath
   * @param mysqlPort
   * @param dbName
   * @param dbPass
   * @return
   */
  def dropTestMySQLUser(String rootuser, String rootpass, String dbUser = '', String mysqlPath = '', String mysqlPort = '', String dbName = '', String dbPass = '') {
    EvaluateMySQLDatabaseConfiguration config = new EvaluateMySQLDatabaseConfiguration()
    def credentialsConfig = config.check mysqlPath, mysqlPort, dbName, dbUser, dbPass, this.steps
    // Run shell commands to drop the user here
    steps.echo "Dropping user: ${dbUser}"
    GString dropUserStatement = "DROP USER '${dbUser}'@'localhost';"
    GString commandDrop = "\"${credentialsConfig.mysqlPath}\" -u \"${rootuser}\" --password=\"${rootpass}\" <<-EOF\n${dropUserStatement}\nEOF"
    steps.sh "${commandDrop}"
  }
}