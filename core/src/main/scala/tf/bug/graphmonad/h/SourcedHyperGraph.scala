package tf.bug.graphmonad.h

import cats.Monad

case class SourcedHyperGraph[+A](
  values: Set[A],
  edges: Set[SourcedHyperGraph[A]],
  head: A,
  tail: Set[A]
)

object SourcedHyperGraph {

  implicit val hSourcedHyperGraphMonad: Monad[SourcedHyperGraph] = new Monad[SourcedHyperGraph] {

    override def pure[A](x: A): SourcedHyperGraph[A] =
      SourcedHyperGraph(
        Set(x),
        Set(),
        x,
        Set()
      )

    override def flatMap[A, B](fa: SourcedHyperGraph[A])(f: A => SourcedHyperGraph[B]): SourcedHyperGraph[B] =
      flatten(map(fa)(f))

    override def map[A, B](fa: SourcedHyperGraph[A])(f: A => B): SourcedHyperGraph[B] =
      SourcedHyperGraph(
        fa.values.map(f),
        fa.edges.map(map(_)(f)),
        f(fa.head),
        fa.tail.map(f)
      )

    override def flatten[A](ffa: SourcedHyperGraph[SourcedHyperGraph[A]]): SourcedHyperGraph[A] =
      SourcedHyperGraph(
        ffa.values.flatMap(_.values),
        ffa.edges.map(flatten[A]),
        ffa.head.head,
        ffa.tail.flatMap(_.tail)
      )

    // FIXME Not stack safe!
    override def tailRecM[A, B](a: A)(f: A => SourcedHyperGraph[Either[A, B]]): SourcedHyperGraph[B] = flatMap(f(a)) {
      case Left(a)  => tailRecM(a)(f)
      case Right(b) => pure(b)
    }

  }

}
