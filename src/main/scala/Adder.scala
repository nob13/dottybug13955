import scala.annotation.experimental
import scala.quoted.*
import scala.concurrent.Future

trait Adder {
  def add(x: Int): Int
}

object Adder {

  inline def makeAdder(y: Int): Adder = ${
    makeAdderMacro('y)
  }

  @experimental
  def makeAdderMacro(y: Expr[Int])(using Quotes): Expr[Adder] = {
    import quotes.reflect.*
    def decls(cls: Symbol): List[Symbol] = {
        List(
        Symbol.newMethod(
          parent = cls,
          name = "add",
          tpe = MethodType(List("x"))(_ => List(TypeRepr.of[Int]), _ => TypeRepr.of[Int])
        )
        )
    }
    val parents  = List(TypeTree.of[Object], TypeTree.of[Adder])
    val implName = "Adder_impl"
    val cls      = Symbol.newClass(Symbol.spliceOwner, implName, parents = parents.map(_.tpe), decls, selfType = None)
    val defs = List(
      {
        val decl = cls.declaredMethod("add").head
        DefDef(
          decl,
          argss => Some('{
            // Works
            // ${argss.head.head.asExprOf[Int]} + ${y}

            // Crashes
            val result = ${argss.head.head.asExprOf[Int]} + ${y}
            result
          }.asTerm)
        )
      }
    )
    val clsDef = ClassDef(cls, parents, body = defs)
    val newCls =
      Typed(Apply(Select(New(TypeIdent(cls)), cls.primaryConstructor), Nil), TypeTree.of[Adder])
    val result = Block(List(clsDef), newCls).asExprOf[Adder]
    println(s"Result: ${result.show}")
    result
  }

}
