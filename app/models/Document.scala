package models

import com.sksamuel.elastic4s.{RichSearchHit, HitAs}

/**
  * Created by fred on 02/12/15.
  */
case class Document(val id: String, val title: String)

object SearchImplicits {

  implicit object DocumentHitAs extends HitAs[Document] {
    override def as(hit: RichSearchHit): Document = {
      Document(hit.sourceAsMap("_id").toString, hit.sourceAsMap("titlefr").toString)
    }
  }

}