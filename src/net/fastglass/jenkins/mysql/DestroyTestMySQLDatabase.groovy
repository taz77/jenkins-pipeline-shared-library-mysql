package net.fastglass.jenkins.mysql

class DestroyTestMySQLDatabase{
  def steps
  DestroyTestMySQLDatabase(steps) { this.steps = steps }

  def destroyTestMySQLDatabase(String rootuser, String rootpass, String dbName, String dbUser, String mysqlPath = '', String mysqlPort = '', String dbPass = '') {
    DropTestMySQLUser destroyuser = new DropTestMySQLUser(this.steps)
    DropMySQLDatabase destroydb = new DropMySQLDatabase(this.steps)
    destroyuser.dropTestMySQLUser(rootuser, rootpass, dbUser)
    destroydb.dropMySQLDatabase(rootuser, rootpass, dbName)
  }
}
