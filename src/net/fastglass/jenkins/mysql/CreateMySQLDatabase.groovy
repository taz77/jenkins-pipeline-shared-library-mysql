package net.fastglass.jenkins.mysql

class CreateMySQLDatabase{
  def mysqlPath
  def mysqlPort
  def dbName
  def dbUser
  def dbPass
  def rootuser
  def rootpass
  def steps

  // Constructor to bring in the steps object in order to be able to log.
  CreateMySQLDatabase(steps) { this.steps = steps }

  def createMySQLDatabase(String rootuser, String rootpass, String mysqlPath = '', String mysqlPort = '', String dbName = '', String dbUser = '', String dbPass = '') {
    // Map to  hold all data created.
    def dbconfig = [:]
    EvaluateMySQLDatabaseConfiguration config = new EvaluateMySQLDatabaseConfiguration()
    def credentialsConfig = config.check mysqlPath, mysqlPort, dbName, dbUser, dbPass, this.steps
    // Set all variables.
    this.mysqlPath = credentialsConfig.mysqlPath
    this.mysqlPort = credentialsConfig.mysqlPort
    this.dbName = credentialsConfig.dbName
    this.dbUser = credentialsConfig.dbUser
    this.dbPass = credentialsConfig.dbPass
    this.rootuser = rootuser
    this.rootpass = rootpass
    steps.echo "Starting database creation process"
    def createSqlStatement = "CREATE DATABASE IF NOT EXISTS ${this.dbName};"
    def createUserStatement = "CREATE USER '${this.dbUser}'@'localhost' IDENTIFIED BY '${this.dbPass}';"
    def grantSqlStatement = "GRANT ALL PRIVILEGES ON ${this.dbName}.* TO '${this.dbUser}'@'localhost';"
    def flushStatement = "FLUSH PRIVILEGES;"

    steps.echo "Creating database '${this.dbName}'"
    def shellCommandCreate = "\"${this.mysqlPath}\" -u \"${this.rootuser}\" --password=\"${this.rootpass}\" <<-EOF\n${createSqlStatement}\nEOF"
    steps.sh "${shellCommandCreate}"
    steps.echo "Creating user '${this.dbUser}'"
    def shellCommandCreateUser = "\"${this.mysqlPath}\" -u \"${this.rootuser}\" --password=\"${this.rootpass}\" <<-EOF\n${createUserStatement}\nEOF"
    steps.sh "${shellCommandCreateUser}"
    steps.echo "Granting permissions on '${this.dbName}'"
    def shellCommandGrant = "\"${this.mysqlPath}\" -u \"${this.rootuser}\" --password=\"${this.rootpass}\" <<-EOF\n${grantSqlStatement}\nEOF"
    steps.sh "${shellCommandGrant}"
    steps.echo "Flush Privileges"
    def shellCommandFlush = "\"${this.mysqlPath}\" -u \"${this.rootuser}\" --password=\"${this.rootpass}\" <<-EOF\n${flushStatement}\nEOF"
    steps.sh "${shellCommandFlush}"

    // Set return variables.
    dbconfig.dbUser = this.dbUser
    dbconfig.dbName = this.dbName
    dbconfig.dbPass = this.dbPass
    // Return the configuration.
    return dbconfig
  }
}