(ns brain-clj.core
  (:require [clojure.string :as s]))

(def test-code
  "+++++ +++++             initialize counter (cell #0) to 10
  [                       use loop to set 70/100/30/10
      > +++++ ++              add  7 to cell #1
      > +++++ +++++           add 10 to cell #2
      > +++                   add  3 to cell #3
      > +                     add  1 to cell #4
  <<<< -                  decrement counter (cell #0)
  ]
  > ++ .                  print 'H'
  > + .                   print 'e'
  +++++ ++ .              print 'l'
  .                       print 'l'
  +++ .                   print 'o'
  > ++ .                  print ' '
  << +++++ +++++ +++++ .  print 'W'
  > .                     print 'o'
  +++ .                   print 'r'
  ----- - .               print 'l'
  ----- --- .             print 'd'
  > + .                   print '!'
  > .                     print '\n'")

(defn find-next-bracket [code code-tracker]
  (loop [ct code-tracker]
    (if (= (code ct) "]")
      ct
      (recur (inc ct)))))

(defn find-previous-bracket [code pointer]
  (loop [p pointer]
    (if (= (code p) "[")
      p
      (recur (dec p)))))

(defn brain [text]
  "This function attempts to interpret brainfuck (does not support input)"
  (let [code (s/split test-code #"")]
    (loop [pointer 0 ;; pointer that looks at memory
           mem (into [] (repeat 100 0)) ; memory 
           code-tracker 0 ;; pointer that looks at the code
           out ""] ;; output string
      (if (or (= code-tracker (count code)) (= pointer (count mem)))
        out
        (let [c (int (get mem pointer))
              ch (get code code-tracker)]
          (do #_(println (str "c: " c " ch: " ch " code-tracker: " code-tracker " pointer: " pointer))
              (cond
                (= ch "+") (recur pointer (assoc mem pointer (inc c)) (inc code-tracker)  out)
                (= ch "-") (recur pointer (assoc mem pointer (dec c)) (inc code-tracker)  out)
                (= ch ">") (recur (inc pointer) mem (inc code-tracker) out)
                (= ch "<") (recur (dec pointer) mem (inc code-tracker) out)
                (= ch ".") (recur pointer mem (inc code-tracker) (str out (char c)))
                (= ch "[") (if (= c 0)
                             (recur pointer mem (inc (find-next-bracket code code-tracker)) out)
                             (recur pointer mem (inc code-tracker) out))
                (= ch "]") (if (= c 0)
                             (recur pointer mem (inc code-tracker) out)
                             (recur pointer mem (find-previous-bracket code code-tracker) out))
                :else
                (recur pointer mem (inc code-tracker) out))))))))

(= (brain test-code) "Hello World!\n")
