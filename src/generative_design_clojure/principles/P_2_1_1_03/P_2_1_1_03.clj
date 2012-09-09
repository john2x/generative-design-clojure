; P_2_1_1_03.clj
;
; Licensed under the Apache License, Version 2.0

; changing number, color and strokeweight on diagonals in a grid
;
; MOUSE
; position x      : diagonal strokeweight
; position y      : number diagonals
; left click      : new random layout
;
; KEYS
; s               : save png
; 1               : color left diagonal
; 2               : color right diagonal
; 3               : switch transparency left diagonal on/off
; 4               : switch transparency right diagonal on/off
; 0               : default

(ns generative-design-clojure.principles.P_2_1_1_03.P_2_1_1_03
  (:use quil.core
        quil.helpers.seqs
        [quil.applet :only [current-applet]])
  (:import java.util.Calendar
           generativedesign.GenerativeDesign))

(def DELETE 127)
(def BACKSPACE 8)

(defn setup []
  (color-mode :hsb 360 100 100 100)
  (set-state!
    :color-back (atom (color 360))
    :color-left (atom (color 323 100 77))
    :color-right (atom (color 0))
    :tile-count (atom 1)
    :act-random-seed (atom 0)
    :transparent-left (atom false)
    :transparent-right (atom false)
    :alpha-left (atom 100)
    :alpha-right (atom 100)))

(defn draw []
  (color-mode :hsb 360 100 100 100)
  (background @(state :color-back))
  (smooth)
  (no-fill)
  (random-seed @(state :act-random-seed))
  (stroke-weight (/ (mouse-x) 15))

  (let [tile-count (/ (mouse-y) 15)]
    (doseq [grid-y (range tile-count),
            grid-x (range tile-count),
            :let [pos-x (* (/ (width) tile-count) grid-x),
                  pos-y (* (/ (height) tile-count) grid-y),
                  toggle (int (random 0 2))]]

      (if @(state :transparent-left)
        (reset! (state :alpha-left) (* grid-y 10))
        (reset! (state :alpha-left) 100))
      (if @(state :transparent-right)
        (reset! (state :alpha-right) (- 100 (* grid-y 10)))
        (reset! (state :alpha-right) 100))

      (when (= toggle 0)
        (do
          (stroke @(state :color-left) @(state :alpha-left))
          (line pos-x
                pos-y
                (+ pos-x (/ (/ (width) tile-count) 2))
                (+ pos-y (/ (height) tile-count)))
          (line (+ pos-x (/ (/ (width) tile-count) 2))
                pos-y
                (+ pos-x (/ (width) tile-count))
                (+ pos-y (/ (height) tile-count)))))

      (when (= toggle 1)
        (do
          (stroke @(state :color-right) @(state :alpha-right))
          (line pos-x 
                (+ pos-y (/ (width) tile-count))
                (+ pos-x (/ (/ (height) tile-count) 2))
                pos-y)
          (line (+ pos-x (/ (/ (height) tile-count) 2)) 
                (+ pos-y (/ (width) tile-count))
                (+ pos-x (/ (height) tile-count))
                pos-y)))
      )))

(defn timestamp []
  (let [now (Calendar/getInstance)]
    (format "%1$ty%1$tm%1$td_%1$tH%1$tM%1$tS" now)))

(defn mouse-release []
  (reset! (state :act-random-seed) (random 100000)))

(defn key-release []
  (case (str (raw-key))
    ("s" "S") (save-frame (str (timestamp) "_##.png"))
    ("1") (if (= @(state :color-left) (color 273 73 51))
            (reset! (state :color-left) (color 323 100 77))
            (reset! (state :color-left) (color 273 73 51)))
    ("2") (if (= @(state :color-right) (color 0))
            (reset! (state :color-right) (color 192 100 64))
            (reset! (state :color-right) (color 0)))
    ("3") (reset! (state :transparent-left) (not @(state :transparent-left)))
    ("4") (reset! (state :transparent-right) (not @(state :transparent-right)))
    ("0") (do
            (reset! (state :transparent-left) false)
            (reset! (state :transparent-right) false)
            (reset! (state :color-left) (color 323 100 77))
            (reset! (state :color-right) (color 0)))
    nil))

(defsketch P_2_1_1_03
           :title "P_2_1_1_03"
           :setup setup
           :draw draw
           :key-released key-release
           :mouse-released mouse-release
           :size [600 600])
