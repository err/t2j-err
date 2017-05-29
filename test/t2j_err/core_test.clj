(ns t2j-err.core-test
  (:require [clojure.test :refer :all]))

(deftest a-test-creates-reflection-warning
  (is (= "0" (eval '(.toString 0)))))
