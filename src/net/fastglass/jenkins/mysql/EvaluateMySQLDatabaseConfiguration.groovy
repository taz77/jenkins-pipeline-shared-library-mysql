package net.fastglass.jenkins.mysql
import java.util.UUID

def check(mysqlPath, mysqlPort, dbName, dbUser, dbPass, steps) {
  def config = [:]
  String uuid = UUID.randomUUID()
  def modifieduuid = uuid.replaceAll('-','_')

  // Set any unprovided configuration to random generated.
  if (dbName == null || dbName == '') {
    String dbNameConstructed = "testdb_" + modifieduuid + "_" + env.BUILD_NUMBER
    config.dbName = dbNameConstructed
  }
  if (dbUser == null || dbUser == ''){
    String dbUserConstructed = "testuser_" + modifieduuid
    // Usernames cannot be more than 32 characters.
    config.dbUser = dbUserConstructed.take(32)
  }
  if (dbPass == null || dbPass == ''){
    String dbPassConstructed = "testpass_" + modifieduuid
    config.dbPass = dbPassConstructed
  }
  // Use defaults if nothing is provided.
  config.mysqlPath = mysqlPath ?: '/usr/bin/mysql'
  config.mysqlPort = mysqlPort ?: 3306

  // Return the parsed database configuration.
  return config
}
