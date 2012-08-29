(ns generative-design-clojure.principles.P_1_0_01.P_1_0_01
  (:use quil.core))

(defn setup []
  (no-cursor)
  (set-state! :mouse-position (atom [0 0])))

(defn draw []
  (color-mode :hsb 360, 100, 100)
  (rect-mode :center)
  (no-stroke)
  (let [[x y] @(state :mouse-position)]
    (background (/ y 2.0) 100 100)
    (fill (- 360 (/ y 2.0)) 100 100)
    (rect 360 360 (+ x 1) (+ x 1))))

(defn mouse-moved []
  (let [x (mouse-x), y (mouse-y)]
    (reset! (state :mouse-position) [x y])))
