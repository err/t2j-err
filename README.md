# t2j-err

This project demonstrates an edge case not handled by [test2junit "1.3.2"](https://github.com/ruedigergad/test2junit). If the Clojure compiler prints warning messages during test execution, test2junit breaks. The breakage is due to clj-assorted-utils not providing an implementation of the 3-arity variant of `java.io.StringWriter/write`.

- `lein test2junit`
- observe error output:

```
Testing: t2j-err.core-test
Running tests in: t2j-err.core-test
  Running test: #'t2j-err.core-test/a-test-creates-reflection-warning
    ERROR
      Message: clojure.lang.ArityException: Wrong number of args (4) passed to: core/apply-junit-output-hook/fn--253/fn--254, compiling:((...)/form-init4255135209920479730.clj:6:21)
      Cause: #error {
 :cause Wrong number of args (4) passed to: core/apply-junit-output-hook/fn--253/fn--254
 :via
 [{:type clojure.lang.ArityException
   :message Wrong number of args (4) passed to: core/apply-junit-output-hook/fn--253/fn--254
   :at [clojure.lang.AFn throwArity AFn.java 429]}]
 :trace
 [[clojure.lang.AFn throwArity AFn.java 429]
  [clojure.lang.AFn invoke AFn.java 44]
  [test2junit.core.proxy$java.io.StringWriter$ff19274a write nil -1]
  [java.io.PrintWriter write PrintWriter.java 456]
  [java.io.PrintWriter write PrintWriter.java 473]
  [java.io.PrintWriter append PrintWriter.java 1004]
  [java.io.PrintWriter append PrintWriter.java 56]
  [java.util.Formatter$FixedString print Formatter.java 2595]
  [java.util.Formatter format Formatter.java 2508]
  [java.io.PrintWriter format PrintWriter.java 905]
  [clojure.lang.Compiler$InstanceFieldExpr <init> Compiler.java 1157]
  [clojure.lang.Compiler$HostExpr$Parser parse Compiler.java 998]
  [clojure.lang.Compiler analyzeSeq Compiler.java 6868]
  [clojure.lang.Compiler analyze Compiler.java 6669]
  [clojure.lang.Compiler analyze Compiler.java 6625]
  [clojure.lang.Compiler$BodyExpr$Parser parse Compiler.java 6001]
  [clojure.lang.Compiler$FnMethod parse Compiler.java 5380]
  [clojure.lang.Compiler$FnExpr parse Compiler.java 3972]
  [clojure.lang.Compiler analyzeSeq Compiler.java 6866]
  [clojure.lang.Compiler analyze Compiler.java 6669]
  [clojure.lang.Compiler eval Compiler.java 6924]
  [clojure.lang.Compiler eval Compiler.java 6890]
  [clojure.core$eval invokeStatic core.clj 3105]
  [clojure.core$eval invoke core.clj 3101]
  [t2j_err.core_test$fn__486 invokeStatic core_test.clj 6]
  [t2j_err.core_test$fn__486 invoke core_test.clj 4]
```



## Notes

- We set `*warn-on-reflection*` to true in project.clj
- `t2j-err.core-test` evaluates code at runtime which produces a warning message from the compiler
- The capture of STDOUT/STDERR is handled by [`clj-assorted-utils.util/with-eo-str`](https://github.com/ruedigergad/clj-assorted-utils/blob/master/src/clj_assorted_utils/util.clj#L535), which is invoked by [test2junit](https://github.com/ruedigergad/test2junit/blob/9829edc5e2b5f3d2ad8dd292dffad45031594c34/src/test2junit/core.clj#L66-L68)
- [clojure/tools.analyzer.jvm](https://github.com/clojure/tools.analyzer.jvm/blob/bbb70a685e2807adcc1ec408169fa6798e713b84/src/main/clojure/clojure/tools/analyzer/passes/jvm/warn_on_reflection.clj#L14) is responsible for printing the reflection warning.
## License

Copyright © 2017 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
