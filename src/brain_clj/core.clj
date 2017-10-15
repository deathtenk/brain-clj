(ns brain-clj.core
  (:require [clojure.string :as s]))

(defn find-next-bracket
  ([code code-tracker]
   (find-next-bracket code code-tracker -1))
  ([code code-tracker nested]
   (loop [ct code-tracker
          n nested]
     (if (and (= (code ct) "]") (= n 0))
       ct
       (cond
         (= "[" (code ct)) (recur (inc ct) (inc n))
         (= "]" (code ct)) (recur (inc ct) (dec n))
         :else (recur (inc ct) n))))))

(defn find-previous-bracket
  ([code code-tracker]
   (find-previous-bracket code code-tracker -1))
  ([code code-tracker nested]
   (loop [ct code-tracker
          n nested]
     (if (and (= (code ct) "[") (= n 0))
       ct
       (cond
         (= "]" (code ct)) (recur (dec ct) (inc n))
         (= "[" (code ct)) (recur (dec ct) (dec n))
         :else (recur (dec ct) n))))))

(defn brain [text]
  "This function attempts to interpret brainfuck (does not support input)"
  (let [code (s/split text #"")]
    (loop [pointer 0                    ;; pointer that looks at memory
           mem (into [] (repeat 100 0)) ;; memory
           code-tracker 0               ;; pointer that looks at the code
           out ""]                      ;; output string
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
