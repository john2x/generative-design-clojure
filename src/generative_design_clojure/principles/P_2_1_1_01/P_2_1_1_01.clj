; P_2_1_1_01.clj
;
; Licensed under the Apache License, Version 2.0

; drawing a filled circle with lines
;
; MOUSE
; position x      : left diagonal strokeweight
; position y      : right diagonal strokeweight
; left click      : new random layout
;
; KEYS
; 1               : round strokecap
; 2               : square strokecap
; 3               : project strokecap
; s               : save png

(ns generative-design-clojure.principles.P_2_1_1_01.P_2_1_1_01
  (:use quil.core
        quil.helpers.seqs
        [quil.applet :only [current-applet]])
  (:import java.util.Calendar
           generativedesign.GenerativeDesign))

(def DELETE 127)
(def BACKSPACE 8)

(defn setup []
  (color-mode :hsb 360 100 100 100)
  (smooth)
  (no-fill)
  (background 360)
  (set-state!
    :tile-count (atom 20)
    :act-random-seed (atom 0)
    :act-stroke-cap (atom :round)))

(defn draw []
  (background 255)
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
        (stroke-weight (/ (mouse-x) 20))
        (line pos-x pos-y
              (+ pos-x (/ (width) @(state :tile-count)))
              (+ pos-y (/ (height) @(state :tile-count))))))
    (when (= toggle 1)
      (do
        (stroke-weight (/ (mouse-y) 20))
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
    nil))

(defsketch P_2_1_1_01
           :title "P_2_1_1_01"
           :setup setup
           :draw draw
           :key-released key-release
           :mouse-released mouse-release
           :size [600 600])
