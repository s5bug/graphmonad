package tf.bug.graphmonad.h

import cats.{Applicative, Functor, Monad}
import shapeless._

case class HyperGraph[A, L](
  vertices: Set[A],
  edges: RankedSet[A, L]
)

case class HyperEdge[A, L, R <: Nat](
  label: L,
  arity: R,
  incidence: Sized[Vector[A], R]
) {
  def mapLabel[M](f: L => M): HyperEdge[A, M, R] = HyperEdge(f(label), arity, incidence)
}

trait Arity[F[_]] {
  type R <: Nat

  val first: F[R]
  val second: R

  override def equals(o: Any): Boolean = o match {
    case oa: Arity[F] =>
      second == oa.second && first == oa.first
    case _ => false
  }
}

object Arity {
  def apply[F[_], A <: Nat](f: F[A], s: A): Arity[F] = new Arity[F] {
    override type R = A
    override val first: F[A] = f
    override val second: A = s
  }
}

case class RankedSet[A, L](values: Set[Arity[HyperEdge[A, L, *]]]) {
  def mapLabel[M](f: L => M): RankedSet[A, M] = RankedSet(
    values.map { arity =>
      val first = arity.first
      val second = arity.second
      Arity(first.mapLabel(f), second)
    }
  )
}

case class H[A, L](
  t: RankedSet[HyperGraph[A, L], L]
) {



}

object H {



}
