package cn.superid.id_generator.modules

import cn.superid.id_generator.beans.decoders.impl.{DbTableDecoder, ServerGroupXmlDecoder, ServerMigrationDecoder, ServerStateXmlDecoder}
import cn.superid.id_generator.beans.decoders.{IDbTableDecoder, IServerGroupXmlDecoder, IServerMigrationDecoder, IServerStateXmlDecoder}
import cn.superid.id_generator.services._
import cn.superid.id_generator.services.impl._
import cn.superid.id_generator.workers.IIdGeneratorWorker
import cn.superid.id_generator.workers.impl.IdGeneratorWorker
import cn.superid.net.IWebSocketHandlerDispatcher
import cn.superid.net.impl.WebSocketHandlerDispatcher
import com.google.inject.AbstractModule

/**
 * Created by zoowii on 14/10/18.
 */
class IdGeneratorModule extends AbstractModule {
  protected def configure {
    bind(classOf[IIdGeneratorWorker]).to(classOf[IdGeneratorWorker])
    bind(classOf[IIdMerger]).to(classOf[ObjectIdMerge])
    bind(classOf[IMachineClusterStateManager]).to(classOf[MachineClusterStateManager])
    bind(classOf[IServerStateXmlDecoder]).to(classOf[ServerStateXmlDecoder])
    bind(classOf[IServerGroupXmlDecoder]).to(classOf[ServerGroupXmlDecoder])
    bind(classOf[IServerGroupService]).to(classOf[ServerGroupService])
    bind(classOf[IDbIdSequenceService]).to(classOf[DbIdSequenceService])
    bind(classOf[IServerMigrationDecoder]).to(classOf[ServerMigrationDecoder])
    bind(classOf[IDbTableDecoder]).to(classOf[DbTableDecoder])
    bind(classOf[IDbRouter]).to(classOf[MockDbRouter])
    bind(classOf[IWebSocketHandlerDispatcher]).to(classOf[WebSocketHandlerDispatcher])
    bind(classOf[IServerWeightCountService]).to(classOf[ServerWeightCountService])
  }
}
