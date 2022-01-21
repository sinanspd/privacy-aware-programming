package policy

object PolicyArguments extends ProductArgs {
  def applyProduct[L <: HList, NarrowArgs <: HList, Args <: HList, F, R](
    l: L
  )(implicit 
    ihc: IsHCons.Aux[L, F, NarrowArgs],
    ftp: FnToProduct.Aux[F, Args => R],
    ev: NarrowArgs <:< Args
  ): R = {
    val (func, args) = (l.head, l.tail)
    func.toProduct(args)
  }
}

trait Policy[K]{
  private val data : K
  protected def checkDeclassify() : K 
  def unsafeUnwrap(reason: String): K
}

trait Policy[K]{
  private[policy] val data : K
  protected def check[I](k: K)(implicit args: PolicyArguments, scope: ScopedController): Boolean
  protected def map[B](f: K => B): Policy[B] 
  def and(that: Policy[K]) : Policy[K]
  def unsafeUnwrap(reason: String): K
}

object Policy{
  def apply[A](a : A) = new Policy[A]{
    val data = a
    protected def check[Any](a: A) = Any => true
  }
}

userService.insertBand(band: Band)

5.+(Policy(5))

type A, Policy[A]

class Policy[A] {
  def method1(b: B)
  def method2(a : Policy[A])
  def method3(a: Policy[A], b: B)
}
