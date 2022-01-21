import dotty.tools.dotc.plugins.ResearchPlugin
import policy._
/*import policies._

@secure
case class User(
  name: String,
  @secureWith("UserInterestPolicy")
  interests: List[String]
)

@secure
case class User(
  @secureWith(policy1)
  friends: List[Friend]
)

val x = user.friends // Proxy[Policy1..]
val y = x.relationship 

val z = x.age + 5 //Proxy[Policy1[...]]

case class Friend{
  @secureWith(policy2)
  relationship: Relationship
} 

case class Relationship{
  commonInterestes: Interests 
}  */


/////////// When traversing the AST to copy the methods, wrap in a check iff 
/// 1) The member access is policy protected
//// 2) there is a foreign library call 

////// TO DO: 
///// 1) investigate if it is possible to easily distinguish library calls 
////// 2) extract a minimal working version of minerva for band + user + producer 

trait A
trait B

chaperone class Example(a: Int, ab: A embelishes B) 

def f(a : Policy[Int]) = {

}

@main def hello: Unit = 
  val x = Policy(5) + 6
  println(x)
  val a = Example(5, new A embelishes B{})
  println(s"Hello world! ${a.a}")
  println("Hello")

  val x1 : Int = 5 + Policy(5)
  val x2 = Policy(5) + 5


