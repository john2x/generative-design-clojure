; P_2_1_3_02.clj
;
; Licensed under the Apache License, Version 2.0

; changing size of circles in a rad grid depending on mouse position
;
; MOUSE
; position x      : number of tiles horizontally
; position y      : number of tiles vertically
;
; KEYS
; 1-3             : draw mode
; s               : save png

(ns generative-design-clojure.principles.P_2_1_3_02.P_2_1_3_02
  (:use quil.core
        quil.helpers.seqs
        [quil.applet :only [current-applet]])
  (:import java.util.Calendar
           generativedesign.GenerativeDesign))

(defn setup []
  (set-state!
    :count (atom 10)
    :color-step (atom 15)
    :line-weight (atom 0)
    :stroke-color (atom 0)
    :background-color (atom 0)
    :draw-mode (atom 1)))

(defn draw []
  (color-mode :hsb 360 100 100)
  (smooth)
  (stroke-weight 0.5)
  (stroke-cap :round)
  (background @(state :background-color))

  (let [tile-count-x (+ (/ (mouse-x) 30) 1),
        tile-count-y (+ (/ (mouse-y) 30) 1),
        x2 (atom 0), y2 (atom 0)]
    (doseq [grid-y (range-incl tile-count-y),
            grid-x (range-incl tile-count-x)
            :let [tile-width (/ (width) tile-count-x),
                  tile-height (/ (height) tile-count-y),
                  pos-x (* tile-width grid-x),
                  pos-y (* tile-height grid-y),
                  x1 (/ tile-width 2), y1 (/ tile-height 2)]]
      (push-matrix)
      (translate pos-x pos-y)

      (reset! x2 0)
      (reset! y2 0)

      (doseq [side (range 4),
              i (range @(state :count))]
        ; move end point around the four sides of the tile
        (when (= side 0)
          (do (swap! x2 + (/ tile-width @(state :count)))
              (reset! y2 0)))
        (when (= side 1)
          (do (reset! x2 tile-width)
              (swap! y2 + (/ tile-height @(state :count)))))
        (when (= side 2)
          (do (swap! x2 - (/ tile-width @(state :count))))
              (reset! y2 tile-height))
        (when (= side 3)
          (do (reset! x2 0)
              (swap! y2 - (/ tile-height @(state :count)))))

        ; adjust weight and color of the line
        (if (< i (/ @(state :count) 2))
          (do
            (swap! (state :line-weight) inc)
            (swap! (state :stroke-color) + 60))
          (do
            (swap! (state :line-weight) dec)
            (swap! (state :stroke-color) - 60)))

        ; set colors depending on draw mode
        (case @(state :draw-mode)
          1 (do
              (reset! (state :background-color) 360)
              (stroke 0))
          2 (do
              (reset! (state :background-color) 360)
              (stroke 0)
              (stroke-weight @(state :line-weight)))
          3 (do
              (reset! (state :background-color) 0)
              (stroke @(state :stroke-color))
              (stroke-weight (/ (mouse-x) 100)))
          nil)

        ; draw the line
        (line x1 y1 @x2 @y2))
      (pop-matrix))))

(defn timestamp []
  (let [now (Calendar/getInstance)]
    (format "%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS" now)))

(defn key-release []
  (case (str (raw-key))
    ("s" "S") (save-frame (str (timestamp) "_##.png"))
    "1" (reset! (state :draw-mode) 1)
    "2" (reset! (state :draw-mode) 2)
    "3" (reset! (state :draw-mode) 3)
    nil))

(defsketch P_2_1_3_02
           :title "P_2_1_3_02"
           :setup setup
           :draw draw
           :key-released key-release
           :size [600 600])
