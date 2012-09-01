(ns generative-design-clojure.principles.P_1_2_1_01.P_1_2_1_01
  (:use quil.core)
  (:import java.util.Calendar))

; P_1_2_1_01.clj
;
; Licensed under the Apache License, Version 2.0

; shows how to interpolate colors in different styles/ color modes
;
; MOUSE
; left click    : new random color set
; position x    : interpolation resolution
; position y    : row count
;
; KEYS
; 1-2           : switch interpolation style
; s             : save png
(declare shake-colors)

(defn setup []
  (background 0)
  (set-state!
    :tile-count-x (atom 2)
    :tile-count-y (atom 10)
    :colors-left (atom (range 10)) ; start with 10 colors
    :colors-right (atom (range 10)) ; start with 10 colors
    ; :colors (atom []) just for ase export
    :interpolate-shortest (atom true)
    :inter-col (atom (lerp-color 0 0 0)))
  (no-stroke)
  (shake-colors))

(defn draw []
  (reset! (state :tile-count-x) (int (map-range (mouse-x) 0 (width) 2 100)))
  (reset! (state :tile-count-y) (int (map-range (mouse-y) 0 (height) 2 10)))

  (let [tile-width (/ (width) (float @(state :tile-count-x))),
        tile-height (/ (height) (float @(state :tile-count-y))),
        inter-col (atom (color 0)),
        colors-left @(state :colors-left),
        colors-right @(state :colors-right),
        tile-count-x @(state :tile-count-x),
        tile-count-y @(state :tile-count-y)]

    (doseq [grid-y (range tile-count-y)
            :let [col1 (colors-left grid-y),
                  col2 (colors-right grid-y)]]

      (doseq [grid-x (range tile-count-x)
              :let [amount (map-range grid-x 0 (- tile-count-x 1) 0 1)]]

        (if @(state :interpolate-shortest)
          (do
            ; switch to rgb
            (color-mode :rgb 255 255 255 255)
            (reset! (state :inter-col) (lerp-color col1 col2 amount))
            ; switch back
            (color-mode :hsb 360 100 100 100))
          ; else
          (reset! (state :inter-col) (lerp-color col1 col2 amount)))

        (fill @(state :inter-col))
        (rect (* tile-width grid-x) (* tile-height grid-y) tile-width tile-height)))))

(defn key-release []
  (case (str (raw-key))
    "1" (reset! (state :interpolate-shortest) true)
    "2" (reset! (state :interpolate-shortest) false)
    nil))

(defn mouse-release []
  (shake-colors))

(defn timestamp []
  (let [now (Calendar/getInstance)]
    (format "%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS" now)))

(defn key-press []
  (if (= (str (raw-key)) "s")
    (save-frame (str (timestamp) "_##.png"))))

(defn shake-colors []
  (let [colors-left (atom []),
        colors-right (atom [])]

    (doseq [i (range 10)] ; always have 10 colors
      (swap! colors-left conj (color (random 0 60) (random 0 100) 100))
      (swap! colors-right conj (color (random 160 190) 100 (random 0 100))))

    (reset! (state :colors-left) @colors-left)
    (reset! (state :colors-right) @colors-right)))

(defsketch P_1_2_1_01
           :title "P_1_2_1_01"
           :setup setup
           :draw draw
           :key-released key-release
           :mouse-released mouse-release
           :size [800 800])
