package com.weiglewilczek.demo.akka
package banking

import se.scalablesolutions.akka.actor.Actor
import se.scalablesolutions.akka.util.Logging

/**
 * Actor for a bank account. Receives the following messages:
 * <ul>
 * <li>GetBalance: Replies with Balance</li>
 * <li>Deposit: Increases the balance by the given amount; no reply</li>
 * <li>Withdraw: Decreases the balance by the given amount; no reply</li>
 * </ul>
 */
class Account extends Actor with Logging {
  log ifDebug "Account created."

  override def receive = {
    case GetBalance       => self reply Balance(balance)
    case Deposit(amount)  => deposit(amount)
    case Withdraw(amount) => withdraw(amount)
  }

  private var balance = 0

  private def deposit(amount: Int) {
    balance += amount
    log ifInfo "New balance after Deposit(%s) = %s".format(amount, balance)
  }

  private def withdraw(amount: Int) {
    balance -= amount
    log ifInfo "New balance after Deposit(%s) = %s".format(amount, balance)
  }
}

/** Message to ask for the balance. */
case object GetBalance

/** Message to answer with the balance. */
case class Balance(vlaue: Int)

/** Message to disposit the given amount. */
case class Deposit(amount: Int)

/** Message to withdraw the given amount. */
case class Withdraw(amount: Int)
