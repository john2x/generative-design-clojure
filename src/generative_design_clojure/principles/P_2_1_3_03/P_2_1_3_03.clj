; P_2_1_3_03.clj
;
; Licensed under the Apache License, Version 2.0

; changing positions of stapled circles in a grid
;
; MOUSE
; position x      : module detail
; position y      : module parameter
;
; KEYS
; 1-3             : draw mode
; arrow left/right: number of tiles horizontally
; arrow up/down   : number of tiles vertically
; s               : save png

(ns generative-design-clojure.principles.P_2_1_3_03.P_2_1_3_03
  (:use quil.core
        quil.helpers.seqs
        [quil.applet :only [current-applet]])
  (:import java.util.Calendar
           generativedesign.GenerativeDesign))

(defn setup []
  (background 255)
  (smooth)
  (set-state!
    :tile-count-x (atom 6.0)
    :tile-count-y (atom 6.0)
    :count (atom 0)
    :draw-mode (atom 1)))

(defn draw []
  (color-mode :hsb 360 100 100)
  (rect-mode :center)
  (smooth)
  (stroke 0)
  (no-fill)
  (background 360)

  (let [count (+ (/ (mouse-x) 20) 5),
        para (- (float (/(mouse-y) (height))) 0.5)]

    (doseq [grid-y (range-incl @(state :tile-count-y)),
            grid-x (range-incl @(state :tile-count-x))
            :let [tile-width (/ (width) @(state :tile-count-x)),
                  tile-height (/ (width) @(state :tile-count-y)),
                  pos-x (+ (* tile-width grid-x) (/ tile-width 2)),
                  pos-y (+ (* tile-height grid-y) (/ tile-height 2))]]
      (push-matrix)
      (translate pos-x pos-y)
      
      ; switch between modules
      (case @(state :draw-mode)
        1 (do (translate (/ (* tile-width -1) 2) (/ (* tile-height -1) 2))
              (doseq [i (range count)]
                (line 0 (* (+ para 0.5) tile-height)
                    tile-width (* i (/ tile-height count)))
                (line 0 (* i (/ tile-height count)) tile-width
                      (- tile-height (* (+ para 0.5) tile-height)))))
        2 (doseq [i (range-incl count)]
            (line (* para tile-width) (* para tile-height)
                  (/ tile-width 2) (* (- (/ i count) 0.5) tile-width))
            (line (* para tile-width) (* para tile-height)
                  (/ (* tile-width -1) 2) (* (- (/ i count) 0.5) tile-width))
            (line (* para tile-width) (* para tile-height)
                  (* (- (/ i count) 0.5) tile-width) (/ tile-width 2))
            (line (* para tile-width) (* para tile-height)
                  (* (- (/ i count) 0.5) tile-width) (/ (* tile-width -1) 2)))
        3 (doseq [i (range-incl count)]
            (line 0 (* para tile-height)
                  (/ tile-width 2) (* (- (/ i count) 0.5) tile-width))
            (line 0 (* para tile-height)
                  (/ (* tile-width -1) 2) (* (- (/ i count) 0.5) tile-width))
            (line 0 (* para tile-height)
                  (* (- (/ i count) 0.5) tile-width) (/ tile-width 2))
            (line 0 (* para tile-height)
                  (* (- (/ i count) 0.5) tile-width) (/ (* tile-width -1) 2)))
        nil)
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

(defn key-press []
  (case (key-as-keyword)
    :down (reset! (state :tile-count-y) (max (dec @(state :tile-count-y)) 1))
    :up (swap! (state :tile-count-y) inc)
    :left (reset! (state :tile-count-x) (max (dec @(state :tile-count-x)) 1))
    :right (swap! (state :tile-count-x) inc)
    nil))

(defsketch P_2_1_3_03
           :title "P_2_1_3_03"
           :setup setup
           :draw draw
           :key-released key-release
           :key-pressed key-press
           :size [600 600])
