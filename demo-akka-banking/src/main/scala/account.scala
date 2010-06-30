package com.weiglewilczek.demo.akka
package banking

import se.scalablesolutions.akka.actor.Transactor
import se.scalablesolutions.akka.stm.Ref
import se.scalablesolutions.akka.util.Logging

/**
 * Actor for a bank account. Receives the following messages:
 * <ul>
 * <li>GetBalance: Replies with Balance</li>
 * <li>Deposit: Increases the balance by the given amount; no reply</li>
 * <li>Withdraw: Decreases the balance by the given amount; no reply</li>
 * </ul>
 */
class Account extends Transactor with Logging {
  log ifDebug "Account created."

  override def receive = {
    case GetBalance       => self reply Balance(balanceValue)
    case Deposit(amount)  => deposit(amount)
    case Withdraw(amount) => withdraw(amount)
  }

  private val balance = Ref(0)

  private def balanceValue = balance getOrElse 0

  private def deposit(amount: Int) {
    balance swap balanceValue + amount
    log ifInfo "New balance after Deposit(%s) = %s".format(amount, balanceValue)
  }

  private def withdraw(amount: Int) {
    val newValue = balanceValue - amount
    if (newValue < -10) {
      log ifWarning "Account overdrawn! Aborting by throwing an OverdrawException."
      throw new OverdrawException
    }
    else {
      balance swap newValue
      log ifInfo "New balance after Deposit(%s) = %s".format(amount, newValue)
    }
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

/** Aborts a withdrawl. */
class OverdrawException extends RuntimeException
