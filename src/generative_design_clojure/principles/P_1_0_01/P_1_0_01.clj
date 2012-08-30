(ns generative-design-clojure.principles.P_1_0_01.P_1_0_01
  (:use quil.core))

(defn setup []
  (no-cursor))

(defn draw []
  (color-mode :hsb 360, 100, 100)
  (rect-mode :center)
  (no-stroke)
  (background (/ (mouse-y) 2.0) 100 100)
  (fill (- 360 (/ (mouse-y) 2.0)) 100 100)
  (rect 360 360 (+ (mouse-x) 1) (+ (mouse-x) 1)))

(defsketch P_1_0_01
           :title "P_1_0_01"
           :setup setup
           :draw draw
           :size [720 720])
