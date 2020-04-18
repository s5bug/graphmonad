package tf.bug.graphmonad.h

import shapeless.{Nat, Sized}

case class H[+A](
  vertices: Set[A],
  edges: RankedSet[A, HE],
)

case class HE[+A, R <: Nat](
  label: String,
  head: A,
  tail: Sized[Vector[A], R]
)

trait RankedSet[A, Edge[_, _ <: Nat]]
