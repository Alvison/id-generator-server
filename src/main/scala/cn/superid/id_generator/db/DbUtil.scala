package cn.superid.id_generator.db

import cn.superid.id_generator.utils.ConfigUtil

/**
 * Created by zoowii on 14/10/18.
 */
object DbUtil {
  def getDbUrl: String = {
    ConfigUtil.getConfig("db.url")
  }

  def getDbUser: String = {
    ConfigUtil.getConfig("db.user")
  }

  def getDbPassword: String = {
    ConfigUtil.getConfig("db.password")
  }
}
