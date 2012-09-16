; P_2_1_3_05.clj
;
; Licensed under the Apache License, Version 2.0

; changing positions of stapled circles in a grid
;
; MOUSE
; position x      : horizontal position shift
; position y      : vertical position shift
; left click      : random position shift
;
; KEYS
; s               : save png

(ns generative-design-clojure.principles.P_2_1_3_05.P_2_1_3_05
  (:use quil.core
        quil.helpers.seqs
        [quil.applet :only [current-applet]])
  (:import java.util.Calendar
           generativedesign.GenerativeDesign))

(defn setup []
  (set-state!
    :tile-count-x (atom 10.0)
    :tile-count-y (atom 10.0)
    :color-step (atom 6)
    :act-random-seed (atom 0)))

(defn draw []
  (color-mode :hsb 360 100 100)
  (smooth)
  (no-stroke)
  (background 360)
  (random-seed @(state :act-random-seed))

  (let [step-size (/ (mouse-x) 10),
        end-size (/ (mouse-y) 10)]

    (doseq [grid-y (range-incl @(state :tile-count-y)),
            grid-x (range-incl @(state :tile-count-x))
            :let [tile-width (/ (width) @(state :tile-count-x)),
                  tile-height (/ (width) @(state :tile-count-y)),
                  pos-x (+ (* tile-width grid-x) (/ tile-width 2)),
                  pos-y (+ (* tile-height grid-y) (/ tile-height 2))]]
      (case (int (random 4))
        0 (doseq [i (range step-size),
                  :let [diameter (map-range i 0 step-size tile-width end-size)]]
            (fill (- 360 (* i @(state :color-step))))
            (ellipse (+ pos-x i) pos-y diameter diameter))
        1 (doseq [i (range step-size),
                  :let [diameter (map-range i 0 step-size tile-width end-size)]]
            (fill (- 360 (* i @(state :color-step))))
            (ellipse pos-x (+ pos-y i) diameter diameter))
        2 (doseq [i (range step-size),
                  :let [diameter (map-range i 0 step-size tile-width end-size)]]
            (fill (- 360 (* i @(state :color-step))))
            (ellipse (- pos-x i) pos-y diameter diameter))
        3 (doseq [i (range step-size),
                  :let [diameter (map-range i 0 step-size tile-width end-size)]]
            (fill (- 360 (* i @(state :color-step))))
            (ellipse pos-x (- pos-y i) diameter diameter))
        nil))))

(defn timestamp []
  (let [now (Calendar/getInstance)]
    (format "%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS" now)))

(defn key-release []
  (case (str (raw-key))
    ("s" "S") (save-frame (str (timestamp) "_##.png"))
    nil))

(defn mouse-press []
  (reset! (state :act-random-seed) (int (random 100000))))

(defsketch P_2_1_3_05
           :title "P_2_1_3_05"
           :setup setup
           :draw draw
           :key-released key-release
           :mouse-pressed mouse-press
           :size [600 600])
