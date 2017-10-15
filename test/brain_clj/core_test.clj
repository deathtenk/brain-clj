(ns brain-clj.core-test
  (:require [clojure.test :refer :all]
            [brain-clj.core :refer :all]))

(def test-code
  "++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++.")


(deftest  brain-test
  (is (= (brain test-code) "Hello World!\n")))

(deftest find-previous-bracket-test
  (is (= (find-previous-bracket ["[" "[" "]" "]"] 3) 0)))


(deftest find-next-bracket-test
  (is (find-next-bracket ["[" "[" "]" "]"] 0) 3)))
