import scala.language.implicitConversions

package object policy {
 
  implicit def policy2A[A](p: Policy[A]) : A = {
    p.data
  }
}