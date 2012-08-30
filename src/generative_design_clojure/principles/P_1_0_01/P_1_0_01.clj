(ns generative-design-clojure.principles.P_1_0_01.P_1_0_01
  (:use quil.core)
  (:import java.util.Calendar))

; P_1_0_01.clj
;
; Licensed under the Apache License, Version 2.0

; changing colors and size by moving the mouse
;
; MOUSE
; position x    : size
; position y    : color
;
; KEYS
; s             : save png

(defn setup []
  (no-cursor))

(defn draw []
  (color-mode :hsb 360, 100, 100)
  (rect-mode :center)
  (no-stroke)
  (background (/ (mouse-y) 2.0) 100 100)
  (fill (- 360 (/ (mouse-y) 2.0)) 100 100)
  (rect 360 360 (+ (mouse-x) 1) (+ (mouse-x) 1))
  (text-size 20))

(defn timestamp []
  (let [now (Calendar/getInstance)]
    (format "%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS" now)))

(defn key-press []
  (if (= (str (raw-key)) "s")
    (save-frame (str (timestamp) "_##.png"))))

(defsketch P_1_0_01
           :title "P_1_0_01"
           :setup setup
           :draw draw
           :key-pressed key-press
           :size [720 720])
