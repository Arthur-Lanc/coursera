package recfun

object Main {
  def main(args: Array[String]) {
    println("Pascal's Triangle")
    for (row <- 0 to 10) {
      for (col <- 0 to row)
        print(pascal(col, row) + " ")
      println()
    }

    println(balance("(just an) example".toList))
    println(balance("(if (zero? x) max (/ 1 x))".toList))
    println(balance("I told him (that it’s not (yet) done). (But he wasn’t listening)".toList))
    println(balance(":-)".toList))
    println(balance("())(".toList))

    println(":-)".toList.head)
    println(":-)".toList.tail)
    println(":".toList.head)
    println(":".toList.tail)

    println(countChange(4,List(1, 2)))
  }

  /**
   * Exercise 1
   */
    def pascal(c: Int, r: Int): Int = {
      if (c == 0 || c == r) 1
      else pascal(c-1,r-1)+pascal(c,r-1)
    }
  
  /**
   * Exercise 2
   */
    def balance(chars: List[Char]): Boolean = {
      def balanceIter(chars: List[Char], count: Int): Boolean ={
        if (count < 0 ) false
        else if (chars.isEmpty) count == 0
        else if (chars.head == '(') balanceIter(chars.tail, count+1)
        else if (chars.head == ')') balanceIter(chars.tail, count-1)
        else balanceIter(chars.tail, count)
      }
      balanceIter(chars, 0)
    }
  
  /**
   * Exercise 3
   */
    def countChange(money: Int, coins: List[Int]): Int = {
      if (money < 0 || coins.isEmpty ) 0
      else if (money == 0 ) 1
      else countChange(money, coins.tail) + countChange(money - coins.head, coins)
    }
  }
