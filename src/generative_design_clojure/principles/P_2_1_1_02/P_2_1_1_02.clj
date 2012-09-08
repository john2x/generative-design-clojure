; P_2_1_1_02.clj
;
; Licensed under the Apache License, Version 2.0

; changing strokeweight on diagonals in a grid with colors
;
; MOUSE
; position x      : left diagonal strokeweight
; position y      : right diagonal strokeweight
; left click      : new random layout
;
; KEYS
; s               : save png
; 1               : round strokecap
; 2               : square strokecap
; 3               : project strokecap
; 4               : color left diagonal
; 5               : color right diagonal
; 6               : transparency left diagonal
; 7               : transparency right diagonal
; 0               : default

(ns generative-design-clojure.principles.P_2_1_1_02.P_2_1_1_02
  (:use quil.core
        quil.helpers.seqs
        [quil.applet :only [current-applet]])
  (:import java.util.Calendar
           generativedesign.GenerativeDesign))

(def DELETE 127)
(def BACKSPACE 8)

(defn setup []
  (set-state!
    :tile-count (atom 20)
    :act-random-seed (atom 0)
    :act-stroke-cap (atom :round)
    :color-left (atom (color 197 0 123))
    :color-right (atom (color 87 35 129))
    :alpha-left (atom 100)
    :alpha-right (atom 100)))

(defn draw []
  (color-mode :hsb 360 100 100 100)
  (background 360)
  (smooth)
  (no-fill)
  (stroke-cap @(state :act-stroke-cap))
  (random-seed @(state :act-random-seed))

  (doseq [grid-y (range @(state :tile-count)),
          grid-x (range @(state :tile-count)),
          :let [pos-x (* (/ (width) @(state :tile-count)) grid-x),
                pos-y (* (/ (height) @(state :tile-count)) grid-y),
                toggle (int (random 0 2))]]
    (when (= toggle 0)
      (do
        (stroke @(state :color-left) @(state :alpha-left))
        (stroke-weight (/ (mouse-x) 10))
        (line pos-x pos-y
              (+ pos-x (/ (width) @(state :tile-count)))
              (+ pos-y (/ (height) @(state :tile-count))))))
    (when (= toggle 1)
      (do
        (stroke @(state :color-right) @(state :alpha-right))
        (stroke-weight (/ (mouse-y) 10))
        (line pos-x 
              (+ pos-y (/ (width) @(state :tile-count)))
              (+ pos-x (/ (height) @(state :tile-count)))
              pos-y)))
    ))

(defn timestamp []
  (let [now (Calendar/getInstance)]
    (format "%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS" now)))

(defn mouse-release []
  (reset! (state :act-random-seed) (random 100000)))

(defn key-release []
  (case (str (raw-key))
    ("s" "S") (save-frame (str (timestamp) "_##.png"))
    ("1") (reset! (state :act-stroke-cap) :round)
    ("2") (reset! (state :act-stroke-cap) :square)
    ("3") (reset! (state :act-stroke-cap) :project)
    ("4") (if (= @(state :color-left) (color 0))
            (reset! (state :color-left) (color 323 100 77))
            (reset! (state :color-left) (color 0)))
    ("5") (if (= @(state :color-right) (color 0))
            (reset! (state :color-right) (color 273 73 51))
            (reset! (state :color-right) (color 0)))
    ("6") (if (= @(state :alpha-left) 100)
            (reset! (state :alpha-left) 50)
            (reset! (state :alpha-left) 100))
    ("7") (if (= @(state :alpha-right) 100)
            (reset! (state :alpha-right) 50)
            (reset! (state :alpha-right) 100))
    ("0") (do
            (reset! (state :act-stroke-cap) :round)
            (reset! (state :color-left) (color 0))
            (reset! (state :color-right) (color 0))
            (reset! (state :alpha-left) 100)
            (reset! (state :alpha-right) 100))
    nil))

(defsketch P_2_1_1_02
           :title "P_2_1_1_02"
           :setup setup
           :draw draw
           :key-released key-release
           :mouse-released mouse-release
           :size [600 600])
