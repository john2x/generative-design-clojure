; P_2_1_2_01.clj
;
; Licensed under the Apache License, Version 2.0

; changing the size and position of circles in a grid
;
; MOUSE
; position x      : circle position
; position y      : circle size
; left click      : random position
;
; KEYS
; s               : save png

(ns generative-design-clojure.principles.P_2_1_2_01.P_2_1_2_01
  (:use quil.core
        quil.helpers.seqs
        [quil.applet :only [current-applet]])
  (:import java.util.Calendar
           generativedesign.GenerativeDesign))

(defn setup []
  (background 255)
  (smooth)
  (set-state!
    :tile-count (atom 20.0)
    :circle-color (atom (color 0))
    :circle-alpha (atom 180)
    :act-random-seed (atom 0)))

(defn draw []
  (translate (/ (/ (width) @(state :tile-count)) 2)
             (/ (/ (height) @(state :tile-count)) 2))
  (background 255)
  (smooth)
  (no-fill)

  (random-seed @(state :act-random-seed))

  (stroke @(state :circle-color) @(state :circle-alpha))
  (stroke-weight (/ (mouse-y) 60))

  (let [tile-count @(state :tile-count)]
    (doseq [grid-y (range tile-count),
            grid-x (range tile-count),
            :let [pos-x (* (/ (width) tile-count) grid-x),
                  pos-y (* (/ (height) tile-count) grid-y),
                  shift-x (/ (random (unchecked-negate (mouse-x)) (mouse-x)) 20),
                  shift-y (/ (random (unchecked-negate (mouse-x)) (mouse-x)) 20)]]
      (ellipse (+ pos-x shift-x) (+ pos-y shift-y)
               (/ (mouse-y) 15) (/ (mouse-y) 15))
      )))

(defn timestamp []
  (let [now (Calendar/getInstance)]
    (format "%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS" now)))

(defn key-release []
  (case (str (raw-key))
    ("s" "S") (save-frame (str (timestamp) "_##.png"))
    nil))

(defn mouse-press []
  (reset! (state :act-random-seed) (random 100000)))

(defsketch P_2_1_2_01
           :title "P_2_1_2_01"
           :setup setup
           :draw draw
           :key-released key-release
           :mouse-pressed mouse-press
           :size [600 600])
