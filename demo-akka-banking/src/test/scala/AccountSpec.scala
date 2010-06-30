package com.weiglewilczek.demo.akka
package banking

import org.specs.Specification
import se.scalablesolutions.akka.actor.Actor._

class AccountSpec extends Specification {

  "Sending GetBalance to a new Account" should {
    "return a zero balance" in {
      val account = actorOf[Account].start
      try {
        val balance = account !! GetBalance
        balance mustEqual Some(Balance(0))
      }
      finally {
        account.stop
      }
    }
  }

  "Sending Deposit(10) and then GetBalance to a new Account" should {
    "return a balance of 10" in {
      val account = actorOf[Account].start
      try {
        account ! Deposit(10)
        val balance = account !! GetBalance
        balance mustEqual Some(Balance(10))
      }
      finally {
        account.stop
      }
    }
  }

  "Sending Withdraw(10) and then GetBalance to a new Account" should {
    "return a balance of -10" in {
      val account = actorOf[Account].start
      try {
        account ! Withdraw(10)
        val balance = account !! GetBalance
        balance mustEqual Some(Balance(-10))
      }
      finally {
        account.stop
      }
    }
  }
}
