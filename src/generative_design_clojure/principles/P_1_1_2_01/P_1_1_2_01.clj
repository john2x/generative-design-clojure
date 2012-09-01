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

(ns generative-design-clojure.principles.P_1_1_2_01.P_1_1_2_01
  (:use quil.core)
  (:import java.util.Calendar))

(defn setup []
  (background 0)
  (set-state! :segment-count (atom 360)))

(defn draw []
  (no-stroke)
  (color-mode :hsb 360 (width) (height))
  (background 360)
  (let
    [angle-step (/ 360 @(state :segment-count)),
     radius 300]
    (begin-shape :triangle-fan)
    (vertex (/ (width) 2) (/ (height) 2))
    (doseq
      [angle (range 0 (+ 360 angle-step) angle-step)
       :let [vx (+ (/ (width) 2) (* (->> angle radians cos) radius))
             vy (+ (/ (height) 2) (* (->> angle radians sin) radius))]]
      (vertex vx vy)
      (fill angle (mouse-x) (mouse-y)))))

(defn key-release []
  (case (str (raw-key))
    "1" (reset! (state :segment-count) 360)
    "2" (reset! (state :segment-count) 45)
    "3" (reset! (state :segment-count) 24)
    "4" (reset! (state :segment-count) 12)
    "5" (reset! (state :segment-count) 6)))

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
           :key-released key-release
           :size [800 800])
