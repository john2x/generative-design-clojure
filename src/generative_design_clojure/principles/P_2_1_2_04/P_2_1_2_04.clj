; P_2_1_2_04.clj
;
; Licensed under the Apache License, Version 2.0

; changing size of circles in a rad grid depending on mouse position
;
; MOUSE
; position x      : corner position offset x
; position y      : corner position offset y
; left click      : random position
;
; KEYS
; s               : save png

(ns generative-design-clojure.principles.P_2_1_2_04.P_2_1_2_04
  (:use quil.core
        quil.helpers.seqs
        [quil.applet :only [current-applet]])
  (:import java.util.Calendar
           generativedesign.GenerativeDesign))

(defn setup []
  (background 255)
  (smooth)
  (set-state!
    :tile-count (atom 20)
    :rect-size (atom 30)
    :act-random-seed (atom 0)))

(defn draw []
  (color-mode :hsb 360 100 100 100)
  (background 360)
  (smooth)
  (no-stroke)

  (fill 192 100 64 60)

  (random-seed @(state :act-random-seed))

  (doseq [grid-y (range @(state :tile-count)),
          grid-x (range @(state :tile-count)),
          :let [pos-x (* (/ (width) @(state :tile-count)) grid-x),
                pos-y (* (/ (height) @(state :tile-count)) grid-y),
                
                shift-x1 (* (/ (mouse-x) 20) (random -1 1)),
                shift-y1 (* (/ (mouse-y) 20) (random -1 1)),
                shift-x2 (* (/ (mouse-x) 20) (random -1 1)),
                shift-y2 (* (/ (mouse-y) 20) (random -1 1)),
                shift-x3 (* (/ (mouse-x) 20) (random -1 1)),
                shift-y3 (* (/ (mouse-y) 20) (random -1 1)),
                shift-x4 (* (/ (mouse-x) 20) (random -1 1)),
                shift-y4 (* (/ (mouse-y) 20) (random -1 1))]]
    (begin-shape)
    (vertex (+ pos-x shift-x1) (+ pos-y shift-y1))
    (vertex (+ pos-x shift-x2 @(state :rect-size)) (+ pos-y shift-y2))
    (vertex (+ pos-x shift-x3 @(state :rect-size)) (+ pos-y shift-y3 @(state :rect-size)))
    (vertex (+ pos-x shift-x4) (+ pos-y shift-y4 @(state :rect-size)))
    (end-shape :close)))

(defn timestamp []
  (let [now (Calendar/getInstance)]
    (format "%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS" now)))

(defn key-release []
  (case (str (raw-key))
    ("s" "S") (save-frame (str (timestamp) "_##.png"))
    nil))

(defn mouse-press []
  (reset! (state :act-random-seed) (random 100000)))

(defsketch P_2_1_2_04
           :title "P_2_1_2_04"
           :setup setup
           :draw draw
           :key-released key-release
           :mouse-pressed mouse-press
           :size [600 600])
