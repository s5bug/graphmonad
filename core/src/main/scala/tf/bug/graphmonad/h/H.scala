package tf.bug.graphmonad.h

import cats.{Applicative, Functor, Monad}
import shapeless._

case class Label[L, R <: Nat](value: L, arity: R)

case class HyperEdge[L, R <: Nat](
  label: Label[L, R],
  vertices: Sized[Vector[L], R],
  arity: R
)

trait Arity[F[_]] {
  type R <: Nat

  val first: F[R]
  val second: R
}

case class RankedSet[F[_ <: Nat]](values: Set[Arity[F]])

case class HyperGraph[L](
  vertices: Set[L],
  edges: RankedSet[HyperEdge[L, *]]
)

object H extends Monad[HyperGraph] {
  override def flatMap[A, B](fa: HyperGraph[A])(f: A => HyperGraph[B]): HyperGraph[B] = ???

  override def tailRecM[A, B](a: A)(f: A => HyperGraph[Either[A, B]]): HyperGraph[B] = ???

  override def pure[A](x: A): HyperGraph[A] = ???
}
