(ns generative-design-clojure.core)

(defn -main
  "Run the sketch in `part`, `chapter`.
  e.g.
  `lein run principles P_1_0_01` will run the sketch in
  principles/P_1_0_01/P_1_0_01.clj"
  [part chapter & args]
  (use (symbol
         (str "generative-design-clojure."
              part "." chapter "." chapter))))
