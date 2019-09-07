package util

fun <P1, P2, R> compose(f: (P1) -> P2, g: (P2) -> R): (P1) -> R = { x -> g(f(x)) }

@JvmName("composeExt")
fun <P1, P2, R> ((P1) -> P2).compose(f: (P2) -> R): (P1) -> (R) = { x -> f(this.invoke(x)) }