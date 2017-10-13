# brain-clj

A Clojure brainfuck compiler.

## Usage

```clojure
(require '[brain-clj.core :refer brain])
(def brainf-code "++++++++[>++++[>++>+++>+++>+<<<<-]>+>+>->>+[<]<-]>>.>---.+++++++..+++.>>.<-.<.+++.------.--------.>>+.>++.")

(brain brainf-code) ;; => "Hello World!\n"
```

## License

Copyright © 2017 FIXME

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
