package util

fun <P1, P2, R> partially(p1: P1, f: (P1, P2) -> R): (P2) -> R = { p2 -> f(p1, p2) }

fun <P1, P2, P3, R> partially(p1: P1, p2: P2, f: (P1, P2, P3) -> R): (P3) -> R = { p3 -> f(p1, p2, p3) }

fun <P1, P2, R> ((P1, P2) -> R).partially(p1: P1): (P2) -> R = { p2 -> this.invoke(p1, p2) }

fun <P1, P2, P3, R> ((P1, P2, P3) -> R).partially(p1: P1, p2: P2): (P3) -> R = { p3 -> this.invoke(p1, p2, p3) }