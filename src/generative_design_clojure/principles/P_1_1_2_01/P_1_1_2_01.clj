(ns generative-design-clojure.principles.P_1_1_2_01.P_1_1_2_01
  (:use quil.core)
  (:import java.util.Calendar))

; P_1_1_1_01.clj
;
; Licensed under the Apache License, Version 2.0

; changing the color circle by moving the mouse.
;
; MOUSE
; position x    : saturation
; position y    : brightness
;
; KEYS
; 1-5           : number of segments
; s             : save png

(defn setup []
  (background 0))

(defn draw []
  (no-stroke)
  (color-mode :hsb (width) (height) 100)
  (let
    [step-x (+ (mouse-x) 2),
     step-y (+ (mouse-y) 2)]
    (doseq
      [grid-y (range 0 (height) step-y),
       grid-x (range 0 (width) step-x)]
      (fill grid-x (- (height) grid-y) 100)
      (rect grid-x grid-y step-x step-y))))

(defn timestamp []
  (let [now (Calendar/getInstance)]
    (format "%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS" now)))

(defn key-press []
  (if (= (str (raw-key)) "s")
    (save-frame (str (timestamp) "_##.png"))))

(defsketch P_1_1_2_01
           :title "P_1_1_2_01"
           :setup setup
           :draw draw
           :key-pressed key-press
           :size [800 400])
