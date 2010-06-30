package com.weiglewilczek.demo.akka
package banking

import se.scalablesolutions.akka.util.Logging
import se.scalablesolutions.akka.actor.{ ActorRef, Transactor }

/**
 * Actor for banking transactions. Receives the following messages:
 * <ul>
 * <li>Transfer: Transfers the given amount from the given from-account to the given to-account; no reply</li>
 * </ul>
 */
class Bank extends Transactor with Logging {
  log ifDebug "Bank created."

  override def receive = {
    case Transfer(amount, from, to) => transfer(amount, from, to)
  }

  private def transfer(amount: Int, from: ActorRef, to: ActorRef) {
    // Let's run into a rollback scenario deliberately!
    to   ! Deposit(amount)
    from ! Withdraw(amount)
  }
}

/** Message to transfer the given amount from the given from-account to the given to-account. */
case class Transfer(amount: Int, from: ActorRef, to: ActorRef)
