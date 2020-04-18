package tf.bug.graphmonad.h

import cats.{Applicative, Functor, Monad}
import shapeless._

case class Label[L, R <: Nat](value: L, arity: R)

trait HyperEdgeLike[L, R <: Nat] {
  val label: Label[L, R]
  val vertices: Sized[Vector[L], R]
  val arity: R
}

case class HyperEdge[L, R <: Nat](
  label: Label[L, R],
  vertices: Sized[Vector[L], R],
  arity: R
) extends HyperEdgeLike[L, R]

trait Arity[F[_]] {
  type R <: Nat

  val first: F[R]
  val second: R
}

trait ArityPreservingFunction[F[_, _ <: Nat], L, M] {
  def apply[R <: Nat](a: F[L, R]): F[M, R]
}

case class RankedSet[F[_ <: Nat]](values: Set[Arity[F]])

case class HyperGraph[L](
  vertices: Set[L],
  edges: RankedSet[HyperEdgeLike[L, *]]
)

case class SourcedHyperGraph[L, R <: Nat](
  label: Label[L, R],
  graph: HyperGraph[L],
  vertices: Sized[Vector[L], R],
  arity: R
) extends HyperEdgeLike[L, R]

object H {

  def pure[L, R <: Nat](l: Label[L, R], arity: R): SourcedHyperGraph[L, R] = SourcedHyperGraph(
    l,

  )

  def map[L, M](fl: HyperGraph[L])(f: ArityPreservingFunction[Label, L, M]): HyperGraph[M] = ???

}
