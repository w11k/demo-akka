package com.weiglewilczek.demo.akka
package simple

import se.scalablesolutions.akka.actor.Actor
import se.scalablesolutions.akka.actor.Actor._
import se.scalablesolutions.akka.util.Logging

/**
 * Main class to run this project.
 */
object Main extends Logging {

  def main(args: Array[String]) {
    val pingPong = actorOf[PingPong].start
    try {
      // Fire and forget
      pingPong ! Ping

      // Ask-answer
      (pingPong !! Ping).as[Pong] match {
        case Some(pong) => log ifInfo "Received answer %s.".format(pong)
        case None       => log ifError "Timeout: Received no answer!"
      }
    }
    finally {
      Thread sleep 1000 // Let's wait until our actors are finished.
      pingPong.stop
    }
  }
}

/**
 * Simple actor receiving Ping messages and replying with Pong messages.
 */
class PingPong extends Actor with Logging {
  log ifInfo "PingPong created."

  override def receive = {
    case Ping =>
      log ifInfo "Received Ping."
      self reply Pong(System.currentTimeMillis)
  }
}

/** Ping message. */
case object Ping

/** Pong message with time in milliseconds. */
case class Pong(time: Long)
