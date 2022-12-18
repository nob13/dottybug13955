# Update

The bug goes away when `given Quotes = decl.asQuotes`.

Indeed, Scala gives a better error message when `-Xcheck-macros` is activated:

```
[error] -- Error: /home/nos/working/rc/scalabug13955/src/test/scala/AdderTest.scala:6:31 
[error]  6 |    val adder = Adder.makeAdder(5)
[error]    |                ^^^^^^^^^^^^^^^^^^
[error]    |Exception occurred while executing macro expansion.
[error]    |java.lang.AssertionError: assertion failed: Tree had an unexpected owner for val result
[error]    |Expected: method add (AdderTest._$_$_$Adder_impl.add)
[error]    |But was: val macro (AdderTest._$_$macro)
[error]    |
[error]    |
[error]    |The code of the definition of val result is
[error]    |val result: scala.Int = x.+(5)
[error]    |
[error]    |which was found in the code
[error]    |{
[error]    |  val result: scala.Int = x.+(5)
[error]    |
[error]    |  (result: scala.Int)
[error]    |}
[error]    |
[error]    |which has the AST representation
[error]    |Inlined(Some(TypeIdent("Adder$")), Nil, Block(List(ValDef("result", Inferred(), Some(Apply(Select(Inlined(None, Nil, Ident("x")), "+"), List(Inlined(None, Nil, Inlined(None, Nil, Literal(IntConstant(5))))))))), Typed(Ident("result"), Inferred())))
[error]    |
[error]    |
[error]    |
[error]    |Tip: The owner of a tree can be changed using method `Tree.changeOwner`.
[error]    |Tip: The default owner of definitions created in quotes can be changed using method `Symbol.asQuotes`.
```


# Dotty Bug 13955

Demonstration code for https://github.com/lampepfl/dotty/issues/13955

## Observed Behaviour

Compiling/Running test crashes with 

```
Error while emitting AdderTest.scala
java.util.NoSuchElementException: val x$2 while running genBCode on /home/nos/working/rc/scalabug13955/src/test/scala/AdderTest.scala
[info] exception occurred while compiling /home/nos/working/rc/scalabug13955/src/test/scala/AdderTest.scala
java.util.NoSuchElementException: val x$2 while compiling /home/nos/working/rc/scalabug13955/src/test/scala/AdderTest.scala
[error] ## Exception when compiling 1 sources to /home/nos/working/rc/scalabug13955/target/scala-3.2.1/test-classes
[error] java.util.NoSuchElementException: val x$2
[error] scala.collection.mutable.AnyRefMap$ExceptionDefault.apply(AnyRefMap.scala:508)
[error] scala.collection.mutable.AnyRefMap$ExceptionDefault.apply(AnyRefMap.scala:507)
[error] scala.collection.mutable.AnyRefMap.apply(AnyRefMap.scala:207)
[error] dotty.tools.backend.jvm.BCodeSkelBuilder$PlainSkelBuilder$locals$.load(BCodeSkelBuilder.scala:514)
[error] dotty.tools.backend.jvm.BCodeBodyBuilder$PlainBodyBuilder.genLoadTo(BCodeBodyBuilder.scala:423)
[error] dotty.tools.backend.jvm.BCodeBodyBuilder$PlainBodyBuilder.genLoad(BCodeBodyBuilder.scala:290)
[error] dotty.tools.backend.jvm.BCodeBodyBuilder$PlainBodyBuilder.genArithmeticOp(BCodeBodyBuilder.scala:146)
[error] dotty.tools.backend.jvm.BCodeBodyBuilder$PlainBodyBuilder.genPrimitiveOp(BCodeBodyBuilder.scala:254)
[error] dotty.tools.backend.jvm.BCodeBodyBuilder$PlainBodyBuilder.genApply(BCodeBodyBuilder.scala:793)
[error] dotty.tools.backend.jvm.BCodeBodyBuilder$PlainBodyBuilder.genLoadTo(BCodeBodyBuilder.scala:369)
[error] dotty.tools.backend.jvm.BCodeBodyBuilder$PlainBodyBuilder.genLoad(BCodeBodyBuilder.scala:290)
[error] dotty.tools.backend.jvm.BCodeBodyBuilder$PlainBodyBuilder.genLoadTo(BCodeBodyBuilder.scala:307)
[error] dotty.tools.backend.jvm.BCodeBodyBuilder$PlainBodyBuilder.genLoad(BCodeBodyBuilder.scala:290)
[error] dotty.tools.backend.jvm.BCodeBodyBuilder$PlainBodyBuilder.genStat(BCodeBodyBuilder.scala:111)
[error] dotty.tools.backend.jvm.BCodeBodyBuilder$PlainBodyBuilder.genBlockTo$$anonfun$1(BCodeBodyBuilder.scala:1052)
[error] scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
[error] scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
[error] scala.collection.immutable.List.foreach(List.scala:333)
[error] dotty.tools.backend.jvm.BCodeBodyBuilder$PlainBodyBuilder.genBlockTo(BCodeBodyBuilder.scala:1052)
[error] dotty.tools.backend.jvm.BCodeBodyBuilder$PlainBodyBuilder.genLoadTo(BCodeBodyBuilder.scala:441)
[error] dotty.tools.backend.jvm.BCodeSkelBuilder$PlainSkelBuilder.emitNormalMethodBody$1(BCodeSkelBuilder.scala:809)
[error] dotty.tools.backend.jvm.BCodeSkelBuilder$PlainSkelBuilder.genDefDef(BCodeSkelBuilder.scala:832)
[error] dotty.tools.backend.jvm.BCodeSkelBuilder$PlainSkelBuilder.gen(BCodeSkelBuilder.scala:615)
[error] dotty.tools.backend.jvm.BCodeSkelBuilder$PlainSkelBuilder.gen$$anonfun$1(BCodeSkelBuilder.scala:621)
[error] scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
[error] scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
[error] scala.collection.immutable.List.foreach(List.scala:333)
[error] dotty.tools.backend.jvm.BCodeSkelBuilder$PlainSkelBuilder.gen(BCodeSkelBuilder.scala:621)
[error] dotty.tools.backend.jvm.BCodeSkelBuilder$PlainSkelBuilder.genPlainClass(BCodeSkelBuilder.scala:233)
[error] dotty.tools.backend.jvm.GenBCodePipeline$Worker1.visit(GenBCode.scala:266)
[error] dotty.tools.backend.jvm.GenBCodePipeline$Worker1.run(GenBCode.scala:231)
[error] dotty.tools.backend.jvm.GenBCodePipeline.buildAndSendToDisk(GenBCode.scala:598)
[error] dotty.tools.backend.jvm.GenBCodePipeline.run(GenBCode.scala:564)
[error] dotty.tools.backend.jvm.GenBCode.run(GenBCode.scala:69)
[error] dotty.tools.dotc.core.Phases$Phase.runOn$$anonfun$1(Phases.scala:316)
[error] scala.collection.immutable.List.map(List.scala:246)
[error] dotty.tools.dotc.core.Phases$Phase.runOn(Phases.scala:320)
[error] dotty.tools.backend.jvm.GenBCode.runOn(GenBCode.scala:77)
[error] dotty.tools.dotc.Run.runPhases$1$$anonfun$1(Run.scala:233)
[error] scala.runtime.function.JProcedure1.apply(JProcedure1.java:15)
[error] scala.runtime.function.JProcedure1.apply(JProcedure1.java:10)
[error] scala.collection.ArrayOps$.foreach$extension(ArrayOps.scala:1321)
[error] dotty.tools.dotc.Run.runPhases$1(Run.scala:244)
[error] dotty.tools.dotc.Run.compileUnits$$anonfun$1(Run.scala:252)
[error] dotty.tools.dotc.Run.compileUnits$$anonfun$adapted$1(Run.scala:261)
[error] dotty.tools.dotc.util.Stats$.maybeMonitored(Stats.scala:68)
[error] dotty.tools.dotc.Run.compileUnits(Run.scala:261)
[error] dotty.tools.dotc.Run.compileSources(Run.scala:185)
[error] dotty.tools.dotc.Run.compile(Run.scala:169)
[error] dotty.tools.dotc.Driver.doCompile(Driver.scala:35)
[error] dotty.tools.xsbt.CompilerBridgeDriver.run(CompilerBridgeDriver.java:88)
[error] dotty.tools.xsbt.CompilerBridge.run(CompilerBridge.java:22)
[error] sbt.internal.inc.AnalyzingCompiler.compile(AnalyzingCompiler.scala:91)
[error] sbt.internal.inc.MixedAnalyzingCompiler.$anonfun$compile$7(MixedAnalyzingCompiler.scala:193)
[error] scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.java:23)
[error] sbt.internal.inc.MixedAnalyzingCompiler.timed(MixedAnalyzingCompiler.scala:248)
[error] sbt.internal.inc.MixedAnalyzingCompiler.$anonfun$compile$4(MixedAnalyzingCompiler.scala:183)
[error] sbt.internal.inc.MixedAnalyzingCompiler.$anonfun$compile$4$adapted(MixedAnalyzingCompiler.scala:163)
[error] sbt.internal.inc.JarUtils$.withPreviousJar(JarUtils.scala:239)
[error] sbt.internal.inc.MixedAnalyzingCompiler.compileScala$1(MixedAnalyzingCompiler.scala:163)
[error] sbt.internal.inc.MixedAnalyzingCompiler.compile(MixedAnalyzingCompiler.scala:211)
[error] sbt.internal.inc.IncrementalCompilerImpl.$anonfun$compileInternal$1(IncrementalCompilerImpl.scala:534)
[error] sbt.internal.inc.IncrementalCompilerImpl.$anonfun$compileInternal$1$adapted(IncrementalCompilerImpl.scala:534)
[error] sbt.internal.inc.Incremental$.$anonfun$apply$5(Incremental.scala:179)
[error] sbt.internal.inc.Incremental$.$anonfun$apply$5$adapted(Incremental.scala:177)
[error] sbt.internal.inc.Incremental$$anon$2.run(Incremental.scala:463)
[error] sbt.internal.inc.IncrementalCommon$CycleState.next(IncrementalCommon.scala:116)
[error] sbt.internal.inc.IncrementalCommon$$anon$1.next(IncrementalCommon.scala:56)
[error] sbt.internal.inc.IncrementalCommon$$anon$1.next(IncrementalCommon.scala:52)
[error] sbt.internal.inc.IncrementalCommon.cycle(IncrementalCommon.scala:263)
[error] sbt.internal.inc.Incremental$.$anonfun$incrementalCompile$8(Incremental.scala:418)
[error] sbt.internal.inc.Incremental$.withClassfileManager(Incremental.scala:506)
[error] sbt.internal.inc.Incremental$.incrementalCompile(Incremental.scala:405)
[error] sbt.internal.inc.Incremental$.apply(Incremental.scala:171)
[error] sbt.internal.inc.IncrementalCompilerImpl.compileInternal(IncrementalCompilerImpl.scala:534)
[error] sbt.internal.inc.IncrementalCompilerImpl.$anonfun$compileIncrementally$1(IncrementalCompilerImpl.scala:488)
[error] sbt.internal.inc.IncrementalCompilerImpl.handleCompilationError(IncrementalCompilerImpl.scala:332)
[error] sbt.internal.inc.IncrementalCompilerImpl.compileIncrementally(IncrementalCompilerImpl.scala:425)
[error] sbt.internal.inc.IncrementalCompilerImpl.compile(IncrementalCompilerImpl.scala:137)
[error] sbt.Defaults$.compileIncrementalTaskImpl(Defaults.scala:2363)
[error] sbt.Defaults$.$anonfun$compileIncrementalTask$2(Defaults.scala:2313)
[error] sbt.internal.server.BspCompileTask$.$anonfun$compute$1(BspCompileTask.scala:30)
[error] sbt.internal.io.Retry$.apply(Retry.scala:46)
[error] sbt.internal.io.Retry$.apply(Retry.scala:28)
[error] sbt.internal.io.Retry$.apply(Retry.scala:23)
[error] sbt.internal.server.BspCompileTask$.compute(BspCompileTask.scala:30)
[error] sbt.Defaults$.$anonfun$compileIncrementalTask$1(Defaults.scala:2311)
[error] scala.Function1.$anonfun$compose$1(Function1.scala:49)
[error] sbt.internal.util.$tilde$greater.$anonfun$$u2219$1(TypeFunctions.scala:62)
[error] sbt.std.Transform$$anon$4.work(Transform.scala:68)
[error] sbt.Execute.$anonfun$submit$2(Execute.scala:282)
[error] sbt.internal.util.ErrorHandling$.wideConvert(ErrorHandling.scala:23)
[error] sbt.Execute.work(Execute.scala:291)
[error] sbt.Execute.$anonfun$submit$1(Execute.scala:282)
[error] sbt.ConcurrentRestrictions$$anon$4.$anonfun$submitValid$1(ConcurrentRestrictions.scala:265)
[error] sbt.CompletionService$$anon$2.call(CompletionService.scala:64)
[error] java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
[error] java.base/java.util.concurrent.Executors$RunnableAdapter.call(Executors.java:515)
[error] java.base/java.util.concurrent.FutureTask.run(FutureTask.java:264)
[error] java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1128)
[error] java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:628)
[error] java.base/java.lang.Thread.run(Thread.java:829)
[error]            
[error] stack trace is suppressed; run last Test / compileIncremental for the full output
[error] (Test / compileIncremental) java.util.NoSuchElementException: val x$2
[error] Total time: 3 s, completed 16.12.2022, 09:51:48
```

## Expected Behaviour

Testcase works